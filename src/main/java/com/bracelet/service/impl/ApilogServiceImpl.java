package com.bracelet.service.impl;

import com.bracelet.service.IApilogService;
import com.bracelet.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.sql.Types;

@Service
public class ApilogServiceImpl implements IApilogService {
	@Autowired
	JdbcTemplate jdbcTemplate;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean insert(String name, String req, String resp, String imei, Integer rstatus, String rmsg, Long time) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"insert into apilog (name, req, resp, imei, rstatus, rmsg, time, createtime) values (?,?,?,?,?,?,?,?)",
				new Object[] { name, req, resp, imei, rstatus, rmsg, time, now }, new int[] { Types.VARCHAR, Types.VARCHAR,
						Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.TIMESTAMP });
		return i == 1;
	}

}
