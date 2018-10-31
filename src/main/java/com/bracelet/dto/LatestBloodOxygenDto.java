package com.bracelet.dto;

/**
 * 最近血糖
 * 
 */
public class LatestBloodOxygenDto {
	
    private Integer bloodOxygen;
    private Integer pulseRate;
    private Long timestamp;
	public Integer getBloodOxygen() {
		return bloodOxygen;
	}
	public void setBloodOxygen(Integer bloodOxygen) {
		this.bloodOxygen = bloodOxygen;
	}
	public Integer getPulseRate() {
		return pulseRate;
	}
	public void setPulseRate(Integer pulseRate) {
		this.pulseRate = pulseRate;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

}
