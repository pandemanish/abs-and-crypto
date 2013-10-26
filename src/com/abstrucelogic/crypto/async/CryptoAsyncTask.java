package com.abstrucelogic.crypto.async;

import com.abstrucelogic.crypto.constants.CryptoProcessStatus;
import com.abstrucelogic.crypto.processor.CryptoProcessStatusListener;
import com.abstrucelogic.crypto.processor.DecryptionProcessor;
import com.abstrucelogic.crypto.processor.EncryptionProcessor;

import android.os.AsyncTask;

public class CryptoAsyncTask extends AsyncTask<Void, Integer, Boolean> implements CryptoProcessStatusListener{
	private EncryptionProcessor mEncProcessor;
	private DecryptionProcessor mDecProcessor;
	private CryptoProcessStatusListener cryptoProcessStatusListener;

	public CryptoAsyncTask(CryptoProcessStatusListener cryptoProcessStatusListener){
		this.mEncProcessor = new EncryptionProcessor();
		this.mEncProcessor.setProgressListener(this);
		this.mDecProcessor = new DecryptionProcessor();
		this.mDecProcessor.setProgressListener(this);
		this.cryptoProcessStatusListener = cryptoProcessStatusListener;
	}
	public EncryptionProcessor getmEncProcessor() {
		return mEncProcessor;
	}
	public DecryptionProcessor getmDecProcessor() {
		return mDecProcessor;
	}
	@Override
	protected Boolean doInBackground(Void... params) {
		methodCall();
		return null;
	}
	
	protected void methodCall(){
		//respective method would be called
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}
	@Override
	public void processStatusUpdate(CryptoProcessStatus status, int progressPer) {
		cryptoProcessStatusListener.processStatusUpdate(status, progressPer);
	}


}
