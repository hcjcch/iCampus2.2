package com.example.icampus2_2;

import com.amap.api.location.a;

import cn.edu.bistu.about.About;
import cn.edu.bistu.bus.BusShow;
import cn.edu.bistu.map.BistuMap;
import cn.edu.bistu.school.SchoolShow;
import cn.edu.bistu.yellowPage.YellowPageShow;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ICampus extends Activity {
	private ImageView news;
	private ImageView school;
	private ImageView yellowPage;
	private ImageView map;
	private ImageView bus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
	}

	private void init() {
		map = (ImageView) findViewById(R.id.imageView6);
		news = (ImageView) findViewById(R.id.imageView5);
		school = (ImageView) findViewById(R.id.imageView2);
		yellowPage = (ImageView) findViewById(R.id.imageView3);
		bus = (ImageView) findViewById(R.id.imageView4);
		news.setOnClickListener(new Click());
		school.setOnClickListener(new Click());
		yellowPage.setOnClickListener(new Click());
		map.setOnClickListener(new Click());
		bus.setOnClickListener(new Click());
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
			default:
				break;
			}
			startActivity(intent);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		 MenuInflater inflater = getMenuInflater();  
	        inflater.inflate(R.menu.icampus, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.more:
			Intent intent = new Intent();
			intent.setClass(ICampus.this, About.class);
			startActivity(intent);
			/*Update aUpdate = new Update(ICampus.this);
			aUpdate.checkUpdateInfo();*/
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
