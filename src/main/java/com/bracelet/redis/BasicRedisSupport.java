package com.bracelet.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class BasicRedisSupport {
	private static Log logger = LogFactory.getLog(BasicRedisSupport.class.getName());
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private RedisClient redisClient;
	
	public RedisClient getRedisClient() {
		return redisClient;
	}

	public void setRedisClient(RedisClient redisClient) {
		this.redisClient = redisClient;
	}
//从池中获取一个jedis对象
	public Jedis getJedis() {
		try {
			logger.info("getResource redis size is -------->>>"+jedisPool.getNumActive() +" getNumIdle: " + jedisPool.getNumIdle() + " getNumWaiters: " + jedisPool.getNumWaiters() );
			return jedisPool.getResource();
		} catch (Exception e) {
			logger.error("getRedisClent error : " + e.getMessage());
		}
		return null;
	}
//释放对象池
	public void returnResource(Jedis jedis) {
		logger.info("before close redis size is -------->>>"+jedisPool.getNumActive() +" getNumIdle: " + jedisPool.getNumIdle() + " getNumWaiters: " + jedisPool.getNumWaiters() );
		if (jedis != null) {
			jedis.close();
		}
		logger.info("after close redis size is -------->>>"+jedisPool.getNumActive() +" getNumIdle: " + jedisPool.getNumIdle() + " getNumWaiters: " + jedisPool.getNumWaiters() );
	}
}
