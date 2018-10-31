package com.bracelet.service;

import com.bracelet.entity.SensitivePointLog;
import java.sql.Timestamp;
import java.util.List;

public interface ISensitivePointlogService {

	boolean insert(Long user_id, Long sp_id, String imei, String lat, String lng, Integer radius, String lat1,
			String lng1, Integer status, String content, Timestamp upload_time);

	List<SensitivePointLog> findAllByCount(Long user_id, Integer status, Integer limit);

	List<SensitivePointLog> findByCount(Long user_id, Long sp_id, Integer status, Integer limit);

}
