package cn.edu.bistu.tools.secondhandtools;


import cn.edu.bistu.secondhand.Goods_Detail;

import com.example.icampus2_2.R;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class ViewPageImageAdapter extends PagerAdapter{
	
	private String[] urls;
	
    public ViewPageImageAdapter(String[] urls) {
		// TODO Auto-generated constructor stub
    	this.urls = urls;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE;
	}
	@Override
	public View instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		final ImageView imageView = new ImageView(container.getContext());
		container.addView(imageView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		imageView.setImageResource(R.drawable.icampusimgplaceholder);
		Goods_Detail.IMAGE_CACHE.get(urls[position%urls.length], imageView);
		return imageView;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}
}
