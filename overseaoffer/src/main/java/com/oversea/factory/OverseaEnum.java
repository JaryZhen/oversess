
package com.oversea.factory;

import java.util.HashMap;
import java.util.Map;

import com.oversea.tran.OverseaBaseTrans;
import com.oversea.tran.postback.OverseaCat_api4Tran;


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
    public final static int                    Cat_gli_api4					=80;
    public final static int                    Cat_pub_api4					=81;
    public final static int                    Cat_supersonic_api4		=82;
    public final static int					   Cat_ir_api4                 =  83;
    public final static int	                    Cat_aoc_api4                 =  84;
    public final static int                    Cat_mobusi_api4                =  86;
    public final static int                    Cat_youappi_api4               = 93 ;
    public final static int                    Cat_Tabatoo_api4               = 98 ; 
    public final static int                  Cat_mobvista_api4                 = 99;
    public final static int                  Cat_adxmi_api4                 = 4010;
    public final static int                  Cat_TYROOapi4                    = 4004; 
    public final static int                Cat_clickky_api4 				 = 4007; 
    public final static int                Cat_webeye_api4 				 = 4012;
    public final static int                Cat_appnext_api4 			 = 4013;
    public final static int 			   Cat_Mobvista_dev_api4         = 4014;
    
    public final static int                  Tapcash_ym 			        =114;
    public final static int                  CORDRIM                        =124;//手动
    public final static int 				  APPCOACH_MAN   				=125;
    public final static int 				  SHARKGAMES   				    =133;
    
    public final static int                  FTG                    	   = 101;
    public final static int                  YOUMI                   		 = 108;
    
    public static final int BATCH_NUM = 1000;//批量插入的数量

    
    // TAPJOY
    public final static int                  TAPJOY_COUNT                  = 1;
    private final static String              TAPJOY_APPID0                 = "e5785140-4164-4c08-b5d2-61f2335db6dc";
    private final static String              TAPJOY_CURRENCY_ID0           = "e5785140-4164-4c08-b5d2-61f2335db6dc";
    public final static Map<Integer, String> TAPJOY_APPIDMAP               = new HashMap<Integer, String>();
    public final static Map<Integer, String> TAPJOY_CURRENCYMAP            = new HashMap<Integer, String>();
	
    //postBack
    public static OverseaBaseTrans getTrans(String src)
	{
		OverseaBaseTrans ret = null;
		if("cappn4".equals(src)){
			ret = new OverseaCat_api4Tran();
		}
		if("ctry4".equals(src)){
			ret = new OverseaCat_api4Tran();
		}
		if("cwebye4".equals(src)){
			ret = new OverseaCat_api4Tran();
		}
		if("cclky4".equals(src)){
			ret = new OverseaCat_api4Tran();
		}
		if("cadxm4".equals(src)){
			ret = new OverseaCat_api4Tran();
		}
		if("cmvt4".equals(src)){
			ret = new OverseaCat_api4Tran();
		}
		if("ctabt4".equals(src)){
			ret = new OverseaCat_api4Tran();
		}
		if("cyou4".equals(src)){
			ret = new OverseaCat_api4Tran();
		}
		if("caa4".equals(src)){
			ret = new OverseaCat_api4Tran();
		}
		if("cgla4".equals(src)){
			ret = new OverseaCat_api4Tran();
		}
		if("cpua4".equals(src)){
			ret = new OverseaCat_api4Tran();
		}
		if("cira4".equals(src)){

			ret = new OverseaCat_api4Tran();
		}
		if("caoa4".equals(src)){

			ret = new OverseaCat_api4Tran();
		}
		if("cmob4".equals(src)){

			ret = new OverseaCat_api4Tran();
		}
		if("cmoc4".equals(src)){//mobvista_dev_api4

			ret = new OverseaCat_api4Tran();
		}
		return ret;
	}
    
    static
    {
        TAPJOY_APPIDMAP.put(0, TAPJOY_APPID0);
        TAPJOY_CURRENCYMAP.put(0, TAPJOY_CURRENCY_ID0);
    }
//   
}
