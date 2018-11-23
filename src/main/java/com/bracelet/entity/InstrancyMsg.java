package com.bracelet.entity;

import java.sql.Timestamp;

/**
 * 紧急通知
 */
public class InstrancyMsg {

	private Long id;
	private String msg;
	private String addname;
	private String time_slot;
	private Integer rstatus;
	private Timestamp createtime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getAddname() {
		return addname;
	}
	public void setAddname(String addname) {
		this.addname = addname;
	}
	public String getTime_slot() {
		return time_slot;
	}
	public void setTime_slot(String time_slot) {
		this.time_slot = time_slot;
	}
	public Integer getRstatus() {
		return rstatus;
	}
	public void setRstatus(Integer rstatus) {
		this.rstatus = rstatus;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}


}
