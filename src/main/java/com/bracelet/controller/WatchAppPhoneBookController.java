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
import com.bracelet.entity.WatchPhoneBook;
import com.bracelet.exception.BizException;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IMemService;
import com.bracelet.service.IStepService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.service.WatchFriendService;
import com.bracelet.service.WatchSetService;
import com.bracelet.socket.BaseChannelHandler;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.HttpClientGet;
import com.bracelet.util.RadixUtil;
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
	@Autowired
	IMemService memberService;
	@Resource
	BaseChannelHandler baseChannelHandler;
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
			bb.put("code", -1);
			return bb.toString();
		}

		WatchPhoneBook phoneBook = memberService.getPhoneBookByImeiAndPhone(imei, phone);
	/*	if (phoneBook != null) {
			bb.put("code", 3);
			return bb.toString();
		}*/
		if (phoneBook == null) {
			memberService.insertPhoneBookInfo(imei, name, phone, cornet, headType, 1);
		}

		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);

		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("code", 4);
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
						sb2.append("1000000000");
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
						sb2.append("95078001011");
					} else {
						sb2.append("|");
						sb2.append("1000000000");
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
						sb2.append("95078001011");
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
			socketLoginDto.getChannel().writeAndFlush(sb.toString());
			bb.put("code", 1);
		} else {
			bb.put("code", 2);
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
			bb.put("code", -1);
			return bb.toString();
		}

		List<WatchPhoneBook> watchbookList = memberService.getPhoneBookByImei(imei);

		if (watchbookList.size() > 0 && watchbookList != null) {
			JSONArray jsonArray = new JSONArray();
			for (WatchPhoneBook location : watchbookList) {
				JSONObject dataMap = new JSONObject();
				dataMap.put("id", location.getId());
				dataMap.put("cornet", location.getCornet());
				dataMap.put("headtype", location.getHeadtype());
				dataMap.put("imei", location.getImei());
				dataMap.put("phone", location.getPhone());
				dataMap.put("name", location.getName());
				dataMap.put("timestamp", location.getCreatetime().getTime());
				jsonArray.add(dataMap);
			}

			bb.put("code", 1);
			bb.put("result", jsonArray);
		} else {
			bb.put("code", 0);
		}

		return bb.toString();
	}

	/* 删除 通讯录 */
	@ResponseBody
	@RequestMapping(value = "/deletePhoneBook/{token}/{id}/{imei}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String selectfriend(@PathVariable String token, @PathVariable Long id, @PathVariable String imei) {
		JSONObject bb = new JSONObject();

		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("code", -1);
			return bb.toString();
		}
		memberService.deletePhonebookById(id);

		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("code", 4);
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
						sb2.append("1000000000");
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
						sb2.append("95078001011");
					} else {
						sb2.append("|");
						sb2.append("1000000000");
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
						sb2.append("95078001011");
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
			socketLoginDto.getChannel().writeAndFlush(sb.toString());
			bb.put("code", 1);
		} else {
			bb.put("code", 2);
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
			bb.put("code", -1);
			return bb.toString();
		}

		memberService.updatePhonebookById(id, name, phone, cornet, headType);
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("code", 4);
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
						sb2.append("1000000000");
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
						sb2.append("95078001011");
					} else {
						sb2.append("|");
						sb2.append("1000000000");
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
						sb2.append("95078001011");
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
			socketLoginDto.getChannel().writeAndFlush(sb.toString());
			bb.put("code", 1);
		} else {
			bb.put("code", 2);
		}

		return bb.toString();
	}

}
