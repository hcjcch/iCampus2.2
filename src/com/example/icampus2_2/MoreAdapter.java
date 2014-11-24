package com.example.icampus2_2;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MoreAdapter extends BaseAdapter{

	private List<String> list;
	private Context context;
	
	public MoreAdapter(List<String> list, Context context) {
		// TODO Auto-generated constructor stub
		this.list = list;
		this.context = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		arg1 = LayoutInflater.from(context).inflate(R.layout.more_item, null);
		ImageView imageView = (ImageView)arg1.findViewById(R.id.more_item_imageview);
		TextView textView = (TextView)arg1.findViewById(R.id.more_item_text);
		imageView.setImageResource(R.drawable.ic_action_about);
		textView.setText(list.get(arg0));
		return arg1;
	}
	
}
