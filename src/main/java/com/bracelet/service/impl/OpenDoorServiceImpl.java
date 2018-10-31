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
import com.bracelet.entity.OpenDoorInfo;
import com.bracelet.entity.PwdInfo;
import com.bracelet.entity.WhiteListInfo;
import com.bracelet.service.IOpenDoorService;
import com.bracelet.service.IPwdService;
import com.bracelet.service.ISosService;
import com.bracelet.util.Utils;

/**
 * sos服务
 * 
 */
@Service
public class OpenDoorServiceImpl implements IOpenDoorService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	@DataSourceChange(slave = true)
	public List<OpenDoorInfo> getHistory(Long user_id, String imei, String starttime, String endtime) {
		String sql = "select * from open_door_info where user_id=? and imei =? and createtime>= ? and createtime<= ? ORDER BY createtime DESC";
		List<OpenDoorInfo> list = jdbcTemplate.query(sql, new Object[] { user_id, imei, starttime, endtime },
				new BeanPropertyRowMapper<OpenDoorInfo>(OpenDoorInfo.class));
		return list;
	}

	@Override
	public boolean insert(Integer type, Long userid, Integer way, Integer side, String imei, String name,
			Timestamp timestamp) {
		// Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"insert into open_door_info (type,user_id,imei,way,side,createtime,name) values (?,?,?,?,?,?,?)",
				new Object[] { type, userid, imei, way, side, timestamp, name }, new int[] { Types.INTEGER,
						Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.TIMESTAMP, Types.VARCHAR });
		return i == 1;
	}

	@Override
	public Integer getOpenCount(String imei) {
		String sql = "select count(id) AS id from open_door_info where imei = ? and TO_DAYS(createtime) = TO_DAYS(NOW())";
		int i = jdbcTemplate.queryForObject(sql, new Object[] { imei }, Integer.class);
		return i;
	}

	@Override
	@DataSourceChange(slave = true)
	public List<OpenDoorInfo> getAllHistory(String imei, String starttime, String endtime) {
		String sql = "select * from open_door_info where  imei =? and createtime>= ? and createtime<= ?  order by createtime desc";
		List<OpenDoorInfo> list = jdbcTemplate.query(sql, new Object[] { imei, starttime, endtime },
				new BeanPropertyRowMapper<OpenDoorInfo>(OpenDoorInfo.class));
		return list;
	}

	@Override
	public boolean deleteByImei(String imei) {
		jdbcTemplate.update("delete from open_door_info where imei = ?", new Object[] { imei },
				new int[] { Types.VARCHAR });
		return true;
	}

}
