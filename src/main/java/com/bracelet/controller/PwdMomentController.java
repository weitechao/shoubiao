package com.bracelet.controller;

import java.util.Arrays;
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
import com.bracelet.entity.PwdInfo;
import com.bracelet.entity.PwdMomentInfo;
import com.bracelet.entity.WhiteListInfo;
import com.bracelet.exception.BizException;
import com.bracelet.service.IPwdService;
import com.bracelet.service.ISosService;
import com.bracelet.util.RespCode;
import com.bracelet.util.StringUtil;

/*
 *
 * */

@Controller
@RequestMapping("/pwdMoment")
public class PwdMomentController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	IPwdService pwdService;

	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public HttpBaseDto setWhiteList(@RequestParam String token,
			@RequestParam String pwd, @RequestParam String imei) {
		logger.info("增加临时密码=" + token + ",临时密码=" + pwd);// 不管是用户名还是密码只能有一个
		Long user_id = checkTokenAndUser(token);
		if (StringUtils.isAllEmpty(pwd, imei)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		List<String> result = Arrays.asList(pwd.split(","));
		for (int i = 0; i < result.size(); i++) {
			pwdService.insertMonent(user_id, imei, result.get(i));
		}
		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public HttpBaseDto get(@RequestParam String token, @RequestParam String imei) {
		logger.info("获取临时密码=" + token);
		Long user_id = checkTokenAndUser(token);
		HttpBaseDto dto = new HttpBaseDto();

		List<Map<String, Object>> datalist = new LinkedList<Map<String, Object>>();
		List<PwdMomentInfo> list = pwdService.getMomentPwdInfo(user_id, imei);
		if (list != null) {
			for (PwdMomentInfo info : list) {
				Map<String, Object> dataMap = new HashMap<>();
				dataMap.put("id", info.getId());
				dataMap.put("pwd", info.getPwd());
				dataMap.put("createtime", info.getCreatetime().getTime());
				dataMap.put("updatetime", info.getUpdatetime().getTime());
				dataMap.put("type", info.getType());
				dataMap.put("status", info.getStatus());
				datalist.add(dataMap);
			}
		}
		dto.setData(datalist);

		return dto;
	}


	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public HttpBaseDto update(@RequestParam String token,
			@RequestParam Long id) {
		logger.info("修改密码=" + token);
		Long user_id = checkTokenAndUser(token);
		pwdService.updateMomentInfo(user_id, id);
		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
	public HttpBaseDto deleteAll(@RequestParam String token,
			@RequestParam String imei) {
		logger.info("修改密码=" + token);
		Long user_id = checkTokenAndUser(token);
		pwdService.deleteAllMomentInfo(user_id, imei);
		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public HttpBaseDto delete(@RequestParam String token,
			@RequestParam Long id) {
		logger.info("修改密码=" + token);
		Long user_id = checkTokenAndUser(token);
		pwdService.deleteAllMomentInfo(id);
		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}

}
