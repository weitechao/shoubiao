package com.bracelet.entity;

import java.sql.Timestamp;


public class WatchPhoneBook {
		
	private Integer id;
	private String imei;
	private String name;
	private String phone;
	private String cornet;
	private String headtype;
	private Integer status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private String headImg;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	
}
