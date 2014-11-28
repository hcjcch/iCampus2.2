package com.hcjcch.educationaladministration.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.icampus2_2.R;
import com.hcjcch.educationaladministration.bean.ClassRoomItem;
import com.hcjcch.educationaladministration.utils.EduHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pangrong on 2014/11/5.
 */
public class RoomListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ClassRoomItem> rooms;

    public RoomListAdapter(Context context){
        this.context = context;
    }
    public void setRooms(ArrayList<ClassRoomItem> rooms){
        this.rooms = rooms;
    }

    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public Object getItem(int i) {
        return rooms.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    class ViewHodler{
        public TextView text;
        public ImageView[] image = new ImageView[13];
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHodler hodler;
        //if (view == null) {
            hodler = new ViewHodler();
            view = LayoutInflater.from(context).inflate(R.layout.class_item,null);
            hodler.text = (TextView)view.findViewById(R.id.roomNames);
            hodler.image[0] = (ImageView)view.findViewById(R.id.One);
            hodler.image[1] = (ImageView)view.findViewById(R.id.Two);
            hodler.image[2] = (ImageView)view.findViewById(R.id.Three);
            hodler.image[3] = (ImageView)view.findViewById(R.id.Four);
            hodler.image[4] = (ImageView)view.findViewById(R.id.Fifth);
            hodler.image[5] = (ImageView)view.findViewById(R.id.Six);
            hodler.image[6] = (ImageView)view.findViewById(R.id.Seven);
            hodler.image[7] = (ImageView)view.findViewById(R.id.Eight);
            hodler.image[8] = (ImageView)view.findViewById(R.id.Nine);
            hodler.image[9] = (ImageView)view.findViewById(R.id.Ten);
            hodler.image[10] = (ImageView)view.findViewById(R.id.Eleven);
            hodler.image[11] = (ImageView)view.findViewById(R.id.shier);
            hodler.image[12] = (ImageView)view.findViewById(R.id.thirth);
            view.setTag(hodler);
      /*  }else {
            hodler = (ViewHodler)view.getTag();
        }*/
        int n = rooms.get(i).getSjd().size();
        hodler.text.setText(rooms.get(i).getRoomNames());
            for (int j = 0;j<n;j++){
                int s = Integer.valueOf(rooms.get(i).getSjd().get(j));
                if (s < 12){
                    hodler.image[s-1].setImageResource(R.drawable.mark2);
                    hodler.image[s].setImageResource(R.drawable.mark2);
                }

            }
        return view;
    }
}
