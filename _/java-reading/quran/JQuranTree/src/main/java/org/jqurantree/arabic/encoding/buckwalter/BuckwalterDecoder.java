package org.jqurantree.arabic.encoding.buckwalter;

import org.jqurantree.arabic.encoding.ArabicDecoderBase;

public class BuckwalterDecoder extends ArabicDecoderBase {
	public BuckwalterDecoder() {
		super(BuckwalterTable.getBuckwalterTable());
	}
}
