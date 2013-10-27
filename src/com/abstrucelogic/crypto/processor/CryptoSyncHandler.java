package com.abstrucelogic.crypto.processor;

import javax.crypto.Cipher;

import com.abstrucelogic.crypto.CryptoHandler;
import com.abstrucelogic.crypto.CryptoScheduler;
import com.abstrucelogic.crypto.conf.CryptoConf;
import com.abstrucelogic.crypto.constants.CryptoOperation;
import com.abstrucelogic.crypto.constants.CryptoProcessStatus;

public class CryptoSyncHandler implements CryptoHandler {

	private CryptoConf mCurCryptoConf;
	private EncryptionProcessor mEncProcessor;
	private DecryptionProcessor mDecProcessor;
	
	public CryptoSyncHandler(CryptoConf conf) {
		this.mEncProcessor = new EncryptionProcessor();
		this.mEncProcessor.setProgressListener(this);
		this.mDecProcessor = new DecryptionProcessor();
		this.mDecProcessor.setProgressListener(this);
		this.mCurCryptoConf = conf;
	}
	
	@Override
	public void processStatusUpdate(CryptoProcessStatus status, float progressPer) {
		switch(status) {
			case COMPLETE :
				this.mCurCryptoConf.getListener().cryptoProcessComplete();
				CryptoScheduler.getInstance().requestRemoveFromSchedulingMap(this.mCurCryptoConf);
				break;
			case ERROR :
				this.mCurCryptoConf.getListener().cryptoProcessError();
				CryptoScheduler.getInstance().requestRemoveFromSchedulingMap(this.mCurCryptoConf);
				break;
			case INPROGRESS : 
				this.mCurCryptoConf.getListener().cryptoInProgress(progressPer);
				break;
			case START:
				this.mCurCryptoConf.getListener().cryptoProcessStarted();
				break;
		}
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
