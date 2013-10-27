package com.abstrucelogic.crypto.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.abstrucelogic.crypto.CryptoHandler;
import com.abstrucelogic.crypto.conf.CryptoConf;
import com.abstrucelogic.crypto.constants.CryptoProcessStatus;
import com.abstrucelogic.crypto.service.CryptoService.CryptoServiceBinder;

public class CryptoServiceHandler implements CryptoHandler {

	private CryptoService mCurServiceInstance;
	
	private CryptoConf mCurCryptoConf;
	private Context mCurContext;
	private boolean wasExecCalledWhenServiceNotReady = false;
	
	public CryptoServiceHandler(CryptoConf conf, Context context) {
		this.mCurCryptoConf = conf;
		this.mCurContext = context;
		Intent serviceIntent = new Intent(mCurContext, CryptoService.class);
		context.bindService(serviceIntent, serviceConn, Context.BIND_AUTO_CREATE);
	}
	
	@Override
	public void processStatusUpdate(CryptoProcessStatus status, float progressPer) {
		
	}
	
	public void exec() {
		if(this.mCurServiceInstance != null) {
			this.mCurServiceInstance.updateInProcessMap(this.mCurCryptoConf);
			startService();
		} else {
			wasExecCalledWhenServiceNotReady  = true;
		}
	}
	
	private void startService() {
		Intent serviceIntent = new Intent(mCurContext, CryptoService.class);
		serviceIntent.putExtra(CryptoService.EXTRA_IN_PATH, this.mCurCryptoConf.getInputFilePath());
		this.mCurContext.startService(serviceIntent);
	}

	private ServiceConnection serviceConn = new ServiceConnection()	{
		public void onServiceConnected(ComponentName className, IBinder binder) {		
			CryptoServiceBinder curBinder = (CryptoServiceBinder) binder;
			CryptoServiceHandler.this.mCurServiceInstance = curBinder.getService();
			if(wasExecCalledWhenServiceNotReady) {
				exec();
				wasExecCalledWhenServiceNotReady = false;
			}
		}
		
		public void onServiceDisconnected(ComponentName className) {
			
		}
	};	

	
}
