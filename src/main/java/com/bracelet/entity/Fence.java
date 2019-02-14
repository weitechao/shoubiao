package com.bracelet.entity;

import java.sql.Timestamp;

/**
 * 电子围栏
 * 
 */
public class Fence {

	private Long id;
	private Long user_id;
	private String lat;
	private String lng;
	private Integer radius;
	// 0: 未知，1:电子围栏内，2:电子围栏外
	private Integer status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private String imei;
	private String name;
	private Integer is_entry;
	private Integer is_exit;
	private Integer is_enable;

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

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public Integer getRadius() {
		return radius;
	}

	public void setRadius(Integer radius) {
		this.radius = radius;
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

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getIs_entry() {
		return is_entry;
	}

	public void setIs_entry(Integer is_entry) {
		this.is_entry = is_entry;
	}

	public Integer getIs_exit() {
		return is_exit;
	}

	public void setIs_exit(Integer is_exit) {
		this.is_exit = is_exit;
	}

	public Integer getIs_enable() {
		return is_enable;
	}

	public void setIs_enable(Integer is_enable) {
		this.is_enable = is_enable;
	}

}
