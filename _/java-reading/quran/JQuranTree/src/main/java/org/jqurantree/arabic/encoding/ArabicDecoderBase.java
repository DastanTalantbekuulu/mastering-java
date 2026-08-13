package org.jqurantree.arabic.encoding;

import org.jqurantree.arabic.ArabicTextBuilder;
import org.jqurantree.arabic.CharacterType;
import org.jqurantree.arabic.DiacriticType;

public abstract class ArabicDecoderBase implements ArabicDecoder {

	private final EncodingTableBase encodingTable;
	private final ArabicTextBuilder builder = new ArabicTextBuilder();

	protected ArabicDecoderBase(EncodingTableBase encodingTable) {
		this.encodingTable = encodingTable;
	}

	public byte[] decode(String text) {
		int size = text.length();
		for (int i = 0; i < size; i++) {
			decode(text.charAt(i));
		}
		return builder.toByteArray();
	}

	private void decode(char ch) {

		EncodingTableItem item = encodingTable.getItem(ch);
		if (item != null) {
			CharacterType characterType = item.getCharacterType();
			DiacriticType diacriticType = item.getDiacriticType();

			if (characterType != null) {
				builder.add(characterType);
			}
			if (diacriticType != null) {
				builder.add(diacriticType);
			}

		} else {
			builder.addWhitespace();
		}
	}
}
