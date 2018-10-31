package com.bracelet.entity;

import java.sql.Timestamp;

/**
 * 脉搏
 * 
 */
public class HeartRate {

	private Long hr_id;
	private Long user_id;
	private String imei;
	private Integer heart_rate;
	private Timestamp upload_time;

	public Long getHr_id() {
		return hr_id;
	}

	public void setHr_id(Long hr_id) {
		this.hr_id = hr_id;
	}

	public Integer getHeart_rate() {
		return heart_rate;
	}

	public void setHeart_rate(Integer heart_rate) {
		this.heart_rate = heart_rate;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Timestamp getUpload_time() {
		return upload_time;
	}

	public void setUpload_time(Timestamp upload_time) {
		this.upload_time = upload_time;
	}

	/**
	 * @return the imei
	 */
	public String getImei() {
		return imei;
	}

	/**
	 * @param imei the imei to set
	 */
	public void setImei(String imei) {
		this.imei = imei;
	}

}
