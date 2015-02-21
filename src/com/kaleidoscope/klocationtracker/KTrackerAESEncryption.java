package com.kaleidoscope.klocationtracker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class KTrackerAESEncryption {
	
	KFileManager fileManager;
	public KTrackerAESEncryption() {
		fileManager=new KFileManager();
	}
	public String encryptLocationData(String locationData)
	{	
		fileManager.writeLocation("Encrypting data..");
		String encryptedMSG="";
		byte[] messageBytes=locationData.getBytes();
		//shared secret
		byte[] key = new byte[] {  0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
		String padding = "ISO10126Padding"; //"ISO10126Padding", "PKCS5Padding"
		fileManager.writeLocation("Key is :"+new String(KBase64Encoder.encode(key)));
		byte[] encryptedMessage = encryptMessage(key, padding, messageBytes);
		encryptedMSG=new String(KBase64Encoder.encode(encryptedMessage));
		return encryptedMSG;
	}
	public static byte[] encryptMessage(byte[] keyBytes, String sPadding, byte[] messageBytes){
        Cipher cipher = getAESECBEncryptor(keyBytes, sPadding);
        return encrypt(cipher, messageBytes);
	}
	public static Cipher getAESECBEncryptor(byte[] keyBytes, String padding){
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/ECB/"+padding);
			cipher.init(Cipher.ENCRYPT_MODE, key);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return cipher;
    }
	public static byte[] encrypt(Cipher cipher, byte[] dataBytes){
        ByteArrayInputStream bIn = new ByteArrayInputStream(dataBytes);
        CipherInputStream cIn = new CipherInputStream(bIn, cipher);
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        int ch;
        try {
			while ((ch = cIn.read()) >= 0) {
			  bOut.write(ch);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return bOut.toByteArray();
    }
}
