package com.bracelet.dto;

/**
 * 电量Dto
 * 
 */
public class LatestVoltageDto {
    private Integer voltage;
    private Long timestamp;
	public Integer getVoltage() {
		return voltage;
	}
	public void setVoltage(Integer voltage) {
		this.voltage = voltage;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

}
