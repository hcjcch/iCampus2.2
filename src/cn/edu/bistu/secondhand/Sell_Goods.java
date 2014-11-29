package cn.edu.bistu.secondhand;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.icampus2_2.R;

import cn.edu.bistu.tools.secondhandtools.NoScrollGridView;
import cn.edu.bistu.tools.secondhandtools.Post;
import cn.edu.bistu.tools.secondhandtools.Upload;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.view.MenuItemCompat;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Sell_Goods extends Activity {
	public static String pic_urls = null;
	private static Uri photoUri;
	private File photo;
	Spinner spinner, spot_spinner;
	Bitmap bm;
	public List<Bitmap> bmp = new ArrayList<Bitmap>();
	public List<String> drr = new ArrayList<String>();
	public static String[] spot = new String[] { "小营", "清河", "健翔桥" };
	/* 上传到服务器的目录 */
	String requestURL = "http://jwcapp.bistu.edu.cn/upload.php";

	private String[] items = new String[] { "选择本地图片", "拍照" };
	int cardNumber, spotNumber;

	EditText title, price, desc, qq, email, tel;
	String result; // 获得服务器返回数据
	/* 请求码 */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	/* 头像名称 */
	private static final String IMAGE_FILE_NAME = "faceImage.jpg";
	private HorizontalScrollView selectimg_horizontalScrollView;
	private NoScrollGridView gridview;
	private GridAdapter adapter;
	private float dp;

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_sell__goods);

		title = (EditText) findViewById(R.id.title);
		price = (EditText) findViewById(R.id.price);
		desc = (EditText) findViewById(R.id.desc);
		qq = (EditText) findViewById(R.id.QQ);
		email = (EditText) findViewById(R.id.e_mail);
		tel = (EditText) findViewById(R.id.tel);

		selectimg_horizontalScrollView = (HorizontalScrollView) findViewById(R.id.selectimg_horizontalScrollView);
		gridview = (NoScrollGridView) findViewById(R.id.noScrollgridview);
		gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		dp = getResources().getDimension(R.dimen.dp);

		ActionBar actionBar = getActionBar();
		actionBar.setIcon(R.drawable.shopping_bag);
		actionBar.show();
		actionBar.setDisplayHomeAsUpEnabled(true);
		spinner = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<String> classadapter = new ArrayAdapter<String>(
				Sell_Goods.this, R.layout.list_items, SecondHand.arr);
		spinner.setAdapter(classadapter);
		spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				cardNumber = arg2;
				// 设置显示当前选择的项
				arg0.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				cardNumber = 0;
			}
		});

		spot_spinner = (Spinner) findViewById(R.id.spinner2);

		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				R.layout.list_items, spot);
		spot_spinner.setAdapter(adapter2);

		spot_spinner
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						spotNumber = arg2;
						// 设置显示当前选择的项
						arg0.setVisibility(View.VISIBLE);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						spotNumber = 0;
					}
				});

		griviewinit();
	}

	private void griviewinit() {
		adapter = new GridAdapter(this, bmp);
		adapter.setSelectedPosition(0);
		int size = 0;
		size = bmp.size() + 1;
		LayoutParams params = gridview.getLayoutParams();
		final int width = size * (int) (dp * 9.4f);
		params.width = width;
		gridview.setLayoutParams(params);
		gridview.setColumnWidth((int) (dp * 9.4f));
		gridview.setStretchMode(GridView.NO_STRETCH);
		gridview.setNumColumns(size);
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == bmp.size()) {
					String sdcardState = Environment.getExternalStorageState();
					if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
						showDialog();
					} else {
						Toast.makeText(Sell_Goods.this, "sdcard不存在?",
								Toast.LENGTH_SHORT).show();
					}
				} else {
				}
			}
		});

		selectimg_horizontalScrollView.getViewTreeObserver()
				.addOnPreDrawListener(new OnPreDrawListener() {
					public boolean onPreDraw() {
						selectimg_horizontalScrollView.scrollTo(width, 0);
						selectimg_horizontalScrollView.getViewTreeObserver()
								.removeOnPreDrawListener(this);
						return false;
					}
				});
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sell__goods, menu);
		MenuItemCompat.setShowAsAction(menu.getItem(0),
				MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.submit:
			String str_title = title.getText().toString();
			String str_price = price.getText().toString();
			String str_desc = desc.getText().toString();

			if (!str_title.isEmpty() && !str_price.isEmpty()
					&& !str_desc.isEmpty()/* &&!pic_url.isEmpty() */) {
				//if (pic_urls != null) {
					if (!qq.getText().toString().isEmpty()
							|| !email.getText().toString().isEmpty()
							|| !tel.getText().toString().isEmpty()) {
						cardNumber++;
						spotNumber++;

						final Map<String, String> params = new HashMap<String, String>();
						params.put("title", str_title);
						params.put("price", str_price);
						params.put("typeid", "" + cardNumber);
						params.put("description", str_desc);
						params.put("pic", pic_urls);
						params.put("xqdm", "" + spotNumber);
						params.put("QQ", qq.getText().toString());
						params.put("email", email.getText().toString());
						params.put("mobile", tel.getText().toString());

						params.put("userid", "1111");

						Runnable postRunnable = new Runnable() {

							public void run() {
								result = Post.submitPostData(params, "utf-8");
								Log.v("Post:", result);
							}
						};

						new Thread(postRunnable).start();
						pic_urls = null;

						Toast.makeText(getApplicationContext(), "发布成功！",
								Toast.LENGTH_SHORT).show();

						Intent intent = new Intent(Sell_Goods.this,
								SecondHand.class);
						startActivity(intent);
						finish();
						break;

					} else {
						Toast.makeText(getApplicationContext(), "至少留一种联系方式",
								Toast.LENGTH_SHORT).show();
					}

				/*} else {
					Toast.makeText(getApplicationContext(), "至少上传一张图片",
							Toast.LENGTH_SHORT).show();
				}*/

			} else {
				Toast.makeText(getApplicationContext(), "请完善信息！",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
		return super.onOptionsItemSelected(item);

	}

	// ********************************* 拍照 上传***************************
	private void showDialog() {

		new AlertDialog.Builder(this)
				.setTitle("设置头像")
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent openAlbumIntent = new Intent(
									Intent.ACTION_PICK,
									android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
							startActivityForResult(openAlbumIntent,
									IMAGE_REQUEST_CODE);
							break;
						case 1:

							Intent intent = new Intent(
									"android.media.action.IMAGE_CAPTURE");
							String pString = Environment
									.getExternalStorageDirectory()
									+ "/ibistutmp";
							final File photodir = new File(pString);
							if (!photodir.exists()) {

								photodir.mkdirs();

							}

							// TODO Auto-generated method stub
							photo = new File(photodir, "test.jpg");
							photoUri = Uri.fromFile(photo);
							intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
							startActivityForResult(intent, CAMERA_REQUEST_CODE);
							break;
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();

	}

	public void showpic(String picpath) {
		Bitmap bitmap = Bimp.getLoacalBitmap(picpath);
		bitmap = Bimp.createFramedPhoto(200, 200, bitmap, (int) (dp * 1.6f));
		drr.add(picpath);
		bmp.add(bitmap);
		griviewinit();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 结果码不等于取消时候
		if (resultCode != RESULT_CANCELED) {

			switch (requestCode) {
			case IMAGE_REQUEST_CODE:

				Uri uri = data.getData();
				String[] pojo = { MediaStore.Images.Media.DATA };
				Cursor cursor = getContentResolver().query(uri, pojo, null,
						null, null);
				String picPath = null;
				if (cursor != null) {
					int columnIndex = cursor.getColumnIndex(pojo[0]);
					cursor.moveToFirst();
					picPath = cursor.getString(columnIndex);
					cursor.close();
				}
				File photopic = new File(picPath);
				photoUri = Uri.fromFile(photopic);
				picpost(photoUri);

				break;
			case CAMERA_REQUEST_CODE:
				if (photoUri != null) {
					picpost(photoUri);
				} else {
					Toast.makeText(Sell_Goods.this, "拍照失败", Toast.LENGTH_LONG)
							.show();
				}
				break;
			case RESULT_REQUEST_CODE:
				if (data != null) {

				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	public void picpost(final Uri photoUri) {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if (msg.arg1 == 2) {
					showpic(photoUri.getPath());
				}

			}
		};

		Runnable connectRunnable = new Runnable() {

			@SuppressLint("NewApi")
			public void run() {
				try {

					Upload.uploadFile(
							Bimp.revitionImageSize(photoUri.getPath()),
							requestURL, getPhotoFileName(), Sell_Goods.this);
					Log.v("大小",
							String.valueOf(Bimp.revitionImageSize(
									photoUri.getPath()).getByteCount()));
					Message message = new Message();
					message.arg1 = 2;
					handler.sendMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		new Thread(connectRunnable).start();

	}
}
