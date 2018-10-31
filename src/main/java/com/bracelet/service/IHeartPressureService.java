package com.bracelet.service;

import java.sql.Timestamp;
import com.bracelet.entity.HeartPressure;
import com.bracelet.service.PageParam;
import com.bracelet.service.Pagination;

public interface IHeartPressureService {

	boolean insert(Long user_id, Integer max_heart_pressure, Integer min_heart_pressure, Timestamp timestamp);

	/**
	 * 查询最近的血压
	 * 
	 * @param user_id
	 * @return
	 */
	HeartPressure getLatest(Long user_id);

	Pagination<HeartPressure> find(Long user_id, PageParam pageParam);

}
