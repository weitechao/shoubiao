package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IStepService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.service.WatchSetService;
import com.bracelet.socket.BaseChannelHandler;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/controllerDevice")
public class WatchAppContDeviceController extends BaseController {

	@Autowired
	ILocationService locationService;
	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	IStepService stepService;
	@Autowired
	WatchSetService  watchSetService;
	@Resource
	BaseChannelHandler baseChannelHandler;
	private Logger logger = LoggerFactory.getLogger(getClass());

	// 定位指令
	@ResponseBody
	@RequestMapping(value = "/ask/localtion/{token}/{imei}", method = RequestMethod.GET)
	public String askLocation(@PathVariable String token,
			@PathVariable String imei) {
		Integer setStatus = 0;
		JSONObject bb = new JSONObject();
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("code", 0);
			watchSetService.insertInfo(imei,setStatus,8);
			return bb.toString();
		}
		String reps = "[YW*"+imei+"*0001*0002*CR]";
		if (socketLoginDto.getChannel().isActive()) {
			socketLoginDto.getChannel().writeAndFlush(reps);
			bb.put("code", 1);
			setStatus =1;
		} else {
			bb.put("code", 0);
		}
		watchSetService.insertInfo(imei,setStatus,8);
		return bb.toString();
	}

	// 关机指令
	@ResponseBody
	@RequestMapping(value = "/poweroff/{token}/{imei}", method = RequestMethod.GET)
	public String powerOff(@PathVariable String token, @PathVariable String imei) {
		JSONObject bb = new JSONObject();
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		Integer setStatus = 0;
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			watchSetService.insertInfo(imei,setStatus,9);
			bb.put("code", 0);
			return bb.toString();
		}
		String reps = "[YW*"+imei+"*0001*0008*POWEROFF]";
		if (socketLoginDto.getChannel().isActive()) {
			socketLoginDto.getChannel().writeAndFlush(reps);
			bb.put("code", 1);
			setStatus = 1;
		} else {
			bb.put("code", 0);
		}
		watchSetService.insertInfo(imei,setStatus,9);
		return bb.toString();
	}

	// 找手表指令
	@ResponseBody
	@RequestMapping(value = "/find/{token}/{imei}", method = RequestMethod.GET)
	public String find(@PathVariable String token, @PathVariable String imei) {
		JSONObject bb = new JSONObject();
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		Integer setStatus = 0;
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("code", 0);
			watchSetService.insertInfo(imei,setStatus,10);
			return bb.toString();
		}
		String reps = "[YW*"+imei+"*0001*0004*FIND]";
		if (socketLoginDto.getChannel().isActive()) {
			socketLoginDto.getChannel().writeAndFlush(reps);
			setStatus=1;
			bb.put("code", 1);
		} else {
			bb.put("code", 0);
		}
		watchSetService.insertInfo(imei,setStatus,10);
		return bb.toString();
	}
	
	// 监听
		@ResponseBody
		@RequestMapping(value = "/moniotr/{token}/{imei}/{phone}", method = RequestMethod.GET)
		public String moniotr(@PathVariable String token, @PathVariable String imei, @PathVariable String phone) {
			JSONObject bb = new JSONObject();
			SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
			Integer setStatus = 0;
			if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
				bb.put("code", 0);
				watchSetService.insertMoniotrLog(imei,setStatus,phone);
				return bb.toString();
			}
			if (socketLoginDto.getChannel().isActive()) {
				String reps = "[YW*"+imei+"*0001*0012*MONITOR,"+phone+"]";
				socketLoginDto.getChannel().writeAndFlush(reps);
				bb.put("code", 1);
			} else {
				bb.put("code", 0);
			}
			watchSetService.insertMoniotrLog(imei,setStatus,phone);
			return bb.toString();
		}

		/* 文字推送 */
		@ResponseBody
		@RequestMapping(value = "/pushMessage", method = RequestMethod.POST)
		public String pushMessage(@RequestBody String body) {
			JSONObject jsonObject = (JSONObject) JSON.parse(body);
			String token = jsonObject.getString("token");
			String imei = jsonObject.getString("imei");
			String message = jsonObject.getString("message");
			Integer setStatus = 0;
			JSONObject bb = new JSONObject();
			SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
			if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
				bb.put("code", 0);
				watchSetService.insertpushMessageLog(imei,setStatus,message);
				return bb.toString();
			}
			String reps = "[YW*"+imei+"*0001*0018*MESSAGE,";
			if (socketLoginDto.getChannel().isActive()) {
				reps = reps + message+"]";
				socketLoginDto.getChannel().writeAndFlush(reps);
				bb.put("code", 1);
				setStatus=1;
			} else {
				bb.put("code", 0);
			}
			watchSetService.insertpushMessageLog(imei,setStatus,message);
			return bb.toString();
		}
		
		/* 远程监拍 */
		@ResponseBody
		@RequestMapping(value = "/capt", method = RequestMethod.POST)
		public String capt(@RequestBody String body) {
			JSONObject jsonObject = (JSONObject) JSON.parse(body);
			Integer setStatus = 0;
			String token = jsonObject.getString("token");
			String imei = jsonObject.getString("imei");
			String come = jsonObject.getString("come");//来源
		
			JSONObject bb = new JSONObject();
			SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
			if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
				bb.put("code", 0);
				watchSetService.insertCaptLog(imei,setStatus,come);
				return bb.toString();
			}
			String reps = "[YW*"+imei+"*0001*0006*CAPT,";
			if (socketLoginDto.getChannel().isActive()) {
				reps = reps + come+"]";
				socketLoginDto.getChannel().writeAndFlush(reps);
				bb.put("code", 1);
				setStatus=1;
			} else {
				bb.put("code", 0);
			}
			watchSetService.insertCaptLog(imei,setStatus,come);
			return bb.toString();
		}
}
