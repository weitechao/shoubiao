package com.bracelet.service;

import com.bracelet.entity.SensitivePoint;
import java.util.List;

public interface ISensitivePointService {

	List<SensitivePoint> find(Long user_id);

	boolean insert(Long user_id, String lat, String lng, Integer radius);

	boolean update(Long id, Long user_id, String lat, String lng, Integer radius);

	boolean delete(Long id, Long user_id);

	void checkSensitivePointArea(Long user_id, String imei, String lat, String lng, long time);

	boolean updateStatus(Long id, Integer status);

}
