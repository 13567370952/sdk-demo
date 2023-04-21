package com.thng.sdk.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//AES加密和解密（CBC模式）
public class ThngAESUtils {
    //密钥
//    private static final String key = "ceLBsABoF9dKIFQd";
    public static final String key =  PropertiesUtils.getProperty("aes_key");
    public static final String initVector =  PropertiesUtils.getProperty("aes_iv");
    //偏移量
//    private static final String initVector = "kkyVHZsZx7rtKygL";
    //加密
    private String k = "";
    private String i = "";

    public ThngAESUtils(String key, String iv){
        this.k = key;
        this.i = iv;
    }

    public static String encrypt(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(add(value.getBytes()));
            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            System.out.println("encrypt error ===> " + ex);
            return null;
        }
    }

    public static byte[] add(byte[] bytes){
        int length = 0;
        if (bytes.length % 16 != 0){
            length = 16 - (bytes.length % 16);
        }
        byte[] newBytes  = new byte[bytes.length + length];
        for(int i=0; i< bytes.length + length ; i++) {
            if  (i< bytes.length){
                newBytes[i] = bytes[i];
            }else {
                newBytes[i] = 0;
            }
        }
        return  newBytes;
    }

    public String encryptByKI(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(this.i.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(this.k.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(add(value.getBytes()));
            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            System.out.println("encrypt error ===> " + ex);
            return null;
        }
    }

    public String decryptByKI(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(this.i.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(this.k.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/NOPADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
            return new String(original).trim();
        } catch (Exception ex) {
            System.out.println("decrypt error ===> " + ex);
            return null;
        }
    }


    public static String decrypt(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/NOPADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
            return new String(original).trim();
        } catch (Exception ex) {
            System.out.println("decrypt error ===> " + ex);
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println("Encrypted String - " +  ThngAESUtils.encrypt("THNG_TEXT_AAAAAA"));
        System.out.println("After decryption - " + ThngAESUtils.decrypt("6kGODRJ7I4DONt1Ao5WQqg=="));
        ThngAESUtils hngAESUtils = new ThngAESUtils("ceLBsABoF9dKIFQd","kkyVHZsZx7rtKygL");
        System.out.println("Encrypted String - " +  hngAESUtils.encryptByKI("THNG_TEXT_AAAAAA"));
        System.out.println("After decryption - " + hngAESUtils.decryptByKI("6kGODRJ7I4DONt1Ao5WQqg=="));
    }
}