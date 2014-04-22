package cn.edu.bistu.oauthsdk;

import java.util.HashMap;
import java.util.Map;


public class OAuth {
	public String access_token;
	public long expires_in;

	public String getToken() {
		return access_token;
	}
	 public static String authorizeURL(String response_type) throws Exception{
		Map<String,String> params=new HashMap<String, String>();
		params.put(Contants.MapParamName.CLIENT_ID, Config.getValue("client_id"));
		params.put(Contants.MapParamName.RESPONSE_TYPE,response_type);
		params.put(Contants.MapParamName.REDIRECT_URI,Config.getValue("redirect_uri"));
		params.put(Contants.MapParamName.SCOPE,Config.getValue("scope"));
		String requestUrl=URLUtil.createUrl(Config.getValue("authorizeURL"),params);
		System.out.println(requestUrl);
		return requestUrl;
	} 
	 
		 
}
