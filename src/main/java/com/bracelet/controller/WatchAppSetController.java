package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.HttpBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.Location;
import com.bracelet.entity.LocationOld;
import com.bracelet.entity.LocationRequest;
import com.bracelet.entity.OldBindDevice;
import com.bracelet.entity.Step;
import com.bracelet.entity.UserInfo;
import com.bracelet.entity.VersionInfo;
import com.bracelet.exception.BizException;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IStepService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.service.WatchSetService;
import com.bracelet.socket.BaseChannelHandler;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.HttpClientGet;
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
		Integer setStatus = 0;
		JSONObject bb = new JSONObject();
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("code", 0);
			watchSetService.insertInfo(imei, setStatus, 19);
			return bb.toString();
		}
		String reps = "[YW*" + imei + "*0001*0007*TRACKER]";
		if (socketLoginDto.getChannel().isActive()) {
			socketLoginDto.getChannel().writeAndFlush(reps);
			bb.put("code", 1);
			setStatus = 1;
		} else {
			bb.put("code", 0);
		}
		watchSetService.insertInfo(imei, setStatus, 19);
		return bb.toString();
	}

	/* 发送监控指令 */
	@ResponseBody
	@RequestMapping(value = "/guard/{token}/{imei}/{type}", method = RequestMethod.GET)
	public String guard(@PathVariable String token, @PathVariable String imei,
			@PathVariable Integer type) {
		Integer setStatus = 0;
		JSONObject bb = new JSONObject();
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("code", 0);
			watchSetService.insertGuardLog(imei, setStatus, type);
			return bb.toString();
		}
		String reps = "[YW*" + imei + "*0001*0007*GUARD," + type + "]";
		if (socketLoginDto.getChannel().isActive()) {
			socketLoginDto.getChannel().writeAndFlush(reps);
			bb.put("code", 1);
			setStatus = 1;
		} else {
			bb.put("code", 0);
		}
		watchSetService.insertGuardLog(imei, setStatus, type);
		return bb.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/set", method = RequestMethod.POST)
	public String addfriend(@RequestBody String body) {
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");
		String imei = jsonObject.getString("imei");
		String phone = jsonObject.getString("phone");// 号码
		String data = jsonObject.getString("voiceData");

		JSONObject bb = new JSONObject();
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("code", 0);
			return bb.toString();
		}
		StringBuffer sb = new StringBuffer("[YW*" + imei + "*NNNN*LEN*SET,");
		if (socketLoginDto.getChannel().isActive()) {
			sb.append(phone).append("1234").append(",").append(data);
			sb.append("]");
			socketLoginDto.getChannel().writeAndFlush(sb.toString());
			bb.put("code", 1);
		} else {
			bb.put("code", 0);
		}
		return bb.toString();
	}

}
