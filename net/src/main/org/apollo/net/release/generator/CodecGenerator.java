package org.apollo.net.release.generator;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableSet;
import org.apollo.net.release.generator.parse.Codec;

import java.util.Set;

/**
 * @author Major
 */
public final class CodecGenerator {

	private static final String CODEC_PACKAGE = "org.apollo.game.release.r";

	private final ImmutableSet<Codec> codecs;

	private final String release;

	public CodecGenerator(String release, Set<Codec> codecs) {
		this.release = release;
		this.codecs = ImmutableSet.copyOf(codecs);
	}

	private String camelcase(String string) {
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, string);
	}

	private void generate(Codec codec) {
		switch (codec.type()) {
			case ENCODER:
				generateEncoder(codec);
				break;
			case DECODER:
				generateDecoder(codec);
				break;
		}

		throw new UnsupportedOperationException("Unsupported codec type " + codec.type() + ".");
	}

	private void generateDecoder(Codec codec) {

	}

	private void generateEncoder(Codec codec) {
		String name = CODEC_PACKAGE + release + "." + camelcase(codec.name());
	}

}
