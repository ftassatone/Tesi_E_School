package com.eschool.e_school;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class MyCript {

    private String secretKey = "8d4fd5sf48s4f8s4d";
    Cipher cipher;
    SecretKeySpec keySpec;

    private SecretKeySpec generaChiave(){
        keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
        return keySpec;
    }

    public String encrypt(String string) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {
        Log.d("LOG", "sono in cifra");
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, generaChiave());
        byte[] stringBytes = string.getBytes("UTF-8");
        byte[] encryptedBytes = cipher.doFinal(stringBytes);
        return android.util.Base64.encodeToString(encryptedBytes, com.eschool.e_school.Base64.ENCODE);
    }

    public String decrypt(String string) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
        cipher.init(Cipher.DECRYPT_MODE, generaChiave());
        byte[] stringBytes = android.util.Base64.decode(string.getBytes(), com.eschool.e_school.Base64.DECODE);
        byte[] decryptedBytes = cipher.doFinal(stringBytes);
        return new String(decryptedBytes, "UTF-8");
    }


}