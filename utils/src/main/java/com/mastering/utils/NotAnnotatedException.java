package com.mastering.utils;

public class NotAnnotatedException extends RuntimeException {
    public NotAnnotatedException(String s, IllegalAccessException e) {
        super(s, e);
    }

    public NotAnnotatedException(String s) {
        super(s);
    }
}
