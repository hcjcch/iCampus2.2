package cn.edu.bistu.bistujob;

import java.util.List;

import com.example.icampus2_2.R;

import cn.edu.bistu.bistujobData.JobType;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SortAdapter extends BaseAdapter{

	private Context context;
	private List<JobType> list;
	public SortAdapter(List<JobType> list,Context context) {
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
		LayoutInflater inflater = LayoutInflater.from(context);
		arg1 = inflater.inflate(R.layout.sort_item, null);
		TextView textView = (TextView)arg1.findViewById(R.id.item);
		textView.setText(list.get(arg0).getType());
		return arg1;
	}
	
}
