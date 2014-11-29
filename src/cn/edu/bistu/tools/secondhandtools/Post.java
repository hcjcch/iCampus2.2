package cn.edu.bistu.tools.secondhandtools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import android.net.Uri;

public class Post {
	static String path="http://m.bistu.edu.cn/newapi/secondhand_add.php";
	
	 
	
	 
public static String submitPostData(Map<String, String> params, String encode) {
	
	
    byte[] data = getRequestData(params, encode).toString().getBytes();//���������
    try {         
    	URL url=new URL(path);
        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
        httpURLConnection.setConnectTimeout(3000);            //�������ӳ�ʱʱ��
        httpURLConnection.setDoInput(true);                  //�����������Ա�ӷ�������ȡ����
        httpURLConnection.setDoOutput(true);                 //����������Ա���������ύ����
        httpURLConnection.setRequestMethod("POST");          //������Post��ʽ�ύ����
        httpURLConnection.setUseCaches(false);               //ʹ��Post��ʽ����ʹ�û���
        //������������������ı�����
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        //����������ĳ���
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
        //�����������������д������
        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(data);
        
        int response = httpURLConnection.getResponseCode();            //��÷���������Ӧ��
        if(response == HttpURLConnection.HTTP_OK) {
            InputStream inptStream = httpURLConnection.getInputStream();
            return dealResponseResult(inptStream);                     //�������������Ӧ���
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return "";
}

/*
 * Function  :   ��װ��������Ϣ
 * Param     :   params���������ݣ�encode�����ʽ
 * Author    :   ����԰-���ɵ�Ȼ
 */
public static StringBuffer getRequestData(Map<String, String> params, String encode) {
    StringBuffer stringBuffer = new StringBuffer();        //�洢��װ�õ���������Ϣ
    try {
        for(Map.Entry<String, String> entry : params.entrySet()) {
            stringBuffer.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), encode))
                        .append("&");
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //ɾ������һ��"&"
    } catch (Exception e) {
        e.printStackTrace();
    }
    return stringBuffer;
}

/*
 * Function  :   �������������Ӧ�������������ת�����ַ�����
 * Param     :   inputStream����������Ӧ������
 * Author    :   ����԰-���ɵ�Ȼ
 */
public static String dealResponseResult(InputStream inputStream) {
    String resultData = null;      //�洢������
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    byte[] data = new byte[1024];
    int len = 0;
    try {
        while((len = inputStream.read(data)) != -1) {
            byteArrayOutputStream.write(data, 0, len);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    resultData = new String(byteArrayOutputStream.toByteArray());    
    return resultData;
}
}