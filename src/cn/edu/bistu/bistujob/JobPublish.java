package cn.edu.bistu.bistujob;

import java.util.HashMap;
import java.util.Map;

import com.example.icampus2_2.R;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class JobPublish extends Activity {
	private LinearLayout description;
	private LinearLayout qualifications;
	private Spinner mod;
	private Spinner typeid;
	private EditText title;
	private EditText company;
	private EditText location;
	private EditText salary;
	private EditText contactName;
	private EditText contactEmail;
	private EditText contactPhone;
	private EditText contactQQ;
	private String desc = "";
	private String qual = "";
	private final static int DESCRIPTION = 0;
	private final static int QUALIFICATIONS = 1;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.job_publish);
		init();
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("∑¢≤º");
		actionBar.show();
	}

	private void init() {
		// TODO Auto-generated method stub
		description = (LinearLayout) findViewById(R.id.description);
		qualifications = (LinearLayout) findViewById(R.id.qualifications);
		mod = (Spinner) findViewById(R.id.mod);
		typeid = (Spinner) findViewById(R.id.typeid);
		title = (EditText) findViewById(R.id.title);
		company = (EditText) findViewById(R.id.company);
		location = (EditText) findViewById(R.id.location);
		salary = (EditText) findViewById(R.id.salary);
		contactName = (EditText) findViewById(R.id.contactName);
		contactEmail = (EditText) findViewById(R.id.contactEmail);
		contactPhone = (EditText) findViewById(R.id.contactPhone);
		contactQQ = (EditText) findViewById(R.id.contactQQ);
		description.setOnClickListener(new MyClick());
		qualifications.setOnClickListener(new MyClick());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.publish, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.finish_publish:
			
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private Map<String, String> getParameter() {
		// TODO Auto-generated method stub
		Map<String, String> parameter = new HashMap<String, String>();
		String modString = mod.getSelectedItem().toString();
		if (modString.equals("ºÊ÷∞")) {
			modString = "2";
		}else {
			modString = "1";
		}
		parameter.put("mod", modString);
		//String typeidString = typeid.getSelectedItem().toString();
		return parameter;
	}
	class MyClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.description:
				intent.setClass(JobPublish.this, JobPublishOver.class);
				startActivityForResult(intent, DESCRIPTION);
				break;
			case R.id.qualifications:
				intent.setClass(JobPublish.this, JobPublishOver.class);
				startActivityForResult(intent, QUALIFICATIONS);
				break;
			default:
				break;
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case DESCRIPTION:
			if (resultCode == RESULT_OK) {
				desc = data.getStringExtra("jobpublish");
			}
			break;
		case QUALIFICATIONS:
			if (resultCode == RESULT_OK) {
				qual = data.getStringExtra("jobpublish");
			}
			break;
		default:
			break;
		}
	}
}
