package com.bracelet.entity;

import java.sql.Timestamp;

public class WatchDeviceAlarm {
    private Long id;
    private String imei;
 
    private Timestamp createtime;
    private Timestamp updatetime;
    private String weekAlarm1;
    private String weekAlarm2;
    private String weekAlarm3;
    private String alarm1;
    private String alarm2;
    private String alarm3;
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
	public String getWeekAlarm1() {
		return weekAlarm1;
	}
	public void setWeekAlarm1(String weekAlarm1) {
		this.weekAlarm1 = weekAlarm1;
	}
	public String getWeekAlarm2() {
		return weekAlarm2;
	}
	public void setWeekAlarm2(String weekAlarm2) {
		this.weekAlarm2 = weekAlarm2;
	}
	public String getWeekAlarm3() {
		return weekAlarm3;
	}
	public void setWeekAlarm3(String weekAlarm3) {
		this.weekAlarm3 = weekAlarm3;
	}
	public String getAlarm1() {
		return alarm1;
	}
	public void setAlarm1(String alarm1) {
		this.alarm1 = alarm1;
	}
	public String getAlarm2() {
		return alarm2;
	}
	public void setAlarm2(String alarm2) {
		this.alarm2 = alarm2;
	}
	public String getAlarm3() {
		return alarm3;
	}
	public void setAlarm3(String alarm3) {
		this.alarm3 = alarm3;
	}
   
	
}
