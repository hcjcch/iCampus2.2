package cn.edu.bistu.oauth;

import cn.edu.bistu.oauthsdk.OAuth;
import com.example.icampus2_2.R;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Oauth extends Activity {
	private WebView webView;
	private String url;
	private String result = "";
	private Intent intent;

	@SuppressWarnings("static-access")
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.oauth);
		ActionBar bar = getActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
		webView = (WebView) findViewById(R.id.webView);
		intent = getIntent();
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				try {
					if (url.contains("#")) {
						result = url.substring(url.indexOf("#") + 1,
								url.length());
						
						intent.putExtra("result", result);
						Oauth.this.setResult(RESULT_OK, intent);
						Oauth.this.finish();
					}
				} catch (Exception e) {
					result = "";
				}
				super.onPageFinished(view, url);
			}

			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				handler.proceed(); // 接受所有网站的证书
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				return super.shouldOverrideUrlLoading(view, url);
			}
		});
		OAuth oauth = new OAuth();
		try {
			url = oauth.authorizeURL("token");
			Log.d("url", url);
			openBrowser(url);
		} catch (Exception e) {
			url = "";
			Log.e("error", e.toString());
		}
	}

	// 利用webView的loadUrl方法
	public void openBrowser(String url) {
		//System.out.println(url);
		webView.loadUrl(url);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			intent.putExtra("result", result);
			Oauth.this.setResult(RESULT_OK, intent);
			Oauth.this.finish();
		}
		return super.onKeyDown(keyCode, event);
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
}
