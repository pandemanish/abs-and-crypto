package com.abstrucelogic.crypto;

public interface CryptoProgressListener {
	
	public void cryptoProcessStarted();
	public void cryptoInProgress(int prog);
	public void cryptoProcessComplete();
	public void cryptoProcessError();

} 
