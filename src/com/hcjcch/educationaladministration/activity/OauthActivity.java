package com.hcjcch.educationaladministration.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.http.SslError;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.icampus2_2.R;
import com.hcjcch.educationaladministration.oauthsdk.OAuth;
import com.hcjcch.educationaladministration.oauthsdk.OauthUtil;

/**
 * Created by hcjcch on 2014/10/26.
 */

public class OauthActivity extends Activity {

    private WebView webView;
    private String url;  //璇锋眰鐨勫湴鍧�
    private String result; //寰楀埌鐨勮繑鍥炵粨鏋�
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauth);
        intent = getIntent();
        webView = (WebView) findViewById(R.id.webView_oauth);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                try {   //褰撳緱鍒拌繑鍥炵粨鏋滃悗锛屽缁撴灉杩涜鏁版嵁鐨勫鐞�
                    if (url.contains("#")) {
                        result = url.substring(url.indexOf("#") + 1,
                                url.length());
                        String token  = result.substring(result.indexOf("access_token="), result.length());
                        token =token.substring(0, token.indexOf("&"));
                        token = token.substring(token.indexOf("=")+1,token.length());
                        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(OauthActivity.this);
                        OauthUtil.saveOauth(prefs, token);
                        Toast.makeText(OauthActivity.this,OauthUtil.getAccessToken(prefs),Toast.LENGTH_SHORT).show();
                        OauthActivity.this.finish();
                    }
                } catch (Exception e) {
                    result = "";
                }
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                super.onReceivedSslError(view, handler, error);
            }
        });

        try {
            url = OAuth.authorizeURL("token");
            openBrowser(url);
        } catch (Exception e) {
            url = "";
        }
    }

    // 鍒╃敤webView鐨刲oadUrl鏂规硶
    public void openBrowser(String url) {
        webView.loadUrl(url);
    }
}