package org.apollo.net.release.generator.lex;

/**
 * A categorised lexeme to use when parsing.
 *
 * @author Major
 */
public final class Token {

	/**
	 * The category of a Token.
	 */
	public enum Type {

		/**
		 * A alphabetical lexeme with special meaning to the parser.
		 */
		KEYWORD,

		/**
		 * A numerical lexeme.
		 */
		NUMBER,

		/**
		 * A special type of Token, indicating the end of the input has been reached.
		 */
		END_OF_FILE,

		/**
		 * A string lexeme.
		 */
		STRING,

		/**
		 * A comma character, ','.
		 */
		COMMA,

		/**
		 * A close brace character, '}'.
		 */
		CLOSE_BRACE,

		/**
		 * An open brace character, '{'.
		 */
		OPEN_BRACE,

		/**
		 * A colon character, ':'.
		 */
		COLON,

		/**
		 * An alphabetical lexeme that denotes a variable or type name.
		 */
		IDENTIFIER

	}

	/**
	 * Creates a Token spanning from the specified byte points.
	 *
	 * @param type The {@link Type} of the Token. Must not be {@code null}.
	 * @param value The lexeme of the Token.
	 * @param start The starting index of the lexeme, inclusive, in bytes. Must not be negative.
	 * @param end The ending index of the lexeme, inclusive, in bytes. Must not be negative.
	 * @return The Token. Will never be {@code null}.
	 */
	public static Token spanning(Type type, char value, int start, int end) { // TODO use start/end
		return new Token(Character.toString(value), type);
	}

	/**
	 * Creates a Token spanning from the specified byte points.
	 *
	 * @param type The {@link Type} of the Token. Must not be {@code null}.
	 * @param value The lexeme of the Token. Must not be {@code null}.
	 * @param start The starting index of the lexeme, inclusive, in bytes. Must not be negative.
	 * @param end The ending index of the lexeme, inclusive, in bytes. Must not be negative.
	 * @return The Token. Will never be {@code null}.
	 */
	public static Token spanning(Type type, String value, int start, int end) { // TODO use start/end
		return new Token(value, type);
	}

	/**
	 * The lexeme of this Token.
	 */
	private final String lexeme;

	/**
	 * The {@link Type} of this Token
	 */
	private final Type type;

	/**
	 * Creates the Token.
	 *
	 * @param lexeme The lexeme of the Token. Must not be {@code null}. May be empty.
	 * @param type The {@link Type} of the Token. Must not be {@code null}.
	 */
	public Token(String lexeme, Type type) {
		this.lexeme = lexeme;
		this.type = type;
	}

	/**
	 * Gets the {@link Type} of this Token.
	 *
	 * @return The type. Will never be {@code null}.
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Gets the lexeme of this Token.
	 *
	 * @return The lexeme. Will never be {@code null}.
	 */
	public String getValue() {
		return lexeme;
	}

}
