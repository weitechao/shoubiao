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

import com.bracelet.datasource.DataSourceChange;
import com.bracelet.entity.BloodFat;
import com.bracelet.entity.BloodSugar;
import com.bracelet.service.IBloodFatService;

/**
 * 血脂服务
 */
@Service
public class BloodFatServiceImpl implements IBloodFatService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	    @Autowired
	    JdbcTemplate jdbcTemplate;
		@Override
		public boolean insert(Long user_id, Integer bloodFat,
				Timestamp timsTimestamp) {
			 int i = jdbcTemplate
		                .update("insert into bloodfat_info (user_id, blood_fat,upload_time) values (?,?,?)",
		                        new Object[] { user_id, bloodFat, timsTimestamp }, new int[] {
		                                Types.INTEGER, Types.INTEGER, 
		                                Types.TIMESTAMP });
		        return i == 1;
		}
		
		@Override
		@DataSourceChange(slave = true)
		public BloodFat getLatest(Long user_id) {
	        String sql = "select * from bloodfat_info where user_id=? order by upload_time desc LIMIT 1";
	        List<BloodFat> list = jdbcTemplate.query(sql, new Object[] { user_id }, new BeanPropertyRowMapper<BloodFat>(BloodFat.class));
	        if (list != null && !list.isEmpty()) {
	            return list.get(0);
	        } else {
	            logger.info("getLatest reutrn null.user_id:" + user_id);
	        }
	        return null;
	    }
}
