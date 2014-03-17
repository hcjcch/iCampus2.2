package cn.edu.bistu.busData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BusLine implements Serializable{
	private List<Map<String, String>> maps;

	public BusLine() {
		super();
		// TODO Auto-generated constructor stub
		maps = new ArrayList<Map<String, String>>();
	}

	public BusLine(List<Map<String, String>> maps) {
		super();
		this.maps = maps;
	}

	public List<Map<String, String>> getMaps() {
		return maps;
	}

	public void setMaps(List<Map<String, String>> maps) {
		this.maps = maps;
	}

	@Override
	public String toString() {
		return "BusLine [maps=" + maps + "]";
	}

}
