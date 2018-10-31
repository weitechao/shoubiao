package com.bracelet.service.impl;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.bracelet.entity.HeartRate;
import com.bracelet.service.IHeartRateService;
import com.bracelet.service.PageParam;
import com.bracelet.service.Pagination;

/**
 * 脉搏服务
 * 
 */
@Service
public class HeartRateServiceImpl implements IHeartRateService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;

	public boolean insert(Long user_id, Integer heartRate, Timestamp timestamp) {
		int i = jdbcTemplate.update("insert into heart_rate (user_id, heart_rate, upload_time) values (?,?,?)",
				new Object[] { user_id, heartRate, timestamp }, new int[] { Types.INTEGER, Types.INTEGER, Types.TIMESTAMP });
		return i == 1;
	}

	public HeartRate getLatest(Long user_id) {
		String sql = "select * from heart_rate where user_id=? order by upload_time desc LIMIT 1";
		List<HeartRate> list = jdbcTemplate.query(sql, new Object[] { user_id },
				new BeanPropertyRowMapper<HeartRate>(HeartRate.class));
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("getLatest return null.user_id:" + user_id);
		}
		return null;
	}

	@Override
	public Pagination<HeartRate> find(Long user_id, PageParam pageParam) {
		String sql = "select * from heart_rate where user_id=?";
		return new Pagination<HeartRate>(sql, new Object[] { user_id }, pageParam, jdbcTemplate, HeartRate.class);
	}
}
