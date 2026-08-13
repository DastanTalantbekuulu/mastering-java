package com.mastering.concurrency.patterns.optimistic_locking;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SequenceGenerator {
    private final BigInteger multiplier;
    private final AtomicReference<BigInteger> element;

    public SequenceGenerator() {
        multiplier = BigInteger.valueOf(2);
        element = new AtomicReference<>(BigInteger.ONE);
    }

    public BigInteger next() {
        BigInteger value;
        BigInteger next;
        do {
            value = element.get();
            next = value.multiply(multiplier);
        } while (!element.compareAndSet(value, next));
        return value;
    }
}
