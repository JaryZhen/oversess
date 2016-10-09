package com.oversea.ads.util;

import java.util.Date;

import com.oversea.factory.OverseaRedisFactory;

public class OverseaStatis
{
    private static OverseaStatis instance         = null;

    private static final String  REDIS_KEY_PREFIX = "overseapay-redis-statis#";

    private static final String  ADD_USER_KEY     = REDIS_KEY_PREFIX
                                                          + "adduser#";
    private static final String  ACTIVE_USER_KEY  = REDIS_KEY_PREFIX
                                                          + "activeuser#";

    // minutes
    private static long          startTime        = 0L;
    private static long          endTime          = 0L;

    public static OverseaStatis getInstance()
    {
        if (instance == null)
        {
            instance = new OverseaStatis();
        }
        return instance;
    }

    private static void checkTime()
    {
        long now = new Date().getTime();
        if (now > endTime)
        {
            startTime = OverseaDateUtil.startTime();
            endTime = OverseaDateUtil.endTime();
        }
    }

    public static void addUser(String channel, String appid, String country,
            String clientid, Boolean isnew)
    {
        checkTime();

        String key = startTime + "#" + channel + "#" + appid + "#" + country;

        OverseaRedisFactory.sadd(ADD_USER_KEY + key, clientid,
                OverseaRedisFactory.ONE_MONTH);
        OverseaRedisFactory.sadd(ACTIVE_USER_KEY + key, clientid,
                OverseaRedisFactory.ONE_MONTH);
    }
}
