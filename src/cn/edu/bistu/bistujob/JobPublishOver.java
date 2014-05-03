package cn.edu.bistu.bistujob;

import com.example.icampus2_2.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class JobPublishOver extends Activity {
	private TextView jobPublish;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.job_publish_over);
		intent = getIntent();
		jobPublish = (TextView) findViewById(R.id.jobpublish);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			intent.putExtra("jobpublish", "");
			setResult(RESULT_OK, intent);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.job_publish_over, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.job_publish_over:
			intent.putExtra("jobpublish", jobPublish.getText().toString());
			setResult(RESULT_OK, intent);
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
