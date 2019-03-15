package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.HttpBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.Fence;
import com.bracelet.entity.Fencelog;
import com.bracelet.entity.OddShape;
import com.bracelet.entity.SensitivePoint;
import com.bracelet.entity.SensitivePointLog;
import com.bracelet.entity.WatchDevice;
import com.bracelet.entity.WatchDeviceAlarm;
import com.bracelet.entity.WatchDeviceHomeSchool;
import com.bracelet.entity.WatchPhoneBook;
import com.bracelet.exception.BizException;
import com.bracelet.service.IDeviceService;
import com.bracelet.service.IFenceService;
import com.bracelet.service.IFencelogService;
import com.bracelet.service.ISensitivePointService;
import com.bracelet.service.ISensitivePointlogService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.service.IWatchDeviceService;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.RadixUtil;
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

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/watchinfo")
public class WatchDeviceInfoController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	IDeviceService ideviceService;

	/* 获取 */
	@ResponseBody
	@RequestMapping(value = "/get/{token}/{imei}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String getBabyInfo(@PathVariable String token, @PathVariable String imei) {

		JSONObject bb = new JSONObject();

		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}

		WatchDevice watch = ideviceService.getDeviceInfo(imei);

		if (watch != null) {
			bb.put("id", watch.getId());
			
			//bb.put("head", "");
			bb.put("Birthday", watch.getBirday()+"");
			
			bb.put("Code", 1);
			bb.put("ActiveDate", "");
			bb.put("BabyName", watch.getNickname() + "");
			bb.put("BindNumber", imei);
			bb.put("CreateTime", watch.getCreatetime().getTime());
			bb.put("CurrentFirmware", "Y01_K2_RDA6625_RENQI_LE_DIAN_LINUX.0.967.QGJ_V1.0");
			bb.put("SetVersionNO", 1);
			bb.put("ContactVersionNO", 1);
			bb.put("OperatorType", 1);
			bb.put("SmsNumber", "10086");
			bb.put("SmsBalanceKey", 101);
			bb.put("DeviceID", watch.getId());
			bb.put("UserId", userId);
			bb.put("DeviceModelID", "10000100");
			bb.put("Firmware", "");
			bb.put("HireExpireDate", "");
			bb.put("HireStartDate", "");
			bb.put("IsGuard", "");
			bb.put("Password", "");
			bb.put("Gender", watch.getSex()+"");
			bb.put("Grade", 0);
			
			if(!StringUtils.isAllEmpty(watch.getSchool_age())){
				bb.put("Grade", watch.getSchool_age());
			}
			
			
			bb.put("PhoneNumber", watch.getPhone()+"");
			bb.put("PhoneCornet", watch.getShort_number()+"");
			bb.put("Photo", watch.getHead() + "");
			bb.put("SmsFlowKey", "0");
			bb.put("SerialNumber", imei);

			bb.put("SchoolAddress", "");
			bb.put("SchoolLat", "0");
			bb.put("SchoolLng", "0");
			bb.put("UpdateTime", "");
			bb.put("LatestTime", "");
			bb.put("HomeAddress", "");
			bb.put("HomeLat", "0");
			bb.put("HomeLng", "0");
			
			/*shortNumber，PhoneCornet）,( phone, PhoneNumber) ,( schoolAge, Grade)，(sex, Gender)*/

			WatchDeviceHomeSchool whsc = ideviceService.getDeviceHomeAndFamilyInfo(Long.valueOf(userId));
			if (whsc != null) {
				bb.put("SchoolAddress", whsc.getSchoolAddress()+"");
				
				bb.put("UpdateTime", whsc.getUpdatetime().getTime()+"");
				bb.put("LatestTime", whsc.getLatestTime()+"");
				bb.put("HomeAddress", whsc.getHomeAddress()+"");
				
				if(StringUtil.isEmpty(whsc.getSchoolLat())){
					bb.put("SchoolLat","0");
				}else{
					bb.put("SchoolLat", whsc.getSchoolLat()+"");
				}
				if(StringUtil.isEmpty(whsc.getSchoolLng())){
					bb.put("SchoolLng","0");
				}else{
					bb.put("SchoolLng", whsc.getSchoolLng()+"");
				}
				if(StringUtil.isEmpty(whsc.getHomeLat())){
					bb.put("HomeLat","0");
				}else{
					bb.put("HomeLat",  whsc.getHomeLat()+"");
				}
				if(StringUtil.isEmpty(whsc.getHomeLng())){
					bb.put("HomeLng","0");
				}else{
					bb.put("HomeLng",  whsc.getHomeLng()+"");
				}
			}

		} else {

			if (this.ideviceService.insertDeviceImeiInfo(imei, "", "", 1, "", "", "", "", "", "", "")) {
				WatchDevice watchh = ideviceService.getDeviceInfo(imei);
				bb.put("id", watchh.getId());
				bb.put("Birthday", watchh.getBirday()+"");
				bb.put("phone", watchh.getPhone() + "");
				bb.put("nickname", watchh.getNickname() + "");
				bb.put("createtime", watchh.getCreatetime().getTime());
				bb.put("updatetime", watchh.getUpdatetime().getTime());
				bb.put("dv", watchh.getDv() + "");
				bb.put("type", watchh.getType() + "");
				bb.put("sex", watchh.getSex() + "");
				bb.put("birday", watchh.getBirday() + "");
				bb.put("schoolAge", watchh.getSchool_age() + "");
				bb.put("schoolInfo", watchh.getSchool_info() + "");
				bb.put("homeInfo", watchh.getHome_info() + "");
				bb.put("weight", watchh.getWeight() + "");
				bb.put("height", watchh.getHeight() + "");
				//bb.put("head", watchh.getHead() + "");
			
				
				bb.put("SchoolAddress", "");
				bb.put("SchoolLat", "0");
				bb.put("SchoolLng", "0");
				bb.put("UpdateTime", "");
				bb.put("LatestTime", "");
				bb.put("HomeAddress", "");
				bb.put("HomeLat", "0");
				bb.put("HomeLng", "0");
				bb.put("HomeAddress", watchh.getHome_info()+"");
				
				WatchDeviceHomeSchool whsc = ideviceService.getDeviceHomeAndFamilyInfo(Long.valueOf(userId));
				if (whsc == null) {
					ideviceService.insertDeviceHomeAndFamilyInfo(Long.valueOf(userId), imei, "", "08:00-12:00", "14:00-17:00", "", "0", "0", "", "",
							"0", "0");
				}else{
					bb.put("SchoolAddress", whsc.getSchoolAddress()+"");
					
					bb.put("UpdateTime", whsc.getUpdatetime().getTime());
					bb.put("LatestTime", "");
				
					bb.put("HomeAddress",whsc.getHomeAddress()+"");
					
					bb.put("SchoolLat", whsc.getSchoolLat()+"");
					bb.put("SchoolLng", whsc.getSchoolLng()+"");
					bb.put("HomeLat", whsc.getHomeLat()+"");
					bb.put("HomeLng", whsc.getHomeLng()+"");
					
					
					if(StringUtil.isEmpty(whsc.getSchoolLat())){
						bb.put("SchoolLat","0");
					}else{
						bb.put("SchoolLat", whsc.getSchoolLat()+"");
					}
					if(StringUtil.isEmpty(whsc.getSchoolLng())){
						bb.put("SchoolLng","0");
					}else{
						bb.put("SchoolLng", whsc.getSchoolLng()+"");
					}
					if(StringUtil.isEmpty(whsc.getHomeLat())){
						bb.put("HomeLat","0");
					}else{
						bb.put("HomeLat",  whsc.getHomeLat()+"");
					}
					if(StringUtil.isEmpty(whsc.getHomeLng())){
						bb.put("HomeLng","0");
					}else{
						bb.put("HomeLng",  whsc.getHomeLng()+"");
					}
					
				}
				bb.put("Code", 1);

				bb.put("ActiveDate", "");
				bb.put("BabyName", watchh.getNickname() + "");
				bb.put("BindNumber", imei);
				bb.put("CreateTime", watchh.getCreatetime().getTime());
				bb.put("CurrentFirmware", "Y01_K2_RDA6625_RENQI_LE_DIAN_LINUX.0.967.QGJ_V1.0");
				bb.put("SetVersionNO", 1);
				bb.put("ContactVersionNO", 1);
				bb.put("OperatorType", 1);
				bb.put("SmsNumber", "10086");
				bb.put("SmsBalanceKey", 101);
				bb.put("DeviceID", watchh.getId());
				bb.put("UserId", userId);
				bb.put("DeviceModelID", "10000100");
				bb.put("Firmware", "");
				bb.put("Gender", 0);
				bb.put("Grade", 0);
				bb.put("HireExpireDate", "");
				bb.put("HireStartDate", "");
				bb.put("IsGuard", "");
				bb.put("Password", "");
				bb.put("PhoneNumber", "");
				
				bb.put("Gender", watchh.getSex()+"");
				
				if(!StringUtils.isAllEmpty(watchh.getSchool_age())){
					bb.put("Grade", watchh.getSchool_age());
				}
				
				bb.put("PhoneNumber", watchh.getPhone()+"");
				bb.put("PhoneCornet", watchh.getShort_number()+"");
				bb.put("Photo", watchh.getHead() + "");

				bb.put("SerialNumber", imei);
				bb.put("SmsFlowKey", "0");

			} else {
				bb.put("Code", 0);
			}
		}
		return bb.toString();
	}

	/* 修改全部 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateBabyInfo(@RequestBody String body) {
		JSONObject bb = new JSONObject();
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");

		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}

		// Long id = jsonObject.getLong("id");
		String imei = jsonObject.getString("imei");
		String phone = jsonObject.getString("phone");
		String nickname = jsonObject.getString("nickname");
		Integer sex = jsonObject.getInteger("sex");
		String birday = jsonObject.getString("birday");
		String school_age = jsonObject.getString("schoolAge");
		String weight = jsonObject.getString("weight");
		String height = jsonObject.getString("height");
		/*
		 * String head = jsonObject.getString("head");
		 * 
		 * 
		 * byte[] headByte= Base64.decodeBase64(head); String photoName =
		 * Utils.APP_PHOTO_UTL+id+"_"+new Date().getTime() + ".jpg";
		 * Utils.createFileContent(Utils.PHOTT_FILE_lINUX, photoName, headByte);
		 */

		String familyNumber = jsonObject.getString("familyNumber");
		String shortNumber = jsonObject.getString("shortNumber");

		// if (this.ideviceService.updateImeiNotHomeAndFamilyInfo(id, imei,
		// phone, nickname, sex, birday, school_age, weight, height, head)) {

		WatchDevice watch = ideviceService.getDeviceInfo(imei);
		if (watch != null) {
			if (this.ideviceService.updateWatchImeiInfoById(watch.getId(), phone, nickname, sex, birday, school_age,
					weight, height, familyNumber, shortNumber)) {
				bb.put("Code", 1);
			} else {
				bb.put("Code", 0);
			}
		} else {
			ideviceService.insertDeviceImeiInfo(imei, phone, nickname, sex, birday, school_age, "", "", weight, height,
					"");
			bb.put("Code", 1);
		}
		return bb.toString();
	}

	/* 修改头像 */
	@ResponseBody
	@RequestMapping(value = "/updateHead", method = RequestMethod.POST)
	public String updateBabyHead(@RequestBody String body) {
		JSONObject bb = new JSONObject();
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");

		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}

		String imei = jsonObject.getString("id");
		String head = jsonObject.getString("head");
		logger.info("头像=" + head);

		byte[] headByte = Base64.decodeBase64(head);
		String photoName = imei + "_" + System.currentTimeMillis() + ".jpg";
		Utils.createFileContent(Utils.PHOTT_FILE_lINUX, photoName, headByte);

		// if (this.ideviceService.updateImeiHeadInfo(id,
		// Utils.APP_PHOTO_UTL+photoName)) {

		WatchDevice watch = ideviceService.getDeviceInfo(imei);
		if (watch != null) {

			if (this.ideviceService.updateImeiHeadInfoByImei(watch.getId(), Utils.APP_PHOTO_UTL + photoName)) {
				bb.put("Code", 1);
			} else {
				bb.put("Code", 0);
			}
		} else {
			ideviceService.insertDeviceImeiInfo(imei, "", "", 1, "", "", "", "", "", "", head);
			bb.put("Code", 1);

		}
		return bb.toString();
	}

	/* 修改家庭和family */
	@ResponseBody
	@RequestMapping(value = "/updateHomeAndFamily", method = RequestMethod.POST)
	public String updateBabyhomeAndFamilyInfo(@RequestBody String body) {
		JSONObject bb = new JSONObject();
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");

		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}

		//String id = jsonObject.getString("id");
		String imei = jsonObject.getString("imei");

		/*
		 * 学校信息参数： 时间 classDisable1， classDisable2， weekDisable 位置
		 * schoolAddress， schoolLat， schoolLng家庭信息参数：时间 latestTime位置
		 * homeAddress， homeLat， homeLng
		 */
		String classDisable1 = jsonObject.getString("classDisable1");
		String classDisable2 = jsonObject.getString("classDisable2");
		String weekDisable = jsonObject.getString("weekDisable");
		String schoolAddress = jsonObject.getString("schoolAddress");
		String schoolLat = jsonObject.getString("schoolLat");
		String schoolLng = jsonObject.getString("schoolLng");

		String latestTime = jsonObject.getString("latestTime");
		String homeAddress = jsonObject.getString("homeAddress");
		String homeLat = jsonObject.getString("homeLat");
		String homeLng = jsonObject.getString("homeLng");

		WatchDeviceHomeSchool whsc = ideviceService.getDeviceHomeAndFamilyInfo(Long.valueOf(userId));
		if (whsc != null) {
			if (this.ideviceService.updateImeiHomeAndFamilyInfoById(whsc.getId(), classDisable1, classDisable2, weekDisable,
					schoolAddress, schoolLat, schoolLng, latestTime, homeAddress, homeLat, homeLng)) {
				bb.put("Code", 1);
			} else {
				bb.put("Code", 0);
			}
		} else {
			if (ideviceService.insertDeviceHomeAndFamilyInfo(Long.valueOf(userId), imei, schoolAddress, classDisable1, classDisable2,
					weekDisable, schoolLat, schoolLng, latestTime, homeAddress, homeLng, homeLat)) {
				bb.put("Code", 1);
			} else {
				bb.put("Code", 0);
			}
		}

		return bb.toString();
	}

	/* 修改短号和亲情号 */
	@ResponseBody
	@RequestMapping(value = "/updateCornet", method = RequestMethod.POST)
	public String updateBabyCornet(@RequestBody String body) {
		JSONObject bb = new JSONObject();
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");

		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}

		Long id = jsonObject.getLong("id");
		String familyNumber = jsonObject.getString("familyNumber");
		String shortNumber = jsonObject.getString("shortNumber");

		if (this.ideviceService.updateImeiNumberById(id, familyNumber, shortNumber)) {
			bb.put("Code", 1);
		} else {
			bb.put("Code", 0);
		}
		return bb.toString();
	}

	/* 修改闹钟 */
	@ResponseBody
	@RequestMapping(value = "/updateAlarm", method = RequestMethod.POST)
	public String updateAlarm(@RequestBody String body) {
		JSONObject bb = new JSONObject();
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");

		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}

		String imei = jsonObject.getString("imei");
		String weekAlarm1 = jsonObject.getString("weekAlarm1");
		String weekAlarm2 = jsonObject.getString("weekAlarm2");
		String weekAlarm3 = jsonObject.getString("weekAlarm3");
		String alarm1 = jsonObject.getString("alarm1");
		String alarm2 = jsonObject.getString("alarm2");
		String alarm3 = jsonObject.getString("alarm3");

		WatchDeviceAlarm watch = ideviceService.getDeviceAlarmInfo(imei);
		if (watch != null) {
			if (this.ideviceService.updateWatchAlarmInfoById(watch.getId(), weekAlarm1, weekAlarm2, weekAlarm3, alarm1,
					alarm2, alarm3)) {
				bb.put("Code", 1);
			} else {
				bb.put("Code", 0);
			}
		} else {
			ideviceService.insertDeviceAlarmInfo(imei, weekAlarm1, weekAlarm2, weekAlarm3, alarm1, alarm2, alarm3);
			bb.put("Code", 1);
		}
		return bb.toString();
	}

}
