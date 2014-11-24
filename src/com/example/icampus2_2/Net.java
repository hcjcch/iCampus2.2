package com.example.icampus2_2;

import java.io.Serializable;

public class Net implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean net;
	boolean gprs;
	boolean wifi;

	public Net() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean isNet() {
		return net;
	}

	public void setNet(boolean net) {
		this.net = net;
	}

	public boolean isGprs() {
		return gprs;
	}

	public void setGprs(boolean gprs) {
		this.gprs = gprs;
	}

	public boolean isWifi() {
		return wifi;
	}

	public void setWifi(boolean wifi) {
		this.wifi = wifi;
	}

	@Override
	public String toString() {
		return "Net [net=" + net + ", gprs=" + gprs + ", wifi=" + wifi + "]";
	}
	
}