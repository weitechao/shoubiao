package com.bracelet.socket.business.impl;

import io.netty.channel.Channel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.BindDevice;
import com.bracelet.entity.DeviceManagePhone;
import com.bracelet.entity.Fence;
import com.bracelet.entity.HealthStepManagement;
import com.bracelet.entity.LocationWatch;
import com.bracelet.entity.MemberInfo;
import com.bracelet.entity.UserInfo;
import com.bracelet.entity.WatchDevice;
import com.bracelet.entity.WatchDeviceAlarm;
import com.bracelet.entity.WatchDeviceHomeSchool;
import com.bracelet.entity.WatchDeviceSet;
import com.bracelet.entity.WatchPhoneBook;
import com.bracelet.entity.WatchVoiceInfo;
import com.bracelet.exception.BizException;
import com.bracelet.redis.LimitCache;
import com.bracelet.service.IConfService;
import com.bracelet.service.IFenceService;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IMemService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.service.IVoltageService;
import com.bracelet.service.WatchSetService;
import com.bracelet.service.WatchTkService;
import com.bracelet.socket.business.IService;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.RespCode;
import com.bracelet.util.Utils;

/**
 * 设备解绑
 * 
 */
@Component("disMissService")
public class DissMissService extends AbstractBizService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	IFenceService fenceService;
	@Autowired
	ILocationService locationService;
	@Autowired
	WatchTkService watchtkService;
	@Autowired
	IMemService memService;
	@Autowired
	IConfService confService;
	@Autowired
	WatchSetService watchSetService;

	@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto, JSONObject jsonObject, Channel channel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo, Channel channel) {

		logger.info("设备解绑=" + jsonInfo);
		String[] shuzu = jsonInfo.split("\\*");
		String imei = shuzu[1];// 设备imei

		// 清空设置信息 device_watch_info
		WatchDevice watch = ideviceService.getDeviceInfo(imei);
		if (watch != null) {
			ideviceService.updateWatchImeiInfoById(watch.getId(), "", "", 1, "", "", "", "", "", "");
			ideviceService.updateImeiHeadInfoByImei(watch.getId(), "");
		}

		DeviceManagePhone demp = ideviceService.getManagePhoneByImei(imei);
		if (demp != null) {
			ideviceService.updateAdminPhoneById(demp.getId(), "");
		}
		Fence fence = fenceService.getWatchOne(imei);
		if (fence != null) {
			fenceService.deleteWatchFenceByImei(imei);
		}
		WatchPhoneBook watchPhoneBook = memService.getMemberInfoByImei(imei);
		if (watchPhoneBook != null) {
			memService.deleteWatchMemberByImei(imei);
		}
		// 删语音 删定位
		LocationWatch locationWatch = locationService.getLatest(imei);
		if (locationWatch != null) {
			locationService.deleteByImei(imei);
		}

		WatchVoiceInfo watchVoice = watchtkService.getVoiceByImei(imei);
		if (watchVoice != null) {
			watchtkService.delteByImei(imei);
		}
		// 删闹钟
		WatchDeviceAlarm watchDeviceAlarm = ideviceService.getDeviceAlarmInfo(imei);
		if (watchDeviceAlarm != null) {
			ideviceService.deleteDeviceAlarmInfo(imei);
		}

		// 删除绑定设备
		BindDevice bindDevice = ideviceService.getBindDeviceByImei(imei);
		if (bindDevice != null) {
			ideviceService.deleteBindDevicebyImei(imei);
		}
		HealthStepManagement heathM = confService.getHeathStepInfo(imei);
		if (heathM != null) {
			confService.deteHeathyInfoByImei(heathM.getId());
		}

		UserInfo userInfo = userInfoService.getUserInfoByUsername(imei);
		if (userInfo != null) {
			userInfoService.updateUserPassword(userInfo.getUser_id(), "123456");
			WatchDeviceHomeSchool watchSchool = ideviceService
					.getDeviceHomeAndFamilyInfo(Long.valueOf(userInfo.getUser_id()));
			if (watchSchool != null) {
				ideviceService.updateImeiHomeAndFamilyInfoById(watchSchool.getId(), "08:00-12:00", "14:00-17:00", "",
						"", "", "", "", "", "", "");
			}
			userInfoService.deleteWatchBindByUserId(Long.valueOf(userInfo.getUser_id()));
			
			 WatchDeviceSet deviceSet = watchSetService.getDeviceSetByUserId(userInfo.getUser_id());
			 if( deviceSet !=null ){
				 watchSetService.deleteWatchSetById(deviceSet.getId());
			 }
			 
		}
		String reps = "[YW*" + imei + "*0001*0007*FACTORY]";
		logger.info("设备解绑=" + reps);
		return reps;
	}

	/*
	 * @Override public SocketBaseDto process(JSONObject jsonObject, Channel
	 * channel) { logger.info("===系统心跳：" + jsonObject.toJSONString());
	 * SocketBaseDto dto = new SocketBaseDto();
	 * dto.setType(jsonObject.getIntValue("type"));
	 * dto.setNo(jsonObject.getString("no")); dto.setTimestamp(new
	 * Date().getTime()); dto.setStatus(0);
	 * 
	 * return dto; }
	 * 
	 * @Override public String process(String jsonInfo, Channel channel) {
	 * 
	 * String[] shuzu = jsonInfo.split("\\*"); String imei = shuzu[1];// 设备imei
	 * String no = shuzu[2];// 流水号 String info = shuzu[4];
	 * 
	 * String[] infoshuzu = info.split(","); String energy = infoshuzu[1];
	 * //还需要保存下电量
	 * 
	 * logger.info("链路保持imei:" + imei + "," + ",no:" + no + ",电量:" + energy);
	 * 
	 * 
	 * String resp = "[YW*8800000015*0001*0002*LK ,"+Utils.getTime()+"]"; return
	 * resp; }
	 */

}
