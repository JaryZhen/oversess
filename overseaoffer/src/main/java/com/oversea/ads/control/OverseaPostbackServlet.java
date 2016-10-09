package com.oversea.ads.control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.oversea.ads.bean.RequestBean;
import com.oversea.ads.bean.ResponseBean;
import com.oversea.factory.OverseaEnum;
import com.oversea.factory.OverseaRedisFactory;
import com.oversea.factory.OverseaRedisUtil;
import com.oversea.factory.OverseaResultCodeFactory;
import com.oversea.factory.OverseaTag;
import com.oversea.log.OverseaLog;

public class OverseaPostbackServlet extends OverseaBaseServlet
{
    /**
     * 
     */
    private static final long serialVersionUID = 9136306206719498148L;

    @Override
    public ResponseBean process(RequestBean request)
    {
//        NgsteamBaseServlet servlet = (NgsteamBaseServlet) NgsteamSpringHelper
//                .getBean("postbackServlet");
        String src = request.getReqType();
        ResponseBean ret = null;
        ret = PostCallBack(request,src);
        return ret;
    }
    /**
     *统一 处理API PostCallBack回调
     * @param request
     * @return
     */
    private ResponseBean PostCallBack(RequestBean request,String provider){
    	ResponseBean retBean = new ResponseBean(
    			OverseaResultCodeFactory.SUCCESS);
    	List<Long> list = new ArrayList<Long>();
    	try
    	{
    		/*
    		 * .9服务器上的回调参数名称为adid
    		 * .84服务器上的回调参数名称为suboffer
    		 * 提交代码的时候要注意
    		 * */
    		String adid = request.getValue("mj");	//第si套回调
    		//String adid = request.getValue("suboffer");//第二套回调
    		//String adid = request.getValue("offerback");//第套回调

    		String pid = request.getValue("offerid");
    		String subway ="";
    		SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss"); 
    		String date = df2.format(new Date());
    		
    		SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd"); 
    		String date1 = df1.format(new Date());
    		
    		if(adid.contains(":")){
    			int a =adid.indexOf(":");
    			subway=adid.substring(a+1,adid.length());
    			adid=adid.substring(0,a);
    		}
    		if(adid.contains("_")){
    			int a =adid.indexOf("_");
    			subway=adid.substring(a+1,adid.length());
    			adid=adid.substring(0,a);
    		}
    		String needtrack = adid + "\t" + date+"\t"+provider+"\t"+subway;
    		OverseaLog.postbackLog(needtrack);
    		System.out.println("................................................");
    		String log ="postback-"+provider+"-"+adid;
    		OverseaLog.mobileLog(log);
    		
    		//实时回调数据代码调整
    		String realtime_key = OverseaEnum.ADSPOSTBACKKEY+date1;
    		list.add(Long.valueOf(adid));
    		OverseaRedisUtil.incr(realtime_key, list, OverseaRedisFactory.ONE_DAY * 2);
    		
//			String realtime_key = OverseaEnum.ADSPOSTBACKKEY+";"+date1+";"+adid;
//			OverseaRedisFactory.incr(realtime_key,OverseaRedisFactory.ONE_DAY * 2);
    	}
    	catch (RuntimeException e) // 接口异常记录
    	{
    		e.printStackTrace();
    	}
    	retBean.add(OverseaTag.status, OverseaResultCodeFactory.SUCCESS);
    	return retBean;
    }
    public static void main(String[] args) {
    	String adid = "342452:0";
    	int a =adid.indexOf(":");
    	String subway="";
    	subway=adid.substring(a+1,adid.length());
		adid=adid.substring(0,a);
		System.out.println(adid);
		System.out.println(subway);
    	
	}
}
