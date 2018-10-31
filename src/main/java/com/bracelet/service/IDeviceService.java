package com.bracelet.service;

import java.util.List;

import com.bracelet.entity.IpAddressInfo;
import com.bracelet.entity.WatchDevice;

public interface IDeviceService {

	WatchDevice getDeviceInfo(String addimei);

	List<IpAddressInfo> getipinfo();

	boolean insertParameter(String imei, String parameter);

	boolean insertNewImei(String imei, String phone, int typeOfOperator, String dv);

	boolean updateImeiInfo(Long id, String phone, int typeOfOperator, String dv);



}
