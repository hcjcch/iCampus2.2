package cn.edu.bistu.tools.secondhandtools;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.edu.bistu.secondhand.Sell_Goods;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

public class Upload {

	 public static void uploadFile(Bitmap file,String uploadUrl,String name,Context context)
	  {
	    String end = "\r\n";
	    String twoHyphens = "--";
	    String boundary = "******";
	    String pic_url = null;
	    try
	    {
	      URL url = new URL(uploadUrl);
	      HttpURLConnection httpURLConnection = (HttpURLConnection) url
	          .openConnection();
	      // 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
	      // 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。
	      httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
	      // 允许输入输出流
	      httpURLConnection.setDoInput(true);
	      httpURLConnection.setDoOutput(true);
	      httpURLConnection.setUseCaches(false);
	      // 使用POST方法
	      httpURLConnection.setRequestMethod("POST");
	      httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
	      httpURLConnection.setRequestProperty("Charset", "UTF-8");
	      httpURLConnection.setRequestProperty("Content-Type",
	          "multipart/form-data;boundary=" + boundary);

	      DataOutputStream dos = new DataOutputStream(
	          httpURLConnection.getOutputStream());
	      dos.writeBytes(twoHyphens + boundary + end);
	      dos.writeBytes("Content-Disposition: form-data; name=\"imgFile\"; filename=\"" + name+ "\"" + end);
	      dos.writeBytes(end);

	      ByteArrayOutputStream baos = new ByteArrayOutputStream();  
          file.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
          InputStream fis = new ByteArrayInputStream(baos.toByteArray());
	      
	      
	      byte[] buffer = new byte[8192]; // 8k
	      int count = 0;
	      // 读取文件
	      while ((count = fis.read(buffer)) != -1)
	      {
	        dos.write(buffer, 0, count);
	      }
	      fis.close();

	      dos.writeBytes(end);
	      dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
	      dos.flush();

	      InputStream is = httpURLConnection.getInputStream();
	      InputStreamReader isr = new InputStreamReader(is, "utf-8");
	      BufferedReader br = new BufferedReader(isr);
	      String result = br.readLine();
	      //Log.v("", msg)
	  
	      dos.close();
	      is.close();
          String urlorerr = new JsonDeal(result,context).resultdeal();
          if (!urlorerr.equals("error") ) {
        	  pic_url="http://jwcapp.bistu.edu.cn"+urlorerr; 
    	      if (Sell_Goods.pic_urls != null) {
    	    	  Sell_Goods.pic_urls = Sell_Goods.pic_urls+";"+pic_url;
    		} else {
    			Sell_Goods.pic_urls = pic_url;
    		}
    	      
    	      Log.v("返回值",Sell_Goods.pic_urls);
		}
	      
	      
	    } catch (Exception e)
	    {
	      e.printStackTrace();
	     
	    }
	  }
}
