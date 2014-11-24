package cn.edu.bistu.about;

import com.example.icampus2_2.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AboutAdapter extends BaseAdapter {

	private String[] strings;
	private Context context;
	
	public AboutAdapter(Context context,String[] strings) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.strings = strings;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return strings.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return strings[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		arg1 = LayoutInflater.from(context).inflate(R.layout.about_adapter, null);
		TextView textView = (TextView)arg1.findViewById(R.id.aboutItem);
		textView.setText(strings[arg0]);
		return arg1;
	}

}
