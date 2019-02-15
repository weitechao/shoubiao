package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.HttpBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.BindDevice;
import com.bracelet.entity.Location;
import com.bracelet.entity.LocationOld;
import com.bracelet.entity.LocationWatch;
import com.bracelet.entity.NoticeInfo;
import com.bracelet.entity.UserInfo;
import com.bracelet.entity.VersionInfo;
import com.bracelet.entity.WatchAppVersionInfo;
import com.bracelet.entity.WatchDevice;
import com.bracelet.entity.WatchDeviceAlarm;
import com.bracelet.entity.WatchDeviceHomeSchool;
import com.bracelet.entity.WatchDialpad;
import com.bracelet.exception.BizException;
import com.bracelet.redis.LimitCache;
import com.bracelet.service.IAuthcodeService;
import com.bracelet.service.IDeviceService;
import com.bracelet.service.IFenceService;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IMemService;
import com.bracelet.service.IOpenDoorService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.service.IVoltageService;
import com.bracelet.service.WatchSetService;
import com.bracelet.service.WatchTkService;
import com.bracelet.util.ChannelMap;
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
					bb.put("BindNumber", "0");
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

					/*
					 * SocketLoginDto socketLoginDto =
					 * ChannelMap.getChannel(tel); if (socketLoginDto == null ||
					 * socketLoginDto.getChannel() == null) { WatchDevice watchd
					 * = ideviceService.getDeviceInfo(tel); bb.put("DeviceID",
					 * watchd.getId()); } else { bb.put("DeviceID",
					 * socketLoginDto.getUser_id()); }
					 */
					/*
					 * String deviceid = limitCache.getRedisKeyValue(tel +
					 * "_id"); if (deviceid != null && !"0".equals(deviceid) &&
					 * !"".equals(deviceid)) { bb.put("DeviceID", deviceid); }
					 * else {
					 */
					WatchDevice watchd = ideviceService.getDeviceInfo(tel);
					if (watchd != null) {
						bb.put("DeviceID", watchd.getId());
						limitCache.addKey(tel + "_id", watchd.getId() + "");
					}
					/* } */

				} else {
					bb.put("Code", 2);// 2表示密码错误
					bb.put("Message", "");// 2表示密码错误
				}
			} else {
				// UserInfo userInfoLuRu =
				// userInfoService.getUserInfoLuRuByUsername(tel);//这里查询录入表
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
				bb.put("BindNumber", "0");
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
				} else {

					ideviceService.insertNewImei(tel, "1", 0, "1");
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

				deviceSet.put("TimerOpen", "0");
				deviceSet.put("TimerClose", "0");
				deviceSet.put("BrightScreen", "0");

				deviceSet.put("LocationMode", 0);
				deviceSet.put("LocationTime", "0");
				deviceSet.put("FlowerNumber", 0);
				deviceSet.put("SleepCalculate", "0");
				deviceSet.put("StepCalculate", "0");
				deviceSet.put("HrCalculate", "0");
				deviceSet.put("SosMsgswitch", "0");
				deviceSet.put("CreateTime", "0");
				deviceSet.put("UpdateTime", "0");
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

					deviceSet.put("TimerOpen", "0");
					deviceSet.put("TimerClose", "0");
					deviceSet.put("BrightScreen", "0");

					deviceSet.put("LocationMode", 0);
					deviceSet.put("LocationTime", "0");
					deviceSet.put("FlowerNumber", 0);
					deviceSet.put("SleepCalculate", "0");
					deviceSet.put("StepCalculate", "0");
					deviceSet.put("HrCalculate", "0");
					deviceSet.put("SosMsgswitch", "0");
					deviceSet.put("CreateTime", "0");
					deviceSet.put("UpdateTime", "0");
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

		WatchDeviceHomeSchool watchSchool = ideviceService.getDeviceHomeAndFamilyInfo(imei);
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

}
