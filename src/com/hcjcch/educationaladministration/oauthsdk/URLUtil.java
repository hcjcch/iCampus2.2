package com.hcjcch.educationaladministration.oauthsdk;

import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

public class URLUtil {
    /**
     * 创建URL
     *
     * @param baseUrl
     * @param addParams
     * @return
     * @throws Exception
     * @author lizy
     */
    public static String createUrl(String baseUrl, Map<String, String> addParams) throws Exception {
        String uri = null;
        if (addParams != null) {
            StringBuffer uriBuffer = new StringBuffer();
            uriBuffer.append(baseUrl);
            uriBuffer.append("?");
            Set<String> keySet = addParams.keySet();
            for (String key : keySet) {
                if (!uriBuffer.toString().equals(baseUrl + "?")) {
                    uriBuffer.append("&");
                }
                if (addParams.get(key) != null) {
                    uriBuffer.append(URLEncoder.encode(key, "utf-8"));
                    uriBuffer.append("=");
                    uriBuffer.append(URLEncoder.encode(addParams.get(key), "utf-8"));
                }
            }
            uri = uriBuffer.toString();
        }
        return uri;
    }

}
