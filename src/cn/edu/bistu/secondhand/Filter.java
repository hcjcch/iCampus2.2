package cn.edu.bistu.secondhand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.example.icampus2_2.R;

import cn.edu.bistu.tools.secondhandtools.GetData;
import cn.edu.bistu.tools.secondhandtools.JsonDeal;
import cn.edu.bistu.tools.secondhandtools.KindArrayAdapter;
import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Filter extends Activity {

	int url_kind = 1, url_spot = 1;
	KindArrayAdapter adapter;
	ListView listView;
	private List<SecondHandItem> handItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
		ActionBar actionBar = getActionBar();
		actionBar.setIcon(R.drawable.shopping_bag);
		actionBar.show();
		actionBar.setDisplayHomeAsUpEnabled(true);
		final TextView choosed1 = (TextView) findViewById(R.id.choosed1);
		final TextView choosed2 = (TextView) findViewById(R.id.choosed2);
		listView = (ListView) findViewById(R.id.list_result);
		listView.addHeaderView(new RelativeLayout(this));
		handItems = new ArrayList<SecondHandItem>();
		findViewById(R.id.choose_place).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						AlertDialog.Builder alert = new AlertDialog.Builder(
								Filter.this)
                        
						.setSingleChoiceItems(Sell_Goods.spot, 0,
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int position) {
										// TODO Auto-generated method stub
										choosed1.setText(Sell_Goods.spot[position]);
										position++;
										url_spot = position;

									}
								});
						alert.setNegativeButton("确定", null);
						alert.create().show();

					}
				});

		findViewById(R.id.classify).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alert = new AlertDialog.Builder(Filter.this)

				.setSingleChoiceItems(SecondHand.arr
						.toArray(new String[SecondHand.arr.size()]), 0,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int position) {
								// TODO Auto-generated method stub

								choosed2.setText(SecondHand.arr.get(position));
								position++;
								url_kind = position;
							}
						});
				alert.setNegativeButton("确定", null);
				alert.create().show();

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.filter, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		switch (id) {
		case R.id.finish:
			Log.v("Server :",
					"http://m.bistu.edu.cn/newapi/secondhand.php?xqdm="
							+ url_spot + "&typeid=" + url_kind);
			new GetDataTask()
					.execute("http://m.bistu.edu.cn/newapi/secondhand.php?xqdm="
							+ url_spot + "&typeid=" + url_kind);
			break;
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	private class GetDataTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {

			String result = null;
			try {

				result = new GetData(params[0]).Deal();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return result;

		}

		@Override
		protected void onPostExecute(String result) {
			if (result.equals("[]")) {
				Toast.makeText(getApplicationContext(), "没搜索到哦",
						Toast.LENGTH_SHORT).show();
			}
			handItems = JsonDeal.getseondhanditem(result);
			adapter = new KindArrayAdapter(handItems, Filter.this);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new DrawerItemClickListener(
					handItems, Filter.this, 0));

			super.onPostExecute(result);
		}
	}

}
