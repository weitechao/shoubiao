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
	private String imei;
	private String setInfo;
	private String infoVibration;
	private String infoVoice;
	private String phoneComeVibration;
	private String phoneComeVoice;
	private String watchOffAlarm;
	private String rejectStrangers;
	private String timerSwitch;
	private String disabledInClass;
	private String reserveEmergencyPower;
	private String somatosensory;
	private String reportCallLocation;
	private String automaticAnswering;
	private String sosMsgswitch;
	private String flowerNumber;
	private String brightScreen;
	private String language;
	private String timeZone;
	private String locationMode;
	private String locationTime;
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
	public String getSetInfo() {
		return setInfo;
	}
	public void setSetInfo(String setInfo) {
		this.setInfo = setInfo;
	}
	public String getInfoVibration() {
		return infoVibration;
	}
	public void setInfoVibration(String infoVibration) {
		this.infoVibration = infoVibration;
	}
	public String getInfoVoice() {
		return infoVoice;
	}
	public void setInfoVoice(String infoVoice) {
		this.infoVoice = infoVoice;
	}
	public String getPhoneComeVibration() {
		return phoneComeVibration;
	}
	public void setPhoneComeVibration(String phoneComeVibration) {
		this.phoneComeVibration = phoneComeVibration;
	}
	public String getPhoneComeVoice() {
		return phoneComeVoice;
	}
	public void setPhoneComeVoice(String phoneComeVoice) {
		this.phoneComeVoice = phoneComeVoice;
	}
	public String getWatchOffAlarm() {
		return watchOffAlarm;
	}
	public void setWatchOffAlarm(String watchOffAlarm) {
		this.watchOffAlarm = watchOffAlarm;
	}
	public String getRejectStrangers() {
		return rejectStrangers;
	}
	public void setRejectStrangers(String rejectStrangers) {
		this.rejectStrangers = rejectStrangers;
	}
	public String getTimerSwitch() {
		return timerSwitch;
	}
	public void setTimerSwitch(String timerSwitch) {
		this.timerSwitch = timerSwitch;
	}
	public String getDisabledInClass() {
		return disabledInClass;
	}
	public void setDisabledInClass(String disabledInClass) {
		this.disabledInClass = disabledInClass;
	}
	public String getReserveEmergencyPower() {
		return reserveEmergencyPower;
	}
	public void setReserveEmergencyPower(String reserveEmergencyPower) {
		this.reserveEmergencyPower = reserveEmergencyPower;
	}
	public String getSomatosensory() {
		return somatosensory;
	}
	public void setSomatosensory(String somatosensory) {
		this.somatosensory = somatosensory;
	}
	public String getReportCallLocation() {
		return reportCallLocation;
	}
	public void setReportCallLocation(String reportCallLocation) {
		this.reportCallLocation = reportCallLocation;
	}
	public String getAutomaticAnswering() {
		return automaticAnswering;
	}
	public void setAutomaticAnswering(String automaticAnswering) {
		this.automaticAnswering = automaticAnswering;
	}
	public String getSosMsgswitch() {
		return sosMsgswitch;
	}
	public void setSosMsgswitch(String sosMsgswitch) {
		this.sosMsgswitch = sosMsgswitch;
	}
	public String getFlowerNumber() {
		return flowerNumber;
	}
	public void setFlowerNumber(String flowerNumber) {
		this.flowerNumber = flowerNumber;
	}
	public String getBrightScreen() {
		return brightScreen;
	}
	public void setBrightScreen(String brightScreen) {
		this.brightScreen = brightScreen;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getLocationMode() {
		return locationMode;
	}
	public void setLocationMode(String locationMode) {
		this.locationMode = locationMode;
	}
	public String getLocationTime() {
		return locationTime;
	}
	public void setLocationTime(String locationTime) {
		this.locationTime = locationTime;
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
