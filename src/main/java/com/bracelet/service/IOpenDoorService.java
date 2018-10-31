package com.bracelet.service;

import java.sql.Timestamp;
import java.util.List;

import com.bracelet.datasource.DataSourceChange;
import com.bracelet.entity.MomentPwdInfo;
import com.bracelet.entity.OpenDoorInfo;
import com.bracelet.entity.PwdInfo;
import com.bracelet.entity.WhiteListInfo;

public interface IOpenDoorService {
	
	@DataSourceChange(slave = true)
	List<OpenDoorInfo> getHistory(Long user_id, String imei, String starttime,
			String endtime);

	boolean insert(Integer type,Long userid, Integer way, Integer side, String imei,String name,Timestamp timestamp);

	Integer getOpenCount(String imei);
	
	@DataSourceChange(slave = true)
	List<OpenDoorInfo> getAllHistory(String imei, String starttime,
			String endtime);

	boolean deleteByImei(String imei);
	}