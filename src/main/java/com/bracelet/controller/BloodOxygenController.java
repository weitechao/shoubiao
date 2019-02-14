package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.HttpBaseDto;
import com.bracelet.dto.LatestBloodOxygenDto;
import com.bracelet.dto.LatestBloodSugarDto;
import com.bracelet.entity.BloodOxygen;
import com.bracelet.entity.BloodSugar;
import com.bracelet.entity.HealthStepManagement;
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
@RequestMapping("/bloodOxygen")
public class BloodOxygenController extends BaseController {
    @Autowired
    IBloodOxygenService bloodOxygenService;
    
    @Autowired
	IConfService confService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @ResponseBody
    @RequestMapping("/search/latest/{token}")
    public HttpBaseDto getLatestBloodOxygen(@PathVariable String token) {
        Long user_id = checkTokenAndUser(token);
        BloodOxygen bloodOxygen= bloodOxygenService.getLatest(user_id);
        LatestBloodOxygenDto latestBloodOxygenDto = null;
        if (bloodOxygen != null) {
        	latestBloodOxygenDto = new LatestBloodOxygenDto();
        	latestBloodOxygenDto.setBloodOxygen(bloodOxygen.getBlood_oxygen());
        	latestBloodOxygenDto.setPulseRate(bloodOxygen.getPulse_rate());
        	latestBloodOxygenDto.setTimestamp(bloodOxygen.getUpload_time().getTime());
        }
        HttpBaseDto dto = new HttpBaseDto();
        dto.setData(latestBloodOxygenDto);
        return dto;
    }
    
    @ResponseBody
	@RequestMapping(value = "/heath", method = RequestMethod.POST)
	public String healthManagement(@RequestBody String body) {
		JSONObject bb = new JSONObject();
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");

		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		
		String imei = jsonObject.getString("imei");
		String sleepCalculate = jsonObject.getString("sleepCalculate");
		String hrCalculate = jsonObject.getString("hrCalculate");
		String stepCalculate = jsonObject.getString("stepCalculate");
		
		/*
		 * ["{\"hrCalculate\":\"0\",
		 * \"stepCalculate\":\"1\",
		 * \"deviceId\":\"872018020142169\",
		 * \"imei\":\"872018020142169\",
		 * \"token\":\"3CE53B80AA1FC347867B478C46E51360\",
		 * \"sleepCalculate\":\"1|22:00-23:59|05:00-06:00\"}"]
		 * */
		
		HealthStepManagement  heathM = confService.getHeathStepInfo(imei);
		if(heathM != null){
			if(confService.updateHeathById(heathM.getId(), stepCalculate, sleepCalculate, hrCalculate )){
				bb.put("Code", 1);
			}else{
				bb.put("Code", 0);
			}
		}else{
			if(confService.insertHeath(imei, stepCalculate, sleepCalculate, hrCalculate )){
				bb.put("Code", 1);
			}else{
				bb.put("Code", 0);
			
			}
		}
		return bb.toString();
	}
    
    
    @ResponseBody
   	@RequestMapping(value = "/getHeath/{token}/{imei}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
   	public String getHealthManagement(@PathVariable String token, @PathVariable String imei) {
   		JSONObject bb = new JSONObject();

   		String user_id = checkTokenWatchAndUser(token);
   		if ("0".equals(user_id)) {
   			bb.put("Code", -1);
   			return bb.toString();
   		}
   		
   		HealthStepManagement  heathM = confService.getHeathStepInfo(imei);
   		if(heathM != null){
   		   bb.put("Code", 1);
   		   bb.put("sleepCalculate", heathM.getSleepCalculate()+"");
   		   bb.put("hrCalculate", heathM.getHrCalculate()+"");
   		   bb.put("stepCalculate", heathM.getStepCalculate()+"");
   		}else{
   		 bb.put("Code", 0);
   		}
   		return bb.toString();
   	}
    
}
