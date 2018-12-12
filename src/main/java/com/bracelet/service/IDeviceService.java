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

	boolean updateImeiInfo(Long id, String imei, String phone, String nickname, Integer sex, String birday,
			String school_age, String school_info, String home_info, String weight, String height, String head);

	boolean insertDeviceImeiInfo(String imei, String phone, String nickname, Integer sex, String birday,
			String school_age, String school_info, String home_info, String weight, String height, String head);

	boolean updateImeiHeadInfo(Long id, String head);

	boolean updateImeiHomeAndFamilyInfo(Long id, String school_info, String home_info);

	boolean updateImeiNotHomeAndFamilyInfo(Long id, String imei, String phone, String nickname, Integer sex,
			String birday, String school_age, String weight, String height, String head);



}
