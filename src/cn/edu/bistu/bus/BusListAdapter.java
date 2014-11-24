package cn.edu.bistu.bus;

import java.util.ArrayList;

import com.example.icampus2_2.R;

import cn.edu.bistu.busData.EveryBus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BusListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Object> bus;
	private static final int TITLE_ITEM = 0;
	private static final int BUS_LINE = 1;

	public BusListAdapter(final Context context,ArrayList<Object> bus) {
		super();
		this.context = context;
		this.bus = bus;
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
			String departTime = everyBus.getDepartTime();
			String returnTime = everyBus.getReturnTime();
			busItem.goTimeTextView.setText(departTime.substring(0,departTime.lastIndexOf(":")));
			if (!returnTime.equals("null")) {
				busItem.backTime.setText(returnTime.substring(0,returnTime.lastIndexOf(":")));
			}else {
				busItem.backTime.setText("  -");
			}
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
