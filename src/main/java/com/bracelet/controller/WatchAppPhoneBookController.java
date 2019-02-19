package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.HttpBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.Location;
import com.bracelet.entity.LocationOld;
import com.bracelet.entity.LocationRequest;
import com.bracelet.entity.OldBindDevice;
import com.bracelet.entity.Step;
import com.bracelet.entity.UserInfo;
import com.bracelet.entity.VersionInfo;
import com.bracelet.entity.WatchDevice;
import com.bracelet.entity.WatchFriend;
import com.bracelet.entity.WatchPhoneBook;
import com.bracelet.exception.BizException;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IMemService;
import com.bracelet.service.IPushlogService;
import com.bracelet.service.IStepService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.service.WatchFriendService;
import com.bracelet.service.WatchSetService;
import com.bracelet.socket.BaseChannelHandler;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.HttpClientGet;
import com.bracelet.util.PushUtil;
import com.bracelet.util.RadixUtil;
import com.bracelet.util.RanomUtil;
import com.bracelet.util.RespCode;
import com.bracelet.util.StringUtil;
import com.bracelet.util.Utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/phonebook")
public class WatchAppPhoneBookController extends BaseController {

	@Autowired
	ILocationService locationService;
	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	IStepService stepService;
	@Autowired
	WatchSetService watchSetService;
	@Autowired
	WatchFriendService watchfriendService;
	@Autowired
	IMemService memberService;
	
	@Resource
	BaseChannelHandler baseChannelHandler;
	@Autowired
	IPushlogService pushlogService;
	private Logger logger = LoggerFactory.getLogger(getClass());

	/* 添加 */
	@ResponseBody
	@RequestMapping(value = "/addfriend", method = RequestMethod.POST)
	public String addfriend(@RequestBody String body) {
		JSONObject bb = new JSONObject();
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");
		String imei = jsonObject.getString("imei");
		String name = jsonObject.getString("name");// 角色
		String phone = jsonObject.getString("phone");// 号码
		String cornet = jsonObject.getString("cornet");// 短号
		String headType = jsonObject.getString("headtype");// 头像类型

		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}

		

		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);

		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("Code", 4);
			return bb.toString();
		}

		StringBuffer sb = new StringBuffer("[YW*" + imei + "*0001*");
		if (socketLoginDto.getChannel().isActive()) {
			
			WatchPhoneBook phoneBook = memberService.getPhoneBookByImeiAndPhone(imei, phone);
			
			if (phoneBook == null) {
				memberService.insertPhoneBookInfo(imei, name, phone, cornet, headType, 1);
				bb.put("DeviceContactId", memberService.getPhoneBookByImeiAndPhone(imei, phone).getId());
			}else{
				bb.put("DeviceContactId", phoneBook.getId());
			}
			
			StringBuffer sb1 = new StringBuffer("");
			StringBuffer sb2 = new StringBuffer("");

			List<WatchPhoneBook> watchbookList = memberService.getPhoneBookByImei(imei);

			if (watchbookList.size() > 0) {
				sb1.append(watchbookList.size());
				sb1.append(",");
				for (WatchPhoneBook WatchPhoneBook : watchbookList) {
					if (sb2.toString().isEmpty()) {
						sb2.append(WatchPhoneBook.getHeadtype());
						sb2.append("-");
						sb2.append(WatchPhoneBook.getName());
						sb2.append("-");
						sb2.append(WatchPhoneBook.getPhone());
						sb2.append("-");
						sb2.append(WatchPhoneBook.getCornet());
						sb2.append("-");
						sb2.append(WatchPhoneBook.getHeadtype());
						sb2.append("-");
						sb2.append("0000");
						sb2.append("-");
						sb2.append("");
					} else {
						sb2.append("|");
						sb2.append(WatchPhoneBook.getHeadtype());
						sb2.append("-");
						sb2.append(WatchPhoneBook.getName());
						sb2.append("-");
						sb2.append(WatchPhoneBook.getPhone());
						sb2.append("-");
						sb2.append(WatchPhoneBook.getCornet());
						sb2.append("-");
						sb2.append(WatchPhoneBook.getHeadtype());
						sb2.append("-");
						sb2.append("0000");
						sb2.append("-");
						sb2.append("");
					}
				}
			} else {
				sb1.append("0");
			}
			// PHB,1234, 001B*
			String msg = "PHB,1234," + sb1.toString()+sb2.toString();
			sb.append(RadixUtil.changeRadix(msg));
			sb.append("*");
			sb.append(msg);
			sb.append("]");
			logger.info("设备通讯录增加更新="+sb.toString());
			socketLoginDto.getChannel().writeAndFlush(sb.toString());
			bb.put("Code", 1);
			bb.put("Message", "通讯录添加成功");
			
			
			
			
				JSONObject push = new JSONObject();
				JSONArray jsonArray = new JSONArray();
				JSONObject dataMap = new JSONObject();
				dataMap.put("DeviceID", "");
				String deviceid = limitCache.getRedisKeyValue(imei + "_id");
				if( !StringUtil.isEmpty(deviceid)){
					dataMap.put("DeviceID", deviceid);
				}else{
					WatchDevice watchd = ideviceService.getDeviceInfo(imei);
					if (watchd != null) {
						deviceid=watchd.getId()+"";
						dataMap.put("DeviceID", watchd.getId());
						limitCache.addKey(imei + "_id", watchd.getId()+"");
					}
				}
				dataMap.put("Message", 1);
				dataMap.put("Voice", 0);
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
				dataMap2.put("Type", 7);
				dataMap2.put("DeviceID", deviceid);
				dataMap2.put("Message", "通讯录已同步");
				dataMap2.put("imei", imei);
				jsonArray2.add(dataMap2);
				push.put("Notification", jsonArray2);

				push.put("Code", 1);
				push.put("New", 1);
				
				pushlogService.insertMsgInfo(imei, 7, deviceid, "通讯录已同步", "通讯录已同步");
				
				PushUtil.push(token, "通讯录已同步", push.toString(), "通讯录已同步");	
						
		} else {
			bb.put("Code", 2);
			bb.put("Message", "");
		}

		return bb.toString();
	}

	/* 查询 */
	@ResponseBody
	@RequestMapping(value = "/getPhoneBookList/{token}/{imei}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String getPhoneBookList(@PathVariable String token, @PathVariable String imei) {
		JSONObject bb = new JSONObject();
		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}

		List<WatchPhoneBook> watchbookList = memberService.getPhoneBookByImei(imei);

		JSONArray jsonArray = new JSONArray();
		if (watchbookList.size() > 0 && watchbookList != null) {
			for (WatchPhoneBook location : watchbookList) {
				JSONObject dataMap = new JSONObject();
				dataMap.put("DeviceContactId", location.getId());
				dataMap.put("PhoneShort", location.getCornet()+"");
				dataMap.put("headtype", location.getHeadtype());
				dataMap.put("imei", location.getImei());
				dataMap.put("phone", location.getPhone());
				dataMap.put("Relationship", location.getName());
				dataMap.put("timestamp", location.getCreatetime().getTime());
				dataMap.put("ObjectId", 0);
				dataMap.put("Photo","123456");
				dataMap.put("PhoneNumber",location.getCornet()+"");
				dataMap.put("Type",1);
				dataMap.put("HeadImg",location.getHeadImg()+"");
				
				jsonArray.add(dataMap);
			}

			bb.put("Code", 1);
		} else {
			bb.put("Code", 0);
		}
		bb.put("ContactArr", jsonArray);

		return bb.toString();
	}

	/* 删除 通讯录 */
	@ResponseBody
	@RequestMapping(value = "/deletePhoneBook/{token}/{id}/{imei}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String selectfriend(@PathVariable String token, @PathVariable Long id, @PathVariable String imei) {
		JSONObject bb = new JSONObject();

		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		memberService.deletePhonebookById(id);

		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("Code", 4);
			return bb.toString();
		}

		StringBuffer sb = new StringBuffer("[YW*" + imei + "*0001*");
		if (socketLoginDto.getChannel().isActive()) {
			StringBuffer sb1 = new StringBuffer("");
			StringBuffer sb2 = new StringBuffer("");
			List<WatchPhoneBook> watchbookList = memberService.getPhoneBookByImei(imei);

			if (watchbookList.size() > 0) {
				sb1.append(watchbookList.size());
				sb1.append(",");
				for (WatchPhoneBook WatchPhoneBook : watchbookList) {
					if (sb2.toString().isEmpty()) {
						sb2.append(WatchPhoneBook.getHeadtype());
						sb2.append("-");
						sb2.append(WatchPhoneBook.getName());
						sb2.append("-");
						sb2.append(WatchPhoneBook.getPhone());
						sb2.append("-");
						sb2.append(WatchPhoneBook.getCornet());
						sb2.append("-");
						sb2.append(WatchPhoneBook.getHeadtype());
						sb2.append("-");
						sb2.append("0000");
						sb2.append("-");
						sb2.append("");
					} else {
						sb2.append("|");
						sb2.append(WatchPhoneBook.getHeadtype());
						sb2.append("-");
						sb2.append(WatchPhoneBook.getName());
						sb2.append("-");
						sb2.append(WatchPhoneBook.getPhone());
						sb2.append("-");
						sb2.append(WatchPhoneBook.getCornet());
						sb2.append("-");
						sb2.append(WatchPhoneBook.getHeadtype());
						sb2.append("-");
						sb2.append("0000");
						sb2.append("-");
						sb2.append("");
					}
				}
			} else {
				sb1.append("0");
			}
			// PHB,1234, 001B*
			String msg = "PHB,1234," + sb1.toString()+sb2.toString();
			sb.append(RadixUtil.changeRadix(msg));
			sb.append("*");
			sb.append(msg);
			sb.append("]");
			logger.info("设备通讯录删除更新="+sb.toString());
			socketLoginDto.getChannel().writeAndFlush(sb.toString());
			bb.put("Code", 1);
			
			
			
			JSONObject push = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			JSONObject dataMap = new JSONObject();
			dataMap.put("DeviceID", "");
			String deviceid = limitCache.getRedisKeyValue(imei + "_id");
			if( !StringUtil.isEmpty(deviceid)){
				dataMap.put("DeviceID", deviceid);
			}else{
				WatchDevice watchd = ideviceService.getDeviceInfo(imei);
				if (watchd != null) {
					deviceid=watchd.getId()+"";
					dataMap.put("DeviceID", watchd.getId());
					limitCache.addKey(imei + "_id", watchd.getId()+"");
				}
			}
			dataMap.put("Message", 1);
			dataMap.put("Voice", 0);
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
			dataMap2.put("Type", 7);
			dataMap2.put("DeviceID", deviceid);
			dataMap2.put("Message", "通讯录已同步");
			jsonArray2.add(dataMap2);
			push.put("Notification", jsonArray2);

			push.put("Code", 1);
			push.put("New", 1);
			pushlogService.insertMsgInfo(imei, 7, deviceid, "通讯录已同步", "通讯录已同步");
			PushUtil.push(token, "通讯录已同步", push.toString(), "通讯录已同步");	
			
		} else {
			bb.put("Code", 2);
		}

		return bb.toString();
	}

	/* 修改 */
	@ResponseBody
	@RequestMapping(value = "/updatePhoneBook", method = RequestMethod.POST)
	public String updatePhoneBook(@RequestBody String body) {
		JSONObject bb = new JSONObject();
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");
		Long id = Long.valueOf(jsonObject.getString("id"));
		String imei = jsonObject.getString("imei");
		String name = jsonObject.getString("name");// 角色
		String phone = jsonObject.getString("phone");// 号码
		String cornet = jsonObject.getString("cornet");// 短号
		String headType = jsonObject.getString("headtype");// 头像类型

		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}

		memberService.updatePhonebookById(id, name, phone, cornet, headType);
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("Code", 4);
			return bb.toString();
		}

		StringBuffer sb = new StringBuffer("[YW*" + imei + "*0001*");
		if (socketLoginDto.getChannel().isActive()) {
			StringBuffer sb1 = new StringBuffer("");
			StringBuffer sb2 = new StringBuffer("");

			List<WatchPhoneBook> watchbookList = memberService.getPhoneBookByImei(imei);

			if (watchbookList.size() > 0) {
				sb1.append(watchbookList.size());
				sb1.append(",");
				for (WatchPhoneBook WatchPhoneBook : watchbookList) {
					if (sb2.toString().isEmpty()) {
						sb2.append(WatchPhoneBook.getHeadtype());
						sb2.append("-");
						sb2.append(WatchPhoneBook.getName());
						sb2.append("-");
						sb2.append(WatchPhoneBook.getPhone());
						sb2.append("-");
						sb2.append(WatchPhoneBook.getCornet());
						sb2.append("-");
						sb2.append(WatchPhoneBook.getHeadtype());
						sb2.append("-");
						sb2.append("0000");
						sb2.append("-");
						sb2.append("");
					} else {
						sb2.append("|");
						sb2.append(WatchPhoneBook.getHeadtype());
						sb2.append("-");
						sb2.append(WatchPhoneBook.getName());
						sb2.append("-");
						sb2.append(WatchPhoneBook.getPhone());
						sb2.append("-");
						sb2.append(WatchPhoneBook.getCornet());
						sb2.append("-");
						sb2.append(WatchPhoneBook.getHeadtype());
						sb2.append("-");
						sb2.append("0000");
						sb2.append("-");
						sb2.append("");
					}
				}
			} else {
				sb1.append("0");
			}
			// PHB,1234, 001B*
			String msg = "PHB,1234," + sb1.toString()+sb2.toString();
			sb.append(RadixUtil.changeRadix(msg));
			sb.append("*");
			sb.append(msg);
			sb.append("]");
			logger.info("设备通讯录修改="+sb.toString());
			socketLoginDto.getChannel().writeAndFlush(sb.toString());
			bb.put("Code", 1);
			
			
			
			
			JSONObject push = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			JSONObject dataMap = new JSONObject();
			dataMap.put("DeviceID", "");
			String deviceid = limitCache.getRedisKeyValue(imei + "_id");
			if( !StringUtil.isEmpty(deviceid)){
				dataMap.put("DeviceID", deviceid);
			}else{
				WatchDevice watchd = ideviceService.getDeviceInfo(imei);
				if (watchd != null) {
					deviceid=watchd.getId()+"";
					dataMap.put("DeviceID", watchd.getId());
					limitCache.addKey(imei + "_id", watchd.getId()+"");
				}
			}
			dataMap.put("Message", 1);
			dataMap.put("Voice", 0);
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
			dataMap2.put("Type", 7);
			dataMap2.put("DeviceID", deviceid);
			dataMap2.put("Message", "通讯录已同步");
			jsonArray2.add(dataMap2);
			push.put("Notification", jsonArray2);

			push.put("Code", 1);
			push.put("New", 1);
			pushlogService.insertMsgInfo(imei, 7, deviceid, "通讯录已同步", "通讯录已同步");
			PushUtil.push(token, "通讯录已同步", push.toString(), "通讯录已同步");	
			
		} else {
			bb.put("Code", 2);
		}

		return bb.toString();
	}
	
	
	/* 修改通讯录头像 */
	@ResponseBody
	@RequestMapping(value = "/updateHeadImg", method = RequestMethod.POST)
	public String updateHeadImg(@RequestBody String body) {
		JSONObject bb = new JSONObject();
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");
		//loginId, deviceContactId, headImg
		Long deviceContactId = Long.valueOf(jsonObject.getString("deviceContactId"));
		Long loginId = Long.valueOf(jsonObject.getString("loginId"));
		String head = jsonObject.getString("headImg");// 角色
		

		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		
		byte[] headByte = Base64.decodeBase64(head);
		String photoName = deviceContactId + "_" + System.currentTimeMillis() + ".jpg";
		Utils.createFileContent(Utils.PHONEBook_FILE_lINUX, photoName, headByte);

		if(memberService.updatePhonebookHeadImgById(deviceContactId,Utils.PHONEBook_PHOTO_UTL+photoName)){
			bb.put("Code", 1);
		}else{
			bb.put("Code", 0);
		}
	
		return bb.toString();
	}

}
