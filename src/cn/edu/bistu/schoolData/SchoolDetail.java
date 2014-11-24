package cn.edu.bistu.schoolData;

public class SchoolDetail {
	private String id;
	private String mod;
	private String introName;
	private String introCont;
	private String href;
	private String rank;

	@Override
	public String toString() {
		return "SchoolDetail [id=" + id + ", mod=" + mod + ", introName="
				+ introName + ", introCont=" + introCont + ", href=" + href
				+ ", rank=" + rank + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMod() {
		return mod;
	}

	public void setMod(String mod) {
		this.mod = mod;
	}

	public String getIntroName() {
		return introName;
	}

	public void setIntroName(String introName) {
		this.introName = introName;
	}

	public String getIntroCont() {
		return introCont;
	}

	public void setIntroCont(String introCont) {
		this.introCont = introCont;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public SchoolDetail(String id, String mod, String introName,
			String introCont, String href, String rank) {
		super();
		this.id = id;
		this.mod = mod;
		this.introName = introName;
		this.introCont = introCont;
		this.href = href;
		this.rank = rank;
	}

	public SchoolDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

}
