package com.bracelet.controller;

import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.HttpBaseDto;
import com.bracelet.dto.LatestBloodFatDto;
import com.bracelet.dto.LatestBloodSugarDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.BloodFat;
import com.bracelet.entity.BloodSugar;
import com.bracelet.entity.InstrancyMsg;
import com.bracelet.service.IBloodFatService;
import com.bracelet.service.IBloodSugarService;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.HostUtil;
import com.bracelet.util.HttpClientGet;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/instrancy")
public class InstrancyMsgController extends BaseController {
	@Autowired
	IBloodFatService bloodFatService;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String getHost() {
		JSONObject bb = new JSONObject();
		InstrancyMsg msg = bloodFatService.getMsg(1);
		if (msg != null) {
			bb.put("code", 1);
			bb.put("msg", msg.getMsg());
			bb.put("time_slot", msg.getTime_slot());
			//bb.put("createtime", msg.getCreatetime().getTime());
		} else {
			bb.put("code", 0);
		}
		return bb.toString();
	}
}
