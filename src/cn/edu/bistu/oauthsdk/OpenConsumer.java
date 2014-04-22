package cn.edu.bistu.oauthsdk;

import java.io.Serializable;

/**
 * 
 * @author LIZY
 *
 */
public class OpenConsumer implements Serializable{

	private static final long serialVersionUID = -32130307612913810L;
	public final static String CONSUMER="consumer";
	public final static String BACK_ACTIVITY="back_actity";
	private String appId;
	private String appSecret;
	
	public OpenConsumer(String appId,String appSecret){
		this.appId=appId;
		this.appSecret=appSecret;
	}
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	
}
