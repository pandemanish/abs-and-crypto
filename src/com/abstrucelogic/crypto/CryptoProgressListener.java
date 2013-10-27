package com.abstrucelogic.crypto;

public interface CryptoProgressListener {
	
	public void cryptoProcessStarted();
	public void cryptoInProgress(float prog);
	public void cryptoProcessComplete();
	public void cryptoProcessError();

} 
