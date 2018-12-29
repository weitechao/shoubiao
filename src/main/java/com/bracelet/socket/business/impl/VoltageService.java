package com.bracelet.socket.business.impl;

import io.netty.channel.Channel;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.FingerDto;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.BindDevice;
import com.bracelet.entity.NotRegisterInfo;
import com.bracelet.entity.UserInfo;
import com.bracelet.exception.BizException;
import com.bracelet.service.IPushlogService;
import com.bracelet.service.ISmslogService;
import com.bracelet.service.ITokenInfoService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.service.IVoltageService;
import com.bracelet.socket.business.IService;
import com.bracelet.util.PushUtil;
import com.bracelet.util.RespCode;
import com.bracelet.util.SmsUtil;
import com.taobao.api.ApiException;

/**
 * 电压业务
 * 
 */
@Component("voltageService")
public class VoltageService implements IService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	IVoltageService voltageService;
	@Autowired
	ITokenInfoService tokenInfoService;
	@Autowired
	IPushlogService pushlogService;
	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	ISmslogService smslogService;

	@Override
	public SocketBaseDto process(JSONObject jsonObject, Channel incoming) {

		logger.info("===低电量：" + jsonObject.toJSONString());
		JSONObject jsonObject2 = (JSONObject) jsonObject.get("data");
		if (jsonObject2 == null) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}

		String no = jsonObject.getString("no");
		String imei = jsonObject.getString("imei");
		Integer voltage = jsonObject2.getInteger("battery_percent");

		BindDevice bindd = userInfoService.getBindInfoByImeiAndStatus(imei, 1);

		String notifyContent = bindd.getName() + "智能锁电量低于" + voltage
				+ "%，请及时更换电池";
		FingerDto sosDto = new FingerDto();
		sosDto.setName(bindd.getName());
		sosDto.setImei(imei);
		sosDto.setTimestamp(System.currentTimeMillis());
		sosDto.setContent(notifyContent); 
		String target = tokenInfoService.getTokenByUserId(bindd.getUser_id());
		String title = "低电量提示";
		String content = JSON.toJSONString(sosDto);
		PushUtil.push(target, title, content, notifyContent);
		// save push log
		this.pushlogService.insert(bindd.getUser_id(), imei, 0, target, title,
				content);
		try {
			UserInfo userinfo = userInfoService.getUserInfoById(bindd
					.getUser_id());
			
			String  msg = SmsUtil.lowElectricSosMsg(userinfo.getUsername(), voltage
					+ "%", bindd.getName());

			smslogService.insert("低电量报警", userinfo.getUsername(),
					"SMS_134515053",
					"imei:" + imei + "-tel:" + userinfo.getUsername(), 0, msg);
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * List<BindDevice> list = userInfoService.getBindInfoByImei(imei); if
		 * (list.size() > 0) { for (BindDevice info : list) { Long userId =
		 * info.getUser_id();
		 * 
		 * String notifyContent = "电量小于百分之10，请及时充电!"; FingerDto sosDto = new
		 * FingerDto(); sosDto.setName(info.getName()); sosDto.setImei(imei);
		 * sosDto.setTimestamp(System.currentTimeMillis());
		 * sosDto.setContent(notifyContent); String target =
		 * tokenInfoService.getTokenByUserId(userId); String title = "低电量提示";
		 * String content = JSON.toJSONString(sosDto); PushUtil.push(target,
		 * title, content, notifyContent); // save push log
		 * this.pushlogService.insert(userId, imei, 0, target, title, content);
		 * }
		 * 
		 * }
		 */

		voltageService.insertDianLiang(imei, voltage);

		SocketBaseDto dto = new SocketBaseDto();
		dto.setType(jsonObject.getIntValue("type"));
		dto.setNo(no);
		dto.setTimestamp(System.currentTimeMillis());
		dto.setStatus(0);

		return dto;

	}

	@Override
	public String process(String jsonInfo, Channel incoming) {
		// TODO Auto-generated method stub
		return null;
	}
}/*
 * private Logger logger = LoggerFactory.getLogger(getClass());
 * 
 * @Autowired IVoltageService voltageService;
 * 
 * public SocketBaseDto process1(SocketLoginDto socketLoginDto, JSONObject
 * jsonObject, Channel channel) { JSONArray jsonArray =
 * jsonObject.getJSONArray("data"); logger.info("===电压：" +
 * jsonObject.toJSONString()); Long user_id = socketLoginDto.getUser_id(); for
 * (int i = 0; i < jsonArray.size(); i++) { try { JSONObject jsonObject2 =
 * (JSONObject) jsonArray.get(i); Integer voltage =
 * jsonObject2.getInteger("battery_percent");
 * voltageService.insert(imei,voltage); } catch (Exception e) {
 * logger.error("保存电压数组数据，发生错误:" + jsonArray.toJSONString(), e); } }
 * 
 * SocketBaseDto dto = new SocketBaseDto();
 * dto.setType(jsonObject.getIntValue("type"));
 * dto.setNo(jsonObject.getString("no")); dto.setTimestamp(new
 * Date().getTime()); dto.setStatus(0); return dto; }
 */

