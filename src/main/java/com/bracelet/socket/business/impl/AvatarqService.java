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
import com.bracelet.service.IDeviceService;


@Service("avatarqService")
public class AvatarqService extends AbstractBizService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	IDeviceService ideviceService;

	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo,
			Channel channel) {

		logger.info("通信录真人头像:" + jsonInfo);
		
		return "[YW*5678901234*0001*0006*AVATAR,1,1234567895,0001,1，10，JPG格式二进制图像数据]";
	}

	@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto,
			JSONObject jsonObject, Channel channel) {
		return null;
	}

}
