package com.hcjcch.educationaladministration.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification.Action;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

import com.example.icampus2_2.ICampus;
import com.example.icampus2_2.R;
import com.example.personal.Person;
import com.hcjcch.educationaladministration.config.StaticVariable;
import com.hcjcch.educationaladministration.event.NetworkChangeEvent;
import com.hcjcch.educationaladministration.utils.EduHttpClient;
import com.hcjcch.educationaladministration.utils.MarkUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import de.greenrobot.event.EventBus;

/**
 * Created by limbo on 2014/10/26.
 */
public class MarkQueryActivity extends Activity {
    private Intent intent = null;
    private String id = null;//学号
    private EditText year = null;
    private EditText semester = null;//学期
    private EditText type = null;
    private MarkUtils markUtils = null;
    //private Button querybutton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_query);
        ActionBar actionBar = getActionBar();
        actionBar.setIcon(R.drawable.fronticonsgrade_search);
        actionBar.setDisplayHomeAsUpEnabled(true);
        year = (EditText)findViewById(R.id.EditView1);
        year.setFocusable(false);
        // not show the Keyboard
        semester = (EditText)findViewById(R.id.EditView2);
        semester.setFocusable(false);
        type = (EditText)findViewById(R.id.EditView3);
        type.setFocusable(false);
        //querybutton = (Button)findViewById(R.id.queryButton);
        intent = getIntent();
        //学号接口！！！！
        id = ((Person)intent.getSerializableExtra("user")).getUserid();
        markUtils = new MarkUtils(id, this);
        EventBus.getDefault().register(this);
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        querybutton.setBackgroundResource(R.drawable.searchbutton);
    }*/

    public void input_year(View view){
            //确保years里面存储有干货
        if(!markUtils.years_is_empty()) {
            final CharSequence a[] = markUtils.getyears();
            new AlertDialog.Builder(this).setTitle("选择学年").setItems(a, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    year.setText(a[which]);
                }
            }).show();
        }else {
            show_error();
        }

    }

    public void input_semester(View view){
        //这个是学期。。。。。。
        final CharSequence[] a = new CharSequence[2];
        a[0] = "1";
        a[1] = "2";
        new AlertDialog.Builder(this).setTitle("选择学期").setItems(a,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                semester.setText(a[which]);
            }
        }).show();
    }

    public void input_type(View view){
        //这个是类型；
        //一部分固定的字符串存在StaticVariable
        final CharSequence[] a = new CharSequence[6];
        a[0] = StaticVariable.qbkc;
        a[1] = StaticVariable.ggjck;
        a[2] = StaticVariable.zyjck;
        a[3] = StaticVariable.ggxxk;
        a[4] = StaticVariable.zyxxk;
        a[5] = StaticVariable.sjk;
        new AlertDialog.Builder(this).setTitle("选择课程类型").setItems(a,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                type.setText(a[which]);
            }
        }).show();
    }

    public void query(View view){

        if(!is_void()) {
            Intent intent = new Intent();
            intent.setClass(MarkQueryActivity.this, MarkDetailActivity.class);
            intent.putExtra("id", this.id);
            intent.putExtra("year",year.getText().toString());
            intent.putExtra("semester", semester.getText().toString());//学期
            intent.putExtra("type", type.getText().toString());
            //窗口跳转 、传值
            startActivity(intent);
        }else{
            Toast.makeText(this, "选项不能为空",Toast.LENGTH_SHORT).show();
        }
    }

    private void show_error(){
        Toast.makeText(this, "无法获取信息", Toast.LENGTH_SHORT).show();
    }

    private boolean is_void(){
        boolean res = false;
        if(type.getText().toString().equals("")||year.getText().toString().equals("")||semester.getText().toString().equals(""))
            res = true;
        return res;
    }

    public void onEventMainThread(NetworkChangeEvent event){
        if (event.isNetworkConnected()){
            Toast.makeText(this, "网络连接", Toast.LENGTH_SHORT).show();
        }  else {
            Toast.makeText(this,"网络断开",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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
