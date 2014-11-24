package cn.edu.bistu.newsdata;

import java.io.Serializable;

import android.graphics.Bitmap;

public class NewsListType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String passageId;
	private String title;
	private String preview;
	private String author;
	private String lastUpdate;
	private String icon;
	private String detailUrl;
	private Bitmap bitmap = null;
	private boolean isDownloading = true;
	@Override
	public String toString() {
		return "NewsListType [passageId=" + passageId + ", title=" + title
				+ ", preview=" + preview + ", author=" + author
				+ ", lastUpdate=" + lastUpdate + ", icon=" + icon
				+ ", detailUrl=" + detailUrl + "]";
	}

	public String getPassageId() {
		return passageId;
	}

	public void setPassageId(String passageId) {
		this.passageId = passageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPreview() {
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public boolean isDownloading() {
		return isDownloading;
	}

	public void setIsDownloading(boolean a) {
		this.isDownloading = a;
	}
}
