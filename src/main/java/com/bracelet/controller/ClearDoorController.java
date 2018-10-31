package com.bracelet.controller;

import java.util.Date;
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

import com.alibaba.fastjson.JSON;
import com.bracelet.dto.FingerDto;
import com.bracelet.dto.HttpBaseDto;
import com.bracelet.entity.BindDevice;
import com.bracelet.entity.MomentPwdInfo;
import com.bracelet.entity.OpenDoorInfo;
import com.bracelet.entity.PwdInfo;
import com.bracelet.entity.UserInfo;
import com.bracelet.entity.WhiteListInfo;
import com.bracelet.exception.BizException;
import com.bracelet.service.IFingerService;
import com.bracelet.service.IMemService;
import com.bracelet.service.IMomentPwdService;
import com.bracelet.service.IOpenDoorService;
import com.bracelet.service.IPushlogService;
import com.bracelet.service.IPwdService;
import com.bracelet.service.ISosService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.util.PushUtil;
import com.bracelet.util.RespCode;
import com.bracelet.util.StringUtil;

/*
 * 新增文件 app 服务器接口 
 * 临时密码三个接口
 * 添加
 * 删除
 * 查询
 * */

@Controller
@RequestMapping("/cleardoor")
public class ClearDoorController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	IMemService memService;
	@Autowired
	IFingerService fingerService;
	@Autowired
	IPwdService pwdService;
	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	IPushlogService pushlogService;
	
	@Autowired
	IOpenDoorService opendoorService;
	
	@Autowired
	IMomentPwdService momentService;

	@ResponseBody
	@RequestMapping(value = "/clearAll", method = RequestMethod.POST)
	public HttpBaseDto get(@RequestParam String token, @RequestParam String imei) {
		logger.info("获取开锁记录=" + token);
		Long user_id = checkTokenAndUser(token);
		List<BindDevice> list = userInfoService.getBindInfoByImei(imei);
		if (list.size() > 0) {
			for (BindDevice info : list) {
				Long userId = info.getUser_id();
				String name = info.getName();
				UserInfo userinfo = userInfoService.getUserInfoById(userId);
				if (userinfo != null) {
					FingerDto sosDto = new FingerDto();
					sosDto.setName(name);
					sosDto.setImei(imei);
					sosDto.setTimestamp(new Date().getTime());
					String target = tokenInfoService.getTokenByUserId(userId);
					String title = "设备清除信息";
					String content = JSON.toJSONString(sosDto);
					String notifyContent = "您绑定的门锁"+name+"正在清除信息,请知悉!";
					PushUtil.push(target, title, content, notifyContent);
					// save push log
					this.pushlogService.insert(userId, imei, 0, target, title, content);
				}
			}
		}
		
		// 获得imei后 清除所有绑定信息 ，成员信心， 指纹信息。
		memService.deleteByImei(imei);
		fingerService.deleteByImei(imei);
		pwdService.deleteByImei(imei);
		userInfoService.deleteByImei(imei);
		opendoorService.deleteByImei(imei);
		momentService.deleteByImei(imei);
		HttpBaseDto dto = new HttpBaseDto();

		return dto;
	}

}
