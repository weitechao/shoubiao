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

@Service("getIpService")
public class GetIpService extends AbstractBizService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	IDeviceService ideviceService;

	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo,
			Channel channel) {

		logger.info("获取服务器连接IP:" + jsonInfo);

		String[] shuzu = jsonInfo.split("\\*");
		String imei = shuzu[1];// 设备imei
		String no = shuzu[2];// 流水号
		String info = shuzu[4];
		logger.info("imei=" + imei + ",info=" + info + ",no=" + no);
		List<IpAddressInfo> list = ideviceService.getipinfo();
		int count = list.size();
		StringBuffer sb = new StringBuffer("[YW*" + imei + "*0001*0002*IPREQ,");
		sb.append(count);
		if (count > 0) {
			for (IpAddressInfo infoo : list) {
				sb.append(",");
				sb.append(infoo.getIp());
				sb.append(",");
				sb.append(infoo.getPort());
			}
		}
		sb.append("]");
		return sb.toString();
	}

	@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto,
			JSONObject jsonObject, Channel channel) {
		return null;
	}

	/*
	 * [YW*111111111111111*0002*008f*UD,220414,134652,A,22.571707,N,113.8613968,E
	 * ,
	 * 0.1,0.0,100,7,60,90,1000,50,0000,4,1,460,0,9360,4082,131,9360,4092,148,9360
	 * ,4091,143,9360,4153,141]
	 * 
	 * [YW*111111111111111*0002*0066*UD,230516,123715,V,0.000000,N,0.000000,E,0.00
	 * ,0.0,0.0,0,100,58,1279,0,00000000,1,1,460,0,9331,4770,1,0]
	 * [YW*111111111111111
	 * *0002*0066*UD,230516,123741,V,0.000000,N,0.000000,E,0.00
	 * ,0.0,0.0,0,52,58,1279,0,00000000,1,1,460,0,9331,4740,22,0]
	 */
}
