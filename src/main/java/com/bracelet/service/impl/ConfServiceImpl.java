package com.bracelet.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.bracelet.entity.Conf;
import com.bracelet.service.IConfService;

@Service
public class ConfServiceImpl implements IConfService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<Conf> list() {
		String sql = "select * from conf";
		List<Conf> list = jdbcTemplate.query(sql, new Object[] {}, new BeanPropertyRowMapper<Conf>(Conf.class));
		return list;
	}

}
