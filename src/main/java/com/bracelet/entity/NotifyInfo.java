package com.bracelet.entity;

import java.sql.Timestamp;

/**
 * 设置用户通知
 * 
 */
public class NotifyInfo {
    private Long id;
    private String imei;
    private String notification;
    private String notificationSound;
    private String notificationVibration;
    private Timestamp createtime;
    private Timestamp updatetime;
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
	public String getNotification() {
		return notification;
	}
	public void setNotification(String notification) {
		this.notification = notification;
	}
	public String getNotificationSound() {
		return notificationSound;
	}
	public void setNotificationSound(String notificationSound) {
		this.notificationSound = notificationSound;
	}
	public String getNotificationVibration() {
		return notificationVibration;
	}
	public void setNotificationVibration(String notificationVibration) {
		this.notificationVibration = notificationVibration;
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
