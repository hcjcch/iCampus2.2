package com.example.icampus2_2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.group.GroupFirst;
import com.example.personal.Person;
import com.example.personal.PersonShow;
import com.hcjcch.educationaladministration.activity.MarkQueryActivity;
import com.hcjcch.educationaladministration.activity.SchoolPlaceActivity;

import cn.edu.bistu.about.About;
import cn.edu.bistu.bistujob.BistuJob;
import cn.edu.bistu.bus.BusShow;
import cn.edu.bistu.map.BistuMap;
import cn.edu.bistu.oauth.Oauth;
import cn.edu.bistu.oauthsdk.Config;
import cn.edu.bistu.oauthsdk.HttpRequestGetTask;
import cn.edu.bistu.oauthsdk.OauthUtil;
import cn.edu.bistu.oauthsdk.OpenBistuProvider;
import cn.edu.bistu.oauthsdk.OpenConsumer;
import cn.edu.bistu.school.SchoolShow;
import cn.edu.bistu.secondhand.SecondHand;
import cn.edu.bistu.tools.ACache;
import cn.edu.bistu.tools.GetInformation;
import cn.edu.bistu.tools.MyPopWindow;
import cn.edu.bistu.tools.NetworkLoad;
import cn.edu.bistu.wifi.Login;
import cn.edu.bistu.wifi.Logout;
import cn.edu.bistu.wifi.StatusFile;
import cn.edu.bistu.yellowPage.YellowPageShow;
import cn.edu.bistu.yellowPageData.YelloPage;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

@SuppressLint("NewApi")
public class ICampus extends Activity {
	private GridView gridView;
	private PopupWindow popupWindow;
	private int screenWidth;
	private final static int LOGIN_WIFI_REQUEST_CODE_STRING = 1;
	private final static int LOGOUT_WIFI_REQUEST_CODE_STRING = 2;
	private final static int OATUTH_LOGIN = 3;
	private StatusFile statusFile;
	private boolean isButtonCheck = false;
	private static boolean first = true;
	private Net net;
	private List<Item> moduls;
	private List<Item> menus;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		statusFile = new StatusFile(ICampus.this);
		if (first) {
			Intent intent = getIntent();
			net = (Net) intent.getSerializableExtra("net");
			if (net.isNet()) {
				(new UpdateAsynctask()).execute();
			} else {
				showNoNet();
			}
			first = false;
		}
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		init();
	}

	private void showNoNet() {
		Builder builder = new AlertDialog.Builder(ICampus.this)
				.setTitle("������ʾ��Ϣ")
				.setMessage("���粻���ã���������������������磡")
				.setIcon(R.drawable.ic_action_about2)
				.setPositiveButton("����", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						if (android.os.Build.VERSION.SDK_INT > 10) {
							intent = new Intent(
									android.provider.Settings.ACTION_WIFI_SETTINGS);
						} else {
							intent = new Intent();
							ComponentName component = new ComponentName(
									"com.android.settings",
									"com.android.settings.WirelessSettings");
							intent.setComponent(component);
							intent.setAction("android.intent.action.VIEW");
						}
						startActivity(intent);
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				});
		builder.create();
		builder.show();
	}

	private void init() {
		gridView = (GridView) findViewById(R.id.icampus);
		moduls = new ArrayList<Item>();
		moduls.add(new Item(R.drawable.news, "����", MainActivity.class));
		moduls.add(new Item(R.drawable.school, "ѧԺ", SchoolShow.class));
		moduls.add(new Item(R.drawable.yellowpage, "��ҳ", YellowPageShow.class));
		moduls.add(new Item(R.drawable.map, "��ͼ", BistuMap.class));
		moduls.add(new Item(R.drawable.buslogo, "У��", BusShow.class));
		moduls.add(new Item(R.drawable.wi_fi, "wifi", Login.class));
		moduls.add(new Item(R.drawable.jobseekericon, "����", BistuJob.class));
		moduls.add(new Item(R.drawable.groupicon, "Ⱥ��", GroupFirst.class));
		moduls.add(new Item(R.drawable.shopping_bag, "����", SecondHand.class));
		moduls.add(new Item(R.drawable.fronticonsfreerooms, "����", SchoolPlaceActivity.class));
		moduls.add(new Item(R.drawable.fronticonsgrade_search, "�ɼ�", MarkQueryActivity.class));
		
		GridAdapter adapter = new GridAdapter(moduls, ICampus.this);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				if (moduls.get(arg2).getImageId() == R.drawable.yellowpage) {
					intent.setClass(ICampus.this, YellowPageShow.class);
					ACache aCache = ACache.get(ICampus.this);
					Person person = (Person) aCache.getAsObject("user");
					if (person == null) {
						Toast.makeText(ICampus.this, "����û�е�¼��", Toast.LENGTH_LONG)
								.show();
						intent.setClass(ICampus.this, Oauth.class);
						startActivityForResult(intent, OATUTH_LOGIN);
						return;
					}
					intent.putExtra("user", person);
					startActivity(intent);
				}
				else if (moduls.get(arg2).getImageId() == R.drawable.wi_fi) {
					int status = statusFile.readStatus();
					if (status == 0) {
						intent.setClass(ICampus.this, Login.class);
						startActivityForResult(intent,
								LOGIN_WIFI_REQUEST_CODE_STRING);
					} else {
						intent.setClass(ICampus.this, Logout.class);
						startActivityForResult(intent,
								LOGOUT_WIFI_REQUEST_CODE_STRING);
					}
				}else if (moduls.get(arg2).getImageId() == R.drawable.groupicon) {
					intent.setClass(ICampus.this, GroupFirst.class);
					ACache aCache = ACache.get(ICampus.this);
					Person person = (Person) aCache.getAsObject("user");
					if (person == null) {
						Toast.makeText(ICampus.this, "����û�е�¼��", Toast.LENGTH_LONG)
								.show();
						intent.setClass(ICampus.this, Oauth.class);
						startActivityForResult(intent, OATUTH_LOGIN);
						return;
					}
					intent.putExtra("user", person);
					startActivity(intent);
				}else if (moduls.get(arg2).getImageId() == R.drawable.fronticonsgrade_search) {
					intent.setClass(ICampus.this, MarkQueryActivity.class);
					//intent.setClass(ICampus.this, GroupFirst.class);
					ACache aCache = ACache.get(ICampus.this);
					Person person = (Person) aCache.getAsObject("user");
					if (person == null) {
						Toast.makeText(ICampus.this, "����û�е�¼��", Toast.LENGTH_LONG)
								.show();
						intent.setClass(ICampus.this, Oauth.class);
						startActivityForResult(intent, OATUTH_LOGIN);
						return;
					}
					intent.putExtra("user", person);
					startActivity(intent);
				}else {
					intent.setClass(ICampus.this, moduls.get(arg2).getCla());
					startActivity(intent);
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case LOGIN_WIFI_REQUEST_CODE_STRING:
			if (resultCode == RESULT_OK) {
				switch (data.getIntExtra("status", 0)) {
				case 0:
					Toast.makeText(ICampus.this, "Login Successful",
							Toast.LENGTH_SHORT).show();
					break;
				case 1:
					Toast.makeText(ICampus.this, "�Ѿ���¼�������ظ���¼�������˳���",
							Toast.LENGTH_LONG).show();
					StatusFile status = new StatusFile(ICampus.this);
					status.writeStatus(1);
					break;
				case 2:
					Toast.makeText(ICampus.this, "�������ܾ������Ժ����ԡ�",
							Toast.LENGTH_LONG).show();
				case 3:// ����ִ������
					Toast.makeText(ICampus.this, "���û��Ѿ�������ϵͳ��¼��",
							Toast.LENGTH_LONG).show();
					break;
				case 4:// ����ִ������
					Toast.makeText(ICampus.this, "��ε�¼�������Ժ��¼��",
							Toast.LENGTH_LONG).show();
					break;
				case 5:// ����ִ������
					Toast.makeText(ICampus.this,
							"��¼���󡣿���ԭ���û������������û���˳���������£��˳���������10����֮���ٳ��Ե�¼��",
							Toast.LENGTH_LONG).show();
					break;
				default:
					Toast.makeText(ICampus.this,
							"�쳣��(�����Ƿ��ʳ�ʱ��Ҳ�����������쳣��)�����µ�¼��", Toast.LENGTH_LONG)
							.show();
					break;
				}
			}
			break;
		case LOGOUT_WIFI_REQUEST_CODE_STRING:
			if (resultCode == RESULT_OK) {
				switch (data.getIntExtra("status", 0)) {
				case 0:
					Toast.makeText(ICampus.this, "LogOut Successful",
							Toast.LENGTH_SHORT).show();
					break;
				case 6:
					Toast.makeText(ICampus.this, "�����쳣���Ѿ��Ͽ�����,���������µ�¼�ˣ�",
							Toast.LENGTH_SHORT).show();
					break;
				default:
					Toast.makeText(ICampus.this, "�����쳣", Toast.LENGTH_SHORT)
							.show();
					break;
				}
			}
			break;
		case OATUTH_LOGIN:
			if (resultCode == RESULT_OK) {
				GetInformation.get(data, ICampus.this);
			}
			break;
		default:
			break;
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
		final Intent intent = new Intent();
		switch (item.getItemId()) {
		case R.id.icampus_more:
				final MyPopWindow popWindow = new MyPopWindow(ICampus.this);
				menus = new ArrayList<Item>();
				menus.add(new Item(R.drawable.ic_action_about, "����", About.class));
				menus.add(new Item(R.drawable.ic_action_user, "��¼", Oauth.class));
				menus.add(new Item(R.drawable.ic_action_collapse, "������", About.class));
				menus.add(new Item(R.drawable.ic_action_user, "��������", PersonShow.class));
				MenuAdapter menuAdapter = new MenuAdapter(ICampus.this, menus);
				popWindow.showPopWindow(R.layout.sort, R.id.sort, menuAdapter, new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						switch (arg2) {
						case 0:
							intent.setClass(ICampus.this, About.class);
							startActivity(intent);
							break;
						case 1:
							intent.setClass(ICampus.this, Oauth.class);
							startActivityForResult(intent, OATUTH_LOGIN);
							break;
						case 2:
							isButtonCheck = true;
							(new UpdateAsynctask()).execute();
							break;
						case 3:
							ACache aCache = ACache.get(ICampus.this);
							Person person = (Person) aCache.getAsObject("user");
							if (person == null) {
								Toast.makeText(ICampus.this, "����û�е�¼��", Toast.LENGTH_LONG)
										.show();
								intent.setClass(ICampus.this, Oauth.class);
								startActivityForResult(intent, OATUTH_LOGIN);
								break;
							}
							intent.putExtra("user", person);
							intent.setClass(ICampus.this, PersonShow.class);
							startActivity(intent);		
							break;
						default:
							break;
						}
						popWindow.dismiss();
					}
				}, screenWidth/2+10, findViewById(R.id.icampus_more),-200);
		default:
			break;
			
		}
		return super.onOptionsItemSelected(item);
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
			System.out.println("result" + result + "-------------");
			if (result == null) {
				return;
			}
			try {
				JSONObject jsonObject = new JSONObject(result);
				UpdateType updateType = new UpdateType();
				updateType.setApkName(jsonObject.getString("apkname"));
				updateType.setAppName(jsonObject.getString("appname"));
				updateType.setVerCode(jsonObject.getInt("verCode"));
				updateType.setVerName(jsonObject.getString("verName"));
				updateType.setApkUrl(jsonObject.getString("url"));
				int currentVersionCode = getVerCode();
				String currentVersionName = getVerName();
				if (currentVersionCode < updateType.getVerCode()) {
					(new Update(ICampus.this, currentVersionName,
							updateType.getVerName(), updateType.getApkUrl()))
							.update();
				} else {
					// �ж��ǲ����û�������
					if (isButtonCheck) {
						Toast.makeText(ICampus.this, "��ǰ�Ѿ������°汾",
								Toast.LENGTH_SHORT).show();
					}
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
