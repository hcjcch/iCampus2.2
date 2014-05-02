package cn.edu.bistu.bistujob;

import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import cn.edu.bistu.bistujobData.JobListType;
import cn.edu.bistu.bistujobData.JsonJobList;

import com.example.icampus2_2.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * A dummy fragment representing a section of the app, but that simply displays
 * dummy text.
 */

public class DummySectionFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private ListView listview;
	private String mod;
	private String id;
	private String url;
	List<JobListType> list;

	public DummySectionFragment() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_bistu_job_dummy,
				container, false);
		Bundle bundle = getArguments();
		mod = bundle.getString("mod");
		id = bundle.getString("id");
		if (id.equals("0")) {
			url = "http://m.bistu.edu.cn/newapi/job.php?mod="+mod;
		}else {
			url = "http://m.bistu.edu.cn/newapi/job.php?mod="+mod+"&&"+"typeid="+id;
		}
		listview = (ListView) rootView.findViewById(R.id.list_view);
		// set drop down listener
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("id", list.get(arg2).getId());
				intent.setClass(getActivity(), JobDetail.class);
				startActivity(intent);
			}
		});
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler(){

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				String information = new String(arg2);
				list = (new JsonJobList()).getList(information);
				FragmentAdapter adapter = new FragmentAdapter(list, getActivity());
				listview.setAdapter(adapter);
				super.onSuccess(arg0, arg1, arg2);
			}
			
		});
		return rootView;
	}
}
