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

/**
 * 登录服务 {"type":1,"no":"1234567","timestamp":1501123709,"data":
 * {"dv":"divNo.1","sd":"sdV1"}}
 */
@Component("dzdzLoginService")
public class DzDzLoginService implements IService {
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
		// String no = shuzu[2];// 流水号
		String info = shuzu[4];

		String[] infoshuzu = info.split(",");
		String phone = infoshuzu[1];
		
		String url = "http://kids.yycall.cn/api/check?iccid="+phone+"&imei="+imei;
		logger.info("DZ=url="+url);
		String responseJsonString = HttpClientGet.get(url);
		logger.info("responseJsonString"+responseJsonString);
		
		if("0".equals(responseJsonString)){
			String resp = "[YW*" + imei + "*0001*0006*DZDZ,0]";
			return resp;
			}else{
		
		int TypeOfOperator = Integer.valueOf(infoshuzu[2]);// 运营商类型:1表示移动2表示联通、3表示电信,0xFF表示其他
		String dv = infoshuzu[3];// 设备固件版本

		String haveValue = limitCache.getRedisKeyValue(imei + "_have");
             //判断是否有登陆过
		if (StringUtil.isEmpty(haveValue)) {
			//设备没有登录过的
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
					if("1".equals(watchSelect.getDv()) || StringUtil.isEmpty(watchSelect.getDv())){
						ideviceService.updateDvById(watchSelect.getId(),dv);
					}
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
			/*下面这段代码要隐藏在使用一两天后
			WatchDeviceBak watchd = ideviceService.getDeviceBakInfo(imei);
			if(watchd != null){
				ideviceService.updateDvById(watchd.getId(),dv);
			}
			
			*/
			
			
			
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
		limitCache.addKey(imei+"_energy", "100");
		limitCache.addKey(imei, Utils.IP + ":" + Utils.PORT_HTTP);//设备要先登录，服务器记录设备登录的http ip端口
		
		
		String resp = "[YW*" + imei + "*0001*0006*DZDZ,1]"+"[YW*"+imei+"*0001*0016*LK,"+Utils.getJian8Time()+"]";
		
	
		
		
		
		
		//拼设置的指令包
		
		
		WatchDeviceSet deviceSet = watchSetService.getDeviceSetByImei(imei);

		StringBuffer sendMsg = new StringBuffer("SET" + ",,1234,");// F48,");
		WatchDeviceAlarm watch = ideviceService.getDeviceAlarmInfo(imei);
		
		if (deviceSet != null) {
			StringBuffer setString = new StringBuffer("");
			setString.append(deviceSet.getInfoVibration()).append(deviceSet.getInfoVoice())
					.append(deviceSet.getPhoneComeVibration()).append(deviceSet.getPhoneComeVoice())
					.append(deviceSet.getWatchOffAlarm()).append(deviceSet.getRejectStrangers())
					.append(deviceSet.getTimerSwitch()).append(deviceSet.getDisabledInClass())
					.append(deviceSet.getReserveEmergencyPower()).append(deviceSet.getSomatosensory())
					.append(deviceSet.getReportCallLocation()).append(deviceSet.getAutomaticAnswering());

			String set16 = Integer.toHexString(Integer.parseInt(setString.toString(), 2));
			
			
			
			sendMsg.append(set16).append(",");


			if (deviceSet.getDisabledInClass() == 1) {
				WatchDeviceHomeSchool whsc = ideviceService.getDeviceHomeAndFamilyInfoByImei(imei);
				if (whsc != null) {
					sendMsg.append(whsc.getClassDisable1() + "|" + whsc.getClassDisable2() + "|"
							+ whsc.getWeekDisable1() + ",");
				} else {
					sendMsg.append("08:00-11:30|14:00-16:30|12345,");
				}
			} else {
				sendMsg.append("08:00-11:30|14:00-16:30|12345,");
			}

			if (deviceSet.getTimerSwitch()==1) {
				
				TimeSwitch time = confService.getTimeSwitchByImei(imei);
				if (time != null) {
					sendMsg.append(time.getTimeOpen() + "," + time.getTimeClose() + ",");
				} else {
					sendMsg.append("06:05,23:00,");
				}
			} else {
				sendMsg.append("06:05,23:00,");
			}
			if(deviceSet.getBrightScreen()==0){
				sendMsg.append( "10,2,480,0,");
			}else{
				sendMsg.append(deviceSet.getBrightScreen() + ",2,480,0,");
			}
		

			
			if (watch != null) {
				sendMsg.append(watch.getWeekAlarm1() + "," + watch.getWeekAlarm2() + "," + watch.getWeekAlarm3() + ","
						+ watch.getAlarm1() + "," + watch.getAlarm2() + "," + watch.getAlarm3() + ",");
			} else {
				sendMsg.append("0:0,0:0,0:0,00:00,00:00,00:00, ");
			}
			sendMsg.append(deviceSet.getLocationMode() + "," + deviceSet.getLocationTime() + "," + deviceSet.getFlowerNumber());
			
		} else {
			sendMsg.append("500,");
			sendMsg.append("08:00-11:30|14:00-16:30|12345,");

			sendMsg.append("06:05,23:00,");

			sendMsg.append("10,2,480,0,");
			
			  if(watch!=null){
	        	   sendMsg.append(watch.getWeekAlarm1() + "," + watch.getWeekAlarm2() + "," + watch.getWeekAlarm3() + "," + watch.getAlarm1() + "," + watch.getAlarm2()  + ","
	   					+ watch.getAlarm3()  + ",");
	           }else{
	        	   sendMsg.append( "0:0,0:0,0:0,00:00,00:00,00:00,");
	           }
			sendMsg.append("1,60,0");
}

		HealthStepManagement heathM = confService.getHeathStepInfo(imei);
		if (heathM != null) {
			sendMsg.append("," + heathM.getSleepCalculate() + "," + heathM.getStepCalculate() + ",0,0,baby");
		} else {
			sendMsg.append(",1|23:00-23:59|05:00-06:00,0,0,0,baby");
		}

		String reps = "[YW*" + imei + "*0001*" + RadixUtil.changeRadix(sendMsg.toString()) + "*"
				+ sendMsg.toString() + "]";
		
		
		/*
		 * [YW*YYYYYYYYYY*NNNN*LEN*SET,
		 * 手表电话号码，设置次数流水号,设置项,上课禁用时间段,定时开机时间,定时关机时间,亮屏时间,语言,时区,指示灯,
		 * 闹钟1周期,闹钟2周期,闹钟3周期,闹钟1时间,闹钟2时间,闹钟3时间,定位模式,定位时间,小红花,睡眠,计步,心率,
		 * SOS短信开关,手表宝贝名称,后续还会加设置需要保留扩展]
		 * [YW*872018020142169*0001*0056*SET,123456,F48,08:00-11:30|14:00-16
		 * :30|12345,06:05,23:00,20,2,480,0,0:12345,0:0,2,10,5]
		 * [YW*872018020142169*0001*006a*SET,123456,F48,08:00-11:30|14:00-16
		 * :30|12345,06:05,23:00,60,2,480,0,1:234,0:0,0:0,02:00,00:00,00:00,
		 * 2,10,2] 实例:
		 * [YW*5678901234*0001*000A*SET,13232211111,1234,F48,08:00-11:30|14:
		 * 00-16:30|12345,06:05,23:00,10,2,480,0 ,
		 * 0:12345,0:0,0:0,06:30,00:00,00:00,1,60,,,,,,宝贝]
		 * 
		 * [YW*872018020142169*0001*0065*SET,123456,F48,10:00-12:00|15:00-17
		 * :00|1245,
		 * 06:05,23:00,5,,,1:123,0:25,0:234,02:00,03:00,21:00,2,10,4]
		 */
		return resp+reps;
	}
		
		
		
	/*	
	 * StringBuffer sb = new StringBuffer("[YW*" + imei + "*0001*");//0002*
        StringBuffer add=new StringBuffer("IPREQ,");
		add.append(1);
		add.append(",");

	
			add.append(Utils.SLB_IP);//这是负载均衡的ip和端口
		
		sb.append(RadixUtil.changeRadix(add.toString()));
		sb.append("*");
		sb.append(add.toString());
		sb.append("]");
		
  return sb.toString();*/
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
