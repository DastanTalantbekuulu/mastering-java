package org.jqurantree.orthography;

import org.jqurantree.arabic.ArabicText;
import org.jqurantree.arabic.ByteFormat;

public class Aya extends ArabicText {

    private final Location location;
    private final int[] tokenOffsets;

    public Aya(int suraNumber, int ayaNumber, String text) {
        super(text);
        location = new Location(suraNumber, ayaNumber);
        tokenOffsets = getTokenOffsets();
    }

    public Location getLocation() {
        return location;
    }

    public Sura getSura() {
        return Quran.getSura(location);
    }

    public int getSuraNumber() {
        return location.getSuraNumber();
    }

    public int getAyaNumber() {
        return location.getAyaNumber();
    }

    public Iterable<Token> getTokens() {
        return new AyaTokenIterator(this);
    }

    public Token getToken(Location location) {
        return getToken(location.getTokenNumber());
    }

    public Token getToken(int tokenNumber) {
        int index = tokenNumber - 1;
        int offset = index > 0 ? tokenOffsets[index - 1] : 0;
        return new Token(location.getSuraNumber(),
                location.getAyaNumber(), tokenNumber, buffer, offset,
                (tokenOffsets[index] - offset) / ByteFormat.CHARACTER_WIDTH - 1);
    }

    public int getTokenCount() {
        return tokenOffsets.length;
    }

    private int[] getTokenOffsets() {
        int size = 1;
        int offset = this.offset;
        for (int i = 0; i < characterCount; i++) {
            if (buffer[offset] == ByteFormat.WHITESPACE) {
                size++;
            }
            offset += ByteFormat.CHARACTER_WIDTH;
        }
        int[] tokenOffsets = new int[size];
        int index = 0;
        offset = this.offset;
        for (int i = 0; i < characterCount; i++) {
            if (buffer[offset] == ByteFormat.WHITESPACE) {
                tokenOffsets[index++] = offset + ByteFormat.CHARACTER_WIDTH;
            }
            offset += ByteFormat.CHARACTER_WIDTH;
        }
        tokenOffsets[size - 1] = offset + ByteFormat.CHARACTER_WIDTH;
        return tokenOffsets;
    }

    @Override
    public String toString() {
        return location + " " + toBuckwalter();
    }
}
