package cn.edu.bistu.wifi;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.os.Handler;
import android.os.Message;

public class WifiAdmin {// 代码来自网络，原址找不到了……
	private WifiManager mWifiManager; // 定义WifiManager对象

	private WifiInfo mWifiInfo; // 定义WifiInfo对象

	private List<ScanResult> mWifiList; // 扫描出的网络连接列表

	private List<WifiConfiguration> mWifiConfiguration; // 网络连接列表

	WifiLock mWifiLock; // 定义一个WifiLock

	private Handler handler;

	public WifiAdmin(Context context, Handler handler) {
		mWifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE); // 取得WifiManager对象
		this.handler = handler;
	}

	public WifiInfo getWifiInfo() {
		return mWifiManager.getConnectionInfo();
	}

	// 打开WIFI
	public boolean openWifi() {
		boolean isOpen = true;
		if (!mWifiManager.isWifiEnabled()) {
			isOpen = mWifiManager.setWifiEnabled(true);
		}
		return isOpen;
	}

	// 关闭WIFI
	public void closeWifi() {
		if (mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(false);
		}
	}

	/*
	 * 断开当前连接的网络
	 */
	public void disconnectWifi() {
		int netId = getNetworkId();
		mWifiManager.disableNetwork(netId);
		mWifiManager.disconnect();
		mWifiInfo = null;
	}

	// 锁定WifiLock，当下载大文件时需要锁定
	public void acquireWifiLock() {
		mWifiLock.acquire();
	}

	public void releaseWifiLock() {// 解锁WifiLock
		// 判断时候锁定
		if (mWifiLock.isHeld()) {
			mWifiLock.acquire();
		}
	}

	public void creatWifiLock() {// 创建一个WifiLock
		mWifiLock = mWifiManager.createWifiLock("BISTU");
	}

	public List<WifiConfiguration> getConfiguration() { // 得到配置好的网络
		mWifiConfiguration = mWifiManager.getConfiguredNetworks();// 得到配置好的网络连接
		return mWifiConfiguration;
	}

	public void connectConfiguration(int index) {// 指定配置好的网络进行连接
		// 索引大于配置好的网络索引返回
		if (index > mWifiConfiguration.size()) {
			return;
		}
		// 连接配置好的指定ID的网络
		mWifiManager.enableNetwork(mWifiConfiguration.get(index).networkId,
				true);
	}

	public void startScan() {
		mWifiManager.startScan(); // 得到扫描结果
		mWifiList = mWifiManager.getScanResults();
	}

	public List<ScanResult> getWifiList() {// 得到网络列表
		return mWifiList;
	}

	@SuppressLint("UseValueOf")
	public StringBuilder lookUpScan() {// 查看扫描结果
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < mWifiList.size(); i++) {
			stringBuilder
					.append("Index_" + new Integer(i + 1).toString() + ":");
			stringBuilder.append((mWifiList.get(i)).toString());
			stringBuilder.append("\n");
		}
		return stringBuilder;
	}

	// 得到MAC地址
	public String getMacAddress() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
	}

	// 得到接入点的SSID
	public String getSSID() {
		return (mWifiInfo == null) ? "null" : mWifiInfo.getSSID();
	}

	// 得到接入点的BSSID
	public String getBSSID() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
	}

	// 获得SSID 是否被隐藏
	public boolean getDetailedStateOf() {
		return (mWifiInfo == null) ? false : mWifiInfo.getHiddenSSID();
	}

	// 获取ip地址
	public int getIpAddress() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
	}

	// 得到IP地址
	public int getIPAddress() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
	}

	// 得到连接的ID
	public int getNetworkId() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
	}

	// 得到WifiInfo的所有信息包
	public String getWifiInfotoString() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
	}

	// 添加一个网络并连接
	public void addNetwork(WifiConfiguration wcg) {
		int wcgID = mWifiManager.addNetwork(wcg);
		mWifiManager.enableNetwork(wcgID, true);
	}

	/*
	 * 0 网卡正在关闭 1 网卡已经关闭 2 网卡正在打开 3 网卡已经打开
	 */
	public int checkNetCardState() {
		return mWifiManager.getWifiState();
	}

	// 断开指定ID的网络
	public void disconnectWifi(int netId) {
		mWifiManager.disableNetwork(netId);
		mWifiManager.disconnect();
	}

	public boolean removeWifi(WifiConfiguration configuration) {
		return mWifiManager.removeNetwork(configuration.networkId);
	}

	public void connect() {
		(new MyThread()).start();
	}

	public WifiConfiguration isExsites(String SSID) {
		List<WifiConfiguration> existingConfigs = getConfiguration();
		for (WifiConfiguration wifiConfiguration : existingConfigs) {
			if (wifiConfiguration.SSID.equals("\"" + SSID + "\"")) {
				return wifiConfiguration;
			}
		}
		return null;
	}

	class MyThread extends Thread {
		@Override
		public void run() {

			System.out.println("!!!!!!");

			mWifiInfo = getWifiInfo();
			if (mWifiInfo != null && mWifiInfo.getSSID() != null
					&& mWifiInfo.getSSID().equals("BISTU")) {
				Message msg = new Message();
				msg.what = -1;
				handler.sendMessage(msg);
				return;
			}
			System.out.println("@@@@@@");
			if (mWifiManager.getWifiState() == 1) {
				Message msg = new Message();
				msg.what = 0;
				handler.sendMessage(msg);
			}
			System.out.println("#######");
			if (!openWifi()) {
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
				return;
			}
			System.out.println("$$$$$$$$$$");
			while (mWifiManager.getWifiState() == mWifiManager.WIFI_STATE_ENABLING) {
				try {
					Thread.currentThread();
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			while (mWifiList == null) {
				try {
					startScan();
					Thread.currentThread();
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			/*if (!mWifiList.toString().contains("BISTU")) {
				Message msg = new Message();
				msg.what = 7;
				handler.sendMessage(msg);
				return;
			}*/
			Message msg1 = new Message();
			msg1.what = 2;
			handler.sendMessage(msg1);

			WifiConfiguration formerConfiguration = isExsites("BISTU");
			if (formerConfiguration == null) {
				Message msg = new Message();
				msg.what = 3;
				handler.sendMessage(msg);
				return;
			}

			System.out.println("^^^^^^^^^^^^^^");
			// int netId = mWifiManager.addNetwork(formerConfiguration);
			boolean bRet = mWifiManager.enableNetwork(
					formerConfiguration.networkId, true);
			System.out.println(bRet);
			if (bRet == true) {
				try {
					while (mWifiInfo.getSSID() == null
							|| (!mWifiInfo.getSSID().equals("BISTU") && !mWifiInfo
									.getSSID().equals("\"BISTU\""))) {
						try {
							mWifiInfo = getWifiInfo();
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				System.out.println(getWifiInfo().getSSID());
				Message msg = new Message();
				msg.what = 5;
				handler.sendMessage(msg);
			} else {
				Message msg = new Message();
				msg.what = 6;
				handler.sendMessage(msg);
			}
		}
	}

	public void logOut() {
		(new LogoutThread()).start();
	}

	class LogoutThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			mWifiInfo = getWifiInfo();
			if (mWifiInfo.getSSID() == null || mWifiInfo.getSSID().equals("")) {
				Message message = new Message();
				message.what = 0;
				handler.sendMessage(message);
				return;
			}
			if (mWifiInfo.getSSID().equals("BISTU")
					|| mWifiInfo.getSSID().equals("\"BISTU\"")) {
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
				return;
			} else {
				Message message = new Message();
				message.what = 2;
				message.obj = mWifiInfo.getSSID();
				handler.sendMessage(message);
			}
			super.run();
		}
	}
}