package cn.edu.bistu.schoolData;

import java.io.Serializable;

public class School implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String mod;
	private String introName;

	public School(String id, String mod, String introName) {
		super();
		this.id = id;
		this.mod = mod;
		this.introName = introName;
	}

	public School() {
		super();
		// TODO Auto-generated constructor stub
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

	@Override
	public String toString() {
		return "School [id=" + id + ", mod=" + mod + ", introName=" + introName
				+ "]";
	}
	
}
