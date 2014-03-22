package com.example.icampus2_2;

import org.apache.http.Header;

import cn.edu.bistu.newsdata.DetailNewsType;
import cn.edu.bistu.newsdata.JsonNewsDetail;
import cn.edu.bistu.tools.MyProgressDialog;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

@SuppressLint("NewApi")
public class NewsDetail extends Activity {
	private TextView title;
	private TextView contents;
	private String url;
	private String tititle;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_news);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.show();
		init();
		actionBar.setTitle(tititle);
		show();
	}

	public void init() {
		Intent intent = getIntent();
		url = intent
				.getStringExtra("detailUrl");
		tititle = intent.getStringExtra("tititle");
		title = (TextView) findViewById(R.id.detailTitle);
		contents = (TextView) findViewById(R.id.detail);
	}

	private void show() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://api.bistu.edu.cn/api/api.php?table=news&url=/"
				+ url, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1, arg2);
				DetailNewsType detail = (new JsonNewsDetail())
						.getDetailNews(new String(arg2));
				title.setText(detail.getDoctitle());
				contents.setText(detail.getDochtmlcon());
			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
