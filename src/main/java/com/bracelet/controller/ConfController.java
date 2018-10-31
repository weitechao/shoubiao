package com.bracelet.controller;

import com.bracelet.dto.*;
import com.bracelet.entity.Conf;
import com.bracelet.service.IConfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/conf")
public class ConfController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	IConfService confService;

	@ResponseBody
	@RequestMapping("/service/info")
	public HttpBaseDto getOne() {
		List<Conf> confList = this.confService.list();
		List<Map<String, Object>> dataList = new LinkedList<Map<String, Object>>();
		if (confList != null) {
			for (Conf conf : confList) {
				Map<String, Object> dataMap = new HashMap<>();
				dataMap.put("key", conf.getKey());
				dataMap.put("value", conf.getValue());
				dataMap.put("description", conf.getDescription());
				dataList.add(dataMap);
			}
		}
		HttpBaseDto dto = new HttpBaseDto();
		dto.setData(dataList);
		return dto;
	}

}
