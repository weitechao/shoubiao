package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.HttpBaseDto;
import com.bracelet.dto.LatestBloodOxygenDto;
import com.bracelet.dto.LatestBloodSugarDto;
import com.bracelet.entity.BloodOxygen;
import com.bracelet.entity.BloodSugar;
import com.bracelet.entity.HealthStepManagement;
import com.bracelet.entity.SchoolGuard;
import com.bracelet.entity.TimeSwitch;
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
	public String oldLocation(@RequestBody String body) {
		JSONObject bb = new JSONObject();
//deviceId，stepCalculate，sleepCalculate，hrCalculate 
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");
		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		String deviceId = jsonObject.getString("deviceId");
		String stepCalculate = jsonObject.getString("stepCalculate");
		String sleepCalculate = jsonObject.getString("sleepCalculate");
		String hrCalculate = jsonObject.getString("hrCalculate");
		HealthStepManagement  heathM = confService.getHeathStepInfo(deviceId);
		if(heathM != null){
			if(confService.updateHeathById(heathM.getId(), stepCalculate, sleepCalculate, hrCalculate )){
				bb.put("Code", 1);
			}else{
				bb.put("Code", 0);
			}
		}else{
			if(confService.insertHeath(deviceId, stepCalculate, sleepCalculate, hrCalculate )){
				bb.put("Code", 1);
			}else{
				bb.put("Code", 0);
			
			}
		}
	

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
		bb.put("Code", 1);
		return bb.toString();
	}

	/*
	 * 上学守护开关设置*/
	@ResponseBody
	@RequestMapping(value = "/updateGuardStatus/{token}/{deviceId}/{status}", method = RequestMethod.GET)
	public String updateGuardStatus(@PathVariable String token, @PathVariable String deviceId, @PathVariable Integer status) {
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
			if (confService.insertGuardOffOn(deviceId , status)) {
				bb.put("Code", 1);
			} else {
				bb.put("Code", 0);
			}
		}
		return bb.toString();
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateSleepCalculate/{token}/{deviceId}/{sleepCalculate}", method = RequestMethod.GET)
	public String updatesleepCalculate(@PathVariable String token, @PathVariable String deviceId,
			@PathVariable String sleepCalculate) {
		JSONObject bb = new JSONObject();

		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			bb.put("Message", "");
			return bb.toString();
		}
		
		HealthStepManagement  heathM = confService.getHeathStepInfo(deviceId);
		if(heathM != null){
			if(confService.updateHeathSleepCalculateById(heathM.getId(),  sleepCalculate)){
				bb.put("Code", 1);
			}else{
				bb.put("Code", 0);
			}
		}else{
			if(confService.insertHeath(deviceId, "4000", sleepCalculate, "0" )){
				bb.put("Code", 1);
			}else{
				bb.put("Code", 0);
			
			}
		}
		
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
		
         String imei = jsonObject.getString("imei");
         String timeClose = jsonObject.getString("timeClose");
         String timeOpen  = jsonObject.getString("timeOpen");
         
         TimeSwitch time = confService.getTimeSwitch(Long.valueOf(user_id));
         if(time != null ){
            if(confService.updateTimeSwitchById(time.getId(), timeClose, timeOpen)){
            	bb.put("Code", 1);
            }else{
            	bb.put("Code", 0);
            }
         }else{
        	 if(confService.insertTimeSwtich(Long.valueOf(user_id),timeClose,timeOpen)){
        		 bb.put("Code", 1);
        	 }else{
        		 bb.put("Code", 0);
        	 }
         }
         
		
		
		return bb.toString();
	}
}
