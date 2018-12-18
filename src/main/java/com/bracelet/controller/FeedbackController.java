package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.HttpBaseDto;
import com.bracelet.entity.Feedback;
import com.bracelet.entity.LocationWatch;
import com.bracelet.entity.UserInfo;
import com.bracelet.entity.WatchDeviceAlarm;
import com.bracelet.exception.BizException;
import com.bracelet.service.IFeedbackService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.util.RespCode;

import java.util.List;

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

	/* 意见反馈 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addfeedback(@RequestBody String body) {
		JSONObject bb = new JSONObject();
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");

		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		String imei = jsonObject.getString("imei");
		String content = jsonObject.getString("content");

		feedbackService.insert(Long.valueOf(userId), content, imei);
		bb.put("Code", 1);
		return bb.toString();
	}

	/* 意见反馈 */
	@ResponseBody
	@RequestMapping(value = "/getList/{token}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String getListfeedback(@PathVariable String token) {
		JSONObject bb = new JSONObject();
	
		String userId = checkTokenWatchAndUser(token);
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		/*
		 * AnswerContent ，AnswerUserID ，CreateTime ，FeedbackID ，FeedbackState ，HandleTime ，HandleUserID ，QuestionContent，QuestionImg，QuestionType，QuestionUserID*/
		List<Feedback> feedList = feedbackService.getFeedBackList(Long.valueOf(userId));
		JSONArray jsonArray = new JSONArray();
		if (feedList != null) {
			for (Feedback feed : feedList) {
				JSONObject dataMap = new JSONObject();
				dataMap.put("AnswerContent", "");
				dataMap.put("AnswerUserID", "");
				dataMap.put("CreateTime", "");
				dataMap.put("FeedbackID", "");
				dataMap.put("FeedbackState", "");
				dataMap.put("HandleTime", feed.getCreatetime().getTime());
				dataMap.put("HandleUserID", feed.getUser_id());
				dataMap.put("QuestionContent", feed.getContent()+"");
				dataMap.put("QuestionImg", "");
				dataMap.put("QuestionType", 1);
				dataMap.put("QuestionUserID", feed.getUser_id());
				jsonArray.add(dataMap);
			}
		}
		bb.put("Code", 1);
		bb.put("Arr", jsonArray);
		return bb.toString();
	}

}
