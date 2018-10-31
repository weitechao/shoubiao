package com.bracelet.service.impl;

import com.bracelet.entity.SensitivePoint;
import com.bracelet.entity.UserInfo;
import com.bracelet.service.ISensitivePointService;
import com.bracelet.service.ISensitivePointlogService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.util.SmsUtil;
import com.bracelet.util.Utils;

import org.apache.commons.lang3.StringUtils;
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
public class SensitivePointServiceImpl implements ISensitivePointService {
	@Autowired
	JdbcTemplate jdbcTemplate;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	ISensitivePointlogService sensitivePointlogService;

	@Override
	public List<SensitivePoint> find(Long user_id) {
		String sql = "select * from sensitive_point where user_id=?";
		List<SensitivePoint> list = jdbcTemplate.query(sql, new Object[] { user_id },
				new BeanPropertyRowMapper<SensitivePoint>(SensitivePoint.class));
		return list;
	}

	@Override
	public boolean insert(Long user_id, String lat, String lng, Integer radius) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"insert into sensitive_point (user_id, lat, lng, radius, createtime, updatetime) values (?,?,?,?,?,?)",
				new Object[] { user_id, lat, lng, radius, now, now },
				new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.TIMESTAMP, Types.TIMESTAMP });
		return i == 1;
	}

	@Override
	public boolean update(Long id, Long user_id, String lat, String lng, Integer radius) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"update sensitive_point set lat = ?, lng = ?, radius = ?, updatetime = ? where id = ? and user_id = ?",
				new Object[] { lat, lng, radius, now, id, user_id },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.TIMESTAMP, Types.INTEGER, Types.INTEGER });
		return i == 1;
	}

	@Override
	public boolean delete(Long id, Long user_id) {
		int i = jdbcTemplate.update("delete from sensitive_point where id = ? and user_id = ?",
				new Object[] { id, user_id }, new int[] { Types.INTEGER, Types.INTEGER });
		return i == 1;
	}

	@Override
	public void checkSensitivePointArea(Long user_id, String imei, String lat, String lng, long time) {
		List<SensitivePoint> splist = this.find(user_id);
		if (splist != null && splist.size() > 0) {
			boolean isUserInfoNotNull = false;
			UserInfo userInfo = this.userInfoService.getUserInfoById(user_id);
			if (userInfo != null && !StringUtils.isEmpty(userInfo.getUsername())) {
				isUserInfoNotNull = true;
			}
			for (SensitivePoint sp : splist) {
				boolean needReport = false;
				int newStatus = 0;
				if (sp != null && !StringUtils.isEmpty(sp.getLng()) && !StringUtils.isEmpty(sp.getLat())) {
					double distance = Utils.calcDistance(Double.parseDouble(lng), Double.parseDouble(lat),
							Double.parseDouble(sp.getLng()), Double.parseDouble(sp.getLat()));
					if (distance < sp.getRadius()) {
						// 敏感区域内
						if (sp.getStatus() != 1) {
							newStatus = 1;
							needReport = true;
						}
					} else {
						// 敏感区域外
						if (sp.getStatus() != 2) {
							newStatus = 2;
							needReport = true;
						}
					}
					if (needReport) {
						// 更新status
						this.updateStatus(sp.getId(), newStatus);
						// 报警
						if (isUserInfoNotNull) {
							String type = newStatus == 1 ? "进入" : "离开";
							String timeStr = Utils.format14DateString(time);
							// TODO 
							SmsUtil.sendSms("敏感区域报警", userInfo.getUsername(), "SMS_99420011",
									"{\"type\":\"" + type + "\", \"time\":\"" + timeStr + "\"}");
							// save SensitivePointlog
							this.sensitivePointlogService.insert(user_id, sp.getId(), imei, sp.getLat(), sp.getLng(), sp.getRadius(),
									lat, lng, newStatus, "设备" + type + "敏感区域", new Timestamp(time));
						}
					}
				}
			}
		}

	}

	@Override
	public boolean updateStatus(Long id, Integer status) {
		int i = jdbcTemplate.update("update sensitive_point set status = ? where id = ?", new Object[] { status, id },
				new int[] { Types.INTEGER, Types.INTEGER });
		return i == 1;
	}

}
