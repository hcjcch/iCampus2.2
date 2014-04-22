package cn.edu.bistu.oauthsdk;

import java.util.HashMap;
import java.util.Map;

import android.os.AsyncTask;
import android.widget.TextView;

public class HttpRequestTask extends AsyncTask<Void, Void, String> {
	private OpenBistuProvider provider;
	private Map<String,String> params;
	private TextView tv;

	public HttpRequestTask(Map<String,String> params, OpenBistuProvider provider,TextView tv) {
		super();
		this.params = params;
		this.provider = provider;
		this.tv=tv;
	}

	@Override
	protected String doInBackground(Void... arg0) {
		String result="";
		try {
			//String result=provider.doPost(params);
			Map<String,String> headers=new HashMap<String, String>();
			headers.put("head1", "suntq");
			headers.put("head3", "suntq3");
			Map<String,String> params=new HashMap<String,String>();
			params.put("param1", "中国");
			params.put("param2", "china");
			
			String result2=provider.doPost(headers,params,true);
			if(result2!=null){
				System.out.println(result2);
			}
			result=result2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		tv.setText(result);
	}

}
