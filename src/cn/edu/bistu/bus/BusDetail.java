package cn.edu.bistu.bus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.edu.bistu.busData.BusLine;

import com.example.icampus2_2.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class BusDetail extends Activity {
	private Bundle bundle;
	private View header;
	private View footer;
	private TextView headerTime;
	private TextView footerTime;
	private TextView headerStation;
	private TextView footerStation;
	private BusLine busLine;
	private List<Map<String, String>> lines;
	private ArrayList<String> station = new ArrayList<String>();
	private ArrayList<String> time = new ArrayList<String>();
	private ListView listView;
	private TextView busName1;
	private TextView busName2;
	private TextView busName3;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		lines = new ArrayList<Map<String,String>>();
		LayoutInflater inflater = LayoutInflater.from(this);
		header = inflater.inflate(R.layout.busine_detail_item_header, null);
		footer = inflater.inflate(R.layout.bus_detail_item_footer, null);
		headerTime = (TextView)header.findViewById(R.id.headerTime);
		headerStation = (TextView)header.findViewById(R.id.headerStation);
		footerTime = (TextView)footer.findViewById(R.id.footerTime);
		footerStation = (TextView)footer.findViewById(R.id.footerStation);
		
		Intent intent = getIntent();
		bundle = intent.getExtras();
		busLine = (BusLine) bundle.getSerializable("busLine");
		lines = busLine.getMaps();//获得路线数据(ArrayList<Map<String,String>>)
		String titile = bundle.getString("busType");
		//将传过来的路线数据处理成两个数组
		for (Map<String, String> line : lines) {
			Set<Map.Entry<String, String>> a = line.entrySet();
			Iterator<Map.Entry<String, String>> iterator = a.iterator();
			Map.Entry<String, String> b = iterator.next();
			station.add(b.getKey());
			time.add(b.getValue());
		}
		//设置listview头和尾
		String timeString = time.get(0);
		if (timeString.equals("")||timeString == ""||timeString == null) {
			headerTime.setText("     ");
		}else {
			headerTime.setText(time.get(0));
		}
		headerStation.setText(station.get(0));
		footerTime.setText(time.get(time.size()-1));
		footerStation.setText(station.get(station.size()-1));
		//将已设置的头和尾的数据从元数据中心删除
		time.remove(0);
		time.remove(time.size()-1);
		station.remove(0);
		station.remove(station.size()-1);
		//设置ActionBar
		ActionBar bar = getActionBar();
		bar.setTitle(titile);
		bar.setDisplayHomeAsUpEnabled(true);
		bar.show();
		//设置listview适配
		show();
	}

	public void show() {
		String busName = bundle.getString("busName");
		String[] strings = busName.split("-");
		if (strings.length == 1) {
			setContentView(R.layout.bus_detail1);
			listView = (ListView)findViewById(R.id.detailBus);
			busName1 = (TextView)findViewById(R.id.titleline);
			busName1.setText(busName);
		}else if (strings.length == 2) {
			setContentView(R.layout.bus_detail2);
			listView = (ListView)findViewById(R.id.listView1);
			busName1 = (TextView)findViewById(R.id.textView1);
			busName2 = (TextView)findViewById(R.id.textView2);
			String[] strings2 = busName.split("-");
			busName1.setText(strings2[0]);
			busName2.setText(strings2[1]);
		}else {
			setContentView(R.layout.bus_detail3);
			listView = (ListView)findViewById(R.id.listView1);
			try {
				busName1 = (TextView)findViewById(R.id.textView3);
				busName2 = (TextView)findViewById(R.id.textView1);
				busName3 = (TextView)findViewById(R.id.textView2);
				String[] strings2 = busName.split("-");
				busName1.setText(strings2[0]);
				busName2.setText(strings2[1]);
				busName3.setText(strings2[2]);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		try {
			BusDetailAdapter adapter = new BusDetailAdapter(station, time, this);
			listView.addHeaderView(header);
			listView.addFooterView(footer);
			listView.setAdapter(adapter);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.alarm:
			Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
			startActivity(intent);
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.bus_line, menu);
		return super.onCreateOptionsMenu(menu);
	}
}

