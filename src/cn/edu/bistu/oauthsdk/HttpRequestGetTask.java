package cn.edu.bistu.oauthsdk;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class HttpRequestGetTask extends AsyncTask<Void, Void, Void> {
	private OpenBistuProvider provider;
	private Map<String,String> params;
	private String res ;
	private Context context;
	public HttpRequestGetTask(OpenBistuProvider provider,
			Map<String, String> params,Context context) {
		super();
		this.provider = provider;
		this.params = params;
		this.context = context;
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		String result2;
		Map<String,String> headers=new HashMap<String, String>();
		headers.put("Authorization", "Bearer 9b16620f-5034-4e0f-be9b-744f298c343b");
		headers.put("Host", "211.68.32.116");
		headers.put("API-RemoteIP", "211.68.39.125");
		/*params.put("parmater", "[{\"table\":\"building\",\"action\":\"list\"}]");
		params.put("type", "app");*/
		params.put("scope", "read");
		try {
			result2 = provider.doGet(params, true);
			if(result2!=null){
				res =  new String(result2.getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
 
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Toast.makeText(context, res, Toast.LENGTH_LONG).show();
	}
}
