package com.takwolf.util.digest;

public class SHA512 {

    private static final DigestCoder coder = new DigestCoder("SHA-512");

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
