package cn.edu.bistu.aboutData;

import cn.edu.bistu.newsdata.JsonNewschannel;

public class Intro {
	public String getIntro(){
		JsonNewschannel newschannel = new JsonNewschannel();
		String s = newschannel.getNewsChannel("http://m.bistu.edu.cn/newapi/intro.php?mod=intro");
		return s;
	}
}
