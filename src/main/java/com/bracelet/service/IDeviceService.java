package com.bracelet.service;

import java.util.List;

import com.bracelet.datasource.DataSourceChange;
import com.bracelet.entity.IpAddressInfo;
import com.bracelet.entity.WatchDevice;
import com.bracelet.entity.WatchDeviceAlarm;
import com.bracelet.entity.WatchDeviceBak;
import com.bracelet.entity.WatchDeviceHomeSchool;

public interface IDeviceService {
	
	@DataSourceChange(slave = true)
	WatchDevice getDeviceInfo(String addimei);
	
	@DataSourceChange(slave = true)
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

	boolean insertNewImeiBak(Long id, String imei);
	
	@DataSourceChange(slave = true)
	WatchDeviceBak getDeviceBakInfo(String imei);

	boolean updateImeiNumberById(Long id, String familyNumber, String shortNumber);

	boolean updateImeiHeadInfoByImei(Long id, String string);

	boolean updateWatchImeiInfoByImei(String imei, String phone, String nickname, Integer sex, String birday,
			String school_age, String weight, String height, String familyNumber, String shortNumber);

	WatchDeviceHomeSchool getDeviceHomeAndFamilyInfoByImei(String imei);

	boolean updateWatchImeiInfoById(Long id, String phone, String nickname, Integer sex, String birday,
			String school_age, String weight, String height, String familyNumber, String shortNumber);

	WatchDeviceAlarm getDeviceAlarmInfo(String imei);

	boolean updateWatchAlarmInfoById(Long id, String weekAlarm1, String weekAlarm2, String weekAlarm3, String alarm1,
			String alarm2, String alarm3);

	boolean insertDeviceAlarmInfo(String imei, String weekAlarm1, String weekAlarm2, String weekAlarm3, String alarm1,
			String alarm2, String alarm3);



}
