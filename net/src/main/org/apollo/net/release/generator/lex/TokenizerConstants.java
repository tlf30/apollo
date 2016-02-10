package org.apollo.net.release.generator.lex;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * Contains {@link Tokenizer}-related constants.
 *
 * @author Major
 */
public final class TokenizerConstants {

	/**
	 * The default read-ahead limit marking input.
	 */
	public static final int DEFAULT_READ_AHEAD_LIMIT = 50;

	/**
	 * The value that notifies some sort of input reader than the end of the file has been reached.
	 */
	public static final int END_OF_FILE = -1;

	/**
	 * The {@link Set} of all tolerated whitespace characters.
	 */
	public static final Set<Character> WHITESPACE_CHARACTERS = ImmutableSet.of(
		' ', '\n', '\r', '\t',
		'\u000C' /* form feed */, '\u000B' /* line tab */, '\u0085' /* next line */,
		'\u00A0'  /* no-break space */, '\u1680' /* Ogham space markInput */,
		'\u2000' /* en quad */, '\u2001' /* em quad */, '\u2002' /* en space */, '\u2003' /* em space */,
		'\u2004' /* three-per-em space */, '\u2005' /* four-per-em space */, '\u2006' /* six-per-em space */,
		'\u2007' /* figure space */, '\u2008' /* thin space */, '\u200A' /* hair space */,
		'\u2028' /* line separator */, '\u2029' /* paragraph separator */, '\u202F' /* narrow no-break space */,
		'\u205F' /* medium mathematical space */, '\u3000' /* ideographic space */
	);

	/**
	 * Sole private constructor to prevent instantiation.
	 */
	private TokenizerConstants() {

	}

}
