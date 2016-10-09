package com.oversea.log;

import java.util.Date;

import org.apache.log4j.Logger;

import com.oversea.ads.bean.RequestBean;
import com.oversea.ads.bean.ResponseBean;
import com.oversea.factory.OverseaTag;
import com.oversea.util.OverseaUtil;

public class OverseaLog
{
    protected static final Logger CLIENT_REQ_LOG = Logger.getLogger("client");   // 客户端请求
    protected static final Logger ADMIN_REQ_LOG  = Logger.getLogger("admin");    // 管理后台
    protected static final Logger USER_ERROR_LOG = Logger.getLogger("runerror"); // 运行错误
    protected static final Logger MOBILE_LOG     = Logger.getLogger("mobile");   // 无法识别的手机短信中心号
    protected static final Logger POSTBACK_LOG     = Logger.getLogger("postback");   // 廣告回調
    protected static final Logger PUSH_LOG     = Logger.getLogger("push");   // PUSH
    protected static final Logger ADS_LOG     = Logger.getLogger("ads");   // getAds3
    protected static final Logger ADSPUSH_LOG     = Logger.getLogger("adspush");   // getAds2
    protected static final Logger EVENT_LOG     = Logger.getLogger("event");   // event

    protected static final Logger FEEDBACK_LOG   = Logger.getLogger("feedback");
    protected static final Logger EXCEPTION_LOG  = Logger
                                                         .getLogger("exception");

    private static final int      BUFFER_SIZE    = 1024;

    public final static int       ERROR_LOG      = 1;
    public final static int       ADMIN_LOG      = 2;
    public final static int       CLIENT_LOG     = 3;


    private static void errorLog(RequestBean request, ResponseBean response,
            String result, long totaltime)
    {
        StringBuffer sb = new StringBuffer(BUFFER_SIZE);
        sb.append(OverseaUtil.getDateFormatString(new Date(), 4));
        sb.append("|");
        sb.append(request.getIp());
        sb.append("|");
        sb.append(request.getSrc());
        sb.append("|");
        sb.append(request.getValue(OverseaTag.client_id));
        sb.append("|");
        sb.append(request.getValue(OverseaTag.channel));
        sb.append("|");
        sb.append(request.getValue(OverseaTag.net));
        sb.append("|");
        sb.append(request.getValue(OverseaTag.imei));
        sb.append("|");
        sb.append(request.getValue(OverseaTag.imsi));
        sb.append("|");
        sb.append(request.toString());
        sb.append("|");
        sb.append(response.toString());
        sb.append("|");
        sb.append(totaltime);
        sb.append("|");
        sb.append(result);
        USER_ERROR_LOG.error(sb.toString());
    }

    public static void log(Exception e)
    {
        USER_ERROR_LOG.error("error", e);
    }

    // public static void mobilelog(Long mobile)
    // {
    // MOBILE_LOG.info(mobile);
    // }

    /**
     * 客户端支付请求日志
     * */
    private static void clientLog(RequestBean request, ResponseBean response,
            String result, long totaltime)
    {
        if (request == null)
        {
            return;
        }
        StringBuffer sb = new StringBuffer(BUFFER_SIZE);
        sb.append(OverseaUtil.getDateFormatString(new Date(), 4));
        sb.append("|");
        sb.append(request.getIp());
        sb.append("|");
        sb.append(request.getSrc());
        sb.append("|");
        sb.append(request.getValue(OverseaTag.client_id));
        sb.append("|");
        sb.append(request.getValue(OverseaTag.imsi));
        sb.append("|");
        sb.append(request.getValue(OverseaTag.imei));
        sb.append("|");
        sb.append(request.getValue(OverseaTag.channel));
        sb.append("|");
        sb.append(request.getValue(OverseaTag.net));
        sb.append("|");
        sb.append(request.getValue(OverseaTag.imei));
        sb.append("|");
        sb.append(request.getValue(OverseaTag.imsi));
        sb.append("|");
        sb.append(request.toString());
        sb.append("|");
        sb.append(response.toString());
        sb.append("|");
        sb.append(totaltime);
        sb.append("|");
        sb.append(result);
        CLIENT_REQ_LOG.info(sb.toString());
    }

    // /**
    // * 执行效率日志
    // * */
    // private static void userAccessLog(RequestBean request, String resultcode,
    // String result, long totaltime)
    // {
    // if (request != null)
    // {
    // StringBuffer sb = new StringBuffer(BUFFER_SIZE);
    // sb.append(CommonUtil.getDateFormatString(new Date(), 4));
    // sb.append("|");
    // sb.append(request.getIp());
    // sb.append("|");
    // sb.append(request.getValue("imsi"));
    // sb.append("|");
    // sb.append(request.getValue("imei"));
    // sb.append("|");
    // sb.append(request.getValue("mac"));
    // sb.append("|");
    // sb.append(request.getReqType());
    // sb.append("|");
    // sb.append(resultcode);
    // sb.append("|");
    // sb.append(totaltime);
    // sb.append("|");
    // sb.append(request.toString());
    // sb.append("|");
    // sb.append(result);
    // USER_ACCESS_LOG.info(sb);
    // }
    // }

    /**
     * 管理后台请求日志
     * */
    private static void adminLog(RequestBean request, ResponseBean response,
            String result, long totaltime)
    {
        if (request == null)
        {
            return;
        }
        StringBuffer sb = new StringBuffer(BUFFER_SIZE);
        sb.append(OverseaUtil.getDateFormatString(new Date(), 4));
        sb.append("|");
        sb.append(request.getIp());
        sb.append("|");
        sb.append(request.getSrc());
        sb.append("|");
        sb.append(request.getValue("session_uid"));
        sb.append("|");
        // sb.append(resultcode);
        // sb.append("|");
        sb.append(totaltime);
        sb.append("|");
        sb.append(request.toString());
        sb.append("|");
        sb.append(response.toString());
        sb.append("|");
        sb.append(result);
        ADMIN_REQ_LOG.info(sb.toString());
    }

    public static void feedback(RequestBean request)
    {
        if (request != null)
        {
            StringBuffer sb = new StringBuffer(BUFFER_SIZE);
            sb.append(OverseaUtil.getDateFormatString(new Date(), 4));
            sb.append("|");
            sb.append(request.getIp());
            sb.append("|");
            sb.append(request.toString());
            FEEDBACK_LOG.info(sb.toString());
        }
    }

    public static void feedback(RequestBean request, String channel,
            String client_id, int status)
    {
        if (request != null)
        {
            StringBuffer sb = new StringBuffer(BUFFER_SIZE);
            sb.append(OverseaUtil.getDateFormatString(new Date(), 4));
            sb.append("|");
            sb.append(request.getIp());
            sb.append("|");
            sb.append("status=");
            sb.append(String.valueOf(status));
            sb.append("&");
            sb.append("client_id=");
            sb.append(client_id);
            sb.append("&");
            sb.append("channel=");
            sb.append(channel);
            FEEDBACK_LOG.info(sb.toString());
        }
    }

    public static void feedbackError(RequestBean request)
    {
        if (request != null)
        {
            StringBuffer sb = new StringBuffer(BUFFER_SIZE);
            sb.append(OverseaUtil.getDateFormatString(new Date(), 4));
            sb.append("|");
            sb.append(request.getIp());
            sb.append("|");
            sb.append(request.toString());
            EXCEPTION_LOG.info(sb.toString());
        }
    }

    public static void mobileLog(String smsCenter)
    {
        if (smsCenter != null)
        {
            MOBILE_LOG.info(smsCenter);
        }
    }
    /**
     * 回调日志
     * @param smsCenter
     */
    public static void postbackLog(String smsCenter)
    {
    	if (smsCenter != null)
    	{
    		POSTBACK_LOG.info(smsCenter);
    	}
    }

    public static void feedbackLog(String smsCenter)
    {
        if (smsCenter != null)
        {
            FEEDBACK_LOG.info(smsCenter);
        }
    }
    /**
     * push日志
     * @param msg
     */
    public static void pushLog(String msg)
    {
    	if (msg != null)
    	{
    		PUSH_LOG.info(msg);
    	}
    }
    
    /**
     * ads日志
     * @param msg
     */
    public static void adsLog(String msg)
    {
    	if (msg != null)
    	{
    		ADS_LOG.info(msg);
    	}
    }
    /**
     * ads日志
     * @param msg
     */
    public static void adspushLog(String msg)
    {
    	if (msg != null)
    	{
    		ADSPUSH_LOG.info(msg);
    	}
    }

	public static void contextInsertLog(String context) {
		if(context!=null){
			EVENT_LOG.info(context);
		}
		
	}
    
    

}
