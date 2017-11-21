package com.eschool.e_school;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class MyCript {

    private static String secretKey = "8d4fd5sf48s4f8s4d";
    private static Cipher cipher;
    private static Key key = new SecretKeySpec(secretKey.getBytes(), "AES");
    private static byte[] encryptedData;
    private static byte[] decryptedData;
    private static String stringDecriptata;

    public static byte[] encrypt(String string){
        try {
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        try {
            encryptedData = cipher.doFinal(string.getBytes());
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return encryptedData;
    }

    public static String decript(byte[] stringaCifrata){
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        try {
            decryptedData = cipher.doFinal(stringaCifrata);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        stringDecriptata = new String(decryptedData);
        return stringDecriptata;
    }





}