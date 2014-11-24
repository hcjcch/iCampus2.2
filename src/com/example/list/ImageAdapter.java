package com.example.list;

import java.util.ArrayList;
import java.util.List;

import cn.edu.bistu.newsdata.NewsListType;

import com.example.icampus2_2.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<String> imageThumbUrls;
	private ImageDownLoader mImageDownLoader;
	private List<NewsListType> list;

	public ImageAdapter(Context context, ArrayList<String> imageThumbUrls,
			List<NewsListType> list, ImageDownLoader imageDownLoader) {
		this.context = context;
		this.imageThumbUrls = imageThumbUrls;
		mImageDownLoader = imageDownLoader;
		// listView.setOnScrollListener(this);
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageThumbUrls.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return imageThumbUrls.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public void add(List<NewsListType> list) {
		for (NewsListType newsListType : list) {
			this.list.add(newsListType);
		}
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHoder viewHoder = null;
		final String mImageUrl = imageThumbUrls.get(arg0);
		if (arg1 == null) {
			arg1 = LayoutInflater.from(context).inflate(R.layout.newslist_item,
					null);
			viewHoder = new ViewHoder();
			viewHoder.imageView = (ImageView) arg1
					.findViewById(R.id.newslistIcon);
			viewHoder.titleTextView = (TextView) arg1
					.findViewById(R.id.newslistTitle);
			viewHoder.preViewTextView = (TextView) arg1
					.findViewById(R.id.newslistpreView);
			arg1.setTag(viewHoder);
		} else {
			viewHoder = (ViewHoder) arg1.getTag();
		}
		viewHoder.titleTextView.setText(list.get(arg0).getTitle());
		viewHoder.preViewTextView.setText(list.get(arg0).getPreview());
		// viewHoder.imageView
		// .setLayoutParams(new ListView.LayoutParams(150, 150));
		viewHoder.imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

		// 给ImageView设置Tag,这里已经是司空见惯了
		viewHoder.imageView.setTag(mImageUrl);
		// ******************************* 去掉下面这几行试试是什么效果
		Bitmap bitmap = mImageDownLoader.getBitmapFromMemCache(mImageUrl
				.replaceAll("[^\\w]", ""));
		if (bitmap != null) {
			viewHoder.imageView.setImageBitmap(bitmap);
		} else {
			viewHoder.imageView.setImageDrawable(context.getResources()
					.getDrawable(R.drawable.logo));
		}
		// **********************************************************************************/
		return arg1;
	}

	static class ViewHoder {
		private ImageView imageView;
		private TextView titleTextView;
		private TextView preViewTextView;
	}
}
