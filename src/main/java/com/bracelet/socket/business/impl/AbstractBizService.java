package com.bracelet.socket.business.impl;

import io.netty.channel.Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.exception.BizException;
import com.bracelet.redis.LimitCache;
import com.bracelet.service.IDeviceService;
import com.bracelet.socket.business.IService;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.RespCode;

@Component
public abstract class AbstractBizService implements IService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	LimitCache limitCache;
	
	@Autowired
	IDeviceService ideviceService;

	@Override
	public SocketBaseDto process(JSONObject jsonObject, Channel channel) {
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(channel);
		if (socketLoginDto == null) {
			logger.info("设备没有登录，请登录:", jsonObject);
			throw new BizException(RespCode.DEV_NOT_LOGIN);
		}
		return this.process1(socketLoginDto, jsonObject, channel);
	}

	protected abstract SocketBaseDto process1(SocketLoginDto socketLoginDto, JSONObject jsonObject, Channel channel);
	
	
	public String process(String jsonInfo, Channel channel) {
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(channel);
		if (socketLoginDto == null) {
			logger.info("手表没有登录，请登录:", jsonInfo);
			return "not login";
			//throw new BizException(RespCode.DEV_NOT_LOGIN);
		}
		return this.process2(socketLoginDto, jsonInfo, channel);
	}
	
	protected abstract String process2(SocketLoginDto socketLoginDto, String jsonInfo, Channel channel);
}
