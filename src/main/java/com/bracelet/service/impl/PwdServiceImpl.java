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
import com.bracelet.entity.PwdInfo;
import com.bracelet.entity.PwdMomentInfo;
import com.bracelet.entity.WhiteListInfo;
import com.bracelet.service.IPwdService;
import com.bracelet.service.ISosService;
import com.bracelet.util.Utils;

/**
 * 密码服务
 * 
 */
@Service
public class PwdServiceImpl implements IPwdService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	@DataSourceChange(slave = true)
	public PwdInfo getByPhone(Long user_id, String imei,Long pwdId) {
		String sql = "select * from pwd_info where user_id=? and imei=? and pwd_id=? LIMIT 1";
		List<PwdInfo> list = jdbcTemplate.query(sql, new Object[] { user_id,imei,pwdId },
				new BeanPropertyRowMapper<PwdInfo>(PwdInfo.class));

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("get return null.user_id:" + user_id);
		}
		return null;
	}

	@Override
	public boolean insert(Long user_id, String imei, String pwd,Long pwdId) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"insert into pwd_info (user_id, imei, pwd, createtime, updatetime,pwd_id) values (?,?,?,?,?,?)",
				new Object[] { user_id, imei, pwd, now, now, pwdId},
				new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP ,Types.INTEGER});
		return i == 1;
	}

	@Override
	public boolean delete(Long user_id, Long id) {
		int i = jdbcTemplate.update("delete from pwd_info where id = ? and user_id = ?", new Object[] { id, user_id },
				new int[] { Types.INTEGER, Types.INTEGER });
		return i == 1;
	}

	@Override
	public boolean update(Long user_id, Long id, String pwd) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"update pwd_info set pwd = ?, updatetime = ? where id = ? and user_id = ?",
				new Object[] { pwd, now, id, user_id },
				new int[] { Types.VARCHAR, Types.TIMESTAMP, Types.INTEGER, Types.INTEGER });
		return i == 1;
	}

	@Override
	@DataSourceChange(slave = true)
	public List<PwdInfo> getByPhone(String imei) {
		String sql = "select * from pwd_info where imei=?";
		List<PwdInfo> list = jdbcTemplate.query(sql, new Object[] { imei },
				new BeanPropertyRowMapper<PwdInfo>(PwdInfo.class));
		if (list != null && !list.isEmpty()) {
			return list;
		} else {
			logger.info("查询密码结果为空:" + imei);
		}
		return null;
	}

	@Override
	@DataSourceChange(slave = true)
	public PwdInfo getByPhone(String imei,Long pwdId) {
		String sql = "select * from pwd_info where  imei=? and pwd_id=? LIMIT 1";
		List<PwdInfo> list = jdbcTemplate.query(sql, new Object[] { imei,pwdId },
				new BeanPropertyRowMapper<PwdInfo>(PwdInfo.class));

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("get return null.user_id:");
		}
		return null;
	}

	@Override
	public boolean deleteByImei(String imei) {
		int i = jdbcTemplate.update("delete from pwd_info where  imei = ?", new Object[] { imei },
				new int[] { Types.VARCHAR });
		return i == 1;
	}

	@Override
	public boolean insertMonent(Long user_id, String imei, String pwd) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"insert into pwd_moment_info (user_id, imei, pwd, createtime, updatetime,type,status) values (?,?,?,?,?,?,?)",
				new Object[] { user_id, imei, pwd, now, now, 0, 0},
				new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP ,Types.INTEGER,Types.INTEGER});
		return i == 1;
	}

	@Override
	@DataSourceChange(slave = true)
	public List<PwdMomentInfo> getMomentPwdInfo(Long user_id, String imei) {
		String sql = "select * from pwd_moment_info where imei=? and user_id=?";
		List<PwdMomentInfo> list = jdbcTemplate.query(sql, new Object[] { imei ,user_id},
				new BeanPropertyRowMapper<PwdMomentInfo>(PwdMomentInfo.class));
		if (list != null && !list.isEmpty()) {
			return list;
		} else {
			logger.info("查询临时密码结果为空:" + imei);
		}
		return null;
	}

	@Override
	public boolean updateMomentInfo(Long user_id, Long id) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"update pwd_moment_info set status = ?, updatetime = ? where id = ? and user_id = ?",
				new Object[] { 1, now, id, user_id },
				new int[] { Types.INTEGER, Types.TIMESTAMP, Types.INTEGER, Types.INTEGER });
		return i == 1;
	}

	@Override
	public boolean deleteAllMomentInfo(Long user_id, String imei) {
		int i = jdbcTemplate.update("delete from pwd_moment_info where imei = ? and user_id = ?", new Object[] { imei, user_id },
				new int[] { Types.VARCHAR ,Types.INTEGER });
		return i == 1;
	}

	@Override
	public boolean deleteAllMomentInfo(Long id) {
		int i = jdbcTemplate.update("delete from pwd_moment_info where id = ?", new Object[] { id },
				new int[] { Types.INTEGER });
		return i == 1;
	}

	@Override
	public boolean deleteByImeiAndMemberId(String imei, Long memberId) {
		int i = jdbcTemplate.update("delete from pwd_info where  imei = ? and pwd_id = ?", new Object[] { imei ,memberId},
				new int[] { Types.VARCHAR,Types.INTEGER });
		return i == 1;
	}


	@Override
	public boolean deleteById(Long id) {
		logger.info("通过id删除密码:" + id);
		int i = jdbcTemplate.update("delete from pwd_info where id = ? ", new Object[] { id },
				new int[] { Types.INTEGER });
		return i == 1;
	}

	


}
