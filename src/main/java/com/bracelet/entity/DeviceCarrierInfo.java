package com.bracelet.entity;

import java.sql.Timestamp;

/**
 * 手表设置运营商
 */
public class DeviceCarrierInfo {

	private Long id;
	private Long deviceId;
	private String smsNumber;
	private String smsBalanceKey;
	private String smsFlowKey;
	private Timestamp createtime;
	private Timestamp updatetime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public String getSmsNumber() {
		return smsNumber;
	}
	public void setSmsNumber(String smsNumber) {
		this.smsNumber = smsNumber;
	}
	public String getSmsBalanceKey() {
		return smsBalanceKey;
	}
	public void setSmsBalanceKey(String smsBalanceKey) {
		this.smsBalanceKey = smsBalanceKey;
	}
	public String getSmsFlowKey() {
		return smsFlowKey;
	}
	public void setSmsFlowKey(String smsFlowKey) {
		this.smsFlowKey = smsFlowKey;
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


}
