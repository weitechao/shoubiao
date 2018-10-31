package com.bracelet.service;

import com.bracelet.entity.Pushlog;

public interface IPushlogService {

	boolean insert(Long user_id, String imei, Integer type, String target, String title, String content);

	Pagination<Pushlog> find(Long user_id, PageParam pageParam);

}
