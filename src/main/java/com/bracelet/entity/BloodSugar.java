package com.bracelet.entity;

import java.sql.Timestamp;

/**
 * 血糖
 * 
 */
public class BloodSugar {
    private Long bs_id;
    private Integer blood_sugar;
    private Long user_id;
    private Timestamp upload_time;
	public Long getBs_id() {
		return bs_id;
	}
	public void setBs_id(Long bs_id) {
		this.bs_id = bs_id;
	}
	public Integer getBlood_sugar() {
		return blood_sugar;
	}
	public void setBlood_sugar(Integer blood_sugar) {
		this.blood_sugar = blood_sugar;
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
}
