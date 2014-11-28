package com.hcjcch.educationaladministration.oauthsdk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.hcjcch.educationaladministration.activity.OauthActivity;

/**
 * 锟�?锟斤拷骞冲彴璇锋眰鏈嶅姟鐖剁被
 *
 * @author suntq
 */
public abstract class BaseProvider {
    /**
     * 鍙傛暟绫诲瀷鏄疢AP
     */
    protected final int PARAM_TYPE_MAP = 1;
    /**
     * 鍙傛暟绫诲瀷鏄瓧绗︿覆
     */
    protected final int PARAM_TYPE_STRING = 2;
    /**
     * 鍙傛暟绫诲瀷鏄祦
     */
    protected final int PARAM_TYPE_STREAM = 3;
    /**
     * 璇锋眰鍦板潃
     */
    protected String _url;
    /**
     * 璇锋眰蹇呬紶鍙傛暟
     */
    protected Map<String, String> _mustParams;
    /**
     * 鍙戯拷?璇锋眰鐨勫簲鐢ㄤ俊鎭锟�?
     */
    protected OpenConsumer _consumer;//appid ,appsecret

    protected Context _context;

    public String get_url() {
        return _url;
    }

    public void set_url(String _url) {
        this._url = _url;
    }

    public Map<String, String> get_mustParams() {
        return _mustParams;
    }

    public void set_mustParams(Map<String, String> _mustParams) {
        this._mustParams = _mustParams;
    }

    public OpenConsumer get_consumer() {
        return _consumer;
    }

    public void set_consumer(OpenConsumer _consumer) {
        this._consumer = _consumer;
    }

    public Context get_context() {
        return _context;
    }

    public void set_context(Context _context) {
        this._context = _context;
    }

    /**
     * POST璇锋眰
     *
     * @param url       璇锋眰鍦板潃
     * @param params    璇锋眰闈炲繀浼犲弬锟�? * @param mustParams 璇锋眰蹇呬紶鍙傛暟
     * @param headers   鑷畾涔夎姹傚ご
     * @param content   璇锋眰瀛楃锟�? * @param charSet 璇锋眰瀛楃涓茬紪鐮佹牸锟�? * @param inputStream 璇锋眰鍙傛暟锟�?
     *                  * @param inputStreamLength 璇锋眰鍙傛暟娴侀暱锟�? * @param needAccessToken
     *                  鏄惁锟�?锟斤拷鎺堟潈
     * @param paramType 姝ゆ璇锋眰鍙傛暟绫诲瀷
     * @return
     * @throws Exception
     */
    protected HttpResponse doPostReturnResponse(String url,
                                                Map<String, String> params, Map<String, String> mustParams,
                                                Map<String, String> headers, String content, String charSet,
                                                InputStream inputStream, Long inputStreamLength,
                                                boolean needAccessToken, int paramType) throws Exception {
        HttpResponse result = null;
        mustParams = addMustParams(mustParams, mustParams, needAccessToken);
        if (mustParams == null) {
            return null;
        }
        String headUrl = URLUtil.createUrl(url, mustParams);//鐢熸垚url
        HttpPost httpPost = new HttpPost(headUrl);
        setHeader(headers, httpPost);
        switch (paramType) {
            case PARAM_TYPE_MAP:
                List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
                Set<String> keySet = params.keySet();
                for (String key : keySet) {
                    NameValuePair value = new BasicNameValuePair(key,
                            params.get(key));
                    paramsList.add(value);
                }
                HttpEntity requestEntity = new UrlEncodedFormEntity(paramsList);
                httpPost.setEntity(requestEntity);
                result = getResponse(httpPost);
                break;
            case PARAM_TYPE_STRING:
                StringEntity entity = new StringEntity(content, charSet);
                httpPost.setEntity(entity);
                result = getResponse(httpPost);
                break;
            case PARAM_TYPE_STREAM:
                InputStreamEntity streamEntity = new InputStreamEntity(inputStream,
                        inputStreamLength);
                httpPost.setEntity(streamEntity);
                result = getResponse(httpPost);
                break;
            default:
                break;
        }

        return result;
    }

    /**
     * GET璇锋眰
     *
     * @param url
     * @param params 鐢ㄦ埛鑷畾涔夊弬锟�? * @param mustParams 蹇呬紶鍙傛暟
     * @return
     * @throws Exception
     */
    protected String doGet(String url, Map<String, String> params,
                           Map<String, String> mustParams, Map<String, String> headers,
                           boolean needAccessToken) throws Exception {
        Map<String, String> addParams = new HashMap<String, String>();
        String result = null;
        mustParams = addMustParams(mustParams, params, needAccessToken);
        if (mustParams == null) {
            return null;
        }
        if (params != null) {
            addParams.putAll(params);
        }
        addParams.putAll(mustParams);
        String requestUrl = URLUtil.createUrl(url, addParams);
        HttpGet httpGet = new HttpGet(requestUrl);
        setHeader(headers, httpGet);
        result = execute(httpGet);
        return result;
    }

    /**
     * 灏嗚嚜瀹氫箟HEADER瑁呰浇鍏ヨ姹傚璞′腑
     *
     * @param headers
     * @param request
     */
    protected void setHeader(Map<String, String> headers, HttpUriRequest request) {
        if (headers != null) {
            Set<String> keySet = headers.keySet();
            for (String key : keySet) {
                request.setHeader(key, headers.get(key));
            }
        }
    }

    /**
     * 鎵ц璇锋眰
     *
     * @param request
     * @return
     */
    protected String execute(HttpUriRequest request) {
        String result = null;
        HttpResponse httpResponse = null;
        httpResponse = getResponse(request);
        if (httpResponse != null) {
            result = getResultStringFromResponse(httpResponse);
        }
        return result;
    }

    /**
     * 鑾峰彇鏈嶅姟绔搷锟�? * @param request
     *
     * @return
     */
    protected HttpResponse getResponse(HttpUriRequest request) {
        HttpResponse httpResponse = null;
        HttpClient httpClient = HttpManager.getHttpClient();
        try {
            httpResponse = httpClient.execute(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    /**
     * 鑾峰彇鏈嶅姟绔搷搴旂殑鍐呭
     *
     * @param httpResponse
     * @return
     */
    protected String getResultStringFromResponse(HttpResponse httpResponse) {
        String result = null;
        if (httpResponse != null) {
            result = getResultFromResponse(httpResponse.getEntity());
        }
        return result;
    }

    /**
     * 璇诲彇鏈嶅姟鍣ㄥ搷搴旂殑缁撴灉
     *
     * @param entity
     * @return
     */
    protected String getResultFromResponse(HttpEntity entity) {
        InputStream inputStream = null;
        String result = "";
        try {
            if (entity != null) {
                inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
                URLDecoder decoder = new URLDecoder();
                result = decoder.decode(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 娣诲姞蹇呰鐨勫弬锟�? * @param mustParams
     *
     * @param params
     * @param needAccessToken
     * @return
     * @throws Exception
     */
    protected Map<String, String> addMustParams(Map<String, String> mustParams,
                                                Map<String, String> params, boolean needAccessToken)
            throws Exception {
        if (needAccessToken) {
            String accessToken = getAccessToken();
            if (accessToken == null || accessToken.trim().equals("")) {
                Log.e("9009", "could not find access_token");
                Intent intent = new Intent(_context, OauthActivity.class);
                intent.putExtra(OpenConsumer.CONSUMER, _consumer);
                _context.startActivity(intent);
                return null;
            } else {
                mustParams.put(Contants.RequestParamName.ACCESS_TOKEN,
                        accessToken);//娣诲姞access鈥斺�攖oken
                mustParams.put(
                        Contants.RequestParamName.DIGEST,
                        ParamsUtil.getDigest(params, mustParams,
                                _consumer.getAppSecret()));
            }
        } else {
            mustParams.put(
                    Contants.RequestParamName.DIGEST,
                    ParamsUtil.getDigest(params, mustParams,
                            _consumer.getAppSecret()));
        }
        return mustParams;
    }

    /**
     * 浠庢湰鍦拌鍙朅CCESS_TOKEN
     *
     * @return
     * @throws Exception
     */
    protected String getAccessToken() throws Exception {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(_context);
        String accessToken = OauthUtil.getAccessToken(prefs);
        return accessToken;
    }

}
