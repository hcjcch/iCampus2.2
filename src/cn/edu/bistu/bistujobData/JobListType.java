package cn.edu.bistu.bistujobData;

public class JobListType {
	private String id;
	private String title;
	private String typeid;
	private String location;
	private String salary;
	private String time;

	public String getId() {
		return id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public JobListType(String id, String title, String typeid, String location,
			String salary, String time) {
		super();
		this.id = id;
		this.title = title;
		this.typeid = typeid;
		this.location = location;
		this.salary = salary;
		this.time = time;
	}

	public JobListType() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "JobListType [id=" + id + ", title=" + title + ", typeid="
				+ typeid + ", location=" + location + ", salary=" + salary
				+ ", time=" + time + "]";
	}

}
