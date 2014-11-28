package com.example.group;



import java.util.List;

import org.apache.http.Header;

import cn.edu.bistu.tools.MyProgressDialog;

import com.example.groupdata.JsonStudent;
import com.example.groupdata.Student;
import com.example.icampus2_2.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GroupMen extends Activity {
	private ListView listView;
	private List<Student> list;
	private MyProgressDialog progressDialog;
    @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	Intent intent = getIntent();
    	String title=intent.getStringExtra("title");
    	
    	String classtype=intent.getStringExtra("classtype");
    	setContentView(R.layout.groupfirst);
    	ActionBar actionBar = getActionBar();
    	actionBar.setIcon(R.drawable.groupicon);
    	actionBar.setDisplayHomeAsUpEnabled(true);
    	actionBar.setTitle(title);
    	actionBar.show();
    	progressDialog = new MyProgressDialog(this, "正在加载中", "请稍后...", false);
    	listView= (ListView)findViewById(R.id.groupfirsts);
    	AsyncHttpClient client = new AsyncHttpClient();
    	client.get("http://jwcapi.iflab.org/groupuser.php?group="+title+"&grouptype="+classtype, new AsyncHttpResponseHandler(){
    		@Override
    		public void onFailure(int arg0, Header[] arg1, byte[] arg2,
    				Throwable arg3) {
    			// TODO Auto-generated method stub
    			super.onFailure(arg0, arg1, arg2, arg3);
    		}
    		@Override
    		public void onStart() {
    			// TODO Auto-generated method stub
    			super.onStart();
    			progressDialog.show();
    		}
    		@Override
    		public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
    			// TODO Auto-generated method stub
    			super.onSuccess(arg0, arg1, arg2);
    			String information = new String(arg2);
    			if (information.contains("<HTML>")) {
					Toast.makeText(GroupMen.this, "BistuWifi 请登录",
							Toast.LENGTH_SHORT).show();
				} else {
					JsonStudent jsonStudent = new JsonStudent();
					list=jsonStudent.getList(information);
					StudentAdapter adapter = new StudentAdapter(GroupMen.this, list);
					listView.setAdapter(adapter);
					
				}
    			progressDialog.hideAndCancle();
    		}
    	});
    	
    	
    	listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("num",list.get(arg2).getXH());
				intent.putExtra("nam",list.get(arg2).getXM());
				intent.putExtra("sex",list.get(arg2).getXB());
				intent.setClass(GroupMen.this, StudentDetil.class);
				startActivity(intent);
				
			}
		});
    }
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
    class StudentAdapter extends BaseAdapter{
    	private Context context;
    	private  List<Student> list;
          public StudentAdapter(Context context, List<Student> list) {
	             this.context=context;
	             this.list=list;
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
				arg1 = LayoutInflater.from(context).inflate(R.layout.groupitem, null);
				viewHodler.textView = (TextView)arg1.findViewById(R.id.Item);
				arg1.setTag(viewHodler);
			}else {
				viewHodler = (ViewHodler)arg1.getTag();
			}
			viewHodler.textView.setText(list.get(arg0).getXM());
			return arg1;
		}
		class ViewHodler{
			private TextView textView;
		}
    	
    }
}
