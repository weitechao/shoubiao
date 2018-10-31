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
import com.bracelet.entity.MomentPwdInfo;
import com.bracelet.entity.OpenDoorInfo;
import com.bracelet.entity.PwdInfo;
import com.bracelet.entity.WhiteListInfo;
import com.bracelet.exception.BizException;
import com.bracelet.service.IMomentPwdService;
import com.bracelet.service.IOpenDoorService;
import com.bracelet.service.IPwdService;
import com.bracelet.service.ISosService;
import com.bracelet.service.IUserInfoService;
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
@RequestMapping("/door")
public class OpenDoorController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	IOpenDoorService pwdService;

	@Autowired
	IUserInfoService userInfoService;

	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public HttpBaseDto get(@RequestParam String token,
			@RequestParam String imei, @RequestParam String startTime,
			@RequestParam String endtime) {
		logger.info("获取开锁记录=" + token);
		Long user_id = checkTokenAndUser(token);
		List<OpenDoorInfo> pwdInfo = pwdService.getHistory(user_id, imei,
				startTime, endtime);
		List<Map<String, Object>> datalist = new LinkedList<Map<String, Object>>();
		if (pwdInfo.size() > 0) {
			for (OpenDoorInfo info : pwdInfo) {
				Map<String, Object> pwd = new HashMap<>();
				pwd.put("id", info.getId());
				pwd.put("imei", info.getImei());
				pwd.put("type", info.getType());
				pwd.put("side", info.getSide());
				pwd.put("way", info.getWay());
				pwd.put("createtime", info.getCreatetime().getTime());
				pwd.put("name", info.getName());
				BindDevice  bindDevice= userInfoService.getBindInfoByImeiAndUserId(info.getImei(),user_id);	
				if(bindDevice!=null){
					pwd.put("bindname", bindDevice.getName());
				}else{
					pwd.put("bindname","");
				}
				datalist.add(pwd);
			}
		}
		HttpBaseDto dto = new HttpBaseDto();
		dto.setData(datalist);
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = "/all", method = RequestMethod.POST)
	public HttpBaseDto all(@RequestParam String token,
			@RequestParam String startTime, @RequestParam String endtime) {
		logger.info("获取所有开锁记录=" + token);
		Long user_id = checkTokenAndUser(token);
		List<BindDevice> list = userInfoService.getBindInfoById(user_id);
		List<Map<String, Object>> datalist = new LinkedList<Map<String, Object>>();
		if (list != null && !list.isEmpty()) {
			for (BindDevice wlInfo : list) {
				String imei = wlInfo.getImei();
				List<OpenDoorInfo> pwdInfo = pwdService.getAllHistory(imei, startTime, endtime);
				if (pwdInfo.size() > 0) {
					for (OpenDoorInfo info : pwdInfo) {
						Map<String, Object> pwd = new HashMap<>();
						pwd.put("id", info.getId());
						pwd.put("imei", info.getImei());
						pwd.put("type", info.getType());
						pwd.put("side", info.getSide());
						pwd.put("way", info.getWay());
						pwd.put("createtime", info.getCreatetime().getTime());
						pwd.put("name", info.getName());
						BindDevice  bindDevice= userInfoService.getBindInfoByImeiAndUserId(info.getImei(),user_id);	
						if(bindDevice!=null){
							pwd.put("bindname", bindDevice.getName());
						}else{
							pwd.put("bindname","");
						}
						datalist.add(pwd);
					}
				}
			}
		}

		HttpBaseDto dto = new HttpBaseDto();
		dto.setData(datalist);
		return dto;
	}
	
	  @ResponseBody
	 @RequestMapping("/search/{token}/{startTime}/{endTime}")
	public HttpBaseDto all1(@PathVariable String token,@PathVariable String startTime,@PathVariable String endTime) {
		logger.info("获取所有开锁记录get接口=" + token);
		Long user_id = checkTokenAndUser(token);
		List<BindDevice> list = userInfoService.getBindInfoById(user_id);
		List<Map<String, Object>> datalist = new LinkedList<Map<String, Object>>();
		if (list != null && !list.isEmpty()) {
			for (BindDevice wlInfo : list) {
				String imei = wlInfo.getImei();
				List<OpenDoorInfo> pwdInfo = pwdService.getAllHistory(imei, startTime, endTime);
				if (pwdInfo.size() > 0) {
					for (OpenDoorInfo info : pwdInfo) {
						Map<String, Object> pwd = new HashMap<>();
						pwd.put("id", info.getId());
						pwd.put("imei", info.getImei());
						pwd.put("type", info.getType());
						pwd.put("side", info.getSide());
						pwd.put("way", info.getWay());
						pwd.put("createtime", info.getCreatetime().getTime());
						pwd.put("name", info.getName());
						BindDevice  bindDevice= userInfoService.getBindInfoByImeiAndUserId(info.getImei(),user_id);	
						if(bindDevice!=null){
							pwd.put("bindname", bindDevice.getName());
						}else{
							pwd.put("bindname","");
						}
						datalist.add(pwd);
					}
				}
			}
		}

		HttpBaseDto dto = new HttpBaseDto();
		dto.setData(datalist);
		return dto;
	}

}
