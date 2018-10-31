package com.bracelet.dto;

/**
 * 返回json的dto映射类
 * 
 */
public class SocketBaseDto {

	/**
	 * 应答类型
	 */
	private int a = 1;
	/**
	 * 【数字】数据类型
	 */
	private int type;

	/**
	 * 【字符串】消息号
	 */
	private String no;

	/**
	 * 【数字】状态码
	 */
	private int status;
	/**
	 * 【字符串】"错误信息说明"
	 */
	private String message;

	/**
	 * 【数字】时间戳
	 */
	private Long timestamp;

	/**
	 * 【json】返回结果
	 */
	private Object data;

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
