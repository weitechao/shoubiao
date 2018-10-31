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
import com.bracelet.entity.Fence;
import com.bracelet.entity.FingerInfo;
import com.bracelet.entity.PwdInfo;
import com.bracelet.entity.WhiteListInfo;
import com.bracelet.service.IFingerService;
import com.bracelet.service.IPwdService;
import com.bracelet.service.ISosService;
import com.bracelet.util.Utils;

/**
 * sos服务
 * 
 */
@Service
public class FingerServiceImpl implements IFingerService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public boolean insert(Long user_id, String imei, String finger_id,
			Integer type,String name,Long member_id) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate
				.update("insert into finger_info (user_id, imei, finger_id,type, createtime, updatetime,name,member_id) values (?,?,?,?,?,?,?,?)",
						new Object[] { user_id, imei, finger_id, type, now, now,name,member_id },
						new int[] { Types.INTEGER, Types.VARCHAR,
								Types.INTEGER, Types.INTEGER, Types.TIMESTAMP,
								Types.TIMESTAMP , Types.VARCHAR, Types.INTEGER});
		return i == 1;
	}

	@Override
	@DataSourceChange(slave = true)
	public List<FingerInfo> getFingerInfobyUserId(Long user_id, String imei) {
		String sql = "select * from finger_info where member_id=? and imei =?";
		List<FingerInfo> list = jdbcTemplate.query(sql, new Object[] { user_id,imei },
				new BeanPropertyRowMapper<FingerInfo>(FingerInfo.class));
		return list;
	}

	@Override
	public boolean delete(Long user_id, Long id) {
		int i = jdbcTemplate.update("delete from finger_info where id = ? and user_id = ?", new Object[] { id, user_id },
				new int[] { Types.INTEGER, Types.INTEGER });
		return i == 1;
	}

	@Override
	@DataSourceChange(slave = true)
	public List<FingerInfo> getFingerInfoByFingerId(Long fingerId,
			String imei) {
		String sql = "select * from finger_info where finger_id=? and imei =?";
		List<FingerInfo> list = jdbcTemplate.query(sql, new Object[] { fingerId,imei },
				new BeanPropertyRowMapper<FingerInfo>(FingerInfo.class));
		return list;
	}

	@Override
	public boolean deleteByImei(String imei) {
	  jdbcTemplate.update("delete from finger_info where imei = ?", new Object[] { imei},
				new int[] { Types.VARCHAR });
		return true;
	}

	@Override
	@DataSourceChange(slave = true)
	public List<FingerInfo> getFingerInfobyImei(String imei) {
		String sql = "select * from finger_info where  imei =?";
		List<FingerInfo> list = jdbcTemplate.query(sql, new Object[] {imei },
				new BeanPropertyRowMapper<FingerInfo>(FingerInfo.class));
		return list;
	}

	@Override
	public boolean update(Long id, Integer type) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update("update finger_info set type = ?,updatetime = ? where id = ?",
				new Object[] { type, now, id}, new int[] { Types.INTEGER, Types.TIMESTAMP, Types.INTEGER });
		return i == 1;
	}

	@Override
	public boolean deleteByImeiAndMemberId(String imei, Long memberId) {
		int i = jdbcTemplate.update("delete from finger_info where imei = ? and member_id = ?", new Object[] { imei, memberId },
				new int[] { Types.VARCHAR, Types.INTEGER });
		return i == 1;
	}

	@Override
	public boolean delete(Long id) {
		int i = jdbcTemplate.update("delete from finger_info where id = ? ", new Object[] { id },
				new int[] { Types.INTEGER });
		return i == 1;
	}

}
