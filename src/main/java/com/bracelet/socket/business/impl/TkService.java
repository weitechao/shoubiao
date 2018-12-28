package com.bracelet.socket.business.impl;

import java.util.Date;

import io.netty.channel.Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.WatchDevice;
import com.bracelet.service.WatchTkService;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.PushUtil;
import com.bracelet.util.StringUtil;
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
				voiceName = imei + "_" + new Date().getTime() + ".amr";
			} else {
				voiceName = imei + "_" + voiceName;
			}
			logger.info("amr的位置和65比较="+jsonInfo.lastIndexOf(".amr")+"");
			byte[] vocieByte = ChannelMap.getByte(channel.remoteAddress() + "_byte");

			byte[] voiceSubByte = Utils.subByte(vocieByte, 65, vocieByte.length - 65);

			Utils.createFileContent(Utils.VOICE_FILE_lINUX, voiceName, voiceSubByte);
			
			ChannelMap.removeAll(channel.remoteAddress()+"");
			
			if (thisNumber == allNumber && allNumber != 0) {
				// 如果这个语音已经全部传完。就置空voiceName 不置空 可能还会有遗留
				// ChannelMap.addVoiceName(imei, "");
				watchtkService.insertVoiceInfo(imei, "1", Utils.VOICE_URL + voiceName, "1", 0, "1", 1, 1);
				
				String token = limitCache.getRedisKeyValue(imei + "_push");
				if( !StringUtil.isEmpty(token)){
					JSONObject push = new JSONObject();
					JSONArray jsonArray = new JSONArray();
					JSONObject dataMap = new JSONObject();
					dataMap.put("DeviceID", "");
					String deviceid = limitCache.getRedisKeyValue(imei + "_id");
					if(deviceid !=null && !"0".equals(deviceid) && !"".equals(deviceid)){
						dataMap.put("DeviceID", deviceid);
					}else{
						WatchDevice watchd = ideviceService.getDeviceInfo(imei);
						if (watchd != null) {
							deviceid=watchd.getId()+"";
							dataMap.put("DeviceID", watchd.getId());
							limitCache.addKey(imei + "_id", watchd.getId()+"");
						}
					}
					dataMap.put("Message", 0);
					dataMap.put("Voice", 1);
					dataMap.put("SMS", 0);
					dataMap.put("Photo", 0);
					jsonArray.add(dataMap);
					push.put("NewList", jsonArray);
					JSONArray jsonArray1 = new JSONArray();
					JSONObject dataMap1 = new JSONObject();
					jsonArray1.add(dataMap1);
					push.put("DeviceState", jsonArray1);

					JSONArray jsonArray2 = new JSONArray();
					JSONObject dataMap2 = new JSONObject();
					dataMap2.put("Type", 1);
					dataMap2.put("DeviceID", deviceid);
					dataMap2.put("voiceUrl", Utils.VOICE_URL + voiceName);
					jsonArray2.add(dataMap2);
					push.put("Notification", jsonArray2);

					push.put("Code", 1);
					push.put("New", 1);
					PushUtil.push(token, "新语音", push.toString(), "新语音");	
				}
				
			}
			return "[YW*" + imei + "*0001*0004*TK,1]";
		} else {
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
