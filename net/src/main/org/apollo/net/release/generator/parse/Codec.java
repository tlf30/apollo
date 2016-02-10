package org.apollo.net.release.generator.parse;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Optional;

/**
 * @author Major
 */
public final class Codec {

	public enum Type {

		ENCODER,

		DECODER;

		public static Optional<Type> parse(String input) {
			if (input.equals("downstream")) {
				return Optional.of(ENCODER);
			} else if (input.equals("upstream")) {
				return Optional.of(DECODER);
			}

			return Optional.empty();
		}

	}

	private final String name;

	private final Type type;

	private final Map<String, ? extends Value> values;

	public Codec(String name, Type type, Map<String, ? extends Value> values) {
		this.name = name;
		this.type = type;
		this.values = ImmutableMap.copyOf(values);
	}

	public String name() {
		return name;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("name", name).add("type", type).add("values", values).toString();
	}

	public Type type() {
		return type;
	}

	public Map<String, ? extends Value> values() {
		return values;
	}

}
