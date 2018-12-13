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
import com.bracelet.exception.BizException;
import com.bracelet.util.RespCode;
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
		String no = shuzu[2];// 流水号
		String info = shuzu[4];

		String[] infoshuzu = info.split(",");
		String phone = infoshuzu[1];
		int TypeOfOperator = Integer.valueOf(infoshuzu[2]);// 运营商类型:1表示移动2表示联通、3表示电信,0xFF表示其他
		String dv = infoshuzu[3];// 设备固件版本

		WatchDevice watchd = ideviceService.getDeviceBakInfo(imei);
		if(watchd != null){
			SocketLoginDto channelDto = new SocketLoginDto();
			channelDto.setChannel(channel);
			channelDto.setNo(no);
			channelDto.setImei(imei);
			channelDto.setPhone(phone);
			channelDto.setUser_id(watchd.getD_id());

			logger.info("保存手表登录信息,no:" + no + ",imei" + imei);
			ChannelMap.addChannel(imei, channelDto);
			ChannelMap.addChannel(channel, channelDto);
			
			//ideviceService.updateImeiInfo(watchd.getId(), phone, TypeOfOperator, dv);
		}else{
			ideviceService.insertNewImei(imei, phone, TypeOfOperator, dv);
			WatchDevice watchCopy = ideviceService.getDeviceInfo(imei);
			if(watchCopy != null){
				ideviceService.insertNewImeiCopy(watchCopy.getId(), imei, phone, TypeOfOperator, dv);
				
				SocketLoginDto channelDto = new SocketLoginDto();
				channelDto.setChannel(channel);
				channelDto.setNo(no);
				channelDto.setImei(imei);
				channelDto.setPhone(phone);
				channelDto.setUser_id(watchCopy.getId());

				logger.info("保存手表登录信息,no:" + no + ",imei" + imei);
				ChannelMap.addChannel(imei, channelDto);
				ChannelMap.addChannel(channel, channelDto);
			}
		}
		

		logger.info("设备初始化登录dv:" + dv + "," + ",no:" + no + ",imei:" + imei);

	
		
		/*LocationWatch locationWatch = locationService.getLatest(imei);
		if(locationWatch != null){
			WatchLatestLocation watchlastlocation = new WatchLatestLocation();
			watchlastlocation.setImei(imei);
			watchlastlocation.setLat(locationWatch.getLat());
			watchlastlocation.setLng(locationWatch.getLng());
			watchlastlocation.setLocationType(locationWatch.getLocation_type());
			watchlastlocation.setTimestamp(locationWatch.getUpload_time().getTime());
			ChannelMap.addlocation(imei, watchlastlocation);
		}*/
		

		String resp = "[YW*"+imei+"*0001*0006*INIT,1]";
		logger.info("返回设备登录信息="+resp);
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

		logger.info("保存手环登录信息,no:" + no + ",imei" + imei + ",user_id:"
				+ userInfo.getUser_id());
		ChannelMap.addChannel(imei, channelDto);
		ChannelMap.addChannel(channel, channelDto);

		SocketBaseDto dto = new SocketBaseDto();
		dto.setType(jsonObject.getIntValue("type"));
		dto.setNo(jsonObject.getString("no"));
		dto.setTimestamp(new Date().getTime());
		dto.setStatus(0);
		return dto;
	}
}
