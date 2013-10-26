package com.abstrucelogic.crypto.conf;

import javax.crypto.Cipher;

import com.abstrucelogic.crypto.CryptoProgressListener;
import com.abstrucelogic.crypto.constants.CryptoOperation;
import com.abstrucelogic.crypto.constants.CryptoProcessMode;

public class CryptoConf {

	private CryptoOperation mOperation;
	private CryptoProcessMode mProcessMode;
	private CryptoProgressListener mListener;
	private String mInputFilePath;
	private String mOutputFilePath;
	private Cipher mCipher;
	
	public Cipher getCipher() {
		return mCipher;
	}

	public void setCipher(Cipher cipher) {
		this.mCipher = cipher;
	}

	public String getOutputFilePath() {
		return mOutputFilePath;
	}

	public void setOutputFilePath(String mOutputFilePath) {
		this.mOutputFilePath = mOutputFilePath;
	}

	public CryptoProgressListener getListener() {
		return mListener;
	}

	public void setListener(CryptoProgressListener mListener) {
		this.mListener = mListener;
	}

	public CryptoProcessMode getProcessMode() {
		return mProcessMode;
	}

	public void setProcessMode(CryptoProcessMode processMode) {
		this.mProcessMode = processMode;
	}
	
	public CryptoOperation getOperation() {
		return mOperation;
	}

	public void setOperation(CryptoOperation mOperation) {
		this.mOperation = mOperation;
	}
	
	public String getInputFilePath() {
		return this.mInputFilePath;
	}
	
	public void setInputFilePath(String inputFilePath) {
		this.mInputFilePath = inputFilePath;
	}
	
}
