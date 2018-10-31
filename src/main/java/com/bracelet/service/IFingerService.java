package com.bracelet.service;

import java.util.List;

import com.bracelet.datasource.DataSourceChange;
import com.bracelet.entity.FingerInfo;
import com.bracelet.entity.PwdInfo;
import com.bracelet.entity.WhiteListInfo;

public interface IFingerService {

	boolean insert(Long user_id, String imei, String finger_id, Integer type,String name,Long member_id);
	@DataSourceChange(slave = true)
	List<FingerInfo> getFingerInfobyUserId(Long user_id, String imei);

	boolean delete(Long user_id, Long id);
	boolean delete(Long id);
	
	@DataSourceChange(slave = true)
	List<FingerInfo> getFingerInfoByFingerId(Long fingerId, String imei);
	boolean deleteByImei(String imei);
	@DataSourceChange(slave = true)
	List<FingerInfo> getFingerInfobyImei(String imei);
	
	boolean update(Long id, Integer type);
	boolean deleteByImeiAndMemberId(String imei, Long memberId);

	
}