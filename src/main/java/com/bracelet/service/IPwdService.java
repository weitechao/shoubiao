package com.bracelet.service;

import java.util.List;

import com.bracelet.datasource.DataSourceChange;
import com.bracelet.entity.PwdInfo;
import com.bracelet.entity.PwdMomentInfo;
import com.bracelet.entity.WhiteListInfo;

public interface IPwdService {
	@DataSourceChange(slave = true)
	PwdInfo getByPhone(Long user_id, String imei, Long pwdId);

	boolean insert(Long user_id, String imei, String pwd, Long pwdId);

	boolean delete(Long user_id, Long id);

	boolean update(Long user_id, Long id, String pwd);
	
	@DataSourceChange(slave = true)
	List<PwdInfo> getByPhone(String imei);

	@DataSourceChange(slave = true)
	PwdInfo getByPhone(String imei,Long pwdId);

	boolean deleteByImei(String imei);

	boolean insertMonent(Long user_id, String imei, String pwd);
	
	@DataSourceChange(slave = true)
	List<PwdMomentInfo> getMomentPwdInfo(Long user_id, String imei);

	boolean updateMomentInfo(Long user_id, Long id);

	boolean deleteAllMomentInfo(Long user_id, String imei);

	boolean deleteAllMomentInfo(Long id);

	boolean deleteByImeiAndMemberId(String imei, Long memberId);


	boolean deleteById(Long id);

	
	
	
}