package cn.edu.bistu.newsdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.bistu.tools.NetworkLoad;

public class JsonNewschannel {

	public String getNewsChannel(String url) {
		NetworkLoad loadNewschannel = new NetworkLoad();
		String InforMation = null;
		try {
			InforMation = loadNewschannel.GetGetInformation(url);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return "Http Failed";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return "Http Failed";
		}
		return InforMation;
	}

	public List<NewschannelType> getList(String InforMation) {
		List<NewschannelType> newschannel = new ArrayList<NewschannelType>();
		try {
			JSONArray array = new JSONArray(InforMation);
			for (int i = 0; i < array.length(); i++) {
				NewschannelType newschannelType = new NewschannelType();
				JSONObject jsonObject1 = array.getJSONObject(i);
				String attributes = jsonObject1.getString("attributes");
				JSONObject jsonObject = new JSONObject(attributes);
				newschannelType.setId(jsonObject.getString("id"));
				newschannelType.setTitle(jsonObject.getString("n"));
				newschannelType.setPageType(jsonObject.getString("t"));
				newschannelType.setLastUpdate(jsonObject.getString("lmt"));
				newschannelType.setListUrl(fillterForNewsList(jsonObject
						.getString("url")));
				newschannelType.setIcon(jsonObject.getString("ic"));
				newschannel.add(newschannelType);
			}
			return newschannel;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String fillterForNewsList(String url) {
		int a = url.lastIndexOf("/");
		int b = url.indexOf(".cn/");
		return url.substring(b + 3, a);
	}
}
