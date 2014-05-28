package com.example.personal;

import org.apache.http.Header;

import cn.edu.bistu.tools.MyProgressDialog;

import com.example.groupdata.JsonMessage;
import com.example.groupdata.Messages;
import com.example.icampus2_2.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class PersonShow extends Activity {
	private String num;
	private String nam;
	private String type;
	private MyProgressDialog progressDialog;
	private Messages messages;
	TextView numView;
	TextView namView;
	TextView typeView;
	TextView qqView;
	TextView wechatView;
	TextView mobileView;
	TextView emailView;
	private static final int CHANG_PERSON_INFORMATION = 1;
	private AsyncHttpClient client;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personshow);
		this.setTitle("个人信息");
		Intent intent = getIntent();
		Person person = (Person) intent.getSerializableExtra("user");
		num = person.getUserid();
		nam = person.getUsername();
		type = person.getIdtype();
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.show();
		progressDialog = new MyProgressDialog(this, "正在加载中", "请稍后...", false);
		numView = (TextView) findViewById(R.id.num);
		namView = (TextView) findViewById(R.id.name);
		typeView = (TextView) findViewById(R.id.type);
		qqView = (TextView) findViewById(R.id.qq);
		wechatView = (TextView) findViewById(R.id.wechat);
		mobileView = (TextView) findViewById(R.id.mobile);
		emailView = (TextView) findViewById(R.id.email);
		client = new AsyncHttpClient();
		client.get("http://m.bistu.edu.cn/newapi/userinfo.php?userid=" + num,
				new AsyncHttpResponseHandler() {
					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1, arg2, arg3);
					}

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
						progressDialog.show();
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1, arg2);
						String information = new String(arg2);
						JsonMessage jsonMessage = new JsonMessage();
						messages = jsonMessage.getMess(information);
						numView.setText(num);
						namView.setText(nam);
						typeView.setText(type);
						qqView.setText(messages.getQq());
						wechatView.setText(messages.getWechat());
						mobileView.setText(messages.getMobile());
						emailView.setText(messages.getEmail());
						progressDialog.hideAndCancle();
					}

				});
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.person, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.goperson:
			Intent intent = new Intent();
			intent.putExtra("num", num);
			intent.putExtra("nam", nam);
			intent.putExtra("type", type);
			intent.putExtra("qq", messages.getQq());
			intent.putExtra("wechat", messages.getWechat());
			intent.putExtra("mobile", messages.getMobile());
			intent.putExtra("email", messages.getEmail());
			intent.setClass(PersonShow.this, MessagePost.class);
			startActivityForResult(intent, CHANG_PERSON_INFORMATION);
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case CHANG_PERSON_INFORMATION:
			if (resultCode == RESULT_OK) {
				String changInformation = data
						.getStringExtra("informationChange");
				if (changInformation.equals("1")) {
					Toast.makeText(PersonShow.this, "修改成功！", Toast.LENGTH_SHORT)
							.show();
					client.get(
							"http://m.bistu.edu.cn/newapi/userinfo.php?userid="
									+ num, new AsyncHttpResponseHandler() {
								@Override
								public void onFailure(int arg0, Header[] arg1,
										byte[] arg2, Throwable arg3) {
									// TODO Auto-generated method stub
									super.onFailure(arg0, arg1, arg2, arg3);
								}

								@Override
								public void onStart() {
									// TODO Auto-generated method stub
									super.onStart();
									progressDialog.show();
								}

								@Override
								public void onSuccess(int arg0, Header[] arg1,
										byte[] arg2) {
									// TODO Auto-generated method stub
									super.onSuccess(arg0, arg1, arg2);
									String information = new String(arg2);
									JsonMessage jsonMessage = new JsonMessage();
									messages = jsonMessage.getMess(information);
									numView.setText(num);
									namView.setText(nam);
									typeView.setText(type);
									qqView.setText(messages.getQq());
									wechatView.setText(messages.getWechat());
									mobileView.setText(messages.getMobile());
									emailView.setText(messages.getEmail());
									progressDialog.hideAndCancle();
								}

							});
				} else {
					Toast.makeText(PersonShow.this, "修改失败！", Toast.LENGTH_SHORT)
							.show();
				}
			}
			break;

		default:
			break;
		}
	}
}
