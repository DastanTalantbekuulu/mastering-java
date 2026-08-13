package org.jqurantree.arabic.encoding;

import org.jqurantree.arabic.CharacterType;
import org.jqurantree.arabic.DiacriticType;

class EncodingTableItem {

	private final CharacterType characterType;
	private final DiacriticType diacriticType;

	public EncodingTableItem(CharacterType characterType,
			DiacriticType diacriticType) {
		this.characterType = characterType;
		this.diacriticType = diacriticType;
	}

	public CharacterType getCharacterType() {
		return characterType;
	}

	public DiacriticType getDiacriticType() {
		return diacriticType;
	}
}
