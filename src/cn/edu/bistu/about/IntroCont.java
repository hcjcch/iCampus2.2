package cn.edu.bistu.about;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.bistu.tools.AsyncClientControl;
import cn.edu.bistu.tools.AsyncClientControl.callBack;
import cn.edu.bistu.tools.MyProgressDialog;

import com.example.icampus2_2.R;
import com.loopj.android.http.AsyncHttpClient;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.webkit.WebView;

@SuppressLint("NewApi")
public class IntroCont extends Activity {
	private WebView webView;
	private static String sdRootPath = Environment
			.getExternalStorageDirectory().getPath();
	private static String dataRootPath = null;
	private final static String FOLDER_NAME = File.separator+"iCampus";
	private MyProgressDialog progressDialog;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro_cont);
		dataRootPath = this.getCacheDir().getPath();
		webView = (WebView) findViewById(R.id.introCont);
		Intent intent = getIntent();
		final String mod = intent.getStringExtra("mod");
		String title = intent.getStringExtra("title");
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(title);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.show();
		progressDialog = new MyProgressDialog(IntroCont.this,"正在加载中","请稍后",false);
		AsyncClientControl client = new AsyncClientControl(
				new AsyncHttpClient(), new callBack() {

					@Override
					public void start() {
						// TODO Auto-generated method stub
						progressDialog.show();
					}

					@Override
					public void aferFaile() {
						// TODO Auto-generated method stub
						webView.loadUrl("file://" + getStorageDirectory()
								+ File.separator + mod + ".html");
						progressDialog.hide();
					}

					@Override
					public void afterSucceess(String string) {
						// TODO Auto-generated method stub
						try {
							JSONObject jsonObject = new JSONObject(
									string.substring(1, string.length() - 1));
							String intro = jsonObject.getString("introCont");
							File file = new File(getStorageDirectory() + File.separator
									+ mod + ".html");
							FileOutputStream outputStream = null;
							try {
								outputStream = new FileOutputStream(file);
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							try {
								outputStream.write(intro.getBytes());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							webView.loadUrl("file://" + getStorageDirectory()
									+ File.separator + mod + ".html");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						progressDialog.hide();
					}
				}, "http://m.bistu.edu.cn/newapi/intro.php" + "?mod=" + mod);
		client.getData();
	}

	private String getStorageDirectory() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED) ? sdRootPath + FOLDER_NAME
				: dataRootPath + FOLDER_NAME;
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
