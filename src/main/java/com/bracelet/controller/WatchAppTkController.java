package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.HttpBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.DownLoadFileInfo;
import com.bracelet.entity.Location;
import com.bracelet.entity.LocationOld;
import com.bracelet.entity.LocationRequest;
import com.bracelet.entity.LocationWatch;
import com.bracelet.entity.OldBindDevice;
import com.bracelet.entity.Step;
import com.bracelet.entity.UserInfo;
import com.bracelet.entity.VersionInfo;
import com.bracelet.entity.WatchDevice;
import com.bracelet.entity.WatchFriend;
import com.bracelet.entity.WatchVoiceInfo;
import com.bracelet.exception.BizException;
import com.bracelet.service.IDeviceService;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IStepService;
import com.bracelet.service.IUploadPhotoService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.service.WatchFriendService;
import com.bracelet.service.WatchSetService;
import com.bracelet.service.WatchTkService;
import com.bracelet.socket.BaseChannelHandler;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.HttpClientGet;
import com.bracelet.util.RadixUtil;
import com.bracelet.util.RanomUtil;
import com.bracelet.util.RespCode;
import com.bracelet.util.Utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tk")
public class WatchAppTkController extends BaseController {
	@Autowired
	WatchTkService watchtkService;
	@Autowired
	IUploadPhotoService iUploadPhotoService;
	@Autowired
	IDeviceService ideviceService;
	private Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * 对讲群聊 [YW*YYYYYYYYYY*NNNN*LEN*TK,来源,文件名字,当前包,总分包数,ARM格式二进制音频数据]
	 */
	@ResponseBody
	@RequestMapping(value = "/tkToDevice", method = RequestMethod.POST)
	public String tkToDevice(@RequestBody String body) throws UnsupportedEncodingException {
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		JSONObject bb = new JSONObject();
		String token = jsonObject.getString("token");

		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}

		String imei = jsonObject.getString("imei");
		String phone = jsonObject.getString("phone");// 号码
		String voiceData = jsonObject.getString("voiceData");// 语音内容 base64转字符串
		String sourceName = jsonObject.getString("sourceName");// 文件名字

		 byte[] voicebyte = Base64.decodeBase64(voiceData);
		// insertVoiceInfo(String sender, String receiver, String
		// sourceName,String voiceData, Integer status,String numMessage);
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		String numMessage = Utils.randomString(5);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			watchtkService.insertAppVoiceInfo(phone, imei, sourceName, voiceData, 0, numMessage, 1, 1);
			bb.put("Code", 2);
			return bb.toString();
		}

		if (socketLoginDto.getChannel().isActive()) {
			bb.put("Code", 1);
			if(voicebyte.length>1024){
				Integer zheng = voicebyte.length/1024;
				Integer shengyu = voicebyte.length%1024;
				if(shengyu>0){
					zheng+=1;
				}
				for(int i =0;i<zheng;i++){
					if(i-zheng != -1){
						String msg = "TK," + phone + "," + sourceName + ","+ (i+1) +","+zheng+",";
						String reps = "[YW*" + imei + "*0003*" + RadixUtil.changeRadix(msg.length()+1024) + "*";
						socketLoginDto.getChannel().writeAndFlush(reps);
						byte[] voiceSubByte = Utils.subByte(voicebyte, 1024*i, 1024*(i+1));
						socketLoginDto.getChannel().writeAndFlush(voiceSubByte);
					}else{
						String msg = "TK," + phone + "," + sourceName + ","+ (i+1) +","+zheng+",";
						String reps = "[YW*" + imei + "*0003*" + RadixUtil.changeRadix(msg.length()+(voicebyte.length-i*1024)) + "*";
						socketLoginDto.getChannel().writeAndFlush(reps);
						byte[] voiceSubByte = Utils.subByte(voicebyte, 1024*i, voicebyte.length-i*1024);
						socketLoginDto.getChannel().writeAndFlush(voiceSubByte);
					}
				}
			}else{
				String msg = "TK," + phone + "," + sourceName + ","+ 1 +","+1+",";
				String reps = "[YW*" + imei + "*0003*" + RadixUtil.changeRadix(msg.length()+voicebyte.length) + "*";
				socketLoginDto.getChannel().writeAndFlush(reps);
				socketLoginDto.getChannel().writeAndFlush(voicebyte);
			}
			
			// 因为这里我觉得下发的时候需要增加一个消息号，设备再回复的时候，。把消息号带上，这个消息号永远唯一，消息号我随机生成
			
			// byte[] voiceDatat = Base64.decodeBase64(voiceData);
			// socketLoginDto.getChannel().writeAndFlush(voiceDatat); 调试有可能是这种

			watchtkService.insertAppVoiceInfo(phone, imei, sourceName, voiceData, 1, numMessage, 1, 1);
		} else {
			watchtkService.insertAppVoiceInfo(phone, imei, sourceName, voiceData, 0, numMessage, 1, 1);
			bb.put("Code", 0);
		}
		return bb.toString();
	}

	/* 获取 */
	@ResponseBody
	@RequestMapping(value = "/getDevicePhoto/{token}/{imei}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String getDeviceTk(@PathVariable String token, @PathVariable String imei) {

		JSONObject bb = new JSONObject();

		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}

		List<DownLoadFileInfo>list = iUploadPhotoService.getphotoInfo(imei,0);
		JSONArray jsonArray = new JSONArray();
		if (list != null) {
			SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
			for (DownLoadFileInfo fileInfo : list) {
				JSONObject dataMap = new JSONObject();
				dataMap.put("photoUrl", fileInfo.getSource());
				dataMap.put("createtime", fileInfo.getCreatetime().getTime());
				dataMap.put("photoName", fileInfo.getPhoto_name());
				dataMap.put("DevicePhotoId", fileInfo.getId());
				dataMap.put("DeviceID", "");
				if(socketLoginDto == null || socketLoginDto.getChannel() == null){
					WatchDevice watchd = ideviceService.getDeviceInfo(imei);
					dataMap.put("DeviceID",watchd.getId());
				}else{
					dataMap.put("DeviceID", socketLoginDto.getUser_id());
				}
				dataMap.put("Source", "");
				dataMap.put("DeviceTime", "");
				dataMap.put("Latitude", "");
				dataMap.put("Longitude", "");
				dataMap.put("Mark", "");
				dataMap.put("Path", fileInfo.getSource());
				dataMap.put("Thumb", "");
				dataMap.put("CreateTime", "");
				dataMap.put("UpdateTime", "");
				jsonArray.add(dataMap);
				iUploadPhotoService.updateStatusById(fileInfo.getId(), 1);
			}
			bb.put("Code", 1);
		}else{
			bb.put("Code", 0);
		}
		bb.put("List", jsonArray);

		return bb.toString();
	}
	
	
	/* 获取语音列表 */
	@ResponseBody
	@RequestMapping(value = "/getDeviceVoice/{token}/{imei}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String getVoice(@PathVariable String token, @PathVariable String imei) {

		JSONObject bb = new JSONObject();

		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}

		List<WatchVoiceInfo> list = watchtkService.getVoiceListByImeiAndStatus(imei, 0);
		JSONArray jsonArray = new JSONArray();
		if (list != null) {
			for (WatchVoiceInfo WatchVoiceInfo : list) {
				JSONObject dataMap = new JSONObject();
				dataMap.put("voiceUrl", "");
				dataMap.put("createtime", WatchVoiceInfo.getCreatetime().getTime());
				dataMap.put("DeviceVoiceId", WatchVoiceInfo.getId());
				dataMap.put("DeviceID", 0);
				
				String deviceid = limitCache.getRedisKeyValue(imei + "_id");
				if(deviceid !=null && !"0".equals(deviceid) && !"".equals(deviceid)){
					bb.put("DeviceID", deviceid);
				}else{
					WatchDevice watchd = ideviceService.getDeviceInfo(imei);
					if (watchd != null) {
						bb.put("DeviceID", watchd.getId());
						limitCache.addKey(imei + "_id", watchd.getId()+"");
					}
				}
				
				dataMap.put("State", 1);
				dataMap.put("Type", 3);
				dataMap.put("MsgType", 0);
				dataMap.put("ObjectId", "");
				dataMap.put("Mark", "");
				dataMap.put("Path", WatchVoiceInfo.getSource_name());
				dataMap.put("Length", 0);
				dataMap.put("CreateTime", "");
				dataMap.put("UpdateTime", "");
				jsonArray.add(dataMap);
				watchtkService.updateStatusById(WatchVoiceInfo.getId(), 1);
			}
			bb.put("Code", 1);
		}else{
			bb.put("Code", 0);
		}
		bb.put("VoiceList", jsonArray);
		return bb.toString();
	}
	

}
