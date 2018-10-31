package com.bracelet.controller;

import com.bracelet.dto.HttpBaseDto;
import com.bracelet.entity.UserInfo;
import com.bracelet.exception.BizException;
import com.bracelet.service.IFeedbackService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.util.RespCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/feedback")
public class FeedbackController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	IFeedbackService feedbackService;
	@Autowired
	IUserInfoService userInfoService;
	
	@ResponseBody
	@RequestMapping("/submit")
	public HttpBaseDto submit(@RequestParam String token, @RequestParam String content,
			@RequestParam(value = "contact", required = false) String contact) {
		Long user_id = checkTokenAndUser(token);
		String contact2 = contact;
		if (StringUtils.isEmpty(contact2)) {
			UserInfo userInfo = userInfoService.getUserInfoById(user_id);
			if (userInfo == null) {
				logger.info("feedback submit error.no login.token:" + token);
				throw new BizException(RespCode.U_NOT_EXIST);
			}
			contact2 = userInfo.getUsername();
		}
		feedbackService.insert(user_id, content, contact2);
		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}

}
