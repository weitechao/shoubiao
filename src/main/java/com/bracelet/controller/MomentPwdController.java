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
import com.bracelet.entity.MomentPwdInfo;
import com.bracelet.entity.PwdInfo;
import com.bracelet.entity.WhiteListInfo;
import com.bracelet.exception.BizException;
import com.bracelet.service.IMomentPwdService;
import com.bracelet.service.IPwdService;
import com.bracelet.service.ISosService;
import com.bracelet.util.RespCode;
import com.bracelet.util.StringUtil;
/*
 * 新增文件 app 服务器接口 
 * 临时密码三个接口
 * 添加
 * 删除
 * 查询          是这个是这个。。
 * */

@Controller
@RequestMapping("/momentpwd")
public class MomentPwdController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	IMomentPwdService pwdService;

	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public HttpBaseDto setWhiteList(@RequestParam String token, @RequestParam String imei) {
		logger.info("增加临时密码="+token);
		Long user_id = checkTokenAndUser(token);
		if (StringUtils.isAllEmpty(imei)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		List<MomentPwdInfo> pwdInfo= pwdService.getByImei(user_id, imei);
		if(pwdInfo.size()>0){
			throw new BizException(RespCode.PWD_EXIST);
		}
		pwdService.insert(user_id, imei);
		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}
	
	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public HttpBaseDto get(@RequestParam String token,@RequestParam String imei) {
		logger.info("获取临时性密码="+token);
		Long user_id = checkTokenAndUser(token);
		List<MomentPwdInfo> pwdInfo= pwdService.getByImei(user_id, imei);
		List<Map<String, Object>> datalist = new LinkedList<Map<String, Object>>();
		if(pwdInfo.size()>0){
			for (MomentPwdInfo info : pwdInfo) {
				Map<String, Object> pwd = new HashMap<>();
				pwd.put("id",info.getId());
				pwd.put("pwd",info.getPwd());
				pwd.put("status", info.getStatus());
				pwd.put("createtime", info.getCreatetime().getTime());
				datalist.add(pwd);
			}
		}
		HttpBaseDto dto = new HttpBaseDto();
		dto.setData(datalist);
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public HttpBaseDto cancel(@RequestParam String token, @RequestParam String imei) {
		logger.info("取消临时密码="+token);
		Long user_id = checkTokenAndUser(token);
		pwdService.updateStatus(user_id, imei,0);
		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}
	

}
