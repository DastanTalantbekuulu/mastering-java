package org.jqurantree.arabic;

import java.util.Iterator;

import org.jqurantree.arabic.encoding.ArabicEncoder;
import org.jqurantree.arabic.encoding.EncodingFactory;
import org.jqurantree.arabic.encoding.EncodingOptions;
import org.jqurantree.arabic.encoding.EncodingType;
import org.jqurantree.arabic.encoding.buckwalter.BuckwalterDecoder;
import org.jqurantree.arabic.encoding.buckwalter.BuckwalterEncoder;
import org.jqurantree.arabic.encoding.simple.SimpleEncoder;
import org.jqurantree.arabic.encoding.unicode.UnicodeDecoder;
import org.jqurantree.arabic.encoding.unicode.UnicodeEncoder;

public class ArabicText implements Iterable<ArabicCharacter> {
    protected final byte[] buffer;
    protected final int offset;
    protected final int characterCount;

    ArabicText(byte[] buffer) {
        this(buffer, 0, buffer.length / ByteFormat.CHARACTER_WIDTH);
    }

    ArabicText(String text, EncodingType encodingType) {
        this(EncodingFactory.getDecoder(encodingType).decode(text));
    }

    protected ArabicText(byte[] buffer, int offset, int characterCount) {
        this.buffer = buffer;
        this.offset = offset;
        this.characterCount = characterCount;
    }

    public ArabicText(String text) {
        this(text, EncodingType.Unicode);
    }

    public static ArabicText fromUnicode(String text) {
        return fromEncoding(text, EncodingType.Unicode);
    }

    public static ArabicText fromBuckwalter(String text) {
        return fromEncoding(text, EncodingType.Buckwalter);
    }

    public static ArabicText fromEncoding(String text, EncodingType encodingType) {
        return new ArabicText(text, encodingType);
    }

    @Override
    public String toString() {
        return toString(EncodingType.Unicode, null);
    }

    public String toString(EncodingType encodingType) {
        return toString(encodingType, null);
    }

    public String toString(EncodingType encodingType, EncodingOptions options) {
        ArabicEncoder encoder = EncodingFactory.getEncoder(encodingType);
        return encoder.encode(buffer, offset, characterCount, options);
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

    public int getLength() {
        return characterCount;
    }

    public ArabicCharacter getCharacter(int index) {
        return new ArabicCharacter(buffer, offset + index * ByteFormat.CHARACTER_WIDTH);
    }

    public CharacterType getCharacterType(int index) {
        byte value = buffer[offset + index * ByteFormat.CHARACTER_WIDTH];
        return value != ByteFormat.WHITESPACE ? CharacterType.values[value] : null;
    }

    public Iterator<ArabicCharacter> iterator() {
        return new CharacterIterator(buffer, offset, characterCount);
    }

    public ArabicText getSubstring(int start, int end) {
        return new ArabicText(buffer, offset + start * ByteFormat.CHARACTER_WIDTH, end - start);
    }

    public ArabicText removeDiacritics() {

        byte[] buffer = new byte[characterCount * ByteFormat.CHARACTER_WIDTH];
        int offset1 = 0;
        int offset2 = offset;

        for (int i = 0; i < characterCount; i++) {
            buffer[offset1] = this.buffer[offset2];
            offset1 += ByteFormat.CHARACTER_WIDTH;
            offset2 += ByteFormat.CHARACTER_WIDTH;
        }
        return new ArabicText(buffer, 0, characterCount);
    }

    public int getLetterCount() {
        int count = 0;
        int offset = this.offset;
        for (int i = 0; i < characterCount; i++) {
            if (ByteFormat.isLetter(buffer, offset)) {
                count++;
            }
            offset += ByteFormat.CHARACTER_WIDTH;
        }
        return count;
    }

    public ArabicText removeNonLetters() {
        ArabicText text = this;
        int letterCount = getLetterCount();
        if (letterCount != characterCount) {

            byte[] buffer = new byte[letterCount * ByteFormat.CHARACTER_WIDTH];
            int offset1 = 0;
            int offset2 = offset;

            for (int i = 0; i < characterCount; i++) {
                if (ByteFormat.isLetter(this.buffer, offset2)) {
                    buffer[offset1] = this.buffer[offset2];
                    offset1 += ByteFormat.CHARACTER_WIDTH;
                }
                offset2 += ByteFormat.CHARACTER_WIDTH;
            }
            text = new ArabicText(buffer, 0, characterCount);
        }
        return text;
    }
}
