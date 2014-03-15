package cn.edu.bistu.newsdata;
public class NewschannelType {
	
	private String id;
	private String title;
	private String pageType;
	private String lastUpdate;
	private String icon;
	private String listUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
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

	public String getListUrl() {
		return listUrl;
	}

	public void setListUrl(String listUrl) {
		this.listUrl = listUrl;
	}
	
	@Override
	public String toString() {
		return "NewchannelType [id=" + id + ", title=" + title + ", pageType="
				+ pageType + ", lastUpdate=" + lastUpdate + ", icon=" + icon
				+ ", listUrl=" + listUrl + "]";
	}

}
