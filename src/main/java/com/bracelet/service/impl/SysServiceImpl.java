package com.bracelet.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.bracelet.entity.Sys;
import com.bracelet.service.ISysService;

@Service
public class SysServiceImpl implements ISysService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;

	public Sys getOne() {
		String sql = "select * from sys LIMIT 1";
		List<Sys> list = jdbcTemplate.query(sql, new Object[] {}, new BeanPropertyRowMapper<Sys>(Sys.class));
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("getOne reutrn null");
		}

		return null;
	}

}
