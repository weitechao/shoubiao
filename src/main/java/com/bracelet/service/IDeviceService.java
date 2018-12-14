package com.bracelet.service;

import java.util.List;

import com.bracelet.entity.IpAddressInfo;
import com.bracelet.entity.WatchDevice;
import com.bracelet.entity.WatchDeviceHomeSchool;

public interface IDeviceService {

	WatchDevice getDeviceInfo(String addimei);
	
	WatchDeviceHomeSchool getDeviceHomeAndFamilyInfo(Long id);

	List<IpAddressInfo> getipinfo();

	boolean insertParameter(String imei, String parameter);

	boolean insertNewImei(String imei, String phone, int typeOfOperator, String dv);

	boolean updateImeiInfo(Long id, String phone, int typeOfOperator, String dv);

	boolean updateImeiInfo(Long id, String imei, String phone, String nickname, Integer sex, String birday,
			String school_age, String school_info, String home_info, String weight, String height, String head);

	boolean insertDeviceImeiInfo(String imei, String phone, String nickname, Integer sex, String birday,
			String school_age, String school_info, String home_info, String weight, String height, String head);

	boolean updateImeiHeadInfo(Long id, String head);

	boolean updateImeiHomeAndFamilyInfo(Long id, String school_info, String home_info);

	boolean updateImeiNotHomeAndFamilyInfo(Long id, String imei, String phone, String nickname, Integer sex,
			String birday, String school_age, String weight, String height, String head);

	boolean insertDeviceHomeAndFamilyInfo(Long id, String imei, String schoolAddress, String classDisable1, String classDisable2,
			String weekDisable1, String schoolLat, String schoolLng, String latestTime, String homeAddress, String homeLng,
			String homeLat);

	boolean updateImeiHomeAndFamilyInfoById(Long id, String classDisable1, String classDisable2, String weekDisable,
			String schoolAddress, String schoolLat, String schoolLng, String latestTime, String homeAddress,
			String homeLat, String homeLng);

	boolean insertNewImeiCopy(Long id, String imei, String phone, int typeOfOperator, String dv);

	WatchDevice getDeviceBakInfo(String imei);

	boolean updateImeiNumberById(Long id, String familyNumber, String shortNumber);



}
