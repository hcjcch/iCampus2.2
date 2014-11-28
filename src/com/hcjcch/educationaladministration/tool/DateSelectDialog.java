package com.hcjcch.educationaladministration.tool;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;

import com.hcjcch.educationaladministration.activity.ClassroomActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pangrong on 2014/11/6.
 */
public class DateSelectDialog extends DialogFragment{

    private String datestring;

   public void setDate(String datestring){
       this.datestring = datestring;
   }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int year =1990;
        int month = 9;
        int day = 1;
        String[] time = datestring.split("-");
        year = Integer.valueOf(time[0]);
        month = Integer.valueOf(time[1]);
        Log.v("month",time[1]);
        day = Integer.valueOf(time[2]);
        return new DatePickerDialog(getActivity(),(ClassroomActivity)getActivity(),year,month-1,day);
    }
}
