package com.oversea.factory;

import javax.servlet.http.HttpServletRequest;

public class OverseaSrcFactory
{
    public final static int SRC_GEN_DEVICE                  = 1;      // 注册devid
    public final static int SRC_GET_YEAHMOBI_ADS            = 10;     // 获取yeahmobi的所有的广告
    public final static int SRC_GET_AFFLIATE_ADS            = 11;     // 获取联盟所有的广告
    public final static int SRC_API_END                     = 100;

    public final static int SRC_GET_ADS                     = 101;    		 // 获取所有广告
    public final static int SRC_GET_TRACK                   = 102;   		 // 获得
    public final static int SRC_GET_ADSFROMHDFS                     = 103;   // 获取可下发的广告
    // track的地址
    public final static int SRC_APIV2_END                   = 200;

    // 后台管理相关的接口
    public final static int SRC_ADMIN_START                 = 200;
    public final static int SRC_ADMIN_LOGIN                 = 201;
    public final static int SRC_ADMIN_LOGOUT                = 202;
    public final static int SRC_ADMIN_LOAD_MENU             = 203;
    public final static int SRC_ADMIN_LOAD_USERNAME         = 204;
    public final static int SRC_ADMIN_LOAD_PROVIDER         = 205;
    public final static int SRC_ADMIN_LOAD_AD               = 206;
    public final static int SRC_ADMIN_EDIT_AD               = 207;

    public final static int SRC_ADMIN_LOAD_APK              = 208;    // 查询apk的接口
    public final static int SRC_ADMIN_LOAD_APK_ADD          = 209;    // 查询apk的接口

    public final static int SRC_ADMIN_LOAD_OFFERSTATIS      = 210;    // 查询offer的数据

    public final static int SRC_ADMIN_LOAD_OFFERCHANNELADD  = 211;    // 查询offer的数据

    public final static int SRC_ADMIN_LOAD_OFFERCHANNEL     = 212;    // 查询offer的数据

    public final static int SRC_ADMIN_LOAD_OFFER_1DAY       = 213;    // 查询offer1day的数据
    public final static int SRC_ADMIN_LOAD_APK_1DAY         = 214;    // 查询apk1day的数据
    
    public final static int SRC_ADMIN_LOAD_DEMENSION        = 215;    // 查询维度名称
    public final static int SRC_ADMIN_LOAD_WEIGHT       	=216;		//查询各个维度的数据
    public final static int SRC_ADMIN_LOAD_DIMENSIONUPDATE 	=217;		//维度维护(查询)
    public final static int SRC_ADMIN_EDIT_STATUS			=218;		//修改维度状态
    public final static int SRC_ADMIN_CHANGE_WEIGHT			=219;		//修改维度状态
    public final static int SRC_ADMIN_LOAD_REALTIME_STATIS  = 220;    // 查询offer实时数据的数据
    public final static int SRC_ADMIN_LOAD_ADES              =221;
    public final static int SRC_ADMIN_COUNT_REAL_DATA              =222;//实时数据
    
    public final static int SRC_ADMIN_WAP_SAVE 				= 223;	  // 保存数据
    public final static int SRC_ADMIN_WAP_CHANGE_STATUS		= 224;    // 修改状态
    public final static int SRC_ADMIN_LOAD_WAP				= 225;	  // 查询adwap数据
    public final static int SRC_ADMIN_LOAD_OFFERCHANGE				= 226;	  // 查询offer换量数据
    public final static int SRC_ADMIN_SAVE_OFFERCHANGE				= 227;	  // 保存offer换量数据
    public final static int SRC_ADMIN_ADD_OFFERCHANGE				= 228;	  // 添加offer换量数据
    

    public final static int SRC_ADMIN_END                   = 400;

    public final static int SRC_GET_STATIS                  = 600;    // 客户端上报统计接口
    public final static int SRC_GET_ERRORINFO               = 601;    // 客户端上报统计接口
    public final static int SRC_GET_EVENT         			 = 602;    // 客户端上报事件接口

    public final static int SRC_FOR_FINDLOGMMANAGE          = 611;    // 对于需要查看流程的log的日志的控制，

    public final static int SRC_FOR_FINDLOG                 = 612;    // 对于需要查看流程的log的收集，会直接保存到log中，慢慢分析

    public final static int SRC_GET_CLASSAPK                = 700;    // 文件夹的分类接口
    public final static int SRC_GET_ADLIST                  = 701;    // 获取广告列表
    public final static int SRC_GET_AD_LIST_FL                = 702;    // 根据供应商(FL前缀)获取广告列表
    
    
    public final static int SRC_MASTER_UPDATE              =800; //主apk的更新
    public final static int SRC_MASTER_MONITOR             =801; //主apk的检测
    
    public final static int SRC_SLAVES_UPDATE_MONITOR      =811;//从apk的更新和检测
    
    public final static int SRC_SYSTEM_SLAVE_MONITOR       =821;//system下的从的apk的检测更新
    //TODO 
    public final static int SRC_JIE_KOU       =822;	//咱们需要新实现个接口，接收如下字段信息上传到s3服务器
    
    public final static int SRC_GETAPP_STATIS      =900;    //统计手机上app的信息的接口
    public final static int SRC_GETADS_FOR_OLD = 910;//用于老的push的提供广告的接口
    
    public final static int SRC_GETADS_FOR_PC =920; //为 TY那边单独定制的，只给他返回纯应用的apk列表
    public final static int SRC_POSTBACK_START              = 1000;
   
    
    public static final String   SRC_POSTBACK_Cat_ava_api4  = "caa4";
    
    public final static int SRC_POSTBACK_END                = 1999;

    // Excel 相关
    public final static int EXPORT_EXCEL_4IP                = 400001;
    public final static int EXPORT_EXCEL_4PAD               = 400002;
    public final static int EXPORT_EXCEL_4REALDATA           = 400003;
    
    /**
     * 清除广告下发缓存，安全接口，需提供KEY
     */
    public final static int SAFE_API_CLEAR_CACHE_AD_PUSH = 500001;

    /**
     * 根据请求的URL来判断来源
     * */
    public static String getSrc(final HttpServletRequest request)
    {
        // String src = request.getServletPath();
        String src = request.getRequestURI().substring(
                request.getContextPath().length());
     if (src.contains("kaoba"))
        {
            int startIndex = src.indexOf("kaoba/") + 6;
            int endIndex = src.lastIndexOf('/');
            if (startIndex >= endIndex)
            {
                endIndex = src.length();
            }
            src = src.substring(startIndex, endIndex);
        
        }
	return src;
       
    }
}
