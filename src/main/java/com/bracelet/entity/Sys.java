package com.bracelet.entity;

import java.sql.Timestamp;

/**
 * 系统设置
 * 
 */
public class Sys {

	private Long id;
	private String service_content;
	private Timestamp createtime;
	private Timestamp updatetime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	 * @return the service_content
	 */
	public String getService_content() {
		return service_content;
	}

	/**
	 * @param service_content the service_content to set
	 */
	public void setService_content(String service_content) {
		this.service_content = service_content;
	}

}
