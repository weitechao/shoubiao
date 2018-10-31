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

import com.bracelet.entity.Voltage;
import com.bracelet.service.IVoltageService;
import com.bracelet.util.Utils;

/**
 * 电量服务
 */
@Service
public class VoltageServiceImpl implements IVoltageService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public boolean insert(Long user_id, Integer voltage, Timestamp timsTimestamp) {
		int i = jdbcTemplate
				.update("insert into voltage_info (user_id, voltage,upload_time) values (?,?,?)",
						new Object[] { user_id, voltage, timsTimestamp },
						new int[] { Types.INTEGER, Types.INTEGER,
								Types.TIMESTAMP });
		return i == 1;
	}

	@Override
	public Voltage getLatest(Long user_id) {
		String sql = "select * from voltage_info where user_id=? order by upload_time desc LIMIT 1";
		List<Voltage> list = jdbcTemplate.query(sql, new Object[] { user_id },
				new BeanPropertyRowMapper<Voltage>(Voltage.class));
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("getLatest return null.user_id:" + user_id);
		}
		return null;
	}

	@Override
	public boolean insertDianLiang(String imei, Integer voltage) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate
				.update("insert into voltage_info (imei, voltage,upload_time) values (?,?,?)",
						new Object[] { imei, voltage, now },
						new int[] { Types.VARCHAR, Types.INTEGER,
								Types.TIMESTAMP });
		return i == 1;
	}

	@Override
	public Voltage getLatest(String imei) {
		String sql = "select * from voltage_info where imei=? order by upload_time desc LIMIT 1";
		List<Voltage> list = jdbcTemplate.query(sql, new Object[] { imei },
				new BeanPropertyRowMapper<Voltage>(Voltage.class));
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("getLatest return null.user_id:" + imei);
		}
		return null;
	}

}
