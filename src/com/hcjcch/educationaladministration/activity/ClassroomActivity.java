package com.hcjcch.educationaladministration.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.icampus2_2.R;
import com.hcjcch.educationaladministration.adapter.RoomListAdapter;
import com.hcjcch.educationaladministration.bean.ClassRoomItem;
import com.hcjcch.educationaladministration.tool.DateSelectDialog;
import com.hcjcch.educationaladministration.utils.EduHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ClassroomActivity extends Activity implements DatePickerDialog.OnDateSetListener{

    private ListView roomList;
    private String buildingCode,buildingName;
    private RoomListAdapter roomListAdapter;
    private ArrayList<ClassRoomItem> roomNames;
    private ArrayList<ClassRoomItem> searchNames;
    private TextView dateview;
    private DateSelectDialog dateSelectDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_list);
        Intent intent = getIntent();
        buildingName = intent.getStringExtra("buildingName");
        buildingCode = intent.getStringExtra("buildingCode");
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(buildingName);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.fronticonsfreerooms);
        actionBar.show();
        roomList = (ListView)findViewById(R.id.room_list);
        dateview = (TextView)findViewById(R.id.date);
        dateSelectDialog = new DateSelectDialog();
        roomNames = new ArrayList<ClassRoomItem>();
        searchNames = new ArrayList<ClassRoomItem>();
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String datestring = format.format(date);
        dateSelectDialog.setDate(datestring);
        dateview.setText(datestring);
        getroomdata(datestring);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.date_select, menu);
            MenuItemCompat.setShowAsAction(menu.getItem(0), MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
            MenuItemCompat.setShowAsAction(menu.getItem(1), MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
            SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
            searchView.setIconifiedByDefault(true);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchNames.clear();
                    for (int i = 0; i < roomNames.size();i++){
                        ClassRoomItem classRoomItem = roomNames.get(i);
                        Pattern pattern = Pattern.compile(query);
                        Matcher matcher = pattern.matcher(roomNames.get(i).getRoomNames().split("-")[2]);
                        if (matcher.find()){
                            searchNames.add(classRoomItem);
                        }
                    }
                    if(query.equals("")){
                        roomListAdapter.setRooms(roomNames);
                    }else{
                        roomListAdapter.setRooms(searchNames);
                    }

                    roomList.setAdapter(roomListAdapter);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.select_date:
                dateSelectDialog.show(getFragmentManager(),"datePicker");
                break;
            case R.id.search:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    private void getroomdata(final String date){

        EduHttpClient.get("classroom.php?buildingCode="+buildingCode+"&date="+date,null,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                  String roomdata = new String(responseBody);
                try {
                    JSONArray jsonArray = new JSONArray(roomdata);
                    for (int i = 0;i<jsonArray.length();i++){
                        ClassRoomItem classRoomItem = new ClassRoomItem();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        classRoomItem.setRoomNames(jsonObject.getString("jsmc"));
                        classRoomItem.setJsbn(jsonObject.getString("jsbh"));
                        JSONArray xkkh = jsonObject.getJSONArray("xkkh");
                         for (int j = 0;j<xkkh.length();j++){
                             JSONObject object = xkkh.getJSONObject(j);
                             String sjd = object.getString("sjd");
                             classRoomItem.getSjd().add(sjd);
                         }
                        roomNames.add(classRoomItem);

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

                roomListAdapter = new RoomListAdapter(ClassroomActivity.this);
                roomListAdapter.setRooms(roomNames);
                roomList.setAdapter(roomListAdapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String dateString = Integer.toString(year) + "-"
                + Integer.toString((monthOfYear + 1)) + "-" + Integer.toString(dayOfMonth);
        dateview.setText(dateString);
        roomNames.clear();
        dateSelectDialog.setDate(dateString);
        getroomdata(dateString);
    }
}
