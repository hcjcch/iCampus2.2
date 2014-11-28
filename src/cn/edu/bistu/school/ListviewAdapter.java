package cn.edu.bistu.school;

import java.util.List;

import com.example.icampus2_2.R;

import cn.edu.bistu.schoolData.School;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListviewAdapter extends BaseAdapter {
	private Context context;
	private List<School> list;
	public ListviewAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ListviewAdapter(Context context, List<School> list) {
		super();
		this.context = context;
		this.list = list;
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
		ViewHodler viewHodler;
		if (arg1 == null) {
			viewHodler = new ViewHodler();
			arg1 = LayoutInflater.from(context).inflate(R.layout.collegel_item, null);
			viewHodler.textView = (TextView)arg1.findViewById(R.id.schoolItem);
			arg1.setTag(viewHodler);
		}else {
			viewHodler = (ViewHodler)arg1.getTag();
		}
		viewHodler.textView.setText(list.get(arg0).getIntroName());
		return arg1;
	}
	
	class ViewHodler{
		private TextView textView;
	}
}
