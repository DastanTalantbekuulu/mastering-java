package org.jqurantree.orthography;

import java.util.Iterator;

import org.jqurantree.core.collections.ImmutableIteratorBase;

class QuranVerseIterator extends ImmutableIteratorBase<Aya> implements
        Iterable<Aya> {

    private final Sura[] suras;
    private Aya[] ayas;
    private int suraNumber;
    private int ayaNumber;

    public QuranVerseIterator(Sura[] suras) {
        this.suras = suras;
    }

    public Iterator<Aya> iterator() {
        return this;
    }

    public boolean hasNext() {
        return suraNumber < Quran.SURA_COUNT
                || ayaNumber < ayas.length;
    }

    public Aya next() {
        if (ayas == null || ++ayaNumber > ayas.length) {
            ayas = suras[++suraNumber - 1].ayas;
            ayaNumber = 1;
        }
        return ayas[ayaNumber - 1];
    }
}
