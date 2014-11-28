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

public class BuildingActivity extends Activity{

    private ListView buildplace;
    private SchoolListAdapter listAdapter;
    private String districtCode;
    private ArrayList<String> buildingCodes,buildingNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_place);
        Intent intent = getIntent();
        districtCode = intent.getStringExtra("districtCode");
        buildingCodes = new ArrayList<String>();
        buildingNames = new ArrayList<String>();

        ActionBar actionBar = getActionBar();
        actionBar.setTitle(intent.getStringExtra("districtName"));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.fronticonsfreerooms);
        actionBar.show();

        buildplace = (ListView)findViewById(R.id.school_place);


        getbuilddata();
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
    private void getbuilddata(){
        EduHttpClient.get("building.php?districtCode="+districtCode,null,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String builddata = new String(responseBody);
                try {
                    JSONArray jsonArray = new JSONArray(builddata);
                    for (int i = 0;i< jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        buildingCodes.add(jsonObject.getString("buildingCode"));
                        buildingNames.add(jsonObject.getString("buildingName"));
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                buildplace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent();
                        intent.putExtra("buildingName",buildingNames.get(i));
                        intent.putExtra("buildingCode",buildingCodes.get(i));
                        intent.setClass(BuildingActivity.this,ClassroomActivity.class);
                        startActivity(intent);
                    }
                });
                listAdapter = new SchoolListAdapter(BuildingActivity.this,buildingNames);
                buildplace.setAdapter(listAdapter);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}

