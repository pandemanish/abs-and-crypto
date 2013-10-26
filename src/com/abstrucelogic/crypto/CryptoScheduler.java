package com.abstrucelogic.crypto;

import java.util.HashMap;

import com.abstrucelogic.crypto.async.CryptoAsyncHandler;
import com.abstrucelogic.crypto.conf.CryptoConf;
import com.abstrucelogic.crypto.constants.CryptoProcessMode;
import com.abstrucelogic.crypto.processor.CryptoSyncHandler;
import com.abstrucelogic.crypto.service.CryptoServiceHandler;

public class CryptoScheduler {
	
	private static CryptoScheduler instance;
	
	private HashMap<String, CryptoHandler> mScheduledTasks;
	
	private CryptoScheduler() {
		
	}
	
	public static CryptoScheduler getInstance() {
		if(CryptoScheduler.instance == null) {
			CryptoScheduler.instance = new CryptoScheduler();
		}
		return CryptoScheduler.instance;
	}
	
	public void scheduleNewTask(CryptoConf conf) {
		//check if task being performed on same file path. If yes, abort else proceed
		if(this.mScheduledTasks.get(conf.getInputFilePath()) != null) {
			CryptoHandler handler = this.getCryptoHandlerForConf(conf);
			mScheduledTasks.put(conf.getInputFilePath(), handler);
			//add logic here to schedule a fixed number of tasks etc
			handler.exec();
		} else {
			android.util.Log.e("CryptoScheduler Error", "Crypto operation already running on filepath - " + conf.getInputFilePath());
		}
	}
	
	private CryptoHandler getCryptoHandlerForConf(CryptoConf conf) {
		CryptoHandler handler = null;
		CryptoProcessMode cryptoProcessMode = conf.getProcessMode();
		switch(cryptoProcessMode) {
			case ASYNC : 
				handler = new CryptoAsyncHandler(conf);
				break;
			case SERVICE : 
				handler = new CryptoServiceHandler(conf);
				break;
			case SYNC :
				handler = new CryptoSyncHandler(conf);
				break;
		}
		return handler;
	}
	
}
