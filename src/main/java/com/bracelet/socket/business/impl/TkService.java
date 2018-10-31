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

@Service("tkService")
public class TkService extends AbstractBizService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	WatchTkService watchtkService;

	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo,
			Channel channel) {
		logger.info("语音接收终端回复:" + jsonInfo);
		String[] shuzu = jsonInfo.split("\\*");
		String imei = shuzu[1];// 设备imei
		String no = shuzu[2];// 流水号
		String info = shuzu[4];
		String[] infoshuzu = info.split(",");
		Integer status = Integer.valueOf(infoshuzu[1]);  //:1—成功  0—失败  2—空间已满无法继续接受
		String  voiceNo = infoshuzu[2];  //语音消息号
	    String resp = null; 
		if(status==1){
	    	watchtkService.updateStatusByNoAndImei(voiceNo,imei,2);
	    }else if(status==0){
	    	WatchVoiceInfo wta =watchtkService.getVoiceByNoAndImeiAndStatus(voiceNo,imei,1);
	    	StringBuffer sb = new StringBuffer("[YW*" + imei
					+ "*NNNN*LEN*TK,"+voiceNo+",");
				sb.append(wta.getSender());
				sb.append(",");
				sb.append(wta.getSource_name());
				sb.append(",");
				sb.append(1);
				sb.append(",");
				sb.append(1);
				sb.append(",");
				sb.append(wta.getVoice_content());
				sb.append("]");
				resp=resp.toString();
	    }
		return resp;
	}

	@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto,
			JSONObject jsonObject, Channel channel) {
		return null;
	}

}
