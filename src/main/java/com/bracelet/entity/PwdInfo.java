package com.bracelet.entity;

import java.sql.Timestamp;


public class PwdInfo {
		
	private Integer id;
	private Long user_id;
	private String pwd;
	private String imei;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Long pwd_id;
	
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
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
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
	public Timestamp getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}
	public Long getPwd_id() {
		return pwd_id;
	}
	public void setPwd_id(Long pwd_id) {
		this.pwd_id = pwd_id;
	}
	
}
