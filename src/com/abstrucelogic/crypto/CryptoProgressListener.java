package com.abstrucelogic.crypto;

public interface CryptoProgressListener {
	
	public void cryptoProcessStarted();
	public void cryptoInProgress();
	public void cryptoProcessComplete();
	public void cryptoProcessError();

} 
