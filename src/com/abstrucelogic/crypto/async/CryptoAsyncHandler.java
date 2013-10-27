package com.abstrucelogic.crypto.async;

import com.abstrucelogic.crypto.CryptoHandler;
import com.abstrucelogic.crypto.CryptoScheduler;
import com.abstrucelogic.crypto.conf.CryptoConf;
import com.abstrucelogic.crypto.constants.CryptoProcessStatus;

public class CryptoAsyncHandler implements CryptoHandler {

	private CryptoConf mCurCryptoConf;
	
	public CryptoAsyncHandler(CryptoConf conf) {
		this.mCurCryptoConf = conf;
	}

	@Override
	public void processStatusUpdate(CryptoProcessStatus status, float progressPer) {
		switch(status) {
		case COMPLETE :
			this.mCurCryptoConf.getListener().cryptoProcessComplete();
			CryptoScheduler.getInstance().requestRemoveFromSchedulingMap(this.mCurCryptoConf);
			break;
		case ERROR :
			this.mCurCryptoConf.getListener().cryptoProcessError();
			CryptoScheduler.getInstance().requestRemoveFromSchedulingMap(this.mCurCryptoConf);
			break;
		case INPROGRESS : 
			this.mCurCryptoConf.getListener().cryptoInProgress(progressPer);
			break;
		case START:
			this.mCurCryptoConf.getListener().cryptoProcessStarted();
			break;
		}
	}


	public void exec() {
		new CryptoAsyncTask( this , mCurCryptoConf ).execute((Void)null);
	}



}
