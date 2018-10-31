package com.bracelet.entity;

import java.sql.Timestamp;

/**
 * 血脂
 * 
 */
public class BloodFat {
    private Long bi_id;
    private Integer blood_fat;
    private Long user_id;
    private Timestamp upload_time;
	public Long getBi_id() {
		return bi_id;
	}
	public void setBi_id(Long bi_id) {
		this.bi_id = bi_id;
	}
	public Integer getBlood_fat() {
		return blood_fat;
	}
	public void setBlood_fat(Integer blood_fat) {
		this.blood_fat = blood_fat;
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
