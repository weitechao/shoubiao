package com.bracelet.socket.business.impl;

import com.bracelet.util.SmsUtil;
import com.bracelet.util.StringUtil;
import com.bracelet.util.Utils;

import io.netty.channel.Channel;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.dto.SosDto;
import com.bracelet.entity.Location;
import com.bracelet.entity.UserInfo;
import com.bracelet.entity.WhiteListInfo;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IPushlogService;
import com.bracelet.service.ISoslogService;
import com.bracelet.service.ISosService;
import com.bracelet.service.ITokenInfoService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.util.AndroidPushUtil;
import com.bracelet.util.SingleCallByTxtUtil;

@Component
public class SosService extends AbstractBizService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	ILocationService locationService;
	@Autowired
	ITokenInfoService tokenInfoService;
	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	ISoslogService soslogService;
	@Autowired
	IPushlogService pushlogService;
	@Autowired
	ISosService sosService;

	@Override
	public SocketBaseDto process1(SocketLoginDto socketLoginDto, JSONObject jsonObject, Channel channel) {
		Long userId = socketLoginDto.getUser_id();
		String imei = socketLoginDto.getImei();
		String lat = "";
		String lng = "";
		Location location = locationService.getLatest(userId);
		if (location != null) {
			lat = location.getLat();
			lng = location.getLng();
			SosDto sosDto = new SosDto();
			sosDto.setLat(lat);
			sosDto.setLng(lng);
			sosDto.setTimestamp(location.getUpload_time().getTime());
			String target = tokenInfoService.getTokenByUserId(userId);
			String title = "SOS报警";
			String content = JSON.toJSONString(sosDto);
			String notifyContent = "收到SOS报警，请点击查看";
			AndroidPushUtil.push(target, title, content, notifyContent);
			// save push log
			this.pushlogService.insert(userId, imei, 0, target, title, content);
		}
		UserInfo userInfo = this.userInfoService.getUserInfoById(userId);
		if (userInfo != null && !StringUtils.isEmpty(userInfo.getUsername())) {
			String time = Utils.format14DateString(location.getUpload_time().getTime());
			SmsUtil.sendSms("SOS报警", userInfo.getUsername(), "SMS_99390009", "{\"number\":\"" + time + "\"}");
			SmsUtil.sendSms("SOS报警", userInfo.getUsername(), "SMS_99390009", "{\"number\":\"" + time + "\"}");
			// save sms log
			this.soslogService.insert(userId, imei, lat, lng);
		}

		// 语音播报
		List<WhiteListInfo> list = sosService.find(userId);// 查询白名单
		if (list.size() > 0) {
			for (WhiteListInfo wlInfo : list) {
				String phone = wlInfo.getPhone();
				String name = wlInfo.getName();
				String msg = "";
				if (StringUtil.isPhoneLegal(phone)) {
					try {
						msg = SingleCallByTxtUtil.push(phone, name);
					} catch (ClientException e) {
						msg = e.getErrMsg();
						logger.warn("[SosService] 下发语音播放遇到异常:", e);
					} finally {
						sosService.insertSingleCallByTxt(userId, imei, phone, name, msg);
					}
				}
			}
		}

		SocketBaseDto dto = new SocketBaseDto();
		dto.setType(jsonObject.getIntValue("type"));
		dto.setNo(jsonObject.getString("no"));
		dto.setTimestamp(System.currentTimeMillis());

		dto.setStatus(0);
		return dto;
	}

	@Override
	public String process(String jsonInfo, Channel incoming) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo,
			Channel channel) {
		// TODO Auto-generated method stub
		return null;
	}
}
