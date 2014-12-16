package com.lntu.online.util;

public class ShaUtil {

    private static DigestCoder coder = new DigestCoder("SHA-256");

    public final static String getDigest(String plainText) {
        return coder.getDigest(plainText);
    }

    public final static String getDigest(String plainText, boolean isUpperCase) {
        return coder.getDigest(plainText, isUpperCase);
    }

    public final static boolean compare(String plainText, String digest) {
        return coder.compare(plainText, digest);
    }

}
