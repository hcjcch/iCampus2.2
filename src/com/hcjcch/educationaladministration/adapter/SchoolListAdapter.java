package com.hcjcch.educationaladministration.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import com.example.icampus2_2.R;

/**
 * Created by pangrong on 2014/11/2.
 */
public class SchoolListAdapter  extends BaseAdapter{

    private Context context;
    private List<String> schoollist;

    public SchoolListAdapter(Context context,ArrayList<String> schoollist){
        this.context = context;
        this.schoollist = schoollist;
    }

    @Override
    public int getCount() {
        return schoollist.size();
    }

    @Override
    public Object getItem(int i) {
        return schoollist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHodler viewHodler;
        if (view == null){
            viewHodler = new ViewHodler();
            view = LayoutInflater.from(context).inflate(R.layout.school_item,null);
            viewHodler.text = (TextView)view.findViewById(R.id.schoolitem);
            view.setTag(viewHodler);
        }else {
           viewHodler = (ViewHodler)view.getTag();
        }
         viewHodler.text.setText(schoollist.get(i));
        return view;
    }
    class ViewHodler{
        public TextView text;
    }
}
