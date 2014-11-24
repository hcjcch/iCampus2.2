package com.example.icampus2_2;

public class ItemWithOutDrawable {
	private String text;
	private Class<?> cls;

	public ItemWithOutDrawable(String text, Class<?> cls) {
		super();
		this.text = text;
		this.cls = cls;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Class<?> getCls() {
		return cls;
	}

	public void setCls(Class<?> cls) {
		this.cls = cls;
	}

	@Override
	public String toString() {
		return "ItemWithOutDrawable [text=" + text + ", cls=" + cls + "]";
	}

}
