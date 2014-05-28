package com.example.group;


import org.apache.http.Header;

import com.example.groupdata.JsonMessage;
import com.example.groupdata.Messages;
import com.example.icampus2_2.R;
import com.example.personal.MessagePost;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cn.edu.bistu.tools.MyProgressDialog;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Intents.Insert;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class StudentDetil extends Activity {
	private MyProgressDialog progressDialog;
	private Dialog aDialog;
	private Messages messages;
	String num;
	String nam;
	String sex;
	TextView numView;
	TextView namView;
	TextView sexView;
	TextView qqView;
	TextView wechatView;
	TextView mobileView;
	TextView emailView;
	View view;
	View view2;
     @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.studentdetil);
    	Intent intent=getIntent();
    	  num=intent.getStringExtra("num");
    	  nam=intent.getStringExtra("nam");
    	  sex=intent.getStringExtra("sex");
    	ActionBar actionBar = getActionBar();
    	actionBar.setDisplayHomeAsUpEnabled(true);
    	actionBar.setTitle("个人信息");
    	actionBar.show();
    	progressDialog = new MyProgressDialog(this, "正在加载中", "请稍后...", false);
    	 numView = (TextView)findViewById(R.id.num);
    	 namView = (TextView)findViewById(R.id.name);
    	 sexView = (TextView)findViewById(R.id.sex);
    	 qqView = (TextView)findViewById(R.id.qq);
    	 wechatView = (TextView)findViewById(R.id.wechat);
    	 mobileView = (TextView)findViewById(R.id.mobile);
    	 emailView = (TextView)findViewById(R.id.email);
    	  
    	AsyncHttpClient client = new AsyncHttpClient();
    	client.get("http://m.bistu.edu.cn/newapi/userinfo.php?userid="+num, new AsyncHttpResponseHandler(){
    		@Override
    		public void onFailure(int arg0, Header[] arg1, byte[] arg2,
    				Throwable arg3) {
    			// TODO Auto-generated method stub
    			super.onFailure(arg0, arg1, arg2, arg3);
    		}
    		@Override
    		public void onStart() {
    			// TODO Auto-generated method stub
    			super.onStart();
    			progressDialog.show();
    		}
    		@Override
    		public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
    			// TODO Auto-generated method stub
    			super.onSuccess(arg0, arg1, arg2);
    			String information = new String(arg2);
    			if (information.contains("<HTML>")) {
					Toast.makeText(StudentDetil.this, "BistuWifi 请登录",
							Toast.LENGTH_SHORT).show();
					}else{
    			    JsonMessage jsonMessage = new JsonMessage();
    			    messages = jsonMessage.getMess(information);
    			    numView.setText(num);
    			    namView.setText(nam);
    			    sexView.setText(sex);
    			    qqView.setText(messages.getQq());
    			    wechatView.setText(messages.getWechat());
    			    mobileView.setText(messages.getMobile());
    			    emailView.setText(messages.getEmail());
    			    
    		}
    			
    		progressDialog.hideAndCancle();
    		}
    	});
    	init();
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
     private void init() {
 		LayoutInflater inflater = LayoutInflater.from(this);
 		view = inflater.inflate(R.layout.dialog, null);
 		view2 = inflater.inflate(R.layout.prompt, null);
		TextView pro =(TextView)view2.findViewById(R.id.tishi);
		pro.setText(nam+"同学没有提供电话");
 		Button call = (Button) view.findViewById(R.id.call);
 		Button address = (Button) view.findViewById(R.id.addressList);
 		Button cancel = (Button) view.findViewById(R.id.cancel);
 		aDialog = new Dialog(StudentDetil.this);
 		aDialog.setCanceledOnTouchOutside(true);
 		mobileView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (messages.getMobile().equals("-")) {
					aDialog.setTitle("提示");
					aDialog.setContentView(view2);
					aDialog.show();
				} else {
					aDialog.setTitle(nam);
					aDialog.setContentView(view);
					aDialog.show();
				}
				
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
 				Uri uri = Uri.parse("tel:"+messages.getMobile());
 				Intent intent = new Intent(Intent.ACTION_DIAL, uri);
 				startActivity(intent);
 				aDialog.hide();
 				break;
 			case R.id.addressList:
 				Intent intent1 = new Intent(Intent.ACTION_INSERT);
 		        intent1.setType("vnd.android.cursor.dir/person");
 		        intent1.setType("vnd.android.cursor.dir/contact");
 		        intent1.setType("vnd.android.cursor.dir/raw_contact");
 		        intent1.putExtra(Insert.NAME, nam);
 		        intent1.putExtra(Insert.SECONDARY_PHONE_TYPE,Phone.TYPE_WORK);
 		        intent1.putExtra(Insert.SECONDARY_PHONE,messages.getMobile());
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
}
