package com.example.group;

import java.util.List;

import org.apache.http.Header;

import com.example.groupdata.GroupClass;
import com.example.groupdata.JsonGroup;
import com.example.icampus2_2.R;
import com.example.personal.Person;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cn.edu.bistu.tools.MyProgressDialog;
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

public class GroupFirst extends Activity {
	private ListView listView;
	private List<GroupClass> list;
	String userid = "2012011171";
	private MyProgressDialog progressDialog;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groupfirst);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.show();
		Intent intent = getIntent();
		Person person = (Person) intent.getSerializableExtra("user");
		String id = person.getUserid();
		userid = id;
		progressDialog = new MyProgressDialog(this, "正在加载中", "请稍后...", false);
		listView = (ListView) findViewById(R.id.groupfirsts);
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://jwcapi.iflab.org/usergroup.php?userid=" + userid,
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
						String information = new String(arg2);
						if (information.contains("<HTML>")) {
							Toast.makeText(GroupFirst.this, "BistuWifi 请登录",
									Toast.LENGTH_SHORT).show();
						} else {
							JsonGroup jsonGroup = new JsonGroup();
							list = jsonGroup.getList(information);
							GroupAdapter adapter = new GroupAdapter(
									GroupFirst.this, list);
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
				intent.putExtra("title", list.get(arg2).getGroup());
				intent.setClass(GroupFirst.this, GroupMen.class);
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
}

class GroupAdapter extends BaseAdapter {

	private List<GroupClass> list;
	private Context context;

	public GroupAdapter(Context context, List<GroupClass> list) {
		// TODO Auto-generated constructor stub
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
			arg1 = LayoutInflater.from(context).inflate(R.layout.groupitem,
					null);
			viewHodler.textView = (TextView) arg1.findViewById(R.id.Item);
			arg1.setTag(viewHodler);
		} else {
			viewHodler = (ViewHodler) arg1.getTag();
		}
		viewHodler.textView.setText(list.get(arg0).getGroup());
		return arg1;
	}

	class ViewHodler {
		private TextView textView;
	}

}
