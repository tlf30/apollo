package org.apollo.net.release.generator.parse;

/**
 * A value in a {@link Codec}. This is analogous to a name-value pair in JSON.
 *
 * <code>
 *     downstream Type {
 *         value_name: value // this line is a Value
 *     }
 * </code>
 *
 * @author Major
 */
public abstract class Value { // TODO better name

	/**
	 * The name of this Value.
	 */
	protected final String name;

	/**
	 * Creates the Value.
	 *
	 * @param name The name of the Value. Must not be {@code null}.
	 */
	protected Value(String name) {
		this.name = name;
	}

	/**
	 * Gets the name of this Value.
	 *
	 * @return The name. Will never be {@code null}.
	 */
	public String getName() {
		return name;
	}

}
