package com.example.icampus2_2;

public class UpdateType {
	private String appName;
	private String apkName;
	private String verName;
	private String apkUrl;
	private int verCode;

	public UpdateType(String appName, String apkName, String verName,
			String apkUrl, int verCode) {
		super();
		this.appName = appName;
		this.apkName = apkName;
		this.verName = verName;
		this.apkUrl = apkUrl;
		this.verCode = verCode;
	}

	public UpdateType() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "UpdateType [appName=" + appName + ", apkName=" + apkName
				+ ", verName=" + verName + ", apkUrl=" + apkUrl + ", verCode="
				+ verCode + "]";
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getApkName() {
		return apkName;
	}

	public void setApkName(String apkName) {
		this.apkName = apkName;
	}

	public String getVerName() {
		return verName;
	}

	public void setVerName(String verName) {
		this.verName = verName;
	}

	public String getApkUrl() {
		return apkUrl;
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

	public int getVerCode() {
		return verCode;
	}

	public void setVerCode(int verCode) {
		this.verCode = verCode;
	}

}
