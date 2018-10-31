package com.bracelet.entity;

import java.sql.Timestamp;

public class WatchFriend {
    private Long id;
    private String imei;
    private String role_name;
    private String phone;
    private String cornet;
    private String headtype;
    private Timestamp createtime;
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
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCornet() {
		return cornet;
	}
	public void setCornet(String cornet) {
		this.cornet = cornet;
	}
	public String getHeadtype() {
		return headtype;
	}
	public void setHeadtype(String headtype) {
		this.headtype = headtype;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	
}
