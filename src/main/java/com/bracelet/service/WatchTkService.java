package com.bracelet.service;

import java.util.List;

import com.bracelet.entity.WatchVoiceInfo;



public interface WatchTkService {
	
	boolean insertVoiceInfo(String sender, String receiver, String sourceName,String voiceData, Integer status,String numMessage,Integer thisNubmer,Integer allNumber);


	boolean updateStatusById(Long id, Integer status);

	WatchVoiceInfo getVoiceByNoAndImeiAndStatus(String voiceNo, String imei,Integer status);

	List<WatchVoiceInfo> getVoiceListByImeiAndStatus(String imei, Integer status);
	
}
