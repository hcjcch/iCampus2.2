package cn.edu.bistu.bistujobData;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonJobList {
	public List<JobListType> getList(String information) {
		List<JobListType> list = new ArrayList<JobListType>();
		try {
			JSONArray jsonArray = new JSONArray(information);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JobListType jobListType = new JobListType();
				jobListType.setId(jsonObject.getString("id"));
				jobListType.setTitle(jsonObject.getString("title"));
				jobListType.setTypeid(jsonObject.getString("typeid"));
				jobListType.setLocation(jsonObject.getString("location"));
				jobListType.setSalary(jsonObject.getString("salary"));
				jobListType.setTime(jsonObject.getString("time"));
				list.add(jobListType);
			}
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return list;
		}
	}
}
