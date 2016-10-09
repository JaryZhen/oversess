package com.oversea.factory;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class OverseaRedisFactory
{


	/**
	 * 1分钟过期
	 * */
	public static final int ONE_MINUTE = 60;
	/**
	 * 1小时过期
	 * */
	public static final int ONE_HOUR = 60 * 60;
	/**
	 * 1天过期
	 * */
	public static final int ONE_DAY = 60 * 60 * 24;
	/**
	 * 1周过期
	 * */
	public static final int ONE_WEEK = 60 * 60 * 24 * 7;
	/**
	 * 1月过期
	 * */
	public static final int ONE_MONTH = 60 * 60 * 24 * 30; // 最大不能超过30天
	/**
	 * 永不过期
	 * */
	public static final int ALWAYS = 0;

	
	private static RedisConfig redisConfig = null;
	
	private static JedisCluster getInstance() {
		return RedisFactoryHolder.jedisCluster;
	}
	/**
	 * 防止高并发时创建多个pool
	 * @author demingchen
	 *
	 */
	private static class RedisFactoryHolder{
		private static JedisCluster jedisCluster = null;
		static{
			if (jedisCluster == null) {
				Set<HostAndPort> nodes = new HashSet<HostAndPort>();
				nodes.add(new HostAndPort(redisConfig.getHost(), redisConfig.getPort()));
				jedisCluster = new JedisCluster(nodes, redisConfig.getJedisPoolConfig());
			}
		}
	}
	
	public void setRedisConfig(RedisConfig config){
		redisConfig = config;
	}

	/**
	 * 从队列的左边取出一条数据
	 * 
	 * @param key
	   *            　列表名
	 * @return
	 */
	public static String lpop(String key) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			return redis.lpop(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从队列的右边取出一条数据
	 * 
	 * @param key
	 *            列表名
	 * @return
	 */
	public static String rpop(String key) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			return redis.rpop(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 把一个值添加到对应列表中
	 * 
	 * @param key
	 *            列表名
	 * @param index
	 *            　添加的位置
	 * @param value
	 *            　数据
	 * @return
	 */
	public static String lset(String key, long index, String value) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			return redis.lset(key, index, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Long lpush(String key, String value) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			String[] strs = new String[1];
			strs[0] = value;
			long ret = redis.lpush(key, strs);
			redis.expire(key, OverseaRedisFactory.ONE_DAY);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 把所有数据添加到一个列表中
	 * 
	 * @param key
	 *            列表名
	 * @param values
	 *            　数据
	 * @return
	 */
	public static Long lpush(String key, String[] values) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			return redis.lpush(key, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 把所有数据添加到一个列表中,并且设置列表的存活时间
	 * 
	 * @param key
	 *            列表名
	 * @param values
	 *            数据
	 * @param liveTime
	 *            存活时间--单位(秒)
	 * @return
	 */
	public static Long lpush(String key, String[] values, int liveTime) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			Long result = redis.lpush(key, values);
			redis.expire(key, liveTime);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除列表中对应值的元素
	 * 
	 * @param key
	 *            列表名
	 * @param count
	 *            删除多少个相同的元素
	 * @param value
	 *            数据
	 * @return
	 */
	public static Long lrem(String key, long count, String value) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			return redis.lrem(key, count, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 返回指定列表范围内的元素
	 * 
	 * @param key
	 *            列表名
	 * @param start
	 *            开始位置
	 * @param end
	 *            结束位置
	 * @return
	 */
	public static List<String> lrange(String key, long start, long end) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			return redis.lrange(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Long llen(String key) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			return redis.llen(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过key删除
	 * 
	 * @param key
	 */
	public static void del(String key) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			redis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加key value 并且设置存活时间
	 * 
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public static void set(String key, String value, int liveTime) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			redis.set(key, value);
			redis.expire(key, liveTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加key value
	 * 
	 * @param key
	 * @param value
	 */
	public static void set(String key, String value) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			redis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取redis value (String)
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			String value = redis.get(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String hget(String key, String field) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			return redis.hget(key, field);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static long hset(String key, String field, String value) {
		return hset(key, field, value, OverseaRedisFactory.ONE_DAY);
	}

	public static long hset(String key, String field, String value, int liveTime) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			long ret = redis.hset(key, field, value);
			redis.expire(key, liveTime);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static List<String> hmget(String key, String... fields) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			return redis.hmget(key, fields);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String hmset(String key, Map<String, String> value) {
		return hmset(key, value, OverseaRedisFactory.ONE_DAY);
	}

	public static String hmset(String key, Map<String, String> value,
			int liveTime) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			String ret = redis.hmset(key, value);
			redis.expire(key, liveTime);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static long hdel(String key, String field) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			long ret = redis.hdel(key, field);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String set(String key, Map<String, String> map) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			return redis.hmset(key, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, String> getAllmap(String key) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			return redis.hgetAll(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> get(String key, String... fields) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			return redis.hmget(key, fields);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, String> hgetAll(String key) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			return redis.hgetAll(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static long hincrBy(String key, String field, long value,
			int liveTime) {
		JedisCluster redis = null;
		long ret = 0L;
		try {
			redis = getInstance();
			ret = redis.hincrBy(key, field, value);
			redis.expire(key, liveTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static void incr(String key) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			redis.incr(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void incr(String key, int liveTime) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			redis.incr(key);
			redis.expire(key, liveTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void incrBy(String key, long incr, int liveTime) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			redis.incrBy(key, incr);
			redis.expire(key, liveTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public static void sadd(String key, String member, int liveTime) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			redis.sadd(key, member);
			redis.expire(key, liveTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public static Set<String> smember(String key) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			return redis.smembers(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public static Boolean sismember(String key, String member) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			return redis.sismember(key, member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * 
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public static long scard(String key) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			return redis.scard(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 添加key value 并且设置存活时间
	 * 
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public static void expire(String key, int liveTime) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			redis.expire(key, liveTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检查key是否已经存在
	 * 
	 * @param key
	 * @return
	 */
	public static Boolean exists(String key) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			return redis.exists(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 加入对集合set的操作的，添加一个值
	public static void setSetAdd(String key, String... value) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			redis.sadd(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// smembers(key) ：返回名称为key的set的所有元素
	public static Set<String> getSetmembers(String key) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			Set<String> value = redis.smembers(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// sismember(key, member) ：测试member是否是名称为key的set的元素
	public static Boolean getIsSetmembers(String key, String member) {
		JedisCluster redis = null;
		try {
			redis = getInstance();
			Boolean value = redis.sismember(key, member);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
