package cn.edu.bistu.oauthsdk;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Config {
	private static Properties props = new Properties(); 
	   static String profilepath="ibistu_config.properties";
	static{
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getValue(String key){
		return props.getProperty(key);
	}
	 
     public static void updateProperties(String keyname,String keyvalue) {       
        try {
              OutputStream fos = new FileOutputStream(Thread.currentThread().getContextClassLoader().getResource("ibistu_config.properties").getFile());
        	  props.setProperty(keyname, keyvalue);
              props.store(fos, "Update '" + keyname + "' value");
               fos.close();
         } catch (IOException e) {}
     }

}
