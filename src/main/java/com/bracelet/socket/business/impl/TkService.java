package com.bracelet.socket.business.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import io.netty.channel.Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;

import com.bracelet.service.WatchTkService;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.Utils;

@Service("tkService")
public class TkService extends AbstractBizService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	WatchTkService watchtkService;

	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo, Channel channel) {

		logger.info("语音接收:" + jsonInfo);
		String imei = socketLoginDto.getImei();
		logger.info("从socketLoginDto里取出来的imei=" + imei);

		try {
			String[] shuzu = jsonInfo.split("\\*");
			String info = shuzu[4];
			String[] infoshuzu = info.split(",");
			Integer status = Integer.valueOf(infoshuzu[1]); // :1—成功 0—失败
															// 2—空间已满无法继续接受

			String voiceName = infoshuzu[2];
			Integer thisNumber = Integer.valueOf(infoshuzu[3]);
			Integer allNumber = Integer.valueOf(infoshuzu[4]);

			if (status == 0) {
				
				 int insertNumber = 0;
		            if(allNumber>9 && thisNumber>9 ){
		            	insertNumber=2;
		            }else if(allNumber>9 && thisNumber<9 ){
		            	insertNumber=1;
		            }
		            
				logger.info("[voiceName]=" + voiceName);
				if (voiceName == null || "".equals(voiceName)) {
					voiceName = imei + "_" + new Date().getTime() + ".amr";
				} else {
					voiceName = imei + "_" + voiceName;
				}
				ChannelMap.addVoiceName(imei, voiceName);
				 Integer indexAmr = jsonInfo.indexOf("#!AMR");
				 if(indexAmr != -1 ){
					 String voiceString = jsonInfo.substring(indexAmr+insertNumber, jsonInfo.length());
						logger.info(voiceName + "的有!#AMR语音文本=" + voiceString);
						byte[] voiceData = voiceString.getBytes("UTF-8");
						Utils.createFileContent(Utils.VOICE_FILE_lINUX, voiceName, voiceData);
				 }else{
					 indexAmr = jsonInfo.indexOf(".amr");
					 String voiceString =jsonInfo.substring(indexAmr + 5 + insertNumber, jsonInfo.length());
						logger.info(voiceName + "的无!#AMR语音文本=" + voiceString);
						byte[] voiceData = voiceString.getBytes("UTF-8");
						Utils.createFileContent(Utils.VOICE_FILE_lINUX, voiceName, voiceData);
				 }
				

				if (thisNumber == allNumber && allNumber != 0) {
					// 如果这个语音已经全部传完。就置空voiceName 不置空 可能还会有遗留
					// ChannelMap.addVoiceName(imei, "");
					watchtkService.insertVoiceInfo(imei, "1", Utils.VOICE_URL + voiceName, "1", 0, "1", 1, 1);
				}
				return "[YW*" + imei + "*0001*0004*TK,1]";
			} else {
				logger.info("语音status=" + status);
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto, JSONObject jsonObject, Channel channel) {
		return null;
	}

	public static void main(String[] args) {
		
		
		String a = "[YW*872018020142169*001F*042B*TPBK,18735662247,IMG20181205000600.jpg#,.amr,1,6";

		int intIndex = a.indexOf(".amr");
		System.out.println(intIndex);
		System.out.println(a.substring(intIndex+5, a.length()));
		System.out.println(a.substring(intIndex+4, a.length()));

		String test = "[YW*YYYYYYYYYY*NNNN*LEN*TK,来源,文件名字,当前包,总分包数,ARM";
		System.out.println(test.substring(test.indexOf("TK"), test.indexOf("ARM")));
	}
}
