package com.bracelet.socket.business.impl;

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

	//	try {
			if (jsonInfo.contains("YW*")) {
				String[] shuzu = jsonInfo.split("\\*");
				// imei = shuzu[1];// 设备imei
				//String no = shuzu[2];// 流水号
				String info = shuzu[4];
				String[] infoshuzu = info.split(",");
				Integer status = Integer.valueOf(infoshuzu[1]); // :1—成功 0—失败
																// 2—空间已满无法继续接受

				String voiceName = infoshuzu[2];
				Integer thisNumber = Integer.valueOf(infoshuzu[3]);
				Integer allNumber = Integer.valueOf(infoshuzu[4]);
				
				  int insertNumber = 0;
		            if(allNumber>9 && thisNumber>9 ){
		            	insertNumber=2;
		            }else if(allNumber>9 && thisNumber<9 ){
		            	insertNumber=1;
		            }

				if (status == 0) {
					logger.info("[voiceName]=" + voiceName);
					if (voiceName == null || "".equals(voiceName)) {
						voiceName = imei + "_" + new Date().getTime() + ".amr";
					}else{
						voiceName =imei + "_"+ voiceName; 
					}
					ChannelMap.addVoiceName(imei, voiceName);
					logger.info("从hashmap里获取的voiceName" + ChannelMap.getVoiceName(imei));
					Integer len = Integer.parseInt(shuzu[3], 16);// 流水号
					logger.info("十六进制转换后的长度=" +len);
                    //test.substring(test.indexOf("TK"), test.indexOf("ARM")
					String qianbanduan= jsonInfo.substring(jsonInfo.indexOf("TK"), jsonInfo.indexOf("#!AMR"))+"#!AMR";
					logger.info("前半段="+qianbanduan);
					Integer voiceLen = len - qianbanduan.length();
					logger.info("语音总长度="+voiceLen);
					
					int intIndex = jsonInfo.indexOf("#!AMR");
					logger.info("#!AMR的位置=" + intIndex);
					logger.info("语音json总长度=" + jsonInfo.length());
					if (intIndex != -1) {
						logger.info("ARM格式二进制音频数据=" + jsonInfo.substring(intIndex+5, jsonInfo.length()));
						// byte[] voiceData =
						// Base64.decodeBase64(jsonInfo.substring(intIndex,
						// jsonInfo.length()));
						String voiceString = jsonInfo.substring(intIndex+5, jsonInfo.length());
						Integer voiceStringLen = voiceString.length();
						logger.info("直接获取到的语音长度="+voiceStringLen);
						if(voiceStringLen != voiceLen){
							voiceString+="]";
							voiceStringLen=voiceString.length();
							
						}
						logger.info("经过处理后的的语音长度="+voiceStringLen);
						Integer shengyuLen =   voiceLen-voiceStringLen;
						ChannelMap.addVoiceName(imei+"_voice_len", shengyuLen+"");
						logger.info("剩余的语音长度为="+shengyuLen);
						byte[] voiceData = voiceString.getBytes();
					
						Utils.createFileContent(Utils.VOICE_FILE_lINUX, voiceName, voiceData);
					} else {
						intIndex = jsonInfo.indexOf(".amr");
						String voiceString =jsonInfo.substring(intIndex + 9+insertNumber, jsonInfo.length());
						logger.info("无!#AMR=" + voiceString);
						
						 int voiceSLen = voiceString.length();
						 logger.info("无!#AMR语音长度="+voiceSLen);
							
						 Integer shengyL= Integer.valueOf(ChannelMap.getVoiceName(imei+"_voice_len"));
						 if(voiceSLen!=shengyL){
							 voiceString+="]";
							 voiceSLen=voiceString.length();
						 }
						 shengyL= shengyL-voiceSLen;
						 logger.info("无!#AMR 剩余长度="+shengyL);
						 ChannelMap.addVoiceName(imei+"_voice_len", shengyL+"");
                        logger.info("insertNumber="+insertNumber);
						//byte[] voiceData = Base64.decodeBase64(jsonInfo.substring(intIndex + 9, jsonInfo.length()));
						byte[] voiceData = jsonInfo.substring(intIndex+9+insertNumber, jsonInfo.length()).getBytes();
						Utils.createFileContent(Utils.VOICE_FILE_lINUX, voiceName, voiceData);
					}

					if (thisNumber == allNumber && allNumber != 0) {
						//如果这个语音已经全部传完。就置空voiceName  不置空  可能还会有遗留
						//ChannelMap.addVoiceName(imei, "");
						watchtkService.insertVoiceInfo(imei, "1", Utils.VOICE_URL + voiceName, "1", 0, "1", 1, 1);
					}
					return	"[YW*" + imei + "*0001*0004*TK,1]";
				} else {
					return "";
				}
			} else {
				String voiceName = ChannelMap.getVoiceName(imei);
				logger.info("voiceName=" + voiceName);
			//	byte[] voiceData = Base64.decodeBase64(jsonInfo);
				
				 Integer shengyL= Integer.valueOf(ChannelMap.getVoiceName(imei+"_voice_len"));
				 logger.info("只有语音部分的原始剩余长度="+shengyL);
				 if(jsonInfo.length()!=shengyL){
					 jsonInfo+="]";
					
				 }
				 shengyL= shengyL-jsonInfo.length();
				 logger.info("只有语音部分的处理后的剩余长度="+shengyL);
				 ChannelMap.addVoiceName(imei+"_voice_len", shengyL+"");
				 
				byte[] voiceData = jsonInfo.getBytes();
				Utils.createFileContent(Utils.VOICE_FILE_lINUX, voiceName, voiceData);
				
				return	"[YW*" + imei + "*0001*0004*TK,1]";
			}

		//} catch (UnsupportedEncodingException e) {
		//	e.printStackTrace();
	//	}

		/*
		 * if(status==1){
		 * watchtkService.updateStatusByNoAndImei(voiceNo,imei,2); }else
		 * if(status==0){ WatchVoiceInfo wta
		 * =watchtkService.getVoiceByNoAndImeiAndStatus(voiceNo,imei,1);
		 * StringBuffer sb = new StringBuffer("[YW*" + imei +
		 * "*NNNN*LEN*TK,"+voiceNo+","); sb.append(wta.getSender());
		 * sb.append(","); sb.append(wta.getSource_name()); sb.append(",");
		 * sb.append(1); sb.append(","); sb.append(1); sb.append(",");
		 * sb.append(wta.getVoice_content()); sb.append("]");
		 * resp=resp.toString(); }
		 */
			
			
	    /*String resp = "[YW*" + imei + "*0001*0004*TK,1]";
		return resp;*/
	}

	@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto, JSONObject jsonObject, Channel channel) {
		return null;
	}

	public static void main(String[] args) {
		String a = "[YW*872018020142169*001F*042B*TPBK,18735662247,IMG20181205000600.jpg#,!#AMR,1,6,MR<???g?F??E2";

		int intIndex = a.indexOf("!#AMR");
		System.out.println(intIndex);
		System.out.println(a.substring(intIndex+5 , a.length()));
		
		String test = "[YW*YYYYYYYYYY*NNNN*LEN*TK,来源,文件名字,当前包,总分包数,ARM";
		System.out.println(test.substring(test.indexOf("TK"), test.indexOf("ARM")));
	}
}
