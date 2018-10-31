package com.bracelet.service.impl;

import com.bracelet.entity.Fence;
import com.bracelet.entity.OddShape;
import com.bracelet.entity.UserInfo;
import com.bracelet.service.IFenceService;
import com.bracelet.service.IFencelogService;
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
public class FenceServiceImpl implements IFenceService {
	@Autowired
	JdbcTemplate jdbcTemplate;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	IFencelogService fencelogService;

	@Override
	public Fence getOne(Long user_id) {
		String sql = "select * from fence where user_id=? LIMIT 1";
		List<Fence> list = jdbcTemplate.query(sql, new Object[] { user_id }, new BeanPropertyRowMapper<Fence>(Fence.class));

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("get return null.user_id:" + user_id);
		}
		return null;
	}

	@Override
	public boolean insert(Long user_id, String lat, String lng, Integer radius) {
		Fence fence = this.getOne(user_id);
		if (fence != null) {
			logger.warn("用户[" + user_id + "]已经设置过电子围栏[" + lat + "][" + lng + "][" + radius + "]!");
			return false;
		}

		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"insert into fence (user_id, lat, lng, radius, createtime, updatetime) values (?,?,?,?,?,?)",
				new Object[] { user_id, lat, lng, radius, now, now },
				new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.TIMESTAMP, Types.TIMESTAMP });
		return i == 1;
	}

	@Override
	public boolean update(Long id, Long user_id, String lat, String lng, Integer radius) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"update fence set lat = ?, lng = ?, radius = ?, updatetime = ? where id = ? and user_id = ?",
				new Object[] { lat, lng, radius, now, id, user_id },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.TIMESTAMP, Types.INTEGER, Types.INTEGER });
		return i == 1;
	}

	@Override
	public boolean delete(Long id, Long user_id) {
		int i = jdbcTemplate.update("delete from fence where id = ? and user_id = ?", new Object[] { id, user_id },
				new int[] { Types.INTEGER, Types.INTEGER });
		return i == 1;
	}

	@Override
	public void checkFenceArea(Long user_id, String imei, String lat, String lng, long time) {
		Fence fence = this.getOne(user_id);
		boolean needReport = false;
		int newStatus = 0;
		if (fence != null && !StringUtils.isEmpty(fence.getLng()) && !StringUtils.isEmpty(fence.getLat())) {
			double distance = Utils.calcDistance(Double.parseDouble(lng), Double.parseDouble(lat),
					Double.parseDouble(fence.getLng()), Double.parseDouble(fence.getLat()));
			if (distance < fence.getRadius()) {
				// 电子围栏内
				if (fence.getStatus() != 1) {
					newStatus = 1;
					needReport = true;
				}
			} else {
				// 电子围栏外
				if (fence.getStatus() != 2) {
					newStatus = 2;
					needReport = true;
				}
			}
			if (needReport) {
				// 更新status
				this.updateStatus(fence.getId(), newStatus);
				// 报警
				UserInfo userInfo = this.userInfoService.getUserInfoById(user_id);
				if (userInfo != null && !StringUtils.isEmpty(userInfo.getUsername())) {
					String type = newStatus == 1 ? "进入" : "离开";
					String timeStr = Utils.format14DateString(time);
					SmsUtil.sendSms("电子围栏报警", userInfo.getUsername(), "SMS_99420011",
							"{\"type\":\"" + type + "\", \"time\":\"" + timeStr + "\"}");
					// save fencelog
					this.fencelogService.insert(user_id, imei, fence.getLat(), fence.getLng(), fence.getRadius(), lat, lng,
							newStatus, "设备" + type + "电子围栏", new Timestamp(time));
				}
			}
		}

	}

	@Override
	public boolean updateStatus(Long id, Integer status) {
		int i = jdbcTemplate.update("update fence set status = ? where id = ?", new Object[] { status, id },
				new int[] { Types.INTEGER, Types.INTEGER });
		return i == 1;
	}

	@Override
	public boolean insertOddShape(Long user_id, String point) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate.update(
				"insert into odd_shape (user_id, point, createtime, updatetime) values (?,?,?,?)",
				new Object[] { user_id, point, now, now },
				new int[] { Types.INTEGER, Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP });
		return i == 1;
	}

	@Override
	public OddShape getOddShape(Long user_id) {
		String sql = "select * from odd_shape where user_id=? LIMIT 1";
		List<OddShape> list = jdbcTemplate.query(sql, new Object[] { user_id }, new BeanPropertyRowMapper<OddShape>(OddShape.class));

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("get odd_shape return null.user_id:" + user_id);
		}
		return null;
	}

	@Override
	public boolean deleteOddShape(Long id, Long user_id) {
		int i = jdbcTemplate.update("delete from odd_shape where id = ? and user_id = ?", new Object[] { id, user_id },
				new int[] { Types.INTEGER, Types.INTEGER });
		return i == 1;
	}

}
