package org.jqurantree.arabic.encoding.buckwalter;

import org.jqurantree.arabic.encoding.ArabicEncoderBase;

public class BuckwalterEncoder extends ArabicEncoderBase {
	public BuckwalterEncoder() {
		super(BuckwalterTable.getBuckwalterTable());
	}
}
