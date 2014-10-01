package de.adornis.snookercalculator;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by fightcookie on 9/30/2014.
 */
class TestService extends Service {

	private TestBinder testBinder = new TestBinder();

	@Override
	public IBinder onBind(Intent intent) {
		Log.e("Service", "onBind");
		return testBinder;
	}

	@Override
	public void onDestroy() {
		Log.e("Service", "onDestroy");

		super.onDestroy();
	}

	class TestBinder extends Binder {
		TestService getService() {
			return TestService.this;
		}
	}
}
