package com.mastering.csv;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class Delimiter {

    @Getter
    private final char value;
    private final String toString;

    private Delimiter(char value) {
        this.value = value;
        this.toString = String.valueOf(value);
    }

    private static final Map<Character, Delimiter> MAP = new HashMap<>();

    public static Delimiter get(char c) {
        return MAP.computeIfAbsent(c, k -> new Delimiter(c));
    }

    @Override
    public String toString() {
        return toString;
    }
}