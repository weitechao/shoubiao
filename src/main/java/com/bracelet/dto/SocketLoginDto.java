package com.bracelet.dto;

import io.netty.channel.Channel;

/**
 * 手环登录后保存信息
 * 
 */
public class SocketLoginDto {
	private String no;
	private Long user_id;
	private String imei;
	private String token;
	private Channel channel;
	private String phone;
	private String energy;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEnergy() {
		return energy;
	}

	public void setEnergy(String energy) {
		this.energy = energy;
	}
	
}
