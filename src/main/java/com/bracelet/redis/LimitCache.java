package com.bracelet.redis;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

/**
 * <p>
 * Title:LimitCache
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company:
 * </p>
 *
 * @author yehb
 * @date 2016年5月17日 上午10:54:02
 */
//@Service("limitCache")
@Repository("limitCache")
public class LimitCache extends BasicRedisSupport {

	/**
	 * 是否频繁访问
	 * 
	 * @param imsi
	 * @return
	 */
	public boolean isFrequentvisit(String imsi) {
		boolean flag = true;
		Jedis jedis = getJedis();
		String key = imsi;
		boolean exists = jedis.exists(key);
		Long now = System.currentTimeMillis();
		if (exists) {// 如果存在
			Long last = Long.valueOf(jedis.get(key));
			/*
			 * if (now-last >= App.frequentTime) { flag = false; }
			 */
		} else {
			flag = false;
		}
		jedis.set(key, now.toString());
		returnResource(jedis);
		return flag;
	}

	// 增加key value
	public boolean addKey(String key, String value) {
		boolean flag = true;
		Jedis jedis = getJedis();
		jedis.set(key, value);
		flag = jedis.exists(key);
		returnResource(jedis);
		return flag;
	}

	// 删除key
	public boolean deleteKey(String key) {
		boolean flag = true;
		Jedis jedis = getJedis();
		jedis.del(key);
		flag = jedis.exists(key);
		returnResource(jedis);
		return flag;
	}

	// dbsize：返回当前数据库中key的数目
	public Long getSize() {
		Long size;
		Jedis jedis = getJedis();
		size = jedis.dbSize();
		returnResource(jedis);
		return size;
	}
}
