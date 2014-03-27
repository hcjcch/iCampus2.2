package com.example.icampus2_2;

import cn.edu.bistu.about.About;
import cn.edu.bistu.bus.BusShow;
import cn.edu.bistu.map.BistuMap;
import cn.edu.bistu.school.SchoolShow;
import cn.edu.bistu.yellowPage.YellowPageShow;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ICampus extends Activity {
	private ImageView news;
	private ImageView school;
	private ImageView yellowPage;
	private ImageView map;
	private ImageView bus;
	private ImageView about;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		init();
	}

	private void init() {
		map = (ImageView) findViewById(R.id.imageView6);
		news = (ImageView) findViewById(R.id.imageView5);
		school = (ImageView) findViewById(R.id.imageView2);
		yellowPage = (ImageView) findViewById(R.id.imageView3);
		bus = (ImageView) findViewById(R.id.imageView4);
		about = (ImageView) findViewById(R.id.imageView1);
		news.setOnClickListener(new Click());
		school.setOnClickListener(new Click());
		yellowPage.setOnClickListener(new Click());
		map.setOnClickListener(new Click());
		bus.setOnClickListener(new Click());
		about.setOnClickListener(new Click());
	}

	class Click implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.imageView5:
				intent.setClass(ICampus.this, MainActivity.class);
				break;
			case R.id.imageView2:
				System.out.println(school.getWidth());
				System.out.println(school.getHeight());
				intent.setClass(ICampus.this, SchoolShow.class);
				break;
			case R.id.imageView3:
				intent.setClass(ICampus.this, YellowPageShow.class);
				break;
			case R.id.imageView6:
				intent.setClass(ICampus.this, BistuMap.class);
				break;
			case R.id.imageView4:
				intent.setClass(ICampus.this, BusShow.class);
				break;
			case R.id.imageView1:
				intent.setClass(ICampus.this, About.class);
			default:
				break;
			}
			startActivity(intent);
		}

	}
}
