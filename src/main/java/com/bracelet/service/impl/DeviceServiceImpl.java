package com.bracelet.service.impl;

import com.bracelet.datasource.DataSourceChange;
import com.bracelet.entity.DeviceManagePhone;
import com.bracelet.entity.IpAddressInfo;
import com.bracelet.entity.WatchDevice;
import com.bracelet.entity.WatchDeviceAlarm;
import com.bracelet.entity.WatchDeviceBak;
import com.bracelet.entity.WatchDeviceHomeSchool;
import com.bracelet.service.IDeviceService;
import com.bracelet.util.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

@Service
public class DeviceServiceImpl implements IDeviceService {
	@Autowired
	JdbcTemplate jdbcTemplate;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	@DataSourceChange(slave = true)
	public WatchDevice getDeviceInfo(String addimei) {
		String sql = "select * from device_watch_info where imei=? LIMIT 1";
		List<WatchDevice> list = jdbcTemplate.query(sql, new Object[] { addimei },
				new BeanPropertyRowMapper<WatchDevice>(WatchDevice.class));

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("get getDeviceInfo imei:" + addimei);
		}
		return null;
	}

	@Override
	@DataSourceChange(slave = true)
	public List<IpAddressInfo> getipinfo() {
		String sql = "select * from ip_info where status=1  LIMIT 5";
		List<IpAddressInfo> list = jdbcTemplate.query(sql, new Object[] {},
				new BeanPropertyRowMapper<IpAddressInfo>(IpAddressInfo.class));
		return list;
	}

	@Override
	public boolean insertParameter(String imei, String parameter) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update("insert into watch_parameter_info (imei, parameter, createtime) values (?,?,?)",
				new Object[] { imei, parameter, now },
				new int[] { java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.TIMESTAMP });
		return i == 1;
	}

	@Override
	public boolean insertNewImei(String imei, String phone, int typeOfOperator, String dv) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"insert into device_watch_info (imei, phone, nickname, dv, createtime, updatetime,type,school_age) values (?,?,?,?,?,?,?,?)",
				new Object[] { imei, phone, imei, dv, now, now, typeOfOperator,"1" },
				new int[] { java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR,
						java.sql.Types.VARCHAR, java.sql.Types.TIMESTAMP, java.sql.Types.TIMESTAMP,
						java.sql.Types.INTEGER ,java.sql.Types.VARCHAR});
		return i == 1;
	}

	@Override
	public boolean updateImeiInfo(Long id, String phone, int typeOfOperator, String dv) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update("update device_watch_info set phone=? ,dv=?,type=?,updatetime=? where id = ?",
				new Object[] { phone, dv, typeOfOperator, now, id }, new int[] { Types.VARCHAR, Types.VARCHAR,
						Types.INTEGER, java.sql.Types.TIMESTAMP, java.sql.Types.INTEGER });
		return i == 1;
	}

	@Override
	public boolean updateImeiInfo(Long id, String imei, String phone, String nickname, Integer sex, String birday,
			String school_age, String school_info, String home_info, String weight, String height, String head) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"update device_watch_info set phone=? ,nickname=?,sex=?,birday=?, school_age=?, school_info=?, home_info=?, weight=?, height=?, head=? , updatetime=? where id = ?",
				new Object[] { phone, nickname, sex, birday, school_age, school_info, home_info, weight, height, head,
						now, id },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
						Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, java.sql.Types.TIMESTAMP,
						java.sql.Types.INTEGER });
		return i == 1;
	}

	@Override
	public boolean insertDeviceImeiInfo(String imei, String phone, String nickname, Integer sex, String birday,
			String school_age, String school_info, String home_info, String weight, String height, String head) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"insert into device_watch_info (imei, phone, nickname, sex, birday, school_age, school_info, home_info, head, weight, height, createtime, updatetime) values (?,?,?,?,?,?,?,?,?,?,?,?,?)",
				new Object[] { imei, phone, nickname, sex, birday, school_age, school_info, home_info, head, weight,
						height, now, now },
				new int[] { java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR,
						java.sql.Types.INTEGER, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR,
						java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR,
						java.sql.Types.TIMESTAMP, java.sql.Types.TIMESTAMP });
		return i == 1;
	}

	@Override
	public boolean updateImeiHeadInfo(Long id, String head) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update("update device_watch_info set  head=? , updatetime=? where id = ?",
				new Object[] { head, now, id },
				new int[] { Types.VARCHAR, java.sql.Types.TIMESTAMP, java.sql.Types.INTEGER });
		return i == 1;
	}

	@Override
	public boolean updateImeiHomeAndFamilyInfo(Long id, String school_info, String home_info) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"update device_watch_info set  school_info=?, home_info=? updatetime=? where id = ?",
				new Object[] { school_info, home_info, now, id },
				new int[] { Types.VARCHAR, Types.VARCHAR, java.sql.Types.TIMESTAMP, java.sql.Types.INTEGER });
		return i == 1;
	}

	@Override
	public boolean updateImeiNotHomeAndFamilyInfo(Long id, String imei, String phone, String nickname, Integer sex,
			String birday, String school_age, String weight, String height, String head) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate
				.update("update device_watch_info set phone=? ,nickname=?,sex=?,birday=?, school_age=?, weight=?, height=?, head=? , updatetime=? where id = ?",
						new Object[] { phone, nickname, sex, birday, school_age, weight, height, head, now, id },
						new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR,
								Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, java.sql.Types.TIMESTAMP,
								java.sql.Types.INTEGER });
		return i == 1;
	}

	@Override
	@DataSourceChange(slave = true)
	public WatchDeviceHomeSchool getDeviceHomeAndFamilyInfo(String imei) {
		String sql = "select * from device_watch_hf_info where imei=? LIMIT 1";
		List<WatchDeviceHomeSchool> list = jdbcTemplate.query(sql, new Object[] { imei },
				new BeanPropertyRowMapper<WatchDeviceHomeSchool>(WatchDeviceHomeSchool.class));

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("get getDeviceInfo imei:" + imei);
		}
		return null;
	}

	@Override
	public boolean insertDeviceHomeAndFamilyInfo(Long id, String imei, String schoolAddress, String classDisable1,
			String classDisable2, String weekDisable1, String schoolLat, String schoolLng, String latestTime,
			String homeAddress, String homeLng, String homeLat) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"insert into device_watch_hf_info (w_id, imei, createtime, updatetime, schoolAddress, classDisable1, classDisable2, weekDisable1, schoolLat, schoolLng, latestTime, homeAddress, homeLng, homeLat) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
				new Object[] { id, imei, now, now, schoolAddress, classDisable1, classDisable2, weekDisable1, schoolLat,
						schoolLng, latestTime, homeAddress, homeLng, homeLat },
				new int[] { java.sql.Types.INTEGER, java.sql.Types.VARCHAR, java.sql.Types.TIMESTAMP,
						java.sql.Types.TIMESTAMP, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR,
						java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR,
						java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR,
						java.sql.Types.VARCHAR });
		return i == 1;
	}

	@Override
	public boolean updateImeiHomeAndFamilyInfoById(Long id, String classDisable1, String classDisable2,
			String weekDisable, String schoolAddress, String schoolLat, String schoolLng, String latestTime,
			String homeAddress, String homeLat, String homeLng) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"update device_watch_hf_info set updatetime=?, schoolAddress=?, classDisable1=?, classDisable2=?, weekDisable1=?, schoolLat=?, schoolLng=?, latestTime=?, homeAddress=?, homeLng=?, homeLat=? where id = ?",
				new Object[] { now, schoolAddress, classDisable1, classDisable2, weekDisable, schoolLat, schoolLng,
						latestTime, homeAddress, homeLng, homeLat, id },
				new int[] { Types.TIMESTAMP, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
						Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
						java.sql.Types.INTEGER });
		return i == 1;
	}

	@Override
	public boolean insertNewImeiBak(Long id, String imei) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update("insert into device_watch_bak_info (d_id, imei, createtime) values (?,?,?)",
				new Object[] { id, imei, now },
				new int[] { java.sql.Types.INTEGER, java.sql.Types.VARCHAR, java.sql.Types.TIMESTAMP });
		return i == 1;
	}

	@Override
	public boolean updateImeiNumberById(Long id, String familyNumber, String shortNumber) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"update device_watch_info set  short_number=?, family_number=? updatetime=? where id = ?",
				new Object[] { shortNumber, familyNumber, now, id },
				new int[] { Types.VARCHAR, Types.VARCHAR, java.sql.Types.TIMESTAMP, java.sql.Types.INTEGER });
		return i == 1;
	}

	@Override
	@DataSourceChange(slave = true)
	public WatchDeviceBak getDeviceBakInfo(String imei) {
		String sql = "select id, d_id, imei ,createtime   from device_watch_bak_info where imei=? LIMIT 1";
		List<WatchDeviceBak> list = jdbcTemplate.query(sql, new Object[] { imei },
				new BeanPropertyRowMapper<WatchDeviceBak>(WatchDeviceBak.class));

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("get getDeviceInfo imei:" + imei);
		}
		return null;
	}

	@Override
	public boolean updateImeiHeadInfoByImei(Long id, String head) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update("update device_watch_info set  head=? , updatetime=? where id = ?",
				new Object[] { head, now, id },
				new int[] { Types.VARCHAR, java.sql.Types.TIMESTAMP, java.sql.Types.INTEGER });
		return i == 1;
	}

	@Override
	public boolean updateWatchImeiInfoByImei(String imei, String phone, String nickname, Integer sex, String birday,
			String school_age, String weight, String height, String familyNumber, String shortNumber) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"update device_watch_info set phone=? ,nickname=?, sex=?, birday=?, school_age=?, weight=?, height=?, short_number=?, family_number=? , updatetime=? where imei = ?",
				new Object[] { phone, nickname, sex, birday, school_age,
						weight, height, shortNumber,familyNumber,now,
						imei },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, 
						Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, 
						java.sql.Types.TIMESTAMP ,Types.VARCHAR});
		return i == 1;
	}

	@Override
	public WatchDeviceHomeSchool getDeviceHomeAndFamilyInfoByImei(String imei) {
		String sql = "select * from device_watch_hf_info where imei=? LIMIT 1";
		List<WatchDeviceHomeSchool> list = jdbcTemplate.query(sql, new Object[] { imei },
				new BeanPropertyRowMapper<WatchDeviceHomeSchool>(WatchDeviceHomeSchool.class));

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("get getDeviceHomeAndFamilyInfoByImei imei:" + imei);
		}
		return null;
	}

	@Override
	public boolean updateWatchImeiInfoById(Long id, String phone, String nickname, Integer sex, String birday,
			String school_age, String weight, String height, String familyNumber, String shortNumber) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"update device_watch_info set phone=? ,nickname=?, sex=?, birday=?, school_age=?, weight=?, height=?, short_number=?, family_number=? , updatetime=? where id = ?",
				new Object[] { phone, nickname, sex, birday, school_age,
						weight, height, shortNumber,familyNumber,now,
						id },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, 
						Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, 
						java.sql.Types.TIMESTAMP ,Types.INTEGER});
		return i == 1;
	}

	@Override
	public WatchDeviceAlarm getDeviceAlarmInfo(String imei) {
		String sql = "select * from device_watch_alarm_info where imei=? LIMIT 1";
		List<WatchDeviceAlarm> list = jdbcTemplate.query(sql, new Object[] { imei },
				new BeanPropertyRowMapper<WatchDeviceAlarm>(WatchDeviceAlarm.class));

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("get getDeviceInfo imei:" + imei);
		}
		return null;
	}

	@Override
	public boolean updateWatchAlarmInfoById(Long id, String weekAlarm1, String weekAlarm2, String weekAlarm3,
			String alarm1, String alarm2, String alarm3) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"update device_watch_alarm_info set weekAlarm1=? ,weekAlarm2=?, weekAlarm3=?, alarm1=?, alarm2=?, alarm3=?, updatetime=? where id = ?",
				new Object[] { weekAlarm1, weekAlarm2, weekAlarm3, alarm1, alarm2,
						alarm3,now,
						id },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, 
						Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, 
						java.sql.Types.TIMESTAMP ,Types.INTEGER});
		return i == 1;
	}

	@Override
	public boolean insertDeviceAlarmInfo(String imei, String weekAlarm1, String weekAlarm2, String weekAlarm3,
			String alarm1, String alarm2, String alarm3) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"insert into device_watch_alarm_info ( imei, createtime, updatetime, weekAlarm1, weekAlarm2, weekAlarm3, alarm1, alarm2, alarm3) values (?,?,?,?,?,?,?,?,?)",
				new Object[] { imei, now, now, weekAlarm1,weekAlarm2,weekAlarm3,alarm1,alarm2,alarm3},
				new int[] { java.sql.Types.VARCHAR, java.sql.Types.TIMESTAMP,java.sql.Types.TIMESTAMP, 
						java.sql.Types.VARCHAR, java.sql.Types.VARCHAR,java.sql.Types.VARCHAR, 
						java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR,
						 });
		return i == 1;
	}

	@Override
	public boolean deleteDeviceAlarmInfo(String imei) {
		int i = jdbcTemplate.update(
				"delete from device_watch_alarm_info where imei = ?",
				new Object[] { imei }, new int[] { Types.VARCHAR});
		return i == 1;
	}

	@Override
	public boolean updateAdminPhoneById(Long id, String phone) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update("update device_manage_phone set phone=? ,createtime=? where id = ?",
				new Object[] { phone, now, id }, new int[] { Types.VARCHAR,java.sql.Types.TIMESTAMP, java.sql.Types.INTEGER });
		return i == 1;
	}

	@Override
	public DeviceManagePhone getManagePhoneByImei(String imei) {
		String sql = "select * from device_manage_phone where imei=? LIMIT 1";
		List<DeviceManagePhone> list = jdbcTemplate.query(sql, new Object[] { imei },
				new BeanPropertyRowMapper<DeviceManagePhone>(DeviceManagePhone.class));

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("get DeviceManagePhone imei:" + imei);
		}
		return null;
	}

	@Override
	public boolean insertDeviceAdminPhone(String imei, String phone) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update("insert into device_manage_phone (imei, tel, createtime) values (?,?,?)",
				new Object[] { imei, phone, now },
				new int[] { java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.TIMESTAMP });
		return i == 1;
	}

	@Override
	public boolean deleteBindDevicebyImei(String imei) {
		int i = jdbcTemplate.update(
				"delete from watch_bind_device where b_imei = ?",
				new Object[] { imei }, new int[] { Types.VARCHAR});
		return i == 1;
	}

	
	
}
