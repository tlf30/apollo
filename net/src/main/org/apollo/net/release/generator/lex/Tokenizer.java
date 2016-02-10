package org.apollo.net.release.generator.lex;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Predicate;

/**
 * Performs lexical analysis on input.
 *
 * @author Major
 */
public final class Tokenizer implements Closeable {

	/**
	 * The {@link ImmutableSet} of keywords.
	 */
	private static final ImmutableSet<String> KEYWORDS = ImmutableSet.of(
		"downstream", "upstream",

		"i8", "i16", "i32", "i64",
		"u8", "u16", "u32", "u64",
		"string", "base37",

		"big", "little", "middle", "inverse",
		"added", "subtracted", "negated"
	);

	/**
	 * The source of input for this Tokenizer.
	 */
	private final BufferedReader reader;

	/**
	 * The index of the most recently tokenized character(s), in bytes.
	 */
	private int index;

	/**
	 * Whether or not this Tokenizer is currently {@link #mark() marked}.
	 */
	private boolean marked;

	/**
	 * Creates the Tokenizer.
	 *
	 * @param reader The {@link BufferedReader} to read input from. Must not be {@code null}.
	 */
	public Tokenizer(BufferedReader reader) {
		this.reader = reader;
	}

	@Override
	public void close() throws IOException {
		reader.close();
	}

	/**
	 * Gets the next available {@link Token}.
	 *
	 * @return The {@link Token}. Will never be {@code null}.
	 */
	public Token next() {
		int next = readCharacter();

		if (next == -1) {
			int end = index - 1; // EOF is really the previous character.
			return Token.spanning(Token.Type.END_OF_FILE, "", end, end);
		}

		char character = (char) next;

		if (Character.charCount(next) == 1) {
			if (isAsciiLetter(character)) {
				String lexeme = readWhile(new StringBuilder(Character.toString(character)), this::isIdentifier);
				Token.Type type = KEYWORDS.contains(lexeme) ? Token.Type.KEYWORD : Token.Type.IDENTIFIER;

				return createToken(type, lexeme);
			} else if (isDecimalDigit(character)) {
				return readNumber(character);
			} else if (TokenizerConstants.WHITESPACE_CHARACTERS.contains(character)) {
				return next();
			}

			switch (character) {
				case '{':
					return createToken(Token.Type.OPEN_BRACE, character);
				case '}':
					return createToken(Token.Type.CLOSE_BRACE, character);
				case ',':
					return createToken(Token.Type.COMMA, character);
				case ':':
					return createToken(Token.Type.COLON, character);
				case '"':
					return readString();
				case '#':
					return next();
			}
		}

		return next();
	}

	/**
	 * Tokenizes the input available to this {@link Tokenizer}.
	 *
	 * Note that this method will read {@link Token}s until the underlying input mechanism is exhausted. The returned
	 * {@link ImmutableList} will contain a {@link Token} with a type of {@link Token.Type#END_OF_FILE}, which
	 * indicates the end of the input has been reached.
	 *
	 * @return The {@link ImmutableList} of {@link Token}s. Will never be {@code null} or empty.
	 */
	public ImmutableList<Token> tokenize() {
		ImmutableList.Builder<Token> builder = ImmutableList.builder();
		Token next;

		do {
			next = next();
			builder.add(next);
		} while (next.getType() != Token.Type.END_OF_FILE);

		return builder.build();
	}

	/**
	 * Creates a {@link Token} using {@link #index} for the ending index and {@code index - 1} for the starting index.
	 *
	 * @param type The {@link Token.Type type} of the {@link Token}. Must not be {@code null}.
	 * @param value The value of the {@link Token}.
	 * @return The {@link Token}. Will never be {@code null}.
	 */
	private Token createToken(Token.Type type, char value) {
		int offset = index - 1;
		return Token.spanning(type, value, offset, offset);
	}

	/**
	 * Creates a {@link Token} using {@link #index} for the ending index and {@code index - value.length()} for the
	 * starting index.
	 *
	 * @param type The {@link Token.Type type} of the {@link Token}. Must not be {@code null}.
	 * @param value The value of the {@link Token}. Must not be {@code null}.
	 * @return The {@link Token}. Will never be {@code null}.
	 */
	private Token createToken(Token.Type type, String value) {
		return Token.spanning(type, value, index - value.length(), index - 1);
	}

	/**
	 * Returns whether or not the specified {@code char} is a letter in ASCII (i.e. matches {@code [a-zA-Z]}).
	 *
	 * @param character The character to check.
	 * @return {@code true} iff the character is an ASCII (latin) letter.
	 */
	private boolean isAsciiLetter(char character) {
		return (character >= 'a' && character <= 'z') || (character >= 'A' && character <= 'Z');
	}

	/**
	 * Returns whether or not the specified {@code char} is a valid character in a decimal literal (i.e. matches {@code
	 * [0-9_]}).
	 *
	 * @param character The character to check.
	 * @return {@code true} iff the character is a valid decimal literal character.
	 */
	private boolean isDecimalDigit(char character) {
		return character >= '0' && character <= '9' || character == '_';
	}

	/**
	 * Returns whether or not the specified character can be used in the tail (anything after the first character) of
	 * an identifier.
	 *
	 * @param character The character.
	 * @return {@code true} iff the character can be used in the tail of an identifier.
	 */
	private boolean isIdentifier(char character) {
		return isAsciiLetter(character) || isDecimalDigit(character) || character == '_';
	}

	/**
	 * Marks this Tokenizer, to jump back to at a later point.
	 */
	private void mark() {
		if (marked) {
			throw new IllegalStateException("Cannot mark an already-marked Tokenizer.");
		}

		marked = true;

		try {
			reader.mark(50);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Gets the next unit of input from the {@link BufferedReader}.
	 *
	 * @return The next unit of input.
	 * @throws UncheckedIOException If there is an error reading from the {@link BufferedReader}.
	 */
	private int nextCharacter() {
		try {
			return reader.read();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Peeks at the next available character.
	 *
	 * @return The peeked character.
	 */
	private int peekCharacter() {
		mark();
		int next = nextCharacter();
		reset();
		return next;
	}

	/**
	 * Reads the next available input character and increments the {@link #index} appropriately.
	 *
	 * @return The next character.
	 */
	private int readCharacter() {
		int next = nextCharacter();

		if (!marked) {
			index += (next < 1 << Byte.SIZE) ? Byte.BYTES : Short.BYTES;
		}

		return next;
	}

	/**
	 * Reads a decimal number.
	 *
	 * @param character The starting character.
	 * @return The number {@link Token}. Will never be {@code null}.
	 */
	private Token readNumber(char character) {
		StringBuilder builder = new StringBuilder(Character.toString(character));
		int start = index - 1;

		readWhile(builder, this::isDecimalDigit);
		return Token.spanning(Token.Type.NUMBER, builder.toString(), start, index - 1);
	}

	/**
	 * Reads a {@link Token.Type#STRING string} {@link Token}.
	 *
	 * @return The {@link Token}. Will never be {@code null}.
	 */
	private Token readString() {
		StringBuilder builder = new StringBuilder();
		int start = index - 1;
		int next = readCharacter();

		while (next != '"') {
			if (next == -1) {
				throw new IllegalStateException("Strings must be terminated.");
			}

			builder.append((char) next);
			next = readCharacter();
		}

		String string = builder.toString();
		return Token.spanning(Token.Type.STRING, string, start, index - 1);
	}

	/**
	 * Reads characters into the specified {@link StringBuilder} whilst the specified {@link Predicate} is satisfied by
	 * the input.
	 *
	 * @param builder The {@link StringBuilder} to read characters into. Must not be {@code null}.
	 * @param predicate The {@link Predicate} that must be satisfied to continue reading. Must not be {@code null}.
	 * @return All of the read input, as a String. Will never be {@code null}.
	 */
	private String readWhile(StringBuilder builder, Predicate<Character> predicate) {
		int next = peekCharacter();

		while (next != -1 && predicate.test((char) next)) {
			builder.append((char) next);
			readCharacter();
			next = peekCharacter();
		}

		return builder.toString();
	}

	/**
	 * Resets this Tokenizer, jumping the read point back to the previously-set mark.
	 */
	private void reset() {
		resetInput();
		marked = false;
	}

	/**
	 * Resets the mark of the {@link BufferedReader}.
	 *
	 * @throws UncheckedIOException If there is an error resetting the {@link BufferedReader}.
	 */
	private void resetInput() {
		try {
			reader.reset();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
