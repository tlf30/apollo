package org.apollo.net.release.generator.parse;

import com.google.common.base.MoreObjects;

/**
 * A {@link Value} that is a simple String literal.
 *
 * @author Major
 */
public class StringValue extends Value {

	/**
	 * The value of this StringValue.
	 */
	private final String value;

	/**
	 * Creates the StringValue.
	 *
	 * @param name The name of the StringValue. Must not be {@code null}.
	 * @param value The value of the StringValue. Must not be {@code null}.
	 */
	public StringValue(String name, String value) {
		super(name);
		this.value = value;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("name", name).add("value", value).toString();
	}

	/**
	 * Gets the value of this StringValue.
	 *
	 * @return The value. Will never be {@code null}.
	 */
	public String getValue() {
		return value;
	}

}
