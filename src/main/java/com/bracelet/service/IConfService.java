package com.bracelet.service;

import com.bracelet.entity.Conf;
import com.bracelet.entity.SchoolGuard;

import java.util.List;

public interface IConfService {

	List<Conf> list();

	SchoolGuard getSchoolGuard(Long deviceId);

	boolean updateSchoolGrardOffOnById(Long id, Integer status);

	boolean insertGuardOffOn(Long deviceId, Integer status);

}
