package org.jqurantree.orthography;

import java.util.Iterator;

import org.jqurantree.core.collections.ImmutableIteratorBase;

class AyaTokenIterator extends ImmutableIteratorBase<Token> implements Iterable<Token> {

    private final Aya aya;
    private int tokenNumber = 1;

    public AyaTokenIterator(Aya aya) {
        this.aya = aya;
    }

    public boolean hasNext() {
        return tokenNumber <= aya.getTokenCount();
    }

    public Token next() {
        return aya.getToken(tokenNumber++);
    }

    public Iterator<Token> iterator() {
        return this;
    }
}
