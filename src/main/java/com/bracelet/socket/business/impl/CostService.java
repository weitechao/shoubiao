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
import com.bracelet.service.ISmslogService;
import com.bracelet.service.IVoltageService;
import com.bracelet.socket.business.IService;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.RespCode;
import com.bracelet.util.Utils;

/**
 * (是在短信查询-COST1的基础上,进行回复) 终端发送:
 * [YW*YYYYYYYYYY*NNNN*LEN*COST2,发送短信的号码,终端所获取到的短信查询结果]
 * 实例:[YW*8800000015*0001*0087* COST2,10086,相应的短信内容] 平台回复:
 * [YW*YYYYYYYYYY*NNNN*LEN*COST2,接收结果] 实例:[YW*8800000015*0001*0002*COST2,1]
 * 接收结果：1-成功，0-失败
 * 
 */
@Component("costService")
public class CostService extends AbstractBizService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ISmslogService smsLogService;

	@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto,
			JSONObject jsonObject, Channel channel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo,
			Channel channel) {

		String[] shuzu = jsonInfo.split("\\*");
		String imei = shuzu[1];// 设备imei
		String info = shuzu[4];
		String[] infoshuzu = info.split(",");
		String cmd = infoshuzu[0];
		logger.info("短信回复=" + jsonInfo);
		smsLogService.insertWatch(imei, cmd, infoshuzu[2]);
		String resp = null;
		if ("COST1".equals(cmd)) {
			resp = "[YW*"+imei+"*0001*0002*COST1,1]";
		} else {
			resp = "[YW*"+imei+"*0001*0002*COST2,1]";
		}
		return resp;
	}

}
