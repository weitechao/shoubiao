package com.bracelet.service;

import com.bracelet.datasource.DataSourceChange;
import com.bracelet.entity.LocationFrequency;
import com.bracelet.entity.WatchDeviceSet;
import com.bracelet.entity.WatchDialpad;

public interface WatchSetService {


	boolean insertInfo(String imei, Integer setStatus, Integer type);

	boolean insertSmsSetLogInfo(String imei, Integer setStatus,String operatorNumber, String content);

	boolean insertIpSetInfo(String imei, String ip, String port, Integer setStatus);

	boolean insertApnSetLog(String imei, String apnName, String username,String password, String data, Integer setStatus);

	boolean insertMoniotrLog(String imei, Integer setStatus, String phone);

	boolean insertpushMessageLog(String imei, Integer setStatus, String message);

	boolean insertCaptLog(String imei, Integer setStatus, String come);

	boolean insertGuardLog(String imei, Integer setStatus, Integer type);

	boolean insertaddfriendLog(String imei, Integer setStatus, String role,String phone, String cornet, String headType);

	boolean updateWatchSet(Long id, String data);
	@DataSourceChange(slave = true)
	WatchDeviceSet getDeviceSetByImei(String imei);

	boolean insertWatchDeviceSet(String imei, String data);

	boolean updateWatchSet(Long id, String setInfo, String infoVibration, String infoVoice, String phoneComeVibration,
			String phoneComeVoice, String watchOffAlarm, String rejectStrangers, String timerSwitch,
			String disabledInClass, String reserveEmergencyPower, String somatosensory, String reportCallLocation,
			String automaticAnswering, String sosMsgswitch, String flowerNumber, String brightScreen, String language,
			String timeZone, String locationMode, String locationTime);

	boolean insertWatchDeviceSet(String imei, String setInfo, String infoVibration, String infoVoice,
			String phoneComeVibration, String phoneComeVoice, String watchOffAlarm, String rejectStrangers,
			String timerSwitch, String disabledInClass, String reserveEmergencyPower, String somatosensory,
			String reportCallLocation, String automaticAnswering, String sosMsgswitch, String flowerNumber,
			String brightScreen, String language, String timeZone, String locationMode, String locationTime);

	LocationFrequency getLocationFrequencyByImei(String imei);

	boolean updateLocationFrequencyById(Long id, Integer f);

	boolean insertLocationFrequency(String imei, Integer f);

	boolean setdialpadbyImei(String imei, Integer type);

	WatchDialpad getWatchDialpad(String imei);

	
}
