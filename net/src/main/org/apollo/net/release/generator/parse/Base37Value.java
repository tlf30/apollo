package org.apollo.net.release.generator.parse;

import com.google.common.base.MoreObjects;

/**
 * A {@link Value} for a String encoded in base-37, meaning it must be deserialized from a {@code long}.
 *
 * @author Major
 */
public final class Base37Value extends Value {

	/**
	 * Creates the Base37Value.
	 *
	 * @param name The name of the Base37Value. Must not be {@code null}.
	 */
	public Base37Value(String name) {
		super(name);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("name", name).toString();
	}

}
