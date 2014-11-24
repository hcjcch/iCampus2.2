package cn.edu.bistu.bistujobData;

import java.io.Serializable;

public class JobDetailType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String title;
	private String typeid;
	private String description;
	private String location;
	private String qualifications;
	private String salary;
	private String company;
	private String contactName;
	private String contactEmail;
	private String contactPhone;
	private String contactQQ;
	private String userid;
	private String time;

	public JobDetailType(String id, String title, String typeid,
			String description, String location, String qualifications,
			String salary, String company, String contactName,
			String contactEmail, String contactPhone, String contactQQ,
			String userid, String time) {
		super();
		this.id = id;
		this.title = title;
		this.typeid = typeid;
		this.description = description;
		this.location = location;
		this.qualifications = qualifications;
		this.salary = salary;
		this.company = company;
		this.contactName = contactName;
		this.contactEmail = contactEmail;
		this.contactPhone = contactPhone;
		this.contactQQ = contactQQ;
		this.userid = userid;
		this.time = time;
	}

	public JobDetailType() {
		super();
		// TODO Auto-generated constructor stub
	}

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

	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getQualifications() {
		return qualifications;
	}

	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactQQ() {
		return contactQQ;
	}

	public void setContactQQ(String contactQQ) {
		this.contactQQ = contactQQ;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "JobDetailType [id=" + id + ", title=" + title + ", typeid="
				+ typeid + ", description=" + description + ", location="
				+ location + ", qualifications=" + qualifications + ", salary="
				+ salary + ", company=" + company + ", contactName="
				+ contactName + ", contactEmail=" + contactEmail
				+ ", contactPhone=" + contactPhone + ", contactQQ=" + contactQQ
				+ ", userid=" + userid + ", time=" + time + "]";
	}

}
