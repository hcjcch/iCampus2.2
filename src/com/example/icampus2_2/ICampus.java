package com.example.icampus2_2;

import cn.edu.bistu.map.BistuMap;
import cn.edu.bistu.school.SchoolShow;
import cn.edu.bistu.yellowPage.YellowPageShow;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ICampus extends Activity {
	private Button news;
	private Button school;
	private Button yellowPage;
	private Button map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
	}

	private void init() {
		map = (Button) findViewById(R.id.map);
		news = (Button) findViewById(R.id.newsButton);
		school = (Button) findViewById(R.id.schoolButton);
		yellowPage = (Button) findViewById(R.id.yellowPageButton);
		news.setOnClickListener(new Click());
		school.setOnClickListener(new Click());
		yellowPage.setOnClickListener(new Click());
		map.setOnClickListener(new Click());
	}

	class Click implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.newsButton:
				intent.setClass(ICampus.this, MainActivity.class);
				break;
			case R.id.schoolButton:
				intent.setClass(ICampus.this, SchoolShow.class);
				break;
			case R.id.yellowPageButton:
				intent.setClass(ICampus.this, YellowPageShow.class);
				break;
			case R.id.map:
				intent.setClass(ICampus.this, BistuMap.class);
				break;
			default:
				break;
			}
			startActivity(intent);
		}

	}
}
