package com.oversea.ads.offer;

import java.util.ArrayList;
import java.util.List;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oversea.ads.bean.OverseaAd_Tem;
import com.oversea.ads.bean.OverseaProvider;
import com.oversea.ads.dao.OverseaProviderDao;
import com.oversea.factory.OverseaEnum;
import com.oversea.util.OverseaHttp;
import com.oversea.util.OverseaSpringHelper;


public class OverseaCat_mobvista_api4 extends OverseaBaseOffer
{

	private static String offerUrl = "http://3s.mobvista.com/v3.php?key=9dde0de1d30b6274e2f0fcdaec63a233&platform=android";
   
    public static void work()
    {
    	
    	try
        {
    		List<OverseaAd_Tem> adlist = getOfferList();
        	
        	if(adlist.size() >0){
        		Del_TemAd(OverseaEnum.Cat_mobvista_api4);
    			logger.info("begin put offer totem  db size :"+adlist.size());
    			PutOfferTo_TemDB(adlist);
    			logger.info("end put offer to tem db");
        	}
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 通过Cat_mobvista_api4接口获取offerList
     * 
     * author chenbq
     * 
     */
    public static List<OverseaAd_Tem> getOfferList()
    {
    	List<OverseaAd_Tem> adsList = new ArrayList<OverseaAd_Tem>();
		try{
			OverseaProviderDao providerDao = (OverseaProviderDao) OverseaSpringHelper.getBean("dproviderDao");
			OverseaProvider provider = providerDao.findSingle(OverseaEnum.Cat_mobvista_api4);
			int page = 1;
			List<OverseaAd_Tem> ads = new ArrayList<OverseaAd_Tem>();
			int maxPage = getAds(provider,page,ads);
			adsList.addAll(ads);
			for(int i=0;i<maxPage-1;i++){
				getAds(provider,++page,ads);
				adsList.addAll(ads);
			}
		}catch (Exception e) {
			logger.error(e);
		}
		return adsList;
    }
    
    private static int getAds(OverseaProvider provider,int page,List<OverseaAd_Tem> ads) throws Exception{
    	List<OverseaAd_Tem> adsList = new ArrayList<OverseaAd_Tem>();
    	ads.clear();
    	String url = offerUrl+"&page="+page;
    	logger.info(url);
    	String str = OverseaHttp.sendGet(offerUrl);
    	//String str = HttpUtil.sendGet(url);
    	JSONObject json = JSON.parseObject(str);
    	int maxPage = json.getIntValue("max_page");
    	JSONArray offers = json.getJSONArray("offers");
    	for(int i=0;i<offers.size();i++){
    		JSONObject o = offers.getJSONObject(i);
    		if(!o.getString("status").equals("running")) continue;
    		String platform = o.getString("platform");
    		if(platform==null 
    				|| platform.isEmpty() 
    				|| !platform.toLowerCase().equals("android")) continue;
    		OverseaAd_Tem adTem = new OverseaAd_Tem();
    		String offerId = o.getString("campid");
    		String name = o.getString("offer_name");
    		String tracking_link = o.getString("tracking_link");
    		String geo = o.getString("geo");
    		String country = geo.toUpperCase().replace(",", ":");
    		float price = o.getFloatValue("price");
    		String pkg = o.getString("package_name");
    		int daily_cap = o.getIntValue("daily_cap");
    		String icon = o.getString("icon_link");
    		adTem.setOfferid(offerId);
    		adTem.setClick(tracking_link);
    		adTem.setName(name);
    		adTem.setCountry(country);
    		adTem.setPrice(price);
    		adTem.setMainicon(icon);
    		adTem.setPkg(pkg);
    		adTem.setType(OverseaEnum.AD_AFFLIATE);
    		adTem.setStatus(0);
    		adTem.setProvider(Long.parseLong(OverseaEnum.Cat_mobvista_api4+""));
    		adTem.setObject_cap(daily_cap);
    		if (provider != null) {
    			adTem.setCap(provider.getCap());
    			adTem.setSinstall(provider.getSinstall());
    			adTem.setWeight(provider.getPre_weight());
    			adTem.setPreweight(provider.getPre_weight());
    			//logger.info("the provider set right");
    		} else {
    			adTem.setCap(20);
    			//logger.info("the provider set default");
    		}
    		adsList.add(adTem);
    	}
    	ads.addAll(adsList);
    	return maxPage;
    }
	
    public static void main(String[] args)
    {
    	List<OverseaAd_Tem> adlist = getOfferList();
    	
    	
    }
   
}
