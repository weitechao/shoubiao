package com.bracelet.service;

import java.sql.Timestamp;

import com.bracelet.entity.BindSimCardInfo;
import com.bracelet.entity.Voltage;


public interface IVoltageService {
    boolean insert(Long user_id, Integer voltage, Timestamp timsTimestamp);

	Voltage getLatest(Long user_id);

	boolean insertDianLiang(String imei, Integer voltage);

	Voltage getLatest(String imei);

	BindSimCardInfo getSimCardInfoByIccid(String iccid);

	BindSimCardInfo getSimCardInfoByImei(String imei);

	boolean insertBindSimCardInfo(String imei, String iccid);

}
