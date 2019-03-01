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
import com.bracelet.entity.LocationWatch;
import com.bracelet.entity.UserInfo;
import com.bracelet.entity.WatchDevice;
import com.bracelet.entity.WatchDeviceBak;
import com.bracelet.exception.BizException;
import com.bracelet.redis.LimitCache;
import com.bracelet.util.RespCode;
import com.bracelet.util.StringUtil;
import com.bracelet.util.Utils;
import com.bracelet.service.IDeviceService;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.socket.business.IService;
import com.bracelet.util.ChannelMap;

/**
 * 登录服务 {"type":1,"no":"1234567","timestamp":1501123709,"data":
 * {"dv":"divNo.1","sd":"sdV1"}}
 */
@Component("loginService")
public class LoginService implements IService {
	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	IDeviceService ideviceService;
	@Autowired
	ILocationService locationService;

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
		// String no = shuzu[2];// 流水号
		String info = shuzu[4];

		String[] infoshuzu = info.split(",");
		String phone = infoshuzu[1];
		int TypeOfOperator = Integer.valueOf(infoshuzu[2]);// 运营商类型:1表示移动2表示联通、3表示电信,0xFF表示其他
		String dv = infoshuzu[3];// 设备固件版本

		String haveValue = limitCache.getRedisKeyValue(imei + "_have");
             //判断是否有登陆过
		if (StringUtil.isEmpty(haveValue)) {
			
			WatchDeviceBak watchd = ideviceService.getDeviceBakInfo(imei);
			if (watchd != null) {

				SocketLoginDto channelDto = new SocketLoginDto();
				channelDto.setChannel(channel);
				channelDto.setNo("1");
				channelDto.setImei(imei);
				channelDto.setPhone(phone);
				channelDto.setUser_id(watchd.getD_id());
				limitCache.addKey(imei + "_have", "1");
				limitCache.addKey(imei + "_id", watchd.getD_id() + "");

				logger.info("保存手表登录信息" + ",imei" + imei + "deviceid=" + watchd.getD_id());
				ChannelMap.addChannel(imei, channelDto);
				ChannelMap.addChannel(channel, channelDto);

				// ideviceService.updateImeiInfo(watchd.getId(), phone,
				// TypeOfOperator, dv);
			} else {
				WatchDevice watchSelect = ideviceService.getDeviceInfo(imei);
				if (watchSelect == null) {
					ideviceService.insertNewImei(imei, phone, TypeOfOperator, dv);
					WatchDevice watchCopy = ideviceService.getDeviceInfo(imei);
					if (watchCopy != null) {
						ideviceService.insertNewImeiBak(watchCopy.getId(), imei);

						SocketLoginDto channelDto = new SocketLoginDto();
						channelDto.setChannel(channel);
						channelDto.setNo("1");
						channelDto.setImei(imei);
						channelDto.setPhone(phone);
						channelDto.setUser_id(watchCopy.getId());
						limitCache.addKey(imei + "_have", "1");
						limitCache.addKey(imei + "_id", watchCopy.getId() + "");

						logger.info("保存手表登录信息," + imei);
						ChannelMap.addChannel(imei, channelDto);
						ChannelMap.addChannel(channel, channelDto);
					}
				} else {
					ideviceService.insertNewImeiBak(watchSelect.getId(), imei);

					SocketLoginDto channelDto = new SocketLoginDto();
					channelDto.setChannel(channel);
					channelDto.setNo("1");
					channelDto.setImei(imei);
					channelDto.setPhone(phone);
					channelDto.setUser_id(watchSelect.getId());
					limitCache.addKey(imei + "_have", "1");
					limitCache.addKey(imei + "_id", watchSelect.getId() + "");
					logger.info("保存手表登录信息" + imei);
					ChannelMap.addChannel(imei, channelDto);
					ChannelMap.addChannel(channel, channelDto);
				}
			}
		} else {
			SocketLoginDto channelDto = new SocketLoginDto();
			channelDto.setChannel(channel);
			channelDto.setNo("1");
			channelDto.setImei(imei);
			channelDto.setPhone(phone);
			channelDto.setUser_id(Long.valueOf(limitCache.getRedisKeyValue(imei + "_id")));
			logger.info("redis  里有值  保存手表登录信息" + ",imei" + imei + "deviceid=" + limitCache.getRedisKeyValue(imei + "_id"));
			ChannelMap.addChannel(imei, channelDto);
			ChannelMap.addChannel(channel, channelDto);
		}

		limitCache.addKey(imei, Utils.IP + ":" + Utils.PORT_HTTP);//设备要先登录，服务器记录设备登录的http ip端口
		String resp = "[YW*" + imei + "*0001*0006*INIT,1]";
		logger.info("返回设备登录信息=" + resp+",redis 里的key为="+limitCache.getRedisKeyValue(imei + "_id"));
		return resp;
	}

	public SocketBaseDto process(JSONObject jsonObject, Channel channel) {
		JSONObject jsonObject2 = (JSONObject) jsonObject.get("data");
		if (jsonObject2 == null) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		String dv = jsonObject2.getString("dv");
		String sd = jsonObject2.getString("sd");
		String no = jsonObject.getString("no");
		String imei = jsonObject.getString("imei");
		// Long timestamp = jsonObject.getLong("timestamp");
		logger.info("登录,dv:" + dv + ",sd:" + sd + ",no:" + no + ",imei:" + imei);

		UserInfo userInfo = userInfoService.getUserInfoByImei(imei);
		if (userInfo == null) {
			logger.info("未绑定的设备,imei:" + imei);
			throw new BizException(RespCode.DEV_NOT_EXIST);
		}

		SocketLoginDto channelDto = new SocketLoginDto();
		channelDto.setChannel(channel);
		channelDto.setNo(no);
		channelDto.setImei(imei);
		channelDto.setUser_id(userInfo.getUser_id());

		logger.info("保存手环登录信息,no:" + no + ",imei" + imei + ",user_id:" + userInfo.getUser_id());
		ChannelMap.addChannel(imei, channelDto);
		ChannelMap.addChannel(channel, channelDto);

		SocketBaseDto dto = new SocketBaseDto();
		dto.setType(jsonObject.getIntValue("type"));
		dto.setNo(jsonObject.getString("no"));
		dto.setTimestamp(System.currentTimeMillis());
		dto.setStatus(0);
		return dto;
	}
}
