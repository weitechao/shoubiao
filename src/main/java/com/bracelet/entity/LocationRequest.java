package com.bracelet.entity;

public class LocationRequest {
	private int a = 0;
	private String no;
	private int type;
	private long timestamp;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
