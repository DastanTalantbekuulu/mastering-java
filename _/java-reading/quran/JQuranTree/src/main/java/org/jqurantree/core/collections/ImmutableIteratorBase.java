package org.jqurantree.core.collections;

import java.util.Iterator;

import org.jqurantree.core.error.Errors;

public abstract class ImmutableIteratorBase<T> implements Iterator<T> {

    public abstract boolean hasNext();

    public abstract T next();

    public void remove() {
        throw new UnsupportedOperationException(Errors.IMMUTABLE_COLLECTION);
    }
}
