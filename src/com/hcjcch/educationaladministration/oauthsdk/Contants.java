package com.hcjcch.educationaladministration.oauthsdk;

/**
 * ����
 *
 * @author lizy
 */
public class Contants {

    public static class MapParamName {
        public static String CLIENT_ID = "client_id";
        public static String CLIENT_SECRET = "client_secret";
        public static String RESPONSE_TYPE = "response_type";
        public static String REDIRECT_URI = "redirect_uri";
        public static String GRANT_TYPE = "grant_type";
        public static String SCOPE = "scope";
    }

    public static class RequestParamName {
        public static String ACCESS_TOKEN = "access_token";
        public static String APP_KEY = "appkey";
        public static String TIME_STAMP = "timestamp";
        public static String DIGEST = "digest";
    }

    public static class ResponseParamName {
        public static String ACCESS_TOKEN = "access_token";
        public static String TOKEN_TYPE = "token_type";
        public static String REFRESH_TOKEN = "refresh_token";
        public static String EXPIRES_IN = "expires_in";
    }

}
