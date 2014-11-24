package com.example.icampus2_2;

import com.baidu.frontia.Frontia;
import com.baidu.frontia.api.FrontiaStatistics;
import com.baidu.mobstat.SendStrategyEnum;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;

public class Splash extends Activity {
	private Net net;
	private FrontiaStatistics stat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		boolean isInit = Frontia.init(getApplicationContext(), "your_app_key");
		if (isInit) {
			stat = Frontia.getStatistics();
			stat.setReportId("nMplgyOm7bh4TyUFX9QcftTL");
			stat.setSessionTimeout(20);
			stat.enableExceptionLog();
			stat.start(SendStrategyEnum.SET_TIME_INTERVAL, 10, 10, true);
		}
		setContentView(R.layout.splash);
		net = new Net();
		(new SplashAsy()).execute();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		stat.pageviewStart(this, "Splash");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		stat.pageviewEnd(this, null);
	}

	private void checkNetState() {
		boolean flag = false;
		// �õ�����������Ϣ
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		// ȥ�����ж������Ƿ�����
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

	public static boolean isWiFiActive(Context inContext) {
		WifiManager mWifiManager = (WifiManager) inContext
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
		int ipAddress = wifiInfo == null ? 0 : wifiInfo.getIpAddress();
		if (mWifiManager.isWifiEnabled() && ipAddress != 0) {
			System.out.println("**** WIFI is on");
			return true;
		} else {
			System.out.println("**** WIFI is off");
			return false;
		}
	}
}
