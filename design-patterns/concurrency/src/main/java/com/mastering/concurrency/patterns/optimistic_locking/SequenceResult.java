package com.mastering.concurrency.patterns.optimistic_locking;

import java.math.BigInteger;
import java.util.List;

public record SequenceResult(int workerId, List<BigInteger> sequence) {}

