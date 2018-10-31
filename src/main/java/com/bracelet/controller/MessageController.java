package com.bracelet.controller;

import com.bracelet.dto.HttpBaseDto;
import com.bracelet.entity.Pushlog;
import com.bracelet.service.IPushlogService;
import com.bracelet.service.PageParam;
import com.bracelet.service.Pagination;

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
@RequestMapping("/message")
public class MessageController extends BaseController {
	@Autowired
	IPushlogService pushlogService;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@ResponseBody
	@RequestMapping(value = "/pushlog/{token}", method = RequestMethod.GET)
	public HttpBaseDto listPushlog(@PathVariable String token,
			@RequestParam(value = "page", required = false) Integer page) {
		Long user_id = checkTokenAndUser(token);
		PageParam pageParam = new PageParam();
		if (page != null) {
			pageParam.setPage(page.intValue());
		}
		pageParam.setSort("createtime");
		Pagination<Pushlog> pagination = pushlogService.find(user_id, pageParam);
		List<Map<String, Object>> dataList = new LinkedList<Map<String, Object>>();
		HttpBaseDto dto = new HttpBaseDto();
		if (pagination.getResultList() != null) {
			for (Pushlog row : pagination.getResultList()) {
				Map<String, Object> rowMap = new HashMap<>();
				rowMap.put("id", row.getId());
				rowMap.put("imei", row.getImei());
				rowMap.put("type", row.getType());
				rowMap.put("title", row.getTitle());
				rowMap.put("content", row.getContent());
				rowMap.put("timestamp", row.getCreatetime().getTime());
				dataList.add(rowMap);
			}
		}
		dto.setPageData(pagination, dataList);
		return dto;
	}

}
