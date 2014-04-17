package org.apache.cordova.sharedpreferences;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.util.Base64;
import android.util.Log;

public class SimpleCrypto {
	
	static String TAG = "SimpleCrypto";

	// encrypt 
    public static String encrypt(String cleartext) throws Exception {
        byte[] result = encrypt(cleartext.getBytes());
        return Base64.encodeToString(result, Base64.DEFAULT);        
    }
    
    // decrypt
    public static String decrypt(String encrypted) throws Exception {
        byte[] enc = Base64.decode(encrypted, Base64.DEFAULT);
        byte[] result = decrypt(enc);
        return new String(result);
    }

    // ecnrypt using AES
    private static byte[] encrypt(byte[] clear) throws Exception {
    	KeyGenerator kg = KeyGenerator.getInstance("AES");    	
    	KeyStore ks = KeyStore.getInstance();
    	SecretKey key = kg.generateKey();
    	
    	SecretKeySpec skeySpec = null;
    	
    	boolean success = ks.put("AlpineMetrics", key.getEncoded());
    	
    	// check if operation succeeded and get error code if not
    	if (!success) {
    	   int errorCode = ks.getLastError();
    	   skeySpec = new SecretKeySpec(getRawKey("Bu2GJNnAax".getBytes()), "AES");
    	   // throw new RuntimeException("Keystore error: " + errorCode); 
    	} else {
    		skeySpec = new SecretKeySpec(key.getEncoded(), "AES");
    	}
        
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    // decrypt using AES
    private static byte[] decrypt(byte[] encrypted) throws Exception {
		KeyStore ks = KeyStore.getInstance();
    	byte[] keyBytes = ks.get("AlpineMetrics");
    	
        SecretKeySpec skeySpec = null;
        
        if(keyBytes == null) {
        	
        } else {
        	skeySpec = new SecretKeySpec(keyBytes, "AES");
        }
        
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted); 
        return decrypted;
    }        
    
    // generate key
    private static byte[] getRawKey(byte[] seed) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA");
        digest.update(seed);
        SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");            
        return key.getEncoded(); 
    }
}
