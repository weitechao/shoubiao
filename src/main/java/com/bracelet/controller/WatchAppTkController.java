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
import com.bracelet.util.StringUtil;
import com.bracelet.util.Utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
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
	
	@Autowired
	IUserInfoService userInfoService;
	private Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * 对讲群聊 [YW*YYYYYYYYYY*NNNN*LEN*TK,来源,文件名字,当前包,总分包数,ARM格式二进制音频数据]
	 */
	@ResponseBody
	@RequestMapping(value = "/tkToDevice", method = RequestMethod.POST)
	public String tkToDevice(@RequestBody String body){
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		JSONObject bb = new JSONObject();
		String token = jsonObject.getString("token");

		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}

		String imei = jsonObject.getString("imei");
		
		
		
	     SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("Code", 2);
			return bb.toString();
		}
		
		
		        
		        

		if (socketLoginDto.getChannel().isActive()) {
			
			String phone = jsonObject.getString("phone");// 号码
			String voiceData = jsonObject.getString("voiceData");// 语音内容 base64转字符串
			String sourceName = jsonObject.getString("sourceName");// 文件名字
	        Integer voiceLength = jsonObject.getInteger("voiceLength");
	        if(StringUtil.isEmpty(voiceLength)){
	        	voiceLength = 2;
	        }
	        
			 byte[] voicebyte = Base64.decodeBase64(voiceData);
			 
			 
			Utils.createFileContent(Utils.VOICE_FILE_lINUX, "app_"+sourceName, voicebyte);
		
			
			JSONArray jsonArray = new JSONArray();
			String msgNumber =Utils.randomString(5);
			
			bb.put("Code", 1);
			
		   // WatchVoiceInfo watchAppVoice = watchtkService.getAppVoiceInfoByImeiAndStatus(imei, 1);
			
					JSONObject dataMap = new JSONObject();
					dataMap.put("voiceUrl", "");
					dataMap.put("DeviceVoiceId",((int)((Math.random()*9+1)*10000))+"");
					dataMap.put("DeviceID", 0);
					
					String deviceid = limitCache.getRedisKeyValue(imei + "_id");
					if(deviceid !=null && !"0".equals(deviceid) && !"".equals(deviceid)){
						dataMap.put("DeviceID", deviceid);
					}else{
						WatchDevice watchd = ideviceService.getDeviceInfo(imei);
						if (watchd != null) {
							dataMap.put("DeviceID", watchd.getId());
							limitCache.addKey(imei + "_id", watchd.getId()+"");
						}
					}
					
					dataMap.put("State", 1);
					dataMap.put("Type", 3);
					dataMap.put("MsgType", 0);
					dataMap.put("ObjectId", "0");
					if(StringUtils.isEmpty(limitCache.getRedisKeyValue(imei + "_userid"))){
						UserInfo userInfo = userInfoService.getUserInfoByUsername(imei);
						if(userInfo !=null ){
							dataMap.put("ObjectId", userInfo.getUser_id()+"");
							limitCache.addKey(imei + "_userid", userInfo.getUser_id() + "");
						}
						
					}else{
						
						dataMap.put("ObjectId", limitCache.getRedisKeyValue(imei + "_userid"));
					}
					dataMap.put("Mark", "");
					dataMap.put("Path",Utils.VOICE_URL + "app_"+sourceName);
					dataMap.put("Length", voiceLength);
					dataMap.put("CreateTime", "");
					dataMap.put("UpdateTime", "");
					jsonArray.add(dataMap);
			        bb.put("VoiceList", jsonArray);
			        
			        
			bb.put("Code", 1);
			logger.info("app发送语音byte长度为="+voicebyte.length);
			
			if(voicebyte.length>1024){
				int a = voicebyte.length/1024;
				byte[] sendVoiceByte = Utils.subByte(voicebyte, 0, 1024);
				String msg = "TK,0," + sourceName + ",1,"+(a+1)+",";
				String reps = "[YW*" + imei + "*0003*" + RadixUtil.changeRadix(msg.length()+sendVoiceByte.length) +"*"+msg;
				logger.info("app语音发送="+reps);
				socketLoginDto.getChannel().write(reps);
				socketLoginDto.getChannel().write(Unpooled.copiedBuffer(sendVoiceByte));
				String endSystembol = "]";
				socketLoginDto.getChannel().write(endSystembol);
				socketLoginDto.getChannel().flush();
				//将语音byte放在map里
				ChannelMap.addvoicebyte(sourceName, voicebyte);
			}else{
				String msg = "TK,0," + sourceName + ",1,1,";
				String reps = "[YW*" + imei + "*0003*" + RadixUtil.changeRadix(msg.length()+voicebyte.length) +"*"+msg;
				logger.info("app语音发送="+reps);
				socketLoginDto.getChannel().write(reps);
				socketLoginDto.getChannel().write(Unpooled.copiedBuffer(voicebyte));
				String endSystembol = "]";
				socketLoginDto.getChannel().write(endSystembol);
				socketLoginDto.getChannel().flush();
			}
			
				
				
				//socketLoginDto.getChannel().writeAndFlush(reps);
				//byte[] voiceSubByte = Utils.subByte(voicebyte, 0, 1024);
				//socketLoginDto.getChannel().writeAndFlush(Unpooled.copiedBuffer(voiceSubByte));
				//socketLoginDto.getChannel().writeAndFlush(Unpooled.copiedBuffer(voicebyte));
				//socketLoginDto.getChannel().writeAndFlush("]");
				
			
			//socketLoginDto.getChannel().writeAndFlush(Unpooled.copiedBuffer(voicebyte));
		/*	try {
				String voicebyteUtf8 = new String(voicebyte,"UTF-8");
				int a = voicebyteUtf8.length()/1024;
				for(int i=0;i<a+1;i++){
					if(i !=a){
						if(i==0){
							String sendVoice = voicebyteUtf8.substring(1024*i,(i+1)*1024);
							
							String msg = "TK,0," + sourceName + ","+(i+1) + ","+(a+1)+","+sendVoice;
							String reps = "[YW*" + imei + "*0003*" + RadixUtil.changeRadix(msg) +"*"+ msg + "]";
							logger.info("app语音发送="+reps);
							socketLoginDto.getChannel().writeAndFlush(reps);
						}
						
						
					}else{
						String sendVoice = voicebyteUtf8.substring(1024*i,voicebyteUtf8.length());
						
						String msg = "TK," + phone + "," + sourceName + ","+(i+1) + ","+(a+1)+","+sendVoice;
						String reps = "[YW*" + imei + "*0003*" + RadixUtil.changeRadix(msg) +"*"+ msg + "]";
						logger.info("app语音发送="+reps);
						socketLoginDto.getChannel().writeAndFlush(reps);
					}
				}
			
				
			
			watchtkService.insertAppVoiceInfo("app", imei, sourceName, Utils.VOICE_URL + "app_"+sourceName, 1, msgNumber, 1, 1,voiceLength);
			} catch (UnsupportedEncodingException e) {
			
				e.printStackTrace();
			}*/
			
		} else {
			//watchtkService.insertAppVoiceInfo("app", imei, sourceName, Utils.VOICE_URL + "app_"+sourceName, 0, msgNumber, 1, 1,voiceLength);
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
		
			for (DownLoadFileInfo fileInfo : list) {
				JSONObject dataMap = new JSONObject();
				dataMap.put("photoUrl", fileInfo.getSource());
				dataMap.put("createtime", fileInfo.getCreatetime().getTime());
				dataMap.put("photoName", fileInfo.getPhoto_name());
				dataMap.put("DevicePhotoId", fileInfo.getId());
				dataMap.put("DeviceID", "");
				String deviceid = limitCache.getRedisKeyValue(imei + "_id");
				if(deviceid !=null && !"0".equals(deviceid) && !"".equals(deviceid)){
					dataMap.put("DeviceID", deviceid);
				}else{
					WatchDevice watchd = ideviceService.getDeviceInfo(imei);
					if (watchd != null) {
						dataMap.put("DeviceID", watchd.getId());
						limitCache.addKey(imei + "_id", watchd.getId()+"");
					}
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
				if(!StringUtil.isEmpty(deviceid) && !"0".equals(deviceid)){
					dataMap.put("DeviceID", deviceid);
				}else{
					WatchDevice watchd = ideviceService.getDeviceInfo(imei);
					if (watchd != null) {
						dataMap.put("DeviceID", watchd.getId());
						limitCache.addKey(imei + "_id", watchd.getId()+"");
					}
				}
				
				dataMap.put("State", 1);
				dataMap.put("Type", 3);
				dataMap.put("MsgType", 0);
				dataMap.put("ObjectId", "");
				dataMap.put("Mark", "");
				dataMap.put("Path", WatchVoiceInfo.getSource_name()+"");
				dataMap.put("Length", WatchVoiceInfo.getVoice_length());
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
	
	
	
	@ResponseBody
	@RequestMapping(value = "/testTkToDevice/{imei}", method = RequestMethod.GET)
	public String testTkToDevice(@PathVariable String imei){
		JSONObject bb = new JSONObject();
	     SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("Code", 4);
			return bb.toString();
		}
		 byte[] voicebyte = Utils.getAmrByte("/usr/local/resin/resin-pro-4.0.53-8080/webapps/GXCareDevice/watchvoice/device/aaa_test.amr"); 
		if (socketLoginDto.getChannel().isActive()) {
			socketLoginDto.getChannel().writeAndFlush(Unpooled.copiedBuffer(voicebyte));
			
			
			
			bb.put("Code", 1);
		/*	try {
				String voicebyteUtf8 = new String(voicebyte,"UTF-8");
				int a = voicebyteUtf8.length()/1024;
				for(int i=0;i<a+1;i++){
					if(i !=a){
						if(i==0){
							String sendVoice = voicebyteUtf8.substring(1024*i,(i+1)*1024);
							
							String msg = "TK,0," + "test.amr" + ","+(i+1) + ","+(a+1)+","+sendVoice;
							String reps = "[YW*" + imei + "*0003*" + RadixUtil.changeRadix(msg) +"*"+ msg + "]";
							logger.info("app语音发送="+reps);
							socketLoginDto.getChannel().writeAndFlush(reps);
						}
					}else{
						String sendVoice = voicebyteUtf8.substring(1024*i,voicebyteUtf8.length());
						
						String msg = "TK," + "0" + "," + "test.amr" + ","+(i+1) + ","+(a+1)+","+sendVoice;
						String reps = "[YW*" + imei + "*0003*" + RadixUtil.changeRadix(msg) +"*"+ msg + "]";
						//logger.info("app语音发送="+reps);
						//socketLoginDto.getChannel().writeAndFlush(reps);
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}*/
			bb.put("Code", 1);
		} else {
			bb.put("Code", 0);
		}
		return bb.toString();
	}
	

}
