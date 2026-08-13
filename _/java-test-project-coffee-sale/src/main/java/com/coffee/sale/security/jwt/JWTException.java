package com.coffee.sale.security.jwt;

public class JWTException extends RuntimeException {
    public JWTException(String failedToGenerateJwt) {
        super(failedToGenerateJwt);
    }

    public JWTException(String failedToGenerateJwt, Exception e) {
        super(failedToGenerateJwt, e);
    }
}
