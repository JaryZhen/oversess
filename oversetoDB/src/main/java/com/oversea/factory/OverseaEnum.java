
package com.oversea.factory;

import java.util.HashMap;
import java.util.Map;


public class OverseaEnum
{
    public final static int                  NETWORK_UNKOWN                = 0;
    public final static int                  NETWORK_WIFI                  = 1;
    public final static int                  NETWORK_2G                    = 2;
    public final static int                  NETWORK_3G                    = 3;
    public final static int                  NETWORK_LTE                   = 4;

    public final static int                  AD_APK                        = 1;                                     // apk下载广告
    public final static int                  AD_YEAHMOBI                   = 2;                                     // yeahmobi模拟googleplay广告
    public final static int                  AD_AFFLIATE                   = 3;                                     // 联盟广告
    public final static int                  AD_GOOGLEPLAY                 = 4;                                     // google
    // play广告
    public final static int                  AD_GOOGLEPLAY_QUICK           = 5;                                     // google
    // play快速下载广告
    public final static int                  AD_APK_TRACK                  = 6;                                     // 自己提供apk的方式，但是需要点击track的地址来反馈的
    public final static int                  AD_APK_TAPJOY                 = 7;                                    
    public final static int                  AD_APK_TRACKCLICK                 = 8;                                     // 需要手机端于服务器比对获取交集。特点：track地址变化。Tapjoy,Applift
    // 广告

    public final static String               ADSSTATISKEY                  = "adsstatis_";
    public final static String               ADSSTATISKEY_OLDPUSH                  = "adsstatispush_";
    
    public final static String               ADSGETKEY                     = "adsget_";
    public final static String               ADSGETKEY_OLDPUSH                     = "adsgetoldpush_";

    public final static String               ADSSENDKEY                 = "adssend_"; 
    public final static String               ADSINSTALLKEY                 = "adsinstall_";                         // 对于有安装数据限制的判断
    public final static String               ADSREQKEY                     = "adsreq_";
    public final static String               ADSCLICKKEY                   = "adsclick_";
    public final static String               ADSDOWNKEY                    = "adsdown_";
    public final static String               ADSCONVERSIONKEY              = "adsconversion_";
    
    public final static String               ADSINSTALLKEY_OLDPUSH                 = "adsinstalloldpush_";                         // 对于有安装数据限制的判断
    public final static String               ADSREQKEY_OLDPUSH                     = "adsreqoldpush_";
    public final static String               ADSCLICKKEY_OLDPUSH                   = "adsclickoldpush_";
    public final static String               ADSDOWNKEY_OLDPUSH                    = "adsdownoldpush_";
    public final static String               ADSCONVERSIONKEY_OLDPUSH              = "adsconversionoldpush_";

    public final static String               ADSREQUST_COUNTY_REQUSTNUM    = "adsresustcountyrequst_";

    public final static String               ADSREQUST_NEWADD_REQUSTNUM    = "adsresustaddrequst_";
    public final static String               ADSREQUST_SU_REQUSTNUM        = "adsresustsurequst_";

    public final static String               ADSREQUST_ONEWEEK_REQUSTNUM   = "adsresustoneweekrequst_";
    public final static String               ADSREQUST_TWOWEEK_REQUSTNUM   = "adsresusttwoweekrequst_";
    public final static String               ADSREQUST_THREEWEEK_REQUSTNUM = "adsresustthreeweekrequst_";

    public final static String               ADSAVAZUPOSTBACK              = "avazupostback_";
    public final static String               ADSAPPNEXTPOSTBACK            = "appnextpostback_";
    public final static String               ADSMOBPARTNRPOSTBACK            = "adsmobpartnrpostback_";
    public final static String               ADSGLISPAPOSTBACK            = "adsglispapostback_";
    public final static String               ADSARTOFCLICKPOSTBACK            = "adsartofclickpostback_";
    public final static String               ADS_HUMMEROFFER_POSTBACK            = "ads_hummeroffer_postback_";
    public final static String               ADSPOSTBACKKEY                 = "adspostback_";
    public final static String               ADSPOSTBACKKEY2                 = "pcbads2";

    public final static String               ADSAPPSTATISSTARTNUM                  = "adsappstatstartnum_-_";
    public final static String               ADSAPPSTATISTIMES                  = "adsappstatstimes_-_";
    
    public final static String REDIS_KEY_DB_COUNTRY                = "redis_db_country";
    public final static String REDIS_KEY_DB_COUNTRY_KEYS            = "redis_db_country_keys";
    //postback_log
    public final static String POSTBACK_LOG = "allpostcallback;";
    
    // statistype
    public final static int                  STATISREQ                     = 1;                                     // 展示
    public final static int                  STATISCLICK                   = 2;                                     // 点击
    public final static int                  STATISDOWN                    = 3;                                     // 下载
    public final static int                  STATISINSTALL                 = 4;                                     // 安装

    // provider
    public final static int                  YEAHMOBI                      = 2;                                     // yeahmobi
    public final static int                  AVAZU_API                     = 14;                                    // zvazu
    public final static int                  YEAHMOBI_API                  = 15;                                    // yeahmobi_api
    public final static int                  TAPJOY                        = 16;                                    // tapjoy
    public final static int                  APPNEXT                       = 17;                                    // APPNEXT
    public final static int                  APPFLOOD                      = 18;                                    // APPFLOOD
    public final static int                  MOBVISTA                      = 19;
    public final static int                  MOBPARTNR                     = 20;
    public final static int                  APPLIFT                       = 21;
    public final static int                  GLISPA                        = 22;
    public final static int                  TAPTICA                       = 23;
    public final static int                  TAPZILLION                    = 24;
    public final static int                  APPIA                         = 25;
    public final static int                  PUBNATIVE                     = 26;
    public final static int                  HUMMEROFFER                   = 27;
    public final static int                  SUPERSNOIC                    = 28;
    public final static int                  DUOMENG                       = 29;
    public final static int                  CAKE                          = 30;
    public final static int                  CLICKDEALER                   = 31;
    public final static int                  ARTOFCLICK                    = 32;
    public final static int                  DOMOBMEDIA                    = 33;
    public final static int                  PERFORMANCE                    = 34;
    public final static int                  GLISPA2                    = 35;//另外一个GLISPA
    public final static int                  APPNEXT2                       = 37;//另外一个APPNEXT
    public final static int                  CLICKDEALER2                       = 38;//另外一个Clickdealer
    public final static int                  APPCOACHS                      =39;
    public final static int                  CLICKY                      =40;
    public final static int                  TAPTICA2                    = 41;
    public final static int                  MOBUSI                      =42;
    public final static int                  IRONSOURCE                   =43;
    public final static int                  APPNEXT3                     = 44; 
    public final static int                  LEADHUG                    = 45;
    public final static int                  BATMOBI                       = 46;   
    public final static int                  GLOBALFAST                    = 47;  
    public final static int                  YEAHMOBI_API2                  = 48;                                    // yeahmobi_api2
    public final static int                  YEAHMOBI_API3                  = 49;                                    // yeahmobi_api3
    public final static int                  TYROO                         = 51; 
    public final static int                  IRONSOURCE2					=52;
    public final static int                  MOBISUMMER			    		=53;
    public final static int                  MOBILETRAFFIC			    	=54;
    public final static int                  STARTAPP_NGE			    	=55;//XIAO QIANG
    public final static int                  STARTAPP_YM			    	=56;//NATASHA 
    public final static int                  ROCKYMOBI_ym		    	    =57;
    public final static int                  AVAZU_API4                     =58;
    public final static int                  FURTHERMOBI                    =59;
    public final static int                  FANCYLOCKER					=60;
    public final static int                  ROCKYMOBI_nge		    	     =61;
    public final static int					 Fl_ST3_API						=62;
    public final static int                  FL_MB2_API  		    	     =63;
    public final static int                  TAIGAMOBI_API1_YM	    	     =64;
    public final static int                  PUBNATIVE_api1_n	    	     =65;
    public final static int                  FL_AV_API  		    	     =66;
    public final static int                  SP_AV3_API  		    	     =67;
    public final static int                  FL_tryoo3_API  		    	  =68;
    public final static int                  TrafficSteer_api1_ym  		     =69;
    public final static int 				 Tatatoo_api1_ym				 =70;
    public final static int                  Adscend_media_api1_ym           =71;
    public final static int 				 Zephyr_api1_n				     =72;
    public final static int 				 Mybestclick_api1_n				 =75;
    public final static int                  Fl_Mobquid_api1 				=76;
    public final static int                  Fl_artofclick_api3 			=77;
    public final static int                   Fl_SP_mobisummer_api3         =78;
    public final static int                    Cat_ava_api4                 =79;
    public final static int                  Tapcash_ym 			        =114;
    public final static int                  CORDRIM                        =124;//手动
    public final static int 				  APPCOACH_MAN   				=125;
    public final static int 				  SHARKGAMES   				    =133;
    
    public final static int                  FTG                    	   = 101;
    public final static int                  YOUMI                   		 = 108;
    
    public static final int BATCH_NUM = 1000;//批量插入的数量

    public final static String[]             countrys                      = new String[] {
            "AD:109.111.102.62", "AE:92.96.85.113",
            "AL:81.26.201.50", "AO:105.168.29.41", "AR:190.15.238.11",
            "AT:81.182.55.98", "BB:72.22.130.178", "BD:103.242.23.191",
            "BE:91.181.176.119", "BF:197.239.66.112", "BG:85.118.92.24",
            "BJ:41.138.90.65", "BN:202.160.35.186", "BO:181.114.120.182",
            "BR:177.144.235.193", "BS:24.231.39.240", "BT:119.2.118.230",
            "BY:178.120.1.4", "BZ:190.197.90.52", "CA:38.69.162.205",
            "CD:41.77.223.125", "CF:41.223.184.153", "CG:197.214.230.52",
            "CH:85.2.124.150", "CI:41.66.28.73", "CL:190.44.47.132",
            "CM:196.202.236.51", "CO:186.82.6.172", "CR:201.195.122.114",
            "CU:152.207.7.105", "CV:41.79.124.11", "CZ:81.30.248.23",
            "DE:12.25.9.212", "DJ:41.189.255.35", "DK:185.37.85.46",
            "DM:162.212.13.10", "DO:186.1.93.53", "DZ:105.111.96.80",
            "EC:186.46.228.255", "EE:213.168.13.242", "EG:41.44.185.163",
            "ER:83.229.36.36", "ES:81.32.173.94", "ET:197.156.77.209",
            "FI:109.240.33.58", "FR:90.14.71.207", "GA:154.112.15.194",
            "GD:69.80.47.57", "GH:41.189.161.34", "GM:41.223.215.6",
            "GN:41.79.202.193", "GQ:197.214.64.228", "GR:87.203.69.241",
            "GT:181.189.139.27", "GW:41.82.3.33", "GY:172.17.21.122",
            "HK:42.98.196.162", "HN:190.6.194.213", "HR:89.201.185.117",
            "HT:190.115.181.72", "HU:46.107.104.160", "ID:114.4.21.203",
            "IE:185.38.63.254", "IL:109.253.158.136", "IN:106.66.14.217",
            "IQ:37.237.152.110", "IS:89.160.179.166", "IT:93.36.46.241",
            "JM:72.51.83.82", "JP:125.1.58.170", "KE:196.201.217.46",
            "KH:117.20.118.6", "KM:197.255.230.12", "KP:175.45.178.19",
            "KR:118.44.50.232", "LA:103.1.30.226", "LI:80.248.205.194",
            "LK:175.157.248.30", "LR:69.22.169.32", "LS:197.189.169.43",
            "LT:84.15.185.60", "LU:94.132.102.181", "LV:46.109.191.33",
            "LY:41.254.2.29", "MA:196.206.129.6", "MC:46.99.72.28",
            "MD:178.132.126.214", "ME:212.200.246.93", "MG:197.215.193.172",
            "MK:77.28.181.121", "ML:197.155.135.99", "MM:103.255.174.140",
            "MN:150.129.142.39", "MO:60.246.157.104", "MR:41.188.107.41",
            "MT:46.11.109.42", "MU:197.226.184.112", "MV:110.168.214.247",
            "MW:41.78.248.219", "MX:189.165.172.53", "MY:210.195.250.49",
            "MZ:197.235.37.167", "NA:105.232.212.81", "NE:41.138.33.124",
            "NG:41.206.11.87", "NI:186.77.133.107", "NL:77.172.69.134",
            "NO:89.45.229.73", "NP:49.126.0.37", "PA:201.227.226.131",
            "PE:190.239.93.128", "PH:112.203.184.212", "PK:2.50.67.14",
            "PL:77.87.121.24", "PR:74.213.72.184", "PT:2.81.106.122",
            "PY:181.124.96.238", "RO:86.107.202.187", "RS:178.148.42.174",
            "RU:46.0.37.99", "RW:41.186.56.75", "SC:41.220.99.159",
            "SD:41.223.162.76", "SE:83.254.248.139", "SG:119.56.119.40",
            "SI:31.15.249.3", "SK:85.162.122.10", "SL:41.205.230.233",
            "SN:154.65.34.204", "SO:41.189.229.51", "SR:190.98.20.187",
            "SY:5.0.111.78", "SZ:10.122.87.54", "TD:154.73.112.40",
            "TG:41.207.184.17", "TH:110.168.232.252", "TL:43.254.57.246",
            "TN:197.17.61.56", "TR:78.166.107.129", "TT:186.45.14.97",
            "TW:49.216.17.163", "TZ:41.222.181.160", "UA:93.183.216.237",
            "UG:197.239.3.68", "UK:90.221.107.33", "US:174.230.194.14",
            "UY:179.31.131.130", "VE:186.167.243.150", "VN:123.22.246.147",
            "ZA:41.54.153.46", "ZM:41.223.117.43", "ZW:41.221.154.241"    };

    // TAPJOY
    public final static int                  TAPJOY_COUNT                  = 1;
    private final static String              TAPJOY_APPID0                 = "e5785140-4164-4c08-b5d2-61f2335db6dc";
    private final static String              TAPJOY_CURRENCY_ID0           = "e5785140-4164-4c08-b5d2-61f2335db6dc";
    public final static Map<Integer, String> TAPJOY_APPIDMAP               = new HashMap<Integer, String>();
    public final static Map<Integer, String> TAPJOY_CURRENCYMAP            = new HashMap<Integer, String>();
	
    //postBack
    private static Map<String,Integer> pbMap = null;
    static
    {
        TAPJOY_APPIDMAP.put(0, TAPJOY_APPID0);
        TAPJOY_CURRENCYMAP.put(0, TAPJOY_CURRENCY_ID0);
    }
    public static Map<String,Integer> getPbMap(){
    	//postback需要添加相应的请求provider
    	if(pbMap==null){
    		pbMap = new HashMap<String, Integer>();
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_YEAHMOBI_AFFLIATE+"", YEAHMOBI_API);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_AVAZU_API+"", AVAZU_API);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_APPNEXT+"", APPNEXT);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_MOBPARTNR+"", MOBPARTNR);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_GLISPA+"", GLISPA);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_ARTOFCLICK+"", ARTOFCLICK);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_HUMMEROFFER+"", HUMMEROFFER);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_TAPJOY+"", TAPJOY);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_CLICKDEALER+"", CLICKDEALER);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_APPNEXT2+"", APPNEXT2);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_GLISPA2+"", GLISPA2);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_APPCOACHS+"", APPCOACHS);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_CLICKY+"", CLICKY);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_TAPTICA+"", TAPTICA);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_TAPTICA2+"", TAPTICA2);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_MOBUSI+"", MOBUSI);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_IRONSOURCE+"", IRONSOURCE);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_APPNEXT3+"", APPNEXT3);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_BATMOBI+"", BATMOBI);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_GLOBALFAST+"", GLOBALFAST);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_APPIA+"", APPIA);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_TYROO+"", TYROO);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_LEADHUG+"", LEADHUG);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_YEAHMOBI_API2+"",YEAHMOBI_API2);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_YEAHMOBI_API3+"",YEAHMOBI_API3);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_IRONSOURCE2+"", IRONSOURCE2);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_MOBISUMMER+"", MOBISUMMER);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_MOBILETRAFFIC+"", MOBILETRAFFIC);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_STARTAPP_NGE+"", STARTAPP_NGE);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_ROCKYMOBI_ADYUN+"", ROCKYMOBI_ym);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_AVAZU_API4+"", AVAZU_API4);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_CORDRIM+"", CORDRIM);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_APPCOACH_MAN+"", APPCOACH_MAN);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_FURTHERMOBI+"", FURTHERMOBI);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_ROCKYMOBI_nge+"", ROCKYMOBI_nge);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_FANCYLOCKER+"", FANCYLOCKER);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_SHARKGAMES+"", SHARKGAMES);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_FL_AV_API+"", FL_AV_API);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_FL_MB2_API+"", FL_MB2_API);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_TAIGAMOBI_API1_YM+"", TAIGAMOBI_API1_YM);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_Fl_ST3_API+"", Fl_ST3_API);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_PUBNATIVE_api1_n+"", PUBNATIVE_api1_n);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_SP_AV3_API+"", SP_AV3_API);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_FL_tryoo3_API+"", FL_tryoo3_API);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_TrafficSteer_api1_ym+"", TrafficSteer_api1_ym);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_Tatatoo_api1_ym+"", Tatatoo_api1_ym);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_Adscend_media_api1_ym+"", Adscend_media_api1_ym);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_Zephyr_api1_n+"", Zephyr_api1_n);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_Mybestclick_api1_n+"", Mybestclick_api1_n);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_Fl_Mobquid_api1+"", Fl_Mobquid_api1);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_Fl_artofclick_api3+"", Fl_artofclick_api3);
    		pbMap.put(OverseaSrcFactory.SRC_POSTBACK_Tapcash_ym+"", Tapcash_ym);

    		
    	}
    	
    	
    	return pbMap;
    } 
}
