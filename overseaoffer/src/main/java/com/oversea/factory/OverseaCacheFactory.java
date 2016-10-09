package com.oversea.factory;

import java.util.concurrent.TimeoutException;

import com.oversea.log.OverseaLog;
import com.oversea.util.OverseaSpringHelper;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

public class OverseaCacheFactory
{
    /**
     * 1分钟过期
     * */
    public static final int        ONE_MINUTE  = 60;
    /**
     * 1小时过期
     * */
    public static final int        ONE_HOUR    = 60 * 60;
    /**
     * 1天过期
     * */
    public static final int        ONE_DAY     = 60 * 60 * 24;
    /**
     * 1周过期
     * */
    public static final int        ONE_WEEK    = 60 * 60 * 24 * 7;
    /**
     * 1月过期
     * */
    public static final int        ONE_MONTH   = 60 * 60 * 24 * 30; // 最大不能超过30天
    /**
     * 永不过期
     * */
    public static final int        ALWAYS      = 0;

    private static MemcachedClient cacheClient = null;

    public static MemcachedClient getInstance()
    {
        if (cacheClient == null)
        {
            cacheClient = (MemcachedClient) OverseaSpringHelper
                    .getBean("memcachedClient");
        }
        return cacheClient;
    }

    public static void setMemcachedClient(MemcachedClient cachedClient)
    {
        if (cachedClient != null)
        {
            cacheClient = cachedClient;
        }
    }

    /**
     * 缓存对象
     * */
    public static void add(String key, Object obj, int time)
    {
        MemcachedClient memcache = getInstance();
        try
        {
            memcache.add(key, time, obj);
        }
        catch (TimeoutException e)
        {
            OverseaLog.log(e);
        }
        catch (InterruptedException e)
        {
            OverseaLog.log(e);
        }
        catch (MemcachedException e)
        {
            OverseaLog.log(e);
        }
        memcache = null;
    }

    /**
     * 获取对象
     * */
    public static Object get(String key)
    {
        MemcachedClient memcache = getInstance();
        Object obj = null;
        try
        {
            if (memcache != null)
            {
                obj = memcache.get(key);
            }
        }
        catch (TimeoutException e)
        {
            OverseaLog.log(e);
        }
        catch (InterruptedException e)
        {
            OverseaLog.log(e);
        }
        catch (MemcachedException e)
        {
            OverseaLog.log(e);
        }
        memcache = null;
        return obj;
    }

    /**
     * 删除对象
     * */
    public static void delete(String key)
    {
        MemcachedClient memcache = getInstance();
        try
        {
            if (memcache != null)
            {
                memcache.delete(key);
            }
        }
        catch (TimeoutException e)
        {
            OverseaLog.log(e);
        }
        catch (InterruptedException e)
        {
            OverseaLog.log(e);
        }
        catch (MemcachedException e)
        {
            OverseaLog.log(e);
        }
    }

    /**
     * 清除所有缓存
     */
    public static void flushAll()
    {
        MemcachedClient memcache = getInstance();
        try
        {
            if (memcache != null)
            {
                memcache.flushAll();
            }
        }
        catch (TimeoutException e)
        {
            OverseaLog.log(e);
        }
        catch (InterruptedException e)
        {
            OverseaLog.log(e);
        }
        catch (MemcachedException e)
        {
            OverseaLog.log(e);
        }
    }
}
