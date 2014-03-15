package com.example.icampus2_2;

import java.util.ArrayList;

import org.apache.http.Header;

import cn.edu.bistu.newsdata.DetailNewsType;
import cn.edu.bistu.newsdata.JsonNewsDetail;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class NewsDetail extends Activity {
	private TextView title;
	private TextView contents;
	private ImageButton rightButton;
	private ImageButton leftButton;
	private int position;
	private int totalNews;
	private ArrayList<String> urls;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail_news);
		init();
		show(position);
	}

	@SuppressWarnings("unchecked")
	public void init() {
		Intent intent = getIntent();
		urls = (ArrayList<String>) intent
				.getSerializableExtra("detailUrls");
		position = intent.getIntExtra("position", 0);
		totalNews = urls.size();
		title = (TextView) findViewById(R.id.detailTitle);
		contents = (TextView) findViewById(R.id.detail);
		rightButton = (ImageButton) findViewById(R.id.newsRightButton);
		leftButton = (ImageButton) findViewById(R.id.newsLeftButton);
		rightButton.setOnClickListener(new RightLeftClickListener());
		leftButton.setOnClickListener(new RightLeftClickListener());
	}

	private void show(int currentPage) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://api.bistu.edu.cn/api/api.php?table=news&url=/"
				+ urls.get(currentPage), new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1, arg2);
				DetailNewsType detail = (new JsonNewsDetail())
						.getDetailNews(new String(arg2));
				title.setText(detail.getDoctitle());
				contents.setText(detail.getDochtmlcon());
				try {
					System.out.println(detail.getDetailResources());
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
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

	class RightLeftClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.newsLeftButton:
				if (position > 0 ) {
					show(--position);
					rightButton.setEnabled(true);
				}
				if (position == 0) {
					leftButton.setEnabled(false);
				}
				break;
			case R.id.newsRightButton:
				if (position<totalNews-1) {
					show(++position);
					leftButton.setEnabled(true);
				}
				if (position == totalNews-1) {
					rightButton.setEnabled(false);
				}
				break;
			default:
				Toast.makeText(NewsDetail.this, "NO Data", Toast.LENGTH_SHORT).show();
				break;
			}
		}

	}
}
