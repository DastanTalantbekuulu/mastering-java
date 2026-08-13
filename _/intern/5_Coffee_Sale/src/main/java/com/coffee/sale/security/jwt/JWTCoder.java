package com.coffee.sale.security.jwt;

import java.util.Base64;

public class JWTCoder {

    public static byte[] encode(byte[] data) {
        return Base64.getUrlEncoder().withoutPadding().encode(data);
    }

    public static byte[] decode(String data) {
        return Base64.getUrlDecoder().decode(data);
    }
}