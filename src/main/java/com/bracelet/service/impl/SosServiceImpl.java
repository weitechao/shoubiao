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

import com.bracelet.entity.WhiteListInfo;
import com.bracelet.service.ISosService;
import com.bracelet.util.Utils;

/**
 * sos服务
 * 
 */
@Service
public class SosServiceImpl implements ISosService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public boolean insert(Long user_id, String phone, String name) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"insert into sos_white_list (user_id, phone, name, createtime, updatetime) values (?,?,?,?,?)",
				new Object[] { user_id, phone, name, now, now },
				new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP });
		return i == 1;
	}

	@Override
	public boolean delete(Long userId, Long id) {
		int i = jdbcTemplate.update("delete from sos_white_list where id=? and user_id=?", new Object[] { id, userId },
				new int[] { Types.INTEGER, Types.INTEGER });
		return i == 1;
	}

	@Override
	public WhiteListInfo getByPhone(Long userId, String phone) {
		String sql = "select * from sos_white_list where user_id=? and phone=? order by id desc LIMIT 1";
		List<WhiteListInfo> list = jdbcTemplate.query(sql, new Object[] { userId, phone },
				new BeanPropertyRowMapper<WhiteListInfo>(WhiteListInfo.class));
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("getLatest return null.phone:" + phone);
		}
		return null;
	}

	@Override
	public List<WhiteListInfo> find(Long userId) {
		String sql = "select * from sos_white_list where user_id=?";
		List<WhiteListInfo> list = jdbcTemplate.query(sql, new Object[] { userId },
				new BeanPropertyRowMapper<WhiteListInfo>(WhiteListInfo.class));
		if (list != null && !list.isEmpty()) {
			return list;
		} else {
			logger.info("查询sos电话号码结果为空:" + userId);
		}
		return null;
	}

	@Override
	public boolean insertSingleCallByTxt(Long userId, String imei, String phone, String name, String msg) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"insert into call_info (user_id, imei, phone, name, msg, createtime) values (?,?,?,?,?,?)",
				new Object[] { userId, imei, phone, name, msg, now },
				new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP });
		return i == 1;
	}
}
