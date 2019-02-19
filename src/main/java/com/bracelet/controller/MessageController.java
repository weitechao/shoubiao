package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.HttpBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.DeviceCarrierInfo;
import com.bracelet.entity.LocationWatch;
import com.bracelet.entity.MsgInfo;
import com.bracelet.entity.PhoneCharge;
import com.bracelet.entity.Pushlog;
import com.bracelet.entity.SmsInfo;
import com.bracelet.entity.WatchDevice;
import com.bracelet.redis.LimitCache;
import com.bracelet.service.IDeviceService;
import com.bracelet.service.IPushlogService;
import com.bracelet.service.PageParam;
import com.bracelet.service.Pagination;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.RadixUtil;
import com.bracelet.util.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {
	@Autowired
	IPushlogService pushlogService;

	@Autowired
	IDeviceService ideviceService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@ResponseBody
	@RequestMapping(value = "/pushlog/{token}", method = RequestMethod.GET)
	public HttpBaseDto listPushlog(@PathVariable String token,
			@RequestParam(value = "page", required = false) Integer page) {
		Long user_id = checkTokenAndUser(token);
		PageParam pageParam = new PageParam();
		if (page != null) {
			pageParam.setPage(page.intValue());
		}
		pageParam.setSort("createtime");
		Pagination<Pushlog> pagination = pushlogService.find(user_id, pageParam);
		List<Map<String, Object>> dataList = new LinkedList<Map<String, Object>>();
		HttpBaseDto dto = new HttpBaseDto();
		if (pagination.getResultList() != null) {
			for (Pushlog row : pagination.getResultList()) {
				Map<String, Object> rowMap = new HashMap<>();
				rowMap.put("id", row.getId());
				rowMap.put("imei", row.getImei());
				rowMap.put("type", row.getType());
				rowMap.put("title", row.getTitle());
				rowMap.put("content", row.getContent());
				rowMap.put("timestamp", row.getCreatetime().getTime());
				dataList.add(rowMap);
			}
		}
		dto.setPageData(pagination, dataList);
		return dto;
	}

	/* 手表短信列表 */
	@ResponseBody
	@RequestMapping(value = "/getSmsList/{token}/{deviceId}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String getSmsList(@PathVariable String token, @PathVariable String deviceId) {
		JSONObject bb = new JSONObject();

		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		JSONArray jsonArray = new JSONArray();
		List<SmsInfo> smsList = pushlogService.getSmsList(deviceId);
		if (smsList != null && smsList.size() > 0) {
			for (SmsInfo sms : smsList) {
				JSONObject dataMap = new JSONObject();
				dataMap.put("DeviceSMSID", sms.getId());
				dataMap.put("DeviceID", deviceId);
				dataMap.put("Type", sms.getType());
				dataMap.put("Phone", sms.getPhone() + "");
				dataMap.put("SMS", "");
				dataMap.put("CreateTime", sms.getCreatetime().getTime());
				jsonArray.add(dataMap);
			}
		}
		bb.put("Code", 1);
		bb.put("SMSList", jsonArray);

		return bb.toString();
	}

	/* 手表话费查询 watch_phone_charge */
	@ResponseBody
	@RequestMapping(value = "/getCallCharge", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String getCallCharge(@RequestBody String body) {

		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");
		String phone = jsonObject.getString("phone");
		String content = jsonObject.getString("content");
		String imei = jsonObject.getString("imei");

		JSONObject bb = new JSONObject();

		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}

		PhoneCharge phoneC = pushlogService.getCharge(phone);
		if (phoneC != null) {
			bb.put("Code", 1);
			bb.put("phone", phone + "");
			bb.put("content", phoneC.getContent() + "");
			bb.put("createtime", phoneC.getCreatetime() + "");
			bb.put("imei", phoneC.getImei() + "");
		} else {
			bb.put("Code", 1);
			bb.put("phone", phone + "");
			bb.put("content", "");
			bb.put("createtime", "");
			bb.put("imei", "");
		}

		return bb.toString();
	}

	/* 手表设置运营商 watch_carrier */
	@ResponseBody
	@RequestMapping(value = "/setCarrier", method = RequestMethod.POST)
	public String setCarrier(@RequestBody String body) {
		JSONObject bb = new JSONObject();
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");
		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		// deviceId, smsNumber, smsBalanceKey，smsFlowKey
		String deviceId = jsonObject.getString("deviceId");
		String smsNumber = jsonObject.getString("smsNumber");
		String smsBalanceKey = jsonObject.getString("smsBalanceKey");
		String smsFlowKey = jsonObject.getString("smsFlowKey");

		DeviceCarrierInfo devc = pushlogService.getDeviceCarrInfo(deviceId);
		if (devc != null) {
			if (pushlogService.updateCarrierById(devc.getId(), smsNumber, smsBalanceKey, smsFlowKey)) {
				bb.put("Code", 1);
			} else {
				bb.put("Code", 0);
			}
		} else {
			if (pushlogService.insertCarrier(deviceId, smsNumber, smsBalanceKey, smsFlowKey)) {
				bb.put("Code", 1);
			} else {
				bb.put("Code", 0);
			}
		}

		return bb.toString();
	}

	/* 手表上传错误信息 */
	@ResponseBody
	@RequestMapping(value = "/uploadError", method = RequestMethod.POST)
	public String uploadError(@RequestBody String body) {
		JSONObject bb = new JSONObject();
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");
		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		String content = jsonObject.getString("content");
		// token content
		if (pushlogService.insertErrorInfo(Long.valueOf(user_id), content)) {
			bb.put("Code", 1);
		} else {
			bb.put("Code", 0);
		}
		return bb.toString();
	}

	/* 手表短信列表 */
	@ResponseBody
	@RequestMapping(value = "/msglist/{token}/{imei}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String msglist(@PathVariable String token, @PathVariable String imei) {
		JSONObject bb = new JSONObject();

		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		// List 数组{ Type ，DeviceID ，Content，Message，CreateTime }
		JSONArray jsonArray = new JSONArray();
		List<MsgInfo> msgList = pushlogService.getMsgInfoList(imei);
		if (msgList != null) {
			for (MsgInfo msginfo : msgList) {
				JSONObject dataMap = new JSONObject();
				dataMap.put("Type", msginfo.getType());
				dataMap.put("DeviceID", msginfo.getDevice_id());
				dataMap.put("Content", msginfo.getContent()+"");
				dataMap.put("Message", msginfo.getMessage()+"");
				dataMap.put("CreateTime",  Utils.getTime(msginfo.getCreatetime().getTime()));
				jsonArray.add(dataMap);
			}
		}
		/*
		 * "Type":"106","DeviceID":"805592","Content":"22.7032466399987-113.964199185461","Message":"设备进行过通话","CreateTime":"2018/12/18 16:49:16" 
		 * */
		bb.put("Code", 1);
		bb.put("List", jsonArray);
		return bb.toString();
	}

}
