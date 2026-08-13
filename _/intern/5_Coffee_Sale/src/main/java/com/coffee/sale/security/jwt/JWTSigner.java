package com.coffee.sale.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class JWTSigner {

    private final Mac hmac;

    public JWTSigner(String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        hmac.init(secretKeySpec);
    }

    public boolean verify(byte[] data, byte[] expectedSignature) {
        byte[] actualSignature = sign(data);
        return Arrays.equals(actualSignature, expectedSignature);
    }

    public byte[] sign(byte[] data) {
        return hmac.doFinal(data);
    }
}