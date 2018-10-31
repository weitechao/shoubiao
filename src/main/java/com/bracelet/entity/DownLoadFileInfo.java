package com.bracelet.entity;

import java.sql.Timestamp;

/**
 * 文件下载
 */
public class DownLoadFileInfo {

	private Long id;
	private String imei;
	private String photo_name;
	private String source;
	private Integer this_number;
	private Integer all_number;
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
	public String getPhoto_name() {
		return photo_name;
	}
	public void setPhoto_name(String photo_name) {
		this.photo_name = photo_name;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Integer getThis_number() {
		return this_number;
	}
	public void setThis_number(Integer this_number) {
		this.this_number = this_number;
	}
	public Integer getAll_number() {
		return all_number;
	}
	public void setAll_number(Integer all_number) {
		this.all_number = all_number;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}


}
