package cn.edu.bistu.bistujobData;

import java.io.Serializable;

public class JobType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "JobType [id=" + id + ", type=" + type + "]";
	}

	public JobType(String id, String type) {
		super();
		this.id = id;
		this.type = type;
	}

	public JobType() {
		super();
		// TODO Auto-generated constructor stub
	}

}
