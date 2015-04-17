package com.takwolf.util.digest;

public class MD5 {

    private final static DigestCoder coder = new DigestCoder("MD5");

    public static byte[] getRawDigest(byte[] input) {
        return coder.getRawDigest(input);
    }

    public static byte[] getRawDigest(String input) {
        return coder.getRawDigest(input);
    }

    public static String getMessageDigest(byte[] input) {
        return coder.getMessageDigest(input);
    }

    public static String getMessageDigest(String input) {
        return coder.getMessageDigest(input);
    }

}
