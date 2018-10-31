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
import com.bracelet.entity.WatchFriend;
import com.bracelet.exception.BizException;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IStepService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.service.WatchFriendService;
import com.bracelet.service.WatchSetService;
import com.bracelet.socket.BaseChannelHandler;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.HttpClientGet;
import com.bracelet.util.RanomUtil;
import com.bracelet.util.RespCode;
import com.bracelet.util.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
	@Resource
	BaseChannelHandler baseChannelHandler;
	private Logger logger = LoggerFactory.getLogger(getClass());

	/* 添加 */
	@ResponseBody
	@RequestMapping(value = "/addfriend", method = RequestMethod.POST)
	public String addfriend(@RequestBody String body) {
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");
		String imei = jsonObject.getString("imei");
		String role = jsonObject.getString("role");// 角色
		String phone = jsonObject.getString("phone");// 号码
		String cornet = jsonObject.getString("cornet");// 短号
		String headType = jsonObject.getString("headtype");// 头像类型
		JSONObject bb = new JSONObject();
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		Integer setStatus = 0;
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("code", 0);
			watchSetService.insertaddfriendLog(imei, setStatus, role, phone,
					cornet, headType);
			return bb.toString();
		}
		StringBuffer sb = new StringBuffer("[YW*" + imei
				+ "*0001*001B*PHB,1234,");
		if (socketLoginDto.getChannel().isActive()) {
			watchfriendService
					.insertFriend(imei, role, phone, cornet, headType);
			List<WatchFriend> listWatchInfo = watchfriendService
					.getFriendByImei(imei);
			StringBuffer sb1 = new StringBuffer("");
			if (listWatchInfo.size() > 0) {
				sb.append(listWatchInfo.size());
				sb.append(",");
				for (WatchFriend watfriend : listWatchInfo) {
					if (sb1.toString().isEmpty()) {
						sb1.append("0");
						sb1.append("-");
						sb1.append(watfriend.getRole_name());
						sb1.append("-");
						sb1.append(watfriend.getPhone());
						sb1.append("-");
						sb1.append(watfriend.getCornet());
						sb1.append("-");
						sb1.append(watfriend.getHeadtype());
						sb1.append("-");
						sb1.append("0000");
						sb1.append("-");
						sb1.append("95078001011");
					} else {
						sb1.append("|");
						sb1.append("0");
						sb1.append("-");
						sb1.append(watfriend.getRole_name());
						sb1.append("-");
						sb1.append(watfriend.getPhone());
						sb1.append("-");
						sb1.append(watfriend.getCornet());
						sb1.append("-");
						sb1.append(watfriend.getHeadtype());
						sb1.append("-");
						sb1.append("0000");
						sb1.append("-");
						sb1.append("95078001011");
					}
				}
			}
			sb.append(sb1.toString());
			sb.append("]");
			socketLoginDto.getChannel().writeAndFlush(sb.toString());
			bb.put("code", 1);
			setStatus = 1;
		} else {
			bb.put("code", 0);
			setStatus = 0;
		}
		watchSetService.insertaddfriendLog(imei, setStatus, role, phone,
				cornet, headType);
		return bb.toString();
	}

	/* 查询 */
	@ResponseBody
	@RequestMapping(value = "/selectfriend/{token}/{imei}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String selectfriend(@PathVariable String token,
			@PathVariable String imei) {
		JSONObject bb = new JSONObject();
		List<WatchFriend> listWatchInfo = watchfriendService
				.getFriendByImei(imei);
		JSONArray jsonArray = new JSONArray();
		if (listWatchInfo != null && listWatchInfo.size() > 0) {
			for (WatchFriend location : listWatchInfo) {
				JSONObject dataMap = new JSONObject();
				dataMap.put("id", location.getId());
				dataMap.put("cornet", location.getCornet());
				dataMap.put("headtype", location.getHeadtype());
				dataMap.put("imei", location.getImei());
				dataMap.put("phone", location.getPhone());
				dataMap.put("name", location.getRole_name());
				dataMap.put("timestamp", location.getCreatetime().getTime());
				jsonArray.add(dataMap);
			}
			bb.put("code", 1);
		} else {
			bb.put("code", 0);
		}

		bb.put("result", jsonArray);
		return bb.toString();
	}

	/* 删除 */
	@ResponseBody
	@RequestMapping(value = "/deletefriend/{token}/{id}/{imei}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String selectfriend(@PathVariable String token,
			@PathVariable Long id, @PathVariable String imei) {
		JSONObject bb = new JSONObject();
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("code", 0);
			return bb.toString();
		}
		StringBuffer sb = new StringBuffer("[YW*" + imei
				+ "*0001*001B*PHB,1234,");
		if (socketLoginDto.getChannel().isActive()) {
			watchfriendService.deleteFriendById(id);
			List<WatchFriend> listWatchInfo = watchfriendService
					.getFriendByImei(imei);
			StringBuffer sb1 = new StringBuffer("");
			if (listWatchInfo.size() > 0) {
				sb.append(listWatchInfo.size());
				sb.append(",");
				for (WatchFriend watfriend : listWatchInfo) {
					if (sb1.toString().isEmpty()) {
						sb1.append("0");
						sb1.append("-");
						sb1.append(watfriend.getRole_name());
						sb1.append("-");
						sb1.append(watfriend.getPhone());
						sb1.append("-");
						sb1.append(watfriend.getCornet());
						sb1.append("-");
						sb1.append(watfriend.getHeadtype());
						sb1.append("-");
						sb1.append("0000");
						sb1.append("-");
						sb1.append("95078001011");
					} else {
						sb1.append("|");
						sb1.append("0");
						sb1.append("-");
						sb1.append(watfriend.getRole_name());
						sb1.append("-");
						sb1.append(watfriend.getPhone());
						sb1.append("-");
						sb1.append(watfriend.getCornet());
						sb1.append("-");
						sb1.append(watfriend.getHeadtype());
						sb1.append("-");
						sb1.append("0000");
						sb1.append("-");
						sb1.append("95078001011");
					}
				}
			}
			sb.append(sb1.toString());
			sb.append("]");
			socketLoginDto.getChannel().writeAndFlush(sb.toString());
			bb.put("code", 1);
		} else {
			bb.put("code", 0);
		}
		return bb.toString();
	}

	/* 修改*/
	@ResponseBody
	@RequestMapping(value = "/updatefriend", method = RequestMethod.POST)
	public String updatefriend(@RequestBody String body) {
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");
		String imei = jsonObject.getString("imei");
		Long id = Long.valueOf(jsonObject.getString("id"));
		String role = jsonObject.getString("role");// 角色
		String phone = jsonObject.getString("phone");// 号码
		String cornet = jsonObject.getString("cornet");// 短号
		String headType = jsonObject.getString("headtype");// 头像类型
		JSONObject bb = new JSONObject();
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("code", 0);
			return bb.toString();
		}
		StringBuffer sb = new StringBuffer("[YW*" + imei
				+ "*0001*001B*PHB,1234,");
		if (socketLoginDto.getChannel().isActive()) {
			watchfriendService.updateFriendById(id, role, phone, cornet,
					headType);
			List<WatchFriend> listWatchInfo = watchfriendService
					.getFriendByImei(imei);
			StringBuffer sb1 = new StringBuffer("");
			if (listWatchInfo.size() > 0) {
				sb.append(listWatchInfo.size());
				sb.append(",");
				for (WatchFriend watfriend : listWatchInfo) {
					if (sb1.toString().isEmpty()) {
						sb1.append("0");
						sb1.append("-");
						sb1.append(watfriend.getRole_name());
						sb1.append("-");
						sb1.append(watfriend.getPhone());
						sb1.append("-");
						sb1.append(watfriend.getCornet());
						sb1.append("-");
						sb1.append(watfriend.getHeadtype());
						sb1.append("-");
						sb1.append("0000");
						sb1.append("-");
						sb1.append("95078001011");
					} else {
						sb1.append("|");
						sb1.append("0");
						sb1.append("-");
						sb1.append(watfriend.getRole_name());
						sb1.append("-");
						sb1.append(watfriend.getPhone());
						sb1.append("-");
						sb1.append(watfriend.getCornet());
						sb1.append("-");
						sb1.append(watfriend.getHeadtype());
						sb1.append("-");
						sb1.append("0000");
						sb1.append("-");
						sb1.append("95078001011");
					}
				}
			}
			sb.append(sb1.toString());
			sb.append("]");
			socketLoginDto.getChannel().writeAndFlush(sb.toString());
			bb.put("code", 1);
		} else {
			bb.put("code", 0);
		}
		return bb.toString();
	}

}
