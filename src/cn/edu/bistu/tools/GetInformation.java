package cn.edu.bistu.tools;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;
import cn.edu.bistu.oauthsdk.Config;
import cn.edu.bistu.oauthsdk.HttpRequestGetTask;
import cn.edu.bistu.oauthsdk.OauthUtil;
import cn.edu.bistu.oauthsdk.OpenBistuProvider;
import cn.edu.bistu.oauthsdk.OpenConsumer;

public class GetInformation {
	public static void get(Intent data, Context context) {
		String res = data.getStringExtra("result");
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		if (res != null && !res.equals("")) {
			String[] mes = res.split("&"); // 将取回来的值进行解析，按照&来分开
			for (String info : mes) { // 遍历，取出每个字段对应的值
				if (info.contains("access_token")) {
					info = info.substring(info.indexOf("=") + 1, info.length());
					OauthUtil.saveOauth(prefs, info);// 将access_token写入SharedPreference
				}
				if (info.contains("token_type")) {
					info = info.substring(info.indexOf("=") + 1, info.length());

				}
				if (info.contains("expires_in")) {
					info = info.substring(info.indexOf("=") + 1, info.length());
					ACache aCache = ACache.get(context);
					aCache.put("expires_in", info);
				}
			}
			String appId = Config.getValue("client_id");
			String appSecret = Config.getValue("client_secret");
			OpenConsumer openConsumer = new OpenConsumer(appId, appSecret);
			Map<String, String> params = new HashMap<String, String>();
			OpenBistuProvider provider = new OpenBistuProvider(
					"https://222.249.250.89:8443/m/userinfo.htm", openConsumer,
					context);
			new HttpRequestGetTask(provider, params, context).execute();
		} else {
			Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show();
		}
	}
}
