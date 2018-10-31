package com.bracelet.dto;

/**
 * 最近血脂
 * 
 */
public class LatestBloodFatDto {
	
    private Integer bloodFat;
    private Long timestamp;
	public Integer getBloodFat() {
		return bloodFat;
	}
	public void setBloodFat(Integer bloodFat) {
		this.bloodFat = bloodFat;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

}
