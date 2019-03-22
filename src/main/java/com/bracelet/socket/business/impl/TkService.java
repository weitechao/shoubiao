package com.bracelet.socket.business.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.UserInfo;
import com.bracelet.entity.WatchDevice;
import com.bracelet.service.IPushlogService;
import com.bracelet.service.WatchTkService;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.IOSPushUtil;
import com.bracelet.util.AndroidPushUtil;
import com.bracelet.util.RadixUtil;
import com.bracelet.util.StringUtil;
import com.bracelet.util.Utils;

@Service("tkService")
public class TkService extends AbstractBizService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	WatchTkService watchtkService;

	@Autowired
	IPushlogService pushlogService;

	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo, Channel channel) {

		logger.info("语音接收:" + jsonInfo);
		String imei = socketLoginDto.getImei();

		String[] shuzu = jsonInfo.split("\\*");
		String info = shuzu[4];
		String[] infoshuzu = info.split(",");
		Integer status = Integer.valueOf(infoshuzu[1]); // :1—成功 0—失败

		String voiceName = infoshuzu[2];
		Integer thisNumber = Integer.valueOf(infoshuzu[3]);
		Integer allNumber = Integer.valueOf(infoshuzu[4]);

		if (status == 0) {

			logger.info("[voiceName]=" + voiceName);
			if (voiceName == null || "".equals(voiceName)) {
				voiceName = imei + "_" + System.currentTimeMillis() + ".amr";
			} else {
				voiceName = imei + "_" + voiceName;
			}
			int amr65 = jsonInfo.lastIndexOf(".amr");

			logger.info("amr的位置和65比较=" + amr65);
			if (amr65 == -1) {
				
				return "";
			}
			byte[] vocieByte = ChannelMap.getByte(channel.remoteAddress() + "_byte");

			byte[] voiceSubByte = Utils.subByte(vocieByte, 65, vocieByte.length - 65);

			Utils.createFileContent(Utils.VOICE_FILE_lINUX, voiceName, voiceSubByte);

			ChannelMap.removeAll(channel.remoteAddress() + "");

			if (thisNumber == allNumber && allNumber != 0) {
				// 如果这个语音已经全部传完。就置空voiceName 不置空 可能还会有遗留
				// ChannelMap.addVoiceName(imei, "");

				try {
					File source = new File(Utils.VOICE_FILE_lINUX + "/" + voiceName);
					int voiceLength = Utils.getAmrDuration(source);
					watchtkService.insertVoiceInfo(imei, "1", Utils.VOICE_URL + voiceName, "1", 0, "1", 1, 1,
							voiceLength);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("语音长度获取错误=" + e);
				}

				String token = limitCache.getRedisKeyValue(imei + "_push");
				if (!StringUtil.isEmpty(token)) {
					JSONObject push = new JSONObject();
					JSONArray jsonArray = new JSONArray();
					JSONObject dataMap = new JSONObject();
					dataMap.put("DeviceID", "");
					String deviceid = limitCache.getRedisKeyValue(imei + "_id");
					if (deviceid != null && !"0".equals(deviceid) && !"".equals(deviceid)) {
						dataMap.put("DeviceID", deviceid);
					} else {
						WatchDevice watchd = ideviceService.getDeviceInfo(imei);
						if (watchd != null) {
							deviceid = watchd.getId() + "";
							dataMap.put("DeviceID", watchd.getId());
							limitCache.addKey(imei + "_id", watchd.getId() + "");
						}
					}
					dataMap.put("Message", 0);
					dataMap.put("Voice", 1);
					dataMap.put("SMS", 0);
					dataMap.put("Photo", 0);
					jsonArray.add(dataMap);
					push.put("NewList", jsonArray);
					JSONArray jsonArray1 = new JSONArray();

					push.put("DeviceState", jsonArray1);

					JSONArray jsonArray2 = new JSONArray();
					JSONObject dataMap2 = new JSONObject();
					dataMap2.put("Type", 1);
					dataMap2.put("DeviceID", deviceid);
					dataMap2.put("Message", "新语音");
					dataMap2.put("imei", imei);
					jsonArray2.add(dataMap2);
					push.put("Notification", jsonArray2);
					
					// dataMap2.put("voiceUrl", Utils.VOICE_URL + voiceName);
					JSONArray jsonArrayVoice = new JSONArray();
					JSONObject dataMapVoice = new JSONObject();
					dataMapVoice.put("voiceUrl", "");
					dataMapVoice.put("DeviceVoiceId", ((int) ((Math.random() * 9 + 1) * 10000)) + "");
					// dataMapVoice.put("DeviceID", deviceid);
					dataMapVoice.put("DeviceID", deviceid);
					dataMapVoice.put("State", 1);
					dataMapVoice.put("Type", 3);
					dataMapVoice.put("MsgType", 0);
					dataMapVoice.put("ObjectId", deviceid);
					dataMapVoice.put("Mark", "");
					dataMapVoice.put("Path", Utils.VOICE_URL + voiceName);
					dataMapVoice.put("Length", 2);
					dataMapVoice.put("CreateTime", Utils.getLocationTime(System.currentTimeMillis()));
					dataMapVoice.put("UpdateTime", "");
					jsonArrayVoice.add(dataMapVoice);
					//dataMap2.put("VoiceList", jsonArrayVoice);
					
					push.put("VoiceList", jsonArrayVoice);
					push.put("Code", 1);
					push.put("New", 1);
					String targettime = Utils.getTime(System.currentTimeMillis());
					pushlogService.insertMsgInfo(imei, 1, deviceid, "新语音" + targettime, "新语音" + targettime);
					AndroidPushUtil.pushNotifyNotify(token, "新语音" + targettime, push.toString(), "新语音" + targettime);
					IOSPushUtil.pushNotifyNotify(token, "新语音" + targettime, push.toString(), "新语音" + targettime);
				}

			}
			return "[YW*" + imei + "*0001*0004*TK,1]";
		} else if(status == 1){
			logger.info("语音status=" + status);
			 byte[] voicebyte = ChannelMap.getAppVoiceByte(voiceName);//原始语音byte文件
			 
			 if((allNumber-thisNumber) == 0){
				 return "";
			 }else if((allNumber- thisNumber) != 1){
				 byte[] sendVoiceByte = Utils.subByte(voicebyte, thisNumber*1024, 1024);
					String msg = "TK,0," + voiceName + ","+ (thisNumber+1) +","+ allNumber +",";
					String reps = "[YW*" + imei + "*0003*" + RadixUtil.changeRadix(msg.length()+sendVoiceByte.length) +"*"+msg;
					logger.info(" TKservice 语音发送="+reps);
					channel.write(reps);
					channel.write(Unpooled.copiedBuffer(sendVoiceByte));
					String endSystembol = "]";
					channel.write(endSystembol);
					channel.flush();
			 }else if((allNumber- thisNumber) == 1){
				 //差一个说明是最后一个

				 byte[] sendVoiceByte = Utils.subByte(voicebyte, thisNumber*1024, voicebyte.length-thisNumber*1024);
					String msg = "TK,0," + voiceName + ","+ (thisNumber+1) +","+ allNumber +",";
					String reps = "[YW*" + imei + "*0003*" + RadixUtil.changeRadix(msg.length()+sendVoiceByte.length) +"*"+msg;
					logger.info(" TKservice 语音发送="+reps);
					channel.write(reps);
					channel.write(Unpooled.copiedBuffer(sendVoiceByte));
					String endSystembol = "]";
					channel.write(endSystembol);
					channel.flush();
					ChannelMap.removeVoiceByte(voiceName);
			 }else{
				 return "";
			 }
			
			 
		}else{
			logger.info("语音status=" + status);
		}

		/*
		 * } catch (UnsupportedEncodingException e) { e.printStackTrace(); }
		 */
		return "";
	}

	@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto, JSONObject jsonObject, Channel channel) {
		return null;
	}

}
