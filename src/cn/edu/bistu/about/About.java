package cn.edu.bistu.about;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class About extends Activity{
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.about);
	//	Intro aIntro = new Intro();
		//System.out.println(aIntro.getIntro());
		WebView webView = new WebView(this);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("http://m.bistu.edu.cn/info.php");
		setContentView(webView);
	}
}
