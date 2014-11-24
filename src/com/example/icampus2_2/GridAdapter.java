package com.example.icampus2_2;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {

	private List<Item> items;
	private Context context;

	public GridAdapter(List<Item> items, Context context) {
		super();
		this.items = items;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return items.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		arg1 = LayoutInflater.from(context).inflate(R.layout.icampus_grid_item,
				null);
		ImageView imageView = (ImageView) arg1.findViewById(R.id.grid_image);
		TextView textView = (TextView) arg1.findViewById(R.id.grid_text);
		imageView.setImageResource(items.get(arg0).getImageId());
		textView.setText(items.get(arg0).getText());
		return arg1;
	}

}
