package cn.edu.bistu.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;
import cn.edu.bistu.bistujobData.JobDetailType;
import android.content.Context;

public class OperateFileForMySaveJob {
	private static String path;

	public OperateFileForMySaveJob(Context context) {
		// TODO Auto-generated constructor stub
		path = context.getCacheDir().getPath() + File.separator + "MySave";
	}

	public void saveJob(String id, JobDetailType jobDetail) throws IOException {
		File foldFile = new File(path);
		if (!foldFile.exists()) {
			foldFile.mkdir();
		}
		File file = new File(path + File.separator + id);
		file.createNewFile();
		FileOutputStream outputStream = new FileOutputStream(file);
		@SuppressWarnings("resource")
		ObjectOutputStream outputStream2 = new ObjectOutputStream(outputStream);
		outputStream2.writeObject(jobDetail);
	}

	public List<JobDetailType> readJOb() throws StreamCorruptedException,
			IOException, ClassNotFoundException {
		List<JobDetailType> list = new ArrayList<JobDetailType>();
		File foldFile = new File(path);
		if (!foldFile.exists()) {
			foldFile.mkdir();
		}
		File[] files = foldFile.listFiles();
		System.out.println(files.length);
		if (files.length == 0) {
			return null;
		} else {
			for (int i = 0; i < files.length; i++) {
				JobDetailType jobDetailType = new JobDetailType();
				FileInputStream inputStream = new FileInputStream(files[i]);
				@SuppressWarnings("resource")
				ObjectInputStream inputStream2 = new ObjectInputStream(
						inputStream);
				jobDetailType = (JobDetailType) inputStream2.readObject();
				list.add(jobDetailType);
			}
			return list;
		}
	}

	public boolean deleteJob(String id) {
		File file = new File(path + File.separator + id);
		boolean isDelete = false;
		isDelete = file.delete();
		return isDelete;
	}
}
