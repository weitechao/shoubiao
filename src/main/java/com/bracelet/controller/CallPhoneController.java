package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.HttpBaseDto;
import com.bracelet.dto.LatestBloodOxygenDto;
import com.bracelet.dto.LatestBloodSugarDto;
import com.bracelet.entity.BloodOxygen;
import com.bracelet.entity.BloodSugar;
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
@RequestMapping("/callphone")
public class CallPhoneController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	IConfService confService;

	

	@ResponseBody
	@RequestMapping(value = "/call/{token}/{imei}", method = RequestMethod.GET)
	public String call(@PathVariable String token, @PathVariable String imei) {
		JSONObject bb = new JSONObject();

		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		
		JSONObject bbb = new JSONObject();
		bbb.put("messageID", "0");
		bbb.put("callID", "0");
		
		bb.put("Body", bbb.toString());
		bb.put("Code", 1);
		return bb.toString();
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/callTwo/{token}/{imei}/{messageID}/{callID}", method = RequestMethod.GET)
	public String call(@PathVariable String token, @PathVariable String imei, @PathVariable String messageID, @PathVariable String callID) {
		JSONObject bb = new JSONObject();

		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		JSONObject bbb = new JSONObject();
		bbb.put("messageID", "0");
		bbb.put("callID", "0");
		
		bb.put("Body", bbb.toString());
		
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
		
         String deviceId = jsonObject.getString("deviceId");
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
        	 if(confService.insertTimeSwtich(Long.valueOf(user_id),timeClose,timeOpen,deviceId)){
        		 bb.put("Code", 1);
        	 }else{
        		 bb.put("Code", 0);
        	 }
         }
         
		
		
		return bb.toString();
	}
}
