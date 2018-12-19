package com.bracelet.service;

import com.bracelet.entity.Conf;
import com.bracelet.entity.SchoolGuard;
import com.bracelet.entity.TimeSwitch;

import java.util.List;

public interface IConfService {

	List<Conf> list();

	SchoolGuard getSchoolGuard(Long deviceId);

	boolean updateSchoolGrardOffOnById(Long id, Integer status);

	boolean insertGuardOffOn(Long deviceId, Integer status);

	TimeSwitch getTimeSwitch(Long deviceId);

	boolean updateTimeSwitchById(Long id, String timeClose, String timeOpen);

	boolean insertTimeSwtich(Long deviceId, String timeClose, String timeOpen);


}
