package org.jqurantree.orthography;

import java.util.Iterator;

import org.jqurantree.arabic.ArabicText;
import org.jqurantree.core.collections.ArrayIterator;
import org.jqurantree.core.error.Errors;
import org.jqurantree.core.error.QuranException;

public class Sura implements Iterable<Aya> {

    private final Location location;
    private final ArabicText name;
    private final ArabicText bismillah;
    final Aya[] ayas;
    private int tokenCount;

    public Sura(int suraNumber, ArabicText name, ArabicText bismillah,
                Aya[] ayas) {
        location = new Location(suraNumber);
        this.name = name;
        this.ayas = ayas;
        this.bismillah = bismillah;
    }

    public int getSuraNumber() {
        return location.getSuraNumber();
    }

    public Location getLocation() {
        return location;
    }

    public ArabicText getName() {
        return name;
    }

    public ArabicText getBismillah() {
        return bismillah;
    }

    public Aya getAya(int ayaNumber) {
        if (ayaNumber < 1 || ayaNumber > ayas.length) {
            throw new QuranException(Errors.INVALID_VERSE_NUMBER);
        }
        return ayas[ayaNumber - 1];
    }

    public Aya getAya(Location location) {
        return getAya(location.getAyaNumber());
    }

    public int getAyaCount() {
        return ayas.length;
    }

    public int getTokenCount() {
        if (tokenCount == 0) {
            int tokenCount = 0;
            for (Aya aya : ayas) {
                tokenCount += aya.getTokenCount();
            }
            this.tokenCount = tokenCount;
        }
        return tokenCount;
    }

    public Iterator<Aya> iterator() {
        return new ArrayIterator<Aya>(ayas);
    }

    @Override
    public String toString() {
        return "Sura " + location.getSuraNumber();
    }
}
