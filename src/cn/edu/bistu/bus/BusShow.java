package cn.edu.bistu.bus;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import cn.edu.bistu.busData.Bus;
import cn.edu.bistu.busData.CatBus;
import cn.edu.bistu.busData.EveryBus;
import cn.edu.bistu.busData.JsonBus;
import cn.edu.bistu.tools.MyProgressDialog;
import com.example.icampus2_2.ICampus;
import com.example.icampus2_2.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class BusShow extends Activity {
	private ListView busList;
	private List<Bus> bus;
	private CatBus tongQingbus;
	private CatBus jiaoXueBus;
	private ArrayList<Object> buses;
	private MyProgressDialog progressDialog;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bus);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.show();
		progressDialog = new MyProgressDialog(this, "正在加载中", "请稍后...", false);
		init();
		show();
	}

	public void init() {
		busList = (ListView) findViewById(R.id.busList);
		busList.setOnItemClickListener(new ItemClick());
	}

	public void show() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://m.bistu.edu.cn/newapi/bus.php",
				new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1, arg2, arg3);
					}

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						progressDialog.show();
						super.onStart();
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1, arg2);
						String string = new String(arg2);
						if (string.contains("<HTML>")) {
							Toast.makeText(BusShow.this, "BistuWifi 请登录",
									Toast.LENGTH_SHORT).show();
						}else {
							bus = (new JsonBus()).getList(string);
							tongQingbus = bus.get(0).getCatbus();
							jiaoXueBus = bus.get(1).getCatbus();
							buses = new ArrayList<Object>();
							buses.add("通勤班车");
							for (EveryBus everyBus : tongQingbus.getCatbus()) {
								buses.add(everyBus);
							}
							buses.add("教学班车");
							for (EveryBus everyBus : jiaoXueBus.getCatbus()) {
								buses.add(everyBus);
							}
							BusListAdapter adapter = new BusListAdapter(
									BusShow.this, buses);
							busList.setAdapter(adapter);
						}
						progressDialog.hideAndCancle();
					}
				});
	}

	class ItemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			EveryBus everyBus = (EveryBus)buses.get(arg2);
			int busDivide = buses.indexOf("教学班车");
			if (arg2 < busDivide) {
				bundle.putString("busType", "通勤班车");
			}else {
				bundle.putString("busType", "教学班车");
			}
			bundle.putString("busName", everyBus.getBusName());
			bundle.putString("departTime", everyBus.getDepartTime());
			bundle.putString("returnime", everyBus.getReturnTime());
			bundle.putSerializable("busLine", everyBus.getBusLine());
			intent.putExtras(bundle);
			intent.setClass(BusShow.this, BusDetail.class);
			startActivity(intent);
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, ICampus.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
