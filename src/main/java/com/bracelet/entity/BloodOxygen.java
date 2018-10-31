package com.bracelet.entity;

import java.sql.Timestamp;

/**
 * 血氧
 * 
 */
public class BloodOxygen {
    private Long bo_id;
    private Integer pulse_rate;
    private Integer blood_oxygen;
    private Long user_id;
    private Timestamp upload_time;
	public Long getBo_id() {
		return bo_id;
	}
	public void setBo_id(Long bo_id) {
		this.bo_id = bo_id;
	}
	public Integer getPulse_rate() {
		return pulse_rate;
	}
	public void setPulse_rate(Integer pulse_rate) {
		this.pulse_rate = pulse_rate;
	}
	public Integer getBlood_oxygen() {
		return blood_oxygen;
	}
	public void setBlood_oxygen(Integer blood_oxygen) {
		this.blood_oxygen = blood_oxygen;
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
