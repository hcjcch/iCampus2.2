package cn.edu.bistu.secondhand;

import com.example.icampus2_2.R;

import cn.edu.bistu.secondhand.SecondHand;
import cn.edu.bistu.secondhand.Sell_Goods;
import cn.edu.bistu.tools.secondhandtools.HandlrGetData;
import cn.edu.bistu.tools.secondhandtools.HandlrGetData.onDataLoadListener;
import cn.edu.bistu.tools.secondhandtools.ViewPageImageAdapter;
import cn.trinea.android.common.service.impl.ImageCache;
import cn.trinea.android.common.util.CacheManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Goods_Detail extends Activity implements
		OnPageChangeListener {
	public static final ImageCache IMAGE_CACHE = CacheManager.getImageCache();
	TextView title, content, price, classes, campuses, time,qq,email,tel;
	int typeid = 0, xqdm = 0;
	ImageView pic;
	LinearLayout layout;
	ViewPager viewPager;
	ViewGroup group;
	String[] urls;
	private ImageView[] imageViews;
	int tag;
	String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods__detail);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setIcon(R.drawable.shopping_bag);
		actionBar.show();

		layout = (LinearLayout) findViewById(R.id.images);
		pic = (ImageView) findViewById(R.id.pic);
		content = (TextView) findViewById(R.id.content);
		title = (TextView) findViewById(R.id.title);
		classes = (TextView) findViewById(R.id.classes);
		campuses = (TextView) findViewById(R.id.campuses);
		time = (TextView) findViewById(R.id.time);
		price = (TextView) findViewById(R.id.detail_price);
		viewPager = (ViewPager) findViewById(R.id.image_pager);
		group = (ViewGroup) findViewById(R.id.viewGroup);
		qq=(TextView)findViewById(R.id.qq_detail);
		email=(TextView)findViewById(R.id.email_detail);
		tel=(TextView)findViewById(R.id.tel_detail);

		Bundle extras = getIntent().getExtras();
		typeid = Integer.parseInt(extras.getString("typeid"));
		xqdm = Integer.parseInt(extras.getString("xqdm"));
		tag = extras.getInt("tag");
		id = extras.getString("id");
		// 为适应数组

		// 防止错误数据
		if (typeid == 0) {
			typeid = 1;
		}
		if (xqdm == 0) {
			xqdm = 1;
		}
		typeid--;
		xqdm--;

		title.setText(extras.getString("title"));
		content.setText(extras.getString("description"));
		classes.setText(SecondHand.arr.get(typeid));
		campuses.setText(Sell_Goods.spot[xqdm]);
		time.setText(extras.getString("time"));
		
		qq.setText(extras.getString("QQ"));
		email.setText(extras.getString("email"));
		tel.setText(extras.getString("mobile"));
		
		// Log.v("111111111111","\n发布时间："+extras.getString("time"));
		price.setText("￥ "+extras.getString("price"));
		urls = extras.getString("pic").split(";");
		imageViews = new ImageView[urls.length];
		for (int i = 0; i < urls.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(8, 8));
			imageViews[i] = imageView;
			if (i == 0) {
				imageViews[i].setImageResource(R.drawable.page_indicator_focused);
			} else {
				imageViews[i].setImageResource(R.drawable.page_indicator_unfocused);
			}
			group.addView(imageView);
		}
		viewPager.setAdapter(new ViewPageImageAdapter(urls));
		viewPager.setOnPageChangeListener(this);
		viewPager.setCurrentItem(0);
	}
	

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// TODO Auto-generated method stub
			if (tag == 1) {
				MenuInflater inflater = getMenuInflater();
			    inflater.inflate(R.menu.delete, menu);	
		    	MenuItemCompat.setShowAsAction(menu.getItem(0), MenuItemCompat.SHOW_AS_ACTION_ALWAYS); 
			}
			
			return true;
			
		}
		
		
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.del:
		    deletepublish();
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		for (int i = 0; i < imageViews.length; i++) {
			if (i == arg0 % imageViews.length) {
               System.out.println(i);
				imageViews[i]
						.setImageResource(R.drawable.page_indicator_focused);
			} else {
				imageViews[i]
						.setImageResource(R.drawable.page_indicator_unfocused);
			}

		}
	}
	private void deletepublish() {
		HandlrGetData.data("http://m.bistu.edu.cn/newapi/secondhand_unvalid.php?id="+id,  new onDataLoadListener() {
			
			@Override
			public void onDataLoadr(String result) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}
