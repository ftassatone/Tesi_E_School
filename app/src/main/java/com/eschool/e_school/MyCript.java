package com.eschool.e_school;

import android.util.Log;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class MyCript {

    private static String secretKey = "1234567812345678";
    private static Cipher cipher;
    private static String iv = "fedcba9876543210";
    private static Key key = new SecretKeySpec(secretKey.getBytes(), "AES/128/CBC");
    private static byte[] encryptedData;
    private static byte[] decryptedData;
    private static String stringDecriptata;
    private static IvParameterSpec ivspec;

    public static byte[] encrypt(String string) {
        Log.d("LOG", "sono in cifra");
        try {
            ivspec = new IvParameterSpec(iv.getBytes());
            cipher = Cipher.getInstance("AES/128/CBC");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
            encryptedData = cipher.doFinal(string.getBytes());
        } catch (NoSuchAlgorithmException e) {
            Log.d("LOG", "sono nel primo catch");
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            Log.d("LOG", "sono nel secondo catch");
            e.printStackTrace();
        }
         catch (InvalidKeyException e) {
            Log.d("LOG", "sono nel terzo catch");
            e.printStackTrace();
        }
         catch (IllegalBlockSizeException e) {
            Log.d("LOG", "sono nel quarto catch");
            e.printStackTrace();
        } catch (BadPaddingException e) {
            Log.d("LOG", "sono nel quinto catch");
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        Log.d("LOG", "pswCifrata "+encryptedData);
        return encryptedData;
    }

    public static String decrypt(byte[] stringaCifrata){
        try {
            ivspec = new IvParameterSpec(iv.getBytes());
            cipher = Cipher.getInstance("AES/128/CBC");
            cipher.init(Cipher.DECRYPT_MODE, key, ivspec);
            decryptedData = cipher.doFinal(stringaCifrata);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
          catch (InvalidKeyException e) {
            e.printStackTrace();
        }
          catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        stringDecriptata = new String(decryptedData);
        Log.d("LOG", "pswDecifrata "+stringDecriptata);
        return stringDecriptata;
    }
}