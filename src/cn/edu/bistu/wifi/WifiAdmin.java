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

public class WifiAdmin {// �����������磬ԭַ�Ҳ����ˡ���
	private WifiManager mWifiManager; // ����WifiManager����

	private WifiInfo mWifiInfo; // ����WifiInfo����

	private List<ScanResult> mWifiList; // ɨ��������������б�

	private List<WifiConfiguration> mWifiConfiguration; // ���������б�

	WifiLock mWifiLock; // ����һ��WifiLock

	private Handler handler;

	public WifiAdmin(Context context, Handler handler) {
		mWifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE); // ȡ��WifiManager����
		this.handler = handler;
	}

	public WifiInfo getWifiInfo() {
		return mWifiManager.getConnectionInfo();
	}

	// ��WIFI
	public boolean openWifi() {
		boolean isOpen = true;
		if (!mWifiManager.isWifiEnabled()) {
			isOpen = mWifiManager.setWifiEnabled(true);
		}
		return isOpen;
	}

	// �ر�WIFI
	public void closeWifi() {
		if (mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(false);
		}
	}

	/*
	 * �Ͽ���ǰ���ӵ�����
	 */
	public void disconnectWifi() {
		int netId = getNetworkId();
		mWifiManager.disableNetwork(netId);
		mWifiManager.disconnect();
		mWifiInfo = null;
	}

	// ����WifiLock�������ش��ļ�ʱ��Ҫ����
	public void acquireWifiLock() {
		mWifiLock.acquire();
	}

	public void releaseWifiLock() {// ����WifiLock
		// �ж�ʱ������
		if (mWifiLock.isHeld()) {
			mWifiLock.acquire();
		}
	}

	public void creatWifiLock() {// ����һ��WifiLock
		mWifiLock = mWifiManager.createWifiLock("BISTU");
	}

	public List<WifiConfiguration> getConfiguration() { // �õ����úõ�����
		mWifiConfiguration = mWifiManager.getConfiguredNetworks();// �õ����úõ���������
		return mWifiConfiguration;
	}

	public void connectConfiguration(int index) {// ָ�����úõ������������
		// �����������úõ�������������
		if (index > mWifiConfiguration.size()) {
			return;
		}
		// �������úõ�ָ��ID������
		mWifiManager.enableNetwork(mWifiConfiguration.get(index).networkId,
				true);
	}

	public void startScan() {
		mWifiManager.startScan(); // �õ�ɨ����
		mWifiList = mWifiManager.getScanResults();
	}

	public List<ScanResult> getWifiList() {// �õ������б�
		return mWifiList;
	}

	@SuppressLint("UseValueOf")
	public StringBuilder lookUpScan() {// �鿴ɨ����
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < mWifiList.size(); i++) {
			stringBuilder
					.append("Index_" + new Integer(i + 1).toString() + ":");
			stringBuilder.append((mWifiList.get(i)).toString());
			stringBuilder.append("\n");
		}
		return stringBuilder;
	}

	// �õ�MAC��ַ
	public String getMacAddress() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
	}

	// �õ�������SSID
	public String getSSID() {
		return (mWifiInfo == null) ? "null" : mWifiInfo.getSSID();
	}

	// �õ�������BSSID
	public String getBSSID() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
	}

	// ���SSID �Ƿ�����
	public boolean getDetailedStateOf() {
		return (mWifiInfo == null) ? false : mWifiInfo.getHiddenSSID();
	}

	// ��ȡip��ַ
	public int getIpAddress() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
	}

	// �õ�IP��ַ
	public int getIPAddress() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
	}

	// �õ����ӵ�ID
	public int getNetworkId() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
	}

	// �õ�WifiInfo��������Ϣ��
	public String getWifiInfotoString() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
	}

	// ���һ�����粢����
	public void addNetwork(WifiConfiguration wcg) {
		int wcgID = mWifiManager.addNetwork(wcg);
		mWifiManager.enableNetwork(wcgID, true);
	}

	/*
	 * 0 �������ڹر� 1 �����Ѿ��ر� 2 �������ڴ� 3 �����Ѿ���
	 */
	public int checkNetCardState() {
		return mWifiManager.getWifiState();
	}

	// �Ͽ�ָ��ID������
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