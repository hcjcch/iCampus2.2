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

import cn.edu.bistu.tools.MyProgressDialog;

import com.example.icampus2_2.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	private EditText number;
	private EditText password;
	private Button login;
	private Intent intent;
	private SharedPreferences sharedPreferences;
	private CheckBox rememberBox;
	private MyProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		intent = getIntent();
		progressDialog = new MyProgressDialog(Login.this,"正在登录","请稍后...",false);
		sharedPreferences = this.getSharedPreferences("wifiInfo", Context.MODE_PRIVATE);
		number = (EditText) findViewById(R.id.number);
		password = (EditText) findViewById(R.id.passwd);
		login = (Button) findViewById(R.id.login);
		rememberBox = (CheckBox)findViewById(R.id.rememberPasswd);
		if (sharedPreferences.getBoolean("remember", false)) {
			number.setText(sharedPreferences.getString("name", ""));
			password.setText(sharedPreferences.getString("passwd", ""));
			rememberBox.setChecked(true);
		}
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String name = number.getText().toString();
				String passwd = password.getText().toString();
				if (name.equals("")) {
					Toast.makeText(Login.this, "请输入用户名", Toast.LENGTH_SHORT).show();
					return;
				}
				if (passwd.equals("")) {
					Toast.makeText(Login.this, "请输入密码", Toast.LENGTH_SHORT).show();
					return;
				}
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("redirect_url",
						"http://www.baidu.com"));
				params.add(new BasicNameValuePair("buttonClicked", "4"));
				params.add(new BasicNameValuePair("username", name));
				params.add(new BasicNameValuePair("password", passwd));// 传递参数
				boolean remember = rememberBox.isChecked();
				if (remember) {
					Editor editor = sharedPreferences.edit();
					editor.putString("name", name);
					editor.putString("passwd", passwd);
					editor.putBoolean("remember", true);
					editor.commit();
				}else {
					Editor editor = sharedPreferences.edit();
					editor.putString("name", "");
					editor.putString("passwd", "");
					editor.putBoolean("remember", false);
					editor.commit();
				}
				(new MyAs(params)).execute("https://6.6.6.6/login.html");
			}
		});
	}

	class MyAs extends AsyncTask<String, Integer, String> {
		private List<NameValuePair> params;

		public MyAs(List<NameValuePair> params) {
			// TODO Auto-generated constructor stub
			this.params = params;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			progressDialog.show();
			super.onPreExecute();
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
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
			if (result.equals("faile")) {
				intent.putExtra("status", 6);
				setResult(RESULT_OK, intent);
				progressDialog.cancel();
				finish();
			}
			Pattern pattern = Pattern.compile("Login Successful");
			Matcher matcher = pattern.matcher(result);
			if (matcher.find()) {
				StatusFile status = new StatusFile(Login.this);
				status.writeStatus(1);
				intent.putExtra("status", 0);
				setResult(RESULT_OK, intent);
				progressDialog.cancel();
				finish();
			}
			int a = result.indexOf("statusCode=");
			if (a != -1) {
				a = a + 11;
				String number = result.substring(a, a + 1);
				a = Integer.parseInt(number);
				intent.putExtra("status", a);
				setResult(RESULT_OK, intent);
				progressDialog.cancel();
				finish();
			}
			super.onPostExecute(result);
		}
	}
}