package com.oversea.ads.offer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oversea.ads.bean.OverseaAd;
import com.oversea.ads.bean.OverseaAd_Tem;
import com.oversea.ads.bean.OverseaProvider;
import com.oversea.ads.dao.OverseaAdDao;
import com.oversea.ads.dao.OverseaProviderDao;
import com.oversea.factory.OverseaEnum;
import com.oversea.util.OverseaHttp;
import com.oversea.util.OverseaSpringHelper;


public class OverseaCat_gli_api4 extends OverseaBaseOffer
{

	private static String offerUrl = "http://feed.platform.glispa.com/native-feed/bf3f58d4-5f2b-45a8-88d2-4bbb5dbac45f/app";
    
    public static void work()
    {
    	
    	try
        {
    		List<OverseaAd_Tem> adlist = getOfferList();
        	
        	if(adlist.size() >0){
    			Del_TemAd(OverseaEnum.Cat_gli_api4);
    			logger.info("begin put Cat_gli_api4= "+OverseaEnum.Cat_gli_api4+ " size :"+adlist.size());
    			PutOfferTo_TemDB(adlist);
    			//logger.info("end put offer to db");
    			
        	}
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void PostCallBack(int provider){
     	 //重新取出来，将tarck地址进行修改,增加回调参数
         SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
         String date = df.format(new Date());
         date = date+"000000";
         Long dateint = Long.parseLong(date);
         // 获取数据库中的数据
         OverseaAdDao adDao = (OverseaAdDao) OverseaSpringHelper
                 .getBean("dadDao");
         List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(
                 OverseaEnum.AD_AFFLIATE, provider,dateint);
         
         for (OverseaAd temp1 : allad2)
         {
       	String track = temp1.getClick();

       	if(!track.contains("?subid1")){//处理未添加过的  
       		track+="?subid1="+temp1.getId()+"_{subway}";
       		
       	}
       	temp1.setClick(track);
       	adDao.update2track(temp1);
       	
         }
         logger.info("update track provider ="+OverseaEnum.Cat_gli_api4 +" size:"+allad2.size());

     }
    /**
     * 通过接口获取offerList
     * 
     * author chenbq
     * 
     */
    public static List<OverseaAd_Tem> getOfferList()
    {
    	// 定义需要获取哪些国家的Offer
    	//logger.info("get Cat_gli_api4 offer start");
    	
    	List<OverseaAd_Tem> adsList = new ArrayList<OverseaAd_Tem>();
    	OverseaProviderDao providerDao = (OverseaProviderDao) OverseaSpringHelper.getBean("dproviderDao");
        OverseaProvider provider = null;
        try
        {

            String str = OverseaHttp.sendGet(offerUrl);
            provider = providerDao.findSingle(OverseaEnum.Cat_gli_api4);
            
          //定义初始化变量
        	JSONObject json = null;
        	JSONArray array = null;
        	
            json = JSON.parseObject(str);
    		array = json.getJSONArray("data");
    		
    		//获取offerList列表
    		if (array.size() > 0)
    		{
    			logger.info("Cat_gli_api4 offer size:" + array.size());
    		    for (int i = 0; i < array.size(); i++)
    		    {
    		        JSONObject item = array.getJSONObject(i);
    		        
    		        OverseaAd_Tem aditem = new OverseaAd_Tem();
    		        //开始获取offer对象
    		        String plat= item.getString("mobile_platform");
    		        if(!"Android".endsWith(plat)) continue;
    		        aditem.setOfferid(item.getString("campaign_id"));
    		        aditem.setName(item.getString("name"));
    		        aditem.setMainicon(item.getString("icon"));
    		        aditem.setPkg(item.getString("mobile_app_id"));
    		        String price = item.getString("payout_amount").trim();
    		        aditem.setPrice(Float.parseFloat(price));
    		        aditem.setType(OverseaEnum.AD_AFFLIATE);
    		        aditem.setProvider(Long.parseLong(String
    		                .valueOf(OverseaEnum.Cat_gli_api4)));
    		        
    		        JSONArray countrys = item.getJSONArray("countries");
    		        StringBuffer country = new StringBuffer();
    		        for (Object cty : countrys) {
    		        	String strc = getStrFromString((String)cty);
    		        	country.append(strc).append(":");
    				}
    		        country = country.deleteCharAt(country.length()-1);
   		            // logger.info("country:"+country);
    		        aditem.setCountry(country.toString());
    		        if (provider != null) {
    		        	
    		        	aditem.setCap(provider.getCap());
    		        	aditem.setSinstall(provider.getSinstall());
    		        	aditem.setWeight(provider.getPre_weight());
    		        	aditem.setPreweight(provider.getPre_weight());
    		        }
    		        aditem.setStatus(0);// 状态为0的是可用的，状态w为1是不可用的
    		        aditem.setClick(item.getString("click_url"));
    		        adsList.add(aditem);
    		    }
    		}
            
           
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return adsList;
    }
    
    public static void main(String[] args)
    {
//    	http://api.hdyfhpoi.com/gkview/postback/1018?adid={subid1}
    	
//    	getStrFromString("\"CN\",\"XB\"");
    	getOfferList();
//    	
//    	if(adlist.size() >0){
//    		updateGpOfferToDb(adlist,NgsteamEnum.AD_AFFLIATE, NgsteamEnum.GLISPA);
//    		PostCallBack(NgsteamEnum.GLISPA);
//    	}
    }
   
}
