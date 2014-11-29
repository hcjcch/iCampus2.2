package cn.edu.bistu.tools.secondhandtools;
 
import java.util.List;






import com.example.icampus2_2.R;

import cn.edu.bistu.secondhand.Goods_Detail;
import cn.edu.bistu.secondhand.SecondHandItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class KindArrayAdapter extends BaseAdapter
{
	// 需要包装的JSONArray
	private List<SecondHandItem> kindArray;
	private Context ctx;
	String price,title;

	
	public KindArrayAdapter(List<SecondHandItem> kindArray
		,Context ctx )    // where = 0 表示在主页list;where = 1 表示进入详情
	{
		this.kindArray = kindArray;
		this.ctx = ctx;
	}
	@Override
	public int getCount()
	{
		// 返回ListView包含的列表项的数量
		if(kindArray==null)
		{
			return 0;
		}else
		return kindArray.size();
	}

	


 /*   private class ViewHolder{
    	public TextView tv_title,tv_price,goods_id,xqdm,description,pic,time,typeid;
    	public ImageView imageView;
    }*/
/*	@Override
	public View getView(int position, View convertView,
		ViewGroup parent)
	{
		ViewHolder holder;
		if(kindArray==null)
		{
			return null;
		}
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater flater = LayoutInflater.from(ctx);
			convertView = flater.inflate(R.layout.listitems, null);

			 holder.tv_title = (TextView)convertView.findViewById(R.id.commodity);
			 holder.tv_price = (TextView)convertView.findViewById(R.id.goodprice);		 
			 holder.goods_id = (TextView)convertView.findViewById(R.id.goods_id);
			 holder.xqdm = (TextView)convertView.findViewById(R.id.xqdm);
			 holder.description = (TextView)convertView.findViewById(R.id.description);
			 holder.pic = (TextView)convertView.findViewById(R.id.picurl);
			 holder.time = (TextView)convertView.findViewById(R.id.time);
			 holder.typeid = (TextView)convertView.findViewById(R.id.typeid);
			 holder.imageView = (ImageView)convertView.findViewById(R.id.imageView1);
			 convertView.setTag(holder);
		} else {
             holder = (ViewHolder)convertView.getTag();
		}
		
		 
		try
		{
			 //在主页listview
			 price = ((JSONObject)getItem(position)).getString("price");
			 title = ((JSONObject)getItem(position)).getString("titile");
			 String[] urls = ((JSONObject)getItem(position)).getString("pic").split(";");
			 holder.imageView.setTag(urls[0]);
			 holder.goods_id.setText(((JSONObject)getItem(position)).getString("id")); 
			 holder.xqdm.setText (((JSONObject)getItem(position)).getString("xqdm"));
			 holder.description.setText (((JSONObject)getItem(position)).getString("description"));
		    if (holder.imageView.getTag() != null && holder.imageView.getTag().equals(urls[0]) ) {
				// Goods_Detail.IMAGE_CACHE.get(urls[0], holder.imageView);
			}
			
			try {
				if (urls[0] != null && urls.length > 1 ) {
					 Log.v("图片", urls[0]+"##########");
					 Picasso.with(ctx).load(urls[0]).into(holder.imageView);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			 
			 
		     holder.pic.setText(((JSONObject)getItem(position)).getString("pic"));
			 holder.time.setText(((JSONObject)getItem(position)).getString("time"));
			 holder.typeid.setText(((JSONObject)getItem(position)).getString("typeid"));
			 
			 holder.tv_title.setText(title);//标题
			 holder.tv_price.setText("￥ "+price);

		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return convertView;
		
	}*/
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		if(kindArray==null)
		{
			return null;
		}
		convertView = LayoutInflater.from(ctx).inflate(R.layout.listitems, null);
		TextView tv_title = (TextView)convertView.findViewById(R.id.commodity);
		TextView tv_price = (TextView)convertView.findViewById(R.id.goodprice);
		TextView xqdm = (TextView)convertView.findViewById(R.id.xqdm);
		ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView1);

			 price = (kindArray.get(position).getTv_price());
			 title = (kindArray.get(position).getTv_title());
			 String[] urls = kindArray.get(position).getPic().split(";");
			 //xqdm.setText (((JSONObject)getItem(position)).getString("xqdm"));		 
	      imageView.setTag(urls[0]);
		 tv_title.setText(title);//标题
		 tv_price.setText("￥ "+price);
		if (imageView.getTag() != null && imageView.getTag().equals(urls[0]) ) {
				Goods_Detail.IMAGE_CACHE.get(urls[0], imageView);
			}
		return convertView;
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return kindArray.get(arg0);
	}
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	

	
	
}

