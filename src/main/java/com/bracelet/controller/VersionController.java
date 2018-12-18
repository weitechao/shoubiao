package com.bracelet.controller;

import com.alibaba.fastjson.JSONObject;
import com.bracelet.entity.VersionInfo;
import com.bracelet.service.IUserInfoService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/version")
public class VersionController extends BaseController {

	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	IUserInfoService userInfoService;

	
	@ResponseBody
	@RequestMapping(value = "/get/{token}", method = RequestMethod.GET)
	public String getLastVersion(@PathVariable String token) {
		JSONObject map = new JSONObject();

		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			map.put("Code", -1);
			return map.toString();
		}

		VersionInfo vinfo = userInfoService.getVersionInfo();

		if (vinfo != null) {
			map.put("Code", 1);
			map.put("AndroidUrl", vinfo.getDownload_path() + "");
			map.put("AndroidVersion", vinfo.getVersion());
			map.put("createtime", vinfo.getCreatetime().getTime());
			map.put("AndroidDescription ", vinfo.getDescription() + "");
		} else {
			map.put("Code", 1);
			map.put("AndroidUrl", "");
			map.put("AndroidVersion", 0);
			map.put("createtime", "");
			map.put("AndroidDescription", "");
		}

		return map.toString();
	}

	

}
