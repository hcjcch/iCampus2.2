package com.example.icampus2_2;

public class Item {
	private int imageId;
	private String text;
	private Class<?> cla;

	public Item(int imageId, String text, Class<?> cls) {
		// TODO Auto-generated constructor stub
		this.imageId = imageId;
		this.text = text;
		this.cla = cls;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Class<?> getCla() {
		return cla;
	}

	public void setCla(Class<?> cla) {
		this.cla = cla;
	}

}