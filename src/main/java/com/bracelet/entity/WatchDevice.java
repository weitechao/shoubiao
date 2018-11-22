package com.bracelet.entity;

import java.sql.Timestamp;

public class WatchDevice {
    private Long id;
    private String imei;
    private String phone;
    private String nickname;
    private Timestamp createtime;
    private Timestamp updatetime;
    private String dv;
    private Integer type;
    private Integer sex;
    private String birday;
    private String school_age;
    private String school_info;
    private String home_info;
    private String weight;
    private String height;
    private String head;
    
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public String getDv() {
		return dv;
	}
	public void setDv(String dv) {
		this.dv = dv;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getBirday() {
		return birday;
	}
	public void setBirday(String birday) {
		this.birday = birday;
	}
	public String getSchool_age() {
		return school_age;
	}
	public void setSchool_age(String school_age) {
		this.school_age = school_age;
	}
	public String getSchool_info() {
		return school_info;
	}
	public void setSchool_info(String school_info) {
		this.school_info = school_info;
	}
	public String getHome_info() {
		return home_info;
	}
	public void setHome_info(String home_info) {
		this.home_info = home_info;
	}
	public Timestamp getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	
}
