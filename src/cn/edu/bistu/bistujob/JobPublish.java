package cn.edu.bistu.bistujob;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.bistu.bistujobData.JobType;
import cn.edu.bistu.oauth.Oauth;
import cn.edu.bistu.tools.ACache;
import cn.edu.bistu.tools.CheckInformation;
import cn.edu.bistu.tools.GetInformation;
import cn.edu.bistu.tools.NetworkLoad;

import com.example.icampus2_2.R;
import com.example.personal.Person;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class JobPublish extends Activity {
	private LinearLayout description;
	private LinearLayout qualifications;
	private Spinner mod;
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
	private TextView descrip;
	private TextView quali;
	private Map<String, String> maps;
	private String uploadUrlString = "http://m.bistu.edu.cn/newapi/job_add.php";
	private Map<String, String> map;
	private Spinner typeidSpinner;
	private String[] strings;
	private static final int OAUTH_NAME = 2;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.job_publish);
		Intent intent = getIntent();		
		@SuppressWarnings("unchecked")
		ArrayList<JobType> types = (ArrayList<JobType>) intent.getSerializableExtra("type");
		maps = new HashMap<String, String>();
		map = new HashMap<String, String>();
		strings = new String[types.size()];
		for (int i = 0; i < types.size(); i++) {
			maps.put(types.get(i).getType(), types.get(i).getId());
			strings[i] = types.get(i).getType();
		}
		init();
		ActionBar actionBar = getActionBar();
		actionBar.setIcon(R.drawable.jobseekericon);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("发布");
		actionBar.show();
	}

	private void init() {
		// TODO Auto-generated method stub
		typeidSpinner = (Spinner)findViewById(R.id.typeid);
		typeidSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,strings));
		description = (LinearLayout) findViewById(R.id.description);
		// qualifications = (LinearLayout) findViewById(R.id.qualifications);
		descrip = (TextView) description.findViewById(R.id.descri);
		// quali = (TextView)qualifications.findViewById(R.id.quali);
		mod = (Spinner) findViewById(R.id.mod);
		title = (EditText) findViewById(R.id.title);
		// company = (EditText) findViewById(R.id.company);
		// location = (EditText) findViewById(R.id.location);
		// salary = (EditText) findViewById(R.id.salary);
		contactName = (EditText) findViewById(R.id.contactName);
		contactEmail = (EditText) findViewById(R.id.contactEmail);
		contactPhone = (EditText) findViewById(R.id.contactPhone);
		contactQQ = (EditText) findViewById(R.id.contactQQ);
		description.setOnClickListener(new MyClick());
		// qualifications.setOnClickListener(new MyClick());
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
			/*
			 * SharedPreferences prefs = PreferenceManager
			 * .getDefaultSharedPreferences(JobPublish.this); String accessToken
			 * = OauthUtil.getAccessToken(prefs);
			 * System.out.println(accessToken);
			 */
			try {
				map = getParameter();
				if (map == null) {
					
				}
				else {
					(new MyAsy()).execute();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
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
		if (modString.equals("兼职")) {
			modString = "2";
		} else {
			modString = "1";
		}
		parameter.put("mod", modString);
		String typeidString = typeidSpinner.getSelectedItem().toString();
		parameter.put("typeid", maps.get(typeidString));
		String titleString = title.getText().toString();
		parameter.put("title", titleString);
		// String companyString = company.getText().toString();
		parameter.put("company", " ");
		parameter.put("description", desc + "");
		parameter.put("qualifications", qual + " ");
		// String locationString = location.getText().toString();
		parameter.put("location", " ");
		// String salaryString = salary.getText().toString();
		parameter.put("salary", " ");
		String conactNameString = contactName.getText().toString();
		parameter.put("contactName", conactNameString + "");
		String conactEmailString = contactEmail.getText().toString();
		parameter.put("contactEmail", conactEmailString + "");
		String conactPhoneString = contactPhone.getText().toString();
		parameter.put("contactPhone", conactPhoneString + "");
		String contactQQString = contactQQ.getText().toString();
		parameter.put("contactQQ", contactQQString + "");
		ACache aCache = ACache.get(this);
		String userid = "";
		Person person = (Person) aCache.getAsObject("user");
		if (person == null) {
			Toast.makeText(JobPublish.this, "没有登录,请登录！", Toast.LENGTH_SHORT)
					.show();
			Intent intent = new Intent(JobPublish.this, Oauth.class);
			startActivityForResult(intent, OAUTH_NAME);
		} else {
			userid = person.getUserid();
			parameter.put("userid", userid);
		}
		if (titleString.equals("")||titleString == null) {
			Toast.makeText(JobPublish.this, "请输入标题", Toast.LENGTH_SHORT).show();
			return null;
		}
		if (desc.equals("")||desc == null) {
			Toast.makeText(JobPublish.this, "工作描述", Toast.LENGTH_SHORT).show();
			return null;
		}
		if (conactNameString.equals("")||conactEmailString == null) {
			Toast.makeText(JobPublish.this, "请输入联系人姓名", Toast.LENGTH_SHORT).show();
			contactName.setFocusable(true);
			return null;
		}
		if (!CheckInformation.chaeckPhone(conactPhoneString)) {
			Toast.makeText(JobPublish.this, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();
			contactPhone.setFocusable(true);
			return null;
		}
		if (!CheckInformation.checkMail(conactEmailString)) {
			Toast.makeText(JobPublish.this, "请输入正确的邮箱", Toast.LENGTH_SHORT).show();
			contactEmail.setFocusable(true);
			return null;
		}
		if (!CheckInformation.checkQQ(contactQQString)) {
			Toast.makeText(JobPublish.this, "请输入正确的QQ", Toast.LENGTH_SHORT).show();
			contactQQ.setFocusable(true);
			return null;
		}
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
			/*
			 * case R.id.qualifications: intent.setClass(JobPublish.this,
			 * JobPublishOver.class); startActivityForResult(intent,
			 * QUALIFICATIONS); break;
			 */
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
				descrip.setText(desc);
			}
			break;
		case QUALIFICATIONS:
			if (resultCode == RESULT_OK) {
				qual = data.getStringExtra("jobpublish");
				quali.setText(qual);
			}
			break;
		case OAUTH_NAME:
			if (resultCode == RESULT_OK) {
				System.out.println("登录成功！");
				GetInformation.get(data, JobPublish.this);
			}
			break;
		default:
			break;
		}
	}

	class MyAsy extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			NetworkLoad networkLoad = new NetworkLoad();
			String result = "";
			try {
				result = networkLoad.PostGetInformation(uploadUrlString, map);
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
			try {
				JSONObject jsonObject = new JSONObject(result);
				String id = jsonObject.getString("id");
				if (id == null) {
					Toast.makeText(JobPublish.this, "上传失败", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(JobPublish.this, "上传成功！", Toast.LENGTH_LONG)
							.show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Toast.makeText(JobPublish.this, "上传失败", Toast.LENGTH_SHORT)
						.show();
				e.printStackTrace();
			}
			super.onPostExecute(result);
		}
	}
}
