package com.bracelet.service;

import java.util.List;

import com.bracelet.datasource.DataSourceChange;
import com.bracelet.entity.MomentPwdInfo;
import com.bracelet.entity.PwdInfo;
import com.bracelet.entity.WhiteListInfo;

public interface IMomentPwdService {
	@DataSourceChange(slave = true)
	List<MomentPwdInfo> getByImei(Long user_id, String imei);

	boolean insert(Long user_id, String imei);

	boolean updateStatus(Long user_id, String imei,Integer status);

	boolean deleteByImei(String imei);

	boolean updateStatus(String imei, Integer password, Integer status);
	
	@DataSourceChange(slave = true)
    MomentPwdInfo getByImeiAndPwd(String imei, String password);

	boolean updateStatusById(Integer id, Integer status);
	
}