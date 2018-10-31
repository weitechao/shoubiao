package com.bracelet.socket.business.impl;

import java.util.List;

import io.netty.channel.Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.InsertFriend;
import com.bracelet.entity.IpAddressInfo;
import com.bracelet.entity.WatchDevice;
import com.bracelet.service.IDeviceService;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IVoltageService;
import com.bracelet.service.IinsertFriendService;

@Service("resetService")
public class ResetService extends AbstractBizService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	IDeviceService ideviceService;

	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo,
			Channel channel) {

		logger.info("指令终端返回:" + jsonInfo);
		
		return "";
	}

	@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto,
			JSONObject jsonObject, Channel channel) {
		return null;
	}

}
