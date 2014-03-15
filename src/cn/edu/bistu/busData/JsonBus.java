package cn.edu.bistu.busData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.icampus2_2.R;

import android.app.Activity;
import android.os.Bundle;

import cn.edu.bistu.newsdata.JsonNewschannel;

public class JsonBus extends Activity{
	public String getInformation(String url){
		JsonNewschannel jsonNewschannel = new JsonNewschannel();
		return jsonNewschannel.getNewsChannel(url);
	}
	public List<Bus> getList(String Information){
		try {
			List<Bus> buses = new ArrayList<Bus>();
			JSONArray jsonArray = new JSONArray(Information);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Bus bus = new Bus();
				bus.setId(jsonObject.getString("id"));
				bus.setCatName(jsonObject.getString("catName"));
				bus.setCatIntro(jsonObject.getString("catIntro"));
				CatBus catBus = new CatBus();
				List<EveryBus> everyBuses = new ArrayList<EveryBus>();
				String one = jsonObject.getString("catBus");
				JSONArray jsonArray2 = new JSONArray(one);
				for (int j = 0; j < jsonArray2.length(); j++) {
					EveryBus everyBus = new EveryBus();
					JSONObject jsonObject2 = jsonArray2.getJSONObject(j);
					everyBus.setCatBusId(jsonObject2.getString("id"));
					everyBus.setBusName(jsonObject2.getString("busName"));
					everyBus.setDepartTime(jsonObject2.getString("departTime"));
					everyBus.setReturnTime(jsonObject2.getString("returnTime"));
					everyBus.setBusUntro(jsonObject2.getString("busIntro"));
					BusLine busLine = new BusLine();
					List<Map<String, String>> maps = new ArrayList<Map<String,String>>();
					String two = jsonObject2.getString("busLine");
					JSONArray jsonArray3 = new JSONArray(two);
					for (int k = 0; k < jsonArray3.length(); k++) {
						Map<String, String> map = new HashMap<String, String>();
						JSONObject jsonObject3 = jsonArray3.getJSONObject(k);
						Iterator<String> iterator = jsonObject3.keys();
						String key = iterator.next();
						map.put(key, jsonObject3.getString(key));
						maps.add(map);
					}
					busLine.setMaps(maps);
					everyBus.setBusLine(busLine);
					everyBuses.add(everyBus);
				}
				catBus.setCatbus(everyBuses);
				bus.setCatbus(catBus);
				buses.add(bus);
			}
			return buses;
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
		System.out.println(getList(getInformation("http://m.bistu.edu.cn/newapi/bus.php")));
	}
}
