package com.bracelet.entity;

import java.sql.Timestamp;

/**
 * 步数
 */
public class Step {

	private Long id;
	private Long user_id;
	private String imei;
	private Integer step_number;
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
	 * @return the user_id
	 */
	public Long getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
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
	 * @return the step_number
	 */
	public Integer getStep_number() {
		return step_number;
	}

	/**
	 * @param step_number the step_number to set
	 */
	public void setStep_number(Integer step_number) {
		this.step_number = step_number;
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

}
