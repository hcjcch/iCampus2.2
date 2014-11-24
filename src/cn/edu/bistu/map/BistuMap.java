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
	private static final LatLng JIANXIANGQIAO = new LatLng(39.989088, 116.38193);// �����ž�γ��
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
		// R ��Ҫ���ð�import com.amapv2.apis.R;
		setContentView(R.layout.map);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// ����Ҫд
		maptype = AMap.MAP_TYPE_NORMAL;
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.show();
		init();
	}

	/**
	 * ��ʼ��AMap����
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
		}
		aMap.setMapType(maptype);
		aMap.addMarker(new MarkerOptions()
				.position(JIANXIANGQIAO).title("������У��").perspective(true)
				.draggable(true).visible(true));
		aMap.addMarker(new MarkerOptions()
				.position(XIAOYING)
				.title("СӪ").snippet("У��")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
				.perspective(true).draggable(true));
		//marker.showInfoWindow();// ����Ĭ����ʾһ��infowinfow
		aMap.addMarker(new MarkerOptions().position(QINGHE)
				.title("���У��").perspective(true).draggable(true).visible(true));
		aMap.addMarker(new MarkerOptions().position(JINGTAI)
				.title("��̨·У��").perspective(true).draggable(true).visible(true));
		aMap.addMarker(new MarkerOptions()
				.position(JIUXIOUQIAO).title("������У��").perspective(true)
				.draggable(true).visible(true));
		
		MyTrafficStyle myTrafficStyle = new MyTrafficStyle();//����ʵʱ��ͨ
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
		// �Զ���ϵͳ��λС����
		MyLocationStyle myLocationStyle = getMyLocationStyle();
		aMap.setMyLocationStyle(myLocationStyle);
		aMap.setMyLocationRotateAngle(180);
		aMap.setLocationSource(this);// ���ö�λ����
		aMap.getUiSettings().setMyLocationButtonEnabled(false);// ����Ĭ�϶�λ��ť�Ƿ���ʾ
		aMap.setMyLocationEnabled(true);// ����Ϊtrue��ʾ��ʾ��λ�㲢�ɴ�����λ��false��ʾ���ض�λ�㲢���ɴ�����λ��Ĭ����false
		
		aMap.setOnMapLoadedListener(this);
		aMap.setOnMarkerClickListener(this);
		aMap.setInfoWindowAdapter(this);
	}

	public MyLocationStyle getMyLocationStyle(){
		MyLocationStyle myLocationStyle = new MyLocationStyle();
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory
				.fromResource(R.drawable.location_marker));// ����С�����ͼ��
		myLocationStyle.strokeColor(Color.BLACK);// ����Բ�εı߿���ɫ
		myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// ����Բ�ε������ɫ
		// myLocationStyle.anchor(int,int)//����С�����ê��
		myLocationStyle.strokeWidth(0.1f);// ����Բ�εı߿��ϸ
		return myLocationStyle;
	}
	/**
	 * ����������д
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * ����������д
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * ����������д
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * ����������д
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
			mListener.onLocationChanged(arg0);// ��ʾϵͳС����
			/*marker1.setPosition(new LatLng(arg0.getLatitude(), arg0
					.getLongitude()));// ��λ�״�Сͼ��
*/			float bearing = aMap.getCameraPosition().bearing;
			aMap.setMyLocationRotateAngle(bearing);// ����С������ת�Ƕ�
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
			 * 1.0.2�汾��������������true��ʾ��϶�λ�а���gps��λ��false��ʾ�����綨λ��Ĭ����true Location
			 * API��λ����GPS�������϶�λ��ʽ
			 * ����һ�������Ƕ�λprovider���ڶ�������ʱ�������2000���룬������������������λ���ף����ĸ������Ƕ�λ������
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
