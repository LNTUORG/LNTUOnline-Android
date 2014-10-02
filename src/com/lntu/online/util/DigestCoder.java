package com.lntu.online.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class DigestCoder {

    private final String algorithm;
    private final Charset charset;

    /**
     * 构造函数
     * @param algorithm 摘要算法
     * 可选值为：MD2,MD5,SHA-1,SHA-256,SHA-384,SHA-512
     * @param charsetName 编码类型
     */
    public DigestCoder(String algorithm, String charsetName) {
        if (algorithm == null || charsetName == null) {
            throw new IllegalArgumentException("Parameters could not be null.");
        }
        try {
            MessageDigest.getInstance(algorithm);
            this.algorithm = algorithm;
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Unsupported algorithm.");
        }
        try {
            charset = Charset.forName(charsetName);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unsupported charset.");
        }
    }

    /**
     * 构造函数
     * @param alogrithm 摘要算法
     * 可选值为：MD2,MD5,SHA-1,SHA-256,SHA-384,SHA-512
     */
    public DigestCoder(String alogrithm) {
        this(alogrithm, "UTF-8");
    }

    /**
     * 获取摘要特征
     * @param plainText 明文
     * @return 摘要特征
     */
    public String getDigest(String plainText) {
        if (plainText == null) {
            throw new IllegalArgumentException("Parameter 'plainText' could not be null.");
        }
        try {
            byte[] buffer = MessageDigest.getInstance(algorithm).digest(plainText.getBytes(charset));
            StringBuilder sb = new StringBuilder(buffer.length * 2);
            for (byte b : buffer) {
                sb.append(Character.forDigit((b >>> 4) & 15, 16));
                sb.append(Character.forDigit(b & 15, 16));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Get digest instance failed.");
        }
    }

    /**
     * 获取摘要特征
     * @param plainText 明文
     * @param isUpperCase 是否为大写
     * @return 摘要特征
     */
    public String getDigest(String plainText, boolean isUpperCase) {
        String digest = getDigest(plainText);
        if (isUpperCase) {
            return digest.toUpperCase(Locale.US);
        } else {
            return digest.toLowerCase(Locale.US);
        }
    }

    /**
     * 比较摘要特征
     * @param plainText 明文
     * @param digest 摘要特征
     * @return 是否相同
     */
    public boolean compare(String plainText, String digest) {
        if (plainText == null || digest == null) {
            return false;
        } else {
            return getDigest(plainText).equalsIgnoreCase(digest);
        }
    }

}
