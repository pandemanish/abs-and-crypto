package com.abstrucelogic.crypto.processor;

import com.abstrucelogic.crypto.constants.CryptoProcessStatus;

public interface CryptoProcessStatusListener {
	
	public void processStatusUpdate(CryptoProcessStatus status, float progressPer);
	
}
