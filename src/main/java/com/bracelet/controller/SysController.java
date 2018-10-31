package com.bracelet.controller;

import com.bracelet.dto.*;
import com.bracelet.entity.Sys;
import com.bracelet.service.ISysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/sys")
public class SysController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	ISysService sysService;

	@ResponseBody
	@RequestMapping("/service/info")
	public HttpBaseDto getOne() {
		Sys sys = this.sysService.getOne();
		Map<String, Object> dataMap = new HashMap<>();
		if (sys != null) {
			dataMap.put("service_content", sys.getService_content());
		}
		HttpBaseDto dto = new HttpBaseDto();
		dto.setData(dataMap);
		return dto;
	}

}
