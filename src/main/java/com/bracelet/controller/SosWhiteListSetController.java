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
import com.bracelet.entity.WhiteListInfo;
import com.bracelet.exception.BizException;
import com.bracelet.service.ISosService;
import com.bracelet.util.RespCode;
import com.bracelet.util.StringUtil;
/*
 * 新增文件 app 服务器接口 
 * 白名单三个接口
 * 添加
 * 删除
 * 查询
 * */

@Controller
@RequestMapping("/sos")
public class SosWhiteListSetController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	ISosService sosService;

	@ResponseBody
	@RequestMapping(value = "/whitelist", method = RequestMethod.POST)
	public HttpBaseDto setWhiteList(@RequestParam String token, @RequestParam String phone, @RequestParam String name) {
		logger.info("增加白名单="+token);
		Long user_id = checkTokenAndUser(token);
		if (StringUtils.isAllEmpty(phone, name)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		if (StringUtil.isPhoneLegal(phone) != true) {
			logger.info("增加白名单名字:电话号码不合法="+phone);
			throw new BizException(RespCode.PHONE_INVALID);
		}
		WhiteListInfo wtlist = sosService.getByPhone(user_id, phone);
		if(wtlist != null){
			throw new BizException(RespCode.WL_ALREADY_EXIT);
		}
		sosService.insert(user_id, phone, name);
		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = "/whitelist/del", method = RequestMethod.POST)
	public HttpBaseDto deleteWhiteList(@RequestParam String token, @RequestParam Long id) {
		logger.info("删除白名单="+token);
		Long user_id = checkTokenAndUser(token);
		sosService.delete(user_id, id);
		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}
	
	@ResponseBody
	@RequestMapping(value = "/whitelist/{token}", method = RequestMethod.GET)
	public HttpBaseDto selectWhiteList(@PathVariable String token) {
		logger.info("查找白名单="+token);
		Long user_id = checkTokenAndUser(token);
		List<WhiteListInfo> list = sosService.find(user_id);
		List<Map<String, Object>> datalist = new LinkedList<Map<String, Object>>();
		if (list != null) {
			for (WhiteListInfo wlInfo : list) {
				Map<String, Object> dataMap = new HashMap<>();
				dataMap.put("id", wlInfo.getId());
				dataMap.put("phone", wlInfo.getPhone());
				dataMap.put("name", wlInfo.getName());
				datalist.add(dataMap);
			}
		}
		HttpBaseDto dto = new HttpBaseDto();
		dto.setData(datalist);
		return dto;
	}
}
