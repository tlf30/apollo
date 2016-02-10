package org.apollo.net.release.generator.parse;

import com.google.common.base.MoreObjects;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.release.generator.Signedness;

import java.util.Optional;

/**
 * A single piece of data, used as part of a {@link Codec}.
 *
 * @author Major
 */
public final class DataValue extends Value {

	/**
	 * Creates a {@link DataValue}, using default {@link DataTransformation}s and {@link DataOrder}s if they are not
	 * present.
	 *
	 * @param name The name of the value. Must not be {@code null}.
	 * @param signedness The {@link Signedness} of the data. Must not be {@code null}.
	 * @param type The {@link DataType} of the data. Must not be {@code null}.
	 * @param transformation The {@link Optional} possibly containing the {@link DataTransformation} to apply. Must not
	 * be {@code null}.
	 * @param order The {@link Optional} possibly containing the {@link DataOrder}. Must not be {@code null}.
	 * @return The {@link DataValue}. Will never be {@code null}.
	 */
	public static DataValue create(String name, Signedness signedness, DataType type,
	                               Optional<DataTransformation> transformation, Optional<DataOrder> order) {
		DataTransformation dataTransformation = transformation.orElse(DataTransformation.NONE);
		DataOrder endianness = order.orElse(DataOrder.BIG);

		return new DataValue(name, signedness, type, dataTransformation, endianness);
	}

	/**
	 * The {@link DataOrder} of this DataValue.
	 */
	private final DataOrder order;

	/**
	 * The {@link Signedness} of this DataValue.
	 */
	private final Signedness signedness;

	/**
	 * The {@link DataTransformation} of this DataValue.
	 */
	private final DataTransformation transformation;

	/**
	 * The {@link DataType} of this DataValue.
	 */
	private final DataType type;

	/**
	 * Creates the DataValue.
	 *
	 * @param name The name of the DataValue. Must not be {@code null}.
	 * @param signedness The {@link Signedness} of the data. Must not be {@code null}.
	 * @param type The {@link DataType} of the data. Must not be {@code null}.
	 * @param transformation The {@link DataTransformation} to apply to the data. Must not be {@code null}.
	 * @param order The {@link DataOrder} of the data. Must not be {@code null}.
	 */
	public DataValue(String name, Signedness signedness, DataType type, DataTransformation transformation,
	                 DataOrder order) {
		super(name);
		this.signedness = signedness;
		this.type = type;
		this.transformation = transformation;
		this.order = order;
	}

	/**
	 * Gets the {@link DataOrder} of the data.
	 *
	 * @return The {@link DataOrder}. Will never be {@code null}.
	 */
	public DataOrder getOrder() {
		return order;
	}

	/**
	 * Gets the {@link Signedness} of the data.
	 *
	 * @return The {@link Signedness}. Will never be {@code null}.
	 */
	public Signedness getSignedness() {
		return signedness;
	}

	/**
	 * Gets the {@link DataTransformation} to apply to the data.
	 *
	 * @return The {@link DataTransformation}. Will never be {@code null}.
	 */
	public DataTransformation getTransformation() {
		return transformation;
	}

	/**
	 * Gets the {@link DataType} of the data.
	 *
	 * @return The {@link DataType}. Will never be {@code null}.
	 */
	public DataType getType() {
		return type;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("name", name).add("order", order).add("signedness", signedness)
			.add("transformation", transformation).add("type", type).toString();
	}

}
