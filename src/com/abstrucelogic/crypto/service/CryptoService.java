package com.abstrucelogic.crypto.service;

import android.app.Service;
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
	
	public static String EXTRA_PATH = "filepath";
	public static String EXTRA_OPP = "operation";

	private IBinder curBinder;
	private ServiceHandler curServiceHandler;

	public int onStartCommand(Intent intent, int flags, int startId) {
		android.util.Log.e("--------------------", "start service");
		Message msg = this.curServiceHandler.obtainMessage(); 
		msg.arg1 = startId;
		msg.arg2 = intent.getIntExtra(CryptoService.EXTRA_OPP, 3);
		msg.obj = intent.getStringExtra(CryptoService.EXTRA_PATH);
		this.curServiceHandler.sendMessage(msg);	
		return START_STICKY;
	}

	public void onCreate() {
		HandlerThread encThread = new HandlerThread("EncDecThread", Process.THREAD_PRIORITY_BACKGROUND);
		encThread.start();
		Looper encThreadLooper = encThread.getLooper();
		this.curServiceHandler = new ServiceHandler(encThreadLooper);
		//this.showNotification();
	}

	public IBinder onBind(Intent intent) {
		if(this.curBinder == null) {
			this.curBinder = new CryptoServiceBinder();
		}
		return this.curBinder;	
	}

	public void onDestroy() {
		/*NotificationManager notMan = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notMan.cancel(1);*/
	}

	/*private void showNotification() {
		Notification not = new Notification(R.drawable.padlock, "test", System.currentTimeMillis());	
		PendingIntent pIntent = PendingIntent.getActivity(this, 1, new Intent(this, HomeActivity.class), 0);	
		not.setLatestEventInfo(this, "test", "test", pIntent);
		NotificationManager notManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);	
		notManager.notify(1, not);
	}*/
	
	private class ServiceHandler extends Handler {
		
		public ServiceHandler(Looper l) {
			super(l);
		}

		public void handleMessage(Message msg) {
			int shouldEncrypt = msg.arg2;
			String filePath = (String) msg.obj;
			if(shouldEncrypt == 1) {
				encryptFile(filePath);
			} else if(shouldEncrypt == 0){
				decryptFile(filePath);
			}
			CryptoService.this.stopSelf(msg.arg1);
		}

		public void encryptFile(String filePath) {
			Toast.makeText(CryptoService.this, "Enc Started for - " + filePath, Toast.LENGTH_SHORT).show();
			//FileEncryptor.encryptFile(EncService.this, filePath);		
		}
		
		public void decryptFile(String filePath) {
			Toast.makeText(CryptoService.this, "Dec started for - " + filePath, Toast.LENGTH_SHORT).show();
			//FileDecryptor.decryptFile(EncService.this, filePath);	
		}
	
	}

	public class CryptoServiceBinder extends Binder {
		public CryptoService getService() {
			return CryptoService.this;
		}
	}		

}
