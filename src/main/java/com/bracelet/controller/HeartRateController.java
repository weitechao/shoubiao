package com.bracelet.controller;

import com.bracelet.dto.HttpBaseDto;
import com.bracelet.entity.HeartRate;
import com.bracelet.service.IHeartRateService;
import com.bracelet.service.PageParam;
import com.bracelet.service.Pagination;
import com.bracelet.util.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/heartRate")
public class HeartRateController extends BaseController {
	@Autowired
	IHeartRateService heartRateService;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@ResponseBody
	@RequestMapping("/search/latest/{token}")
	public HttpBaseDto getLatestHeartRate(@PathVariable String token) {
		Long user_id = checkTokenAndUser(token);
		HeartRate heartRate = heartRateService.getLatest(user_id);
		Map<String, Object> dataMap = new HashMap<>();
		if (heartRate != null) {
			dataMap.put("heartRate", heartRate.getHeart_rate());
			dataMap.put("status", Utils.checkHeartRate(heartRate.getHeart_rate()));
			dataMap.put("timestamp", heartRate.getUpload_time().getTime());
		}
		HttpBaseDto dto = new HttpBaseDto();
		dto.setData(dataMap);
		return dto;
	}

	@ResponseBody
	@RequestMapping("/upload")
	public HttpBaseDto upload(@RequestParam String token, @RequestParam Integer heartRate) {
		Long user_id = checkTokenAndUser(token);
		heartRateService.insert(user_id, heartRate, Utils.getCurrentTimestamp());
		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}

	
	@ResponseBody
	@RequestMapping(value = "/history/{token}", method = RequestMethod.GET)
	public HttpBaseDto listHeartRate(@PathVariable String token,
			@RequestParam(value = "page", required = false) Integer page) {
		Long user_id = checkTokenAndUser(token);
		PageParam pageParam = new PageParam();
		if (page != null) {
			pageParam.setPage(page.intValue());
		}
		pageParam.setSort("upload_time");
		Pagination<HeartRate> pagination = heartRateService.find(user_id, pageParam);
		List<Map<String, Object>> dataList = new LinkedList<Map<String, Object>>();
		HttpBaseDto dto = new HttpBaseDto();
		if (pagination.getResultList() != null) {
			for (HeartRate row : pagination.getResultList()) {
				Map<String, Object> rowMap = new HashMap<>();
				rowMap.put("heartRate", row.getHeart_rate());
				rowMap.put("status", Utils.checkHeartRate(row.getHeart_rate()));
				rowMap.put("timestamp", row.getUpload_time().getTime());
				dataList.add(rowMap);
			}
		}
		dto.setPageData(pagination, dataList);
		return dto;
	}
}
