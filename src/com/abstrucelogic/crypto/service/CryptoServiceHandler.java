package com.abstrucelogic.crypto.service;

import javax.crypto.Cipher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.abstrucelogic.crypto.CryptoHandler;
import com.abstrucelogic.crypto.conf.CryptoConf;
import com.abstrucelogic.crypto.constants.CryptoOperation;
import com.abstrucelogic.crypto.constants.CryptoProcessStatus;
import com.abstrucelogic.crypto.processor.DecryptionProcessor;
import com.abstrucelogic.crypto.processor.EncryptionProcessor;
import com.abstrucelogic.crypto.service.CryptoService.CryptoServiceBinder;

public class CryptoServiceHandler implements CryptoHandler {

	private CryptoService mCurServiceInstance;
	
	private CryptoConf mCurCryptoConf;
	private Context mCurContext;
	//private EncryptionProcessor mEncProcessor;
	//private DecryptionProcessor mDecProcessor;
	
	public CryptoServiceHandler(CryptoConf conf) {
		//this.mEncProcessor = new EncryptionProcessor();
		//this.mDecProcessor = new DecryptionProcessor();
		this.mCurCryptoConf = conf;
	}
	
	@Override
	public void processStatusUpdate(CryptoProcessStatus status, int progressPer) {
		
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
		//this.mEncProcessor.encryptFile(inputFilePath, outputFilePath, cipher);
	}
	
	public void decrypt(String inputFilePath, String outputFilePath, Cipher cipher) {
		//this.mDecProcessor.decryptFile(inputFilePath, outputFilePath, cipher);
	}
	
	private void startService(int operation, String filePath) {
		Intent serviceIntent = new Intent(mCurContext, CryptoService.class);
		serviceIntent.putExtra(CryptoService.EXTRA_OPP, operation);
		serviceIntent.putExtra(CryptoService.EXTRA_PATH, filePath);	
		this.mCurContext.startService(serviceIntent);
	}

	private ServiceConnection serviceConn = new ServiceConnection()	{
		public void onServiceConnected(ComponentName className, IBinder binder) {		
			CryptoServiceBinder curBinder = (CryptoServiceBinder) binder;
			CryptoServiceHandler.this.mCurServiceInstance = curBinder.getService();
		}
		
		public void onServiceDisconnected(ComponentName className) {
			//TODO - do cleanup required here
		}
	};	

	
}
