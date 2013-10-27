package com.abstrucelogic.crypto.service;

import java.util.HashMap;

import com.abstrucelogic.crypto.R;
import com.abstrucelogic.crypto.conf.CryptoConf;
import com.abstrucelogic.crypto.constants.CryptoOperation;
import com.abstrucelogic.crypto.processor.DecryptionProcessor;
import com.abstrucelogic.crypto.processor.EncryptionProcessor;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
import android.os.Process;

public class CryptoService extends Service {

	public static String EXTRA_IN_PATH = "inpath";
	private IBinder curBinder;
	private ServiceHandler curServiceHandler;
	private HashMap<String, CryptoConf> inProcessMap; 
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "Service onStart", Toast.LENGTH_SHORT).show();
		Message msg = this.curServiceHandler.obtainMessage(); 
		msg.arg1 = startId;
		msg.obj = intent.getStringExtra(EXTRA_IN_PATH);
		this.curServiceHandler.sendMessage(msg);	
		return START_STICKY;
	}

	public void onCreate() {
		HandlerThread encThread = new HandlerThread("EncDecThread", Process.THREAD_PRIORITY_BACKGROUND);
		encThread.start();
		Looper encThreadLooper = encThread.getLooper();
		this.curServiceHandler = new ServiceHandler(encThreadLooper);
		this.inProcessMap = new HashMap<String, CryptoConf>();
		this.showNotification();
	}

	public void updateInProcessMap(CryptoConf conf) {
		this.inProcessMap.put(conf.getInputFilePath(), conf);
	}
	
	public IBinder onBind(Intent intent) {
		if(this.curBinder == null) {
			this.curBinder = new CryptoServiceBinder();
		}
		return this.curBinder;	
	}

	public void onDestroy() {
		NotificationManager notMan = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notMan.cancel(1);
		Toast.makeText(this, "onDestroy service", Toast.LENGTH_SHORT).show();
	}

	private void showNotification() {
		Notification.Builder mBuilder = new Notification.Builder(this);
		mBuilder.setSmallIcon(R.drawable.loc);
		mBuilder.setContentTitle("Crypto Service");
		mBuilder.setContentText("Crypto operation in progress");
		mBuilder.setStyle(new Notification.BigTextStyle().bigText("Please be patient!"));
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(), 0);
		mBuilder.setContentIntent(contentIntent);
		NotificationManager notManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);	
		notManager.notify(1, mBuilder.build());
	}
	
	private class ServiceHandler extends Handler {
		
		public ServiceHandler(Looper l) {
			super(l);
		}

		public void handleMessage(Message msg) {
			String inPath = (String) msg.obj;
			CryptoConf conf = CryptoService.this.inProcessMap.get(inPath);
			CryptoOperation opp = conf.getOperation();
			EncryptionProcessor encProcessor = null;
			DecryptionProcessor decProcessor = null;
			switch(opp) {
				case ENCRYPTION :
					encProcessor = new EncryptionProcessor();
					encProcessor.encryptFile(conf.getInputFilePath(), conf.getOutputFilePath(), conf.getCipher());
					//encProcessor.setProgressListener(this);
					break;
				case DECRYPTION :
					decProcessor = new DecryptionProcessor();
					decProcessor.decryptFile(conf.getInputFilePath(), conf.getOutputFilePath(), conf.getCipher());
					//decProcessor.setProgressListener(this);
					break;
			}
			CryptoService.this.inProcessMap.remove(inPath);
			CryptoService.this.stopSelf(msg.arg1);
		}
	}

	public class CryptoServiceBinder extends Binder {
		public CryptoService getService() {
			return CryptoService.this;
		}
	}		

}
