package com.bracelet.entity;

import java.sql.Timestamp;

/**
 * 绑定SIM卡
 * 
 */
public class BindSimCardInfo {
    private Integer b_id;
    private String imei;
    private String iccid;
    private Timestamp udpate_time;
	
	public Integer getB_id() {
		return b_id;
	}
	public void setB_id(Integer b_id) {
		this.b_id = b_id;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public Timestamp getUdpate_time() {
		return udpate_time;
	}
	public void setUdpate_time(Timestamp udpate_time) {
		this.udpate_time = udpate_time;
	}
	
}
