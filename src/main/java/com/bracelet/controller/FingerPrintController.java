package com.bracelet.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bracelet.dto.HttpBaseDto;
import com.bracelet.entity.FingerInfo;
import com.bracelet.exception.BizException;
import com.bracelet.service.IFingerService;
import com.bracelet.util.RespCode;

/*
 * 新增文件 app 服务器接口 
 * 指纹三个接口
 * 添加
 * 删除
 * 查询
 * */

@Controller
@RequestMapping("/finger")
public class FingerPrintController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	IFingerService fingerService;

	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public HttpBaseDto setWhiteList(@RequestParam String token,
			@RequestParam String finger_id, @RequestParam String imei,
			@RequestParam Integer type, @RequestParam String name,
			@RequestParam Long member_id) {
		logger.info("增加指纹=" + token);
		Long user_id = checkTokenAndUser(token);
		if (StringUtils.isAllEmpty(finger_id, imei)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		fingerService.insert(user_id, imei, finger_id, type, name, member_id);
		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public HttpBaseDto get(@RequestParam String token,
			@RequestParam String imei, @RequestParam Integer type) {
		logger.info("获取指纹=" + token);
		Long user_id = checkTokenAndUser(token);
		List<FingerInfo> pwdInfoList = null;
		if (type == 0) {// 0成员
			pwdInfoList = fingerService.getFingerInfobyUserId(user_id, imei);
			logger.info("查询成员指纹=" + type);
		} else if (type == 1) {// 1管理员
			pwdInfoList = fingerService.getFingerInfobyImei(imei);
			logger.info("查询管理员指纹=" + type);
		}
		List<Map<String, Object>> datalist = new LinkedList<Map<String, Object>>();
		if (pwdInfoList != null) {
			for (FingerInfo info : pwdInfoList) {
				Map<String, Object> pwd = new HashMap<>();
				pwd.put("id", info.getId());
				pwd.put("user_id", info.getUser_id());
				pwd.put("finger_id", info.getFinger_id());
				pwd.put("member_id", info.getMember_id());
				pwd.put("type", info.getType());
				pwd.put("name", info.getName());
				pwd.put("createtime", info.getCreatetime().getTime());
				datalist.add(pwd);
			}
		}
		HttpBaseDto dto = new HttpBaseDto();
		dto.setData(datalist);
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public HttpBaseDto deleteFinger(@RequestParam String token,
			@RequestParam Long id) {
		logger.info("删除指纹=" + token);
		Long user_id = checkTokenAndUser(token);
		//fingerService.delete(user_id, id);
		fingerService.delete(id);
		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}
	
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public HttpBaseDto udpateFingerType(@RequestParam String token,
			@RequestParam Long id, @RequestParam Integer type) {
		logger.info("修改指纹类型=" + token);
		Long user_id = checkTokenAndUser(token);
		fingerService.update(id, type);
		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}

	@ResponseBody
	@RequestMapping("/search/{token}/{imei}")
	public HttpBaseDto search(@PathVariable String token,
			@PathVariable String imei) {
		logger.info("获取指纹=" + token);
		Long user_id = checkTokenAndUser(token);
		List<FingerInfo> pwdInfoList = fingerService.getFingerInfobyUserId(
				user_id, imei);
		List<Map<String, Object>> datalist = new LinkedList<Map<String, Object>>();
		if (pwdInfoList != null) {
			for (FingerInfo info : pwdInfoList) {
				Map<String, Object> pwd = new HashMap<>();
				pwd.put("id", info.getId());
				pwd.put("user_id", info.getUser_id());
				pwd.put("finger_id", info.getFinger_id());
				pwd.put("type", info.getType());
				pwd.put("name", info.getName());
				pwd.put("createtime", info.getCreatetime().getTime());
				datalist.add(pwd);
			}
		}
		HttpBaseDto dto = new HttpBaseDto();
		dto.setData(pwdInfoList);
		return dto;
	}

}
