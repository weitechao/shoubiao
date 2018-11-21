package com.bracelet.service;

import com.bracelet.entity.WatchDeviceSet;

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

	WatchDeviceSet getDeviceSetByImei(String imei);

	boolean insertWatchDeviceSet(String imei, String data);
	
}
