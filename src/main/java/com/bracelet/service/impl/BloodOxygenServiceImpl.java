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

import com.bracelet.entity.BloodOxygen;
import com.bracelet.entity.BloodSugar;
import com.bracelet.service.IBloodOxygenService;

/**
 * 血氧服务
 */
@Service
public class BloodOxygenServiceImpl implements IBloodOxygenService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	    @Autowired
	    JdbcTemplate jdbcTemplate;
		@Override
		public boolean insert(Long user_id, Integer pulseRate,
				Integer bloodOxygen, Timestamp timsTimestamp) {
			 int i = jdbcTemplate
		                .update("insert into blood_oxygen_info (user_id, pulse_rate,blood_oxygen,upload_time) values (?,?,?,?)",
		                        new Object[] { user_id, pulseRate,bloodOxygen, timsTimestamp }, new int[] {
		                                Types.INTEGER, Types.INTEGER, Types.INTEGER,
		                                Types.TIMESTAMP });
		        return i == 1;
		}
		@Override
		public BloodOxygen getLatest(Long user_id) {
	        String sql = "select * from blood_oxygen_info where user_id=? order by upload_time desc LIMIT 1";
	        List<BloodOxygen> list = jdbcTemplate.query(sql, new Object[] { user_id }, new BeanPropertyRowMapper<BloodOxygen>(BloodOxygen.class));
	        if (list != null && !list.isEmpty()) {
	            return list.get(0);
	        } else {
	            logger.info("getLatest reutrn null.user_id:" + user_id);
	        }
	        return null;
	    }
	
}
