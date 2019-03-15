package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.BindDevice;
import com.bracelet.entity.DeviceManagePhone;
import com.bracelet.entity.HealthStepManagement;
import com.bracelet.entity.UserInfo;
import com.bracelet.entity.WatchDevice;
import com.bracelet.entity.WatchDeviceAlarm;
import com.bracelet.entity.WatchDeviceHomeSchool;
import com.bracelet.entity.WatchDialpad;
import com.bracelet.entity.WatchPhoneBook;
import com.bracelet.service.IAuthcodeService;
import com.bracelet.service.IConfService;
import com.bracelet.service.IDeviceService;
import com.bracelet.service.IFenceService;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IMemService;
import com.bracelet.service.IOpenDoorService;
import com.bracelet.service.IPushlogService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.service.IVoltageService;
import com.bracelet.service.WatchSetService;
import com.bracelet.service.WatchTkService;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.IOSPushUtil;
import com.bracelet.util.AndroidPushUtil;
import com.bracelet.util.RadixUtil;
import com.bracelet.util.RanomUtil;
import com.bracelet.util.RespCode;
import com.bracelet.util.StringUtil;
import com.bracelet.util.Utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/watchAppUser")
public class WatchAppUserController extends BaseController {

	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	IAuthcodeService authcodeService;
	@Autowired
	IVoltageService voltageService;
	@Autowired
	IOpenDoorService openService;
	@Autowired
	ILocationService locationService;

	@Autowired
	WatchTkService watchtkService;

	@Autowired
	IMemService memService;
	
	@Autowired
	WatchSetService watchSetService;

	@Autowired
	IFenceService fenceService;

	@Autowired
	IDeviceService ideviceService;
	
	@Autowired
	IMemService memberService;
	@Autowired
	IPushlogService pushlogService;
	
	@Autowired
	IConfService confService;
	private Logger logger = LoggerFactory.getLogger(getClass());

	// 登录
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String watchapplogin(@RequestBody String body) {
		try {

			JSONObject jsonObject = (JSONObject) JSON.parse(body);

			String tel = jsonObject.getString("tel");
			String password = jsonObject.getString("pwd");// 默认123456
			JSONObject bb = new JSONObject();

			// 先检查登录表，如果登录表里有，检查密码，如果密码正确则登录OK，判断设备是否在线，在线，则发送定位请求，不在线则查询最后一次定位，
			UserInfo userInfo = userInfoService.getUserInfoByUsername(tel);
			String ipport = limitCache.getRedisKeyValue(tel);
			if (userInfo != null) {

				if (password.equalsIgnoreCase(userInfo.getPassword())) {
					String token = tokenInfoService.genToken(userInfo.getUser_id());
					bb.put("Code", 1);// 1表示login succes
					bb.put("token", token);
					bb.put("LoginId", "0");
					bb.put("UserId", userInfo.getUser_id());
					limitCache.addKey(tel + "_userid", userInfo.getUser_id() + "");
					limitCache.addKey(tel + "_push", token);
					bb.put("PhoneNumber", "0");
					bb.put("BindNumber", tel+"");
					bb.put("Birthday", "");
					bb.put("UserType", 0);
					bb.put("Name", "0");
					bb.put("Notification", "True");
					bb.put("NotificationSound", "True");
					bb.put("NotificationVibration", "True");
					bb.put("ip", Utils.IP + ":" + Utils.PORT_HTTP);

					
					if (!StringUtil.isEmpty(ipport)) {
						bb.put("ip", ipport);
					}
					WatchDevice watchd = ideviceService.getDeviceInfo(tel);
					if (watchd != null) {
						bb.put("DeviceID", watchd.getId());
						limitCache.addKey(tel + "_id", watchd.getId() + "");
					}
					bb.put("phone", "");
					
					WatchPhoneBook phoneBook = memberService.getPhoneBookByImeiAndStatus(tel, 1);
					
					if(phoneBook != null){
						bb.put("phone", "1");
					}
					

				} else {
					bb.put("Code", 2);// 2表示密码错误
					bb.put("Message", "");// 2表示密码错误
				}
			} else {
				
				// if(userInfoLuRu !=null ){//说明已经录入 第一次登录
				userInfoService.saveUserInfo(tel, "123456", 1);// 表示新注册

				String token = tokenInfoService.genToken(userInfoService.getUserInfoByUsername(tel).getUser_id());
				bb.put("Code", 1);// 1表示login succes
				bb.put("token", token);
				bb.put("LoginId", "0");
				bb.put("UserId", "0");
				UserInfo userInfoo = userInfoService.getUserInfoByUsername(tel);
				if (userInfoo != null) {
					bb.put("UserId", userInfoo.getUser_id() + "");
					limitCache.addKey(tel + "_userid", userInfoo.getUser_id() + "");
				}
				limitCache.addKey(tel + "_push", token);

				bb.put("PhoneNumber", "0");
				bb.put("BindNumber", tel+"");
				bb.put("UserType", 0);
				bb.put("Name", "0");
				bb.put("Notification", "True");
				bb.put("NotificationSound", "True");
				bb.put("NotificationVibration", "True");
				bb.put("Birthday", "");
				bb.put("ip", Utils.IP + ":" + Utils.PORT_HTTP);
				if (ipport != null && !"".equals(ipport)) {
					bb.put("ip", ipport);
				}

				/*
				 * String deviceid = limitCache.getRedisKeyValue(tel + "_id");
				 * if (deviceid != null && !"0".equals(deviceid) &&
				 * !"".equals(deviceid)) { bb.put("DeviceID", deviceid); } else
				 * {
				 */
				WatchDevice watchd = ideviceService.getDeviceInfo(tel);
				if (watchd != null) {
					bb.put("DeviceID", watchd.getId());
					bb.put("Birthday", watchd.getBirday() + "");
					limitCache.addKey(tel + "_id", watchd.getId() + "");
					
					bb.put("phone", "");
                   WatchPhoneBook phoneBook = memberService.getPhoneBookByImeiAndStatus(tel, 1);
					if(phoneBook != null){
						bb.put("phone", "1");
					}
				} else {

					ideviceService.insertNewImei(tel, "1", 0, "1");
					bb.put("phone", "");
					WatchDevice watchdd = ideviceService.getDeviceInfo(tel);
					if (watchdd != null) {
						bb.put("DeviceID", watchdd.getId());
						limitCache.addKey(tel + "_id", watchdd.getId() + "");
					}

				}
				/* } */

			}
			logger.info("app登录deviceid=" + limitCache.getRedisKeyValue(tel + "_id"));
			return bb.toString();
		} catch (Exception e) {
			return e.getMessage() + "";
		}
	}

	// 修改密码
	@ResponseBody
	@RequestMapping(value = "/udpatePwd", method = RequestMethod.POST)
	public String watchappUpdatePwd(@RequestBody String body) {
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		JSONObject bb = new JSONObject();
		String token = jsonObject.getString("token");
		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		String tel = jsonObject.getString("tel");
		String password = jsonObject.getString("pwd");// 默认123456
		String oldPassword = jsonObject.getString("oldpwd");
		UserInfo userInfo = userInfoService.getUserInfoByUsername(tel);
		if (userInfo != null) {
			if (userInfo.getPassword().equals(oldPassword)) {
				userInfoService.updateUserPassword(userInfo.getUser_id(), password);
				bb.put("Code", 1);
			} else {
				bb.put("Code", 2);
			}

		} else {
			bb.put("Code", 0);
		}
		return bb.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/getAuthCode/{tel}", method = RequestMethod.GET)
	public String getAuthCode(@PathVariable String tel) {
		JSONObject bb = new JSONObject();
		if (StringUtils.isEmpty(tel)) {
			bb.put("code", 2);
			return bb.toString();
		}
		this.authcodeService.sendAuthCode(tel);
		bb.put("code", 1);
		return bb.toString();
	}

	// 找回密码
	@ResponseBody
	@RequestMapping(value = "/findPwd", method = RequestMethod.POST)
	public String findPwd(@RequestBody String body) {
		JSONObject jsonObject = (JSONObject) JSON.parse(body);

		// String token = jsonObject.getString("token");
		String tel = jsonObject.getString("tel");
		String password = jsonObject.getString("pwd");// 默认123456
		String code = jsonObject.getString("code");// 验证码
		JSONObject bb = new JSONObject();
		if (this.authcodeService.verifyAuthCode(tel, code)) {
			UserInfo userInfo = userInfoService.getUserInfoByUsername(tel);
			if (userInfo != null) {
				userInfoService.updateUserPassword(userInfo.getUser_id(), password);
				bb.put("code", 1);
			} else {
				bb.put("code", 0);
			}
		} else {
			bb.put("code", 2);
		}
		return bb.toString();
	}

	// 绑定设备
	@ResponseBody
	@RequestMapping(value = "/bindDevice", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String bindDevice(@RequestBody String body) {
		JSONObject bb = new JSONObject();
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");
		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("code", -1);
			return bb.toString();
		}
		long user_id = Long.valueOf(checkTokenWatchAndUser(token));
		String imei = jsonObject.getString("imei");
		String name = jsonObject.getString("name");
		BindDevice bd = userInfoService.getBindInfoByImeiAndStatus(imei, 1);
		if (bd != null) {// 说明已有管理员
			if (userInfoService.getBindInfoByImeiAndStatus(imei, 0) != null) {
				bb.put("Code", 0);
			} else {
				userInfoService.bindDevice(user_id, imei, 0, name);
				bb.put("Code", 1);
			}
		} else {
			userInfoService.bindDevice(user_id, imei, 1, name);
			bb.put("Code", 2);
		}

		return bb.toString();
	}

	// 获取绑定设备列表
	@ResponseBody
	@RequestMapping(value = "/getbindDeviceList/{token}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String getbindDeviceList(@PathVariable String token) {
		JSONObject bb = new JSONObject();
		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		long user_id = Long.valueOf(checkTokenWatchAndUser(token));

		List<BindDevice> bdList = userInfoService.getBindInfoById(user_id);
		JSONArray jsonArray = new JSONArray();
		if (bdList != null) {
			for (BindDevice location : bdList) {
				JSONObject dataMap = new JSONObject();
				dataMap.put("id", location.getId());
				dataMap.put("imei", location.getImei());
				dataMap.put("status", location.getStatus());
				dataMap.put("name", location.getName());
				dataMap.put("timestamp", location.getCreatetime().getTime());

				dataMap.put("ActiveDate", "");
				dataMap.put("BabyName", location.getName() + "");
				dataMap.put("BindNumber", location.getImei());
				dataMap.put("DeviceType", 1);
				dataMap.put("Birthday", "0");
				dataMap.put("CreateTime", "0");
				dataMap.put("CurrentFirmware", "Y01_K2_RDA6625_RENQI_LE_DIAN_LINUX.0.967.QGJ_V1.0");
				dataMap.put("SetVersionNO", "0");
				dataMap.put("ContactVersionNO", "0");
				dataMap.put("OperatorType", 0);
				dataMap.put("SmsNumber", "0");
				dataMap.put("SmsBalanceKey", "0");
				dataMap.put("SmsFlowKey", "0");
				dataMap.put("DeviceID", "1");

				String deviceid = limitCache.getRedisKeyValue(location.getImei() + "_id");
				if (deviceid != null && !"0".equals(deviceid) && !"".equals(deviceid)) {
					dataMap.put("DeviceID", deviceid);
				} else {
					WatchDevice watchd = ideviceService.getDeviceInfo(location.getImei());
					if (watchd != null) {
						dataMap.put("DeviceID", watchd.getId());
						limitCache.addKey(location.getImei() + "_id", watchd.getId() + "");
					}
				}

				dataMap.put("UserId", "0");
				dataMap.put("DeviceModelID", "10000100");
				dataMap.put("Firmware", "0");
				dataMap.put("Gender", 0);
				dataMap.put("Grade", 0);
				dataMap.put("HireExpireDate", "0");
				dataMap.put("HireStartDate", "0");
				
				dataMap.put("HomeAddress", "");
				dataMap.put("HomeLat", "0");
				dataMap.put("HomeLng", "0");
				dataMap.put("SchoolLat", "0");
				dataMap.put("SchoolLng", "0");
				dataMap.put("SchoolAddress", "");
				
				dataMap.put("IsGuard", "0");
				dataMap.put("Password", "0");
				dataMap.put("PhoneCornet", "");
				dataMap.put("PhoneNumber", "");
				dataMap.put("Photo", "0");
			
				dataMap.put("SerialNumber", location.getImei());
				dataMap.put("LatestTime", "0");
				dataMap.put("UpdateTime", "0");
				dataMap.put("CloudPlatform", 0);

				JSONObject deviceSet = new JSONObject();
				deviceSet.put("SetInfo", "1-1-1-1-0-0-0-0-1-0-1-0");

				deviceSet.put("WeekAlarm1", "");
				deviceSet.put("WeekAlarm2", "");
				deviceSet.put("WeekAlarm3", "");
				deviceSet.put("Alarm1", "");
				deviceSet.put("Alarm2", "");
				deviceSet.put("Alarm3", "");

				WatchDeviceAlarm watch = ideviceService.getDeviceAlarmInfo(location.getImei());
				if (watch != null) {
					deviceSet.put("WeekAlarm1", watch.getWeekAlarm1() + "");
					deviceSet.put("WeekAlarm2", watch.getWeekAlarm2() + "");
					deviceSet.put("WeekAlarm3", watch.getWeekAlarm3() + "");
					deviceSet.put("Alarm1", watch.getAlarm1() + "");
					deviceSet.put("Alarm2", watch.getAlarm2() + "");
					deviceSet.put("Alarm3", watch.getAlarm3() + "");
				}

				deviceSet.put("ClassDisabled1", "08:00-12:00");
				deviceSet.put("ClassDisabled2", "14:00-17:00");
				deviceSet.put("WeekDisabled", "");

				WatchDeviceHomeSchool whsc = ideviceService.getDeviceHomeAndFamilyInfoByImei(location.getImei());
				if (whsc != null) {
					deviceSet.put("ClassDisabled1", whsc.getClassDisable1() + "");
					deviceSet.put("ClassDisabled2", whsc.getClassDisable2() + "");
					deviceSet.put("WeekDisabled", whsc.getWeekDisable1() + "");
				}

				deviceSet.put("TimerOpen", "07:00");
				deviceSet.put("TimerClose", "00:00");
				deviceSet.put("BrightScreen", "0");

				deviceSet.put("LocationMode", 2);
				deviceSet.put("LocationTime", "0");
				deviceSet.put("FlowerNumber", 1);
				deviceSet.put("SleepCalculate", "1|23:00-23:59|05:00-06:00");
				deviceSet.put("sleepCalculate", "1|23:00-23:59|05:00-06:00");
				deviceSet.put("StepCalculate", "1");
				deviceSet.put("stepCalculate", "1");
				deviceSet.put("HrCalculate", "0");
				deviceSet.put("hrCalculate", "1");
				deviceSet.put("SosMsgswitch", "0");
				deviceSet.put("CreateTime", "1550130107000");
				deviceSet.put("UpdateTime", "1550130107000");
				deviceSet.put("dialPad", "1");
				
				WatchDialpad watDiapad = watchSetService.getWatchDialpad(location.getImei());
				if(watDiapad !=null ){
					deviceSet.put("dialPad", watDiapad.getType()+"");
				}
				
				dataMap.put("DeviceSet", deviceSet);

				JSONObject deviceState = new JSONObject();
				deviceState.put("Altitude", 0);
				deviceState.put("Course", 0);
				deviceState.put("LocationType", 0);
				deviceState.put("CreateTime", "0");
				deviceState.put("DeviceTime", "0");
				deviceState.put("Electricity", 0);
				deviceState.put("GSM", 0);
				deviceState.put("Step", 0);
				deviceState.put("Health", "0");
				deviceState.put("Latitude", 0);
				deviceState.put("Longitude", 0);
				deviceState.put("Online", 0);
				deviceState.put("SatelliteNumber", 0);
				deviceState.put("ServerTime", "");
				deviceState.put("Speed", 0);
				deviceState.put("UpdateTime", "");
				dataMap.put("DeviceState", deviceState);

				JSONArray jsonArray1 = new JSONArray();
				dataMap.put("ContactArr", jsonArray1);

				jsonArray.add(dataMap);
			}
			bb.put("Code", 1);

		} else {
			UserInfo UserInfo = userInfoService.getUserInfoById(user_id);
			if (userInfoService.saveBindInfo(user_id, UserInfo.getUsername(), "", 0)) {
				List<BindDevice> bdListt = userInfoService.getBindInfoById(user_id);

				for (BindDevice location : bdListt) {
					JSONObject dataMap = new JSONObject();
					dataMap.put("id", location.getId());
					dataMap.put("imei", location.getImei());
					dataMap.put("status", location.getStatus());
					dataMap.put("name", location.getName());
					dataMap.put("timestamp", location.getCreatetime().getTime());

					dataMap.put("ActiveDate", "");
					dataMap.put("BabyName", location.getName() + "");
					dataMap.put("BindNumber", location.getImei());
					dataMap.put("DeviceType", 1);
					dataMap.put("Birthday", "0");
					dataMap.put("CreateTime", "0");
					dataMap.put("CurrentFirmware", "Y01_K2_RDA6625_RENQI_LE_DIAN_LINUX.0.967.QGJ_V1.0");
					dataMap.put("SetVersionNO", "0");
					dataMap.put("ContactVersionNO", "0");
					dataMap.put("OperatorType", 0);
					dataMap.put("SmsNumber", "0");
					dataMap.put("SmsBalanceKey", "0");
					dataMap.put("SmsFlowKey", "0");
					dataMap.put("DeviceID", "");

					String deviceid = limitCache.getRedisKeyValue(location.getImei() + "_id");
					if (deviceid != null && !"0".equals(deviceid) && !"".equals(deviceid)) {
						dataMap.put("DeviceID", deviceid);
					} else {
						WatchDevice watchd = ideviceService.getDeviceInfo(location.getImei());
						if (watchd != null) {
							dataMap.put("DeviceID", watchd.getId());
							limitCache.addKey(location.getImei() + "_id", watchd.getId() + "");
						}
					}

					dataMap.put("UserId", "0");
					dataMap.put("DeviceModelID", "10000100");
					dataMap.put("Firmware", "0");
					dataMap.put("Gender", 0);
					dataMap.put("Grade", 0);
					dataMap.put("HireExpireDate", "0");
					dataMap.put("HireStartDate", "0");
					dataMap.put("HomeAddress", "0");
					dataMap.put("HomeLat", "0");
					dataMap.put("HomeLng", "0");
					dataMap.put("IsGuard", "0");
					dataMap.put("Password", "0");
					dataMap.put("PhoneCornet", "");
					dataMap.put("PhoneNumber", "");
					dataMap.put("Photo", "0");
					dataMap.put("SchoolAddress", "0");
					dataMap.put("SchoolLat", "0");
					dataMap.put("SchoolLng", "0");
					dataMap.put("SerialNumber", location.getImei());
					dataMap.put("LatestTime", "0");
					dataMap.put("UpdateTime", "0");
					dataMap.put("CloudPlatform", 0);

					JSONObject deviceSet = new JSONObject();
					deviceSet.put("SetInfo", "1-1-1-1-0-0-0-0-1-0-1-0");

					deviceSet.put("WeekAlarm1", "");
					deviceSet.put("WeekAlarm2", "");
					deviceSet.put("WeekAlarm3", "");
					deviceSet.put("Alarm1", "");
					deviceSet.put("Alarm2", "");
					deviceSet.put("Alarm3", "");

					WatchDeviceAlarm watch = ideviceService.getDeviceAlarmInfo(location.getImei());
					if (watch != null) {
						deviceSet.put("WeekAlarm1", watch.getWeekAlarm1() + "");
						deviceSet.put("WeekAlarm2", watch.getWeekAlarm2() + "");
						deviceSet.put("WeekAlarm3", watch.getWeekAlarm3() + "");
						deviceSet.put("Alarm1", watch.getAlarm1() + "");
						deviceSet.put("Alarm2", watch.getAlarm2() + "");
						deviceSet.put("Alarm3", watch.getAlarm3() + "");
					}

					deviceSet.put("ClassDisabled1", "08:00-12:00");
					deviceSet.put("ClassDisabled2", "14:00-17:00");
					deviceSet.put("WeekDisabled", "");

					WatchDeviceHomeSchool whsc = ideviceService.getDeviceHomeAndFamilyInfoByImei(location.getImei());
					if (whsc != null) {
						deviceSet.put("ClassDisabled1", whsc.getClassDisable1() + "");
						deviceSet.put("ClassDisabled2", whsc.getClassDisable2() + "");
						deviceSet.put("WeekDisabled", whsc.getWeekDisable1() + "");
					}

					deviceSet.put("TimerOpen", "07:00");
					deviceSet.put("TimerClose", "00:00");
					deviceSet.put("BrightScreen", "0");

					deviceSet.put("LocationMode", 2);
					deviceSet.put("LocationTime", "0");
					deviceSet.put("FlowerNumber", 1);
					deviceSet.put("SleepCalculate", "1|23:00-23:59|05:00-06:00");
					deviceSet.put("sleepCalculate", "1|23:00-23:59|05:00-06:00");
					deviceSet.put("StepCalculate", "1");
					deviceSet.put("stepCalculate", "1");
					deviceSet.put("HrCalculate", "0");
					deviceSet.put("hrCalculate", "1");
					deviceSet.put("SosMsgswitch", "0");
					deviceSet.put("CreateTime", "1550130107000");
					deviceSet.put("UpdateTime", "1550130107000");
					deviceSet.put("dialPad", "1");
					dataMap.put("DeviceSet", deviceSet);
					
				

					JSONObject deviceState = new JSONObject();
					deviceState.put("Altitude", 0);
					deviceState.put("Course", 0);
					deviceState.put("LocationType", 0);
					deviceState.put("CreateTime", "0");
					deviceState.put("DeviceTime", "0");
					deviceState.put("Electricity", 0);
					deviceState.put("GSM", 0);
					deviceState.put("Step", 0);
					deviceState.put("Health", "0");
					deviceState.put("Latitude", 0);
					deviceState.put("Longitude", 0);
					deviceState.put("Online", 0);
					deviceState.put("SatelliteNumber", 0);
					deviceState.put("ServerTime", "");
					deviceState.put("Speed", 0);
					deviceState.put("UpdateTime", "");
					dataMap.put("DeviceState", deviceState);

					JSONArray jsonArray1 = new JSONArray();
					dataMap.put("ContactArr", jsonArray1);

					jsonArray.add(dataMap);
				}
				bb.put("Code", 1);

			}
		}
		bb.put("deviceList", jsonArray);
		return bb.toString();
	}

	// 解除绑定
	@ResponseBody
	@RequestMapping(value = "/deleteDevice/{token}/{id}", method = RequestMethod.GET)
	public String deleteDevice(@PathVariable String token, @PathVariable Long id) {
		JSONObject bb = new JSONObject();
		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			bb.put("Message", "");
			return bb.toString();
		}
		userInfoService.deleteDeviceBind(id);
		bb.put("Code", 1);
		bb.put("Message", "");
		return bb.toString();
	}

	// 解除绑定通过imei 1.清空设置 2.电子围栏 3.通讯录
	@ResponseBody
	@RequestMapping(value = "/deleteDeviceByImei/{token}/{imei}", method = RequestMethod.GET)
	public String deleteDeviceByImei(@PathVariable String token, @PathVariable String imei) {
		JSONObject bb = new JSONObject();
		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			// bb.put("Message", "");
			return bb.toString();
		}

		// 清空设置信息 device_watch_info
		WatchDevice watch = ideviceService.getDeviceInfo(imei);
		if (watch != null) {
			this.ideviceService.updateWatchImeiInfoById(watch.getId(), "", "", 1, "", "", "", "", "", "");
		}
		ideviceService.updateImeiHeadInfoByImei(watch.getId(), "");

		WatchDeviceHomeSchool watchSchool = ideviceService.getDeviceHomeAndFamilyInfo(Long.valueOf(userId));
		if (watchSchool != null) {
			ideviceService.updateImeiHomeAndFamilyInfoById(watchSchool.getId(), "08:00-12:00", "14:00-17:00", "", "",
					"", "", "", "", "", "");
		}
		
		DeviceManagePhone demp = ideviceService.getManagePhoneByImei(imei);
		if(demp != null){
			ideviceService.updateAdminPhoneById(demp.getId(), "");	
		}

		fenceService.deleteWatchFenceByImei(imei);
		memService.deleteWatchMemberByImei(imei);
		// 删语音 删定位
		locationService.deleteByImei(imei);
		watchtkService.delteByImei(imei);
		// 删闹钟
		ideviceService.deleteDeviceAlarmInfo(imei);
		
		userInfoService.deleteWatchBindByUserId(Long.valueOf(userId));
		//删除绑定设备
		ideviceService.deleteBindDevicebyImei(imei);
		HealthStepManagement  heathM = confService.getHeathStepInfo(imei);
		if(heathM != null){
			confService.deteHeathyInfoByImei(heathM.getId());
		}
		
		UserInfo userInfo = userInfoService.getUserInfoByUsername(imei);
		if (userInfo != null) {
				userInfoService.updateUserPassword(userInfo.getUser_id(), "123456");
		}
		bb.put("Code", 1);
		// bb.put("Message", "解绑成功");
		return bb.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/exit/{token}", method = RequestMethod.GET)
	public String exit(@PathVariable String token) {
		JSONObject bb = new JSONObject();
		bb.put("Code", 1);
		return bb.toString();
	}
	
	//绑定设备
	@ResponseBody
	@RequestMapping(value = "/bindOtherImei", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String bindOtherImei(@RequestBody String body) {
		JSONObject bb = new JSONObject();
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");
		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		String imei = jsonObject.getString("imei");
		String name = jsonObject.getString("name");
		
		
		BindDevice  bindDeviceApp= userInfoService.getWatchBindInfoByUserId(Long.valueOf(userId));
		if(bindDeviceApp != null){
			BindDevice  bindDevice= userInfoService.getWatchBindInfoByImeiAndUserId(imei, Long.valueOf(userId));	
			if(bindDevice != null){
				bb.put("Code", 2);
			}else{
				userInfoService.saveWatchBindInfo(Long.valueOf("1"), bindDeviceApp.getImei()+"", bindDeviceApp.getImei()+"", 1, imei);
				userInfoService.saveWatchBindInfo(Long.valueOf("2"), imei, name, 1, bindDeviceApp.getImei()+"");
				
				bb.put("Code", 1);
			}
		}else{
			String imeii = userInfoService.getUserInfoById(Long.valueOf(userId)).getUsername()+"";
			userInfoService.saveWatchBindInfo(Long.valueOf("1"), imeii, imeii, 1, imeii);
			userInfoService.saveWatchBindInfo(Long.valueOf("2"), imeii, name, 1, imei);
			bb.put("Code", 1);
		}
		//String appImei = userInfoService.getUserInfoById(Long.valueOf(userId)).getImei();
		
		
		return bb.toString();
	}
	
	//获取绑定列表
		@ResponseBody
		@RequestMapping(value = "/getBindList/{token}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
		public String getBindList(@PathVariable String token) {
			JSONObject bb = new JSONObject();
			String userId = checkTokenWatchAndUser(token);
			if ("0".equals(userId)) {
				bb.put("Code", -1);
				// bb.put("Message", "");
				return bb.toString();
			}
			
			BindDevice  bindDeviceApp= userInfoService.getWatchBindInfoByUserId(Long.valueOf(userId));
			String imei =userInfoService.getUserInfoById(Long.valueOf(userId)).getUsername()+"";
			if(bindDeviceApp == null ){
				userInfoService.saveWatchBindInfo(Long.valueOf(userId), imei, imei, 1, imei);
			}
			List<BindDevice> list = userInfoService.getWatchBindInfoByBimei(imei);
			JSONArray jsonArray = new JSONArray();
			if (list != null && !list.isEmpty()) {
				for (BindDevice wlInfo : list) {
					JSONObject dataMap = new JSONObject();
					dataMap.put("imei", wlInfo.getImei());
					dataMap.put("id", wlInfo.getId());
					dataMap.put("name", wlInfo.getName()+"");
					jsonArray.add(dataMap);
				}
			}
			bb.put("Code", 1);
			bb.put("List", jsonArray);
			return bb.toString();
		}
		
		
		//删除绑定设备
		@ResponseBody
		@RequestMapping(value = "/deletebindDeviceById/{token}/{id}", method = RequestMethod.GET)
		public String bindOtherImei(@PathVariable String token, @PathVariable Long id) {
			JSONObject bb = new JSONObject();
			String userId = checkTokenWatchAndUser(token);
			if ("0".equals(userId)) {
				bb.put("Code", -1);
				return bb.toString();
			}
			if(userInfoService.unWatchbindDevice(id)){
				bb.put("Code", 1);
			}else{
				bb.put("Code", 2);
			}
			
			return bb.toString();
		}
		
		
		//修改管理员电话号码
		@ResponseBody
		@RequestMapping(value = "/updateAdminPhone/{token}/{imei}/{phone}", method = RequestMethod.GET)
		public String updateAdminPhone(@PathVariable String token, @PathVariable String imei ,@PathVariable String phone) {
			JSONObject bb = new JSONObject();
			String userId = checkTokenWatchAndUser(token);
			if ("0".equals(userId)) {
				bb.put("Code", -1);
				return bb.toString();
			}
			
			SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);

			if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
				bb.put("Code", 4);
				return bb.toString();
			}

			StringBuffer sb = new StringBuffer("[YW*" + imei + "*0001*");
			if (socketLoginDto.getChannel().isActive()) {
				
				DeviceManagePhone demp = ideviceService.getManagePhoneByImei(imei);
				if(demp != null){
					if(ideviceService.updateAdminPhoneById(demp.getId(), phone)){
						memberService.insertPhoneBookInfo(imei, "管理员", phone, phone, "10", 1);
						bb.put("Code", 1);
					}else{
						bb.put("Code", 2);
					}
				}else{
					memberService.insertPhoneBookInfo(imei, "管理员", phone, phone, "10", 1);
					ideviceService.insertDeviceAdminPhone(imei,phone);
					bb.put("Code", 1);
				}
				
				
				StringBuffer sb1 = new StringBuffer("");
				StringBuffer sb2 = new StringBuffer("");

				List<WatchPhoneBook> watchbookList = memberService.getPhoneBookByImei(imei);

				if (watchbookList.size() > 0) {
					sb1.append(watchbookList.size());
					sb1.append(",");
					for (WatchPhoneBook WatchPhoneBook : watchbookList) {
						if (sb2.toString().isEmpty()) {
							sb2.append(WatchPhoneBook.getHeadtype());
							sb2.append("-");
							sb2.append(WatchPhoneBook.getName());
							sb2.append("-");
							sb2.append(WatchPhoneBook.getPhone());
							sb2.append("-");
							sb2.append(WatchPhoneBook.getCornet());
							sb2.append("-");
							sb2.append(WatchPhoneBook.getHeadtype());
							sb2.append("-");
							sb2.append("0000");
							sb2.append("-");
							sb2.append("");
						} else {
							sb2.append("|");
							sb2.append(WatchPhoneBook.getHeadtype());
							sb2.append("-");
							sb2.append(WatchPhoneBook.getName());
							sb2.append("-");
							sb2.append(WatchPhoneBook.getPhone());
							sb2.append("-");
							sb2.append(WatchPhoneBook.getCornet());
							sb2.append("-");
							sb2.append(WatchPhoneBook.getHeadtype());
							sb2.append("-");
							sb2.append("0000");
							sb2.append("-");
							sb2.append("");
						}
					}
				} else {
					sb1.append("0");
				}
				// PHB,1234, 001B*
				String msg = "PHB,1234," + sb1.toString()+sb2.toString();
				sb.append(RadixUtil.changeRadix(msg));
				sb.append("*");
				sb.append(msg);
				sb.append("]");
				logger.info("设备通讯录增加更新="+sb.toString());
				socketLoginDto.getChannel().writeAndFlush(sb.toString());
				bb.put("Code", 1);
				bb.put("Message", "通讯录添加成功");
				
				
				
				
					JSONObject push = new JSONObject();
					JSONArray jsonArray = new JSONArray();
					JSONObject dataMap = new JSONObject();
					dataMap.put("DeviceID", "");
					String deviceid = limitCache.getRedisKeyValue(imei + "_id");
					if( !StringUtil.isEmpty(deviceid)){
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
					dataMap2.put("Type", 7);
					dataMap2.put("DeviceID", deviceid);
					dataMap2.put("Message", "通讯录已同步");
					dataMap2.put("imei", imei);
					jsonArray2.add(dataMap2);
					push.put("Notification", jsonArray2);

					push.put("Code", 1);
					push.put("New", 1);
					
					pushlogService.insertMsgInfo(imei, 7, deviceid, "通讯录已同步", "通讯录已同步");
					
					AndroidPushUtil.push(token, "通讯录已同步", push.toString(), "通讯录已同步");	
					IOSPushUtil.push(token, "通讯录已同步", push.toString(), "通讯录已同步");	
							
			} else {
				bb.put("Code", 2);
				bb.put("Message", "");
			}
			
			
			
			return bb.toString();
		}
		
		
		
		
		
		
		// 解除绑定通过imei 1.清空设置 2.电子围栏 3.通讯录
		@ResponseBody
		@RequestMapping(value = "/shoudongUnbindByImei/{imei}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
		public String shoudongUnbindByImei(@PathVariable String imei) {
			JSONObject bb = new JSONObject();
			// 清空设置信息 device_watch_info
			WatchDevice watch = ideviceService.getDeviceInfo(imei);
			if (watch != null) {
				this.ideviceService.updateWatchImeiInfoById(watch.getId(), "", "", 1, "", "", "", "", "", "");
			}
			ideviceService.updateImeiHeadInfoByImei(watch.getId(), "");

			WatchDeviceHomeSchool watchSchool = ideviceService.getDeviceHomeAndFamilyByImei(imei);
			if (watchSchool != null) {
				ideviceService.updateImeiHomeAndFamilyInfoById(watchSchool.getId(), "08:00-12:00", "14:00-17:00", "", "",
						"", "", "", "", "", "");
			}

			fenceService.deleteWatchFenceByImei(imei);
			memService.deleteWatchMemberByImei(imei);
			// 删语音 删定位
			locationService.deleteByImei(imei);
			watchtkService.delteByImei(imei);
			// 删闹钟
			ideviceService.deleteDeviceAlarmInfo(imei);
			//删除绑定设备
			ideviceService.deleteBindDevicebyImei(imei);
			HealthStepManagement  heathM = confService.getHeathStepInfo(imei);
			if(heathM != null){
				confService.deteHeathyInfoByImei(heathM.getId());
			}
			
			DeviceManagePhone demp = ideviceService.getManagePhoneByImei(imei);
			if(demp != null){
				ideviceService.updateAdminPhoneById(demp.getId(), "");	
			}
			
			UserInfo userInfo = userInfoService.getUserInfoByUsername(imei);
			if (userInfo != null) {
				     Long userId = userInfo.getUser_id();
			     	userInfoService.deleteWatchBindByUserId(Long.valueOf(userId));
					userInfoService.updateUserPassword(userInfo.getUser_id(), "123456");
			}
			bb.put("Code", 1);
		    bb.put("Message", "手动解绑成功");
			return bb.toString();
		}
		
		
		
		// 切换设备
		@ResponseBody
		@RequestMapping(value = "/switchDevice/{tel}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
		public String switchDevice(@PathVariable String tel) {
			try {
				JSONObject bb = new JSONObject();
				// 先检查登录表，如果登录表里有，检查密码，如果密码正确则登录OK，判断设备是否在线，在线，则发送定位请求，不在线则查询最后一次定位，
				UserInfo userInfo = userInfoService.getUserInfoByUsername(tel);
				String ipport = limitCache.getRedisKeyValue(tel);
				if (userInfo != null) {
						String token = tokenInfoService.genToken(userInfo.getUser_id());
						bb.put("Code", 1);// 1表示login succes
						bb.put("token", token);
						bb.put("LoginId", "0");
						bb.put("UserId", userInfo.getUser_id());
						limitCache.addKey(tel + "_userid", userInfo.getUser_id() + "");
						limitCache.addKey(tel + "_push", token);
						bb.put("PhoneNumber", "0");
						bb.put("BindNumber", tel+"");
						bb.put("Birthday", "");
						bb.put("UserType", 0);
						bb.put("Name", "0");
						bb.put("Notification", "True");
						bb.put("NotificationSound", "True");
						bb.put("NotificationVibration", "True");
						bb.put("ip", Utils.IP + ":" + Utils.PORT_HTTP);

						
						if (!StringUtil.isEmpty(ipport)) {
							bb.put("ip", ipport);
						}
						WatchDevice watchd = ideviceService.getDeviceInfo(tel);
						if (watchd != null) {
							bb.put("DeviceID", watchd.getId());
							limitCache.addKey(tel + "_id", watchd.getId() + "");
						}
						bb.put("phone", "");
						  WatchPhoneBook phoneBook = memberService.getPhoneBookByImeiAndStatus(tel, 1);
							if(phoneBook != null){
								bb.put("phone", "1");
							}
						/* } */

					
				} else {
					
					// if(userInfoLuRu !=null ){//说明已经录入 第一次登录
					userInfoService.saveUserInfo(tel, "123456", 1);// 表示新注册

					String token = tokenInfoService.genToken(userInfoService.getUserInfoByUsername(tel).getUser_id());
					bb.put("Code", 1);// 1表示login succes
					bb.put("token", token);
					bb.put("LoginId", "0");
					bb.put("UserId", "0");
					UserInfo userInfoo = userInfoService.getUserInfoByUsername(tel);
					if (userInfoo != null) {
						bb.put("UserId", userInfoo.getUser_id() + "");
						limitCache.addKey(tel + "_userid", userInfoo.getUser_id() + "");
					}
					limitCache.addKey(tel + "_push", token);

					bb.put("PhoneNumber", "0");
					bb.put("BindNumber", tel+"");
					bb.put("UserType", 0);
					bb.put("Name", "0");
					bb.put("Notification", "True");
					bb.put("NotificationSound", "True");
					bb.put("NotificationVibration", "True");
					bb.put("Birthday", "");
					bb.put("ip", Utils.IP + ":" + Utils.PORT_HTTP);
					if (ipport != null && !"".equals(ipport)) {
						bb.put("ip", ipport);
					}

					/*
					 * String deviceid = limitCache.getRedisKeyValue(tel + "_id");
					 * if (deviceid != null && !"0".equals(deviceid) &&
					 * !"".equals(deviceid)) { bb.put("DeviceID", deviceid); } else
					 * {
					 */
					WatchDevice watchd = ideviceService.getDeviceInfo(tel);
					if (watchd != null) {
						bb.put("DeviceID", watchd.getId());
						bb.put("Birthday", watchd.getBirday() + "");
						limitCache.addKey(tel + "_id", watchd.getId() + "");
						
						bb.put("phone", "");
						  WatchPhoneBook phoneBook = memberService.getPhoneBookByImeiAndStatus(tel, 1);
							if(phoneBook != null){
								bb.put("phone", "1");
							}
					} else {

						ideviceService.insertNewImei(tel, "1", 0, "1");
						bb.put("phone", "");
						WatchDevice watchdd = ideviceService.getDeviceInfo(tel);
						if (watchdd != null) {
							bb.put("DeviceID", watchdd.getId());
							limitCache.addKey(tel + "_id", watchdd.getId() + "");
						}

					}
					/* } */

				}
				logger.info("app登录deviceid=" + limitCache.getRedisKeyValue(tel + "_id"));
				return bb.toString();
			} catch (Exception e) {
				return e.getMessage() + "";
			}
		}
		
		
		// 如果管理员跟IMEI匹配则可以直接修改密码
		@ResponseBody
		@RequestMapping(value = "/udpatePwdByImei", method = RequestMethod.POST)
		public String udpatePwdByImei(@RequestBody String body) {
			JSONObject jsonObject = (JSONObject) JSON.parse(body);
			JSONObject bb = new JSONObject();
		
			String tel = jsonObject.getString("imei");
			String password = jsonObject.getString("pwd");// 默认123456
		
			UserInfo userInfo = userInfoService.getUserInfoByUsername(tel);
			if (userInfo != null) {
					userInfoService.updateUserPassword(userInfo.getUser_id(), password);
					bb.put("Code", 1);
			} else {
				bb.put("Code", 0);
			}
			return bb.toString();
		}
		
		
		
		/*验证用户输入的管理员手机号是否正确*/
		@ResponseBody
		@RequestMapping(value = "/verificationImeiAdmin/{imei}/{phone}", method = RequestMethod.GET)
		public String getSchoolInfo(@PathVariable String phone, @PathVariable String imei) {
			JSONObject bb = new JSONObject();

			WatchPhoneBook phoneBook = memberService.getPhoneBookByImeiAndStatus(imei, 1);
			if(phoneBook != null){
				if(phone.equals(phoneBook.getPhone())){
					bb.put("Code", 1);
				}else{
					bb.put("Code", 2);
				}
			}else{
				bb.put("Code", 3);
			}
			return bb.toString();
		}

}
