package cn.edu.bistu.wifi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.message.BasicNameValuePair;

import com.example.icampus2_2.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
	private EditText number;
	private EditText password;
	private Button login;
	private Intent intent;
	private SharedPreferences sharedPreferences;
	private CheckBox rememberBox;
	private Handler handler;
	List<NameValuePair> params = new ArrayList<NameValuePair>();
	private TextView textView;
	private int status = 0;
	private ProgressBar bar;
	private CheckBox lookPasswd;

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		params = new ArrayList<NameValuePair>();
		intent = getIntent();
		sharedPreferences = this.getSharedPreferences("wifiInfo",
				Context.MODE_PRIVATE);
		number = (EditText) findViewById(R.id.number);
		password = (EditText) findViewById(R.id.passwd);
		login = (Button) findViewById(R.id.login);
		rememberBox = (CheckBox) findViewById(R.id.rememberPasswd);
		textView = (TextView) findViewById(R.id.jindu);
		bar = (ProgressBar) findViewById(R.id.progressBar1);
		lookPasswd = (CheckBox) findViewById(R.id.lookPasswd);
		bar.setVisibility(View.GONE);
		if (sharedPreferences.getBoolean("remember", false)) {
			number.setText(sharedPreferences.getString("name", ""));
			password.setText(sharedPreferences.getString("passwd", ""));
			rememberBox.setChecked(true);
		}
		lookPasswd.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (lookPasswd.isChecked()) {
					password.setTransformationMethod(HideReturnsTransformationMethod
							.getInstance());
				} else {
					password.setTransformationMethod(PasswordTransformationMethod
							.getInstance());
				}
			}
		});
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				// System.out.println(msg.what);
				switch (msg.what) {
				case -1:
					textView.setText("���ڵ�¼bistu wifi��");
					status = 1;
					(new MyAs()).execute("https://1.1.1.1/login.html");
					break;
				case 0:
					textView.setText("���ڴ�wifi��");
					break;
				case 1:
					Toast.makeText(Login.this, "wifi��ʧ��!", Toast.LENGTH_SHORT)
							.show();
					finish();
					break;
				case 2:
					textView.setText("��������bistu wifi��");
					break;
				case 3:
					Toast.makeText(Login.this, "wifi�б�����bistu����!",
							Toast.LENGTH_SHORT).show();
					finish();
					break;
				case 5:
					textView.setText("���ڵ�¼bistu wifi��");
					status = 0;
					(new MyAs()).execute("https://6.6.6.6/login.html");
					break;
				case 6:
					Toast.makeText(Login.this, "wifi����ʧ��!", Toast.LENGTH_SHORT)
							.show();
					finish();
					break;
				case 7:
					Toast.makeText(Login.this, "������bistu wifi!�����ȷ�Ÿ�����bistu wifi���ٴε����",
							Toast.LENGTH_LONG).show();
					finish();
					break;
				default:
					break;
				}
				super.handleMessage(msg);
			}
		};
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				bar.setVisibility(View.VISIBLE);
				number.setEnabled(false);
				password.setEnabled(false);
				rememberBox.setEnabled(false);
				login.setEnabled(false);
				lookPasswd.setEnabled(false);
				String name = number.getText().toString();
				String passwd = password.getText().toString();
				if (name.equals("")) {
					Toast.makeText(Login.this, "�������û���", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (passwd.equals("")) {
					Toast.makeText(Login.this, "����������", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				params.add(new BasicNameValuePair("redirect_url",
						"http://www.baidu.com"));
				params.add(new BasicNameValuePair("buttonClicked", "4"));
				params.add(new BasicNameValuePair("username", name));
				params.add(new BasicNameValuePair("password", passwd));// ���ݲ���
				boolean remember = rememberBox.isChecked();
				if (remember) {
					Editor editor = sharedPreferences.edit();
					editor.putString("name", name);
					editor.putString("passwd", passwd);
					editor.putBoolean("remember", true);
					editor.commit();
				} else {
					Editor editor = sharedPreferences.edit();
					editor.putString("name", "");
					editor.putString("passwd", "");
					editor.putBoolean("remember", false);
					editor.commit();
				}
				WifiAdmin wifiAdmin = new WifiAdmin(Login.this, handler);
				wifiAdmin.connect();
			}
		});
	}

	class MyAs extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			if (status == 0) {
				while(!isWifiConnect()){
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			MyHttpClient httpClient = new MyHttpClient();
			try {
				String result = httpClient.webServiceLogin(arg0[0], params);
				return result;
			} catch (ConnectTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return "faile";
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			// System.out.println(result);
			if (result.equals("faile")) {
				intent.putExtra("status", 6);
				setResult(RESULT_OK, intent);
				finish();
			}
			Pattern pattern = Pattern.compile("Login Successful");
			Matcher matcher = pattern.matcher(result);
			if (matcher.find()) {
				StatusFile status = new StatusFile(Login.this);
				status.writeStatus(1);
				intent.putExtra("status", 0);
				setResult(RESULT_OK, intent);
				finish();
			}
			int a = result.indexOf("statusCode=");
			if (a != -1) {
				a = a + 11;
				String number = result.substring(a, a + 1);
				a = Integer.parseInt(number);
				intent.putExtra("status", a);
				setResult(RESULT_OK, intent);
				finish();
			}

			super.onPostExecute(result);
		}
	}

	public boolean isWifiConnect() {
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return mWifi.isConnected();
	}
}