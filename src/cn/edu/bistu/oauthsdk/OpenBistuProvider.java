package cn.edu.bistu.oauthsdk;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;

import android.content.Context;


/**
 * �?��平台请求服务�? * @author lizy
 *
 */
public class OpenBistuProvider extends BaseProvider{
	
	final int PARAM_TYPE_MAP=1;
	final int PARAM_TYPE_STRING=2;
	final int PARAM_TYPE_STREAM=3;
	
	public OpenBistuProvider(String url,OpenConsumer consumer,Map<String, String> mustParams,Context context) {
		super();
		this._url = url;
		this._mustParams = mustParams;
		this._mustParams.put(Contants.RequestParamName.APP_KEY,_consumer.getAppId());
		this._mustParams.put(Contants.RequestParamName.TIME_STAMP, ParamsUtil.getTimeStamp());
		this._consumer=consumer;
		this._context=context;
	}
	
	public OpenBistuProvider(String url,OpenConsumer consumer,Context context){
		super();
		this._url=url;
		this._consumer=consumer;
		this._context=context;
		this._mustParams=new HashMap<String, String>();
		this._mustParams.put(Contants.RequestParamName.APP_KEY,_consumer.getAppId());
		this._mustParams.put(Contants.RequestParamName.TIME_STAMP, ParamsUtil.getTimeStamp());
	}
	/**
	 * GET请求
	 * @param params 请求参数
	 * @param needAccessToken 是否�?��授权
	 * @return
	 * @throws Exception
	 */
	public String doGet(Map<String,String> params,boolean needAccessToken)throws Exception{
		return this.doGet(_url, params, _mustParams,null,needAccessToken);
	}
	/**
	 * GET请求
	 * @param params 请求参数
	 * @param headers 自定义请求头
	 * @param needAccessToken 是否�?��授权
	 * @return
	 * @throws Exception
	 */
	public String doGet(Map<String,String> params,Map<String,String> headers,boolean needAccessToken)throws Exception{
		return this.doGet(_url, params, _mustParams,headers,needAccessToken);
	}
	/**
	 * POST请求
	 * @param params 请求参数
	 * @param needAccessToken 是否�?��授权
	 * @return
	 * @throws Exception
	 */
	public String doPost(Map<String,String> params,boolean needAccessToken)throws Exception{
		return this.getResultStringFromResponse(this.doPostResponse(params,needAccessToken));
	}
	/**
	 * POST请求
	 * @param headers 自定义请求头
	 * @param params 请求参数
	 * @param needAccessToken 是滞�?��授权
	 * @return
	 * @throws Exception
	 */
	public String doPost(Map<String,String> headers,Map<String,String> params,boolean needAccessToken)throws Exception{
		return this.getResultStringFromResponse(this.doPostResponse(headers,params,needAccessToken));
	}
	/**
	 * POST请求
	 * @param headers 自定义请求头
	 * @param content 请求参数自定义字符串或协�?	 * @param charSet 参数编码
	 * @param needAccessToken 是否�?��授权
	 * @return
	 * @throws Exception
	 */
	public String doPost(Map<String,String> headers,String content,String charSet,boolean needAccessToken)throws Exception{
		return this.getResultStringFromResponse(this.doPostResponse(headers, content, charSet, needAccessToken));
	}
	/**
	 * POST请求
	 * @param content 请求参数自定义字符串或协�?	 * @param charSet 参数编码
	 * @param needAccessToken 是否�?��授权
	 * @return
	 * @throws Exception
	 */
	public String doPost(String content,String charSet,boolean needAccessToken)throws Exception{
		return this.getResultStringFromResponse(this.doPostResponse(content, charSet, needAccessToken));
	}
	/**
	 * POST请求
	 * @param inputStream 请求参数�?	 * @param length 参数流长�?	 * @param needAccessToken 是否�?��授权
	 * @return
	 * @throws Exception
	 */
	public String doPost(InputStream inputStream,long length,boolean needAccessToken)throws Exception{
		return this.getResultStringFromResponse(this.doPostResponse(inputStream, length, needAccessToken));
	}
	
	public HttpResponse doPostResponse(Map<String,String> params,boolean needAccessToken)throws Exception{
		return this.doPostReturnResponse(_url, params, _mustParams,null,null,null,null,null,needAccessToken,PARAM_TYPE_MAP);
	}
	
	public HttpResponse doPostResponse(Map<String,String> headers,Map<String,String> params,boolean needAccessToken)throws Exception{
		return this.doPostReturnResponse(_url, params, _mustParams,headers,null,null,null,null,needAccessToken,PARAM_TYPE_MAP);
	}
	
	public HttpResponse doPostResponse(Map<String,String> headers,String content,String charSet,boolean needAccessToken)throws Exception{
		return this.doPostReturnResponse(_url,null, _mustParams,headers,content,charSet,null,null,needAccessToken,PARAM_TYPE_STRING);
	}
	
	public HttpResponse doPostResponse(String content,String charSet,boolean needAccessToken)throws Exception{
		return this.doPostReturnResponse(_url, null, _mustParams,null,content,charSet,null,null,needAccessToken,PARAM_TYPE_STRING);
	}
	
	public HttpResponse doPostResponse(InputStream inputStream,long length,boolean needAccessToken)throws Exception{
		return this.doPostReturnResponse(_url, null, _mustParams,null,null,null,inputStream,length,needAccessToken,PARAM_TYPE_STREAM);
	}
}
