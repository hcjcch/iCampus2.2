package cn.edu.bistu.school;

import java.util.ArrayList;

import org.apache.http.Header;

import cn.edu.bistu.schoolData.JsonSchoolDetail;
import cn.edu.bistu.schoolData.School;
import cn.edu.bistu.schoolData.SchoolDetail;
import cn.edu.bistu.tools.MyProgressDialog;

import com.example.icampus2_2.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

@SuppressLint("NewApi")
public class SchoolDetailShow extends Activity {
	private TextView school;
	private ArrayList<School> schools;
	private int position;
	private MyProgressDialog progressDialog;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.school_detail);
		init();
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		Intent intent = getIntent();
		schools = (ArrayList<School>) intent.getSerializableExtra("schoolList");
		position = intent.getIntExtra("position", 0);
		String introName = intent.getStringExtra("introName");
		actionBar.setTitle(introName);
		actionBar.show();
		progressDialog = new MyProgressDialog(this, "正在加载中", "请稍后...", false);
		show(position);
	}

	private void show(int position) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(
				"http://api.bistu.edu.cn/api/api.php?table=collegeintro&action=detail&mod="
						+ schools.get(position).getMod() + "&id="
						+ schools.get(position).getId(),
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
						JsonSchoolDetail jsonSchoolDetail = new JsonSchoolDetail();
						SchoolDetail schoolDetail = jsonSchoolDetail
								.getList(information);
						school.setText(schoolDetail.getIntroCont());
						progressDialog.hide();
					}
				});
	}

	private void init() {
		school = (TextView) findViewById(R.id.schoolDetail);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			progressDialog.cancel();
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
