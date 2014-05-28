package com.example.groupdata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonMessage {
	public Messages getMess(String information) {
		try {
			Messages page = new Messages();
			JSONArray jsonArray = new JSONArray(information);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			page.setQq(jsonObject.getString("qq"));
			page.setWechat(jsonObject.getString("wechat"));
			page.setMobile(jsonObject.getString("mobile"));
			page.setEmail(jsonObject.getString("email"));
			return page;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
