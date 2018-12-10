package com.bracelet.socket.business.impl;

import io.netty.channel.Channel;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.exception.BizException;
import com.bracelet.service.IApilogService;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.RespCode;
import com.bracelet.util.Utils;
import com.bracelet.socket.business.IBusinessHandler;
import com.bracelet.socket.business.IService;

@Component
public class BusinessHandler implements IBusinessHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	SocketBusinessFactory socketBusinessFactory;
	@Autowired
	IApilogService apilogService;

	public void process(String json, Channel incoming) {

		IService service = null;
		int type = 0;
		//int a = 0;
		String no = null;
		String reponse = null;
		SocketBaseDto dto = null;
		logger.info(json);
		long startTime = System.currentTimeMillis();
		String serviceName = "";
	//	String resp = "";
		int rstatus = 0;
		String rmsg = "";
		String imei = "";
		String cmd = null;
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(incoming);
		if (socketLoginDto != null) {
			imei = socketLoginDto.getImei();
		}
		try {
			json = Utils.hexStringToString(json);
			logger.info("[" + incoming.remoteAddress() + "]原始发送信息字符串:" + json + "]");
			
				String jsonInfo = json.substring(1, json.length());
				String[] shuzu = jsonInfo.split("\\*");
				// String deviceid=shuzu[1];
				String info = shuzu[4];
				cmd = info.split(",")[0];
				service = socketBusinessFactory.getService(cmd);
				serviceName = service.getClass().getName();
				reponse = service.process(jsonInfo, incoming);
			
		} catch (Exception e) {
			logger.error("process error:", e);
			rstatus = 1;
			rmsg = e.getMessage();
			dto = new SocketBaseDto();
			dto.setMessage(e.getMessage());
			dto.setTimestamp(new Date().getTime() / 1000);
			dto.setNo(no);
			dto.setType(type);
			dto.setStatus(RespCode.SYS_ERR.getCode());
			dto.setMessage("System error");
			if (e instanceof BizException) {
				dto.setStatus(((BizException) e).getCode());
				dto.setMessage(e.getMessage());
			}
			if (e instanceof JSONException) {
				dto.setStatus(RespCode.ERR_PARAM.getCode());
				dto.setMessage("Params error.Not json type");
			}
		}
		long time = System.currentTimeMillis() - startTime;
		/*
		 * String responseJson = JSON.toJSONString(dto); if (a == 0) { //
		 * 如果a是1，表示应答，则无需返回结果 incoming.writeAndFlush(responseJson + "\r\n"); }
		 */
        logger.info("cmd:"+cmd+"=返回:"+reponse);
		incoming.writeAndFlush(reponse);
         if(!"LK".equals(cmd)){
        	 apilogService.insert(serviceName, json, reponse, imei, rstatus, rmsg,time);
         }
	}

}
