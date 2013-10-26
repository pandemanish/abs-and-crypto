package com.abstrucelogic.crypto.processor;

import javax.crypto.Cipher;

import com.abstrucelogic.crypto.CryptoHandler;
import com.abstrucelogic.crypto.conf.CryptoConf;
import com.abstrucelogic.crypto.constants.CryptoOperation;

public class CryptoSyncHandler implements CryptoHandler {

	private CryptoConf mCurCryptoConf;
	private EncryptionProcessor mEncProcessor;
	private DecryptionProcessor mDecProcessor;
	
	public CryptoSyncHandler(CryptoConf conf) {
		this.mEncProcessor = new EncryptionProcessor();
		this.mDecProcessor = new DecryptionProcessor();
		this.mCurCryptoConf = conf;
	}
	
	public void exec() {
		CryptoOperation opp = this.mCurCryptoConf.getOperation();
		switch(opp) {
			case ENCRYPTION:
				this.encrypt(this.mCurCryptoConf.getInputFilePath(), this.mCurCryptoConf.getOutputFilePath(), this.mCurCryptoConf.getCipher());
				break;
			case DECRYPTION:
				this.decrypt(this.mCurCryptoConf.getInputFilePath(), this.mCurCryptoConf.getOutputFilePath(), this.mCurCryptoConf.getCipher());
				break;
		}
	}
	
	public void encrypt(String inputFilePath, String outputFilePath, Cipher cipher) {
		this.mEncProcessor.encryptFile(inputFilePath, outputFilePath, cipher);
	}
	
	public void decrypt(String inputFilePath, String outputFilePath, Cipher cipher) {
		this.mDecProcessor.decryptFile(inputFilePath, outputFilePath, cipher);
	}
	
}
