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
import com.bracelet.entity.BindDevice;
import com.bracelet.entity.FingerInfo;
import com.bracelet.entity.MemberInfo;
import com.bracelet.entity.NotRegisterInfo;
import com.bracelet.entity.PwdInfo;
import com.bracelet.entity.UserInfo;
import com.bracelet.entity.WhiteListInfo;
import com.bracelet.exception.BizException;
import com.bracelet.service.IMemService;
import com.bracelet.service.IPwdService;
import com.bracelet.service.ISosService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.util.RespCode;
import com.bracelet.util.StringUtil;
/*
 *
 * */

@Controller
@RequestMapping("/manager")
public class ManagerController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	IMemService memService;
	@Autowired
	IUserInfoService userInfoService;

	
	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public HttpBaseDto get(@RequestParam String token,@RequestParam String imei) {
		logger.info("获取成员="+token);
		Long user_id = checkTokenAndUser(token);
		
		BindDevice bind = userInfoService
				.getBindInfoByUserIdAndImei(user_id,imei,1);
		if(bind==null){
			throw new BizException(RespCode.SYS_AUTH);
		}
		
		List<MemberInfo> pwdInfoList= memService.getMemberInfo(user_id, imei);
		List<Map<String, Object>> datalist = new LinkedList<Map<String, Object>>();
		if (pwdInfoList != null) {
			for (MemberInfo info : pwdInfoList) {
				Map<String, Object> pwd = new HashMap<>();
				pwd.put("id",info.getId());
				pwd.put("name", info.getName());
				pwd.put("phone", info.getPhone());
				pwd.put("createtime", info.getCreatetime().getTime());
				datalist.add(pwd);
			}
		}
		HttpBaseDto dto = new HttpBaseDto();
		dto.setData(pwdInfoList);
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public HttpBaseDto del(@RequestParam String token, @RequestParam Long id) {
		logger.info("删除密码="+token);
		Long user_id = checkTokenAndUser(token);
		memService.delete(user_id, id);
		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}
	

}
