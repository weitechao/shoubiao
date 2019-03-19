package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.bracelet.entity.NotifyInfo;
import com.bracelet.entity.WatchDevice;
import com.bracelet.service.IConfService;
import com.bracelet.service.IDeviceService;
import com.bracelet.util.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/notify")
public class NotifyController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	IConfService confService;
	
	
	@Autowired
	IDeviceService ideviceService;

	@ResponseBody
	@RequestMapping(value = "/updateUserNotify", method = RequestMethod.POST)
	public String updateUserNotify(@RequestBody String body) {
		JSONObject bb = new JSONObject();
		// loginId，notification，notificationSound，notificationVibration
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");
		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		String deviceId = jsonObject.getString("deviceId");
		String notification = jsonObject.getString("notification");
		String notificationSound = jsonObject.getString("notificationSound");
		String notificationVibration = jsonObject.getString("notificationVibration");
		NotifyInfo nof = confService.getNotiFyInfo(deviceId);

		if (nof != null) {
			if (confService.updateNotifyById(nof.getId(), notification, notificationSound, notificationVibration)) {
				bb.put("Code", 1);
			} else {
				bb.put("Code", 0);
			}

		} else {
			if (confService.insertNotify(deviceId, notification, notificationSound, notificationVibration)) {
				bb.put("Code", 1);
			} else {
				bb.put("Code", 0);
			}
		}

		return bb.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/notifyList/{token}", method = RequestMethod.GET)
	public String notifyList(@PathVariable String token) {
		JSONObject bb = new JSONObject();
         String imei = "872018020142169";
		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}

		JSONArray jsonArray = new JSONArray();
		JSONObject dataMap = new JSONObject();
		dataMap.put("DeviceID", "");
		
		String deviceid = limitCache.getRedisKeyValue(imei + "_id");
		if(deviceid !=null && !"0".equals(deviceid) && !"".equals(deviceid)){
			dataMap.put("DeviceID", deviceid);
		}else{
			WatchDevice watchd = ideviceService.getDeviceInfo(imei);
			if (watchd != null) {
				dataMap.put("DeviceID", watchd.getId());
				limitCache.addKey(imei + "_id", watchd.getId()+"");
			}
		}
		
		
		dataMap.put("Message", 0);
		dataMap.put("Voice", 0);
		dataMap.put("SMS", 0);
		dataMap.put("Photo", 0);
		jsonArray.add(dataMap);
		bb.put("NewList", jsonArray);

		JSONArray jsonArray1 = new JSONArray();
		JSONObject dataMap1 = new JSONObject();
		dataMap1.put("DeviceID", limitCache.getRedisKeyValue(imei + "_id"));
		dataMap1.put("Altitude", 0);
		dataMap1.put("Course", 0);
		dataMap1.put("LocationType", 2);
		dataMap1.put("wifi", "");
		dataMap1.put("CreateTime", Utils.getLocationTime(System.currentTimeMillis()));
		dataMap1.put("DeviceTime", "");
		dataMap1.put("Electricity", "100");
		dataMap1.put("GSM", 76);
		dataMap1.put("Step", 0);
		dataMap1.put("Health", "0:0");
		dataMap1.put("Latitude", "");
		dataMap1.put("Longitude", "");
		dataMap1.put("Online", "0");
		dataMap1.put("SatelliteNumber", "0");
		dataMap1.put("ServerTime", "");
		dataMap1.put("Speed", "0");
		dataMap1.put("UpdateTime", "0");
		jsonArray1.add(dataMap1);
		bb.put("DeviceState", jsonArray1);

		JSONArray jsonArray2 = new JSONArray();
		bb.put("Notification", jsonArray2);

		bb.put("Code", 1);
		bb.put("New", 0);
		return bb.toString();
	}

}
