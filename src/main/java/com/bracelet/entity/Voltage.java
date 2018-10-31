package com.bracelet.entity;

import java.sql.Timestamp;

/**
 * 低电量
 * 
 */
public class Voltage {
    private Long vi_id;
    private Integer voltage;
    private Long user_id;
    private Timestamp upload_time;
	public Long getVi_id() {
		return vi_id;
	}
	public void setVi_id(Long vi_id) {
		this.vi_id = vi_id;
	}
	public Integer getVoltage() {
		return voltage;
	}
	public void setVoltage(Integer voltage) {
		this.voltage = voltage;
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
