package cn.edu.bistu.newsdata;

public class DetailResource {
	/*
	 * n:资源名称 t:资源类型 url:资源url
	 */
	private String n;
	private String t;
	private String url;

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "DetailResource [n=" + n + ", t=" + t + ", url=" + url + "]";
	}

}
