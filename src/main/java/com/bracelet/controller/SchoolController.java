package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.HttpBaseDto;
import com.bracelet.dto.LatestBloodOxygenDto;
import com.bracelet.dto.LatestBloodSugarDto;
import com.bracelet.entity.BloodOxygen;
import com.bracelet.entity.BloodSugar;
import com.bracelet.entity.SchoolGuard;
import com.bracelet.entity.VersionInfo;
import com.bracelet.service.IBloodOxygenService;
import com.bracelet.service.IBloodSugarService;
import com.bracelet.service.IConfService;
import com.bracelet.util.HttpClientGet;

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
@RequestMapping("/school")
public class SchoolController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	IConfService confService;

	@ResponseBody
	@RequestMapping(value = "/heath", method = RequestMethod.POST)
	public String oldLocation(@RequestBody String json) {
		JSONObject bb = new JSONObject();

		bb.put("Code", 1);

		return bb.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/getSchoolInfo/{token}/{imei}", method = RequestMethod.GET)
	public String getSchoolInfo(@PathVariable String token, @PathVariable String imei) {
		JSONObject bb = new JSONObject();

		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		bb.put("SchoolID", "");
		bb.put("SchoolDay", "");
		bb.put("SchoolArriveContent", "");
		bb.put("SchoolArriveTime", "");
		bb.put("SchoolLeaveContent", "");
		bb.put("SchoolLeaveTime", "");
		bb.put("RoadStayContent", "");
		bb.put("RoadStayTime", "");
		bb.put("HomeBackContent", "");
		bb.put("HomeBackTime", "");
		return bb.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/updateGuardStatus/{token}/{deviceId}/{status}", method = RequestMethod.GET)
	public String updateGuardStatus(@PathVariable String token, @PathVariable Long deviceId,
			@PathVariable Integer status) {
		JSONObject bb = new JSONObject();

		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		SchoolGuard schoolguard = confService.getSchoolGuard(deviceId);
		if (schoolguard != null) {
			if (confService.updateSchoolGrardOffOnById(schoolguard.getId(), status)) {
				bb.put("Code", 1);
			} else {
				bb.put("Code", 0);
			}
		} else {
			if (confService.insertGuardOffOn(deviceId, status)) {
				bb.put("Code", 1);
			} else {
				bb.put("Code", 0);
			}
		}
		return bb.toString();
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateSleepCalculate/{token}/{deviceId}/{status}", method = RequestMethod.GET)
	public String updatesleepCalculate(@PathVariable String token, @PathVariable Long deviceId,
			@PathVariable Integer status) {
		JSONObject bb = new JSONObject();

		bb.put("Code", 1);
		
		return bb.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/timeSwitch", method = RequestMethod.POST)
	public String timeSwitch(@RequestBody String body) {
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		JSONObject bb = new JSONObject();
		String token = jsonObject.getString("token");
		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		
         Long deviceId = jsonObject.getLong("deviceId");
         
		bb.put("Code", 1);
		
		return bb.toString();
	}
}
