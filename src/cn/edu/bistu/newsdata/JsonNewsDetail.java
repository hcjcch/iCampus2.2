package cn.edu.bistu.newsdata;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonNewsDetail {

	public DetailNewsType getDetailNews(String Information) {
		DetailNewsType detailNewsType = new DetailNewsType();
		////String Information = (new JsonNewschannel()).getNewsChannel(url);
		if (Information.equals("-1")||Information == "-1") {
			return null;
		}
		try {
			JSONObject jsonObject = new JSONObject(Information);
			String as = jsonObject.getString("as");
			String property = jsonObject.getString("property");
			jsonObject = new JSONObject(property);
			detailNewsType.setDocid(jsonObject.getString("docid"));
			detailNewsType.setDocchannel(jsonObject.getString("docchannel"));
			detailNewsType.setDoctype(jsonObject.getString("doctype"));
			detailNewsType.setDoctitle(filterReturnInTitle(jsonObject
					.getString("doctitle")));
			detailNewsType.setSubdoctitle(jsonObject.getString("subdoctitle"));
			detailNewsType.setDocauthor(jsonObject.getString("docauthor"));
			detailNewsType.setDocsourcename(jsonObject
					.getString("docsourcename"));
			detailNewsType.setDocreltime(jsonObject.getString("docreltime"));
			detailNewsType.setOpertime(jsonObject.getString("opertime"));
			detailNewsType.setCruser(jsonObject.getString("cruser"));
			detailNewsType.setCrtime(jsonObject.getString("crtime"));
			detailNewsType.setDocabstract(jsonObject.getString("docabstract"));
			detailNewsType.setDochtmlcon(filterReturn(jsonObject
					.getString("dochtmlcon")));
			try {
				jsonObject = new JSONObject(as);
				String a = jsonObject.getString("a");
				JSONArray jsonArray = new JSONArray(a);
				ArrayList<DetailResource> list = new ArrayList<DetailResource>();
				for (int i = 0; i < jsonArray.length(); i++) {
					DetailResource detailResource = new DetailResource();
					jsonObject = jsonArray.getJSONObject(i);
					String attributes = jsonObject.getString("attributes");
					jsonObject = new JSONObject(attributes);
					detailResource.setN(jsonObject.getString("n"));
					detailResource.setT(jsonObject.getString("t"));
					detailResource.setUrl(jsonObject.getString("url"));
					list.add(detailResource);
				}
				detailNewsType.setDetailResources(list);
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return detailNewsType;
	}

	public String filterReturn(String parameter) {
		return parameter.replaceAll("<br/>", "\n");
	}
	public String filterReturnInTitle(String parameter) {
		String string = parameter.replaceAll("<br/>", "");
		string = string.replaceAll("\\u3000","" );
		return string;
	}
}
