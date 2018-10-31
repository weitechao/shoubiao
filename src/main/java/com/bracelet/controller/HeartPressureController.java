package com.bracelet.controller;

import com.bracelet.dto.HttpBaseDto;
import com.bracelet.entity.HeartPressure;
import com.bracelet.service.IHeartPressureService;
import com.bracelet.service.PageParam;
import com.bracelet.service.Pagination;
import com.bracelet.util.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/heartPressure")
public class HeartPressureController extends BaseController {
	@Autowired
	IHeartPressureService heartPressureService;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@ResponseBody
	@RequestMapping(value = "/search/latest/{token}", method = RequestMethod.GET)
	public HttpBaseDto getLatestHeartPressure(@PathVariable String token) {
		Long user_id = checkTokenAndUser(token);
		HeartPressure heartPressure = heartPressureService.getLatest(user_id);
		Map<String, Object> dataMap = new HashMap<>();
		if (heartPressure != null) {
			dataMap.put("maxHeartPressure", heartPressure.getMax_heart_pressure());
			dataMap.put("minHeartPressure", heartPressure.getMin_heart_pressure());
			dataMap.put("status",
					Utils.checkHeartPressure(heartPressure.getMax_heart_pressure(), heartPressure.getMin_heart_pressure()));
			dataMap.put("timestamp", heartPressure.getUpload_time().getTime());
		}
		HttpBaseDto dto = new HttpBaseDto();
		dto.setData(dataMap);
		return dto;
	}

	@ResponseBody
	@RequestMapping("/upload")
	public HttpBaseDto upload(@RequestParam String token, @RequestParam Integer maxHeartPressure,
			@RequestParam Integer minHeartPressure) {
		Long user_id = checkTokenAndUser(token);
		heartPressureService.insert(user_id, maxHeartPressure, minHeartPressure, Utils.getCurrentTimestamp());
		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}

	// 血压历史
	@ResponseBody
	@RequestMapping(value = "/history/{token}", method = RequestMethod.GET)
	public HttpBaseDto listHeartPressure(@PathVariable String token,
			@RequestParam(value = "page", required = false) Integer page) {
		Long user_id = checkTokenAndUser(token);
		PageParam pageParam = new PageParam();
		if (page != null) {
			pageParam.setPage(page.intValue());
		}
		pageParam.setSort("upload_time");
		Pagination<HeartPressure> pagination = heartPressureService.find(user_id, pageParam);
		List<Map<String, Object>> dataList = new LinkedList<Map<String, Object>>();
		HttpBaseDto dto = new HttpBaseDto();
		if (pagination.getResultList() != null) {
			for (HeartPressure row : pagination.getResultList()) {
				Map<String, Object> rowMap = new HashMap<>();
				rowMap.put("maxHeartPressure", row.getMax_heart_pressure());
				rowMap.put("minHeartPressure", row.getMin_heart_pressure());
				rowMap.put("status", Utils.checkHeartPressure(row.getMax_heart_pressure(), row.getMin_heart_pressure()));
				rowMap.put("timestamp", row.getUpload_time().getTime());
				dataList.add(rowMap);
			}
		}
		dto.setPageData(pagination, dataList);
		return dto;
	}

}
