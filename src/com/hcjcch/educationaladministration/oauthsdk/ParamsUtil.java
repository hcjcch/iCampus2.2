package com.hcjcch.educationaladministration.oauthsdk;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;

@SuppressLint("SimpleDateFormat")
public class ParamsUtil {
    public static String getTimeStamp() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(new Date());
    }

    public static String getDigest(Map<String, String> params, Map<String, String> mustParams, String appSecret) throws UnsupportedEncodingException {
        Map<String, String> allParams = new HashMap<String, String>();
        if (params != null) {
            allParams.putAll(params);
        }
        if (mustParams != null) {
            allParams.putAll(mustParams);
        }
        Set<String> keySet = allParams.keySet();
        List<String> sortList = new ArrayList<String>();
        for (String key : keySet) {
            sortList.add(key + allParams.get(key));
        }
        Collections.sort(sortList);
        StringBuffer strBuffer = new StringBuffer();
        for (String param : sortList) {
            strBuffer.append(param);
        }
        strBuffer.append(appSecret);
        return md5(URLEncoder.encode(strBuffer.toString(), "utf-8"));
    }

    /**
     * MD5加密
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        try {
            MessageDigest alga = MessageDigest.getInstance("MD5");
            alga.update(str.getBytes());
            byte[] digest = alga.digest();
            return byte2hex(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        for (byte aB : b) {
            String stmp = Integer.toHexString(aB & 0xFF);
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

}
