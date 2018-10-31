package com.bracelet.dto;

/**
 * 心跳Dto
 * 
 */
public class LatestHeartPressureDto {
    private Integer maxHeartPressure;
    private Integer minHeartPressure;
    private Long timestamp;

    public Integer getMaxHeartPressure() {
        return maxHeartPressure;
    }

    public void setMaxHeartPressure(Integer maxHeartPressure) {
        this.maxHeartPressure = maxHeartPressure;
    }

    public Integer getMinHeartPressure() {
        return minHeartPressure;
    }

    public void setMinHeartPressure(Integer minHeartPressure) {
        this.minHeartPressure = minHeartPressure;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}
