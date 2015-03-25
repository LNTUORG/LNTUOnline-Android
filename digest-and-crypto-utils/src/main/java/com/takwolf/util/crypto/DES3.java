package com.takwolf.util.crypto;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.takwolf.util.coder.Base64;

public class DES3 {

    private final static String IV = "01234567";
    private final static String CHARSET = "utf-8";

    /**
     * 加密
     * @param iv 加密向量
     * @param secretKey 密钥
     * @param plainText 原文
     * @return 密文
     * @throws Exception
     */
    public static String encrypt(String iv, String secretKey, String plainText) throws Exception {
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        Key deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] encryptData = cipher.doFinal(plainText.getBytes(CHARSET));
        return Base64.encodeFromBytes(encryptData);
    }

    /**
     * 加密并使用默认向量
     * @param secretKey 密钥
     * @param plainText 原文
     * @return 密文
     * @throws Exception
     */
    public static String encrypt(String secretKey, String plainText) throws Exception {
        return encrypt(IV, secretKey, plainText);
    }

    /**
     * 解密
     * @param iv 向量
     * @param secretKey 密钥
     * @param encryptText 密文
     * @return 原文
     * @throws Exception
     */
    public static String decrypt(String iv, String secretKey, String encryptText) throws Exception {
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        Key deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        byte[] decryptData = cipher.doFinal(Base64.decodeToBytes(encryptText));
        return new String(decryptData, CHARSET);
    }

    /**
     * 解密并使用默认向量
     * @param secretKey 密钥
     * @param encryptText 密文
     * @return 原文
     * @throws Exception
     */
    public static String decrypt(String secretKey, String encryptText) throws Exception {
        return decrypt(IV, secretKey, encryptText);
    }

}
