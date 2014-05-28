package cn.edu.bistu.bistujob;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.bistu.bistujobData.JobDetailType;
import cn.edu.bistu.tools.OperateFileForMySaveJob;

import com.example.icampus2_2.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MySave extends Activity {
	private List<JobDetailType> list;
	private ListView listView;
	private MySaveAdapter adapter;
	private OperateFileForMySaveJob job;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_publish);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		listView = (ListView) findViewById(R.id.my_publish);
		list = new ArrayList<JobDetailType>();
		job = new OperateFileForMySaveJob(MySave.this);
		try {
			list = job.readJOb();
			if (list == null) {
				Toast.makeText(MySave.this, "您还没有收藏任何的工作哦！", Toast.LENGTH_SHORT).show();
				list = new ArrayList<JobDetailType>();
			}
			adapter = new MySaveAdapter(list);
			listView.setAdapter(adapter);
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("status", "2");
				intent.putExtra("detail", list.get(arg2));
				intent.setClass(MySave.this, JobDetail.class);
				startActivity(intent);
			}
		});
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(MySave.this)
						.setIcon(R.drawable.ic_action_about)
						.setTitle("删除？")
						.setPositiveButton("删除",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										job.deleteJob(list.get(arg2).getId());
										list.remove(arg2);
										adapter.notifyDataSetChanged();
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

									}
								}).show();
				return true;
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

	class MySaveAdapter extends BaseAdapter {

		private List<JobDetailType> listadapter;

		public MySaveAdapter(List<JobDetailType> listadapter) {
			// TODO Auto-generated constructor stub
			this.listadapter = listadapter;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listadapter.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return listadapter.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder;
			if (arg1 == null) {
				arg1 = LayoutInflater.from(MySave.this).inflate(
						R.layout.job_item, null);
				viewHolder = new ViewHolder();
				viewHolder.title = (TextView) arg1.findViewById(R.id.title);
				arg1.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) arg1.getTag();
			}
			try {
				viewHolder.title.setText(listadapter.get(arg0).getTitle());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return arg1;
		}

		class ViewHolder {
			TextView title;
		}
	}
}
