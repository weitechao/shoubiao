package com.bracelet.service.impl;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import com.bracelet.entity.SensitivePointLog;
import com.bracelet.entity.Voltage;
import com.bracelet.entity.WatchVoiceInfo;
import com.bracelet.service.WatchSetService;
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
	public boolean insertVoiceInfo(String imei, String phone, String sourceName, String voiceData, Integer status,String numMessage,Integer thisNubmer,Integer allNumber) {
		Timestamp now = Utils.getCurrentTimestamp();
		//status 0表示新增  1表示已阅读
		int i = jdbcTemplate
				.update("insert into watch_voice_info (sender, receiver, voice_content, status, source_name, createtime,no,updatetime,this_number,all_number) values (?,?,?,?,?,?,?,?,?,?)",
						new Object[] { imei, phone,  voiceData, status, sourceName, now, numMessage, now, thisNubmer, allNumber}, new int[] {
								Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR,Types.TIMESTAMP , Types.VARCHAR,Types.TIMESTAMP , Types.INTEGER, Types.INTEGER});
		return i == 1;
	}
	
	@Override
	public boolean updateStatusByNoAndImei(String voiceNo, String imei,Integer status) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate
				.update("update watch_voice_info set status=?, updatetime=? where receiver = ? and no = ?",
						new Object[] { status ,now, imei,voiceNo }, new int[] {
						Types.INTEGER,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR });
		return i == 1;
	}
	@Override
	public WatchVoiceInfo getVoiceByNoAndImeiAndStatus(String voiceNo,
			String imei, Integer status) {
		String sql = "select * from watch_voice_info where no=? and receiver=? and status=? order by id desc LIMIT 1";
		List<WatchVoiceInfo> list = jdbcTemplate.query(sql, new Object[] { voiceNo,imei,status },
				new BeanPropertyRowMapper<WatchVoiceInfo>(WatchVoiceInfo.class));
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("getLatestVoice return null.user_id:" + voiceNo);
		}
		return null;
	}
	@Override
	public List<WatchVoiceInfo> getVoiceListByImeiAndStatus(String imei,
			Integer status) {
		String sql = "select * from watch_voice_info where receiver=? and status=? ";
		List<WatchVoiceInfo> list = jdbcTemplate.query(sql, new Object[] { imei, status },
				new BeanPropertyRowMapper<WatchVoiceInfo>(WatchVoiceInfo.class));
		return list;
	}
	
}
