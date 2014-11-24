package cn.edu.bistu.tools;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class downloadPicture {

	public static Bitmap loadPicture(String url) {
		Bitmap bitmap = null;
		if (url.equals(null)||url.equals("")||url == null) {
			return bitmap;
		}
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpRequest = new HttpGet(url);
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
		HttpConnectionParams.setSoTimeout(httpParams, 5000);
		httpRequest.setParams(httpParams);
		try {
			HttpResponse response = httpClient.execute(httpRequest);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return bitmap;
			}
			HttpEntity entity = response.getEntity();
			BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
			InputStream instream = bufHttpEntity.getContent();
			bitmap = BitmapFactory.decodeStream(instream);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			 httpClient.getConnectionManager().shutdown();
		}
		return bitmap;
	}

}
