package cn.edu.bistu.bistujob;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.bistu.bistujobData.JobListType;
import cn.edu.bistu.bistujobData.JsonJobList;
import cn.edu.bistu.oauth.Oauth;
import cn.edu.bistu.tools.ACache;
import cn.edu.bistu.tools.NetworkLoad;

import com.example.icampus2_2.R;
import com.example.personal.Person;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class MyPublish extends Activity {
	private ListView listView;
	private String url = "http://m.bistu.edu.cn/newapi/job.php?userid=";
	private String deleteUrl = "http://m.bistu.edu.cn/newapi/job_unvalid.php";
	private List<JobListType> list;
	private FragmentAdapter adapter;
	private String userid = ""; 
	private int deleteId = 0;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_publish);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setIcon(R.drawable.jobseekericon);
		actionBar.setTitle("我的发布");
		list = new ArrayList<JobListType>();
		listView = (ListView) findViewById(R.id.my_publish);
		AsyncHttpClient client = new AsyncHttpClient();
		ACache aCache = ACache.get(MyPublish.this);
		Person person = (Person) aCache.getAsObject("user");
		if (person == null) {
			Toast.makeText(MyPublish.this, "没有登录，请登录!", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent();
			intent.setClass(MyPublish.this, Oauth.class);
		}
		userid = person.getUserid();
		client.get(url+userid, new AsyncHttpResponseHandler() {

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				String information = new String(arg2);
				list = (new JsonJobList()).getList(information);
				if (list == null ||list.size() == 0) {
					Toast.makeText(MyPublish.this, "您还没有发布任何信息！", Toast.LENGTH_SHORT).show();
					return;
				}
				adapter = new FragmentAdapter(list,
						MyPublish.this);
				listView.setAdapter(adapter);
				super.onSuccess(arg0, arg1, arg2);
			}

		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("id", list.get(arg2).getId());
				intent.putExtra("status", "1");
				intent.setClass(MyPublish.this, JobDetail.class);
				startActivity(intent);
			}
		});
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(MyPublish.this)
						.setIcon(R.drawable.ic_action_about)
						.setTitle("删除？")
						.setPositiveButton("删除",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										deleteId = arg2;
										(new MyAsynctask()).execute(deleteUrl+"?id="+list.get(arg2).getId());
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

									}
								}).show();
				return true;
			}
		});
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

	class MyAsynctask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result = "";
			try {
				result = (new NetworkLoad()).GetGetInformation(params[0]);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if (result.equals("")) {
				Toast.makeText(MyPublish.this, "删除失败！", Toast.LENGTH_SHORT)
						.show();
			}
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(result);
				String idString = jsonObject.getString("id");
				if (idString.equals("0")) {
					Toast.makeText(MyPublish.this, "删除成功！", Toast.LENGTH_SHORT)
							.show();
				}
				list.remove(deleteId);
				adapter.notifyDataSetChanged();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Toast.makeText(MyPublish.this, "删除失败！", Toast.LENGTH_SHORT)
						.show();
				e.printStackTrace();
			}
			super.onPostExecute(result);
		}
	}
}
