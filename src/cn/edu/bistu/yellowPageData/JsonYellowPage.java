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

public class JsonYellowPage extends Activity {
	public String getInformation(String url) {
		JsonNewschannel jsonNewschannel = new JsonNewschannel();
		return jsonNewschannel.getNewsChannel(url);
	}

	public List<YelloPage> getList(String information) {
		try {
			List<YelloPage> pages = new ArrayList<YelloPage>();
			JSONArray jsonArray = new JSONArray(information);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				YelloPage page = new YelloPage();
				page.setId(jsonObject.getString("id"));
				page.setName(jsonObject.getString("name"));
				page.setTelnum(jsonObject.getString("telnum"));
				page.setDepart(jsonObject.getString("depart"));
				pages.add(page);
			}
			return pages;
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
				.println(getList(getInformation("http://m.bistu.edu.cn/newapi/yellowpage.php?action=cat")));
	}
}
