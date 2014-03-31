package com.example.icampus2_2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.bistu.about.About;
import cn.edu.bistu.bus.BusShow;
import cn.edu.bistu.map.BistuMap;
import cn.edu.bistu.school.SchoolShow;
import cn.edu.bistu.tools.NetworkLoad;
import cn.edu.bistu.yellowPage.YellowPageShow;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

@SuppressLint("NewApi")
public class ICampus extends Activity {
	private ImageView news;
	private ImageView school;
	private ImageView yellowPage;
	private ImageView map;
	private ImageView bus;
	private PopupWindow popupWindow;
	private int screenWidth;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		(new UpdateAsynctask()).execute();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		init();
	}

	private void init() {
		map = (ImageView) findViewById(R.id.imageView6);
		news = (ImageView) findViewById(R.id.imageView5);
		school = (ImageView) findViewById(R.id.imageView2);
		yellowPage = (ImageView) findViewById(R.id.imageView3);
		bus = (ImageView) findViewById(R.id.imageView4);
		news.setOnClickListener(new Click());
		school.setOnClickListener(new Click());
		yellowPage.setOnClickListener(new Click());
		map.setOnClickListener(new Click());
		bus.setOnClickListener(new Click());
	}

	class Click implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.imageView5:
				intent.setClass(ICampus.this, MainActivity.class);
				break;
			case R.id.imageView2:
				intent.setClass(ICampus.this, SchoolShow.class);
				break;
			case R.id.imageView3:
				intent.setClass(ICampus.this, YellowPageShow.class);
				break;
			case R.id.imageView6:
				intent.setClass(ICampus.this, BistuMap.class);
				break;
			case R.id.imageView4:
				intent.setClass(ICampus.this, BusShow.class);
				break;
			default:
				break;
			}
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.icampus, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.more:
			ICampus.this.showPopWindow();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void showPopWindow() {
		if (popupWindow == null) {
			LayoutInflater layoutInflater = LayoutInflater.from(ICampus.this);
			final View view = layoutInflater.inflate(R.layout.popwindow, null);
			ListView listView = (ListView) view.findViewById(R.id.moreList);
			List<String> list = new ArrayList<String>();
			list.add("关于");
			list.add("检查更新");
			list.add("添加");
			MoreAdapter adapter = new MoreAdapter(list, ICampus.this);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					switch (arg2) {
					case 0:
						Intent intent = new Intent();
						intent.setClass(ICampus.this, About.class);
						startActivity(intent);
						popupWindow.dismiss();
						break;
					case 1:
						(new UpdateAsynctask()).execute();
					default:
						break;
					}
				}
			});
			// 这块还不太明白
			popupWindow = new PopupWindow(view, screenWidth / 2,
					LayoutParams.WRAP_CONTENT);
		}
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAsDropDown(findViewById(R.id.more), 0, 0);
	}

	class UpdateAsynctask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				String updateInformation = (new NetworkLoad())
						.GetGetInformation("http://m.bistu.edu.cn/newapi/ibistuAndroid.php");
				return updateInformation;
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			try {
				JSONObject jsonObject = new JSONObject(result);
				UpdateType updateType = new UpdateType();
				updateType.setApkName(jsonObject.getString("apkname"));
				updateType.setAppName(jsonObject.getString("appname"));
				updateType.setVerCode(jsonObject.getInt("verCode"));
				updateType.setVerName(jsonObject.getString("verName"));
				int currentVersionCode = getVerCode();
				String currentVersionName = getVerName();
				if (currentVersionCode < updateType.getVerCode()) {
					(new Update(ICampus.this, currentVersionName, updateType.getVerName())).update();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.onPostExecute(result);
		}
	}

	private int getVerCode() {
		int verCode = -1;
		try {
			verCode = getPackageManager().getPackageInfo(
					"com.example.icampus2_2", 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return verCode;
	}

	private String getVerName() {
		String verName = "";
		try {
			verName = getPackageManager().getPackageInfo(
					"com.example.icampus2_2", 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return verName;
	}
}
