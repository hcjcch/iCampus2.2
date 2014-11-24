package com.example.groupdata;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;

public class JsonStudent extends Activity {
	public List<Student> getList(String information) {
		try {
			List<Student> pages = new ArrayList<Student>();
			JSONArray jsonArray = new JSONArray(information);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Student page = new Student();
				page.setXH(jsonObject.getString("XH"));
				page.setXM(jsonObject.getString("XM"));
				page.setXB(jsonObject.getString("XB"));
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
