package com.example.icampus2_2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;

public class Splash extends Activity {
	private Net net;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		net = new Net();
		(new SplashAsy()).execute();
	}

	private void checkNetState() {
		boolean flag = false;
		// 得到网络连接信息
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		// 去进行判断网络是否连接
		if (manager.getActiveNetworkInfo() != null) {
			flag = manager.getActiveNetworkInfo().isAvailable();
		}
		net.setNet(flag);
		if (flag) {
			State gprs = manager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
			State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
					.getState();
			if (gprs == State.CONNECTED || wifi == State.CONNECTING) {
				net.setGprs(true);
			} else {
				net.setGprs(false);
			}
			if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
				net.setWifi(true);
			} else {
				net.setWifi(false);
			}
		} else {
			
		}
	}

	class SplashAsy extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			checkNetState();
			Intent intent = new Intent();
			intent.putExtra("net", net);
			intent.setClass(Splash.this, ICampus.class);
			startActivity(intent);
			Splash.this.finish();
		}
	}
}
