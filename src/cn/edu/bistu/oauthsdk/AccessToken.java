package cn.edu.bistu.oauthsdk;


public class AccessToken {
	public AccessToken(String accessToken, long expireIn) {
		 		this.accessToken = accessToken;
		this.expireIn = expireIn;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public long getExpireIn() {
		return expireIn;
	}
	public void setExpireIn(long expireIn) {
		this.expireIn = expireIn;
	}
	private String accessToken;
	private long expireIn;
	public AccessToken getAT(String res) {
		org.json.JSONObject json = new org.json.JSONObject();
		try {
			accessToken = json.getString("access_token");
			expireIn = (long)json.getDouble("expires_in");
		} catch (Exception e) {
		}
		 
		return null;
	}
 
   
}