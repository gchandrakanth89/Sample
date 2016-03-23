package com.pervacio;

public class MyObject {
	private int i = 10;
	private long l=20;
	private String str = "Chandu";

	public MyObject(int i, long l, String str) {
		super();
		this.i = i;
		this.l = l;
		this.str = str;
	}

	public MyObject() {
	}

	private int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	/**
	 * @hide
	 */
	private long getL() {
		return l;
	}

	public void setL(long l) {
		this.l = l;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

}
