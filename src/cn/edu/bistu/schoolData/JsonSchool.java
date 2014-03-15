package cn.edu.bistu.schoolData;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.icampus2_2.R;

import android.app.Activity;
import android.os.Bundle;

import cn.edu.bistu.newsdata.JsonNewschannel;

public class JsonSchool extends Activity{
	public String getInformation(String url){
		JsonNewschannel newschannel = new JsonNewschannel();
		return newschannel.getNewsChannel(url);
	}
	public List<School> getList(String information){
		List<School> schools = new ArrayList<School>();
		try {
			JSONArray jsonArray = new JSONArray(information);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				School school = new School();
				school.setId(jsonObject.getString("id"));
				school.setIntroName(jsonObject.getString("introName"));
				school.setMod(jsonObject.getString("mod"));
				schools.add(school);
			}
			return schools;
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
		System.out.println(getList(getInformation("http://api.bistu.edu.cn/api/api.php?table=collegeintro")));
	}
}
