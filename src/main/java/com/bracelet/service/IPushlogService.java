package com.bracelet.service;

import java.util.List;

import com.bracelet.entity.DeviceCarrierInfo;
import com.bracelet.entity.MsgInfo;
import com.bracelet.entity.PhoneCharge;
import com.bracelet.entity.Pushlog;
import com.bracelet.entity.SmsInfo;

public interface IPushlogService {

	boolean insert(Long user_id, String imei, Integer type, String target, String title, String content);

	Pagination<Pushlog> find(Long user_id, PageParam pageParam);

	boolean insertPushMsg(String imei, String message, Integer status);

	List<SmsInfo> getSmsList(String deviceId);

	PhoneCharge getCharge(String phone);

	DeviceCarrierInfo getDeviceCarrInfo(String deviceId);

	boolean updateCarrierById(Long id, String smsNumber, String smsBalanceKey, String smsFlowKey);

	boolean insertCarrier(String deviceId, String smsNumber, String smsBalanceKey, String smsFlowKey);

	boolean insertErrorInfo(Long userId, String content);

	List<MsgInfo> getMsgInfoList(String imei);

	boolean insertMsgInfo(String imei, Integer type, String deviceid, String content, String message);

	boolean deleteMsgInfo(String imei,Long id,Long userId);

}
