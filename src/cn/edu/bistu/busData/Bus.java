package cn.edu.bistu.busData;

public class Bus {
	private String id;
	private String catName;
	private String catIntro;
	private CatBus catbus;
	public Bus(String id, String catName, String catIntro, CatBus catbus) {
		super();
		this.id = id;
		this.catName = catName;
		this.catIntro = catIntro;
		this.catbus = catbus;
	}
	public Bus() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public String getCatIntro() {
		return catIntro;
	}
	public void setCatIntro(String catIntro) {
		this.catIntro = catIntro;
	}
	public CatBus getCatbus() {
		return catbus;
	}
	public void setCatbus(CatBus catbus) {
		this.catbus = catbus;
	}
	@Override
	public String toString() {
		return "Bus [id=" + id + ", catName=" + catName + ", catIntro="
				+ catIntro + ", catbus=" + catbus + "]";
	}
	
}
