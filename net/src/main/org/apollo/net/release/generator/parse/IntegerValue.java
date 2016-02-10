package org.apollo.net.release.generator.parse;

import com.google.common.base.MoreObjects;

/**
 * A {@link Value} that is a simple integer literal.
 *
 * @author Major
 */
public final class IntegerValue extends Value {

	/**
	 * The value of this IntegerValue.
	 */
	private final long value;

	/**
	 * Creates the IntegerValue.
	 *
	 * @param name The name of the IntegerValue. Must not be {@code null}.
	 * @param value The value of the IntegerValue.
	 */
	public IntegerValue(String name, long value) {
		super(name);
		this.value = value;
	}

	/**
	 * Gets the value of this IntegerValue.
	 *
	 * @return The value.
	 */
	public long getValue() {
		return value;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("name", name).add("value", value).toString();
	}

}
