package cn.edu.bistu.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckInformation {
	public static boolean checkQQ(String qq) {
		if (qq.length() < 5 || qq.length() > 11) {
			return false;
		}
		char[] qqs = qq.toCharArray();
		for (char c : qqs) {
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}

	public static boolean checkMail(String mail) {
		Pattern p = Pattern
				.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
		Matcher m = p.matcher(mail);
		boolean isMail = m.matches();
		return isMail;
	}

	public static boolean chaeckPhone(String phone) {
		if (phone.length() < 6 || phone.length() > 13) {
			return false;
		}
		char[] qqs = phone.toCharArray();
		for (char c : qqs) {
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}
	
}