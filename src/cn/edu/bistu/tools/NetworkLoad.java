package cn.edu.bistu.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class NetworkLoad {

	public String GetGetInformation(String url) throws ClientProtocolException,
			IOException {
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = client.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			return EntityUtils.toString(entity, "UTF-8");
		}
		return null;
	}

	public String PostGetInformation(String url, Map<String, String> map)
			throws ClientProtocolException, IOException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Set<Map.Entry<String, String>> set = map.entrySet();
		Iterator<Map.Entry<String, String>> iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator
					.next();
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		HttpResponse httpResponse = client.execute(post);
		HttpEntity entity = httpResponse.getEntity();
		if (entity != null) {
			return EntityUtils.toString(entity, "UTF-8");
		}
		return null;
	}
}
