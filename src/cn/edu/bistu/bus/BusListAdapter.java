package cn.edu.bistu.bus;

import java.util.ArrayList;
import java.util.List;

import com.example.icampus2_2.R;

import cn.edu.bistu.busData.Bus;
import cn.edu.bistu.busData.CatBus;
import cn.edu.bistu.busData.EveryBus;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BusListAdapter extends BaseAdapter {
	private Context context;
	private CatBus tongQingbus;
	private CatBus jiaoXueBus;
	private ArrayList<Object> bus;
	private static final int TITLE_ITEM = 0;
	private static final int BUS_LINE = 1;
	private ListView listView;

	public BusListAdapter(final Context context, List<Bus> list,ListView listView) {
		super();
		this.context = context;
		tongQingbus = list.get(0).getCatbus();
		jiaoXueBus = list.get(1).getCatbus();
		this.listView = listView;
		bus = new ArrayList<Object>();
		bus.add("通勤班车");
		for (EveryBus everyBus : tongQingbus.getCatbus()) {
			bus.add(everyBus);
		}
		bus.add("教学班车");
		for (EveryBus everyBus : jiaoXueBus.getCatbus()) {
			bus.add(everyBus);
		}
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				System.out.println(arg2);
				intent.setClass(context,BusDetail.class);
				context.startActivity(intent);
			}
		});
	}

	public BusListAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		if (bus.get(position) instanceof EveryBus) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return bus.get(position) instanceof EveryBus;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bus.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return bus.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (getItemViewType(arg0) == TITLE_ITEM) {
			arg1 = LayoutInflater.from(context).inflate(
					R.layout.bus_special_item, null);
			TextView textView = (TextView) arg1.findViewById(R.id.textView1);
			textView.setText((String) bus.get(arg0));
		} else {
			BusItem busItem = null;
			if (arg1 == null) {
				busItem = new BusItem();
				arg1 = LayoutInflater.from(context)
						.inflate(R.layout.bus_item, null);
				busItem.line1 = (TextView)arg1.findViewById(R.id.line1);
				busItem.line2 = (TextView)arg1.findViewById(R.id.line2);
				busItem.goTimeTextView = (TextView)arg1.findViewById(R.id.goTime);
				busItem.backTime = (TextView)arg1.findViewById(R.id.backTime);
				arg1.setTag(busItem);
			}else {
				busItem = (BusItem)arg1.getTag();
			}
			EveryBus everyBus = (EveryBus)bus.get(arg0);
			busItem.line1.setText(everyBus.getBusName());
			busItem.line2.setText(everyBus.getBusName());
			busItem.goTimeTextView.setText("  GO："+everyBus.getDepartTime());
			busItem.backTime.setText("BACK："+everyBus.getReturnTime());
		}
		return arg1;
	}

	class SpecialItem{
		TextView textView;
	}
	class BusItem{
		TextView line1;
		TextView line2;
		TextView goTimeTextView;
		TextView backTime;
	}
}
