package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.HttpBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.BindDevice;
import com.bracelet.entity.Location;
import com.bracelet.entity.LocationWatch;
import com.bracelet.entity.NoticeInfo;
import com.bracelet.entity.UserInfo;
import com.bracelet.entity.VersionInfo;
import com.bracelet.entity.WatchAppVersionInfo;
import com.bracelet.exception.BizException;
import com.bracelet.service.IAuthcodeService;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IOpenDoorService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.service.IVoltageService;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.RanomUtil;
import com.bracelet.util.RespCode;
import com.bracelet.util.Utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/watchAppUser")
public class WatchAppUserController extends BaseController {

	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	IAuthcodeService authcodeService;
	@Autowired
	IVoltageService voltageService;

	@Autowired
	IOpenDoorService openService;
	@Autowired
	ILocationService locationService;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	//登录
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String watchapplogin(@RequestBody String body) {
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		
		String tel = jsonObject.getString("tel");
		String password = jsonObject.getString("pwd");// 默认123456
		JSONObject bb = new JSONObject();
		
		//先检查登录表，如果登录表里有，检查密码，如果密码正确则登录OK，判断设备是否在线，在线，则发送定位请求，不在线则查询最后一次定位，
		UserInfo userInfo = userInfoService.getUserInfoByUsername(tel);
		if(userInfo !=null ){
			
			String token = tokenInfoService.genToken(userInfo
					.getUser_id());
			if (password.equalsIgnoreCase(userInfo.getPassword())) {
				bb.put("code", 1);//1表示login succes
				bb.put("token", token);
			}else{
				bb.put("code", 2);//2表示密码错误
			}
		}else{
			//UserInfo userInfoLuRu = userInfoService.getUserInfoLuRuByUsername(tel);//这里查询录入表
			//if(userInfoLuRu !=null ){//说明已经录入 第一次登录
				userInfoService.saveUserInfo(tel, "123456", 1);//表示新注册
			
				String token = tokenInfoService.genToken(userInfoService.getUserInfoByUsername(tel).getUser_id());
				bb.put("code", 1);//1表示login succes
			//	loginStatus=true;
				bb.put("token", token);
			//}else{
			//	bb.put("code", -1);//1表示login fail
			//}
	/*	}
		if(loginStatus){//登录成功才给设备发送定位指令
			BindDevice bdd= userInfoService.getBindInfoById(userId);
			SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
			if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
				bb.put("deviceStatus", 0);
			}
			String reps = "[YW*"+imei+"*0001*0002*CR]";
			if (socketLoginDto.getChannel().isActive()) {
				bb.put("deviceStatus", 1);
				socketLoginDto.getChannel().writeAndFlush(reps);
				bb.put("sendStatus", 1);
			} else {
				bb.put("deviceStatus", 0);
				bb.put("sendStatus", 2);
			}
			LocationWatch location = locationService.getLatest(imei);//这里需要修改为从redis里拿或者hashmap里
			if(location!=null){
				bb.put("locationLast", 1);//查询到
				bb.put("lat", location.getLat());
				bb.put("lng", location.getLng());
				bb.put("locationtype", location.getLocation_type());
				bb.put("time", location.getUpload_time().getTime());
			}else{
				bb.put("locationLast", 0);//未查询到
			}
		}*/
		}
		return bb.toString();
	}
	
	
	
	
	//修改密码
	@ResponseBody
	@RequestMapping(value = "/udpatePwd", method = RequestMethod.POST)
	public String watchappUpdatePwd(@RequestBody String body) {
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		JSONObject bb = new JSONObject();
		String token = jsonObject.getString("token");
		String user_id = checkTokenWatchAndUser(token);
         if("0".equals(user_id)){
        	 bb.put("code", 2);
        	 return bb.toString();
         }
		String tel = jsonObject.getString("tel");
		String password = jsonObject.getString("pwd");// 默认123456
		UserInfo userInfo = userInfoService.getUserInfoByUsername(tel);
		if(userInfo!=null){
			userInfoService.updateUserPassword(userInfo.getUser_id(), password);			
			bb.put("code", 1);
		}else{
			bb.put("code", 0);
		}
		return bb.toString();
	}
	
	//找回密码
		@ResponseBody
		@RequestMapping(value = "/findPwd", method = RequestMethod.POST)
		public String findPwd(@RequestBody String body) {
			JSONObject jsonObject = (JSONObject) JSON.parse(body);
			
			//String token = jsonObject.getString("token");
			String tel = jsonObject.getString("tel");
			String password = jsonObject.getString("pwd");// 默认123456
			JSONObject bb = new JSONObject();
			UserInfo userInfo = userInfoService.getUserInfoByUsername(tel);
			if(userInfo!=null){
				userInfoService.updateUserPassword(userInfo.getUser_id(), password);			
				bb.put("code", 1);
			}else{
				bb.put("code", 0);
			}
			return bb.toString();
		}
		
		
		//绑定设备
		@ResponseBody
		@RequestMapping(value = "/bindDevice", method = RequestMethod.POST)
		public String bindDevice(@RequestBody String body) {
			JSONObject bb = new JSONObject();
			JSONObject jsonObject = (JSONObject) JSON.parse(body);
			String token = jsonObject.getString("token");
			
			String user_id = checkTokenWatchAndUser(token);
	         if("0".equals(user_id)){
	        	 bb.put("code", 2);
	        	 return bb.toString();
	         }
		
			
			String tel = jsonObject.getString("imei");
			UserInfo userInfo = userInfoService.getUserInfoByUsername(tel);
			if(userInfo!=null){
				userInfoService.updateUserPassword(userInfo.getUser_id(), password);			
				bb.put("code", 1);
			}else{
				bb.put("code", 0);
			}
			return bb.toString();
		}
	
	

}
