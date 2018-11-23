package com.bracelet.entity;

import java.sql.Timestamp;

/**
 * 语音实体
 */
public class WatchVoiceInfo {

	private Long id;
	private String sender;
	private String receiver;
	private String voice_content;
	private Timestamp createtime;
	private Integer status;
	private String source_name;
	private Timestamp updatetime;
	private String no;
	private Integer this_number;
	private Integer all_number;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getVoice_content() {
		return voice_content;
	}
	public void setVoice_content(String voice_content) {
		this.voice_content = voice_content;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getSource_name() {
		return source_name;
	}
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}
	public Timestamp getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
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
}
