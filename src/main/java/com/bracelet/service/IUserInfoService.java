package com.bracelet.service;

import java.util.List;

import com.bracelet.entity.BindDevice;
import com.bracelet.entity.NotRegisterInfo;
import com.bracelet.entity.NoticeInfo;
import com.bracelet.entity.OldBindDevice;
import com.bracelet.entity.UserInfo;
import com.bracelet.entity.VersionInfo;
import com.bracelet.entity.WatchAppVersionInfo;

public interface IUserInfoService {

	boolean insert(String tel);

	boolean bindDevice(Long user_id, String imei,Integer status,String name);

	boolean unbindDevice(Long user_id,Integer id);

	UserInfo getUserInfoByImei(String imei);

	UserInfo getUserInfoById(Long id);

	UserInfo getUserInfoByUsername(String username);

	boolean saveUserPassword(Long user_id, String md5);

	boolean updateUserInfo(Long user_id, String avatar, String nickname,
			Integer intSex, String weight, String height, String address);

	boolean saveUserInfo(String tel, String password,Integer type);
	
	boolean saveUserInfo(String tel, String password,Integer type,String name);

	List<BindDevice> getBindInfoById(Long user_id);

	List<BindDevice> getBindInfoByImei(String imei);

	boolean updateUserPassword(String tel, String password);

	boolean insertNotRegistUser(String tel, String name, Long user_id,String imei);

	NotRegisterInfo getNotRegistUserIdByCondition(String tel, String name,
			Long user_id, String imei);

	NotRegisterInfo getNotRegistUserIdByCondition(Long userid);

	boolean updateUserInfoHeadAndNickName(Long user_id, String nickname,
			String head);
	boolean updateUserInfoHeadAndNickName(Long user_id, String nickname);

	BindDevice getBindInfoByUserIdAndImei(Long user_id, String imei,Integer status);

	boolean deleteByImei(String imei);

	boolean updatePwdAndType(String tel, String pwd, Integer type);

	BindDevice getBindInfoByImeiAndStatus(String imei, Integer status);

	boolean updateName(Long id, String name);

	BindDevice getBindInfoByImeiAndUserId(String imei, Long user_id);

	VersionInfo getVersionInfo();

	boolean insertNoticeSet(Long user_id, Integer memberunlockswitch,
			Integer temporaryunlockswitch, Integer abnormalunlockswitch,
			Integer appupdateswitch);

	NoticeInfo getNoticeSet(Long user_id);

	WatchAppVersionInfo getWatchAppVersionInfo();

	OldBindDevice getOldDevice(String phone, String imei);

	boolean insertBindOldDevice(String phone, String imei, String name);


	List<OldBindDevice> getOldPhoneDeviceBind(String phone);

	boolean deleteDeviceBind(Long id);

	boolean updateOldBindDeviceInfo(Long id, String name);

	WatchAppVersionInfo getItfyVersionInfo();

}
