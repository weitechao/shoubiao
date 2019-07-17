package com.bracelet.socket.business.impl;

import io.netty.channel.Channel;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.BindSimCardInfo;
import com.bracelet.service.IVoltageService;
import com.bracelet.util.RadixUtil;

/**
 * 绑卡操作
 * 
 */
@Component("bindCardService")
public class BindCardService extends AbstractBizService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	IVoltageService voltageService;
	
	
	@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto,
			JSONObject jsonObject, Channel channel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo,
			Channel channel) {
		/*
		 *  [YW*861900039990378*0001*0033*BINDCARD,13256122653,123123] 
		 *  
		 *   服务器的逻辑是先查询这个sim卡的iccid
                             是否有被使用过，如果使用过。看是否跟imei匹配，
                             如果匹配就回复1  不匹配回复0  
                             如果  查询不到这个iccid ，就再查询imei。
                             如果有就检查这次的iccid是否跟imei匹配，不匹配就回复0，匹配就回复1   
                              如果都没查询到这说明是新设备第一次注册，回复1  
		 * */
		
		String[] shuzu = jsonInfo.split("\\*");
		String imei = shuzu[1];// 设备imei
		String info = shuzu[4];
		String[] infoshuzu = info.split(",");
		String iccid = infoshuzu[2];
		
		String resp = "[YW*"+imei+"*0001*000A*BINDCARD,";
		BindSimCardInfo bindIccid = voltageService.getSimCardInfoByIccid(iccid);
		if(bindIccid !=  null ){
			if(imei.equals(bindIccid.getImei())){
				resp = resp + "1"; 
			}else{
				resp = resp + "0"; 
			}
		}else{
			BindSimCardInfo bindImei = voltageService.getSimCardInfoByImei(imei);
			if(bindImei != null){
				if(iccid.equals(bindImei.getIccid())){
					resp = resp + "1"; 
				}else{
					resp = resp + "0"; 
				}
			}else{
				voltageService.insertBindSimCardInfo(imei,iccid);
				resp = resp + "1"; 
			}
		}
		
		logger.info("绑卡操作返回="+resp);
		return resp+"]";
		}
	 
public static void main(String[] args) {
	System.out.println(RadixUtil.changeRadix("BINDCARD,2,1"));
}
}
