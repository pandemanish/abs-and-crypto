package com.abstrucelogic.crypto.service;

import com.abstrucelogic.crypto.CryptoHandler;
import com.abstrucelogic.crypto.conf.CryptoConf;
import com.abstrucelogic.crypto.processor.DecryptionProcessor;
import com.abstrucelogic.crypto.processor.EncryptionProcessor;

public class CryptoServiceHandler implements CryptoHandler {

	private CryptoConf mCurCryptoConf;
	private EncryptionProcessor mEncProcessor;
	private DecryptionProcessor mDecProcessor;
	
	public CryptoServiceHandler(CryptoConf conf) {
		this.mEncProcessor = new EncryptionProcessor();
		this.mDecProcessor = new DecryptionProcessor();
		this.mCurCryptoConf = conf;
	}
	
	public void exec() {
		
	}
	
}
