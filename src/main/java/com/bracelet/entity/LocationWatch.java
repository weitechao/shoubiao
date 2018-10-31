package com.bracelet.entity;

import java.sql.Timestamp;

/**
 * 位置
 * 
 */
public class LocationWatch {

	private Long id;
	private String imei;
	private Integer location_type;
	private String lat;
	private String lng;
	private Timestamp upload_time;
	private String status;
	private String location_time;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public Integer getLocation_type() {
		return location_type;
	}
	public void setLocation_type(Integer location_type) {
		this.location_type = location_type;
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
	public Timestamp getUpload_time() {
		return upload_time;
	}
	public void setUpload_time(Timestamp upload_time) {
		this.upload_time = upload_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLocation_time() {
		return location_time;
	}
	public void setLocation_time(String location_time) {
		this.location_time = location_time;
	}
	
}
