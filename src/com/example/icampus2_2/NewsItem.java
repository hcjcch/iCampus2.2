package com.example.icampus2_2;

import java.util.ArrayList;
import java.util.List;

import com.example.list.ImageAdapter;
import com.example.list.ImageDownLoader;
import com.example.list.ImageDownLoader.onImageLoaderListener;

import cn.edu.bistu.newsdata.JsonNewsList;
import cn.edu.bistu.newsdata.NewsListType;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;

public class NewsItem extends Fragment {
	private ListView listView = null;
	private int currentPage;
	private ImageAdapter adapter;
	private ArrayList<String> urlList;
	private ImageDownLoader mImageDownLoader;
	private boolean isFirstEnter;
	private int mFirstVisibleItem;
	private int mVisibleItemCount;
	private int totalItem;
	private FrameLayout frameLayout;
	private List<NewsListType> newsList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View contextView = inflater.inflate(R.layout.fragment_item, container,
				false);
		listView = (ListView) contextView.findViewById(R.id.newsListView);
		newsList = new ArrayList<NewsListType>();
		urlList = new ArrayList<String>();
		currentPage = 1;
		isFirstEnter = true;
		mImageDownLoader = new ImageDownLoader(getActivity());
		// 获取Activity传递过来的参数
		Bundle mBundle = getArguments();
		final String url = mBundle.getString("url");
		final String tititle = mBundle.getString("bundle");
		LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(
				getActivity()).inflate(R.layout.footer, null);
		frameLayout = (FrameLayout) linearLayout.findViewById(R.id.footer);
		ImageView imageView = (ImageView) frameLayout
				.findViewById(R.id.footer_imageview);
		Animation animation = AnimationUtils.loadAnimation(getActivity(),
				R.anim.load);
		imageView.setAnimation(animation);
		listView.addFooterView(linearLayout);
		(new NewsListAsyncTask()).execute(url);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), NewsDetail.class);
				intent.putExtra("detailUrl", newsList.get(arg2).getDetailUrl());
				intent.putExtra("tititle", tititle);
				startActivity(intent);
			}
		});
		listView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				// 仅当GridView静止时才去下载图片，GridView滑动时取消所有正在下载的任务
				if ((scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE)
						&& (mFirstVisibleItem + mVisibleItemCount == totalItem)) {
					(new NewsListAsyncTask()).execute(url);
				}
				try {
					if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
						showImage(mFirstVisibleItem, mVisibleItemCount);
					} else {
						cancelTask();
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				mFirstVisibleItem = firstVisibleItem;
				mVisibleItemCount = visibleItemCount;
				totalItem = totalItemCount;
				// 因此在这里为首次进入程序开启下载任务。
				if (isFirstEnter && visibleItemCount > 0) {
					showImage(mFirstVisibleItem, mVisibleItemCount);
					isFirstEnter = false;
				}
			}
		});
		return contextView;
	}

	private void showImage(int firstVisibleItem, int visibleItemCount) {
		// 注：firstVisibleItem + visibleItemCount-1 = 20 1其中包括了footview，这儿一定要小心！
		for (int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount
				- 1; i++) {
			String mImageUrl = urlList.get(i);
			final ImageView mImageView = (ImageView) listView
					.findViewWithTag(mImageUrl);
			mImageDownLoader.getBitmap(mImageUrl, new onImageLoaderListener() {

				public void onImageLoader(Bitmap bitmap, String url) {
					if (mImageView != null && bitmap != null) {
						mImageView.setImageBitmap(bitmap);
					}
				}
			});
		}
	}

	public void cancelTask() {
		mImageDownLoader.cacelTask();
	}

	class NewsListAsyncTask extends
			AsyncTask<String, String, List<NewsListType>> {
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected List<NewsListType> doInBackground(String... params) {
			// TODO Auto-generated method stub
			JsonNewsList jsonNewsList = new JsonNewsList();
			List<NewsListType> list = new ArrayList<NewsListType>();
			list = jsonNewsList
					.getList("http://api.bistu.edu.cn/api/api.php?table=newslist&url="
							+ params[0] + "&index=" + currentPage);
			return list;
		}

		@Override
		protected void onPostExecute(List<NewsListType> list) {
			// TODO Auto-generated method stub
			if (JsonNewsList.state == 1) {
				for (NewsListType type : list) {
					urlList.add(type.getIcon());
					newsList.add(type);
				}
				if (currentPage == 1) {
					adapter = new ImageAdapter(getActivity(), urlList, list,
							mImageDownLoader);
					listView.setAdapter(adapter);
					currentPage++;
				} else {
					adapter.add(list);
					frameLayout.setVisibility(View.VISIBLE);
					adapter.notifyDataSetChanged();
					currentPage++;
				}
			} else if (JsonNewsList.state == -1) {
				Toast.makeText(getActivity(), "No more Data",
						Toast.LENGTH_SHORT).show();
				frameLayout.setVisibility(View.GONE);
			} else if (JsonNewsList.state == 0) {
				Toast.makeText(getActivity(), "无法访问网络！", Toast.LENGTH_SHORT)
						.show();
				frameLayout.setVisibility(View.GONE);
			} else {
				Toast.makeText(getActivity(), "数据解析失败！", Toast.LENGTH_SHORT)
						.show();
				frameLayout.setVisibility(View.GONE);
			}
		}

	}
}