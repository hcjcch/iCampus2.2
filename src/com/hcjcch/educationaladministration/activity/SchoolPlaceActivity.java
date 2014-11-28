package com.hcjcch.educationaladministration.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.icampus2_2.R;
import com.hcjcch.educationaladministration.adapter.SchoolListAdapter;
import com.hcjcch.educationaladministration.utils.EduHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pangrong on 2014/11/1.
 */
public class SchoolPlaceActivity extends Activity{

    private ListView schoolplace;
    private ArrayList<String> schoolname,schoolcode;
    private SchoolListAdapter schoolListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_place);
        schoolname = new ArrayList<String>();
        schoolcode = new ArrayList<String>();

        ActionBar actionBar = getActionBar();
        actionBar.setTitle(R.string.menu_room_query);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.fronticonsfreerooms);
        actionBar.show();

        schoolplace = (ListView)findViewById(R.id.school_place);
        getplacedata();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getplacedata(){
        EduHttpClient.get("district.php",null,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String placedata = new String(responseBody);
                try {
                    JSONArray placelist = new JSONArray(placedata);
                    for (int i = 0 ; i <placelist.length() ; i++){
                        JSONObject jsonObject = placelist.getJSONObject(i);
                        schoolcode.add(jsonObject.getString("districtCode"));
                        schoolname.add(jsonObject.getString("districtName"));
                    }

                    schoolListAdapter = new SchoolListAdapter(SchoolPlaceActivity.this,schoolname);

                    schoolplace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                     Intent intent = new Intent();
                            intent.setClass(SchoolPlaceActivity.this,BuildingActivity.class);
                            intent.putExtra("districtCode",schoolcode.get(i));
                            intent.putExtra("districtName",schoolname.get(i));
                            startActivity(intent);
                        }
                    });
                    schoolplace.setAdapter(schoolListAdapter);
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }
}
