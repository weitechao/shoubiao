package com.bracelet.entity;

import java.sql.Timestamp;

/**
 * 接口日志
 */
public class Apilog {

	private Long id;
	private String name;
	private String req;
	private String resp;
	private String imei;
	private Integer rstatus;
	private String rmsg;
	private Long time;
	private Timestamp createtime;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
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
	 * @return the req
	 */
	public String getReq() {
		return req;
	}

	/**
	 * @param req the req to set
	 */
	public void setReq(String req) {
		this.req = req;
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
	 * @return the resp
	 */
	public String getResp() {
		return resp;
	}

	/**
	 * @param resp the resp to set
	 */
	public void setResp(String resp) {
		this.resp = resp;
	}

	/**
	 * @return the time
	 */
	public Long getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Long time) {
		this.time = time;
	}

}
