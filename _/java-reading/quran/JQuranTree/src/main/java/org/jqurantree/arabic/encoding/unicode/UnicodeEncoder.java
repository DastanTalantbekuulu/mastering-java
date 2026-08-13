package org.jqurantree.arabic.encoding.unicode;

import org.jqurantree.arabic.encoding.ArabicEncoderBase;

public class UnicodeEncoder extends ArabicEncoderBase {
	public UnicodeEncoder() {
		super(UnicodeTable.getUnicodeTable());
	}
}
