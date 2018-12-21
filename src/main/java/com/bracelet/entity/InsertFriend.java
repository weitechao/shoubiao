package com.bracelet.entity;

import java.sql.Timestamp;

/**
 * 交友
 * 
 */
public class InsertFriend {
    private Long id;
    private String imei;
    private String add_imei;
    private int status;
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
	public String getAdd_imei() {
		return add_imei;
	}
	public void setAdd_imei(String add_imei) {
		this.add_imei = add_imei;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	

}
