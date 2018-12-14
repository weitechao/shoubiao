package com.bracelet.service.impl;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import com.bracelet.datasource.DataSourceChange;
import com.bracelet.entity.DownLoadFileInfo;
import com.bracelet.entity.WatchFriend;
import com.bracelet.service.WatchFriendService;
import com.bracelet.util.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class WatchFriendServiceImpl implements WatchFriendService {
	@Autowired
	JdbcTemplate jdbcTemplate;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean insertFriend(String imei, String role, String phone,
			String cornet, String headType) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate
				.update("insert into watch_friend_info ( imei,role_name, phone, cornet, headtype, createtime) values (?,?,?,?,?,?)",
						new Object[] { imei, role, phone, cornet, headType, now },
						new int[] { Types.VARCHAR, Types.VARCHAR,
								Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
								Types.TIMESTAMP });
		return i == 1;
	}

	@Override
	@DataSourceChange(slave = true)
	public List<WatchFriend> getFriendByImei(String imei) {
		String sql = "select * from watch_friend_info where imei=? ";
		List<WatchFriend> list = jdbcTemplate.query(sql, new Object[] { imei },
				new BeanPropertyRowMapper<WatchFriend>(WatchFriend.class));
		return list;
	}

	@Override
	public boolean deleteFriendById(Long id) {
		int i = jdbcTemplate.update(
				"delete from watch_friend_info where id = ?",
				new Object[] { id }, new int[] { Types.INTEGER });
		return i == 1;
	}

	@Override
	public boolean updateFriendById(Long id, String role, String phone,
			String cornet, String headType) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate
				.update("update watch_friend_info set role_name=?,phone=?,cornet=?,headtype=?,createtime=? where id = ?",
						new Object[] { role, phone, cornet, headType, now, id },
						new int[] { Types.VARCHAR, Types.VARCHAR,
								Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP,
								Types.INTEGER });
		return i == 1;
	}

}
