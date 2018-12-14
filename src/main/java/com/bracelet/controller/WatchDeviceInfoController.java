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
			/*
			 * bb.put("phone", watch.getPhone() + ""); bb.put("updatetime",
			 * watch.getUpdatetime().getTime()); bb.put("dv", watch.getDv() +
			 * ""); bb.put("type", watch.getType() + ""); bb.put("sex",
			 * watch.getSex() + ""); bb.put("birday", watch.getBirday() + "");
			 * bb.put("weight", watch.getWeight() + ""); bb.put("height",
			 * watch.getHeight() + "");
			 */
			bb.put("head", watch.getHead() + "");

			bb.put("Code", 1);
			bb.put("ActiveDate", "");
			bb.put("BabyName", watch.getNickname() + "");
			bb.put("BindNumber", "");
			bb.put("CreateTime", watch.getCreatetime().getTime());
			bb.put("CurrentFirmware", "");
			bb.put("SetVersionNO", 1);
			bb.put("ContactVersionNO", 1);
			bb.put("OperatorType", 1);
			bb.put("SmsNumber", "10086");
			bb.put("SmsBalanceKey", 101);
			bb.put("DeviceID", watch.getId());
			bb.put("UserId", "");
			bb.put("DeviceModelID", "");
			bb.put("Firmware", "");
			bb.put("Gender", 0);
			bb.put("Grade", 0);
			bb.put("HireExpireDate", "");
			bb.put("HireStartDate", "");
			bb.put("IsGuard", "");
			bb.put("Password", "");
			bb.put("PhoneNumber", "");
			bb.put("PhoneCornet", watch.getShort_number() + "," + watch.getFamily_number());
			bb.put("Photo", "");

			bb.put("SchoolAddress", "");
			bb.put("SchoolLat", "");
			bb.put("SchoolLng", "");
			bb.put("SerialNumber", imei);
			bb.put("UpdateTime", "");
			bb.put("LatestTime", "");
			bb.put("HomeAddress", "");
			bb.put("HomeLat", "");
			bb.put("HomeLng", "");

			WatchDeviceHomeSchool whsc = ideviceService.getDeviceHomeAndFamilyInfo(watch.getId());
			if (whsc != null) {
				bb.put("SchoolAddress", whsc.getSchoolAddress());
				bb.put("SchoolLat", whsc.getSchoolLat());
				bb.put("SchoolLng", whsc.getSchoolLng());
				bb.put("UpdateTime", whsc.getUpdatetime().getTime());
				bb.put("LatestTime", whsc.getLatestTime());
				bb.put("HomeAddress", whsc.getHomeAddress());
				bb.put("HomeLat", whsc.getHomeLat());
				bb.put("HomeLng", whsc.getHomeLng());
			}

		} else {

			if (this.ideviceService.insertDeviceImeiInfo(imei, "", "", 1, "", "", "", "", "", "", "")) {
				WatchDevice watchh = ideviceService.getDeviceInfo(imei);
				bb.put("id", watchh.getId());
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
				bb.put("head", watchh.getHead() + "");

				WatchDeviceHomeSchool whsc = ideviceService.getDeviceHomeAndFamilyInfo(watchh.getId());
				if (whsc == null) {
					ideviceService.insertDeviceHomeAndFamilyInfo(watchh.getId(), imei, "", "", "", "", "", "", "", "",
							"", "");
				}
				bb.put("Code", 1);

				bb.put("ActiveDate", "");
				bb.put("BabyName", watchh.getNickname() + "");
				bb.put("BindNumber", "");
				bb.put("CreateTime", watchh.getCreatetime().getTime());
				bb.put("CurrentFirmware", "");
				bb.put("SetVersionNO", 1);
				bb.put("ContactVersionNO", 1);
				bb.put("OperatorType", 1);
				bb.put("SmsNumber", "10086");
				bb.put("SmsBalanceKey", 101);
				bb.put("DeviceID", watchh.getId());
				bb.put("UserId", "");
				bb.put("DeviceModelID", "");
				bb.put("Firmware", "");
				bb.put("Gender", 0);
				bb.put("Grade", 0);
				bb.put("HireExpireDate", "");
				bb.put("HireStartDate", "");
				bb.put("IsGuard", "");
				bb.put("Password", "");
				bb.put("PhoneNumber", "");
				bb.put("PhoneCornet", watchh.getShort_number() + "," + watchh.getFamily_number());
				bb.put("Photo", "");

				bb.put("SchoolAddress", "");
				bb.put("SchoolLat", "");
				bb.put("SchoolLng", "");
				bb.put("SerialNumber", imei);
				bb.put("UpdateTime", "");
				bb.put("LatestTime", "");
				bb.put("HomeAddress", "");
				bb.put("HomeLat", "");
				bb.put("HomeLng", "");

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
		String school_age = jsonObject.getString("school_age");
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
		if (this.ideviceService.updateWatchImeiInfoByImei(imei, phone, nickname, sex, birday, school_age, weight,
				height, familyNumber, shortNumber)) {
			bb.put("Code", 1);
		} else {
			bb.put("Code", 0);
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

		String id = jsonObject.getString("id");
		String head = jsonObject.getString("head");
		logger.info("头像=" + head);

		byte[] headByte = Base64.decodeBase64(head);
		String photoName = id + "_" + new Date().getTime() + ".jpg";
		Utils.createFileContent(Utils.PHOTT_FILE_lINUX, photoName, headByte);

		// if (this.ideviceService.updateImeiHeadInfo(id,
		// Utils.APP_PHOTO_UTL+photoName)) {
		if (this.ideviceService.updateImeiHeadInfoByImei(id, Utils.APP_PHOTO_UTL + photoName)) {
			bb.put("Code", 1);
		} else {
			bb.put("Code", 0);
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

		Long id = jsonObject.getLong("id");
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

		WatchDeviceHomeSchool whsc = ideviceService.getDeviceHomeAndFamilyInfo(id);
		if (whsc != null) {
			if (this.ideviceService.updateImeiHomeAndFamilyInfoById(id, classDisable1, classDisable2, weekDisable,
					schoolAddress, schoolLat, schoolLng, latestTime, homeAddress, homeLat, homeLng)) {
				bb.put("Code", 1);
			} else {
				bb.put("Code", 0);
			}
		} else {
			if (ideviceService.insertDeviceHomeAndFamilyInfo(id, imei, schoolAddress, classDisable1, classDisable2,
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

}
