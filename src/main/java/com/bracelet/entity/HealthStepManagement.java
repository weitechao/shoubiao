package com.bracelet.entity;

import java.sql.Timestamp;

/**
 * 健康计步管理
 * 
 */
public class HealthStepManagement {
    private Long id;
    private String imei;
    private String stepCalculate;
    private String sleepCalculate;
    private String hrCalculate;
    private Timestamp createtime;
    private Timestamp updatetime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getStepCalculate() {
		return stepCalculate;
	}
	public void setStepCalculate(String stepCalculate) {
		this.stepCalculate = stepCalculate;
	}
	public String getSleepCalculate() {
		return sleepCalculate;
	}
	public void setSleepCalculate(String sleepCalculate) {
		this.sleepCalculate = sleepCalculate;
	}
	public String getHrCalculate() {
		return hrCalculate;
	}
	public void setHrCalculate(String hrCalculate) {
		this.hrCalculate = hrCalculate;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public Timestamp getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

}
