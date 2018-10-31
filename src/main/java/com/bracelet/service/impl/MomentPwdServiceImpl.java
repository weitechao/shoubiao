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
import com.bracelet.entity.MomentPwdInfo;
import com.bracelet.entity.PwdInfo;
import com.bracelet.entity.WhiteListInfo;
import com.bracelet.service.IMomentPwdService;
import com.bracelet.service.IPwdService;
import com.bracelet.service.ISosService;
import com.bracelet.util.Utils;

/**
 * 
 * 
 */
@Service
public class MomentPwdServiceImpl implements IMomentPwdService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	@DataSourceChange(slave = true)
	public List<MomentPwdInfo> getByImei(Long user_id, String imei) {
		String sql = "select * from pwd_moment_info where user_id=? and imei=?";
		List<MomentPwdInfo> list = jdbcTemplate.query(sql, new Object[] {
				user_id, imei }, new BeanPropertyRowMapper<MomentPwdInfo>(
				MomentPwdInfo.class));
		return list;
	}

	@Override
	public boolean insert(Long user_id, String imei) {
		for (int i = 0; i < 10; i++) {
			Timestamp now = Utils.getCurrentTimestamp();
			int b = jdbcTemplate
					.update("insert into pwd_moment_info (user_id, imei, pwd, createtime, status) values (?,?,?,?,?)",
							new Object[] { user_id, imei, Utils.rendom6(), now,
									1 }, new int[] { Types.INTEGER,
									Types.VARCHAR, Types.INTEGER,
									Types.TIMESTAMP, Types.INTEGER });
			if (b != 1) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean updateStatus(Long user_id, String imei,Integer status) {
		int i = jdbcTemplate.update("update pwd_moment_info set status=? where user_id = ? and imei = ?",
				new Object[] { status, user_id ,imei}, new int[] { Types.INTEGER,Types.INTEGER,Types.VARCHAR });
		return i == 1;
	}

	@Override
	public boolean deleteByImei(String imei) {
		 jdbcTemplate.update("delete from pwd_moment_info where   imei = ?", new Object[] { imei },
					new int[] { Types.VARCHAR});
			return true;
			}

	@Override
	public boolean updateStatus(String imei, Integer password, Integer status) {
		int i = jdbcTemplate.update("update pwd_moment_info set status=? where pwd = ? and imei = ?",
				new Object[] { status, password ,imei}, new int[] { Types.INTEGER,Types.VARCHAR,Types.VARCHAR });
		return i == 1;
	}

	@Override
	@DataSourceChange(slave = true)
	public MomentPwdInfo getByImeiAndPwd(String imei, String password) {
		String sql = "select * from pwd_moment_info where  imei=? and pwd = ?";
		List<MomentPwdInfo> list = jdbcTemplate.query(sql, new Object[] {
				imei, password }, new BeanPropertyRowMapper<MomentPwdInfo>(
				MomentPwdInfo.class));
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("cannot find userinfo,imei:" + imei);
		}
		return null;
	}

	@Override
	public boolean updateStatusById(Integer id, Integer status) {
		int i = jdbcTemplate.update("update pwd_moment_info set status=? where id = ? ",
				new Object[] { status, id}, new int[] { Types.INTEGER , Types.INTEGER  });
		return i == 1;
	}

}
