package cn.edu.bistu.yellowPage;

import java.util.List;

import org.apache.http.Header;

import cn.edu.bistu.tools.MyProgressDialog;
import cn.edu.bistu.yellowPageData.JsonYellowPage;
import cn.edu.bistu.yellowPageData.YelloPage;

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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

@SuppressLint("NewApi")
public class YellowPageShow extends Activity {
	private ListView yellowPage;
	private List<YelloPage> list;

	private MyProgressDialog progressDialog;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.school_show);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.show();
		progressDialog = new MyProgressDialog(this, "正在加载中", "请稍后...", false);
		init();
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://m.bistu.edu.cn/newapi/yellowpage.php?action=cat",
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
							Toast.makeText(YellowPageShow.this, "BistuWifi 请登录",
									Toast.LENGTH_SHORT).show();
						} else {
							JsonYellowPage yellowPage = new JsonYellowPage();
							list = yellowPage.getList(information);
							YellowPageAdapter adapter = new YellowPageAdapter(
									YellowPageShow.this, list);
							YellowPageShow.this.yellowPage.setAdapter(adapter);
						}
						progressDialog.hideAndCancle();
					}
				});
		yellowPage.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("depart", list.get(arg2).getDepart());
				intent.putExtra("name", list.get(arg2).getName());
				intent.setClass(YellowPageShow.this, ContactPerson.class);
				startActivity(intent);
			}
		});
	}

	private void init() {
		yellowPage = (ListView) findViewById(R.id.schooleList);
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
			// finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
