package com.example.icampus2_2;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {

	private Context context;
	private List<Item> menusItems;

	public MenuAdapter(Context context, List<Item> menusItems) {
		super();
		this.context = context;
		this.menusItems = menusItems;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return menusItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return menusItems.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		arg1 = layoutInflater.inflate(R.layout.more_item, null);
		ImageView menuImageView = (ImageView) arg1
				.findViewById(R.id.more_item_imageview);
		TextView textView = (TextView) arg1.findViewById(R.id.more_item_text);
		menuImageView.setImageResource(menusItems.get(arg0).getImageId());
		textView.setText(menusItems.get(arg0).getText());
		return arg1;
	}

}
