package com.example.icampus2_2;

import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabPageIndicatorAdapter extends FragmentPagerAdapter {
	Map<String, String> map;
	public TabPageIndicatorAdapter(FragmentManager fm) {
		// TODO Auto-generated constructor stub
		super(fm);
	}
	public TabPageIndicatorAdapter(FragmentManager fm,Map<String, String> map) {
		// TODO Auto-generated constructor stub
		super(fm);
		this.map = map;
	}
	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		Fragment fragment = new NewsItem();
		Bundle bundle = new Bundle();
		bundle.putString("bundle", MainActivity.NEWSCHANNEL[arg0]);
		bundle.putString("url", map.get(MainActivity.NEWSCHANNEL[arg0]));
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return MainActivity.NEWSCHANNEL[position];
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return MainActivity.NEWSCHANNEL.length;
	}
}
