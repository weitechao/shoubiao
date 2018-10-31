package com.bracelet.entity;

import java.sql.Timestamp;

/**
 * 敏感区域记录
 */
public class SensitivePointLog {

	private Long id;
	private Long user_id;
	// SensitivePoint id
	private Long sp_id;
	private String imei;
	private String lat;
	private String lng;
	private Integer radius;
	private String lat1;
	private String lng1;
	// 1:电子围栏内，2:电子围栏外
	private Integer status;
	private String content;
	private Timestamp upload_time;
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

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the createtime
	 */
	public Timestamp getCreatetime() {
		return createtime;
	}

	/**
	 * @param createtime the createtime to set
	 */
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	/**
	 * @return the imei
	 */
	public String getImei() {
		return imei;
	}

	/**
	 * @param imei the imei to set
	 */
	public void setImei(String imei) {
		this.imei = imei;
	}

	/**
	 * @return the lat
	 */
	public String getLat() {
		return lat;
	}

	/**
	 * @param lat the lat to set
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}

	/**
	 * @return the lng
	 */
	public String getLng() {
		return lng;
	}

	/**
	 * @param lng the lng to set
	 */
	public void setLng(String lng) {
		this.lng = lng;
	}

	/**
	 * @return the radius
	 */
	public Integer getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(Integer radius) {
		this.radius = radius;
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

	/**
	 * @return the lat1
	 */
	public String getLat1() {
		return lat1;
	}

	/**
	 * @param lat1 the lat1 to set
	 */
	public void setLat1(String lat1) {
		this.lat1 = lat1;
	}

	/**
	 * @return the lng1
	 */
	public String getLng1() {
		return lng1;
	}

	/**
	 * @param lng1 the lng1 to set
	 */
	public void setLng1(String lng1) {
		this.lng1 = lng1;
	}

	/**
	 * @return the upload_time
	 */
	public Timestamp getUpload_time() {
		return upload_time;
	}

	/**
	 * @param upload_time the upload_time to set
	 */
	public void setUpload_time(Timestamp upload_time) {
		this.upload_time = upload_time;
	}

	/**
	 * @return the sp_id
	 */
	public Long getSp_id() {
		return sp_id;
	}

	/**
	 * @param sp_id the sp_id to set
	 */
	public void setSp_id(Long sp_id) {
		this.sp_id = sp_id;
	}

}
