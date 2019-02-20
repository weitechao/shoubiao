package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IPushlogService;
import com.bracelet.service.IStepService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.service.WatchSetService;
import com.bracelet.socket.BaseChannelHandler;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.RadixUtil;
import com.bracelet.util.Utils;

import io.netty.buffer.Unpooled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

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
	@Autowired
	IPushlogService pushMsgService;
	@Resource
	BaseChannelHandler baseChannelHandler;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	// 定位指令
	@ResponseBody
	@RequestMapping(value = "/ask/localtion/{token}/{imei}", method = RequestMethod.GET)
	public String askLocation(@PathVariable String token,
			@PathVariable String imei) {
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
		String reps = "[YW*"+imei+"*0001*0002*CR]";
		if (socketLoginDto.getChannel().isActive()) {
			socketLoginDto.getChannel().writeAndFlush(reps);
			bb.put("Code", 1);
		} else {
			bb.put("Code", 0);
		}
		return bb.toString();
	}

	// 关机指令
	@ResponseBody
	@RequestMapping(value = "/poweroff/{token}/{imei}", method = RequestMethod.GET)
	public String powerOff(@PathVariable String token, @PathVariable String imei) {
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
		String reps = "[YW*"+imei+"*0001*0008*POWEROFF]";
		if (socketLoginDto.getChannel().isActive()) {
			socketLoginDto.getChannel().writeAndFlush(reps);
			bb.put("Code", 1);
		} else {
			bb.put("Code", 0);
		}
		return bb.toString();
	}

	// 找手表指令
	@ResponseBody
	@RequestMapping(value = "/find/{token}/{imei}", method = RequestMethod.GET)
	public String find(@PathVariable String token, @PathVariable String imei) {
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
		String reps = "[YW*"+imei+"*0001*0004*FIND]";
		if (socketLoginDto.getChannel().isActive()) {
			socketLoginDto.getChannel().writeAndFlush(reps);
			bb.put("Code", 1);
		} else {
			bb.put("Code", 0);
		}
		return bb.toString();
	}
	
	// 监听
		@ResponseBody
		@RequestMapping(value = "/moniotr/{token}/{imei}/{phone}", method = RequestMethod.GET)
		public String moniotr(@PathVariable String token, @PathVariable String imei, @PathVariable String phone) {
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
				String msg="MONITOR,"+phone;
				String reps = "[YW*"+imei+"*0001*"+RadixUtil.changeRadix(msg)+"*"+msg+"]";
				socketLoginDto.getChannel().writeAndFlush(reps);
				bb.put("Code", 1);
			} else {
				bb.put("Code", 0);
			}
			return bb.toString();
		}

		/* 文字推送 */
		@ResponseBody
		@RequestMapping(value = "/pushMessage", method = RequestMethod.POST)
		public String pushMessage(@RequestBody String body) {
			JSONObject bb = new JSONObject();
			JSONObject jsonObject = (JSONObject) JSON.parse(body);
			String token = jsonObject.getString("token");
			String user_id = checkTokenWatchAndUser(token);
			if ("0".equals(user_id)) {
				bb.put("Code", -1);
				return bb.toString();
			}
			
			String imei = jsonObject.getString("imei");
			String message = jsonObject.getString("message");
		
			SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
			if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
				pushMsgService.insertPushMsg(imei,message,0);
				bb.put("Code", 4);
				return bb.toString();
			}
			if (socketLoginDto.getChannel().isActive()) {
				String msg="MESSAGE,"+message;
				try {
					int msglength = msg.getBytes("UTF-8").length;
					String reps = "[YW*"+imei+"*0001*"+RadixUtil.changeRadix(msglength)+"*"+msg+"]";
					logger.info("app发送文字="+reps);
					//Unpooled.copiedBuffer(msg, Charset.forName("UTF-8"));
					//socketLoginDto.getChannel().writeAndFlush(Unpooled.copiedBuffer(reps, Charset.forName("UTF-8")));
					socketLoginDto.getChannel().writeAndFlush(reps);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				bb.put("Code", 1);
				pushMsgService.insertPushMsg(imei,message,1);
			} else {
				bb.put("Code", 0);
				pushMsgService.insertPushMsg(imei,message,0);
			}
			return bb.toString();
		}
		
		/* 远程监拍 */
		@ResponseBody
		@RequestMapping(value = "/capt", method = RequestMethod.POST)
		public String capt(@RequestBody String body) {
			JSONObject bb = new JSONObject();
			JSONObject jsonObject = (JSONObject) JSON.parse(body);
			String token = jsonObject.getString("token");
			
			String user_id = checkTokenWatchAndUser(token);
			if ("0".equals(user_id)) {
				bb.put("Code", -1);
				return bb.toString();
			}
			
			String imei = jsonObject.getString("imei");
			String come = jsonObject.getString("come");//来源
		
			SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
			if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
				bb.put("Code", 4);
				return bb.toString();
			}
			if (socketLoginDto.getChannel().isActive()) {
				String msg="CAPT,"+come;
				String reps = "[YW*"+imei+"*0002*"+RadixUtil.changeRadix(msg)+"*"+msg+"]";
				socketLoginDto.getChannel().writeAndFlush(reps);
				bb.put("Code", 1);
			} else {
				bb.put("Code", 0);
			}
			return bb.toString();
		}
}
