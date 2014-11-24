package cn.edu.bistu.tools;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class AsyncClientControl {
	private AsyncHttpClient client;
	private callBack back;
	private String url;

	public AsyncClientControl(AsyncHttpClient client, callBack back,String url) {
		// TODO Auto-generated constructor stub
		this.client = client;
		this.back = back;
		this.url = url;
	}

	public void getData() {
		client.get(url, new AsyncHttpResponseHandler() {
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1, arg2, arg3);
				back.aferFaile();
			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				back.start();
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1, arg2);
				String string = null;
				try {
					string = new String(arg2,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					System.out.println("×ªÂë´íÎó");
					e.printStackTrace();
				}
				back.afterSucceess(string);
			}
		});
	}

	public interface callBack {
		void afterSucceess(String string);

		void aferFaile();

		void start();
	}
}