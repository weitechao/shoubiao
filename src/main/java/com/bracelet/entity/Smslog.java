package com.bracelet.entity;

import java.sql.Timestamp;

/**
 * Sms记录
 */
public class Smslog {

	private Long id;
	private String name;
	private String mobile;
	private String tpl_code;
	private String tpl_param;
	private Integer rstatus;
	private String rmsg;
	private Timestamp createtime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the tpl_code
	 */
	public String getTpl_code() {
		return tpl_code;
	}

	/**
	 * @param tpl_code the tpl_code to set
	 */
	public void setTpl_code(String tpl_code) {
		this.tpl_code = tpl_code;
	}

	/**
	 * @return the tpl_param
	 */
	public String getTpl_param() {
		return tpl_param;
	}

	/**
	 * @param tpl_param the tpl_param to set
	 */
	public void setTpl_param(String tpl_param) {
		this.tpl_param = tpl_param;
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
	 * @return the rstatus
	 */
	public Integer getRstatus() {
		return rstatus;
	}

	/**
	 * @param rstatus the rstatus to set
	 */
	public void setRstatus(Integer rstatus) {
		this.rstatus = rstatus;
	}

	/**
	 * @return the rmsg
	 */
	public String getRmsg() {
		return rmsg;
	}

	/**
	 * @param rmsg the rmsg to set
	 */
	public void setRmsg(String rmsg) {
		this.rmsg = rmsg;
	}

}
