package com.bracelet.service;

import java.util.List;

import com.bracelet.datasource.DataSourceChange;
import com.bracelet.entity.BindDevice;
import com.bracelet.entity.NotRegisterInfo;
import com.bracelet.entity.NoticeInfo;
import com.bracelet.entity.OldBindDevice;
import com.bracelet.entity.UserInfo;
import com.bracelet.entity.VersionInfo;
import com.bracelet.entity.WatchAppVersionInfo;

public interface IUserInfoService {

	boolean insert(String tel);

	boolean bindDevice(Long user_id, String imei, Integer status, String name);

	boolean unbindDevice(Long user_id, Integer id);

	@DataSourceChange(slave = true)
	UserInfo getUserInfoByImei(String imei);

	@DataSourceChange(slave = true)
	UserInfo getUserInfoById(Long id);

	@DataSourceChange(slave = true)
	UserInfo getUserInfoByUsername(String username);

	boolean saveUserPassword(Long user_id, String md5);

	boolean updateUserInfo(Long user_id, String avatar, String nickname, Integer intSex, String weight, String height,
			String address);

	boolean saveUserInfo(String tel, String password, Integer type);

	boolean saveUserInfo(String tel, String password, Integer type, String name);

	@DataSourceChange(slave = true)
	List<BindDevice> getBindInfoById(Long user_id);

	@DataSourceChange(slave = true)
	List<BindDevice> getBindInfoByImei(String imei);

	boolean updateUserPassword(String tel, String password);

	boolean insertNotRegistUser(String tel, String name, Long user_id, String imei);

	@DataSourceChange(slave = true)
	NotRegisterInfo getNotRegistUserIdByCondition(String tel, String name, Long user_id, String imei);

	@DataSourceChange(slave = true)
	NotRegisterInfo getNotRegistUserIdByCondition(Long userid);

	boolean updateUserInfoHeadAndNickName(Long user_id, String nickname, String head);

	boolean updateUserInfoHeadAndNickName(Long user_id, String nickname);

	@DataSourceChange(slave = true)
	BindDevice getBindInfoByUserIdAndImei(Long user_id, String imei, Integer status);

	boolean deleteByImei(String imei);

	boolean updatePwdAndType(String tel, String pwd, Integer type);

	@DataSourceChange(slave = true)
	BindDevice getBindInfoByImeiAndStatus(String imei, Integer status);

	boolean updateName(Long id, String name);

	@DataSourceChange(slave = true)
	BindDevice getBindInfoByImeiAndUserId(String imei, Long user_id);

	@DataSourceChange(slave = true)
	VersionInfo getVersionInfo();

	boolean insertNoticeSet(Long user_id, Integer memberunlockswitch, Integer temporaryunlockswitch,
			Integer abnormalunlockswitch, Integer appupdateswitch);

	@DataSourceChange(slave = true)
	NoticeInfo getNoticeSet(Long user_id);

	@DataSourceChange(slave = true)
	WatchAppVersionInfo getWatchAppVersionInfo();

	@DataSourceChange(slave = true)
	OldBindDevice getOldDevice(String phone, String imei);

	boolean insertBindOldDevice(String phone, String imei, String name);

	@DataSourceChange(slave = true)
	List<OldBindDevice> getOldPhoneDeviceBind(String phone);

	boolean deleteDeviceBind(Long id);

	boolean updateOldBindDeviceInfo(Long id, String name);

	@DataSourceChange(slave = true)
	WatchAppVersionInfo getItfyVersionInfo();

	@DataSourceChange(slave = true)
	UserInfo getUserInfoLuRuByUsername(String imei);

	boolean updateUserPassword(Long user_id, String password);
	
    boolean saveBindInfo(Long user_id, String imei, String name, Integer status) ;

	BindDevice getWatchBindInfoByImeiAndUserId(String imei, Long valueOf);

	boolean saveWatchBindInfo(Long user_id, String imei, String name, Integer status);

	List<BindDevice> getWatchBindInfoById(Long user_id);

	boolean unWatchbindDevice(Long user_id, Integer id);

	boolean deleteWatchBindByUserId(Long userId);

	BindDevice getWatchBindInfoByUserId(Long userId);


	

}
