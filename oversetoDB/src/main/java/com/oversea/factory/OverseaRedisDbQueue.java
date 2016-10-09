package com.oversea.factory;

import com.alibaba.fastjson.JSON;

public class OverseaRedisDbQueue
{
    private static OverseaRedisDbQueue instance = null;

    public static OverseaRedisDbQueue getInstance()
    {
        if (instance == null)
        {
            instance = new OverseaRedisDbQueue();
        }
        return instance;
    }

    public Long push(String key, Object obj)
    {
        return OverseaRedisFactory.lpush(key, toJson(obj));
    }

    public String toJson(Object obj)
    {
        return JSON.toJSONString(obj);
    }

}
