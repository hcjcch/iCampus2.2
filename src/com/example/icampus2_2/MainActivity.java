package com.example.icampus2_2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.bistu.newsdata.JsonNewschannel;
import cn.edu.bistu.newsdata.NewschannelType;

import com.viewpagerindicator.TabPageIndicator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

public class MainActivity extends FragmentActivity {

	private ImageButton refresh;
	final static String[] NEWSCHANNEL = { "学校要闻", "人才培养", "教学科研", "文化活动",
			"媒体关注", "校园人物" };

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		refresh = (ImageButton) findViewById(R.id.refresh);
		final Animation animation = AnimationUtils.loadAnimation(this,
				R.anim.refresh);
		refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				refresh.setVisibility(View.GONE);
				refresh.setAnimation(animation);
				refresh.setVisibility(View.VISIBLE);
			}
		});
		FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(
				getSupportFragmentManager(), initChannelData());
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);
		TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);
	}

	private Map<String, String> initChannelData() {
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
	}

}
