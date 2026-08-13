package org.jqurantree.arabic.encoding;

import org.jqurantree.arabic.ByteFormat;
import org.jqurantree.arabic.CharacterType;
import org.jqurantree.arabic.encoding.unicode.UnicodeType;

public abstract class ArabicEncoderBase implements ArabicEncoder {

    private byte[] buffer;
    private int offset;
    private byte value;
    private boolean isMaddah;
    private boolean isHamzaAbove;
    private final EncodingTableBase encodingTable;
    private EncodingOptions options;

    protected final StringBuilder text = new StringBuilder();

    protected ArabicEncoderBase() {
        encodingTable = null;
    }

    protected ArabicEncoderBase(EncodingTableBase encodingTable) {
        this.encodingTable = encodingTable;
    }

    public String encode(byte[] buffer, int offset, int characterCount, EncodingOptions options) {

        this.options = options;
        for (int i = 0; i < characterCount; i++) {
            if (i > 0) {
                writeCharacterSeperator();
            }
            encodeCharacter(buffer, offset);
            offset += ByteFormat.CHARACTER_WIDTH;
        }
        return text.toString();
    }

    protected void writeCharacterSeperator() {
    }

    protected void encodeCharacter(byte[] buffer, int offset) {

        this.buffer = buffer;
        this.offset = offset;

        value = buffer[offset];
        if (value == ByteFormat.WHITESPACE) {
            text.append(' ');
        } else {
            text.append(getCharacter());
            writeDiacritics();
        }
    }

    private char getCharacter() {

        CharacterType characterType = CharacterType.valueOf(value);
        UnicodeType unicodeType = null;
        isMaddah = ByteFormat.isMaddah(buffer, offset);
        isHamzaAbove = ByteFormat.isHamzaAbove(buffer, offset);

        if (options == EncodingOptions.CombineAlifWithMaddah
                && characterType == CharacterType.Alif
                && ByteFormat.isMaddah(buffer, offset)) {
            unicodeType = UnicodeType.AlifWithMaddah;
            isMaddah = false;
        } else if (characterType == CharacterType.Alif && isHamzaAbove
                && !ByteFormat.isAlifKhanjareeya(buffer, offset)) {
            unicodeType = UnicodeType.AlifWithHamzaAbove;
            isHamzaAbove = false;
        } else if (characterType == CharacterType.Waw && isHamzaAbove) {
            unicodeType = UnicodeType.WawWithHamzaAbove;
            isHamzaAbove = false;
        } else if (characterType == CharacterType.Alif
                && ByteFormat.isHamzaBelow(buffer, offset)) {
            unicodeType = UnicodeType.AlifWithHamzaBelow;
        } else if (characterType == CharacterType.Ya && isHamzaAbove) {
            unicodeType = UnicodeType.YaWithHamzaAbove;
            isHamzaAbove = false;
        } else if (characterType == CharacterType.Alif
                && ByteFormat.isAlifKhanjareeya(buffer, offset)) {
            unicodeType = UnicodeType.AlifKhanjareeya;
        } else if (characterType == CharacterType.Alif
                && ByteFormat.isHamzatWasl(buffer, offset)) {
            unicodeType = UnicodeType.AlifWithHamzatWasl;
        }

        return unicodeType != null ? encodingTable.getCharacter(unicodeType)
                : encodingTable.getCharacter(characterType);
    }

    private void writeDiacritics() {

        if (isHamzaAbove) {
            text.append(encodingTable.getCharacter(UnicodeType.HamzaAbove));
        }

        if (ByteFormat.isShadda(buffer, offset)) {
            text.append(encodingTable.getCharacter(UnicodeType.Shadda));
        }

        if (ByteFormat.isFathatan(buffer, offset)) {
            text.append(encodingTable.getCharacter(UnicodeType.Fathatan));
        }

        if (ByteFormat.isDammatan(buffer, offset)) {
            text.append(encodingTable.getCharacter(UnicodeType.Dammatan));
        }

        if (ByteFormat.isKasratan(buffer, offset)) {
            text.append(encodingTable.getCharacter(UnicodeType.Kasratan));
        }

        if (ByteFormat.isFatha(buffer, offset)) {
            text.append(encodingTable.getCharacter(UnicodeType.Fatha));
        }

        if (ByteFormat.isDamma(buffer, offset)) {
            text.append(encodingTable.getCharacter(UnicodeType.Damma));
        }

        if (ByteFormat.isKasra(buffer, offset)) {
            text.append(encodingTable.getCharacter(UnicodeType.Kasra));
        }

        if (ByteFormat.isSukun(buffer, offset)) {
            text.append(encodingTable.getCharacter(UnicodeType.Sukun));
        }

        if (isMaddah) {
            text.append(encodingTable.getCharacter(UnicodeType.Maddah));
        }
    }
}
