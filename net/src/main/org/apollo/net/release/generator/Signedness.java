package org.apollo.net.release.generator;

/**
 * The signedness of a piece of data being decoded.
 *
 * @author Major
 */
public enum Signedness {

	/**
	 * Indicates the data is signed (i.e. can be negative).
	 */
	SIGNED,

	/**
	 * Indicates the data is unsigned (i.e. can only be positive).
	 */
	UNSIGNED,

}