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
import com.bracelet.entity.WatchVoiceInfo;
import com.bracelet.service.IDeviceService;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IVoltageService;
import com.bracelet.service.IinsertFriendService;
import com.bracelet.service.WatchTkService;
import com.bracelet.util.RadixUtil;
import com.bracelet.util.Utils;

@Service("tkqService")
public class TkqService extends AbstractBizService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	WatchTkService watchtkService;

	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo, Channel channel) {
		logger.info("终端请求录音下发:" + jsonInfo);
		String[] shuzu = jsonInfo.split("\\*");
		String imei = shuzu[1];// 设备imei
		String no = shuzu[2];// 流水号
		/*List<WatchVoiceInfo> wtalist = watchtkService.getVoiceListByImeiAndStatus(imei, 0);
		if (wtalist.size() > 0) {
			for (WatchVoiceInfo wta : wtalist) {

				String msg = "TK2," + imei + "," + wta.getSource_name() + "," + wta.getThis_number() + ","
						+ wta.getAll_number() + "," + wta.getVoice_content();
				String reps = "[YW*" + imei + "*0001*" + RadixUtil.changeRadix(msg) + "*" + msg + "]";
				channel.writeAndFlush(reps);
			}
		}*/
		return "";
	}

	@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto, JSONObject jsonObject, Channel channel) {
		return null;
	}

}
