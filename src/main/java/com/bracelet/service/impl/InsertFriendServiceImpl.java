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

import com.bracelet.entity.InsertFriend;
import com.bracelet.entity.MemberInfo;
import com.bracelet.service.IinsertFriendService;
import com.bracelet.util.Utils;

@Service
public class InsertFriendServiceImpl implements IinsertFriendService{
	@Autowired
	JdbcTemplate jdbcTemplate;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public InsertFriend getInfo(String imei, String addimei) {
		String sql = "select * from add_friend_info where imei=? and add_imei=?  LIMIT 1";
		List<InsertFriend> list = jdbcTemplate.query(sql, new Object[] { imei, addimei },
				new BeanPropertyRowMapper<InsertFriend>(InsertFriend.class));

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("get return imei:" + imei);
		}
		return null;
	}
	@Override
	public boolean insertFriendInfo(String imei, String addimei) {
		Timestamp now = Utils.getCurrentTimestamp();
		//新增好友默认状态1
		int i = jdbcTemplate
				.update("insert into add_friend_info (imei, add_imei, status, createtime) values (?,?,?,?)",
						new Object[] { imei, addimei, 1, now},
						new int[] { Types.VARCHAR, Types.VARCHAR,
								Types.INTEGER, Types.TIMESTAMP});
		return i == 1;
	}
	@Override
	public List<InsertFriend> getInfoList(String imei, Integer status) {
		String sql = "select * from add_friend_info where imei=? and status =?";
		List<InsertFriend> list = jdbcTemplate
				.query(sql, new Object[] { imei, status },
						new BeanPropertyRowMapper<InsertFriend>(InsertFriend.class));
		return list;
	}
}
