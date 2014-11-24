package cn.edu.bistu.yellowPageData;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.icampus2_2.R;

import android.app.Activity;
import android.os.Bundle;

import cn.edu.bistu.newsdata.JsonNewschannel;

public class JsonYellowPagePersonal extends Activity {
	public String getInformatiopn(String url) {
		JsonNewschannel jsonNewschannel = new JsonNewschannel();
		return jsonNewschannel.getNewsChannel(url);
	}

	public List<YelloPagePersonal> getList(String information) {
		try {
			List<YelloPagePersonal> pagePersonals = new ArrayList<YelloPagePersonal>();
			JSONArray jsonArray = new JSONArray(information);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				YelloPagePersonal pagePersonal = new YelloPagePersonal();
				pagePersonal.setId(jsonObject.getString("id"));
				pagePersonal.setName(jsonObject.getString("name"));
				pagePersonal.setTelnum(jsonObject.getString("telnum"));
				pagePersonal.setDepart(jsonObject.getString("depart"));
				pagePersonals.add(pagePersonal);
			}
			return pagePersonals;
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
				.println(getList(getInformatiopn("http://m.bistu.edu.cn/newapi/yellowpage.php?action=tel&catid=33")));
	}
}
