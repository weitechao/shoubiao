package com.bracelet.entity;

import java.sql.Timestamp;

/**
 * app对手表的设置
 */
public class WatchDeviceSet {
	
	/*
	 * 
	 * 		String setInfo = jsonObject.getString("setInfo");
		String infoVibration = jsonObject.getString("infoVibration");//手表信息震动
		String infoVoice = jsonObject.getString("infoVoice");//手表信息声音
		String phoneComeVibration = jsonObject.getString("phoneComeVibration");//手表来电话震动
		String phoneComeVoice = jsonObject.getString("phoneComeVoice");//手表来电话声音
		String watchOffAlarm = jsonObject.getString("watchOffAlarm");//手表脱落报警
		String rejectStrangers = jsonObject.getString("rejectStrangers");//拒绝陌生人来电
		String timerSwitch = jsonObject.getString("timerSwitch");//定时开关机
		String disabledInClass = jsonObject.getString("disabledInClass");//上课禁用
		String reserveEmergencyPower = jsonObject.getString("reserveEmergencyPower");//预留紧急电量6
		String somatosensory = jsonObject.getString("somatosensory");//体感接听 
		String reportCallLocation = jsonObject.getString("reportCallLocation");//报告通话位置
		String automaticAnswering = jsonObject.getString("automaticAnswering");//自动接听
		String sosMsgswitch = jsonObject.getString("sosMsgswitch");//sos开关
		String flowerNumber = jsonObject.getString("flowerNumber");//爱心奖励
		String brightScreen = jsonObject.getString("brightScreen");//亮屏时间
		String language = jsonObject.getString("language");//语言
		String timeZone = jsonObject.getString("timeZone");//时区
		String locationMode = jsonObject.getString("locationMode");//工作模式
		String locationTime = jsonObject.getString("locationTime");//工作时长*/

	private Long id;
	private Long user_id;
	private String imei;
	private Timestamp createtime;
	private Timestamp updatetime;
	private String setInfo;
	
	private Integer infoVibration;
	private Integer infoVoice;
	private Integer phoneComeVibration;
	private Integer phoneComeVoice;
	private Integer watchOffAlarm;
	private Integer rejectStrangers;
	private Integer timerSwitch;
	private Integer disabledInClass;
	private Integer reserveEmergencyPower;
	private Integer somatosensory;
	private Integer reportCallLocation;
	private Integer automaticAnswering;
	private Integer sosMsgswitch;
	private Integer flowerNumber;
	private Integer brightScreen;
	private Integer language;
	private Integer timeZone;
	private Integer locationMode;
	private Integer locationTime;
	
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
	public String getSetInfo() {
		return setInfo;
	}
	public void setSetInfo(String setInfo) {
		this.setInfo = setInfo;
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
	public Integer getInfoVibration() {
		return infoVibration;
	}
	public void setInfoVibration(Integer infoVibration) {
		this.infoVibration = infoVibration;
	}
	public Integer getInfoVoice() {
		return infoVoice;
	}
	public void setInfoVoice(Integer infoVoice) {
		this.infoVoice = infoVoice;
	}
	public Integer getPhoneComeVibration() {
		return phoneComeVibration;
	}
	public void setPhoneComeVibration(Integer phoneComeVibration) {
		this.phoneComeVibration = phoneComeVibration;
	}
	public Integer getPhoneComeVoice() {
		return phoneComeVoice;
	}
	public void setPhoneComeVoice(Integer phoneComeVoice) {
		this.phoneComeVoice = phoneComeVoice;
	}
	public Integer getWatchOffAlarm() {
		return watchOffAlarm;
	}
	public void setWatchOffAlarm(Integer watchOffAlarm) {
		this.watchOffAlarm = watchOffAlarm;
	}
	public Integer getRejectStrangers() {
		return rejectStrangers;
	}
	public void setRejectStrangers(Integer rejectStrangers) {
		this.rejectStrangers = rejectStrangers;
	}
	public Integer getTimerSwitch() {
		return timerSwitch;
	}
	public void setTimerSwitch(Integer timerSwitch) {
		this.timerSwitch = timerSwitch;
	}
	public Integer getDisabledInClass() {
		return disabledInClass;
	}
	public void setDisabledInClass(Integer disabledInClass) {
		this.disabledInClass = disabledInClass;
	}
	public Integer getReserveEmergencyPower() {
		return reserveEmergencyPower;
	}
	public void setReserveEmergencyPower(Integer reserveEmergencyPower) {
		this.reserveEmergencyPower = reserveEmergencyPower;
	}
	public Integer getSomatosensory() {
		return somatosensory;
	}
	public void setSomatosensory(Integer somatosensory) {
		this.somatosensory = somatosensory;
	}
	public Integer getReportCallLocation() {
		return reportCallLocation;
	}
	public void setReportCallLocation(Integer reportCallLocation) {
		this.reportCallLocation = reportCallLocation;
	}
	public Integer getAutomaticAnswering() {
		return automaticAnswering;
	}
	public void setAutomaticAnswering(Integer automaticAnswering) {
		this.automaticAnswering = automaticAnswering;
	}
	public Integer getSosMsgswitch() {
		return sosMsgswitch;
	}
	public void setSosMsgswitch(Integer sosMsgswitch) {
		this.sosMsgswitch = sosMsgswitch;
	}
	public Integer getFlowerNumber() {
		return flowerNumber;
	}
	public void setFlowerNumber(Integer flowerNumber) {
		this.flowerNumber = flowerNumber;
	}
	public Integer getBrightScreen() {
		return brightScreen;
	}
	public void setBrightScreen(Integer brightScreen) {
		this.brightScreen = brightScreen;
	}
	public Integer getLanguage() {
		return language;
	}
	public void setLanguage(Integer language) {
		this.language = language;
	}
	public Integer getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(Integer timeZone) {
		this.timeZone = timeZone;
	}
	public Integer getLocationMode() {
		return locationMode;
	}
	public void setLocationMode(Integer locationMode) {
		this.locationMode = locationMode;
	}
	public Integer getLocationTime() {
		return locationTime;
	}
	public void setLocationTime(Integer locationTime) {
		this.locationTime = locationTime;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	
}
