package cn.edu.bistu.yellowPage;

import java.util.List;

import org.apache.http.Header;

import cn.edu.bistu.tools.MyProgressDialog;
import cn.edu.bistu.yellowPageData.JsonYellowPagePersonal;
import cn.edu.bistu.yellowPageData.YelloPagePersonal;

import com.example.icampus2_2.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Intents.Insert;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

@SuppressLint("NewApi")
public class ContactPerson extends Activity {
	private ListView listView;
	private List<YelloPagePersonal> list;
	private String name;
	private View view;
	private Dialog aDialog;
	private int position;
	private MyProgressDialog progressDialog;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_person);
		init();
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.show();
		progressDialog = new MyProgressDialog(this, "正在加载中", "请稍后...", false);
		Intent intent = getIntent();
		String depart = intent.getStringExtra("depart");
		name = intent.getStringExtra("name");
		this.setTitle(name);
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(
				"http://m.bistu.edu.cn/newapi/yellowpage.php?action=tel&catid="
						+ depart, new AsyncHttpResponseHandler() {

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
						JsonYellowPagePersonal pagePersonal = new JsonYellowPagePersonal();
						list = pagePersonal.getList(information);
						ContactPersonAdapter adapter = new ContactPersonAdapter(
								ContactPerson.this, list);
						listView.setAdapter(adapter);
						progressDialog.hideAndCancle();
					}
				});
	}

	private void init() {
		listView = (ListView) findViewById(R.id.contact);
		LayoutInflater inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.dialog, null);
		Button call = (Button) view.findViewById(R.id.call);
		Button address = (Button) view.findViewById(R.id.addressList);
		Button cancel = (Button) view.findViewById(R.id.cancel);
		aDialog = new Dialog(ContactPerson.this);
		aDialog.setCanceledOnTouchOutside(true);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				position = arg2;
				aDialog.setTitle(list.get(arg2).getName());
				aDialog.setContentView(view);
				aDialog.show();
			}
		});
		call.setOnClickListener(new Click());
		address.setOnClickListener(new Click());
		cancel.setOnClickListener(new Click());
	}

	class Click implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.call:
				Uri uri = Uri.parse("tel:"+list.get(position).getTelnum());
				Intent intent = new Intent(Intent.ACTION_DIAL, uri);
				startActivity(intent);
				aDialog.hide();
				break;
			case R.id.addressList:
				Intent intent1 = new Intent(Intent.ACTION_INSERT);
		        intent1.setType("vnd.android.cursor.dir/person");
		        intent1.setType("vnd.android.cursor.dir/contact");
		        intent1.setType("vnd.android.cursor.dir/raw_contact");
		        intent1.putExtra(Insert.NAME, list.get(position).getName());
		        intent1.putExtra(Insert.SECONDARY_PHONE_TYPE,Phone.TYPE_WORK);
		        intent1.putExtra(Insert.SECONDARY_PHONE,list.get(position).getTelnum());
		        startActivity(intent1);
				aDialog.hide();
				break;
			case R.id.cancel:
				aDialog.hide();
				break;
			default:
				break;
			}
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			aDialog.cancel();
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
