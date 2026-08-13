package org.jqurantree.arabic;

import org.jqurantree.arabic.encoding.EncodingFactory;
import org.jqurantree.arabic.encoding.EncodingType;

public class ArabicCharacter {

    private final byte[] buffer;
    private int offset;

    ArabicCharacter(byte[] buffer, int offset) {
        this.buffer = buffer;
        this.offset = offset;
    }

    public CharacterType getType() {
        return CharacterType.valueOf(buffer[offset]);
    }

    public boolean isFatha() {
        return ByteFormat.isFatha(buffer, offset);
    }

    public boolean isDamma() {
        return ByteFormat.isDamma(buffer, offset);
    }

    public boolean isKasra() {
        return ByteFormat.isKasra(buffer, offset);
    }

    public boolean isFathatan() {
        return ByteFormat.isFathatan(buffer, offset);
    }

    public boolean isDammatan() {
        return ByteFormat.isDammatan(buffer, offset);
    }

    public boolean isKasratan() {
        return ByteFormat.isKasratan(buffer, offset);
    }

    public boolean isShadda() {
        return ByteFormat.isShadda(buffer, offset);
    }

    public boolean isSukun() {
        return ByteFormat.isSukun(buffer, offset);
    }

    public boolean isMaddah() {
        return ByteFormat.isMaddah(buffer, offset);
    }

    public boolean isHamzaAbove() {
        return ByteFormat.isHamzaAbove(buffer, offset);
    }

    public boolean isHamzaBelow() {
        return ByteFormat.isHamzaBelow(buffer, offset);
    }

    public boolean isHamzatWasl() {
        return ByteFormat.isHamzatWasl(buffer, offset);
    }

    public boolean isAlifKhanjareeya() {
        return ByteFormat.isAlifKhanjareeya(buffer, offset);
    }

    public boolean isSingleDiacritic(DiacriticType diacriticType) {
        return ByteFormat.isSingleDiacritic(buffer, offset, diacriticType);
    }

    public int getDiacriticCount() {
        return ByteFormat.getDiacriticCount(buffer, offset);
    }

    public boolean isLetter() {
        return ByteFormat.isLetter(buffer, offset);
    }

    @Override
    public String toString() {
        return toString(EncodingType.Simple);
    }

    public String toString(EncodingType encodingType) {
        return EncodingFactory.getEncoder(encodingType).encode(buffer, offset,
                1, null);
    }

    public String toUnicode() {
        return toString(EncodingType.Unicode);
    }

    public String toBuckwalter() {
        return toString(EncodingType.Buckwalter);
    }

    public String toSimpleEncoding() {
        return toString(EncodingType.Simple);
    }
}
