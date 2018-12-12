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
	@RequestMapping(value = "/get/{token}/{imei}", method = RequestMethod.GET ,produces="text/html;charset=UTF-8")
	public String getwatchfence(@PathVariable String token, @PathVariable String imei) {

		JSONObject bb = new JSONObject();

		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}

		WatchDevice watch = ideviceService.getDeviceInfo(imei);

		if (watch != null) {
			// bb.put("id",watch.getId() );
			bb.put("phone", watch.getPhone()+"");
			bb.put("nickname", watch.getNickname()+"");
			bb.put("createtime", watch.getCreatetime().getTime());
			bb.put("updatetime", watch.getUpdatetime().getTime());
			bb.put("dv", watch.getDv()+"");
			bb.put("type", watch.getType()+"");
			bb.put("sex", watch.getSex()+"");
			bb.put("birday", watch.getBirday()+"");
			bb.put("schoolAge", watch.getSchool_age()+"");
			bb.put("schoolInfo", watch.getSchool_info()+"");
			bb.put("homeInfo", watch.getHome_info()+"");
			bb.put("weight", watch.getWeight()+"");
			bb.put("height", watch.getHeight()+"");
			bb.put("head", watch.getHead()+"");
			bb.put("Code", 1);
		} else {
			bb.put("Code", 0);
		}
		return bb.toString();
	}

	/* 修改 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String udpateWatchInfo(@RequestBody String body) {
		JSONObject bb = new JSONObject();
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");

		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}

		String imei = jsonObject.getString("imei");
		String phone = jsonObject.getString("phone");
		String nickname = jsonObject.getString("nickname");
		Integer sex = jsonObject.getInteger("sex");
		String birday = jsonObject.getString("birday");
		String school_age = jsonObject.getString("school_age");
		String school_info = jsonObject.getString("school_info");
		String home_info = jsonObject.getString("home_info");
		String weight = jsonObject.getString("weight");
		String height = jsonObject.getString("height");
		String head = jsonObject.getString("head");

		WatchDevice watch = ideviceService.getDeviceInfo(imei);
		if (watch != null) {
			if (this.ideviceService.updateImeiInfo(watch.getId(), imei, phone, nickname, sex, birday, school_age,
					school_info, home_info, weight, height, head)) {
				bb.put("Code", 1);
			} else {
				bb.put("Code", 0);
			}
		} else {
			if (this.ideviceService.insertDeviceImeiInfo(imei, phone, nickname, sex, birday, school_age, school_info,
					home_info, weight, height, head)) {
				bb.put("Code", 1);
			} else {
				bb.put("Code", 0);
			}
		}

		return bb.toString();
	}

}
