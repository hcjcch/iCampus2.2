package cn.edu.bistu.tools.secondhandtools;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import android.os.Handler;
import android.os.Message;


public class HandlrGetData {

       public static void data(final String url,final onDataLoadListener dataLoadListener){
    	   final Handler classhandler = new Handler(){
   			@Override
   			public void handleMessage(Message msg) {
   				// TODO Auto-generated method stub
   				super.handleMessage(msg);
   				String result = (String) msg.obj;
   				dataLoadListener.onDataLoadr(result);
 			}
   		};
   		Runnable runnable = new Runnable() {
   			
   			@Override
   			public void run() {
   				// TODO Auto-generated method stub
   				Message message = new Message();
   				try {
   					message.obj = new GetData(url).Deal();
   					classhandler.sendMessage(message);
   				} catch (ClientProtocolException e) {
   					// TODO Auto-generated catch block
   					e.printStackTrace();
   				} catch (IOException e) {
   					// TODO Auto-generated catch block
   					e.printStackTrace();
   				}
   			}
   		};
   		new Thread(runnable).start();

       }
       public interface onDataLoadListener {
   		void onDataLoadr(String result);
   	}
}
