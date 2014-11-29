package cn.edu.bistu.secondhand;

import java.util.ArrayList;
import java.util.List;

import cn.edu.bistu.tools.secondhandtools.HandlrGetData;
import cn.edu.bistu.tools.secondhandtools.HandlrGetData.onDataLoadListener;
import cn.edu.bistu.tools.secondhandtools.JsonDeal;
import cn.edu.bistu.tools.secondhandtools.KindArrayAdapter;

import com.example.icampus2_2.R;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class MyPublish extends Activity {
	
	private ListView publishlist;
	private KindArrayAdapter kindArrayAdapter;
	private List<SecondHandItem> handItems;
	
  
	 @SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mypublish);
		ActionBar actionBar = getActionBar();
		actionBar.setIcon(R.drawable.shopping_bag);
		actionBar.setTitle("我的发布");
		actionBar.setDisplayHomeAsUpEnabled(true);
		handItems = new ArrayList<SecondHandItem>();
		publishlist = (ListView)findViewById(R.id.mypublishlist);
		publishlist.addHeaderView(new RelativeLayout(this));
		publishlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		getdata("1111");
        
	}
	 @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getdata("1111");
	}
	 @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		 switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	 private void getdata(String id){
		 HandlrGetData.data("http://m.bistu.edu.cn/newapi/secondhand.php?userid="+id, new onDataLoadListener() {
			
			@Override
			public void onDataLoadr(String result) {
				// TODO Auto-generated method stub
                    handItems = JsonDeal.getseondhanditem(result);
					kindArrayAdapter = new KindArrayAdapter(handItems, MyPublish.this);
					publishlist.setAdapter(kindArrayAdapter);
		            publishlist.setOnItemClickListener(new DrawerItemClickListener(handItems, MyPublish.this,1));
			}
		});
	 }
}
