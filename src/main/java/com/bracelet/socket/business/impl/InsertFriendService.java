package com.bracelet.socket.business.impl;

import io.netty.channel.Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.InsertFriend;
import com.bracelet.entity.WatchDevice;
import com.bracelet.service.IDeviceService;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IVoltageService;
import com.bracelet.service.IinsertFriendService;
import com.bracelet.util.RadixUtil;

@Service("insertFriendService")
public class InsertFriendService extends AbstractBizService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	IinsertFriendService insertFriendService;
	@Autowired
	IDeviceService ideviceService;

	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo,
			Channel channel) {

		logger.info("好友添加:" + jsonInfo);
		int status = 1;
		String[] shuzu = jsonInfo.split("\\*");
		String imei = shuzu[1];// 设备imei
		String no = shuzu[2];// 流水号
		String info = shuzu[4];
		logger.info("imei=" + imei + ",info=" + info + ",no=" + no);
		String[] infoshuzu = info.split(",");
		String addimei = infoshuzu[1];
		WatchDevice watdev = ideviceService.getDeviceInfo(addimei);
//结果：0，检查双方的设备发现不存在或者已经删除 1，请求成功  2已经发送过请求或者已经是好友
		if (watdev != null) {
			InsertFriend insertfo = insertFriendService.getInfo(imei, addimei);
			if (insertfo != null) {
				status = 2;
			} else {
				insertFriendService.insertFriendInfo(imei, addimei);
				status=1;
			}
		} else {
			status = 0;
		}
		String message="MFD,"+addimei+"," + status + "]";
		
		StringBuffer sb=new StringBuffer("[YW*"+imei+"*0001*");
		sb.append(RadixUtil.changeRadix(message));
		sb.append("*");
		sb.append(message);
         logger.info("好友添加返回设备info"+sb.toString());
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
