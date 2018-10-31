package com.bracelet.entity;

import java.sql.Timestamp;

/**
 * 血压
 * 
 */
public class HeartPressure {
	
	private Long hp_id;
	private Long user_id;
	private String imei;
	// 收缩压
	private Integer max_heart_pressure;
	// 舒张压
	private Integer min_heart_pressure;
	private Timestamp upload_time;

	public Long getHp_id() {
		return hp_id;
	}

	public void setHp_id(Long hp_id) {
		this.hp_id = hp_id;
	}

	public Integer getMax_heart_pressure() {
		return max_heart_pressure;
	}

	public void setMax_heart_pressure(Integer max_heart_pressure) {
		this.max_heart_pressure = max_heart_pressure;
	}

	public Integer getMin_heart_pressure() {
		return min_heart_pressure;
	}

	public void setMin_heart_pressure(Integer min_heart_pressure) {
		this.min_heart_pressure = min_heart_pressure;
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
