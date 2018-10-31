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
import com.bracelet.entity.PwdInfo;
import com.bracelet.entity.WhiteListInfo;
import com.bracelet.exception.BizException;
import com.bracelet.service.IPwdService;
import com.bracelet.service.ISosService;
import com.bracelet.util.RespCode;
import com.bracelet.util.StringUtil;

/*
 * 新增文件 app 服务器接口 
 * 门锁密码三个接口
 * 添加
 * 删除
 * 查询
 * */

@Controller
@RequestMapping("/pwd")
public class PwdController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	IPwdService pwdService;

	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public HttpBaseDto setWhiteList(@RequestParam String token,
			@RequestParam String pwd, @RequestParam String imei,
			@RequestParam Long pwdId) {
		logger.info("增加密码=" + token);// 不管是用户名还是密码只能有一个
		Long user_id = checkTokenAndUser(token);
		if (StringUtils.isAllEmpty(pwd, imei)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}

		// PwdInfo pwdInfo = pwdService.getByPhone(user_id, imei,pwdId);
		PwdInfo pwdInfo = pwdService.getByPhone(imei, pwdId);
		if (pwdInfo != null) {
			throw new BizException(RespCode.PWD_EXIST);
		}
		pwdService.insert(user_id, imei, pwd, pwdId);
		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public HttpBaseDto get(@RequestParam String token,
			@RequestParam String imei, @RequestParam Integer type) {
		logger.info("获取密码=" + token);
		Long user_id = checkTokenAndUser(token);
		HttpBaseDto dto = new HttpBaseDto();
		List<Map<String, Object>> datalist = new LinkedList<Map<String, Object>>();

		if (type == 0) {// 0是成员
			PwdInfo pwdInfo = pwdService.getByPhone(imei, user_id);
			Map<String, Object> pwd = new HashMap<>();
			if (pwdInfo != null) {
				pwd.put("pwd", pwdInfo.getPwd());
				pwd.put("id", pwdInfo.getId());
				pwd.put("user_id", pwdInfo.getPwd_id());
				datalist.add(pwd);
			}
			// dto.setData(datalist);
		} else if (type == 1) {

			List<PwdInfo> list = pwdService.getByPhone(imei);
			if (list != null) {
				for (PwdInfo info : list) {
					Map<String, Object> dataMap = new HashMap<>();
					dataMap.put("id", info.getId());
					dataMap.put("pwd", info.getPwd());
					dataMap.put("user_id", info.getPwd_id());
					datalist.add(dataMap);
				}
			}
		}
		dto.setData(datalist);
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public HttpBaseDto deletePwd(@RequestParam String token,
			@RequestParam Long id) {
		logger.info("删除密码=" + token);
		Long user_id = checkTokenAndUser(token);
		// pwdService.delete(user_id, id);
		pwdService.deleteById(id);
		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}

	@ResponseBody
	@RequestMapping("/deletebyid/{token}/{id}")
	public HttpBaseDto shanchuMiMa(@PathVariable String token,
			@PathVariable Long id) {

		logger.info("删除密码GET=" + token);
		Long user_id = checkTokenAndUser(token);
		pwdService.deleteById(id);
		HttpBaseDto dto = new HttpBaseDto();
		return dto;

	}

	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public HttpBaseDto update(@RequestParam String token,
			@RequestParam Long id, @RequestParam String pwd) {
		logger.info("修改密码=" + token);
		Long user_id = checkTokenAndUser(token);
		pwdService.update(user_id, id, pwd);
		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}
}
