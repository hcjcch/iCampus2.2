package com.hcjcch.educationaladministration.utils;

import android.content.Context;
import android.os.Message;
import android.widget.Toast;

import com.hcjcch.educationaladministration.activity.MarkDetailActivity;
import com.hcjcch.educationaladministration.config.StaticVariable;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limbo on 2014/11/6.
 */
public class MarkUtils {
    private Context context;
    private String[] years = null;
    private List<Map<String,Object>> list_score = null;
    private String xuehao = null;
    private String year = null;
    private String semester = null;
    private String type = null;
    public MarkUtils(String id, Context context){
        this.xuehao = id;
        this.context = context;
        this.years = this.pull_years("year.php");
    }
    //获取学年列表
    private String[] pull_years(String url){
        final String[] str = new String[4];
        RequestParams params = new RequestParams();
        params.add("xh",xuehao);
        //api_path;
        //异步获取json并解析json
        EduHttpClient.get(url,params,new AsyncHttpResponseHandler() {
            @Override
            public void onStart(){
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //execution;
                String json = new String(responseBody);
                try {
                    //!!!decode JSON must try block!!!!
                    JSONArray array = new JSONArray(json);
                    for(int i=0;i<array.length();i++){
                        //解析json
                        JSONObject object = array.getJSONObject(i);
                        str[i]=object.getString("title");
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //show the error
                error.printStackTrace();
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
        return str;
    }

    public CharSequence[] getyears(){
        CharSequence[] a = new CharSequence[4];
        a[0]=years[0];
        a[1]=years[1];
        a[2]=years[2];
        a[3]=years[3];
        return a;
    }

    public boolean years_is_empty(){
        for(int i=0;i<4;i++){
            if((years[i]==null)||(years[i].equals("")))
                return true;
        }
        return false;
    }

}
