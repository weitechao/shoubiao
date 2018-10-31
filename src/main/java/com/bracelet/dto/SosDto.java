package com.bracelet.dto;

import com.alibaba.fastjson.JSON;

public class SosDto {
	private Long timestamp;
	private String lat;
	private String lng;

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public static void main(String[] args) {
		SosDto sosDto = new SosDto();
		sosDto.setLat("22.33123");
		sosDto.setLng("123.123123");
		sosDto.setTimestamp(System.currentTimeMillis());
		System.out.println(JSON.toJSONString(sosDto));
	}
}
