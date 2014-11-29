package cn.edu.bistu.secondhand;


import java.util.List;

import com.example.icampus2_2.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GridAdapter extends BaseAdapter {
    
	private Context context;
	private List<Bitmap> bmp;
	private int selectedPosition = -1;
	
	public GridAdapter(Context context,List<Bitmap> bmp) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.bmp = bmp;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bmp.size() + 1;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	public class ViewHolder {
		public ImageView image;
	}

	public void setSelectedPosition(int position) {
		selectedPosition = position;
	}

	public int getSelectedPosition() {
		return selectedPosition;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (arg1 == null) {
			holder = new ViewHolder();
			arg1 = LayoutInflater.from(context).inflate(R.layout.item_publish, null);
			holder.image = (ImageView)arg1.findViewById(R.id.item_grida_image);
			arg1.setTag(holder);
			
		} else {
           holder = (ViewHolder)arg1.getTag();
		}
		if (arg0 == bmp.size()) {
			holder.image.setImageBitmap(BitmapFactory.decodeResource(
					context.getResources(), R.drawable.icon_addpic_unfocused));
		} else {
			holder.image.setImageBitmap(bmp.get(arg0));
		}
		return arg1;
	}

}
