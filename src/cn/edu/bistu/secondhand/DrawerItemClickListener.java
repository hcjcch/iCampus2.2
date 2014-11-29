package cn.edu.bistu.secondhand;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;


public class DrawerItemClickListener implements OnItemClickListener {
	private List<SecondHandItem> handItems;
	private Context context;
	private int tag;
	
	public DrawerItemClickListener(List<SecondHandItem> handItems,Context context,int tag) {
		// TODO Auto-generated constructor stub
		this.handItems = handItems;
		this.context = context;
		this.tag = tag;
	}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int arg0, long id) {
    	int i = arg0-1;
    	
    	Intent intent=new Intent(context,Goods_Detail.class);
		 intent.putExtra("title",handItems.get(i).getTv_title());
		 intent.putExtra("price",handItems.get(i).getTv_price());
    	 intent.putExtra("id",handItems.get(i).getGoods_id());
       	 intent.putExtra("xqdm",handItems.get(i).getXqdm());
    	 intent.putExtra("description",handItems.get(i).getDescription());
    	 intent.putExtra("pic",handItems.get(i).getPic());
    	 intent.putExtra("time",handItems.get(i).getTime());
    	 intent.putExtra("typeid",handItems.get(i).getTypeid());
    	 intent.putExtra("QQ", handItems.get(i).getQQ());
    	 intent.putExtra("email", handItems.get(i).getEmail());
    	 intent.putExtra("mobile", handItems.get(i).getTel());
    	 intent.putExtra("tag", tag);
    	
    	context.startActivity(intent);
    }

}
