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

import com.bracelet.entity.BloodSugar;
import com.bracelet.entity.HeartPressure;
import com.bracelet.service.IBloodSugarService;

/**
 * 血糖服务
 */
@Service
public class BloodSugarServiceImpl implements IBloodSugarService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	    @Autowired
	    JdbcTemplate jdbcTemplate;
		@Override
		public boolean insert(Long user_id, Integer bloodSugar,
				Timestamp timsTimestamp) {
			 int i = jdbcTemplate
		                .update("insert into bloodsugar_info (user_id, blood_sugar,upload_time) values (?,?,?)",
		                        new Object[] { user_id, bloodSugar, timsTimestamp }, new int[] {
		                                Types.INTEGER, Types.INTEGER, 
		                                Types.TIMESTAMP });
		        return i == 1;
		}
		@Override
		public BloodSugar getLatest(Long user_id) {
	        String sql = "select * from bloodsugar_info where user_id=? order by upload_time desc LIMIT 1";
	        List<BloodSugar> list = jdbcTemplate.query(sql, new Object[] { user_id }, new BeanPropertyRowMapper<BloodSugar>(BloodSugar.class));
	        if (list != null && !list.isEmpty()) {
	            return list.get(0);
	        } else {
	            logger.info("getLatest reutrn null.user_id:" + user_id);
	        }

	        return null;
	    }
	
	
}
