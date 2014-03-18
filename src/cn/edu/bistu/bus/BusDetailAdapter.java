package cn.edu.bistu.bus;

import java.util.ArrayList;

import com.example.icampus2_2.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BusDetailAdapter extends BaseAdapter{
	private ArrayList<String> station;
	private ArrayList<String> time;
	private Context context;
	
	public BusDetailAdapter(ArrayList<String> station, ArrayList<String> time,
			Context context) {
		super();
		this.station = station;
		this.time = time;
		this.context = context;
	}
	public BusDetailAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return station.size();
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return station.get(arg0);
	}
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		arg1 = LayoutInflater.from(context).inflate(R.layout.bus_detail_item, null);
		TextView time = (TextView)arg1.findViewById(R.id.time);
		TextView station = (TextView)arg1.findViewById(R.id.station);
		String timeString = this.time.get(arg0);
		if (timeString != null) {
			time.setText(this.time.get(arg0));
		}else {
			time.setText("     ");
		}
		station.setText(this.station.get(arg0));
		return arg1;
	}
	
}
