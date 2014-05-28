package com.example.personal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import cn.edu.bistu.tools.NetworkLoad;

import com.example.icampus2_2.R;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MessagePost extends Activity {
	private EditText qqText;
	private EditText wechatText;
	private EditText mobileText;
	private EditText emailText;
	private String num;
	private String nam;
	private String type;
	private String qq;
	private String wechat;
	private String mobile;
	private String email;
	TextView numView;
	TextView namView;
	TextView typeView;
	private Intent intent;
	private Map<String, String> map;
	private String informationUrl = "http://m.bistu.edu.cn/newapi/userinfomod.php";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		map = new HashMap<String, String>();
		setContentView(R.layout.messagepost);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.show();
		intent = getIntent();
		num = intent.getStringExtra("num");
		nam = intent.getStringExtra("nam");
		type = intent.getStringExtra("type");
		qq = intent.getStringExtra("qq");
		wechat = intent.getStringExtra("wechat");
		mobile = intent.getStringExtra("mobile");
		email = intent.getStringExtra("email");
		numView = (TextView) findViewById(R.id.num);
		namView = (TextView) findViewById(R.id.name);
		typeView = (TextView) findViewById(R.id.type);
		qqText = (EditText) findViewById(R.id.qq);
		wechatText = (EditText) findViewById(R.id.wechat);
		mobileText = (EditText) findViewById(R.id.mobile);
		emailText = (EditText) findViewById(R.id.email);
		numView.setText(num);
		namView.setText(nam);
		typeView.setText(type);
		qqText.setText(qq);
		wechatText.setText(wechat);
		mobileText.setText(mobile);
		emailText.setText(email);

	}

	public Map<String, String> getMap() {
		map.put("userid", numView.getText().toString());
		map.put("qq", qqText.getText().toString());
		map.put("wechat", wechatText.getText().toString());
		map.put("mobile", mobileText.getText().toString());
		map.put("email", emailText.getText().toString());
		return map;
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.job_publish_over, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.job_publish_over:
			(new MyAsynctask()).execute();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	class MyAsynctask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result = "";
			try {
				map = getMap();
				result = (new NetworkLoad()).PostGetInformation(informationUrl,
						map);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			intent.putExtra("informationChange", result);
			MessagePost.this.setResult(RESULT_OK, intent);
			MessagePost.this.finish();
			super.onPostExecute(result);
		}
	}
}
