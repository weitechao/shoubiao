package com.bracelet.controller;

import com.bracelet.exception.BizException;
import com.bracelet.service.ITokenInfoService;
import com.bracelet.util.RespCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {

	@Autowired
	protected ITokenInfoService tokenInfoService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	protected Long checkTokenAndUser(String token) {
		if (StringUtils.isEmpty(token)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		Long user_id = tokenInfoService.getUserIdByToken(token);
		if (user_id == null) {
			logger.info("[checkTokenAndUser] 通过token检查userid不存在，token:" + token);
			throw new BizException(RespCode.U_NOT_EXIST);
		}
		return user_id;
	}
	
	protected String checkTokenWatchAndUser(String token) {
		String reponse="0";
		/*if (StringUtils.isEmpty(token)) {
			reponse="0";
		}*/
		Long user_id = tokenInfoService.getUserIdByToken(token);
		if (user_id != null) {
			reponse=user_id+"";
		}else{
			logger.info("[checkTokenAndUser] 通过token检查userid不存在，token:" + token);
		}
		return reponse;
	}
	
	
}
