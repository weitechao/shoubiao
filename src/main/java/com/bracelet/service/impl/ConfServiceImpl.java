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
import com.bracelet.entity.Conf;
import com.bracelet.entity.SchoolGuard;
import com.bracelet.entity.Step;
import com.bracelet.entity.TimeSwitch;
import com.bracelet.service.IConfService;
import com.bracelet.util.Utils;

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

	@Override
	public SchoolGuard getSchoolGuard(String deviceId) {
		String sql = "select * from school_guard where deviceId=?  LIMIT 1";
		List<SchoolGuard> list = jdbcTemplate.query(sql, new Object[] { deviceId },
				new BeanPropertyRowMapper<SchoolGuard>(SchoolGuard.class));

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("getLatest return null.user_id:" + deviceId);
		}
		return null;
	}

	@Override
	public boolean updateSchoolGrardOffOnById(Long id, Integer status) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update("update school_guard  set offOn=?,updatetime=? where id = ?",
				new Object[] { status, now, id }, new int[] { Types.INTEGER, Types.TIMESTAMP, Types.INTEGER });
		return i == 1;
	}
	
	
	@Override
	public boolean insertGuardOffOn(String deviceId, Integer status) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"insert into school_guard (deviceId, offOn, createtime, updatetime) values (?,?,?,?)",
				new Object[] { deviceId, status, now, now },
				new int[] { Types.VARCHAR, Types.INTEGER,  Types.TIMESTAMP, Types.TIMESTAMP });
		return i == 1;
	}

	@Override
	public TimeSwitch getTimeSwitch(String deviceId) {
		String sql = "select * from watch_time_switch where deviceId=?  LIMIT 1";
		List<TimeSwitch> list = jdbcTemplate.query(sql, new Object[] { deviceId },
				new BeanPropertyRowMapper<TimeSwitch>(TimeSwitch.class));

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("getLatest return null.user_id:" + deviceId);
		}
		return null;
	}

	@Override
	public boolean updateTimeSwitchById(Long id, String timeClose, String timeOpen) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update("update watch_time_switch  set timeOpen=?, timeClose=?, updatetime=? where id = ?",
				new Object[] { timeOpen, timeClose, now, id }, new int[] { Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.INTEGER });
		return i == 1;
	}

	@Override
	public boolean insertTimeSwtich(String deviceId, String timeClose, String timeOpen) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"insert into watch_time_switch (deviceId, timeOpen, timeClose, createtime, updatetime) values (?,?,?,?,?)",
				new Object[] { deviceId,timeOpen, timeClose, now, now },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,  Types.TIMESTAMP, Types.TIMESTAMP });
		return i == 1;
	}

}
