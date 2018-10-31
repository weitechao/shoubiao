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
import com.bracelet.entity.HeartPressure;
import com.bracelet.service.IHeartPressureService;
import com.bracelet.service.PageParam;
import com.bracelet.service.Pagination;

/**
 * 血压服务
 */
@Service
public class HeartPressureServiceImpl implements IHeartPressureService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;

	public boolean insert(Long user_id, Integer max_heart_pressure, Integer min_heart_pressure, Timestamp timestamp) {
		int i = jdbcTemplate.update(
				"insert into heart_pressure (user_id, max_heart_pressure, min_heart_pressure, upload_time) values (?,?,?,?)",
				new Object[] { user_id, max_heart_pressure, min_heart_pressure, timestamp },
				new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.TIMESTAMP });
		return i == 1;
	}

	public HeartPressure getLatest(Long user_id) {
		String sql = "select * from heart_pressure where user_id=? order by upload_time desc LIMIT 1";
		List<HeartPressure> list = jdbcTemplate.query(sql, new Object[] { user_id },
				new BeanPropertyRowMapper<HeartPressure>(HeartPressure.class));
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("getLatest reutrn null.user_id:" + user_id);
		}

		return null;
	}

	@Override
	public Pagination<HeartPressure> find(Long user_id, PageParam pageParam) {
		String sql = "select * from heart_pressure where user_id=?";
		return new Pagination<HeartPressure>(sql, new Object[] { user_id }, pageParam, jdbcTemplate, HeartPressure.class);
	}
}
