package org.jqurantree.orthography;

import java.util.Iterator;

import org.jqurantree.core.collections.ImmutableIteratorBase;

class QuranTokenIterator extends ImmutableIteratorBase<Token> implements
        Iterable<Token> {

    private Sura[] suras;
    private Aya[] ayas;
    private Aya aya;
    private int suraNumber;
    private int ayaNumber;
    private int tokenNumber;

    public QuranTokenIterator(Sura[] suras) {
        this.suras = suras;
    }

    public Iterator<Token> iterator() {
        return this;
    }

    public boolean hasNext() {
        return suraNumber < Quran.SURA_COUNT
                || ayaNumber < ayas.length
                || tokenNumber < aya.getTokenCount();
    }

    public Token next() {
        if (aya == null || ++tokenNumber > aya.getTokenCount()) {
            if (ayas == null || ++ayaNumber > ayas.length) {
                ayas = suras[++suraNumber - 1].ayas;
                ayaNumber = 1;
            }
            aya = ayas[ayaNumber - 1];
            tokenNumber = 1;
        }
        return aya.getToken(tokenNumber);
    }
}
