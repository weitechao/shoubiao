package com.bracelet.service.impl;

import com.bracelet.entity.TokenInfo;
import com.bracelet.service.ITokenInfoService;
import com.bracelet.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.List;

@Service
public class TokenInfoServiceImpl implements ITokenInfoService {
	@Autowired
	JdbcTemplate jdbcTemplate;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public Long getUserIdByToken(String token) {
		String sql = "select * from token_info where token=? order by createtime LIMIT 1";
		List<TokenInfo> list = jdbcTemplate.query(sql, new Object[] { token },
				new BeanPropertyRowMapper<TokenInfo>(TokenInfo.class));
		if (list != null && !list.isEmpty()) {
			return list.get(0).getUser_id();
		} else {
			logger.info("cannot find userId,token:" + token);
		}

		return null;
	}

	@Override
	public String getTokenByUserId(Long userId) {
		String sql = "select * from token_info where user_id=? order by createtime LIMIT 1";
		List<TokenInfo> list = jdbcTemplate.query(sql, new Object[] { userId },
				new BeanPropertyRowMapper<TokenInfo>(TokenInfo.class));
		if (list != null && !list.isEmpty()) {
			return list.get(0).getToken();
		} else {
			logger.info("cannot find token,userId:" + userId);
		}

		return null;
	}

	@Override
	public String genToken(Long userId) {
		long timestamp = new Date().getTime();
		int randomCode = Utils.randomInt(10, 10000);
		String otoken = "U" + timestamp + userId + "-" + randomCode;
		// md5 签名
		String token = Utils.getMD5(otoken);

		// save to db
		Timestamp now = Utils.getCurrentTimestamp();
		jdbcTemplate.update("replace into token_info (token, user_id, createtime) values (?,?,?)",
				new Object[] { token, userId, now }, new int[] { Types.VARCHAR, Types.INTEGER, Types.TIMESTAMP });

		return token;
	}
}
