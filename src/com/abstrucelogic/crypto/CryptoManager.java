package com.abstrucelogic.crypto;

public class CryptoManager {

	private static CryptoManager instance;
	
	private CryptoProgressListener mListener;	
	
	private CryptoManager() {
	
	}

	public CryptoManager getInstance() {
		if(CryptoManager.instance == null) {
			CryptoManager.instance = new CryptoManager();	
		}
		return CryptoManager.instance;
	}

	public void encrypt() {
		//initiate the encrypton	
	}

	public void decrypt() {
		//initizte the decryption	
	}	

	public void setCryptoProgressListener(CryptoProgressListener listener) {
		this.mListener = listener;
	}

}
