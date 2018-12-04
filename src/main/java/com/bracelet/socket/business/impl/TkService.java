package com.bracelet.socket.business.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.netty.channel.Channel;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.dto.TianQiLatest;
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
			if (jsonInfo.contains("YW*")) {
				String[] shuzu = jsonInfo.split("\\*");
				// imei = shuzu[1];// 设备imei
				String no = shuzu[2];// 流水号
				String info = shuzu[4];
				String[] infoshuzu = info.split(",");
				Integer status = Integer.valueOf(infoshuzu[1]); // :1—成功 0—失败
																// 2—空间已满无法继续接受

				String voiceName = infoshuzu[2];
				Integer thisNumber = Integer.valueOf(infoshuzu[3]);
				Integer allNumber = Integer.valueOf(infoshuzu[4]);

				if (status == 0) {
					logger.info("[voiceName]=" + voiceName);
					if (voiceName == null || "".equals(voiceName)) {
						voiceName = imei + "_" + new Date().getTime() + ".amr";
					}
					ChannelMap.addVoiceName(imei, voiceName);
					logger.info("从hashmap里获取的voiceName" + ChannelMap.getVoiceName(imei));
					String len = shuzu[3];// 流水号
					logger.info("十六进制转换后的长度=" + Integer.parseInt(len, 16));

					int intIndex = jsonInfo.indexOf("#!AMR");
					logger.info("#!AMR的位置=" + intIndex);
					logger.info("语音json总长度=" + jsonInfo.length());
					if (intIndex != -1) {
						logger.info("ARM格式二进制音频数据=" + jsonInfo.substring(intIndex, jsonInfo.length()));
						// byte[] voiceData =
						// Base64.decodeBase64(jsonInfo.substring(intIndex,
						// jsonInfo.length()));
						byte[] voiceData = jsonInfo.substring(intIndex, jsonInfo.length()).getBytes("UTF-8");
						logger.info("voiceName(status=0)=" + voiceName);
						Utils.createFileContent(Utils.VOICE_FILE_lINUX, voiceName, voiceData);
					} else {
						intIndex = jsonInfo.indexOf(".amr");
						logger.info("无!#AMR=" + jsonInfo.substring(intIndex + 9, jsonInfo.length()));
						//byte[] voiceData = Base64.decodeBase64(jsonInfo.substring(intIndex + 9, jsonInfo.length()));
						byte[] voiceData = jsonInfo.substring(intIndex+9, jsonInfo.length()).getBytes("UTF-8");
						Utils.createFileContent(Utils.VOICE_FILE_lINUX, voiceName, voiceData);
					}

					if (thisNumber == allNumber && allNumber != 0) {
						watchtkService.insertVoiceInfo(imei, "1", Utils.VOICE_URL + voiceName, "1", 0, "1", 1, 1);
					}
				} else {
					return "";
				}
			} else {
				String voiceName = ChannelMap.getVoiceName(imei);
				logger.info("voiceName=" + voiceName);
			//	byte[] voiceData = Base64.decodeBase64(jsonInfo);
				byte[] voiceData = jsonInfo.getBytes("UTF-8");
				Utils.createFileContent(Utils.VOICE_FILE_lINUX, voiceName, voiceData);
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		String resp = "[YW*" + imei + "*0001*0004*TK,1]";
		return resp;
	}

	@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto, JSONObject jsonObject, Channel channel) {
		return null;
	}

	public static void main(String[] args) {
		String a = "[YW*872018020142169*001F*042B*TPBK,18735662247,IMG20181205000600.jpg,1,6,MR<???g?F??E2";

		int intIndex = a.indexOf(".jpg");
		System.out.println(intIndex);
		System.out.println(a.substring(intIndex + 9, a.length()));
	}
}
