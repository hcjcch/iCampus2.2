package cn.edu.bistu.map;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.MyTrafficStyle;
import com.example.icampus2_2.ICampus;
import com.example.icampus2_2.R;

import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;

public class BistuMap extends Activity implements InfoWindowAdapter,
		OnMapLoadedListener, OnMarkerClickListener, LocationSource,
		AMapLocationListener{
	private MapView mapView;
	private AMap aMap;
	private static final LatLng JIANXIANGQIAO = new LatLng(39.989088, 116.38193);// 健翔桥经纬度
	private static final LatLng XIAOYING = new LatLng(40.037217, 116.347535);
	private static final LatLng QINGHE = new LatLng(40.044232, 116.342463);
	private static final LatLng JINGTAI = new LatLng(39.919, 116.47);
	private static final LatLng JIUXIOUQIAO = new LatLng(39.963, 116.49);
	private int maptype;
	private Marker marker;
	private boolean isShowTraffic = false;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// R 需要引用包import com.amapv2.apis.R;
		setContentView(R.layout.map);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 必须要写
		maptype = AMap.MAP_TYPE_NORMAL;
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.show();
		init();
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
		}
		aMap.setMapType(maptype);
		aMap.addMarker(new MarkerOptions()
				.position(JIANXIANGQIAO).title("健翔桥校区").perspective(true)
				.draggable(true).visible(true));
		aMap.addMarker(new MarkerOptions()
				.position(XIAOYING)
				.title("小营").snippet("校区")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
				.perspective(true).draggable(true));
		//marker.showInfoWindow();// 设置默认显示一个infowinfow
		aMap.addMarker(new MarkerOptions().position(QINGHE)
				.title("清河校区").perspective(true).draggable(true).visible(true));
		aMap.addMarker(new MarkerOptions().position(JINGTAI)
				.title("金台路校区").perspective(true).draggable(true).visible(true));
		aMap.addMarker(new MarkerOptions()
				.position(JIUXIOUQIAO).title("酒仙桥校区").perspective(true)
				.draggable(true).visible(true));
		
		MyTrafficStyle myTrafficStyle = new MyTrafficStyle();//设置实时交通
		myTrafficStyle.setSeriousCongestedColor(0xff92000a);
		myTrafficStyle.setCongestedColor(0xffea0312);
		myTrafficStyle.setSlowColor(0xffff7508);
		myTrafficStyle.setSmoothColor(0xff00a209);
		aMap.setMyTrafficStyle(myTrafficStyle);
		aMap.setTrafficEnabled(isShowTraffic);
		
		/*ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
		giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point1));
		giflist.add(BitmapDescriptorFactory.fromResource(R.draw
		able.point2));
		giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point3));
		giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point4));
		giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point5));
		giflist.add(BitmapDescriptorFactory.fromResource(R.drawable.point6));
		marker1 = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
				.icons(giflist).period(50));*/
		// 自定义系统定位小蓝点
		MyLocationStyle myLocationStyle = getMyLocationStyle();
		aMap.setMyLocationStyle(myLocationStyle);
		aMap.setMyLocationRotateAngle(180);
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		
		aMap.setOnMapLoadedListener(this);
		aMap.setOnMarkerClickListener(this);
		aMap.setInfoWindowAdapter(this);
	}

	public MyLocationStyle getMyLocationStyle(){
		MyLocationStyle myLocationStyle = new MyLocationStyle();
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory
				.fromResource(R.drawable.location_marker));// 设置小蓝点的图标
		myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
		myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
		// myLocationStyle.anchor(int,int)//设置小蓝点的锚点
		myLocationStyle.strokeWidth(0.1f);// 设置圆形的边框粗细
		return myLocationStyle;
	}
	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	public View getInfoContents(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getInfoWindow(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onMapLoaded() {
		// TODO Auto-generated method stub
		LatLngBounds bounds = new LatLngBounds.Builder().include(JIANXIANGQIAO)
				.include(XIAOYING).include(QINGHE).include(JINGTAI)
				.include(JIUXIOUQIAO).build();
		aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, ICampus.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;
		case R.id.qiehuan:
			if (maptype == AMap.MAP_TYPE_NORMAL) {
				aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
				maptype = AMap.MAP_TYPE_SATELLITE;
			} else {
				aMap.setMapType(AMap.MAP_TYPE_NORMAL);
				maptype = AMap.MAP_TYPE_NORMAL;
			}
			break;
		case R.id.traffic:
			if (isShowTraffic == false) {
				isShowTraffic = true;
			}else {
				isShowTraffic =false;
			}
			aMap.setTrafficEnabled(isShowTraffic);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		if (arg0 == marker) {
			aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(JIANXIANGQIAO, 1000));
		}
		return false;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(AMapLocation arg0) {
		// TODO Auto-generated method stub
		if (mListener != null && arg0 != null) {
			mListener.onLocationChanged(arg0);// 显示系统小蓝点
			/*marker1.setPosition(new LatLng(arg0.getLatitude(), arg0
					.getLongitude()));// 定位雷达小图标
*/			float bearing = aMap.getCameraPosition().bearing;
			aMap.setMyLocationRotateAngle(bearing);// 设置小蓝点旋转角度
		}
	}

	@Override
	public void activate(OnLocationChangedListener arg0) {
		// TODO Auto-generated method stub
		mListener = arg0;
		if (mAMapLocationManager  == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(this);
			/*
			 * mAMapLocManager.setGpsEnable(false);
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
			 * API定位采用GPS和网络混合定位方式
			 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
			 */
			mAMapLocationManager.requestLocationUpdates(
					LocationProviderProxy.AMapNetwork, 2000, 10, this);
		}
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destory();
		}
		mAMapLocationManager = null;
	}
}
