package org.jqurantree.orthography;

import org.jqurantree.arabic.ArabicText;

public class Token extends ArabicText {

    private final Location location;

    Token(
            int suraNumber,
            int ayaNumber,
            int tokenNumber,
            byte[] buffer,
            int offset,
            int characterCount
    ) {
        super(buffer, offset, characterCount);
        location = new Location(suraNumber, ayaNumber, tokenNumber);
    }

    public Location getLocation() {
        return location;
    }

    public int getSuraNumber() {
        return location.getSuraNumber();
    }

    public int getAyaNumber() {
        return location.getAyaNumber();
    }

    public int getTokenNumber() {
        return location.getTokenNumber();
    }

    public Aya getAya() {
        return Quran.getAya(location);
    }

    public Sura getSura() {
        return Quran.getSura(location);
    }
}
