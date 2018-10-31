package com.bracelet.service.impl;

import com.bracelet.service.IFeedbackService;
import com.bracelet.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.sql.Types;

@Service
public class FeedbackServiceImpl implements IFeedbackService {
	@Autowired
	JdbcTemplate jdbcTemplate;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean insert(Long user_id, String content, String contact) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update("insert into feedback (user_id, content, contact, createtime) values (?,?,?,?)",
				new Object[] { user_id, content, contact, now },
				new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP });
		return i == 1;
	}

}
