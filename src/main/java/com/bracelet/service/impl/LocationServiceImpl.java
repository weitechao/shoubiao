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

import com.bracelet.datasource.DataSourceChange;
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

	@DataSourceChange(slave = true)
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
		List<Location> list = jdbcTemplate.query(sql, new Object[] { user_id, dateString },
				new BeanPropertyRowMapper<Location>(Location.class));
		return list;
	}

	@DataSourceChange(slave = true)
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

	@DataSourceChange(slave = true)
	public Location getRealtimeLocation(Long user_id, Integer status) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, -15);
		String dateString = Utils.format14DateString(calendar.getTime());
		String sql = "select * from location where user_id=? and status= ? and upload_time > ? order by upload_time desc LIMIT 1";
		List<Location> list = jdbcTemplate.query(sql, new Object[] { user_id, status, dateString },
				new BeanPropertyRowMapper<Location>(Location.class));

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("getLatest return null.user_id:" + user_id);
		}
		return null;
	}

	public boolean insert(Long user_id, String location_type, String lat, String lng, Integer accuracy, Integer status,
			Timestamp timestamp) {
		int i = jdbcTemplate.update(
				"insert into location (user_id, location_type, lat, lng, accuracy, status, upload_time) values (?,?,?,?,?,?,?)",
				new Object[] { user_id, location_type, lat, lng, accuracy, status, timestamp },
				new int[] { java.sql.Types.INTEGER, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR,
						java.sql.Types.VARCHAR, java.sql.Types.INTEGER, java.sql.Types.INTEGER,
						java.sql.Types.TIMESTAMP });
		return i == 1;
	}

	@Override
	public boolean insertOldLocation(String phone, String lat, String lng) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update("insert into location_old (phone, lat, lng, upload_time) values (?,?,?,?)",
				new Object[] { phone, lat, lng, now },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP });
		return i == 1;
	}

	@Override
	@DataSourceChange(slave = true)
	public LocationOld getOldLocationLatest(String phone) {
		String sql = "select * from location_old where phone=? order by id desc LIMIT 1";
		List<LocationOld> list = jdbcTemplate.query(sql, new Object[] { phone },
				new BeanPropertyRowMapper<LocationOld>(LocationOld.class));

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("getOldLocationLatest return null.user_id:" + phone);
		}
		return null;
	}

	@Override
	@DataSourceChange(slave = true)
	public List<LocationOld> getOldPhoneFootprint(String imei, String startime, String endtime) {
		String sql = "select * from location_old where phone=? and upload_time > ? and upload_time < ? order by upload_time asc";
		List<LocationOld> list = jdbcTemplate.query(sql, new Object[] { imei, startime, endtime },
				new BeanPropertyRowMapper<LocationOld>(LocationOld.class));
		return list;
	}

	@Override
	public boolean insertUdInfo(String imei, Integer locationType, String lat, String lon, String status, String time,
			Integer locationStyle) {
		Timestamp now = Utils.getCurrentTimestamp();
		// 1正常2报警3天气4拍照
		String table = "location_watchinfo";
		if (locationStyle == 2) {
			table = "sos_location_watchinfo";
		} else if (locationStyle == 4) {
			table = "photo_location_watchinfo";
		} else {
			Integer count = Integer.valueOf(imei.substring(imei.length() - 1, imei.length())) % 20;
			if (count == 1) {
				table = "location_1_watchinfo";
			} else if (count == 2) {
				table = "location_2_watchinfo";
			} else if (count == 3) {
				table = "location_3_watchinfo";
			}else if (count == 4) {
				table = "location_4_watchinfo";
			}else if (count == 5) {
				table = "location_5_watchinfo";
			}else if (count == 6) {
				table = "location_6_watchinfo";
			}else if (count == 7) {
				table = "location_7_watchinfo";
			}else if (count == 8) {
				table = "location_8_watchinfo";
			}else if (count == 9) {
				table = "location_9_watchinfo";
			}else if (count == 10) {
				table = "location_10_watchinfo";
			}else if (count == 11) {
				table = "location_11_watchinfo";
			}else if (count == 12) {
				table = "location_12_watchinfo";
			}else if (count == 13) {
				table = "location_13_watchinfo";
			}else if (count == 14) {
				table = "location_14_watchinfo";
			}else if (count == 15) {
				table = "location_15_watchinfo";
			}else if (count == 16) {
				table = "location_16_watchinfo";
			}else if (count == 17) {
				table = "location_17_watchinfo";
			}else if (count == 18) {
				table = "location_18_watchinfo";
			}else if (count == 19) {
				table = "location_19_watchinfo";
			}else if (count == 20) {
				table = "location_20_watchinfo";
			}
		}
		int i = jdbcTemplate.update(
				"insert into  " + table
						+ "   (imei, location_type, lat, lng, status, location_time, upload_time, location_style) values (?,?,?,?,?,?,?,?)",
				new Object[] { imei, locationType, lat, lon, status, time, now, locationStyle },
				new int[] { Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
						Types.TIMESTAMP, Types.INTEGER });
		return i == 1;
	}

	@Override
	@DataSourceChange(slave = true)
	public LocationWatch getLatest(String imei) {

		String table = "location_watchinfo";

		Integer count = Integer.valueOf(imei.substring(imei.length() - 1, imei.length())) % 20;
		if (count == 1) {
			table = "location_1_watchinfo";
		} else if (count == 2) {
			table = "location_2_watchinfo";
		} else if (count == 3) {
			table = "location_3_watchinfo";
		}else if (count == 4) {
			table = "location_4_watchinfo";
		}else if (count == 5) {
			table = "location_5_watchinfo";
		}else if (count == 6) {
			table = "location_6_watchinfo";
		}else if (count == 7) {
			table = "location_7_watchinfo";
		}else if (count == 8) {
			table = "location_8_watchinfo";
		}else if (count == 9) {
			table = "location_9_watchinfo";
		}else if (count == 10) {
			table = "location_10_watchinfo";
		}else if (count == 11) {
			table = "location_11_watchinfo";
		}else if (count == 12) {
			table = "location_12_watchinfo";
		}else if (count == 13) {
			table = "location_13_watchinfo";
		}else if (count == 14) {
			table = "location_14_watchinfo";
		}else if (count == 15) {
			table = "location_15_watchinfo";
		}else if (count == 16) {
			table = "location_16_watchinfo";
		}else if (count == 17) {
			table = "location_17_watchinfo";
		}else if (count == 18) {
			table = "location_18_watchinfo";
		}else if (count == 19) {
			table = "location_19_watchinfo";
		}else if (count == 20) {
			table = "location_20_watchinfo";
		}

		String sql = "select * from  " + table + "  where imei=? order by upload_time desc LIMIT 1";
		List<LocationWatch> list = jdbcTemplate.query(sql, new Object[] { imei },
				new BeanPropertyRowMapper<LocationWatch>(LocationWatch.class));

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("getLatestLocaiton return null.user_id:" + imei);
		}
		return null;
	}

	@Override
	@DataSourceChange(slave = true)
	public List<LocationWatch> getWatchFootprint(String imei, String starttime, String endtime) {
		String table = "location_watchinfo";
		Integer count = Integer.valueOf(imei.substring(imei.length() - 1, imei.length())) % 20;
		if (count == 1) {
			table = "location_1_watchinfo";
		} else if (count == 2) {
			table = "location_2_watchinfo";
		} else if (count == 3) {
			table = "location_3_watchinfo";
		}else if (count == 4) {
			table = "location_4_watchinfo";
		}else if (count == 5) {
			table = "location_5_watchinfo";
		}else if (count == 6) {
			table = "location_6_watchinfo";
		}else if (count == 7) {
			table = "location_7_watchinfo";
		}else if (count == 8) {
			table = "location_8_watchinfo";
		}else if (count == 9) {
			table = "location_9_watchinfo";
		}else if (count == 10) {
			table = "location_10_watchinfo";
		}else if (count == 11) {
			table = "location_11_watchinfo";
		}else if (count == 12) {
			table = "location_12_watchinfo";
		}else if (count == 13) {
			table = "location_13_watchinfo";
		}else if (count == 14) {
			table = "location_14_watchinfo";
		}else if (count == 15) {
			table = "location_15_watchinfo";
		}else if (count == 16) {
			table = "location_16_watchinfo";
		}else if (count == 17) {
			table = "location_17_watchinfo";
		}else if (count == 18) {
			table = "location_18_watchinfo";
		}else if (count == 19) {
			table = "location_19_watchinfo";
		}else if (count == 20) {
			table = "location_20_watchinfo";
		}

		String sql = "select * from  " + table
				+ "  where imei=? and upload_time > ? and upload_time < ? order by id asc  limit 5";
		List<LocationWatch> list = jdbcTemplate.query(sql, new Object[] { imei, starttime, endtime },
				new BeanPropertyRowMapper<LocationWatch>(LocationWatch.class));
		return list;
	}

	@Override
	public boolean insertUdPhotoInfo(String imei, Integer locationType, String lat, String lon, String status,
			String time, Integer locationStyle, String photoName) {
		Timestamp now = Utils.getCurrentTimestamp();

		int i = jdbcTemplate.update(
				"insert into  photo_location_watchinfo  (imei, location_type, lat, lng, status, location_time, upload_time, location_style,photo_name) values (?,?,?,?,?,?,?,?,?)",
				new Object[] { imei, locationType, lat, lon, status, time, now, locationStyle, photoName },
				new int[] { Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
						Types.TIMESTAMP, Types.INTEGER, Types.VARCHAR });
		return i == 1;
	}

	@Override
	public boolean deleteByImei(String imei) {
		
		String table = "location_watchinfo";

		Integer count = Integer.valueOf(imei.substring(imei.length() - 1, imei.length())) % 20;
		if (count == 1) {
			table = "location_1_watchinfo";
		} else if (count == 2) {
			table = "location_2_watchinfo";
		} else if (count == 3) {
			table = "location_3_watchinfo";
		}else if (count == 4) {
			table = "location_4_watchinfo";
		}else if (count == 5) {
			table = "location_5_watchinfo";
		}else if (count == 6) {
			table = "location_6_watchinfo";
		}else if (count == 7) {
			table = "location_7_watchinfo";
		}else if (count == 8) {
			table = "location_8_watchinfo";
		}else if (count == 9) {
			table = "location_9_watchinfo";
		}else if (count == 10) {
			table = "location_10_watchinfo";
		}else if (count == 11) {
			table = "location_11_watchinfo";
		}else if (count == 12) {
			table = "location_12_watchinfo";
		}else if (count == 13) {
			table = "location_13_watchinfo";
		}else if (count == 14) {
			table = "location_14_watchinfo";
		}else if (count == 15) {
			table = "location_15_watchinfo";
		}else if (count == 16) {
			table = "location_16_watchinfo";
		}else if (count == 17) {
			table = "location_17_watchinfo";
		}else if (count == 18) {
			table = "location_18_watchinfo";
		}else if (count == 19) {
			table = "location_19_watchinfo";
		}else if (count == 20) {
			table = "location_20_watchinfo";
		}
		
		
		jdbcTemplate.update("delete from "+ table + "   where   imei = ?",
				new Object[] { imei }, new int[] { Types.VARCHAR });
		return true;
	}
}
