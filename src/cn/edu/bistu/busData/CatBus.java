package cn.edu.bistu.busData;

import java.util.ArrayList;
import java.util.List;

public class CatBus {
	private List<EveryBus> catbus;

	public CatBus() {
		super();
		// TODO Auto-generated constructor stub
		catbus = new ArrayList<EveryBus>();
	}

	public CatBus(List<EveryBus> catbus) {
		super();
		this.catbus = catbus;
	}

	public List<EveryBus> getCatbus() {
		return catbus;
	}

	public void setCatbus(List<EveryBus> catbus) {
		this.catbus = catbus;
	}

	@Override
	public String toString() {
		return "CatBus [catbus=" + catbus + "]";
	}
	
	
}
