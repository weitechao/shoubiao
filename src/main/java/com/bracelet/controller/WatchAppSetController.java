package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.HttpBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.BindDevice;
import com.bracelet.entity.HealthStepManagement;
import com.bracelet.entity.Location;
import com.bracelet.entity.LocationFrequency;
import com.bracelet.entity.LocationOld;
import com.bracelet.entity.LocationRequest;
import com.bracelet.entity.OldBindDevice;
import com.bracelet.entity.Step;
import com.bracelet.entity.TimeSwitch;
import com.bracelet.entity.UserInfo;
import com.bracelet.entity.VersionInfo;
import com.bracelet.entity.WatchDevice;
import com.bracelet.entity.WatchDeviceAlarm;
import com.bracelet.entity.WatchDeviceHomeSchool;
import com.bracelet.entity.WatchDeviceSet;
import com.bracelet.entity.WatchDialpad;
import com.bracelet.exception.BizException;
import com.bracelet.service.IConfService;
import com.bracelet.service.IDeviceService;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IPushlogService;
import com.bracelet.service.IStepService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.service.WatchSetService;
import com.bracelet.socket.BaseChannelHandler;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.HttpClientGet;
import com.bracelet.util.IOSPushUtil;
import com.bracelet.util.AndroidPushUtil;
import com.bracelet.util.RadixUtil;
import com.bracelet.util.RanomUtil;
import com.bracelet.util.RespCode;
import com.bracelet.util.StringUtil;
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
	IConfService confService;

	@Autowired
	IDeviceService ideviceService;
	@Autowired
	IPushlogService pushlogService;
	
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
		

		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("Code", 4);
			// bb.put("Message", "");
			return bb.toString();
		}
		if (socketLoginDto.getChannel().isActive()) {
			
			Integer brightScreen = Integer.valueOf(jsonObject.getString("brightScreen"));// 亮屏时间
			Integer flowerNumber =  Integer.valueOf(jsonObject.getString("flowerNumber"));// 爱心奖励
			Integer phoneComeVibration = Integer.valueOf(jsonObject.getString("phoneComeVibration"));// 手表来电话震动
			
			Integer language = 2;// 语言
			Integer infoVibration = Integer.valueOf(jsonObject.getString("infoVibration"));// 手表信息震动
			Integer disabledInClass = Integer.valueOf(jsonObject.getString("disabledInClass"));// 上课禁用******
			Integer rejectStrangers = Integer.valueOf(jsonObject.getString("rejectStrangers"));// 拒绝陌生人来电***
			Integer timerSwitch =  Integer.valueOf(jsonObject.getString("timerSwitch"));// 定时开关机***************
			Integer reportCallLocation = Integer.valueOf(jsonObject.getString("reportCallLocation"));// 报告通话位置
			Integer phoneComeVoice = Integer.valueOf(jsonObject.getString("phoneComeVoice"));// 手表来电响铃
			Integer watchOffAlarm = Integer.valueOf(jsonObject.getString("watchOffAlarm"));// 手表脱落报警
			String setInfo = jsonObject.getString("setInfo");
			Integer locationMode = Integer.valueOf(jsonObject.getInteger("locationMode"));// 工作模式
//			Integer timeZone = Integer.valueOf(jsonObject.getString("timeZone"));// 时区
			Integer timeZone = 480;// 时区
			Integer infoVoice = Integer.valueOf(jsonObject.getString("infoVoice"));// 手表信息声音
			//Integer locationTime = Integer.valueOf(jsonObject.getString("locationTime"));// 工作时长
			Integer locationTime = 60;// 工作时长
			Integer reserveEmergencyPower = Integer.valueOf(jsonObject.getString("reserveEmergencyPower"));// 预留紧急电量6
			Integer sosMsgswitch = Integer.valueOf(jsonObject.getString("sosMsgswitch"));// sos短信开关
			Integer automaticAnswering = Integer.valueOf(jsonObject.getString("automaticAnswering"));// 自动接听
			Integer somatosensory = Integer.valueOf(jsonObject.getString("somatosensory"));// 体感接听
			
//			Integer language = jsonObject.getInteger("language");// 语言
	
		

			/*
			["{\n  \"brightScreen\" : \"5\",
			\n  \"flowerNumber\" : \"4\",
			\n  \"phoneComeVibration\" : \"1\",\n  
			\"language\" : \"\",\n  
			\"infoVibration\" : \"1\",\n 
			 \"disabledInClass\" : \"1\",\n  \"imei\" : \"861807000437760\",\n 
			  \"rejectStrangers\" : \"0\",\n  
			  \"timerSwitch\" : \"1\",\n  \"reportCallLocation\" : \"1\",\n  
			  \"phoneComeVoice\" : \"0\",\n  \"watchOffAlarm\" : \"0\",\n  \"setInfo\" : \"1-1-1-0-0-0-1-1-1-0-1-1\",\n 
			   \"locationMode\" : \"2\",\n  \"timeZone\" : \"\",\n  \"infoVoice\" : \"1\",\n  
			   \"token\" : \"9A5FFC09DB7CB212E47F120F2AE5207F\",\n  
			   \"locationTime\" : \"0\",\n  \"reserveEmergencyPower\" : \"1\",\n  \"sosMsgswitch\" : \"0\",\n  \"automaticAnswering\" : \"1\",\n  \"somatosensory\" : \"0\"\n}"]* */
			
			//修改数据库的数据
			 WatchDeviceSet deviceSet = watchSetService.getDeviceSetByUserId(Long.valueOf(userId));
				if (deviceSet != null) {
					watchSetService.updateWatchSet(deviceSet.getId(), setInfo, infoVibration, infoVoice, phoneComeVibration,
							phoneComeVoice, watchOffAlarm, rejectStrangers, timerSwitch,disabledInClass, reserveEmergencyPower,
							somatosensory, reportCallLocation, automaticAnswering, sosMsgswitch, Integer.valueOf(flowerNumber), brightScreen,
							language, 1, locationMode, locationTime);
				} else {
					watchSetService.insertWatchDeviceSet(Long.valueOf(userId),imei, setInfo, infoVibration, infoVoice, phoneComeVibration,
							phoneComeVoice, watchOffAlarm, rejectStrangers, timerSwitch, disabledInClass, reserveEmergencyPower,
							somatosensory, reportCallLocation, automaticAnswering, sosMsgswitch, Integer.valueOf(flowerNumber), brightScreen,
							language, 1, locationMode, locationTime);
				}
				
			
				StringBuffer setString = new StringBuffer("");
				setString.append(infoVibration).append(infoVoice).append(phoneComeVibration).append(phoneComeVoice)
				.append(watchOffAlarm).append(rejectStrangers).append(timerSwitch).append(disabledInClass).append(reserveEmergencyPower)
	            .append(somatosensory).append(reportCallLocation).append(automaticAnswering);
				
				String set16 = Integer.toHexString(Integer.parseInt(setString.toString(), 2));
				
			StringBuffer sendMsg = new StringBuffer("SET" + ",,1234,");//F48,");
			sendMsg.append(set16).append(",");


			if (disabledInClass == 1) {
				WatchDeviceHomeSchool whsc = ideviceService.getDeviceHomeAndFamilyInfo(Long.valueOf(userId));
				if (whsc != null) {
					sendMsg.append(whsc.getClassDisable1() + "|" + whsc.getClassDisable2() + "|"
							+ whsc.getWeekDisable1() + ",");
				} else {
					sendMsg.append("08:00-11:30|14:00-16:30|12345,");
				}
			} else {
				sendMsg.append("08:00-11:30|14:00-16:30|12345,");
			}

			if (timerSwitch==1) {
				TimeSwitch time = confService.getTimeSwitch(Long.valueOf(userId));
				if (time != null) {
					sendMsg.append(time.getTimeOpen() + "," + time.getTimeClose() + ",");
				} else {
					sendMsg.append("06:05,23:00,");
				}
			} else {
				sendMsg.append("06:05,23:00,");
			}
			if(brightScreen==0){
				sendMsg.append( "10,2,480,0,");
			}else{
				sendMsg.append(brightScreen + ",2,480,0,");
			}
		

			WatchDeviceAlarm watch = ideviceService.getDeviceAlarmInfo(imei);
			if (watch != null) {
				sendMsg.append(watch.getWeekAlarm1() + "," + watch.getWeekAlarm2() + "," + watch.getWeekAlarm3() + ","
						+ watch.getAlarm1() + "," + watch.getAlarm2() + "," + watch.getAlarm3() + ",");
			} else {
				sendMsg.append("0:0,0:0,0:0,00:00,00:00,00:00, ");
			}
			sendMsg.append(locationMode + "," + locationTime + "," + flowerNumber);
			
			
			
			HealthStepManagement  heathM = confService.getHeathStepInfo(imei);
			if(heathM != null){
				sendMsg.append(","+heathM.getSleepCalculate()+","+heathM.getStepCalculate()+",0,"+sosMsgswitch+","+"baby");
			}else{
				sendMsg.append(",1|23:00-23:59|05:00-06:00,0,0,"+sosMsgswitch+","+"baby");
			}
			
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
			String reps = "[YW*" + imei + "*0001*" + RadixUtil.changeRadix(sendMsg.toString()) + "*"
					+ sendMsg.toString() + "]";
			logger.info("设备参数设置=" + reps);
			socketLoginDto.getChannel().writeAndFlush(reps);
		
			
			//下面开始推送组包

			JSONObject push = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			JSONObject dataMap = new JSONObject();
			dataMap.put("DeviceID", "");
			String deviceid = limitCache.getRedisKeyValue(imei + "_id");
			if (deviceid != null && !"0".equals(deviceid) && !"".equals(deviceid)) {
				dataMap.put("DeviceID", deviceid);
			} else {
				WatchDevice watchd = ideviceService.getDeviceInfo(imei);
				if (watchd != null) {
					deviceid = watchd.getId() + "";
					dataMap.put("DeviceID", watchd.getId());
					limitCache.addKey(imei + "_id", watchd.getId() + "");
				}
			}
			dataMap.put("Message", 1);
			dataMap.put("Voice", 0);
			dataMap.put("SMS", 0);
			dataMap.put("Photo", 0);
			jsonArray.add(dataMap);
			push.put("NewList", jsonArray);
			JSONArray jsonArray1 = new JSONArray();
			push.put("DeviceState", jsonArray1);

			JSONArray jsonArray2 = new JSONArray();
			JSONObject dataMap2 = new JSONObject();
			dataMap2.put("Type", 231);
			dataMap2.put("DeviceID", deviceid);
			dataMap2.put("Message", "成功更新设备设置");
			dataMap2.put("imei", imei);
			jsonArray2.add(dataMap2);
			push.put("Notification", jsonArray2);

			push.put("Code", 1);
			push.put("New", 1);
			pushlogService.insertMsgInfo(imei, 231, deviceid, "成功更新设备设置", "成功更新设备设置");
			AndroidPushUtil.push(token, "成功更新设备设置", push.toString(), "成功更新设备设置");
			IOSPushUtil.push(token, "成功更新设备设置", push.toString(), "成功更新设备设置");
			
			// bb.put("Message", "");
			
			bb.put("Code", 1);
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
		WatchDeviceSet deviceSet = watchSetService.getDeviceSetByUserId(Long.valueOf(userId));
		if (deviceSet != null) {
			// bb.put("data", deviceSet.getData());
			bb.put("Code", 1);
			bb.put("SetInfo", deviceSet.getSetInfo() + "");
			bb.put("ClassDisabled1", "08:00-12:00");
			bb.put("ClassDisabled2", "14:00-17:00");
			bb.put("WeekDisabled", "");
			bb.put("dialPad", "1");

			WatchDeviceHomeSchool whsc = ideviceService.getDeviceHomeAndFamilyInfoByImei(imei);

			if (whsc != null) {
				bb.put("ClassDisabled1", whsc.getClassDisable1() + "");
				bb.put("ClassDisabled2", whsc.getClassDisable2() + "");
				bb.put("WeekDisabled", whsc.getWeekDisable1() + "");
			}

			bb.put("TimerOpen", "07:00");
			bb.put("TimerClose", "00:00");
			
			  TimeSwitch time = confService.getTimeSwitch(Long.valueOf(userId));
			  if(time != null){
				  bb.put("TimerOpen", time.getTimeOpen()+"");
				  bb.put("TimerClose", time.getTimeClose()+"");
			  }
			 
			  
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
			bb.put("SleepCalculate", "1|23:00-23:59|05:00-06:00");
			bb.put("StepCalculate", "0");
			bb.put("HrCalculate", "0");
			bb.put("SosMsgswitch", "0");
			
			bb.put("CreateTime", Utils.getLocationTime(deviceSet.getCreatetime().getTime()));
			bb.put("UpdateTime", deviceSet.getUpdatetime().getTime());
			
			bb.put("BrightScreen", deviceSet.getBrightScreen());
			bb.put("LocationMode", deviceSet.getLocationMode());
			bb.put("LocationTime", deviceSet.getLocationTime());
			bb.put("FlowerNumber", deviceSet.getFlowerNumber());
			bb.put("SosMsgswitch", deviceSet.getSosMsgswitch());
			
			HealthStepManagement  heathM = confService.getHeathStepInfo(imei);
	   		if(heathM != null){
		   		   bb.put("SleepCalculate", heathM.getSleepCalculate()+"");
		   		   bb.put("HrCalculate", heathM.getHrCalculate()+"");
		   		   bb.put("StepCalculate", heathM.getStepCalculate()+"");
		   		}

			LocationFrequency locaFre = watchSetService.getLocationFrequencyByImei(imei);
			if (locaFre != null) {
				bb.put("LocationMode", locaFre.getFrequency() + "");
			}

			WatchDialpad watDiapad = watchSetService.getWatchDialpad(imei);
			if (watDiapad != null) {
				bb.put("dialPad", watDiapad.getType() + "");
			}

		} else {
			bb.put("Code", 1);
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
			bb.put("LocationMode", "2");
			bb.put("LocationTime", 0);
			bb.put("FlowerNumber", 1);
			bb.put("SleepCalculate", "1|23:00-23:59|05:00-06:00");
			bb.put("StepCalculate", "0");
			bb.put("HrCalculate", "0");
			bb.put("SosMsgswitch", 0);
			bb.put("CreateTime", Utils.getLocationTime(System.currentTimeMillis()));
			bb.put("UpdateTime", "");
			bb.put("dialPad", "1");
			
			HealthStepManagement  heathM = confService.getHeathStepInfo(imei);
	   		if(heathM != null){
		   		   bb.put("SleepCalculate", heathM.getSleepCalculate()+"");
		   		   bb.put("HrCalculate", heathM.getHrCalculate()+"");
		   		   bb.put("StepCalculate", heathM.getStepCalculate()+"");
		   		}
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
	public String setLocationFrequency(@PathVariable String token, @PathVariable String imei, @PathVariable Integer f) {
		JSONObject bb = new JSONObject();
		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		LocationFrequency locaFre = watchSetService.getLocationFrequencyByImei(imei);
		if (locaFre != null) {
			if (f == locaFre.getFrequency()) {
				bb.put("Code", 1);
			} else {
				if (watchSetService.updateLocationFrequencyById(locaFre.getId(), f)) {
					bb.put("Code", 1);
				} else {
					bb.put("Code", 0);
				}
			}
		} else {
			if (watchSetService.insertLocationFrequency(imei, f)) {
				bb.put("Code", 1);
			} else {
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
		LocationFrequency locaFre = watchSetService.getLocationFrequencyByImei(imei);
		if (locaFre != null) {
			bb.put("Code", 1);
			bb.put("f", locaFre.getFrequency());
		} else {
			bb.put("Code", 1);
			bb.put("f", 1);
		}

		return bb.toString();
	}
	
	

}
