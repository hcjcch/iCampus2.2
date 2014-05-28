package cn.edu.bistu.bistujob;

import java.io.IOException;

import org.apache.http.Header;

import cn.edu.bistu.bistujobData.JobDetailType;
import cn.edu.bistu.bistujobData.JsonJobDetail;
import cn.edu.bistu.tools.OperateFileForMySaveJob;

import com.example.icampus2_2.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class JobDetail extends Activity {
	private String id;
	private TextView detailpublishcompany;
	private TextView detailworklocation;
	private TextView detaildescription;
	private TextView detailqualification;
	private TextView detailsalary;
	private TextView detailtime;
	private TextView detailcontactName;
	private TextView detailcontactEmail;
	private TextView detailcontactPhone;
	private TextView detailcontactQQ;
	private TextView detailtitle;
	private String status = "0";
	private JobDetailType detail;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.job_detail);
		try {
			init();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.show();
		Intent intent = getIntent();
		status = intent.getStringExtra("status");
		if (status.equals("2")) {
			detail = (JobDetailType) intent.getSerializableExtra("detail");
			setdata(detail);
			return;
		}
		id = intent.getStringExtra("id");
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://m.bistu.edu.cn/newapi/jobdetail.php?id=" + id,
				new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1, arg2, arg3);
					}

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						super.onFinish();
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						String infor = new String(arg2);
						int a = infor.indexOf("[") + 1;
						int b = infor.lastIndexOf("]");
						infor = infor.substring(a, b);
						detail = (new JsonJobDetail())
								.getList(infor);
						try {
							setdata(detail);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
						super.onSuccess(arg0, arg1, arg2);
					}

				});
	}

	private void init() {
		// TODO Auto-generated method stub
		detailtitle = (TextView) findViewById(R.id.detail_title);
		detailpublishcompany = (TextView) findViewById(R.id.detailpublishcompany);
		detailworklocation = (TextView) findViewById(R.id.detailworklocation);
		detaildescription = (TextView) findViewById(R.id.detaildescription);
		detailqualification = (TextView) findViewById(R.id.detailqualification);
		detailsalary = (TextView) findViewById(R.id.detailsalary);
		detailtime = (TextView) findViewById(R.id.detailtime);
		detailcontactName = (TextView) findViewById(R.id.detailcontactName);
		detailcontactEmail = (TextView) findViewById(R.id.detailcontactEmail);
		detailcontactPhone = (TextView) findViewById(R.id.detailcontactPhone);
		detailcontactQQ = (TextView) findViewById(R.id.detailcontactQQ);
	}

	private void setdata(JobDetailType detail) {
		// TODO Auto-generated method stub
		detailtitle.setText(detail.getTitle());
		detailpublishcompany.setText(detail.getCompany());
		detailworklocation.setText(detail.getLocation());
		detaildescription.setText(detail.getDescription());
		detailqualification.setText(detail.getQualifications());
		detailsalary.setText(detail.getSalary());
		detailtime.setText(detail.getTime());
		detailcontactName.setText(detail.getContactName());
		detailcontactEmail.setText(detail.getContactEmail());
		detailcontactPhone.setText(detail.getContactPhone());
		detailcontactQQ.setText(detail.getContactQQ());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		if (status.equals("0")) {
			getMenuInflater().inflate(R.menu.job_detail, menu);
		} else if(status.equals("1")) {
			getMenuInflater().inflate(R.menu.none, menu);
		}else {
			getMenuInflater().inflate(R.menu.none, menu);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.save:
			OperateFileForMySaveJob saveJob = new OperateFileForMySaveJob(JobDetail.this);
			try {
				saveJob.saveJob(id, detail);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("job ’≤ÿ¥ÌŒÛ");
				e.printStackTrace();
			}
			break;
		case R.id.tel:
			break;
		case R.id.talk:
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
