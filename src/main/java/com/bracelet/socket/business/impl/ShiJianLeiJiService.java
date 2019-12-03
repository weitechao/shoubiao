package com.bracelet.socket.business.impl;

import io.netty.channel.Channel;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.dto.WatchLatestLocation;
import com.bracelet.entity.HealthStepManagement;
import com.bracelet.entity.LocationWatch;
import com.bracelet.entity.TimeSwitch;
import com.bracelet.entity.UserInfo;
import com.bracelet.entity.WatchDevice;
import com.bracelet.entity.WatchDeviceAlarm;
import com.bracelet.entity.WatchDeviceBak;
import com.bracelet.entity.WatchDeviceHomeSchool;
import com.bracelet.entity.WatchDeviceSet;
import com.bracelet.entity.WatchDeviceShiChang;
import com.bracelet.exception.BizException;
import com.bracelet.redis.LimitCache;
import com.bracelet.util.RespCode;
import com.bracelet.util.StringUtil;
import com.bracelet.util.Utils;
import com.bracelet.service.IConfService;
import com.bracelet.service.IDeviceService;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.service.WatchSetService;
import com.bracelet.socket.business.IService;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.HttpClientGet;
import com.bracelet.util.RadixUtil;


@Component("shiJianLeiJiService")
public class ShiJianLeiJiService implements IService {
	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	IDeviceService ideviceService;
	@Autowired
	ILocationService locationService;
	
	@Autowired
	WatchSetService watchSetService;
	
	@Autowired
	IConfService confService;

	@Autowired
	LimitCache limitCache;

	private Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * 终端发送:
	 * [YW*YYYYYYYYYY*NNNN*LEN*INIT,电话号码,运营商类型,固件版本号,全部参数设置次数流水号,通信录设置次数流水号
	 * ,长度计算方式]
	 * 实例:[YW*861900039990378*0001*0033*INIT,13256122653,0,D9_WZX_3KEY_V0
	 * .1.389.QGJ_V1.0,0002,2300,1]
	 * 
	 * 手表初始化
	 */

	@Override
	public String process(String jsonInfo, Channel channel) {

		String[] shuzu = jsonInfo.split("\\*");
		String imei = shuzu[1];// 设备imei
		
		String info = shuzu[4];

		String[] infoshuzu = info.split(",");
		int shichang = Integer.valueOf(infoshuzu[1]);//时长
	
		String resp = "[YW*" + imei + "*0001*0006*SJLJ,1]";
		
			//设备没有登录过的
		WatchDeviceShiChang watchd = ideviceService.getDeviceShiChangInfo(imei);
			if (watchd != null) {
				ideviceService.updateWatchShiChangInfo(watchd.getId(),watchd.getSc()+shichang);
			}else{
				ideviceService.insertWatchShiChangInfo(imei,shichang);
			}

		return resp;
		
	}

	public SocketBaseDto process(JSONObject jsonObject, Channel channel) {
		return null;
		
	}
	
}
