package com.oversea.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.oversea.ads.util.OverseaDateUtil;

public class OverseaRedisUtil
{
	private final static Logger                  logger         = Logger
	.getLogger(OverseaRedisUtil.class);
	/**
	 * 1分钟过期
	 * */
	 public static final int                      REDIS_INTERVAL = 5 * 60 * 1000;
	 private static Map<String, OverseaRedisItem> getMap         = new HashMap<String, OverseaRedisItem>();
	 private static ConcurrentHashMap<String, OverseaRedisItem> addMap         = new ConcurrentHashMap<String, OverseaRedisItem>();
	 private static long                          getEndTime     = OverseaDateUtil
	 .endTime();
	 private static long                          addEndTime     = OverseaDateUtil
	 .endTime();

	 public static Map<Long, Integer> getAll(String key)
	 {
		 long now = System.currentTimeMillis();
		 OverseaRedisItem item = getMap.get(key);
		 if ((item == null) || (now - item.getTime() > REDIS_INTERVAL))
		 {
			 if (item == null)
			 {
				 item = new OverseaRedisItem();
				 item.setValue(new HashMap<Long, Integer>());
				 getMap.put(key, item);
			 }
			 Map<String, String> map = OverseaRedisFactory.hgetAll(key);
			 if (map != null && map.size() > 0)
			 {
				 Map<Long, Integer> valueMap = new HashMap<Long, Integer>();
				 for (String k : map.keySet())
				 {
					 String v = map.get(k);
					 valueMap.put(Long.valueOf(k), Integer.valueOf(v));
				 }
				 item.setValue(valueMap);
			 }
			 item.setTime(now);

			 if (getEndTime < now)
			 {
				 getEndTime = OverseaDateUtil.endTime();
				 getMap.clear();
			 }
		 }

		 return item.getValue();
	 }

	 public static void incr(String key, List<Long> list, int liveTime)
	 {
		 //         logger.info("incrtest init... ...");
		 long now = System.currentTimeMillis();
		 OverseaRedisItem item = addMap.get(key);
		 //         logger.info("111 init... ...");
		 if (item == null)
		 {
			 item = new OverseaRedisItem();
			 item.setTime(0L);
			 item.setValue(new HashMap<Long, Integer>());
			 addMap.put(key, item);
		 }
		 //         logger.info("222 init... ...");
		 Map<Long, Integer> map = item.getValue();
		 if (map == null)
		 {
			 map = new HashMap<Long, Integer>();
		 }
		 //         logger.info("333 init... ...");
		 for (Long id : list)
		 {
			 Integer v = map.get(id);
			 if (v == null)
			 {
				 v = 0;
			 }
			 map.put(id, v + 1);
		 }
		 // logger.info("item.getTime()==null:"+item.getTime()==null);
		 if (item.getTime() == null || now - item.getTime() > REDIS_INTERVAL)
		 {
			 Map<Long, Integer> writeMap = new HashMap<Long, Integer>();
			 Map<String, OverseaRedisItem> writeAddMap = null;

			 for (Long k : map.keySet())
			 {
				 Integer v = map.get(k);
				 writeMap.put(k, v);
				 //                OverseaRedisFactory.hincrBy(key, String.valueOf(k), Long
				 //                        .valueOf(v), liveTime);
				 map.put(k, 0);
			 }
			 item.setTime(now);

			 if (addEndTime < now)
			 {
				 addEndTime = OverseaDateUtil.endTime();
				 writeAddMap = new HashMap<String, OverseaRedisItem>();
				 for (String k : addMap.keySet())
				 {
					 OverseaRedisItem v = addMap.get(k);
					 OverseaRedisItem addItem = new OverseaRedisItem();
					 map = v.getValue();
					 if (map != null)
					 {
						 Map<Long, Integer> writeItemMap = new HashMap<Long, Integer>();
						 for (Long k2 : map.keySet())
						 {
							 Integer v2 = map.get(k2);
							 //                            OverseaRedisFactory.hincrBy(key, String.valueOf(k2), Long
									 //                                    .valueOf(v2), liveTime);
							 writeItemMap.put(k2, v2);
						 }
						 addItem.setValue(writeItemMap);
					 }
					 writeAddMap.put(k, addItem);
				 }
				 addMap.clear();
			 }
			 writeRedis(writeAddMap, writeMap, key, liveTime);
		 }
	 }

	 public static void writeRedis(final Map<String, OverseaRedisItem> addMap, final Map<Long, Integer> map, final String key, final int liveTime)
	 {
		 new Thread(new Runnable()
		 {
			 public void run()
			 {
				 if (map != null)
				 {
					 for (Long k : map.keySet())
					 {
						 Integer v = map.get(k);
						 OverseaRedisFactory.hincrBy(key, String.valueOf(k), Long
								 .valueOf(v), liveTime);
					 }
				 }

				 if (addMap != null)
				 {
					 Map<Long, Integer> map2;
					 for (String k : addMap.keySet())
					 {
						 OverseaRedisItem v = addMap.get(k);
						 map2 = v.getValue();
						 if (map2 != null)
						 {
							 for (Long k2 : map2.keySet())
							 {
								 Integer v2 = map2.get(k2);
								 OverseaRedisFactory.hincrBy(key, String.valueOf(k2), Long
										 .valueOf(v2), liveTime);
							 }
						 }
					 }
					 addMap.clear();
				 }
			 }
		 }).start();
	 }

}

