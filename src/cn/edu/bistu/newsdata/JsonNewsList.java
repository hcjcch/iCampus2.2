package cn.edu.bistu.newsdata;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonNewsList{
	
	public static int state = 1;
	//state = 0 表示http访问shibai
	//state = -1表示没有数据了
	//state = 2 数据解析失败
	//state = 1 数据已成功格式返回
	public List<NewsListType> getList(String url){
		String information = (new JsonNewschannel()).getNewsChannel(url);
		if (information.equals("Http Failed")||information == "Http Failed") {
			state = 0;
			return null;
		}
		if (information.equals("-1")||information == "-1") {
			state = -1;
			return null;
		}
		List<NewsListType> list = new ArrayList<NewsListType>();
		String information1 = null;
		try {
			JSONObject jsonObject1 = new JSONObject(information);
			information1 = jsonObject1.getString("d");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			JSONArray jsonArray = new JSONArray(information1);
			for (int i = 0; i < jsonArray.length(); i++) {
				NewsListType newsListType = new NewsListType();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String attributes = jsonObject.getString("attributes");
				JSONObject jsonObject2 = new JSONObject(attributes);
				newsListType.setPassageId(jsonObject2.getString("id"));
				newsListType.setTitle(filterBr(jsonObject2.getString("n")));
				newsListType.setPreview(filterBr(jsonObject2.getString("ab")));
				newsListType.setAuthor(jsonObject2.getString("au"));
				newsListType.setLastUpdate(jsonObject2.getString("rt"));
			//	if (jsonObject2.getString("ic").indexOf(".") != -1) {
					//newsListType.setIcon(add_120(jsonObject2.getString("ic")));
					newsListType.setIcon(jsonObject2.getString("ic"));
			//	}else {
			//		newsListType.setIcon(jsonObject2.getString("ic"));
				//}
				newsListType.setDetailUrl(filterUrl(jsonObject2.getString("url")));
				list.add(newsListType);
			}
			state = 1;
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			state = 2;
			return list = null;
		}
	}
	
	public String filterBr(String s){
		return s.replaceAll("\\|br\\|", "");
	}
	public String filterUrl(String url){
		int a = url.indexOf(".cn/");
		int b = url.indexOf(".xml");
		return url.substring(a+4,b);
	}
	public String add_120(String icon){
		int a = icon.length()-4;
		String string = icon.substring(0,a);
		String string2 = icon.substring(a,icon.length());
		return string+"_120"+string2;
	}
}
