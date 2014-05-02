package cn.edu.bistu.bistujob;

import java.util.List;

import com.example.icampus2_2.R;


import cn.edu.bistu.bistujobData.JobListType;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FragmentAdapter extends BaseAdapter{

	private List<JobListType> list;
	private Context context;
	
	public FragmentAdapter(List<JobListType> list, Context context) {
		super();
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
		ViewHolder viewHolder;
		if (arg1 == null) {
			arg1 = LayoutInflater.from(context).inflate(R.layout.job_item, null);
			viewHolder = new ViewHolder();
			viewHolder.title = (TextView)arg1.findViewById(R.id.title);
			viewHolder.location = (TextView)arg1.findViewById(R.id.location);
			viewHolder.salary = (TextView)arg1.findViewById(R.id.salary);
			viewHolder.time = (TextView)arg1.findViewById(R.id.time);
			arg1.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)arg1.getTag();
		}
		viewHolder.title.setText(list.get(arg0).getTitle());
		viewHolder.location.setText(list.get(arg0).getLocation());
		viewHolder.salary.setText(list.get(arg0).getSalary());
		viewHolder.time.setText(list.get(arg0).getTime());
		return arg1;
	}
	
	class ViewHolder{
		TextView title;
		TextView location;
		TextView salary;
		TextView time;
	}
}
