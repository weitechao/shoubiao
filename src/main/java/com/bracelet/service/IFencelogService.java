package com.bracelet.service;

import com.bracelet.entity.Fencelog;
import java.sql.Timestamp;
import java.util.List;

public interface IFencelogService {

	boolean insert(Long user_id, String imei, String lat, String lng, Integer radius, String lat1, String lng1,
			Integer status, String content, Timestamp upload_time);

	List<Fencelog> findByCount(Long user_id, Integer status, Integer limit);

}
