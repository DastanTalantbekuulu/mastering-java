package org.jqurantree.arabic.encoding.unicode;

import org.jqurantree.arabic.encoding.ArabicDecoderBase;

public class UnicodeDecoder extends ArabicDecoderBase {
	public UnicodeDecoder() {
		super(UnicodeTable.getUnicodeTable());
	}
}
