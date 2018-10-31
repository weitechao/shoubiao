package com.bracelet.socket.business.impl;

import io.netty.channel.Channel;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.LocationWatch;
import com.bracelet.exception.BizException;
import com.bracelet.service.ILocationService;
import com.bracelet.service.ISmslogService;
import com.bracelet.service.IVoltageService;
import com.bracelet.socket.business.IService;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.RespCode;
import com.bracelet.util.SmsUtil;
import com.bracelet.util.Utils;
import com.taobao.api.ApiException;

/**
 * 通过短信获取位置
 * 
 */
@Component("smsToGetLocation")
public class SmsToGetLocation extends AbstractBizService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	ISmslogService smslogService;
	@Autowired
	ILocationService locationService;

	@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto,
			JSONObject jsonObject, Channel channel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo,
			Channel channel) {

		logger.info("通过短信获取位置=" + jsonInfo);
		String[] shuzu = jsonInfo.split("\\*");
		String imei = shuzu[1];// 设备imei
		String no = shuzu[2];// 流水号
		String info = shuzu[4];
		String phone = socketLoginDto.getPhone();
		LocationWatch loinfo = locationService.getLatest(imei);
		String code = null;
		if (loinfo != null) {
          code=loinfo.getLat()+","+loinfo.getLng();
		}else{
			  code="0.00000,0.00000";
		}
		try {
			String msg = SmsUtil.sendMsgMenSuo(phone, code);
			smslogService.insert("短信验证码", phone, "SMS_115095090",
					"{\"number\":\"" + code + "\"}", 0, msg);
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}
