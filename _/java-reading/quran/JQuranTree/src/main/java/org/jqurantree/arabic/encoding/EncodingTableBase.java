package org.jqurantree.arabic.encoding;

import java.util.HashMap;
import java.util.Map;

import org.jqurantree.arabic.CharacterType;
import org.jqurantree.arabic.DiacriticType;
import org.jqurantree.arabic.encoding.unicode.UnicodeType;

public abstract class EncodingTableBase {

    private final Map<Character, EncodingTableItem> unicodeMap = new HashMap<Character, EncodingTableItem>();
    private final char[] characterList = new char[CharacterType.values.length];
    private final char[] unicodeList = new char[UnicodeType.values().length];

    protected EncodingTableBase() {
    }

    public EncodingTableItem getItem(char unicode) {
        return unicodeMap.get(unicode);
    }

    public char getCharacter(CharacterType characterType) {
        return characterList[characterType.ordinal()];
    }

    public char getCharacter(UnicodeType unicodeType) {
        return unicodeList[unicodeType.ordinal()];
    }

    protected void addItem(UnicodeType unicodeType, char ch, CharacterType characterType) {
        addItem(unicodeType, ch, characterType, null);
    }

    protected void addItem(UnicodeType unicodeType, char ch, DiacriticType diacriticType) {
        addItem(unicodeType, ch, null, diacriticType);
    }

    protected void addItem(UnicodeType unicodeType, char ch, CharacterType characterType, DiacriticType diacriticType) {

        EncodingTableItem item = new EncodingTableItem(characterType, diacriticType);

        unicodeMap.put(ch, item);

        if (characterType != null && diacriticType == null) {
            characterList[characterType.ordinal()] = ch;
        }

        unicodeList[unicodeType.ordinal()] = ch;
    }
}
