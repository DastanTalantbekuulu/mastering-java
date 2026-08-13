package org.jqurantree.arabic;

public class ArabicTextBuilder {

    private static final int INITIAL_CAPACITY = 4;
    private byte[] buffer;
    private int characterCount;
    private int characterCapacity;

    public ArabicTextBuilder() {
        this(INITIAL_CAPACITY);
    }

    public ArabicTextBuilder(int characterCapacity) {
        this.characterCapacity = characterCapacity;
        buffer = new byte[characterCapacity * ByteFormat.CHARACTER_WIDTH];
    }

    public void add(CharacterType characterType) {
        if (characterCount >= characterCapacity) {
            int newCapacity = characterCapacity * 2;
            byte[] newBuffer = new byte[newCapacity
                    * ByteFormat.CHARACTER_WIDTH];
            System.arraycopy(buffer, 0, newBuffer, 0, characterCapacity
                    * ByteFormat.CHARACTER_WIDTH);
            buffer = newBuffer;
            characterCapacity = newCapacity;
        }

        int offset = characterCount * ByteFormat.CHARACTER_WIDTH;
        buffer[offset] = characterType != null ? (byte) characterType.ordinal()
                : ByteFormat.WHITESPACE;
        buffer[offset + 1] = 0;
        buffer[offset + 2] = 0;

        characterCount++;
    }

    public void add(DiacriticType diacriticType) {
        int offset = (characterCount - 1) * ByteFormat.CHARACTER_WIDTH;
        ByteFormat.setDiacritic(buffer, offset, diacriticType);
    }

    public void add(CharacterType characterType, DiacriticType... diaciritcTypes) {
        add(characterType);
        for (DiacriticType diacriticType : diaciritcTypes) {
            add(diacriticType);
        }
    }

    public void addWhitespace() {
        add((CharacterType) null);
    }

    @Override
    public String toString() {
        return toText().toString();
    }

    public ArabicText toText() {
        return new ArabicText(toByteArray());
    }

    public byte[] toByteArray() {

        byte[] buffer = this.buffer;
        int byteCount = characterCount * ByteFormat.CHARACTER_WIDTH;

        if (byteCount != buffer.length) {
            buffer = new byte[byteCount];
            System.arraycopy(this.buffer, 0, buffer, 0, byteCount);
        }
        return buffer;
    }
}
