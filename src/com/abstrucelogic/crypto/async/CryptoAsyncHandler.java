package com.abstrucelogic.crypto.async;

import javax.crypto.Cipher;

import com.abstrucelogic.crypto.CryptoHandler;
import com.abstrucelogic.crypto.CryptoScheduler;
import com.abstrucelogic.crypto.conf.CryptoConf;
import com.abstrucelogic.crypto.constants.CryptoOperation;
import com.abstrucelogic.crypto.constants.CryptoProcessStatus;

public class CryptoAsyncHandler implements CryptoHandler {

	private CryptoConf mCurCryptoConf;
	
	public CryptoAsyncHandler(CryptoConf conf) {
		this.mCurCryptoConf = conf;
	}

	@Override
	public void processStatusUpdate(CryptoProcessStatus status, int progressPer) {
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
			this.mCurCryptoConf.getListener().cryptoInProgress();
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

	public void encrypt(final String inputFilePath, final String outputFilePath, final Cipher cipher) {
		new CryptoAsyncTask(this){
			protected void methodCall() {
				getmEncProcessor().encryptFile(inputFilePath, outputFilePath, cipher);
			};
		}.execute((Void)null);

	}

	public void decrypt(final String inputFilePath, final String outputFilePath, final Cipher cipher) {
		new CryptoAsyncTask(this){
			protected void methodCall() {
				getmDecProcessor().decryptFile(inputFilePath, outputFilePath, cipher);
			};
		}.execute((Void)null);

	}


}
