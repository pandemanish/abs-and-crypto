package com.abstrucelogic.crypto;

public class CryptoManager {

	private static CryptoManager instance;
	
	private CryptoProgressListener mListener;	
	
	private CryptoManager() {
	
	}

	public static CryptoManager getInstance() {
		if(CryptoManager.instance == null) {
			CryptoManager.instance = new CryptoManager();	
		}
		return CryptoManager.instance;
	}

	public void encrypt() {	

	}

	public void decrypt() {
			
	}	

	public void setCryptoProgressListener(CryptoProgressListener listener) {
		this.mListener = listener;
	}

}
