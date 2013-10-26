package com.abstrucelogic.crypto;

import com.abstrucelogic.crypto.processor.CryptoProcessStatusListener;

public interface CryptoHandler extends CryptoProcessStatusListener {

	public void exec();
	
}
