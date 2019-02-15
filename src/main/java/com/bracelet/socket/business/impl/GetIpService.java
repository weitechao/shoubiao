package com.bracelet.socket.business.impl;

import java.util.List;

import io.netty.channel.Channel;

import org.apache.commons.lang3.StringUtils;
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
import com.bracelet.redis.LimitCache;
import com.bracelet.service.IDeviceService;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IVoltageService;
import com.bracelet.service.IinsertFriendService;
import com.bracelet.socket.business.IService;
import com.bracelet.util.HttpClientGet;
import com.bracelet.util.RadixUtil;
import com.bracelet.util.Utils;

@Service("getIpService")
public class GetIpService implements IService {
	private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
	IDeviceService ideviceService;
   
    @Override
	public String process(String jsonInfo, Channel channel) {

		logger.info("获取服务器连接IP:" + jsonInfo);

		String[] shuzu = jsonInfo.split("\\*");
		String imei = shuzu[1];// 设备imei
		String no = shuzu[2];// 流水号
		String info = shuzu[4];
		logger.info("imei=" + imei + ",info=" + info + ",no=" + no);
		//List<IpAddressInfo> list = ideviceService.getipinfo();
		//int count = list.size();
		StringBuffer sb = new StringBuffer("[YW*" + imei + "*0001*");//0002*
        StringBuffer add=new StringBuffer("IPREQ,");
		add.append(1);
		add.append(",");
	//	String responseJsonString = HttpClientGet.get(Utils.IP_PORT_URL);
		/*
47.92.183.190   负载均衡ip端口
		 * 分配逻辑
		 * 先通过请求slb 80 端口  slb 自己去获取到后端业务的一个ip和端口返回
		 * */
	
			add.append("47.92.183.190,7780");//这是负载均衡的ip和端口
		
		sb.append(RadixUtil.changeRadix(add.toString()));
		sb.append("*");
		sb.append(add.toString());
		sb.append("]");
		logger.info("获取服务器ip端口返回"+sb.toString());
	
		return sb.toString();
	}
    @Override
	public SocketBaseDto process(JSONObject jsonObject, Channel incoming) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
