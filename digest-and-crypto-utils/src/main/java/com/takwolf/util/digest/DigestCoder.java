package com.takwolf.util.digest;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestCoder {

    private final MessageDigest md;

    public DigestCoder(String algorithm) {
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public byte[] getRawDigest(byte[] input) {
        return md.digest(input);
    }

    public byte[] getRawDigest(String input) {
        return getRawDigest(input.getBytes(Charset.forName("UTF-8")));
    }

    public String getMessageDigest(byte[] input) {
        byte[] buffer = getRawDigest(input);
        StringBuilder sb = new StringBuilder(buffer.length * 2);
        for (byte b : buffer) {
            sb.append(Character.forDigit((b >>> 4) & 15, 16));
            sb.append(Character.forDigit(b & 15, 16));
        }
        return sb.toString();
    }

    public String getMessageDigest(String input) {
        return getMessageDigest(input.getBytes(Charset.forName("UTF-8")));
    }

}
