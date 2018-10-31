package com.bracelet.dto;

/**
 * 最近脉搏
 * 
 */
public class LatestHeartRateDto {
    private Integer heartRate;
    private Long timestamp;

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}
