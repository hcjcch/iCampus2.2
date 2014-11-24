package cn.edu.bistu.schoolData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.icampus2_2.R;

import android.app.Activity;
import android.os.Bundle;

import cn.edu.bistu.newsdata.JsonNewschannel;

public class JsonSchoolDetail extends Activity {
	public String getInformation(String url) {
		JsonNewschannel jsonNewschannel = new JsonNewschannel();
		return jsonNewschannel.getNewsChannel(url);
	}

	public SchoolDetail getList(String information) {
		try {
			JSONArray jsonArray = new JSONArray(information);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			SchoolDetail detail = new SchoolDetail();
			detail.setId(jsonObject.getString("id"));
			detail.setMod(jsonObject.getString("mod"));
			detail.setIntroName(jsonObject.getString("introName"));
			detail.setIntroCont(jsonObject.getString("introCont"));
			detail.setHref(jsonObject.getString("href"));
			detail.setRank(jsonObject.getString("rank"));
			return detail;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		System.out
				.println(getList(getInformation("http://api.bistu.edu.cn/api/api.php?table=collegeintro&action=detail&mod=meec&id=4")));
	}
}
