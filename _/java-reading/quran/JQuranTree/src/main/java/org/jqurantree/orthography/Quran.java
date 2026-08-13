package org.jqurantree.orthography;

import org.jqurantree.core.collections.ArrayIterator;
import org.jqurantree.tanzil.TanzilReader;

public class Quran {
    private static final String QURAN = "Quran";
    private static final Sura[] SURAS;
    static final int SURA_COUNT = 114;
    private static int ayaCount = 0;
    private static int tokenCount = 0;

    static {
        TanzilReader reader = new TanzilReader();
        SURAS = reader.readXml();
        new Quran();
    }

    private Quran() {
    }

    public static String getName() {
        return QURAN;
    }

    public static int getSuraCount() {
        return SURA_COUNT;
    }

    public static int getAyaCount() {
        if (ayaCount == 0) {
            int ayaCount = 0;
            for (Sura sura : SURAS) {
                ayaCount += sura.getAyaCount();
            }
            Quran.ayaCount = ayaCount;
        }
        return ayaCount;
    }

    public static int getTokenCount() {
        if (tokenCount == 0) {
            int tokenCount = 0;
            for (Sura sura : SURAS) {
                tokenCount += sura.getTokenCount();
            }
            Quran.tokenCount = tokenCount;
        }
        return tokenCount;
    }

    public static Sura getSura(int suraNumber) {
        Location.validateSuraNumber(suraNumber);
        return SURAS[suraNumber - 1];
    }

    public static Sura getSura(Location location) {
        return getSura(location.getSuraNumber());
    }

    public static Iterable<Sura> getSuras() {
        return new ArrayIterator<Sura>(SURAS);
    }

    public static Aya getAya(int suraNumber, int ayaNumber) {
        return getSura(suraNumber).getAya(ayaNumber);
    }

    public static Aya getAya(Location location) {
        return getAya(location.getSuraNumber(), location.getAyaNumber());
    }

    public static Iterable<Aya> getAyas() {
        return new QuranVerseIterator(SURAS);
    }

    public static Token getToken(int suraNumber, int ayaNumber, int tokenNumber) {
        return getSura(suraNumber).getAya(ayaNumber).getToken(tokenNumber);
    }

    public static Token getToken(Location location) {
        return getToken(location.getSuraNumber(), location.getAyaNumber(), location.getTokenNumber());
    }

    public static Iterable<Token> getTokens() {
        return new QuranTokenIterator(SURAS);
    }
}
