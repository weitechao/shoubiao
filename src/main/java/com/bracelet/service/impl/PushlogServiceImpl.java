package com.bracelet.service.impl;

import com.bracelet.entity.Pushlog;
import com.bracelet.service.IPushlogService;
import com.bracelet.service.PageParam;
import com.bracelet.service.Pagination;
import com.bracelet.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.sql.Types;

@Service
public class PushlogServiceImpl implements IPushlogService {
	@Autowired
	JdbcTemplate jdbcTemplate;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean insert(Long user_id, String imei, Integer type, String target, String title, String content) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"insert into pushlog (user_id, imei, type, target, title, content, createtime) values (?,?,?,?,?,?,?)",
				new Object[] { user_id, imei, type, target, title, content, now }, new int[] { Types.INTEGER, Types.VARCHAR,
						Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP });
		return i == 1;
	}

	@Override
	public Pagination<Pushlog> find(Long user_id, PageParam pageParam) {
		String sql = "select * from pushlog where user_id=?";
		return new Pagination<Pushlog>(sql, new Object[] { user_id }, pageParam, jdbcTemplate, Pushlog.class);
	}

}
