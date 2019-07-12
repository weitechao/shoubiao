package com.bracelet.service.impl;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import com.bracelet.datasource.DataSourceChange;
import com.bracelet.entity.WatchVoiceInfo;
import com.bracelet.service.WatchTkService;
import com.bracelet.util.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class WatchTkServiceImpl implements WatchTkService {
	@Autowired
	JdbcTemplate jdbcTemplate;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean insertVoiceInfo(String sender, String receiver, String sourceName, String voiceData, Integer status,
			String numMessage, Integer thisNubmer, Integer allNumber,Integer voiceLength) {
		Timestamp now = Utils.getCurrentTimestamp();
		// status 0表示新增 1表示已阅读
		int i = jdbcTemplate.update(
				"insert into watch_voice_info (sender, receiver, voice_content, status, source_name, createtime,no,updatetime,this_number,all_number,voice_length) values (?,?,?,?,?,?,?,?,?,?,?)",
				new Object[] { sender, receiver, voiceData, status, sourceName, now, numMessage, now, thisNubmer,
						allNumber,voiceLength },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.TIMESTAMP,
						Types.VARCHAR, Types.TIMESTAMP, Types.INTEGER, Types.INTEGER, Types.INTEGER });
		return i == 1;
	}

	@Override
	public boolean updateStatusById(Long id, Integer status) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update("update watch_voice_info set status=?, updatetime=? where id = ?",
				new Object[] { status, now, id, }, new int[] { Types.INTEGER, Types.TIMESTAMP, Types.INTEGER });
		return i == 1;
	}

	@Override
	@DataSourceChange(slave = true)
	public WatchVoiceInfo getVoiceByNoAndImeiAndStatus(String voiceNo, String imei, Integer status) {
		String sql = "select * from watch_voice_info where no=? and receiver=? and status=? order by id desc LIMIT 1";
		List<WatchVoiceInfo> list = jdbcTemplate.query(sql, new Object[] { voiceNo, imei, status },
				new BeanPropertyRowMapper<WatchVoiceInfo>(WatchVoiceInfo.class));
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("getLatestVoice return null.user_id:" + voiceNo);
		}
		return null;
	}

	@Override
	@DataSourceChange(slave = true)
	public List<WatchVoiceInfo> getVoiceListByImeiAndStatus(String imei, Integer status) {
		String sql = "select * from watch_voice_info where sender=? and status=? ";
		List<WatchVoiceInfo> list = jdbcTemplate.query(sql, new Object[] { imei, status },
				new BeanPropertyRowMapper<WatchVoiceInfo>(WatchVoiceInfo.class));
		return list;
	}

	@Override
	public boolean insertAppVoiceInfo(String sender, String receiver, String sourceName, String voiceData,
			Integer status, String numMessage, Integer thisNubmer, Integer allNumber,Integer voiceLength) {
		Timestamp now = Utils.getCurrentTimestamp();
		// status 0表示新增 1表示已阅读
		int i = jdbcTemplate.update(
				"insert into app_voice_info (sender, receiver, voice_content, status, source_name, createtime,no,updatetime,this_number,all_number,voice_length) values (?,?,?,?,?,?,?,?,?,?,?)",
				new Object[] { sender, receiver, voiceData, status, sourceName, now, numMessage, now, thisNubmer,
						allNumber ,voiceLength},
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.TIMESTAMP,
						Types.VARCHAR, Types.TIMESTAMP, Types.INTEGER, Types.INTEGER, Types.INTEGER });
		return i == 1;
	}

	@Override
	public WatchVoiceInfo getAppVoiceInfoByImeiAndStatus(String imei, Integer status) {
		String sql = "select * from app_voice_info where receiver=? and status=?  order by id desc limit 1";
		List<WatchVoiceInfo> list = jdbcTemplate.query(sql, new Object[] { imei, status },
				new BeanPropertyRowMapper<WatchVoiceInfo>(WatchVoiceInfo.class));
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("getLatestVoice return null.user_id:" + imei);
		}
		return null;
	}

	@Override
	public boolean delteByImei(String imei) {
		int i = jdbcTemplate.update("delete from watch_voice_info where sender = ?",
				new Object[] { imei}, new int[] { Types.VARCHAR });
		return i == 1;
	}

	@Override
	public WatchVoiceInfo getVoiceByImei(String imei) {
		String sql = "select * from watch_voice_info where sender=?  LIMIT 1";
		List<WatchVoiceInfo> list = jdbcTemplate.query(sql, new Object[] {imei },
				new BeanPropertyRowMapper<WatchVoiceInfo>(WatchVoiceInfo.class));
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("getLatestVoice return null.user_id:" + imei);
		}
		return null;
	}

}
