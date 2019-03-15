package com.bracelet.service.impl;

import com.bracelet.entity.Feedback;
import com.bracelet.service.IFeedbackService;
import com.bracelet.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

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

	@Override
	public List<Feedback> getFeedBackList(Long userId) {
		String sql = "select * from feedback where user_id=?";
		List<Feedback> list = jdbcTemplate.query(sql, new Object[] { userId }, new BeanPropertyRowMapper<Feedback>(Feedback.class));
		return list;
	}

	@Override
	public boolean deleteInfoById(Long id) {
		int i = jdbcTemplate.update("delete from feedback where id = ? ", new Object[] { id },
				new int[] { Types.INTEGER});
		return i == 1;
	}

}
