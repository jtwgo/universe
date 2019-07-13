package com.jtw.main.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Locale;

public class PasswordUtils {

    private static final int KEY_SIZE = 256 ;
    private static final int DECIMAL_2 = 2;
    private static final int DECIMAL_16 = 16;
    private static final int HEX_255 = 255;

    private static final String AESKEY ="0576CD7EBFEF6D34713D771C2867182A1C3899E0E9E2BAFF3262C7F7CD3F1F6A";
    private PasswordUtils(){}
    public static String decryptByAES256(String content,String aeskey)
    {
        KeyGenerator kgen = null;
        String decyptResult = null;
        try {
            kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(aeskey.getBytes());
            kgen.init(KEY_SIZE, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(parseHexStr2Byte(content));
            decyptResult = new String(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return decyptResult;
    }

    public static String decryptByAES256(String content)
    {
        KeyGenerator kgen = null;
        String decyptResult = null;
        try {
            kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(AESKEY.getBytes());
            kgen.init(KEY_SIZE, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(parseHexStr2Byte(content));
            decyptResult = new String(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return decyptResult;
    }

    public static String encryptByAES256(String content,String aeskey)
    {
        KeyGenerator kgen = null;
        String encyptResult = null;
        try {
            kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(aeskey.getBytes());
            kgen.init(KEY_SIZE, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 初始化
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] byteContent = content.getBytes("utf-8");
            byte[] result = cipher.doFinal(byteContent);
            encyptResult = parseByte2HexStr(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encyptResult;
    }

    public static String encryptByAES256(String content)
    {
        KeyGenerator kgen = null;
        String encyptResult = null;
        try {
            kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(AESKEY.getBytes());
            kgen.init(KEY_SIZE, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 初始化
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] byteContent = content.getBytes("utf-8");
            byte[] result = cipher.doFinal(byteContent);
            encyptResult = parseByte2HexStr(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encyptResult;
    }

    private static byte[] parseHexStr2Byte(String hexStr)
    {
        if(hexStr.length()<1)
        {
            return new byte[0];
        }
        byte[] result = new byte[hexStr.length()/DECIMAL_2];
        for (int i = 0; i < hexStr.length()/DECIMAL_2; i++)
        {
            int high = Integer.parseInt(hexStr.substring(i*DECIMAL_2,i*DECIMAL_2+1),DECIMAL_16);
            int low = Integer.parseInt(hexStr.substring(i*DECIMAL_2+1,i*DECIMAL_2+DECIMAL_2),DECIMAL_16);
            result[i] = (byte)(high*DECIMAL_16+low);
        }
        return result;
    }
    private static String parseByte2HexStr(byte[] buff)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buff.length; i++) {
            String hex = Integer.toHexString(buff[i]&HEX_255);
            if(hex.length()==1)
            {
                hex = '0'+hex;
            }
            sb.append(hex.toUpperCase(Locale.US));

        }
        return sb.toString();
    }

}
