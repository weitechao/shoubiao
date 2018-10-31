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
import com.bracelet.util.ChannelMap;
import com.bracelet.util.Utils;

@Service("tk2Service")
public class Tk2Service extends AbstractBizService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	WatchTkService watchtkService;

	/*
	 * [YW*YYYYYYYYYY*NNNN*LEN*TK2,ZZZZZZZZZZ,文件名字,当前包,总分包数,ARM格式二进制音频数据]
	 * ZZZZZZZZZZ：要送达的好友的设备号，该设备需已在平台上注册过
	 */
	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo,
			Channel channel) {
		logger.info("好友微聊:" + jsonInfo);
		String[] shuzu = jsonInfo.split("\\*");
		String imei = shuzu[1];// 设备imei
		String no = shuzu[2];// 流水号
		String info = shuzu[4];
		String[] infoshuzu = info.split(",");
		String receiver = infoshuzu[1];
		String sourceName = infoshuzu[2];
		Integer thisNumber = Integer.valueOf(infoshuzu[3]);
		Integer allNumber = Integer.valueOf(infoshuzu[4]);
		String voiceContent = infoshuzu[5];
		// 发送语音格式[YW*YYYYYYYYYY*NNNN*LEN*TK,来源,文件名字,当前包,总分包数,ARM格式二进制音频数据]
		SocketLoginDto socketLoginDtoto = ChannelMap.getChannel(receiver);
		String noo = Utils.randomString(5);
		if (socketLoginDtoto == null || socketLoginDtoto.getChannel() == null) {
			watchtkService.insertVoiceInfo(imei, receiver, sourceName,
					voiceContent, 0, noo);
			return "";
		}
		if (socketLoginDtoto.getChannel().isActive()) {
			watchtkService.insertVoiceInfo(imei, receiver, sourceName,
					voiceContent, 1, noo);
			StringBuffer sb = new StringBuffer("[YW");
			sb.append("imei").append("*NNNN*LEN*TK,").append(imei)
					.append(sourceName).append(",").append(thisNumber)
					.append(",").append(allNumber).append(",")
					.append(voiceContent);
			socketLoginDtoto.getChannel().writeAndFlush(sb.toString());
		}
		return "[YW*" + imei + "*NNNN*LEN*TK2,1] ";
	}

	@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto,
			JSONObject jsonObject, Channel channel) {
		return null;
	}

}
