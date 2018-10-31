package com.bracelet.dto;

/**
 * 最近血糖
 * 
 */
public class LatestBloodSugarDto {
	
    private Integer bloodSugar;
    private Long timestamp;
	public Integer getBloodSugar() {
		return bloodSugar;
	}
	public void setBloodSugar(Integer bloodSugar) {
		this.bloodSugar = bloodSugar;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

}
