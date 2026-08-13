package org.jqurantree.arabic.encoding.simple;

import org.jqurantree.arabic.ByteFormat;
import org.jqurantree.arabic.CharacterType;
import org.jqurantree.arabic.DiacriticType;
import org.jqurantree.arabic.encoding.ArabicEncoderBase;

public class SimpleEncoder extends ArabicEncoderBase {

	public SimpleEncoder() {
	}

	protected void encodeCharacter(byte[] buffer, int offset) {

		if (buffer[offset] == ByteFormat.WHITESPACE) {
			text.append("<space>");
		} else {
			CharacterType characterType = CharacterType.valueOf(buffer[offset]);
			if (characterType == CharacterType.Alif
					&& ByteFormat.isSingleDiacritic(buffer, offset,
							DiacriticType.AlifKhanjareeya)) {
				text.append("AlifKhanjareeya");
			} else {
				text.append(characterType);
				for (DiacriticType diacriticType : DiacriticType.values) {
					if (ByteFormat.isDiacritic(buffer, offset, diacriticType)) {
						text.append(" + ");
						text.append(diacriticType);
					}
				}
			}
		}
	}

	protected void writeCharacterSeperator() {
		text.append(" | ");
	}
}
