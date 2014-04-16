package cn.edu.bistu.wifi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.message.BasicNameValuePair;

import cn.edu.bistu.tools.MyProgressDialog;

import com.example.icampus2_2.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Logout extends Activity {
	private Button logout;
	private Intent intent;
	private Handler handler;
	private List<NameValuePair> params;
	private ProgressBar bar;
	private TextView textView;

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logout);
		params = new ArrayList<NameValuePair>();
		intent = getIntent();
		logout = (Button) findViewById(R.id.logout);
		bar = (ProgressBar)findViewById(R.id.progressBar2);
		textView = (TextView)findViewById(R.id.logouttext);
		bar.setVisibility(View.GONE);
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				StatusFile statusFile = new StatusFile(Logout.this);
				switch (msg.what) {
				case 0:
					Toast.makeText(Logout.this, "当前无网络连接", Toast.LENGTH_SHORT).show();
					statusFile.writeStatus(0);
					finish();
					break;
				case 1:
					textView.setText("正在登出bistu");
					(new LogOutAsyncTask())
					.execute("https://6.6.6.6/logout.html");
					break;
				case 2:
					Toast.makeText(Logout.this, "当前已经连接"+msg.obj+"  wifi"+"没有连接到bistu wifi", Toast.LENGTH_SHORT).show();
					statusFile.writeStatus(0);
					finish();
					break;
				default:
					break;
				}
				super.handleMessage(msg);
			}
		};
		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				logout.setEnabled(false);
				bar.setVisibility(View.VISIBLE);
				params.add(new BasicNameValuePair("logout", "logout"));
				params.add(new BasicNameValuePair("userStatus", "1"));
				WifiAdmin wifiAdmin = new WifiAdmin(Logout.this, handler);
				wifiAdmin.logOut();
			}
		});
	}

	class LogOutAsyncTask extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			MyHttpClient httpClient = new MyHttpClient();
			try {
				return httpClient.webServiceLogin(arg0[0], params);
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
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			StatusFile statusFile = new StatusFile(Logout.this);
			statusFile.writeStatus(0);
			if (result == null || result.equals("")) {
				intent.putExtra("status", 6);
				setResult(RESULT_OK, intent);
				finish();
			} else {
				intent.putExtra("sastus", 0);
				setResult(RESULT_OK, intent);
				finish();
			}
			super.onPostExecute(result);
		}
	}
}