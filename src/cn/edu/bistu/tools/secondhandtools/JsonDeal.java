package cn.edu.bistu.tools.secondhandtools;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.bistu.secondhand.SecondHandItem;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;



public class JsonDeal {
	private String result;
	private Context context;
	public JsonDeal(String result,Context context){
		this.result=result;
		this.context = context;
	
	}
	


		public ArrayList<String> Deal(){
			ArrayList<String> urls = new ArrayList<String>();
		Log.v("result",result);
		if(!result.equals("[]"))
		{	
			try {
				JSONArray obj = new JSONArray(result);
	            for (int i = 0; i < obj.length(); i++) {
					JSONObject jsonObject = obj.getJSONObject(i);
					urls.add(jsonObject.getString("pic").split(";")[0]);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
			return null;
		return urls;
	
}
	public String resultdeal(){
		String url = null;
		try {
			JSONObject jsonObject = new JSONObject(result);
			String error = jsonObject.getString("error");
			if (error.equals("0")) {
				url = jsonObject.getString("url");
				
			} else {
               Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
               url = "error";
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
		
	}
	public static List<String> getClasses(String result){
		List<String> classes = new ArrayList<String>();
		String type = null;
		try {
			JSONArray array = new JSONArray(result);
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				type = jsonObject.getString("name");
				classes.add(type);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return classes;
		
	} 
	public static List<SecondHandItem> getseondhanditem(String result){
		
		List<SecondHandItem> handItems = new ArrayList<SecondHandItem>();
		try {
			JSONArray jsonArray = new JSONArray(result);
			for (int i = jsonArray.length()-1; i >= 0; i--) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				SecondHandItem secondHandItem = new SecondHandItem();
				secondHandItem.setTv_title(jsonObject.getString("titile"));
				secondHandItem.setTv_price(jsonObject.getString("price"));
				secondHandItem.setPic(jsonObject.getString("pic"));
				secondHandItem.setGoods_id(jsonObject.getString("id"));
				secondHandItem.setTypeid(jsonObject.getString("typeid"));
				secondHandItem.setTime(jsonObject.getString("time"));
				secondHandItem.setDescription(jsonObject.getString("description"));
				secondHandItem.setXqdm(jsonObject.getString("xqdm"));
				secondHandItem.setQQ(jsonObject.getString("QQ"));
				secondHandItem.setEmail(jsonObject.getString("email"));
				secondHandItem.setTel(jsonObject.getString("mobile"));
				handItems.add(secondHandItem);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < handItems.size(); i++) {
			Log.v("¼ÛÇ®", handItems.get(i).getTv_title());
		}
		
		return handItems;
		
	}
		
	}
