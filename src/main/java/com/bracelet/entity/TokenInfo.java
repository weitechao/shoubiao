package com.bracelet.entity;

import java.sql.Timestamp;

public class TokenInfo {

	private Long t_id;
	private String token;
	private Long user_id;
	private Timestamp createtime;

	public Long getT_id() {
		return t_id;
	}

	public void setT_id(Long t_id) {
		this.t_id = t_id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}
