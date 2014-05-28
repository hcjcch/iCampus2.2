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
import cn.edu.bistu.tools.NetworkLoad;

import com.example.icampus2_2.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

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
	private String url = "http://m.bistu.edu.cn/newapi/job.php?userid=2013011186";
	private String deleteUrl = "http://m.bistu.edu.cn/newapi/job_unvalid.php";
	private List<JobListType> list;
	private FragmentAdapter adapter;
	private int deleteId = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_publish);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("ÎÒµÄ·¢²¼");
		list = new ArrayList<JobListType>();
		listView = (ListView) findViewById(R.id.my_publish);
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {

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
						.setTitle("É¾³ý£¿")
						.setPositiveButton("É¾³ý",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										deleteId = arg2;
										(new MyAsynctask()).execute(deleteUrl+"?id="+list.get(arg2).getId());
									}
								})
						.setNegativeButton("È¡Ïû",
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
				Toast.makeText(MyPublish.this, "É¾³ýÊ§°Ü£¡", Toast.LENGTH_SHORT)
						.show();
			}
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(result);
				String idString = jsonObject.getString("id");
				if (idString.equals("0")) {
					Toast.makeText(MyPublish.this, "É¾³ý³É¹¦£¡", Toast.LENGTH_SHORT)
							.show();
				}
				list.remove(deleteId);
				adapter.notifyDataSetChanged();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Toast.makeText(MyPublish.this, "É¾³ýÊ§°Ü£¡", Toast.LENGTH_SHORT)
						.show();
				e.printStackTrace();
			}
			super.onPostExecute(result);
		}
	}
}
