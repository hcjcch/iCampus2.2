package cn.edu.bistu.secondhand;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

public class BaseActivity extends Activity {

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
