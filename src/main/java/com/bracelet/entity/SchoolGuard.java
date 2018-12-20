package com.bracelet.entity;

import java.sql.Timestamp;

public class SchoolGuard {

	private Long id;
	private String deviceId;
	private Timestamp createtime;
	private Timestamp updatetime;
	private Integer offOn;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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
	public Integer getOffOn() {
		return offOn;
	}
	public void setOffOn(Integer offOn) {
		this.offOn = offOn;
	}

}
