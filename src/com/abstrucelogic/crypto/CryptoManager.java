package com.abstrucelogic.crypto;

import com.abstrucelogic.crypto.conf.CryptoConf;

public class CryptoManager {

	private static CryptoManager instance;
	
	private CryptoManager() {
	
	}

	public static CryptoManager getInstance() {
		if(CryptoManager.instance == null) {
			CryptoManager.instance = new CryptoManager();	
		}
		return CryptoManager.instance;
	}

	public void process(CryptoConf conf) {
		CryptoScheduler.getInstance().scheduleNewTask(conf);
	}
	
}
