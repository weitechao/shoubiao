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
import com.bracelet.entity.WatchFriend;
import com.bracelet.exception.BizException;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IStepService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.service.WatchFriendService;
import com.bracelet.service.WatchSetService;
import com.bracelet.service.WatchTkService;
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
@RequestMapping("/tk")
public class WatchAppTkController extends BaseController {
	@Autowired
	WatchTkService watchtkService;
	@Resource
	BaseChannelHandler baseChannelHandler;
	private Logger logger = LoggerFactory.getLogger(getClass());

	/* 对讲群聊
	 *  [YW*YYYYYYYYYY*NNNN*LEN*TK,来源,文件名字,当前包,总分包数,ARM格式二进制音频数据]
	 *  */
	@ResponseBody
	@RequestMapping(value = "/tkToDevice", method = RequestMethod.POST)
	public String addfriend(@RequestBody String body) {
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");
		String imei = jsonObject.getString("imei");
		String phone = jsonObject.getString("phone");// 号码
		String voiceData = jsonObject.getString("voiceData");// 语音内容 base64转字符串
		String sourceName = jsonObject.getString("sourceName");// 文件名字
		
		JSONObject bb = new JSONObject();
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		String numMessage=Utils.randomString(5);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			watchtkService.insertVoiceInfo(imei,phone,sourceName,voiceData,0,numMessage);
			bb.put("code", 0);
			return bb.toString();
		}
		StringBuffer sb = new StringBuffer("[YW*" + imei
				+ "*NNNN*LEN*TK,"+numMessage+",");
		if (socketLoginDto.getChannel().isActive()) {
			sb.append(phone);
			sb.append(",");
			sb.append(sourceName);
			sb.append(",");
			sb.append(1);
			sb.append(",");
			sb.append(1);
			sb.append(",");
			sb.append(voiceData);
			sb.append("]");
			socketLoginDto.getChannel().writeAndFlush(sb.toString());
			bb.put("code", 1);
			//因为这里我觉得下发的时候需要增加一个消息号，设备再回复的时候，。把消息号带上，这个消息号永远唯一，消息号我随机生成
			watchtkService.insertVoiceInfo(imei,phone,sourceName,voiceData,1,numMessage);
		} else {
			bb.put("code", 0);
		}
		return bb.toString();
	}

}
