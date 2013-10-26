package com.abstrucelogic.crypto.processor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;

public class DecryptionProcessor {

	private String key = "hoplesskey123456";
	
	public void decryptFile(String inputFilePath, String outputFilePath, Cipher cipher) {
		FileInputStream inStream = null;
		CipherInputStream cipherInStream = null;
		BufferedInputStream bufInStream = null;
		FileOutputStream outStream = null;
		BufferedOutputStream bufOutStream = null;

		try {
			//input
			File inFile = new File(inputFilePath);	
			inStream = new FileInputStream(inFile);
			bufInStream = new BufferedInputStream(inStream);
			cipherInStream = new CipherInputStream(bufInStream, cipher);
			
			//output
			File outFile = new File(outputFilePath);	
			outStream = new FileOutputStream(outFile);
			bufOutStream = new BufferedOutputStream(outStream);			

			//decrypt and write to file	
			byte[] buff = new byte[10240];	
			int numOfBytesRead = 0;
			while(true) {
				numOfBytesRead = cipherInStream.read(buff, 0, buff.length);
				if(numOfBytesRead != -1) {
					bufOutStream.write(buff, 0, numOfBytesRead);
					bufOutStream.flush();
				} else {
					break;
				}
			}	
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				cipherInStream.close();
				bufInStream.close();
				inStream.close();
				bufOutStream.close();
				outStream.close();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/*private static Cipher getDecCipher() {
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");	
			byte[] byteArrKey = FileDecryptor.key.getBytes("UTF-8");
			byte[] byteArr = "1234567891234567".getBytes("UTF-8");
			SecretKeySpec keySpec = new SecretKeySpec(byteArrKey, "AES/CBC/PKCS5Padding");
			IvParameterSpec spec = new IvParameterSpec(byteArr);
			cipher.init(Cipher.DECRYPT_MODE, keySpec, spec); 	
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return cipher;

	}*/
	
}
