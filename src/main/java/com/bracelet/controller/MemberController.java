package com.bracelet.controller;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.HttpBaseDto;
import com.bracelet.entity.BindDevice;
import com.bracelet.entity.FingerInfo;
import com.bracelet.entity.MemberInfo;
import com.bracelet.entity.NotRegisterInfo;
import com.bracelet.entity.PwdInfo;
import com.bracelet.entity.UserInfo;
import com.bracelet.entity.WhiteListInfo;
import com.bracelet.exception.BizException;
import com.bracelet.service.IFingerService;
import com.bracelet.service.IMemService;
import com.bracelet.service.IPwdService;
import com.bracelet.service.ISosService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.util.RespCode;
import com.bracelet.util.StringUtil;

/*
 * 新增文件 app 服务器接口 
 * 用户成员三个接口
 * 添加
 * 删除
 * 查询
 * */

@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	IMemService memService;
	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	IFingerService fingerService;
	@Autowired
	IPwdService pwdService;

	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public HttpBaseDto add(@RequestParam String token,
			@RequestParam String tel, @RequestParam String name,
			@RequestParam String imei, @RequestParam String head) {
		logger.info("增加成员=" + token);
		Long user_id = checkTokenAndUser(token);
		if (StringUtils.isAllEmpty(tel, imei)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}

		MemberInfo member = memService.getMemberInfobyTel(user_id, imei, tel);
		if (member != null) {
			throw new BizException(RespCode.WL_ALREADY_EXIT);
		}

		Map<String, Object> pwd = new HashMap<>();
		memService.insert(user_id, tel, name, imei, head);

		member = memService.getMemberInfobyTel(user_id, imei, tel);

		pwd.put("id", member.getId());

		UserInfo userInfo = userInfoService.getUserInfoByUsername(tel);
		if (userInfo != null) {
			userInfoService.updateName(userInfo.getUser_id(), name);

			pwd.put("user_id", userInfo.getUser_id());
			pwd.put("status", 1);
			pwd.put("head", head);
			userInfoService.bindDevice(userInfo.getUser_id(), imei, 0, name);

		} else {
			if (this.userInfoService.saveUserInfo(tel, "123456", 0, name)) {
				userInfo = userInfoService.getUserInfoByUsername(tel);
				userInfoService
						.bindDevice(userInfo.getUser_id(), imei, 0, name);
				pwd.put("user_id", userInfo.getUser_id());
				pwd.put("status", 0);
				pwd.put("head", head);
			}
		}

		HttpBaseDto dto = new HttpBaseDto();
		dto.setData(pwd);
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public HttpBaseDto get(@RequestParam String token, @RequestParam String imei) {
		logger.info("获取成员=" + token);
		Long user_id = checkTokenAndUser(token);
		List<MemberInfo> pwdInfoList = memService.getMemberInfo(user_id, imei);
		List<Map<String, Object>> datalist = new LinkedList<Map<String, Object>>();
		if (pwdInfoList != null) {
			for (MemberInfo info : pwdInfoList) {
				Map<String, Object> pwd = new HashMap<>();
				
				pwd.put("id", info.getId());
				//pwd.put("name", info.getName());
				pwd.put("phone", info.getPhone());
				pwd.put("createtime", info.getCreatetime().getTime());
				
				
				pwd.put("user_id", info.getUser_id());
				UserInfo userInfo = userInfoService.getUserInfoByUsername(info
						.getPhone());
				if (userInfo != null) {
					pwd.put("user_idd", userInfo.getUser_id());
					pwd.put("nickname", userInfo.getNickname());
					pwd.put("name", userInfo.getNickname());
					pwd.put("head", userInfo.getHead());
				} else {
					pwd.put("user_idd", 0);
					pwd.put("nickname", info.getName());
					pwd.put("name", info.getName());
					pwd.put("head", info.getHead());
				}
				datalist.add(pwd);
			}
		}
		HttpBaseDto dto = new HttpBaseDto();
		dto.setData(datalist);
		return dto;
	}



	@ResponseBody
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public HttpBaseDto del(@RequestParam String token, @RequestParam Long id, @RequestParam Long  memberId, @RequestParam String imei) {
		logger.info("删除单个成员=" + token);
		Long user_id = checkTokenAndUser(token);
		
		memService.delete(user_id, id);
		
		//UserInfo userinfo =userInfoService.getUserInfoByUsername(phone);
		
		fingerService.deleteByImeiAndMemberId(imei,memberId);
		pwdService.deleteByImeiAndMemberId(imei,memberId);
	
		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
	public HttpBaseDto delAll(@RequestParam String token) {
		logger.info("删除所有成员=" + token);
		Long user_id = checkTokenAndUser(token);
		memService.deleteAll(user_id);
		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}

}
