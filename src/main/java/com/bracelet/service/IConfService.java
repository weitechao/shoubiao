package com.bracelet.service;

import com.bracelet.entity.Conf;
import com.bracelet.entity.HealthStepManagement;
import com.bracelet.entity.NotifyInfo;
import com.bracelet.entity.SchoolGuard;
import com.bracelet.entity.TimeSwitch;

import java.util.List;

public interface IConfService {

	List<Conf> list();

	SchoolGuard getSchoolGuard(String deviceId);

	boolean updateSchoolGrardOffOnById(Long id, Integer status);

	boolean insertGuardOffOn(String deviceId, Integer status);

	TimeSwitch getTimeSwitch(Long userId);

	boolean updateTimeSwitchById(Long id, String timeClose, String timeOpen);

	boolean insertTimeSwtich(Long userId, String timeClose, String timeOpen,String imei);

	HealthStepManagement getHeathStepInfo(String deviceId);

	boolean updateHeathById(Long id, String stepCalculate, String sleepCalculate, String hrCalculate);

	boolean insertHeath(String deviceId, String stepCalculate, String sleepCalculate, String hrCalculate);

	boolean updateHeathSleepCalculateById(Long id, String sleepCalculate);

	NotifyInfo getNotiFyInfo(String deviceId);

	boolean updateNotifyById(Long id, String notification, String notificationSound, String notificationVibration);

	boolean insertNotify(String deviceId, String notification, String notificationSound, String notificationVibration);

	boolean deteHeathyInfoByImei(Long id);

	TimeSwitch getTimeSwitchByImei(String imei);


}
