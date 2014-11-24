package cn.edu.bistu.wifi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import android.content.Context;

public class StatusFile {
	private static String path;

	public StatusFile(Context context) {
		// TODO Auto-generated constructor stub
		path = context.getCacheDir().getPath() + File.separator
				+ "iCampusWifiStatus";
	}

	public boolean writeStatus(int status) {
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(path);
			try {
				outputStream.write(String.valueOf(status).getBytes());
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			try {
				outputStream.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public int readStatus() {
		File file = new File(path);
		StringBuilder sb = new StringBuilder();
		if (!file.exists()) {
			return 0;
		}
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(
					file));
			String s;
			try {
				while ((s = bufferedReader.readLine()) != null)
					sb.append(s);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				bufferedReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return Integer.parseInt(sb.toString());
	}
}
