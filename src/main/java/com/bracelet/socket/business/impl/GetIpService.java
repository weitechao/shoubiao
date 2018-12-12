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
import com.bracelet.redis.LimitCache;
import com.bracelet.service.IDeviceService;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IVoltageService;
import com.bracelet.service.IinsertFriendService;
import com.bracelet.socket.business.IService;
import com.bracelet.util.RadixUtil;
import com.bracelet.util.Utils;

@Service("getIpService")
public class GetIpService implements IService {
	private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
	IDeviceService ideviceService;
    @Autowired
	LimitCache limitCache;
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
		Integer im = Integer.valueOf(imei.substring(imei.length()-3, imei.length()));
		im = im % 3;
		StringBuffer sb = new StringBuffer("[YW*" + imei + "*0001*");//0002*
        StringBuffer add=new StringBuffer("IPREQ,");
		add.append(1);
		add.append(",");
		String ip = null;
		String port = null;
		
		if(im == 0){
			ip = "47.92.30.81";
			port = "7780";
			add.append(ip);
			add.append(",");
			add.append(port);
		}else if(im == 1){
			ip = "47.92.30.81";
			port = "7780";
			add.append(ip);
			add.append(",");
			add.append(port);
		}else if(im == 2){
			ip = "47.92.30.81";
			port = "7780";
			add.append(ip);
			add.append(",");
			add.append(port);
		}
		
		sb.append(RadixUtil.changeRadix(add.toString()));
		sb.append("*");
		sb.append(add.toString());
		sb.append("]");
		logger.info("获取服务器ip端口返回"+sb.toString());
		String ipt =  ip+":"+port;
		logger.info(imei+"计算imei最后三位="+im+"|分配的ip port="+ipt);
		limitCache.addKey(imei,ipt);
		return sb.toString();
	}
    @Override
	public SocketBaseDto process(JSONObject jsonObject, Channel incoming) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
