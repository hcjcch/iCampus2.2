package cn.edu.bistu.oauthsdk;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * @author lizy
 *
 */
public class OauthUtil {
	
	public static void saveOauth(SharedPreferences prefs,String accessToken){
		if(accessToken!=null&&accessToken.trim()!=""){
			try {
				Editor editor=prefs.edit();
					String access_token= accessToken;
					editor.putString(Contants.ResponseParamName.ACCESS_TOKEN, access_token);
				editor.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
 
	public static String getAccessToken(SharedPreferences prefs){
		return prefs.getString(Contants.ResponseParamName.ACCESS_TOKEN, "");
	}
	
	
}
