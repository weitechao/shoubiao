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
import com.bracelet.entity.Step;
import com.bracelet.service.IStepService;

@Service
public class StepServiceImpl implements IStepService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;

	public Step getLatest(Long user_id) {
		String sql = "select * from step where user_id=? order by createtime desc LIMIT 1";
		List<Step> list = jdbcTemplate.query(sql, new Object[] { user_id }, new BeanPropertyRowMapper<Step>(Step.class));

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("getLatest return null.user_id:" + user_id);
		}
		return null;
	}

	public boolean insert(Long user_id, String imei, Integer step_number, Timestamp timestamp) {
		int i = jdbcTemplate.update("insert into step (user_id, imei, step_number, createtime) values (?,?,?,?)",
				new Object[] { user_id, imei, step_number, timestamp },
				new int[] { Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.TIMESTAMP });
		return i == 1;
	}
}
