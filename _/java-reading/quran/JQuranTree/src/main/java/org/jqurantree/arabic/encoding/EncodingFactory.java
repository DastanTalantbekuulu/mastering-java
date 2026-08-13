package org.jqurantree.arabic.encoding;

import org.jqurantree.arabic.encoding.buckwalter.BuckwalterDecoder;
import org.jqurantree.arabic.encoding.buckwalter.BuckwalterEncoder;
import org.jqurantree.arabic.encoding.simple.SimpleEncoder;
import org.jqurantree.arabic.encoding.unicode.UnicodeDecoder;
import org.jqurantree.arabic.encoding.unicode.UnicodeEncoder;
import org.jqurantree.core.error.Errors;
import org.jqurantree.core.error.QuranException;

public class EncodingFactory {

	static {
		new EncodingFactory();
	}
	private EncodingFactory() {
	}

	public static ArabicEncoder getEncoder(EncodingType encodingType) {

		ArabicEncoder encoder;

		switch (encodingType) {
		case Simple:
			encoder = new SimpleEncoder();
			break;
		case Unicode:
			encoder = new UnicodeEncoder();
			break;
		case Buckwalter:
			encoder = new BuckwalterEncoder();
			break;
		default:
			throw new QuranException(Errors.INVALID_ENCODING_TYPE);
		}
		return encoder;
	}

	public static ArabicDecoder getDecoder(EncodingType encodingType) {

		ArabicDecoder decoder;

		switch (encodingType) {
		case Unicode:
			decoder = new UnicodeDecoder();
			break;
		case Buckwalter:
			decoder = new BuckwalterDecoder();
			break;
		default:
			throw new QuranException(Errors.INVALID_ENCODING_TYPE);
		}
		return decoder;
	}
}
