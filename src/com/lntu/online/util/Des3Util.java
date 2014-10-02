package com.lntu.online.util;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class Des3Util {

    private final static String IV = "01234567";
    private final static String CHARSET = "utf-8";

    /**
     * ����
     * @param iv ��������
     * @param secretKey ��Կ
     * @param plainText ԭ��
     * @return ����
     * @throws Exception
     */
    public static String encode(String iv, String secretKey, String plainText) throws Exception {
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        Key deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] encryptData = cipher.doFinal(plainText.getBytes(CHARSET));
        return Base64.encode(encryptData);
    }

    /**
     * ���ܲ�ʹ��Ĭ������
     * @param secretKey ��Կ
     * @param plainText ԭ��
     * @return ����
     * @throws Exception
     */
    public static String encode(String secretKey, String plainText) throws Exception {
    	return encode(IV, secretKey, plainText);
    }

    /**
     * ����
     * @param iv ����
     * @param secretKey ��Կ
     * @param encryptText ����
     * @return ԭ��
     * @throws Exception
     */
    public static String decode(String iv, String secretKey, String encryptText) throws Exception {
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        Key deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));
        return new String(decryptData, CHARSET);
    }
    
    /**
     * ���ܲ�ʹ��Ĭ������
     * @param secretKey ��Կ
     * @param encryptText ����
     * @return ԭ��
     * @throws Exception
     */
    public static String decode(String secretKey, String encryptText) throws Exception {
    	return decode(IV, secretKey, encryptText);
    }

}
