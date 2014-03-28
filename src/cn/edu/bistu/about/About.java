package cn.edu.bistu.about;

import java.util.HashMap;
import java.util.Map;

import com.example.icampus2_2.R;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

@SuppressLint("NewApi")
public class About extends Activity {
	private ListView aboutList;
	private String[] strings = { "学校简介", "历史沿革", "院系专业", "Credits", "ifLab" };
	private Map<String, String> map = new HashMap<String, String>();
	@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("关于");
		actionBar.show();
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		map.put("学校简介", "intro");
		map.put("历史沿革", "history");
		map.put("院系专业", "colleges");
		map.put("Credits", "credits");
		map.put("ifLab", "iflab");
		aboutList = (ListView) findViewById(R.id.aboutList);
		AboutAdapter adapter = new AboutAdapter(this,strings);
		aboutList.setAdapter(adapter);
		aboutList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("mod", map.get(strings[arg2]));
				intent.putExtra("title", strings[arg2]);
				intent.setClass(About.this, IntroCont.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
