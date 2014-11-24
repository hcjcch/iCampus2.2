package com.example.groupdata;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;

public class JsonGroup extends Activity {
	public List<GroupClass> getList(String information) {
		try {
			List<GroupClass> pages = new ArrayList<GroupClass>();
			JSONArray jsonArray = new JSONArray(information);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				GroupClass page = new GroupClass();
				page.setId(jsonObject.getString("userid"));
				page.setType(jsonObject.getString("type"));
				page.setGroup(jsonObject.getString("group"));
				pages.add(page);
			}
			return pages;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
