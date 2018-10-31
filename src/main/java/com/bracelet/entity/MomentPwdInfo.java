package com.bracelet.entity;

import java.sql.Timestamp;


public class MomentPwdInfo {
		
	private Integer id;
	private Long user_id;
	private Integer pwd;
	private String imei;
    private Integer status;
	private Timestamp createtime;
	private Timestamp udpatetime;
	private Integer type;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Integer getPwd() {
		return pwd;
	}
	public void setPwd(Integer pwd) {
		this.pwd = pwd;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Timestamp getUdpatetime() {
		return udpatetime;
	}
	public void setUdpatetime(Timestamp udpatetime) {
		this.udpatetime = udpatetime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
