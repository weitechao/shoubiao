package com.bracelet.service;

import com.bracelet.entity.Conf;
import com.bracelet.entity.SchoolGuard;
import com.bracelet.entity.TimeSwitch;

import java.util.List;

public interface IConfService {

	List<Conf> list();

	SchoolGuard getSchoolGuard(String deviceId);

	boolean updateSchoolGrardOffOnById(Long id, Integer status);

	boolean insertGuardOffOn(String deviceId, Integer status);

	TimeSwitch getTimeSwitch(String deviceId);

	boolean updateTimeSwitchById(Long id, String timeClose, String timeOpen);

	boolean insertTimeSwtich(String deviceId, String timeClose, String timeOpen);


}
