package com.abstrucelogic.crypto;

import android.content.Context;

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

	public void process(CryptoConf conf, Context context) {
		CryptoScheduler.getInstance().scheduleNewTask(conf, context);
	}
	
}
