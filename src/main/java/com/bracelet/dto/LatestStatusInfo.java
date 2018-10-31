package com.bracelet.dto;

import java.util.Map;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class LatestStatusInfo {
	private Map<String, Object> heartPressure;
	private Map<String, Object> heartRate;
	private Map<String, Object> location;

	public Map<String, Object> getHeartPressure() {
		return heartPressure;
	}

	public void setHeartPressure(Map<String, Object> heartPressure) {
		this.heartPressure = heartPressure;
	}

	public Map<String, Object> getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(Map<String, Object> heartRate) {
		this.heartRate = heartRate;
	}

	public Map<String, Object> getLocation() {
		return location;
	}

	public void setLocation(Map<String, Object> location) {
		this.location = location;
	}
}
