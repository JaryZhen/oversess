package com.oversea.ads.offer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oversea.ads.bean.OverseaAd_Tem;
import com.oversea.ads.bean.OverseaProvider;
import com.oversea.ads.dao.OverseaProviderDao;
import com.oversea.factory.OverseaEnum;
import com.oversea.util.OverseaHttp;
import com.oversea.util.OverseaSpringHelper;


public class OverseaCat_appnext_api4 extends OverseaBaseOffer
{

	private static String offerUrl = "https://admin.appnext.com/offerApi.aspx?pimg=1&id=567f8d50-8fc5-4787-a93a-20d441051396&pbk=q";
   
    public static void work()
    {
    	
    	try
        {
    		List<OverseaAd_Tem> adlist = getOfferList();
        	
        	if(adlist.size() >0){
        		Del_TemAd(OverseaEnum.Cat_appnext_api4);
    			//logger.info("begin put offer totem  db size :"+adlist.size());
    			PutOfferTo_TemDB(adlist);
    			//logger.info("end put offer to tem db");
    			logger.info("begin put Cat_appnext_api4= "+OverseaEnum.Cat_appnext_api4+ " size :"+adlist.size());
        	}
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 通过Clickdealer接口获取offerList
     * 
     * author chenbq
     * 
     */
    public static List<OverseaAd_Tem> getOfferList()
    {
    	//定义初始化变量
    	JSONObject json = null;
    	JSONArray array = null;
    	List<OverseaAd_Tem> adsList = new ArrayList<OverseaAd_Tem>();
    	OverseaProviderDao providerDao = (OverseaProviderDao) OverseaSpringHelper.getBean("dproviderDao");
        OverseaProvider provider = null;
        try
        {
        	//logger.info("get Cat_appnext_api4 offer start");
            String str = OverseaHttp.sendGet(offerUrl);
            //logger.info("the url is:" + offerUrl);

            json = JSON.parseObject(str);
            array = json.getJSONArray("apps");
            
            //获取offerList列表
            if (array.size() > 0)
            {
            	provider = providerDao.findSingle(OverseaEnum.Cat_appnext_api4);
            	//logger.info("Cat_appnext_api4 offer size:" + array.size());
                for (int i = 0; i < array.size(); i++)
                {
                    JSONObject item = array.getJSONObject(i);
                    OverseaAd_Tem aditem = new OverseaAd_Tem();
                    //开始获取offer对象
                    aditem.setName(item.getString("title"));
                    aditem.setMainicon(item.getString("urlImg"));
                    aditem.setPkg(item.getString("androidPackage"));
                    String price = item.getString("revenueRate").trim();
                    aditem.setPrice(Float.parseFloat(price));
                    aditem.setOfferid(item.getString("id"));
                    aditem.setType(OverseaEnum.AD_AFFLIATE);
                    aditem.setProvider(Long.parseLong(String
                            .valueOf(OverseaEnum.Cat_appnext_api4)));
                    
                    JSONArray countrys = item.getJSONArray("country");
    		        StringBuffer country = new StringBuffer();
    		        for (Object cty : countrys) {
    		        	String str_cty = getStrFromString((String)cty);
    		        	country.append(str_cty).append(":");
    				}
    		        country = country.deleteCharAt(country.length()-1);
//    		        logger.info("country:"+country);
    		        aditem.setCountry(country.toString());
                    
                    aditem.setStatus(0);// 状态为0的是可用的，状态w为1是不可用的
                    
                    if (provider != null) {
    		        	
    		        	aditem.setCap(provider.getCap());
    		        	aditem.setSinstall(provider.getSinstall());
    		        	aditem.setWeight(provider.getPre_weight());
    		        	aditem.setPreweight(provider.getPre_weight());
    		        }
                    aditem.setClick(item.getString("urlApp"));
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
    	List<OverseaAd_Tem> adlist = getOfferList();
    	
//    	if(adlist.size() >0){
//    		updateGpOfferToDb(adlist,NgsteamEnum.AD_AFFLIATE, NgsteamEnum.Cat_appnext_api4);
//    	}
    }
   
}
