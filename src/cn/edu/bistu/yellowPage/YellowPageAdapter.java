package cn.edu.bistu.yellowPage;

import java.util.List;

import com.example.icampus2_2.R;

import cn.edu.bistu.yellowPageData.YelloPage;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class YellowPageAdapter extends BaseAdapter{
	private Context context;
	private List<YelloPage> list;
	public YellowPageAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}
	public YellowPageAdapter(Context context, List<YelloPage> list) {
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
		viewHodler.textView.setText(list.get(arg0).getName());
		return arg1;
	}
	class ViewHodler{
		private TextView textView;
	}
}
