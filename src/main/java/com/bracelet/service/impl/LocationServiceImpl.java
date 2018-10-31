package com.bracelet.service.impl;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.bracelet.entity.Location;
import com.bracelet.entity.LocationOld;
import com.bracelet.entity.LocationWatch;
import com.bracelet.entity.OldBindDevice;
import com.bracelet.service.ILocationService;
import com.bracelet.util.Utils;

@Service
public class LocationServiceImpl implements ILocationService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<Location> getFootprint(Long user_id, String type) {
		Calendar calendar = Calendar.getInstance();
		if ("2".equals(type)) {
			// 过去一天
			calendar.add(Calendar.DAY_OF_YEAR, -1);
		} else {
			// 过去1小时
			calendar.add(Calendar.HOUR_OF_DAY, -1);
		}
		String dateString = Utils.format14DateString(calendar.getTime());
		String sql = "select * from location where user_id=? and upload_time > ? order by upload_time asc";
		List<Location> list = jdbcTemplate.query(sql, new Object[] { user_id,
				dateString }, new BeanPropertyRowMapper<Location>(
				Location.class));
		return list;
	}

	public Location getLatest(Long user_id) {
		String sql = "select * from location where user_id=? order by upload_time desc LIMIT 1";
		List<Location> list = jdbcTemplate.query(sql, new Object[] { user_id },
				new BeanPropertyRowMapper<Location>(Location.class));

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("getLatest return null.user_id:" + user_id);
		}
		return null;
	}

	public Location getRealtimeLocation(Long user_id, Integer status) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, -15);
		String dateString = Utils.format14DateString(calendar.getTime());
		String sql = "select * from location where user_id=? and status= ? and upload_time > ? order by upload_time desc LIMIT 1";
		List<Location> list = jdbcTemplate.query(sql, new Object[] { user_id,
				status, dateString }, new BeanPropertyRowMapper<Location>(
				Location.class));

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("getLatest return null.user_id:" + user_id);
		}
		return null;
	}

	public boolean insert(Long user_id, String location_type, String lat,
			String lng, Integer accuracy, Integer status, Timestamp timestamp) {
		int i = jdbcTemplate
				.update("insert into location (user_id, location_type, lat, lng, accuracy, status, upload_time) values (?,?,?,?,?,?,?)",
						new Object[] { user_id, location_type, lat, lng,
								accuracy, status, timestamp }, new int[] {
								java.sql.Types.INTEGER, java.sql.Types.VARCHAR,
								java.sql.Types.VARCHAR, java.sql.Types.VARCHAR,
								java.sql.Types.INTEGER, java.sql.Types.INTEGER,
								java.sql.Types.TIMESTAMP });
		return i == 1;
	}

	@Override
	public boolean insertOldLocation(String phone, String lat, String lng) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate
				.update("insert into location_old (phone, lat, lng, upload_time) values (?,?,?,?)",
						new Object[] { phone, lat, lng, now }, new int[] {
								Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
								Types.TIMESTAMP });
		return i == 1;
	}

	@Override
	public LocationOld getOldLocationLatest(String phone) {
		String sql = "select * from location_old where phone=? order by id desc LIMIT 1";
		List<LocationOld> list = jdbcTemplate.query(sql,
				new Object[] { phone }, new BeanPropertyRowMapper<LocationOld>(
						LocationOld.class));

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("getOldLocationLatest return null.user_id:" + phone);
		}
		return null;
	}

	@Override
	public List<LocationOld> getOldPhoneFootprint(String imei, String startime,
			String endtime) {
		String sql = "select * from location_old where phone=? and upload_time > ? and upload_time < ? order by upload_time asc";
		List<LocationOld> list = jdbcTemplate.query(sql, new Object[] { imei,
				startime, endtime }, new BeanPropertyRowMapper<LocationOld>(
				LocationOld.class));
		return list;
	}

	

	@Override
	public boolean insertUdInfo(String imei, Integer locationType, String lat,
			String lon, String status, String time) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate
				.update("insert into location_watchinfo (imei, location_type, lat, lng, status, location_time, upload_time) values (?,?,?,?,?,?,?)",
						new Object[] { imei, locationType ,lat, lon, status, time, now }, new int[] {
								Types.VARCHAR, Types.INTEGER, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
								Types.TIMESTAMP });
		return i == 1;
	}

	@Override
	public LocationWatch getLatest(String imei) {
		String sql = "select * from location_watchinfo where imei=? order by upload_time desc LIMIT 1";
		List<LocationWatch> list = jdbcTemplate.query(sql, new Object[] { imei },
				new BeanPropertyRowMapper<LocationWatch>(LocationWatch.class));

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("getLatestLocaiton return null.user_id:" + imei);
		}
		return null;
	}
}
