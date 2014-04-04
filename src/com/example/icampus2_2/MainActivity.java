package com.example.icampus2_2;

import java.util.HashMap;
import java.util.Map;

import com.viewpagerindicator.TabPageIndicator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends FragmentActivity {

	final static String[] NEWSCHANNEL = { "ѧУҪ��", "�˲�����", "��ѧ����", "�Ļ��",
			"ý���ע", "У԰����" };
	final Map<String, String> map = new HashMap<String, String>();
	private Button button;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		map.put("ѧУҪ��", "/xw/zhxw");
		map.put("�˲�����", "/xw/rcpy");
		map.put("��ѧ����", "/xw/jxky");
		map.put("�Ļ��", "/xw/whhd");
		map.put("ý���ע", "/xw/mtgz");
		map.put("У԰����", "/xw/xyrw");
		button = (Button) findViewById(R.id.backMain);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, ICampus.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
		FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(
				getSupportFragmentManager(), map);
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);
		TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);
	}

	/*private Map<String, String> initChannelData() {
		JsonNewschannel jsonNewschannel = new JsonNewschannel();
		String channelInformation = jsonNewschannel
				.getNewsChannel("http://api.bistu.edu.cn/api/api.php?table=newschannel");
		List<NewschannelType> lists = jsonNewschannel
				.getList(channelInformation);
		Map<String, String> map = new HashMap<String, String>();
		for (NewschannelType newschannelType : lists) {
			map.put(newschannelType.getTitle(), newschannelType.getListUrl());
		}
		return map;
	}*/
}
