package com.abstrucelogic.crypto.async;

import com.abstrucelogic.crypto.conf.CryptoConf;
import com.abstrucelogic.crypto.constants.CryptoOperation;
import com.abstrucelogic.crypto.constants.CryptoProcessStatus;
import com.abstrucelogic.crypto.processor.CryptoProcessStatusListener;
import com.abstrucelogic.crypto.processor.DecryptionProcessor;
import com.abstrucelogic.crypto.processor.EncryptionProcessor;

import android.os.AsyncTask;

public class CryptoAsyncTask extends AsyncTask<Void, Integer, Boolean> implements CryptoProcessStatusListener{
	private EncryptionProcessor mEncProcessor;
	private DecryptionProcessor mDecProcessor;
	private CryptoProcessStatusListener cryptoProcessStatusListener;
	private CryptoConf mCurCryptoConf;

	public CryptoAsyncTask(CryptoProcessStatusListener cryptoProcessStatusListener,CryptoConf mCurCryptoConf){
		this.mEncProcessor = new EncryptionProcessor();
		this.mEncProcessor.setProgressListener(this);
		this.mDecProcessor = new DecryptionProcessor();
		this.mDecProcessor.setProgressListener(this);
		this.cryptoProcessStatusListener = cryptoProcessStatusListener;
		this.mCurCryptoConf =mCurCryptoConf;
	}
	public EncryptionProcessor getmEncProcessor() {
		return mEncProcessor;
	}
	public DecryptionProcessor getmDecProcessor() {
		return mDecProcessor;
	}
	@Override
	protected Boolean doInBackground(Void... params) {
		if(mCurCryptoConf.getOperation()==CryptoOperation.DECRYPTION){
			mDecProcessor.decryptFile(this.mCurCryptoConf.getInputFilePath(), this.mCurCryptoConf.getOutputFilePath(), this.mCurCryptoConf.getCipher());
		}else{
			mEncProcessor.encryptFile(this.mCurCryptoConf.getInputFilePath(), this.mCurCryptoConf.getOutputFilePath(), this.mCurCryptoConf.getCipher());
		}
		return null;
	}
	@Override
	public void processStatusUpdate(CryptoProcessStatus status, float progressPer) {
		cryptoProcessStatusListener.processStatusUpdate(status, progressPer);
	}
}
