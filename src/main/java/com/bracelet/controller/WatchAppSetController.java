package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.HttpBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.BindDevice;
import com.bracelet.entity.Location;
import com.bracelet.entity.LocationFrequency;
import com.bracelet.entity.LocationOld;
import com.bracelet.entity.LocationRequest;
import com.bracelet.entity.OldBindDevice;
import com.bracelet.entity.Step;
import com.bracelet.entity.UserInfo;
import com.bracelet.entity.VersionInfo;
import com.bracelet.entity.WatchDevice;
import com.bracelet.entity.WatchDeviceAlarm;
import com.bracelet.entity.WatchDeviceHomeSchool;
import com.bracelet.entity.WatchDeviceSet;
import com.bracelet.exception.BizException;
import com.bracelet.service.IDeviceService;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IStepService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.service.WatchSetService;
import com.bracelet.socket.BaseChannelHandler;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.HttpClientGet;
import com.bracelet.util.PushUtil;
import com.bracelet.util.RadixUtil;
import com.bracelet.util.RanomUtil;
import com.bracelet.util.RespCode;
import com.bracelet.util.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/watchset")
public class WatchAppSetController extends BaseController {

	@Autowired
	ILocationService locationService;
	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	IStepService stepService;
	@Autowired
	WatchSetService watchSetService;

	@Autowired
	IDeviceService ideviceService;
	@Resource
	BaseChannelHandler baseChannelHandler;
	private Logger logger = LoggerFactory.getLogger(getClass());

	/* 跟踪模式 */
	@ResponseBody
	@RequestMapping(value = "/tracker/{token}/{imei}", method = RequestMethod.GET)
	public String tracker(@PathVariable String token, @PathVariable String imei) {
		JSONObject bb = new JSONObject();

		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}

		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("Code", 4);
			return bb.toString();
		}
		if (socketLoginDto.getChannel().isActive()) {
			String reps = "[YW*" + imei + "*0001*0007*TRACKER]";
			socketLoginDto.getChannel().writeAndFlush(reps);
			bb.put("Code", 1);
		} else {
			bb.put("Code", 0);
		}
		return bb.toString();
	}

	/* 发送监控指令 */
	@ResponseBody
	@RequestMapping(value = "/guard/{token}/{imei}/{type}", method = RequestMethod.GET)
	public String guard(@PathVariable String token, @PathVariable String imei, @PathVariable Integer type) {
		JSONObject bb = new JSONObject();

		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}

		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("Code", 4);
			return bb.toString();
		}
		String reps = "[YW*" + imei + "*0001*0007*GUARD," + type + "]";
		if (socketLoginDto.getChannel().isActive()) {
			socketLoginDto.getChannel().writeAndFlush(reps);
			bb.put("Code", 1);
		} else {
			bb.put("Code", 0);
		}
		return bb.toString();
	}

	/* 设置全部参数指令 */
	@ResponseBody
	@RequestMapping(value = "/set", method = RequestMethod.POST)
	public String setParameter(@RequestBody String body) {
		JSONObject bb = new JSONObject();
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");
		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			bb.put("Message", "");
			return bb.toString();
		}
		/*
		 * 1．  手表设置setInfo："1-1-1-1-0-1-1-1-1-0-1-1"   对应手表信息振动，信息手表声音，
		 * 手表来电话振动，手表来电话声音， 手表脱落报警，拒绝陌生人来电， 定时开关机，上课禁用， 预留紧急电量， 体感接听，报告通话位置，
		 * 自动接听sosMsgswitch："1" sos开关flowerNumber："0" 爱心奖励brightScreen：
		 * 亮屏时间language： 语言timeZone：locationMode： 工作模式locationTime：工作时长
		 */
		String imei = jsonObject.getString("imei");
		String setInfo = jsonObject.getString("setInfo");
		String infoVibration = jsonObject.getString("infoVibration");// 手表信息震动
		String infoVoice = jsonObject.getString("infoVoice");// 手表信息声音
		String phoneComeVibration = jsonObject.getString("phoneComeVibration");// 手表来电话震动
		String phoneComeVoice = jsonObject.getString("phoneComeVoice");// 手表来电话声音
		String watchOffAlarm = jsonObject.getString("watchOffAlarm");// 手表脱落报警
		String rejectStrangers = jsonObject.getString("rejectStrangers");// 拒绝陌生人来电
		String timerSwitch = jsonObject.getString("timerSwitch");// 定时开关机
		String disabledInClass = jsonObject.getString("disabledInClass");// 上课禁用
		String reserveEmergencyPower = jsonObject.getString("reserveEmergencyPower");// 预留紧急电量6
		String somatosensory = jsonObject.getString("somatosensory");// 体感接听
		String reportCallLocation = jsonObject.getString("reportCallLocation");// 报告通话位置
		String automaticAnswering = jsonObject.getString("automaticAnswering");// 自动接听
		String sosMsgswitch = jsonObject.getString("sosMsgswitch");// sos开关
		String flowerNumber = jsonObject.getString("flowerNumber");// 爱心奖励
		String brightScreen = jsonObject.getString("brightScreen");// 亮屏时间
		String language = jsonObject.getString("language");// 语言
		String timeZone = jsonObject.getString("timeZone");// 时区
		String locationMode = jsonObject.getString("locationMode");// 工作模式
		String locationTime = jsonObject.getString("locationTime");// 工作时长

		WatchDeviceSet deviceSet = watchSetService.getDeviceSetByImei(imei);
		if (deviceSet != null) {
			watchSetService.updateWatchSet(deviceSet.getId(), setInfo, infoVibration, infoVoice, phoneComeVibration,
					phoneComeVoice, watchOffAlarm, rejectStrangers, timerSwitch, disabledInClass, reserveEmergencyPower,
					somatosensory, reportCallLocation, automaticAnswering, sosMsgswitch, flowerNumber, brightScreen,
					language, timeZone, locationMode, locationTime);
		} else {
			watchSetService.insertWatchDeviceSet(imei, setInfo, infoVibration, infoVoice, phoneComeVibration,
					phoneComeVoice, watchOffAlarm, rejectStrangers, timerSwitch, disabledInClass, reserveEmergencyPower,
					somatosensory, reportCallLocation, automaticAnswering, sosMsgswitch, flowerNumber, brightScreen,
					language, timeZone, locationMode, locationTime);
		}

		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("Code", 4);
			// bb.put("Message", "");
			return bb.toString();
		}
		if (socketLoginDto.getChannel().isActive()) {
			String msg = "SET," + "" + ",123456,F48," + disabledInClass + ",06:05,23:00," + brightScreen + ","
					+ language + "," + timeZone + ",0,123456,07:00," + locationMode + "," + locationTime + ","
					+ flowerNumber;
			/*
			 * [YW*YYYYYYYYYY*NNNN*LEN*SET,
			 * 手表电话号码，设置次数流水号,设置项,上课禁用时间段,定时开机时间,定时关机时间,亮屏时间,语言,时区,指示灯,
			 * 闹钟1周期,闹钟2周期,闹钟3周期,闹钟1时间,闹钟2时间,闹钟3时间,定位模式,定位时间,小红花,睡眠,计步,心率,
			 * SOS短信开关,手表宝贝名称,后续还会加设置需要保留扩展]
			 * 
			 * 实例:
			 * [YW*5678901234*0001*000A*SET,13232211111,1234,F48,08:00-11:30|14:
			 * 00-16:30|12345,06:05,23:00,10,2,480,0 ,
			 * 0:12345,0:0,0:0,06:30,00:00,00:00,1,60,,,,,,宝贝]
			 */
			String reps = "[YW*" + imei + "*0001*" + RadixUtil.changeRadix(msg) + "*" + msg + "]";
			logger.info("设备参数设置=" + reps);
			socketLoginDto.getChannel().writeAndFlush(reps);
			bb.put("Code", 1);
			// bb.put("Message", "");
			
			
			JSONObject push = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			JSONObject dataMap = new JSONObject();
			dataMap.put("DeviceID", "");
			String deviceid = limitCache.getRedisKeyValue(imei + "_id");
			if(deviceid !=null && !"0".equals(deviceid) && !"".equals(deviceid)){
				dataMap.put("DeviceID", deviceid);
			}else{
				WatchDevice watchd = ideviceService.getDeviceInfo(imei);
				if (watchd != null) {
					deviceid=watchd.getId()+"";
					dataMap.put("DeviceID", watchd.getId());
					limitCache.addKey(imei + "_id", watchd.getId()+"");
				}
			}
			dataMap.put("Message", 1);
			dataMap.put("Voice", 0);
			dataMap.put("SMS", 0);
			dataMap.put("Photo", 0);
			jsonArray.add(dataMap);
			push.put("NewList", jsonArray);
			JSONArray jsonArray1 = new JSONArray();
			JSONObject dataMap1 = new JSONObject();
			jsonArray1.add(dataMap1);
			push.put("DeviceState", jsonArray1);

			JSONArray jsonArray2 = new JSONArray();
			JSONObject dataMap2 = new JSONObject();
			dataMap2.put("Type", 231);
			dataMap2.put("DeviceID", deviceid);
			jsonArray2.add(dataMap2);
			push.put("Notification", jsonArray2);

			push.put("Code", 1);
			push.put("New", 1);
			PushUtil.push(token, "更新设备设置", push.toString(), "更新设备设置");	
			

		} else {
			bb.put("Code", 2);
			// bb.put("Message", "");
		}
		return bb.toString();
	}

	// 获取设备设置全部参数
	@ResponseBody
	@RequestMapping(value = "/getDeviceSet/{token}/{imei}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String getDeviceSet(@PathVariable String token, @PathVariable String imei) {
		JSONObject bb = new JSONObject();
		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		WatchDeviceSet deviceSet = watchSetService.getDeviceSetByImei(imei);
		if (deviceSet != null) {
			// bb.put("data", deviceSet.getData());
			bb.put("Code", 1);
			bb.put("SetInfo", deviceSet.getSetInfo() + "");
			bb.put("ClassDisabled1", "08:00-12:00");
			bb.put("ClassDisabled2", "14:00-17:00");
			bb.put("WeekDisabled", "");

			WatchDeviceHomeSchool whsc = ideviceService.getDeviceHomeAndFamilyInfoByImei(imei);
			if (whsc != null) {
				bb.put("ClassDisabled1", whsc.getClassDisable1() + "");
				bb.put("ClassDisabled2", whsc.getClassDisable2() + "");
				bb.put("WeekDisabled", whsc.getWeekDisable() + "");
			}

			bb.put("TimerOpen", "07:00");
			bb.put("TimerClose", "00:00");
			bb.put("BrightScreen", "10");

			bb.put("WeekAlarm1", "");
			bb.put("WeekAlarm2", "");
			bb.put("WeekAlarm3", "");
			bb.put("Alarm1", "");
			bb.put("Alarm2", "");
			bb.put("Alarm3", "");

			WatchDeviceAlarm watch = ideviceService.getDeviceAlarmInfo(imei);
			if (watch != null) {
				bb.put("WeekAlarm1", watch.getWeekAlarm1() + "");
				bb.put("WeekAlarm2", watch.getWeekAlarm2() + "");
				bb.put("WeekAlarm3", watch.getWeekAlarm3() + "");
				bb.put("Alarm1", watch.getAlarm1() + "");
				bb.put("Alarm2", watch.getAlarm2() + "");
				bb.put("Alarm3", watch.getAlarm3() + "");
			}
			bb.put("LocationMode", "1");
			bb.put("FlowerNumber", 0);
			bb.put("SleepCalculate", "");
			bb.put("StepCalculate", "");
			bb.put("HrCalculate", "");
			bb.put("SosMsgswitch", "");
			bb.put("CreateTime", deviceSet.getCreatetime().getTime());
			bb.put("UpdateTime", deviceSet.getUpdatetime().getTime());

			WatchDeviceSet deviceSetInfo = watchSetService.getDeviceSetByImei(imei);
			if (deviceSetInfo != null) {
				bb.put("BrightScreen", deviceSetInfo.getBrightScreen() + "");
				bb.put("LocationMode", deviceSetInfo.getLocationMode() + "");
				bb.put("LocationTime", deviceSetInfo.getLocationTime() + "");
				if (!"".equals(deviceSetInfo.getFlowerNumber()) && deviceSetInfo.getFlowerNumber() != null) {
					bb.put("FlowerNumber", Integer.valueOf(deviceSetInfo.getFlowerNumber() + ""));
				}

				bb.put("SosMsgswitch", deviceSetInfo.getSosMsgswitch() + "");
			}
			
			LocationFrequency  locaFre = watchSetService.getLocationFrequencyByImei(imei);
			if(locaFre != null){
				bb.put("LocationMode", locaFre.getFrequency()+"");
			}

		} else {
			bb.put("Code", 0);
			bb.put("SetInfo", "1-1-1-1-0-0-0-0-1-0-1-0");
			bb.put("ClassDisabled1", "08:00-12:00");
			bb.put("ClassDisabled2", "14:00-17:00");
			bb.put("WeekDisabled", "");
			bb.put("TimerOpen", "07:00");
			bb.put("TimerClose", "00:00");
			bb.put("BrightScreen", "10");
			bb.put("WeekAlarm1", "");
			bb.put("WeekAlarm2", "");
			bb.put("WeekAlarm3", "");
			bb.put("Alarm1", "");
			bb.put("Alarm2", "");
			bb.put("Alarm3", "");
			bb.put("LocationMode", "");
			bb.put("LocationTime", "");
			bb.put("FlowerNumber", "");
			bb.put("SleepCalculate", "");
			bb.put("StepCalculate", "");
			bb.put("HrCalculate", "");
			bb.put("SosMsgswitch", "");
			bb.put("CreateTime", "");
			bb.put("UpdateTime", "");
		}
		return bb.toString();
	}

	// 通讯录设置 这种直接让app封装data
	@ResponseBody
	@RequestMapping(value = "/communicationSettings", method = RequestMethod.POST)
	public String CommunicationSettings(@RequestBody String body) {
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");

		JSONObject bb = new JSONObject();
		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		String imei = jsonObject.getString("imei");
		String data = jsonObject.getString("data");

		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("Code", 0);
			return bb.toString();
		}

		if (socketLoginDto.getChannel().isActive()) {
			String msg = "PHB," + data;
			String reps = "[YW*" + imei + "*0001*" + RadixUtil.changeRadix(msg) + "*" + msg + "]";
			socketLoginDto.getChannel().writeAndFlush(reps);
			bb.put("Code", 1);
		} else {
			bb.put("Code", 0);
		}
		return bb.toString();
	}

	// 设置设备定位频率
	@ResponseBody
	@RequestMapping(value = "/setLocationFrequency/{token}/{imei}/{f}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String setLocationFrequency(@PathVariable String token, @PathVariable String imei,
			@PathVariable Integer f) {
		JSONObject bb = new JSONObject();
		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		LocationFrequency  locaFre = watchSetService.getLocationFrequencyByImei(imei);
		if(locaFre != null){
			if(f == locaFre.getFrequency()){
				bb.put("Code", 1);
			}else{
				if(watchSetService.updateLocationFrequencyById(locaFre.getId(),f)){
					bb.put("Code", 1);
				}else{
					bb.put("Code", 0);
				}
			}
		}else{
			if(watchSetService.insertLocationFrequency(imei,f)){
				bb.put("Code", 1);
			}else{
				bb.put("Code", 0);
			}
		}
		
		return bb.toString();

	}
	
	
	// 获取设备定位频率
		@ResponseBody
		@RequestMapping(value = "/getLocationFrequency/{token}/{imei}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
		public String getLocationFrequency(@PathVariable String token, @PathVariable String imei) {
			JSONObject bb = new JSONObject();
			String userId = checkTokenWatchAndUser(token);
			if ("0".equals(userId)) {
				bb.put("Code", -1);
				return bb.toString();
			}
			LocationFrequency  locaFre = watchSetService.getLocationFrequencyByImei(imei);
			if(locaFre != null){
				bb.put("Code", 1);
				bb.put("f", locaFre.getFrequency());
			}else{
				bb.put("Code", 1);
				bb.put("f", 1);
			}
			
			return bb.toString();

		}

}
