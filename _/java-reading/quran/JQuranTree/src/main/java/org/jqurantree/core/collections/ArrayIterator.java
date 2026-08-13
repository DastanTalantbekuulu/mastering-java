package org.jqurantree.core.collections;

import java.util.Iterator;

public class ArrayIterator<T> extends ImmutableIteratorBase<T> implements Iterable<T> {

    private final T[] items;
    private int index;

    public ArrayIterator(T[] items) {
        this.items = items;
    }

    public Iterator<T> iterator() {
        return this;
    }

    public boolean hasNext() {
        return index < items.length;
    }

    public T next() {
        return items[index++];
    }
}
