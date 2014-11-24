package cn.edu.bistu.yellowPage;

import java.util.List;

import com.example.icampus2_2.R;

import cn.edu.bistu.yellowPageData.YelloPagePersonal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactPersonAdapter extends BaseAdapter{
	private Context context;
	private List<YelloPagePersonal> list;
	public ContactPersonAdapter(Context context, List<YelloPagePersonal> list) {
		super();
		this.context = context;
		this.list = list;
	}
	public ContactPersonAdapter() {
		super();
		// TODO Auto-generated constructor stub
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
			viewHolder = new ViewHolder();
			arg1 = LayoutInflater.from(context).inflate(R.layout.contact_person_item, null);
			viewHolder.textView1 = (TextView)arg1.findViewById(R.id.telName);
			viewHolder.textView2 = (TextView)arg1.findViewById(R.id.telNum);
			arg1.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)arg1.getTag();
		}
		viewHolder.textView1.setText(list.get(arg0).getName());
		viewHolder.textView2.setText(list.get(arg0).getTelnum());
		return arg1;
	}
	class ViewHolder{
		TextView textView1;
		TextView textView2;
	}
}
