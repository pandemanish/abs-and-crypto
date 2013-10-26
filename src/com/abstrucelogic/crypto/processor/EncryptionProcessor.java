package com.abstrucelogic.crypto.processor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;

public class EncryptionProcessor {

	private static String key = "hoplesskey123456";
	
	public static void encryptFile(String inputFilePath, String outputFilePath, Cipher cipher) {
		FileInputStream inStream = null;
		BufferedInputStream buffInStream = null;
		FileOutputStream outStream = null;
		BufferedOutputStream buffOutStream = null;
		CipherOutputStream cipherOutStream = null;
		try {
			//input file
			File inputFile = new File(inputFilePath);	
			inStream = new FileInputStream(inputFile);
			buffInStream = new BufferedInputStream(inStream);				
			
			//output file
			File outputFile = new File(outputFilePath);
			if(outputFile.exists()) {
				//break the process and ask the user to rename the file before attempting encryption
			} else {
				outputFile.createNewFile();
			}
			outStream = new FileOutputStream(outputFile);	
			buffOutStream = new BufferedOutputStream(outStream);
		
			//encryption
			cipherOutStream = new CipherOutputStream(buffOutStream, cipher);	
			byte[] buff = new byte[10240];	
			int numOfBytesRead = 0;
			while(true) {
				numOfBytesRead = buffInStream.read(buff, 0, buff.length);
				if(numOfBytesRead != -1) {
					cipherOutStream.write(buff, 0, numOfBytesRead);
					cipherOutStream.flush();
				} else {
					break;
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				buffInStream.close();
				inStream.close();
				cipherOutStream.close();
				buffOutStream.close();
				outStream.close();
			} catch(Exception ex) {
				ex.printStackTrace();
			}	
		}
	}

	/*private static Cipher getEncCipher() {
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");	
			byte[] byteArr = "1234567891234567".getBytes("UTF-8");
			byte[] byteArrKey = EncryptionProcessor.key.getBytes("UTF-8");
			SecretKeySpec keySpec = new SecretKeySpec(byteArrKey, "AES/CBC/PKCS5Padding");
			IvParameterSpec spec = new IvParameterSpec(byteArr);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, spec); 	
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return cipher;
	}*/

	
}
