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
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.FingerDto;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.BindDevice;
import com.bracelet.entity.NoticeInfo;
import com.bracelet.entity.UserInfo;
import com.bracelet.exception.BizException;
import com.bracelet.util.PushUtil;
import com.bracelet.util.RespCode;
import com.bracelet.util.SmsUtil;
import com.bracelet.service.IOpenDoorService;
import com.bracelet.service.IPushlogService;
import com.bracelet.service.ISmslogService;
import com.bracelet.service.ITokenInfoService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.socket.business.IService;
import com.bracelet.util.ChannelMap;
import com.taobao.api.ApiException;

@Component("prizeupdoorService")
public class PrizeUpDoorService implements IService {
	@Autowired
	IOpenDoorService opendoorService;
	@Autowired
	ISmslogService smslogService;
	@Autowired
	IUserInfoService userInfoService;
	
	@Autowired
	ITokenInfoService tokenInfoService;
	@Autowired
	IPushlogService pushlogService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public SocketBaseDto process(JSONObject jsonObject, Channel channel) {
		logger.info("===窍门记录：" + jsonObject.toJSONString());
		String no = jsonObject.getString("no");
		String imei = jsonObject.getString("imei");
		
		long timestamp = jsonObject.getLongValue("timestamp");
		opendoorService.insert(3, Long.valueOf(0), 4, 1, imei,"",new Timestamp(timestamp * 1000));

		List<BindDevice> list = userInfoService.getBindInfoByImei(imei);
		if (list.size() > 0) {
			for (BindDevice info : list) {
				Long userId = info.getUser_id();
				UserInfo userinfo = userInfoService.getUserInfoById(userId);
				if (userinfo != null) {
					String tel = userinfo.getUsername();
					try {
						String msg = SmsUtil.pickALockSendMsg(info.getName(), tel);
						smslogService.insert("撬锁报警", tel, "SMS_91990103",
								"imei:" + imei + "-tel:" + tel, 0, msg);
								
								FingerDto sosDto = new FingerDto();
								sosDto.setName("不明用户");
								sosDto.setImei(imei);
								sosDto.setTimestamp(new Date().getTime());
								String target = tokenInfoService.getTokenByUserId(userId);
								String title = "撬锁报警";
								String content = JSON.toJSONString(sosDto);
								String notifyContent = "有人正在撬锁,请及时查看!";
								
								NoticeInfo vinfo = userInfoService.getNoticeSet(userId);
								if (vinfo == null ||  vinfo.getMemberunlockswitch() == 1) {
								PushUtil.push(target, title, content, notifyContent);
								// save push log
								this.pushlogService.insert(userId, imei, 0, target, title, content);
								}
						
					} catch (ApiException e) {
						e.printStackTrace();
					}
				}
			}
		}

		SocketBaseDto dto = new SocketBaseDto();
		dto.setType(jsonObject.getIntValue("type"));
		dto.setNo(no);
		dto.setTimestamp(new Date().getTime());
		dto.setStatus(0);

		return dto;

	}

	@Override
	public String process(String jsonInfo, Channel incoming) {
		// TODO Auto-generated method stub
		return null;
	}
}
