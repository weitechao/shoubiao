package com.bracelet.entity;

import java.sql.Timestamp;

public class NoticeInfo {
	private Long id;
	private Long user_id;
	private Integer memberunlockswitch;
	private Integer temporaryunlockswitch;
	private Integer abnormalunlockswitch;
	private Integer appupdateswitch;
	private Timestamp createtime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Integer getMemberunlockswitch() {
		return memberunlockswitch;
	}
	public void setMemberunlockswitch(Integer memberunlockswitch) {
		this.memberunlockswitch = memberunlockswitch;
	}
	public Integer getTemporaryunlockswitch() {
		return temporaryunlockswitch;
	}
	public void setTemporaryunlockswitch(Integer temporaryunlockswitch) {
		this.temporaryunlockswitch = temporaryunlockswitch;
	}
	public Integer getAbnormalunlockswitch() {
		return abnormalunlockswitch;
	}
	public void setAbnormalunlockswitch(Integer abnormalunlockswitch) {
		this.abnormalunlockswitch = abnormalunlockswitch;
	}
	public Integer getAppupdateswitch() {
		return appupdateswitch;
	}
	public void setAppupdateswitch(Integer appupdateswitch) {
		this.appupdateswitch = appupdateswitch;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	
	

}
