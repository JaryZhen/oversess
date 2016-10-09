package com.oversea.ads.offer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.http.client.ClientProtocolException;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oversea.ads.bean.OverseaAd_Tem;
import com.oversea.ads.bean.OverseaProvider;
import com.oversea.ads.dao.OverseaProviderDao;
import com.oversea.factory.OverseaEnum;
import com.oversea.util.OverseaHttp;
import com.oversea.util.OverseaSpringHelper;


public class OverseaCat_mobvista_dev_api4 extends OverseaBaseOffer
{

	private static String offerUrl = "http://net.rayjump.com/agentapi/ad?appid=24123&apikey=33a38afcb07f437383614fd81bce49d9&restype=json";
   
    public static void work()
    {
    	
    	try
        {
    		logger.info("start work Cat_Mobvista_dev_api4");
    		List<OverseaAd_Tem> adlist = getOfferList();
        	
        	if(adlist.size() >0){
        		Del_TemAd(OverseaEnum.Cat_Mobvista_dev_api4);
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
     * 通过Yw_Mobvista_api5接口获取offerList
     * 
     * author chenbq
     * 
     */
    public static List<OverseaAd_Tem> getOfferList()
    {
		// 定义初始化变量
		//JSONObject array = null;
		List<OverseaAd_Tem> adsList = new ArrayList<OverseaAd_Tem>();

		OverseaProviderDao providerDao = (OverseaProviderDao) OverseaSpringHelper.getBean("dproviderDao");
		OverseaProvider provider = null;
		try {
			//logger.info("get yeahmobi3 affliate offer start");
			String str = OverseaHttp.sendGet(offerUrl);
			//logger.info("the url is:" + offerUrl);

			JSONObject json1 = JSON.parseObject(str);
			JSONObject json = json1.getJSONObject("campaigns");
			
			// 获取offerList列表
			if (json != null) {
				//logger.info("yeahmobi offer size:" + json.size());
				Set<Entry<String, Object>> entrySet = json.entrySet();
				Iterator<Entry<String, Object>> iterator = entrySet.iterator();
				provider = providerDao.findSingle(OverseaEnum.Cat_Mobvista_dev_api4);
				while (iterator.hasNext()) {
					Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
					JSONObject item = (JSONObject) entry.getValue();
					String pkg = item.getString("allow_device");

					if("android".equals(pkg)){

						OverseaAd_Tem aditem = new OverseaAd_Tem();
						String countries = item.getString("allow_country");
						countries = countries.replaceAll(",", ":");
						//logger.info(countries);
						aditem.setCountry(countries);
						String offerid = item.getString("campaign_id");
						aditem.setOfferid(offerid);
						
						aditem.setName(item.getString("title"));
						aditem.setPkg(item.getString("trace_app_id"));
						String price = item.getString("payout");
						aditem.setPrice(Float.parseFloat(price.substring(1,price.length())));
						aditem.setType(OverseaEnum.AD_AFFLIATE);
						aditem.setStatus(0);// 状态为0的是可用的，状态为1是不可用的
						aditem.setClick(item.getString("trackurl"));
						aditem.setProvider(Long.parseLong(String
								.valueOf(OverseaEnum.Cat_Mobvista_dev_api4)));
//						logger.info(Float.parseFloat(price.substring(1,price.length())));
//						logger.info(item.getString("trace_app_id"));
//						logger.info(item.getString("title"));
//						logger.info(item.getString("trackurl"));
						
						if (provider != null) {
							aditem.setCap(provider.getCap());
							aditem.setSinstall(provider.getSinstall());
							aditem.setWeight(provider.getPre_weight());
							aditem.setPreweight(provider
									.getPre_weight());
							//logger.info("the provider set right");
						} else {
							aditem.setPreweight(3000);
							aditem.setSinstall(25);
							aditem.setWeight(5000); // 设置默认权重
							//logger.info("the provider set default");
						}
						
						adsList.add(aditem);
					}
				}
					
				
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return adsList;
	}
    
    public static void main(String[] args)
    {
    	List<OverseaAd_Tem> adlist = getOfferList();
    	
    	
    }
   
}
