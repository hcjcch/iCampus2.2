package com.example.groupdata;

public class Student {
    private String XH;
    private String XM;
    private String XB;
    public Student() {
		// TODO Auto-generated constructor stub
	}
    public Student(String XB, String XM, String XH) {
		// TODO Auto-generated constructor stub
    	this.XH = XH;
    	this.XM = XM;
    	this.XB = XB;
	}
	public String getXH() {
		return XH;
	}
	public void setXH(String XH) {
		this.XH = XH;
	}
	public String getXM() {
		return XM;
	}
	public void setXM(String XM) {
		this.XM = XM;
	}
	public String getXB() {
		return XB;
	}
	public void setXB(String XB) {
		this.XB = XB;
	}
}