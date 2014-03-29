package cn.edu.bistu.school;

import java.util.ArrayList;

import org.apache.http.Header;

import cn.edu.bistu.schoolData.JsonSchool;
import cn.edu.bistu.schoolData.School;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

@SuppressLint("NewApi")
public class SchoolShow extends Activity {
	private ListView schoolListView;
	private ArrayList<School> list;
	private MyProgressDialog progressDialog;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.school_show);
		init();
		ActionBar actionBar = getActionBar();
		//actionBar.setHomeButtonEnabled(true);
		actionBar.show();
		actionBar.setDisplayHomeAsUpEnabled(true);
		progressDialog = new MyProgressDialog(this, "正在加载中", "请稍后...", false);
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://api.bistu.edu.cn/api/api.php?table=collegeintro",
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
						JsonSchool school = new JsonSchool();
						String information = new String(arg2);
						list = (ArrayList<School>) school.getList(information);
						ListviewAdapter adapter = new ListviewAdapter(
								SchoolShow.this, list);
						schoolListView.setAdapter(adapter);
						progressDialog.hideAndCancle();
					}
				});
		schoolListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("schoolList", list);
				intent.putExtra("position", arg2);
				intent.putExtra("introName", list.get(arg2).getIntroName());
				intent.setClass(SchoolShow.this, SchoolDetailShow.class);
				startActivity(intent);
			}
		});
	}

	private void init() {
		schoolListView = (ListView) findViewById(R.id.schooleList);
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
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
