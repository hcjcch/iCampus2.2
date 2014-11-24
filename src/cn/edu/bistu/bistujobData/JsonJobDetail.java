package cn.edu.bistu.bistujobData;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonJobDetail {
	public JobDetailType getList(String information) {
		JobDetailType detailType = new JobDetailType();
		try {
			JSONObject jsonObject = new JSONObject(information);
			detailType.setId(jsonObject.getString("id"));
			detailType.setTitle(jsonObject.getString("title"));
			detailType.setTypeid(jsonObject.getString("typeid"));
			detailType.setDescription(jsonObject.getString("description"));
			detailType.setLocation(jsonObject.getString("location"));
			detailType.setQualifications(jsonObject.getString("qualifications"));
			detailType.setSalary(jsonObject.getString("salary"));
			detailType.setCompany(jsonObject.getString("company"));
			detailType.setContactName(jsonObject.getString("contactName"));
			detailType.setContactEmail(jsonObject.getString("contactEmail"));
			detailType.setContactPhone(jsonObject.getString("contactPhone"));
			detailType.setContactQQ(jsonObject.getString("contactQQ"));
			detailType.setUserid(jsonObject.getString("userid"));
			detailType.setTime(jsonObject.getString("time"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return detailType;
	}
}
