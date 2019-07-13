package com.jtw.main.test.cipherTest;

import sun.misc.BASE64Encoder;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AESTest
{
    private static SecretKey secretKey = null;
    static {
        try {
            secretKey = genKeyAES();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static SecretKey genKeyAES() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        SecretKey key = keyGen.generateKey();
        return key;
    }

    public static byte[] encryptAES(byte[] source, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE,key);
        byte[] bytes = cipher.doFinal(source);
        return bytes;
    }

    public static byte[] decryptAES(byte[] source, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE,key);
        byte[] bytes = cipher.doFinal(source);
        return bytes;
    }

    public static void main(String[] args) throws Exception
    {
        byte[] source = "tianwen@123.3".getBytes();
        System.out.println(secretKey);
        byte[] encryptAES = encryptAES(source, secretKey);
        System.out.println(encodeBase64(encryptAES));
        byte[] decryptAES = decryptAES(encryptAES, secretKey);
        System.out.println(new String(decryptAES));
    }

    public static String encodeBase64(byte[] source)
    {

        BASE64Encoder base64Encoder = new BASE64Encoder();
        String encode = base64Encoder.encode(source);
        return encode;
    }
}
