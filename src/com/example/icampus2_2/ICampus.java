package com.example.icampus2_2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.group.GroupFirst;
import com.example.personal.Person;
import com.example.personal.PersonShow;

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
import cn.edu.bistu.tools.ACache;
import cn.edu.bistu.tools.NetworkLoad;
import cn.edu.bistu.wifi.Login;
import cn.edu.bistu.wifi.Logout;
import cn.edu.bistu.wifi.StatusFile;
import cn.edu.bistu.yellowPage.YellowPageShow;
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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

@SuppressLint("NewApi")
public class ICampus extends Activity {
	private ImageView news;
	private ImageView school;
	private ImageView yellowPage;
	private ImageView map;
	private ImageView bus;
	private PopupWindow popupWindow;
	private ImageView publish;
	private int screenWidth;
	private ImageView group;
	private ImageView wifi;
	private final static int LOGIN_WIFI_REQUEST_CODE_STRING = 1;
	private final static int LOGOUT_WIFI_REQUEST_CODE_STRING = 2;
	private final static int OATUTH_LOGIN = 3;
	private StatusFile statusFile;
	private boolean isButtonCheck = false;
	private static boolean first = true;
	private Net net;

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
				.setTitle("网络提示信息")
				.setMessage("网络不可用，如果继续，请先设置网络！")
				.setIcon(R.drawable.ic_action_about2)
				.setPositiveButton("设置", new DialogInterface.OnClickListener() {
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
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				});
		builder.create();
		builder.show();
	}

	private void init() {
		map = (ImageView) findViewById(R.id.imageView6);
		news = (ImageView) findViewById(R.id.imageView5);
		school = (ImageView) findViewById(R.id.imageView2);
		yellowPage = (ImageView) findViewById(R.id.imageView3);
		bus = (ImageView) findViewById(R.id.imageView4);
		wifi = (ImageView) findViewById(R.id.imageView7);
		publish = (ImageView) findViewById(R.id.publish);
		group = (ImageView) findViewById(R.id.ImageView01);
		group.setOnClickListener(new Click());
		publish.setOnClickListener(new Click());
		news.setOnClickListener(new Click());
		school.setOnClickListener(new Click());
		yellowPage.setOnClickListener(new Click());
		map.setOnClickListener(new Click());
		bus.setOnClickListener(new Click());
		wifi.setOnClickListener(new Click());
	}

	class Click implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.imageView5:
				intent.setClass(ICampus.this, MainActivity.class);
				startActivity(intent);
				break;
			case R.id.imageView2:
				intent.setClass(ICampus.this, SchoolShow.class);
				startActivity(intent);
				break;
			case R.id.imageView3:
				intent.setClass(ICampus.this, YellowPageShow.class);
				startActivity(intent);
				break;
			case R.id.imageView6:
				intent.setClass(ICampus.this, BistuMap.class);
				startActivity(intent);
				break;
			case R.id.imageView4:
				intent.setClass(ICampus.this, BusShow.class);
				startActivity(intent);
				break;
			case R.id.imageView7:
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
				break;
			case R.id.publish:
				intent.setClass(ICampus.this, BistuJob.class);
				startActivity(intent);
				break;
			case R.id.ImageView01:
				intent.setClass(ICampus.this, GroupFirst.class);
				ACache aCache = ACache.get(ICampus.this);
				Person person = (Person) aCache.getAsObject("user");
				if(person == null){
					Toast.makeText(ICampus.this, "您还没有登录！",Toast.LENGTH_LONG).show();
					intent.setClass(ICampus.this, Oauth.class);
					startActivity(intent);
					return;
				}
				intent.putExtra("user", person);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
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
					Toast.makeText(ICampus.this, "已经登录，不能重复登录，请先退出！",
							Toast.LENGTH_LONG).show();
					StatusFile status = new StatusFile(ICampus.this);
					status.writeStatus(1);
					break;
				case 2:
					Toast.makeText(ICampus.this, "服务器拒绝，请稍后再试。",
							Toast.LENGTH_LONG).show();
				case 3:// 程序执行正常
					Toast.makeText(ICampus.this, "该用户已经在其他系统登录。",
							Toast.LENGTH_LONG).show();
					break;
				case 4:// 程序执行正常
					Toast.makeText(ICampus.this, "多次登录错误，请稍候登录。",
							Toast.LENGTH_LONG).show();
					break;
				case 5:// 程序执行正常
					Toast.makeText(ICampus.this,
							"登录错误。可能原因：用户名、密码错误；没有退出外网情况下，退出内网，请10分钟之后再尝试登录。",
							Toast.LENGTH_LONG).show();
					break;
				default:
					Toast.makeText(ICampus.this,
							"异常！(可能是访问超时，也可能是网络异常！)请重新登录！", Toast.LENGTH_LONG)
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
					Toast.makeText(ICampus.this, "程序异常，已经断开网络,您可以重新登录了！",
							Toast.LENGTH_SHORT).show();
					break;
				default:
					Toast.makeText(ICampus.this, "程序异常", Toast.LENGTH_SHORT)
							.show();
					break;
				}
			}
			break;
		case OATUTH_LOGIN:
			if (resultCode == RESULT_OK) {
				String res = data.getStringExtra("result");
				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(ICampus.this);
				if (res != null && !res.equals("")) {
					String[] mes = res.split("&"); // 将取回来的值进行解析，按照&来分开
					for (String info : mes) { // 遍历，取出每个字段对应的值
						if (info.contains("access_token")) {
							info = info.substring(info.indexOf("=") + 1,
									info.length());
							OauthUtil.saveOauth(prefs, info);// 将access_token写入SharedPreference
						}
						if (info.contains("token_type")) {
							info = info.substring(info.indexOf("=") + 1,
									info.length());
							
						}
						if (info.contains("expires_in")) {
							info = info.substring(info.indexOf("=") + 1,
									info.length());
							ACache aCache = ACache.get(ICampus.this);
							aCache.put("expires_in", info);
						}
					}
					String appId = Config.getValue("client_id");
					String appSecret = Config.getValue("client_secret");
					OpenConsumer openConsumer = new OpenConsumer(appId,
							appSecret);
					Map<String, String> params = new HashMap<String, String>();
					OpenBistuProvider provider = new OpenBistuProvider(
							"https://222.249.250.89:8443/m/userinfo.htm",
							openConsumer, ICampus.this);
					new HttpRequestGetTask(provider, params, this).execute();
				} else {
					Toast.makeText(ICampus.this, "登录失败", Toast.LENGTH_SHORT)
							.show();
				}
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
		Intent intent = new Intent();
		switch (item.getItemId()) {
		case R.id.more:
			// ICampus.this.showPopWindow();
			break;
		case R.id.login:
			intent.setClass(ICampus.this, Oauth.class);
			startActivityForResult(intent, OATUTH_LOGIN);
			break;
		case R.id.about:
			intent.setClass(ICampus.this, About.class);
			startActivity(intent);
			break;
		case R.id.update:
			isButtonCheck = true;
			(new UpdateAsynctask()).execute();
			break;
		case R.id.personal:
			ACache aCache = ACache.get(ICampus.this);
			Person person = (Person) aCache.getAsObject("user");
			if(person == null){
				Toast.makeText(ICampus.this, "您还没有登录！",Toast.LENGTH_LONG).show();
				intent.setClass(ICampus.this, Oauth.class);
				startActivity(intent);
				break;
			}
			intent.putExtra("user", person);
			intent.setClass(ICampus.this, PersonShow.class);
			startActivity(intent);
			break;
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
					// 判断是不是用户检查更新
					if (isButtonCheck) {
						Toast.makeText(ICampus.this, "当前已经是最新版本",
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
