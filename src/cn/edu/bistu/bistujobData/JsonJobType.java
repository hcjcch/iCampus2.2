package cn.edu.bistu.bistujobData;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonJobType {
	public List<JobType> getJobTypes(String information) {
		List<JobType> jobTypes = new ArrayList<JobType>();
		try {
			JSONArray jsonArray = new JSONArray(information);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JobType jobType = new JobType();
				jobType.setId(jsonObject.getString("id"));
				jobType.setType(jsonObject.getString("name"));
				jobTypes.add(jobType);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return jobTypes;
	}
}
