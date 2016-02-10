package org.apollo.net.codec.game;

import java.util.Arrays;
import java.util.Optional;

/**
 * Represents the different simple data types.
 *
 * @author Graham
 */
public enum DataType {

	/**
	 * A byte.
	 */
	BYTE(1),

	/**
	 * A short.
	 */
	SHORT(2),

	/**
	 * A 'tri byte' - a group of three bytes.
	 */
	TRI_BYTE(3),

	/**
	 * An integer.
	 */
	INT(4),

	/**
	 * A long.
	 */
	LONG(8);

	/**
	 * Attempts to find the DataType with the specified amount of bytes.
	 *
	 * @param bytes The amount of bytes of the DataType to find.
	 * @return The {@link Optional} containing the DataType if found. Will never be {@code null}.
	 */
	public static Optional<DataType> valueOf(int bytes) {
		return Arrays.stream(values()).filter(value -> value.bytes == bytes).findAny();
	}

	/**
	 * The number of bytes this type occupies.
	 */
	private final int bytes;

	/**
	 * Creates a data type.
	 *
	 * @param bytes The number of bytes it occupies.
	 */
	DataType(int bytes) {
		this.bytes = bytes;
	}

	/**
	 * Gets the number of bytes the data type occupies.
	 *
	 * @return The number of bytes.
	 */
	public int getBytes() {
		return bytes;
	}

}