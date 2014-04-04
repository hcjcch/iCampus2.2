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

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Logout extends Activity {
	private Button logout;
	private Intent intent;
	private MyProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logout);
		progressDialog = new MyProgressDialog(Logout.this, "正在登出", "请稍后...", false);
		intent = getIntent();
		logout = (Button) findViewById(R.id.logout);
		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("logout", "logout"));
				params.add(new BasicNameValuePair("userStatus", "1"));
				(new LogOutAsyncTask(params))
						.execute("https://6.6.6.6/logout.html");
			}
		});
	}

	class LogOutAsyncTask extends AsyncTask<String, Integer, String> {
		private List<NameValuePair> params;

		public LogOutAsyncTask(List<NameValuePair> params) {
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
				progressDialog.hide();
				finish();
			}else {
				intent.putExtra("sastus", 0);
				setResult(RESULT_OK,intent);
				progressDialog.hide();
				finish();
			}
			super.onPostExecute(result);
		}
	}
}