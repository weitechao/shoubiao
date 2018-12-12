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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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
			bb.put("phone", watch.getPhone() + "");
			bb.put("nickname", watch.getNickname() + "");
			bb.put("createtime", watch.getCreatetime().getTime());
			bb.put("updatetime", watch.getUpdatetime().getTime());
			bb.put("dv", watch.getDv() + "");
			bb.put("type", watch.getType() + "");
			bb.put("sex", watch.getSex() + "");
			bb.put("birday", watch.getBirday() + "");
			bb.put("schoolAge", watch.getSchool_age() + "");
			bb.put("schoolInfo", watch.getSchool_info() + "");
			bb.put("homeInfo", watch.getHome_info() + "");
			bb.put("weight", watch.getWeight() + "");
			bb.put("height", watch.getHeight() + "");
			bb.put("head", watch.getHead() + "");
			bb.put("Code", 1);
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
				bb.put("Code", 1);
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

		Long id = jsonObject.getLong("id");
		String imei = jsonObject.getString("imei");
		String phone = jsonObject.getString("phone");
		String nickname = jsonObject.getString("nickname");
		Integer sex = jsonObject.getInteger("sex");
		String birday = jsonObject.getString("birday");
		String school_age = jsonObject.getString("school_age");
		String weight = jsonObject.getString("weight");
		String height = jsonObject.getString("height");
		String head = jsonObject.getString("head");

		if (this.ideviceService.updateImeiNotHomeAndFamilyInfo(id, imei, phone, nickname, sex, birday, school_age, weight, height, head)) {
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

		Long id = jsonObject.getLong("id");
		String head = jsonObject.getString("head");

		if (this.ideviceService.updateImeiHeadInfo(id, head)) {
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

		String school_info = jsonObject.getString("school_info");
		String home_info = jsonObject.getString("home_info");

		if (this.ideviceService.updateImeiHomeAndFamilyInfo(id, school_info, home_info)) {
			bb.put("Code", 1);
		} else {
			bb.put("Code", 0);
		}

		return bb.toString();
	}

}
