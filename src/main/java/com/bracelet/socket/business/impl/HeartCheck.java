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
import com.bracelet.exception.BizException;
import com.bracelet.redis.LimitCache;
import com.bracelet.service.IVoltageService;
import com.bracelet.socket.business.IService;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.RespCode;
import com.bracelet.util.Utils;

/**
 * 系统心跳
 * 
 */
@Component("heartCheck")
public class HeartCheck extends AbstractBizService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	IVoltageService voltageService;
	
	@Autowired
	LimitCache limitCache;
	
	@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto,
			JSONObject jsonObject, Channel channel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo,
			Channel channel) {
        
		logger.info("链路保持="+jsonInfo);
		String[] shuzu = jsonInfo.split("\\*");
		String imei = shuzu[1];// 设备imei
		
		String info = shuzu[4];

		String[] infoshuzu = info.split(",");
		String energy = infoshuzu[1];
		
		
	/*	SocketLoginDto channelDto = new SocketLoginDto();
		channelDto.setChannel(channel);
		channelDto.setNo("1");
		channelDto.setImei(imei);
		channelDto.setEnergy(energy);
	
	

		ChannelMap.addChannel(imei, channelDto);
		ChannelMap.addChannel(channel, channelDto);*/
		limitCache.addKey(imei+"_energy", energy+"");
		ChannelMap.addEnergy(imei,energy);
		
		
		//还需要保存下电量
	//	voltageService.insertDianLiang(imei, Integer.valueOf(energy));
		logger.info("链路保持imei:" + imei  + ",电量:" + energy);
	     
		String resp = "[YW*"+imei+"*0001*0016*LK,"+Utils.getTime()+"]";
		logger.info("心跳返回="+resp);
		return resp;
		
		}



	/*@Override
	public SocketBaseDto process(JSONObject jsonObject, Channel channel) {
		logger.info("===系统心跳：" + jsonObject.toJSONString());
		SocketBaseDto dto = new SocketBaseDto();
		dto.setType(jsonObject.getIntValue("type"));
		dto.setNo(jsonObject.getString("no"));
		dto.setTimestamp(new Date().getTime());
		dto.setStatus(0);

		return dto;
	}

	@Override
	public String process(String jsonInfo, Channel channel) {

		String[] shuzu = jsonInfo.split("\\*");
		String imei = shuzu[1];// 设备imei
		String no = shuzu[2];// 流水号
		String info = shuzu[4];

		String[] infoshuzu = info.split(",");
		String energy = infoshuzu[1];
		//还需要保存下电量
	
		logger.info("链路保持imei:" + imei + "," + ",no:" + no + ",电量:" + energy);

	
		String resp = "[YW*8800000015*0001*0002*LK ,"+Utils.getTime()+"]";
		return resp;
	}*/

}
