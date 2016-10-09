package com.oversea.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.oversea.ads.bean.OverseaAd;
import com.oversea.ads.bean.OverseaAd_Tem;

public class OverseaRedisDb
{
    private static OverseaRedisDb instance = null;

    public static OverseaRedisDb getInstance()
    {
        if (instance == null)
        {
            instance = new OverseaRedisDb();
        }
        return instance;
    }

    public long insert(String key, OverseaAd ad)
    {
        if (ad == null)
        {
            return 0;
        }
        String k = String.valueOf(ad.getId());
        long ret = OverseaRedisFactory.hset(key, k, toJson(ad));
        
        return ret;
    }
    
    public boolean delete(String key, OverseaAd ad)
    {
        boolean ret = false;
        if (ad == null)
        {
            return ret;
        }
        String k = String.valueOf(ad.getId());
        long r = OverseaRedisFactory.hdel(key, k);
        ret = r==1 ? true: false;
        return ret;
    }
    
    public boolean delete_cache(String key, String index)
    {
        boolean ret = false;
       
        String k = index;//String.valueOf(ad.getId());
        long r = OverseaRedisFactory.hdel(key, k);
        ret = r==1 ? true: false;
        return ret;
    }
    public List<OverseaAd> replaceAll(String key, List<OverseaAd> list)
    {
        if (list == null || list.size() <= 0)
        {
            return null;
        }
        OverseaRedisFactory.del(key);
        Map<String, String> map = new HashMap<String, String>();
        for (OverseaAd item : list)
        {
            String k = String.valueOf(item.getId());
            map.put(k, toJson(item));
        }
        OverseaRedisFactory.hmset(key, map);
        return list;
    }
    
    public OverseaAd get(String key, long id)
    {
        String k = String.valueOf(id);
        String value = OverseaRedisFactory.hget(key, k);
        OverseaAd item = JSON.parseObject(value, OverseaAd.class);
        return item;
    }
    //jary 10-23
    public List<OverseaAd_Tem> getAll_Afil(String key)
    {
        List<OverseaAd_Tem> list = new ArrayList<OverseaAd_Tem>();
        Map<String, String> map = OverseaRedisFactory.hgetAll(key);
        for(String k : map.keySet())
        {
            String value = map.get(k);
            OverseaAd_Tem item = JSON.parseObject(value, OverseaAd_Tem.class);
            list.add(item);
        }
        return list;
    }
    
    public List<OverseaAd> getAll(String key)
    {
        List<OverseaAd> list = new ArrayList<OverseaAd>();
        Map<String, String> map = OverseaRedisFactory.hgetAll(key);
        for(String k : map.keySet())
        {
            String value = map.get(k);
            OverseaAd item = JSON.parseObject(value, OverseaAd.class);
            list.add(item);
        }
        return list;
    }
    
    public boolean update(String key, OverseaAd ad)
    {
        boolean ret = false;
        if (ad == null)
        {
            return ret;
        }
        String k = String.valueOf(ad.getId());
        long r = OverseaRedisFactory.hset(key, k, toJson(ad));
        ret = r == 1 ? true : false;
        return ret;
    }

    public Object pop(String key)
    {
        String json = OverseaRedisFactory.lpop(key);
        Object obj = JSON.parseObject(json,
                Object.class);
        return obj;
    }
    
    public String toJson(Object obj)
    {
        return JSON.toJSONString(obj);
    }


    /**
     * 将最新的状态为0的offer放入redis缓存
     * 
     * @author chenbq
     * 
     * @return
     */
    public boolean updateRedisFromDb(List<OverseaAd> adList)
    {
    	//清除现有的key
    	Map<String, String> keysMap = OverseaRedisFactory.getAllmap(OverseaEnum.REDIS_KEY_DB_COUNTRY_KEYS);
    	if(keysMap != null){
    		for (String key : keysMap.keySet()) {
    			OverseaRedisFactory.del(key);
        	}
    	}  
    	
    	OverseaRedisFactory.del(OverseaEnum.REDIS_KEY_DB_COUNTRY_KEYS);
    	
    	//添加新的offer数据
    	OverseaRedisDb.getInstance().replaceByCountry(adList);
        
        return true;
        
    }
    
    /**
     * 替换所有数据库offer到redis
     * @param key    每个国家对应的key
     * @param countryKeys	记录增加的所有keys
     * @param list       需要替换的广告列表
     * @return     
     */
    public List<OverseaAd> replaceByCountry(List<OverseaAd> list)
    {
    	
        if (list == null || list.size() <= 0)
        {
            return null;
        }
        for (OverseaAd item : list)
        {
        	updateOfferByCountry(item);
        }
        return list;
    }
    
    /**
     * 增加单个offer到redis
     * @param key
     * @param countryKeys
     * @param ad
     */
    public void updateOfferByCountry(OverseaAd ad)
    {
        String couKey = "";
        Map<String, String> keysMap = OverseaRedisFactory.getAllmap(OverseaEnum.REDIS_KEY_DB_COUNTRY_KEYS);
        
        if (ad == null)
        {
            return ;
        }
        String k = String.valueOf(ad.getId());
        String country  = ad.getCountry();
        if(country.equalsIgnoreCase("ALL")){
        	couKey = OverseaEnum.REDIS_KEY_DB_COUNTRY + "ALL";
        	keysMap.put(couKey, "ALL");
        	OverseaRedisFactory.hset(couKey, k, toJson(ad));
        }else if(country.indexOf(":") > 0){
        	String[] countryList = country.split(":");
            for(int i = 0;i< countryList.length;i++){
            	couKey = OverseaEnum.REDIS_KEY_DB_COUNTRY + countryList[i].trim();
            	keysMap.put(couKey, countryList[i]);
            	OverseaRedisFactory.hset(couKey, k, toJson(ad));
            }
        }else{
        	couKey = OverseaEnum.REDIS_KEY_DB_COUNTRY + country;
        	keysMap.put(couKey, country);
        	OverseaRedisFactory.hset(couKey, k, toJson(ad));
        }
        OverseaRedisFactory.set(OverseaEnum.REDIS_KEY_DB_COUNTRY_KEYS, keysMap);
    }
}
