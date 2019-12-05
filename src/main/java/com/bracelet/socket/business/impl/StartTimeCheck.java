package com.bracelet.socket.business.impl;

import io.netty.channel.Channel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.exception.BizException;
import com.bracelet.redis.LimitCache;
import com.bracelet.service.IVoltageService;
import com.bracelet.socket.business.IService;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.RadixUtil;
import com.bracelet.util.RespCode;
import com.bracelet.util.Utils;

/**
 * 开始时间
 * 
 */
@Component("startTimeCheck")
public class StartTimeCheck extends AbstractBizService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto,
			JSONObject jsonObject, Channel channel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo,
			Channel channel) {
        
		logger.info("开始时间获取="+jsonInfo);
		String[] shuzu = jsonInfo.split("\\*");
		String imei = shuzu[1];// 设备imei
	
		String resp = "[YW*"+imei+"*0001*0016*KL,"+Utils.getJian8Time()+"]";
		logger.info("开始时间获取="+resp);
		return resp;
		}
	 

	

}
