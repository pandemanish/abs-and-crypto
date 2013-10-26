package com.abstrucelogic.crypto;

import java.util.HashMap;

import android.content.Context;

import com.abstrucelogic.crypto.async.CryptoAsyncHandler;
import com.abstrucelogic.crypto.conf.CryptoConf;
import com.abstrucelogic.crypto.constants.CryptoProcessMode;
import com.abstrucelogic.crypto.processor.CryptoSyncHandler;
import com.abstrucelogic.crypto.service.CryptoServiceHandler;

public class CryptoScheduler {
	
	private static CryptoScheduler instance;
	
	private HashMap<String, CryptoHandler> mScheduledTasks;
	
	private CryptoScheduler() {
		this.mScheduledTasks = new HashMap<String, CryptoHandler>();
	}
	
	public static CryptoScheduler getInstance() {
		if(CryptoScheduler.instance == null) {
			CryptoScheduler.instance = new CryptoScheduler();
		}
		return CryptoScheduler.instance;
	}
	
	public void scheduleNewTask(CryptoConf conf, Context context) {
		//check if task being performed on same file path. If yes, abort else proceed
		if(this.mScheduledTasks.get(conf.getInputFilePath()) == null) {
			CryptoHandler handler = this.getCryptoHandlerForConf(conf, context);
			mScheduledTasks.put(conf.getInputFilePath(), handler);
			//add logic here to schedule a fixed number of tasks etc
			handler.exec();
		} else {
			android.util.Log.e("CryptoScheduler Error", "Crypto operation already running on filepath - " + conf.getInputFilePath());
		}
	}
	
	public void requestRemoveFromSchedulingMap(CryptoConf conf) {
		if(this.mScheduledTasks.get(conf.getInputFilePath()) != null) {
			//check process status
			//remove if process stopped
			this.mScheduledTasks.remove(conf.getInputFilePath());
		} else {
			android.util.Log.e("CryptoScheduler", "Error when removing task from scheduling - " + conf.getInputFilePath());
		}
	}
	
	private CryptoHandler getCryptoHandlerForConf(CryptoConf conf, Context context) {
		CryptoHandler handler = null;
		CryptoProcessMode cryptoProcessMode = conf.getProcessMode();
		switch(cryptoProcessMode) {
			case ASYNC : 
				handler = new CryptoAsyncHandler(conf);
				break;
			case SERVICE : 
				handler = new CryptoServiceHandler(conf, context);
				break;
			case SYNC :
				handler = new CryptoSyncHandler(conf);
				break;
		}
		return handler;
	}
	
}
