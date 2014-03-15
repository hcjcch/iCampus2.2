package cn.edu.bistu.school;

import java.util.ArrayList;

import org.apache.http.Header;

import cn.edu.bistu.schoolData.JsonSchoolDetail;
import cn.edu.bistu.schoolData.School;
import cn.edu.bistu.schoolData.SchoolDetail;

import com.example.icampus2_2.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class SchoolDetailShow extends Activity {
	private TextView school;
	private TextView titile;
	private ImageButton left;
	private ImageButton right;
	private ArrayList<School> schools;
	private int position;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.school_detail);
		init();
		Intent intent = getIntent();
		schools = (ArrayList<School>) intent
				.getSerializableExtra("schoolList");
		position = intent.getIntExtra("position", 0);
		show(position);
	}

	private void show(int position){
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(
				"http://api.bistu.edu.cn/api/api.php?table=collegeintro&action=detail&mod="
						+ schools.get(position).getMod() + "&id="
						+ schools.get(position).getId(),
				new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1, arg2, arg3);
					}

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1, arg2);
						String information = new String(arg2);
						JsonSchoolDetail jsonSchoolDetail = new JsonSchoolDetail();
						SchoolDetail schoolDetail = jsonSchoolDetail
								.getList(information);
						school.setText(schoolDetail.getIntroCont());
						titile.setText(schoolDetail.getIntroName());
					}
				});
	}
	class Click implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.newsLeftButton:
				if (position > 0 ) {
					show(--position);
					right.setEnabled(true);
				}
				if (position == 0) {
					left.setEnabled(false);
				}
				break;

			case R.id.newsRightButton:
				if (position<schools.size()-1) {
					show(++position);
					left.setEnabled(true);
				}
				if (position == schools.size()-1) {
					right.setEnabled(false);
				}
				break;
			default:
				break;
			}
		}

	}

	private void init() {
		school = (TextView) findViewById(R.id.schoolDetail);
		titile = (TextView) findViewById(R.id.schoolText);
		left = (ImageButton) findViewById(R.id.newsLeftButton);
		right = (ImageButton) findViewById(R.id.newsRightButton);
		left.setOnClickListener(new Click());
		right.setOnClickListener(new Click());
	}
}
