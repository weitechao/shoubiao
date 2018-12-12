package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.HttpBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.BindDevice;
import com.bracelet.entity.Location;
import com.bracelet.entity.LocationOld;
import com.bracelet.entity.LocationRequest;
import com.bracelet.entity.OldBindDevice;
import com.bracelet.entity.Step;
import com.bracelet.entity.UserInfo;
import com.bracelet.entity.VersionInfo;
import com.bracelet.entity.WatchDeviceSet;
import com.bracelet.exception.BizException;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IStepService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.service.WatchSetService;
import com.bracelet.socket.BaseChannelHandler;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.HttpClientGet;
import com.bracelet.util.RadixUtil;
import com.bracelet.util.RanomUtil;
import com.bracelet.util.RespCode;
import com.bracelet.util.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/watchset")
public class WatchAppSetController extends BaseController {

	@Autowired
	ILocationService locationService;
	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	IStepService stepService;
	@Autowired
	WatchSetService watchSetService;
	@Resource
	BaseChannelHandler baseChannelHandler;
	private Logger logger = LoggerFactory.getLogger(getClass());

	/* 跟踪模式 */
	@ResponseBody
	@RequestMapping(value = "/tracker/{token}/{imei}", method = RequestMethod.GET)
	public String tracker(@PathVariable String token, @PathVariable String imei) {
		JSONObject bb = new JSONObject();
		
		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("Code", 4);
			return bb.toString();
		}
		if (socketLoginDto.getChannel().isActive()) {
			String reps = "[YW*" + imei + "*0001*0007*TRACKER]";
			socketLoginDto.getChannel().writeAndFlush(reps);
			bb.put("Code", 1);
		} else {
			bb.put("Code", 0);
		}
		return bb.toString();
	}

	/* 发送监控指令 */
	@ResponseBody
	@RequestMapping(value = "/guard/{token}/{imei}/{type}", method = RequestMethod.GET)
	public String guard(@PathVariable String token, @PathVariable String imei,
			@PathVariable Integer type) {
		JSONObject bb = new JSONObject();
		
		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("Code", 4);
			return bb.toString();
		}
		String reps = "[YW*" + imei + "*0001*0007*GUARD," + type + "]";
		if (socketLoginDto.getChannel().isActive()) {
			socketLoginDto.getChannel().writeAndFlush(reps);
			bb.put("Code", 1);
		} else {
			bb.put("Code", 0);
		}
		return bb.toString();
	}

	/*设置全部参数指令*/
	@ResponseBody
	@RequestMapping(value = "/set", method = RequestMethod.POST)
	public String setParameter(@RequestBody String body) {
		JSONObject bb = new JSONObject();
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");
		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			bb.put("Message", "");
			return bb.toString();
		}
		String imei = jsonObject.getString("imei");
		String data = jsonObject.getString("data");
		
		WatchDeviceSet deviceSet = watchSetService.getDeviceSetByImei(imei);
		if(deviceSet!=null){
			watchSetService.updateWatchSet(deviceSet.getId(),data);
		}else{
			watchSetService.insertWatchDeviceSet(imei,data);
		}
		
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("Code", 2);
			bb.put("Message", "");
			return bb.toString();
		}
		if (socketLoginDto.getChannel().isActive()) {
			String msg="SET,"+data;
			String reps = "[YW*"+imei+"*0001*"+RadixUtil.changeRadix(msg)+"*"+msg+"]";
			socketLoginDto.getChannel().writeAndFlush(reps);
			bb.put("Code", 1);
			bb.put("Message", "");
			
		} else {
			bb.put("Code", 0);
			bb.put("Message", "");
		}
		return bb.toString();
	}
	
	
	// 获取设备设置全部参数
		@ResponseBody
		@RequestMapping(value = "/getDeviceSet/{token}/{imei}", method = RequestMethod.GET,produces="text/html;charset=UTF-8")
		public String getDeviceSet(@PathVariable String token,@PathVariable String imei) {
			JSONObject bb = new JSONObject();
			String userId = checkTokenWatchAndUser(token);
			if ("0".equals(userId)) {
				bb.put("Code", -1);
				return bb.toString();
			}
			WatchDeviceSet deviceSet = watchSetService.getDeviceSetByImei(imei);
			if(deviceSet !=null){
				bb.put("data", deviceSet.getData());
				bb.put("Code", 1);
				
				
				bb.put("ActiveDate", "");
				bb.put("BabyName", "");
				bb.put("BindNumber", "");
				bb.put("Birthday", "");
				bb.put("CreateTime", "");
				bb.put("CurrentFirmware", "");
				bb.put("SetVersionNO", "");
				bb.put("ContactVersionNO", 0);
				bb.put("OperatorType", 0);
				bb.put("SmsNumber", "");
				bb.put("SmsBalanceKey", "");
				bb.put("SmsFlowKey", "");
				bb.put("DeviceID", "");
				bb.put("UserId", "");
				bb.put("DeviceModelID", "");
				bb.put("Firmware", "");
				bb.put("Gender", 0);
				bb.put("Grade", 0);
				bb.put("HireExpireDate", "");
				bb.put("HireStartDate", "");
				bb.put("HomeAddress", "");
				bb.put("HomeLat", "");
				bb.put("HomeLng", "");
				bb.put("IsGuard", 0);
				bb.put("Password", "");
				bb.put("PhoneCornet", "");
				bb.put("PhoneNumber", "");
				bb.put("Photo", "");
				bb.put("SchoolAddress", "");
				bb.put("SchoolLat", "");
				bb.put("SchoolLng", "");
				bb.put("SerialNumber", "");
				bb.put("UpdateTime", "");
				bb.put("LatestTime", "");
			}else{
				bb.put("Code", 0);
				bb.put("data", "");
			}
			return bb.toString();
		}
		
	
	//通讯录设置  这种直接让app封装data
	@ResponseBody
	@RequestMapping(value = "/communicationSettings", method = RequestMethod.POST)
	public String CommunicationSettings(@RequestBody String body) {
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");
		
		JSONObject bb = new JSONObject();
		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		String imei = jsonObject.getString("imei");
		String data = jsonObject.getString("data");

		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("Code", 0);
			return bb.toString();
		}
		
		if (socketLoginDto.getChannel().isActive()) {
			String msg="PHB,"+data;
			String reps = "[YW*"+imei+"*0001*"+RadixUtil.changeRadix(msg)+"*"+msg+"]";
			socketLoginDto.getChannel().writeAndFlush(reps);
			bb.put("Code", 1);
		} else {
			bb.put("Code", 0);
		}
		return bb.toString();
	}
	
	

}
