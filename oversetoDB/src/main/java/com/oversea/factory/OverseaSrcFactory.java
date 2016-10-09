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
    public final static int SRC_POSTBACK_YEAHMOBI_AFFLIATE  = 1001;
    public final static int SRC_POSTBACK_AVAZU_API          = 1002;
    public final static int SRC_POSTBACK_APPNEXT            = 1003;
    public final static int SRC_POSTBACK_TAPJOY             = 1004;
    public final static int SRC_POSTBACK_OMG                = 1005;
    public final static int SRC_POSTBACK_TAPTICA            = 1006;
    public final static int SRC_POSTBACK_SUPERSNOIC         = 1007;
    public final static int SRC_POSTBACK_HUMMEROFFER        = 1008;
    public final static int SRC_POSTBACK_CLICKDEALER        = 1009;
    public final static int SRC_POSTBACK_CAKE               = 1010;
    public final static int SRC_POSTBACK_DOMOBMEDIA         = 1011;
    public final static int SRC_POSTBACK_MOBPARTNR        	= 1012;
    public final static int SRC_POSTBACK_GLISPA        		= 1013;
    public final static int SRC_POSTBACK_ARTOFCLICK        	= 1014;
    public final static int SRC_POSTBACK_DUOMENG        	= 1015;
    public final static int SRC_POSTBACK_PERFORMANCE        	= 1016;
    public static final int SRC_POSTBACK_APPNEXT2 		= 1017;
    public static final int SRC_POSTBACK_GLISPA2 		= 1018;
    public static final int SRC_POSTBACK_APPCOACHS 		= 1019;
    public static final int SRC_POSTBACK_APPLIFT        = 1020;
    public static final int SRC_POSTBACK_CLICKY 		= 1021;
    public static final int SRC_POSTBACK_TAPTICA2 		= 1022;
    public static final int SRC_POSTBACK_MOBUSI 		= 1023;
    public static final int SRC_POSTBACK_IRONSOURCE 	= 1024;
    public static final int SRC_POSTBACK_APPNEXT3 		= 1025;
    public static final int SRC_POSTBACK_BATMOBI 		= 1026;
    public static final int SRC_POSTBACK_GLOBALFAST  	= 1027;
    public static final int SRC_POSTBACK_APPIA      	= 1028;
    public static final int SRC_POSTBACK_TYROO      	= 1029;
    public static final int SRC_POSTBACK_LEADHUG     	= 1030;
    public static final int SRC_POSTBACK_YEAHMOBI_API2  = 1031;
    public static final int SRC_POSTBACK_YEAHMOBI_API3  = 1032;
    public final static int SRC_POSTBACK_ARTOFCLICK2  	= 1033;//手动的
    public static final int SRC_POSTBACK_IRONSOURCE2 	= 1034;
    public static final int SRC_POSTBACK_MOBISUMMER      = 1035;
    public static final int SRC_POSTBACK_MOBILETRAFFIC   = 1036;
    public static final int SRC_POSTBACK_STARTAPP_NGE    = 1037;
    public static final int SRC_POSTBACK_ROCKYMOBI_ADYUN  = 1038;
    public static final int SRC_POSTBACK_AVAZU_API4		= 1039;
    public static final int SRC_POSTBACK_CORDRIM		= 1040;
    public static final int SRC_POSTBACK_APPCOACH_MAN	= 1041;
    public static final int SRC_POSTBACK_FURTHERMOBI    = 1042;
    public static final int SRC_POSTBACK_FANCYLOCKER	 =1043;
    public static final int SRC_POSTBACK_ROCKYMOBI_nge  = 1044;
    public static final int  SRC_POSTBACK_SHARKGAMES    = 1045;
    public static final int  SRC_POSTBACK_FL_AV_API    = 1046;
    public static final int   SRC_POSTBACK_FL_MB2_API  = 1047;
    public static final int  SRC_POSTBACK_TAIGAMOBI_API1_YM = 1048;
    public static final int  SRC_POSTBACK_Fl_ST3_API     = 1049;
    public static final int SRC_POSTBACK_PUBNATIVE_api1_n  = 1050;
    public static final int SRC_POSTBACK_SP_AV3_API   = 1051;
    public static final int SRC_POSTBACK_FL_tryoo3_API = 1052;
    public static final int SRC_POSTBACK_TrafficSteer_api1_ym = 1053;
    public static final int  SRC_POSTBACK_Tatatoo_api1_ym = 1054;
    public static final int  SRC_POSTBACK_Adscend_media_api1_ym = 1055;
    public static final int  SRC_POSTBACK_Zephyr_api1_n   = 1056;
    public static final int SRC_POSTBACK_Mybestclick_api1_n = 1058;
    public static final int  SRC_POSTBACK_Fl_Mobquid_api1   = 1059;
    public static final int  SRC_POSTBACK_Fl_artofclick_api3 = 1060;
    public static final int   SRC_POSTBACK_Tapcash_ym  = 1061;
    
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
    public static int getSrc(final HttpServletRequest request)
    {
        // String src = request.getServletPath();
        String src = request.getRequestURI().substring(
                request.getContextPath().length());
        
//        String string=request.getContextPath();
//        String ssString=request.getRequestURI();
        if (src.contains("api"))
        {
            int startIndex = src.indexOf("overseaads/") + 6;
            int endIndex = src.lastIndexOf('/');
            if (startIndex >= endIndex)
            {
                endIndex = src.length();
            }
            src = src.substring(startIndex, endIndex);
            int ret = Integer.valueOf(src).intValue();
            return ret;
        }
        else if (src.contains("info"))
        {
            // int startIndex = src.indexOf("ggview/") + 6;
            // int endIndex = src.lastIndexOf('/');
            // if (startIndex >= endIndex)
            // {
            // endIndex = src.length();
            // }
            // src = src.substring(startIndex, endIndex);
            // int ret = Integer.valueOf(src).intValue();
            int start = src.indexOf("info/") + 5;
            src = src.substring(start, src.length());
            int ret = Integer.valueOf(src).intValue();
            return ret;
        }
        else if (src.contains("ppafo"))
        {
            // int startIndex = src.indexOf("ggview/") + 6;
            // int endIndex = src.lastIndexOf('/');
            // if (startIndex >= endIndex)
            // {
            // endIndex = src.length();
            // }
            // src = src.substring(startIndex, endIndex);
            // int ret = Integer.valueOf(src).intValue();
            int start = src.indexOf("ppafo/") + 6;
            src = src.substring(start, src.length());
            int ret = Integer.valueOf(src).intValue();
            return ret;
        }

        else if (src.contains("adminmanager"))
        {
            int startIndex = src.indexOf("adminmanager/") + 13;
            int endIndex = src.lastIndexOf('/');
            if (startIndex >= endIndex)
            {
                endIndex = src.length();
            }
            src = src.substring(startIndex, endIndex);
            int ret = Integer.valueOf(src).intValue();
            return ret;
        }
        else if (src.contains("postback"))
        {
            int startIndex = src.indexOf("postback/") + 9;
            int endIndex = src.lastIndexOf('/');
            if (startIndex >= endIndex)
            {
                endIndex = src.length();
            }
            src = src.substring(startIndex, endIndex);
            int ret = Integer.valueOf(src).intValue();
            return ret;
        }
        return SRC_GEN_DEVICE;
    }
}
