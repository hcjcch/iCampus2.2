package cn.edu.bistu.bistujobData;

public class JobType {
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
