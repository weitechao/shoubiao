package com.bracelet.service;

import java.util.List;

import com.bracelet.datasource.DataSourceChange;
import com.bracelet.entity.WatchVoiceInfo;

public interface WatchTkService {

	boolean insertVoiceInfo(String sender, String receiver, String sourceName, String voiceData, Integer status,
			String numMessage, Integer thisNubmer, Integer allNumber,Integer voiceLength);

	boolean updateStatusById(Long id, Integer status);

	@DataSourceChange(slave = true)
	WatchVoiceInfo getVoiceByNoAndImeiAndStatus(String voiceNo, String imei, Integer status);

	@DataSourceChange(slave = true)
	List<WatchVoiceInfo> getVoiceListByImeiAndStatus(String imei, Integer status);

	boolean insertAppVoiceInfo(String sender, String receiver, String sourceName, String voiceData, Integer status,
			String numMessage, Integer thisNubmer, Integer allNumber,Integer voiceLength);

	WatchVoiceInfo getAppVoiceInfoByImeiAndStatus(String imei, Integer status);

	boolean delteByImei(String imei);

	WatchVoiceInfo getVoiceByImei(String imei);

}
