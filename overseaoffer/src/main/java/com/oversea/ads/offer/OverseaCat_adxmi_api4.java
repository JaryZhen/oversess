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

public class OverseaCat_adxmi_api4 extends OverseaBaseOffer{
	
	private static String offerUrl = "http://api-offers.adxmi.com/aff/offers/list/approval?key=a3944594ee01549751216a7ecf7ae38d&limit=200&page=1";
	
	public static void work(){
		
		List<OverseaAd_Tem> adlist = getOfferList();
		//logger.info("begin put offer to o_ad_zl  db size :"+adlist.size());
		if(adlist.size() >0){
			Del_TemAd(OverseaEnum.Cat_adxmi_api4);
			logger.info("begin put Cat_adxmi_api4= "+OverseaEnum.Cat_adxmi_api4+ " size :"+adlist.size());
			PutOfferTo_TemDB(adlist);
			//logger.info("end put offer to db");
			
    	}
	}
	
	
	private static List<OverseaAd_Tem> getOfferList(){
		logger.info("get Cat_adxmi_api4 offer start");
		JSONObject json = null;
    	JSONArray array = null;
    	OverseaProviderDao providerDao = (OverseaProviderDao) OverseaSpringHelper.getBean("dproviderDao");
        OverseaProvider provider = null;
    	List<OverseaAd_Tem> adsList = null;
		try{
			String str = OverseaHttp.sendGet(offerUrl);
	       // logger.info("the url is:" + offerUrl);
	        json = JSON.parseObject(str).getJSONObject("d");
	        array = json.getJSONArray("offers");
	        provider = providerDao.findSingle(OverseaEnum.Cat_adxmi_api4);
	        logger.info("Cat_adxmi_api4 offer size:" + array.size());
	        if(array==null) return null;
	        adsList = new ArrayList<OverseaAd_Tem>();
	        for(int i=0;i<array.size();i++){
	        	JSONObject item = array.getJSONObject(i);
	        	if(!"active".equals(item.getString("offer_status"))) continue;
	        	if(!"Android".equals(item.getString("system_type"))) continue;
	        	JSONArray categories = item.getJSONArray("categories");
	        	if(!categories.contains("Google Play")) continue;
	        	String preview_url = item.getString("preview_url");
	        	String pkg = null;
	        	if(preview_url!=null && !preview_url.isEmpty()){
	        		if(preview_url.indexOf("play.google.com") < 0) continue; 
	        		preview_url = preview_url.substring(preview_url.indexOf("id=") + 3);
					if(preview_url.indexOf("&") > 0){
						pkg = preview_url.substring(0, preview_url.indexOf("&"));
					}else{
						pkg = preview_url;
					}
	        	}
	        	if(pkg==null || pkg.isEmpty()) continue;
                OverseaAd_Tem aditem = new OverseaAd_Tem();
                aditem.setPkg(pkg);
                aditem.setOfferid(item.getString("offer_id"));
                //logger.info("offer_id):"+item.getString("offer_id"));
                aditem.setName(item.getString("offer_name"));
                JSONArray country = item.getJSONArray("country");
                StringBuilder countries = new StringBuilder();
                int length = country.size();
                for(int j=0;j<length;j++){
                	String countryStr = country.get(j).toString();
                	countries.append("Global".equals(countryStr)?"ALL":countryStr);
                	if(j < length-1) countries.append(":");
                }
                aditem.setEcountry("CN");
                aditem.setCountry(countries.toString());
                aditem.setPrice(item.getFloat("offer_payout"));
                aditem.setClick(item.getString("tracking_link"));
                aditem.setObject_cap(item.getInteger("daily_cap"));
                aditem.setType(OverseaEnum.AD_AFFLIATE);
                aditem.setProvider(Long.parseLong(OverseaEnum.Cat_adxmi_api4+""));
                aditem.setStatus(0);// 状态为0的是可用的，状态w为1是不可用的
                
                aditem.setCap(20); // 设置默认安装量
                
                if (provider != null) {
		        	
		        	aditem.setCap(provider.getCap());
		        	aditem.setSinstall(provider.getSinstall());
		        	aditem.setWeight(provider.getPre_weight());
		        	aditem.setPreweight(provider.getPre_weight());
		        }
                adsList.add(aditem);
	        }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return adsList;
	}
	
	
	public static void main(String[] args){
		List<OverseaAd_Tem> adlist = getOfferList();
		for(int i=0 ;i<adlist.size();i++  ){
			System.out.println("jary "+adlist.get(i).getProvider());
		}
	}
	
}
