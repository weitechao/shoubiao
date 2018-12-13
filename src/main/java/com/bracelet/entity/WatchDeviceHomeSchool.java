package com.bracelet.entity;

import java.sql.Timestamp;

public class WatchDeviceHomeSchool {
    private Long id;
    private Long w_id;
    private String imei;
    private Timestamp createtime;
    private Timestamp updatetime;
    private String classDisable1;
    private String classDisable2;
    private String weekDisable;
    private String schoolLat;
    private String schoolLng;
    private String schoolAddress;
    
    private String latestTime;
    
    private String homeAddress;
    private String homeLng;
    private String homeLat;
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
	public String getClassDisable1() {
		return classDisable1;
	}
	public void setClassDisable1(String classDisable1) {
		this.classDisable1 = classDisable1;
	}
	public String getClassDisable2() {
		return classDisable2;
	}
	public void setClassDisable2(String classDisable2) {
		this.classDisable2 = classDisable2;
	}
	public String getWeekDisable() {
		return weekDisable;
	}
	public void setWeekDisable(String weekDisable) {
		this.weekDisable = weekDisable;
	}
	public String getSchoolLat() {
		return schoolLat;
	}
	public void setSchoolLat(String schoolLat) {
		this.schoolLat = schoolLat;
	}
	public String getSchoolLng() {
		return schoolLng;
	}
	public void setSchoolLng(String schoolLng) {
		this.schoolLng = schoolLng;
	}
	public String getLatestTime() {
		return latestTime;
	}
	public void setLatestTime(String latestTime) {
		this.latestTime = latestTime;
	}
	public String getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	public String getHomeLng() {
		return homeLng;
	}
	public void setHomeLng(String homeLng) {
		this.homeLng = homeLng;
	}
	public Long getW_id() {
		return w_id;
	}
	public void setW_id(Long w_id) {
		this.w_id = w_id;
	}
	public String getHomeLat() {
		return homeLat;
	}
	public void setHomeLat(String homeLat) {
		this.homeLat = homeLat;
	}
	public String getSchoolAddress() {
		return schoolAddress;
	}
	public void setSchoolAddress(String schoolAddress) {
		this.schoolAddress = schoolAddress;
	}
}
