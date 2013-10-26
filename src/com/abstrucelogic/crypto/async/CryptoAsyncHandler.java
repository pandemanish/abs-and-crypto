package com.abstrucelogic.crypto.async;

import com.abstrucelogic.crypto.CryptoHandler;
import com.abstrucelogic.crypto.conf.CryptoConf;
import com.abstrucelogic.crypto.constants.CryptoProcessStatus;
import com.abstrucelogic.crypto.processor.DecryptionProcessor;
import com.abstrucelogic.crypto.processor.EncryptionProcessor;

public class CryptoAsyncHandler implements CryptoHandler {

	private CryptoConf mCurCryptoConf;
	private EncryptionProcessor mEncProcessor;
	private DecryptionProcessor mDecProcessor;
	
	public CryptoAsyncHandler(CryptoConf conf) {
		this.mEncProcessor = new EncryptionProcessor();
		this.mDecProcessor = new DecryptionProcessor();
		this.mCurCryptoConf = conf;
	}
	
	@Override
	public void processStatusUpdate(CryptoProcessStatus status, int progressPer) {
		
	}
	
	public void exec() {
		
	}
	
}
