package org.apollo.net.release.generator.parse;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * A {@link Value} composed of other {@link Value}s.
 *
 * @author Major
 */
public final class CompositeValue extends Value {

	/**
	 * The {@link ImmutableList} of {@link Value}s this CompositeValue consists of.
	 */
	private final ImmutableList<Value> contents;

	/**
	 * Creates the CompositeValue.
	 *
	 * @param name The name of the CompositeValue. Must not be {@code null}.
	 * @param contents The {@link List} of {@link Value}s the CompositeValue contains. Must not be {@code null}.
	 */
	public CompositeValue(String name, List<Value> contents) {
		super(name);
		this.contents = ImmutableList.copyOf(contents);
	}

	/**
	 * Gets the {@link ImmutableList} of {@link Value}s this CompositeValue consists of.
	 *
	 * @return The {@link ImmutableList} of {@link Value}s. Will never be {@code null}. May be empty.
	 */
	public ImmutableList<Value> getContents() {
		return contents;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("name", name).add("contents", contents).toString();
	}
}
