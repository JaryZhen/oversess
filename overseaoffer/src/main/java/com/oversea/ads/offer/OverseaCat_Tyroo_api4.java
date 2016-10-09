package com.oversea.ads.offer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

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


public class OverseaCat_Tyroo_api4 extends OverseaBaseOffer
{

	private static String offerUrl = "https://api.hasoffers.com/Apiv3/json?NetworkId=tyrooone&Target=Affiliate_Offer&Method=findAll&api_key=e63ca5f6d09fefb3d40d46603804f290719bf3a195b8c12048207d1461e2a43a";
   
	
    public  static void work()
    {
    	
    	try
        {
    		List<OverseaAd_Tem> adlist = getOfferList();
        	if(adlist.size() >0){
        		Del_TemAd(OverseaEnum.Cat_TYROOapi4);
    			//logger.info("begin put offer totem  db size :"+adlist.size());
    			PutOfferTo_TemDB(adlist);
    			logger.info("begin put Cat_TYROOapi4= "+OverseaEnum.Cat_TYROOapi4+ " size :"+adlist.size());
        	}
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 通过接口获取offerList
     * 
     * author chenbq
     * 
     */
    
    @SuppressWarnings("unchecked")
	public static List<OverseaAd_Tem> getOfferList()
    {
    	//定义初始化变量
    	JSONObject json = null;
    	List<OverseaAd_Tem> adsList = new ArrayList<OverseaAd_Tem>();
    	OverseaProviderDao providerDao =(OverseaProviderDao) OverseaSpringHelper.getBean("dproviderDao");
		OverseaProvider provider = null;
	    String offerids="";
        try
        {
        	logger.info("get Cat_TYROOapi4 offer start");
            String str = OverseaHttp.sendGet(offerUrl);
            //logger.info("the url is:" + offerUrl);

            json = JSON.parseObject(str);
           // logger.info(json.toString());
            JSONObject json1 = json.getJSONObject("response");
            JSONObject offer = json1.getJSONObject("data");
            //获取offerList列表
            if (offer != null)
            {
            	logger.info("offer size:"+offer.size());
            	Set<Entry<String, Object>> entrySet = offer.entrySet();
            	Iterator<Entry<String, Object>> iterator = entrySet.iterator();
            	provider = providerDao.findSingle(OverseaEnum.Cat_TYROOapi4);
            	//logger.info("entrySet:"+entrySet.size());
            	while (iterator.hasNext()) {
					Map.Entry<java.lang.String, java.lang.Object> entry = (Map.Entry<java.lang.String, java.lang.Object>) iterator.next();
					JSONObject offerItem = (JSONObject)entry.getValue();
					Map<String,String> dataMap = (Map<String,String>)offerItem.get("Offer");
					String require_approval = dataMap.get("require_approval");
					if("0".equals(require_approval)){
						String status = dataMap.get("status");
						if("active".equalsIgnoreCase(status)){
							//只处理GP的数据
							String pkg = dataMap.get("preview_url");
							if(pkg.indexOf("play.google.com") > 0){
								pkg = pkg.substring(pkg.indexOf("id=") + 3);
								OverseaAd_Tem aditem = new OverseaAd_Tem();
	                            //开始获取offer对象
	                            aditem.setOfferid(dataMap.get("id"));
	                            //logger.info("id "+dataMap.get("id"));
	                            String name = dataMap.get("name").trim();
	                            
	                            if(name.length() >0){
	                            	if(name.indexOf("-") > 0){
	                            		String[] names = name.split("-");
	                            		if(names.length == 1){
	                            			aditem.setName(names[0].trim());
	                            		}else{
	                            			aditem.setName(names[1].trim());
	                            		}
	                            		
	                            	}else{
	                            		aditem.setName(name);
	                            	}
	                            }
	                           // logger.info("name:"+name);
	                            aditem.setPkg(pkg);
	                            //logger.info("pkg"+pkg);
	                            String price="0";
	                            try {
	                            	 price = dataMap.get("default_payout");
									
								} catch (Exception e) {
									// TODO: handle exception
									continue;
								}
	                            aditem.setPrice(Float.parseFloat(price));
	                           
	                            aditem.setType(OverseaEnum.AD_AFFLIATE);
	                            aditem.setProvider(Long.parseLong(String
	                                    .valueOf(OverseaEnum.Cat_TYROOapi4)));
	                            
//	                            String country = getCountryByid(dataMap.get("id"));
//	                            if(country=="") country = "ALL";;
//	                            aditem.setCountry(country);
	                            offerids=offerids+"&ids%5B%5D="+dataMap.get("id");
	                            //logger.info("count"+country);
	                            aditem.setStatus(0);// 状态为0的是可用的，状态w为1是不可用的
	                            if (provider != null) {
	    							aditem.setCap(provider.getCap());
	    							aditem.setSinstall(provider.getSinstall());
	    							aditem.setWeight(provider.getPre_weight());
	    							aditem.setPreweight(provider.getPre_weight());
	    							//logger.info("the provider set right");
	    						} else {
	                            aditem.setCap(20); // 设置默认安装量
	                            aditem.setSinstall(25); //
	                            aditem.setWeight(70);
	    						}
//	                            String TrackLink = getTrackByid(dataMap.get("id"));
	                            String TrackLink = "http://tyrooone.go2cloud.org/aff_c?offer_id={offer_id}&aff_id=445";
	                            TrackLink = TrackLink.replace("{offer_id}", dataMap.get("id"));
	                            aditem.setClick(TrackLink);
	                           // logger.info(TrackLink);
	                            adsList.add(aditem);
	                            //logger.info("s "+adsList.size());
							}
						}
					}
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
        adsList =getCountryByid(adsList,offerids);
        return adsList;
    }
    
    
    /**
     * 通过offerID获取国家列表
     * 
     * author chenbq
     * 
     */
    public static List<OverseaAd_Tem> getCountryByid(List<OverseaAd_Tem> list,String offerids)
    {
    	//定义初始化变量
    	JSONObject json = null;
    	JSONArray array = null;
    	JSONObject jsoncs = null;
    	JSONObject jsonc = null;

    	Map<String,String> map = new HashMap<String, String>();
    	List<OverseaAd_Tem> adlist = list;
    	//&ids%5B%5D=403551&ids%5B%5D=395809&ids%5B%5D=120783
    	String countryLink = "https://api.hasoffers.com/Apiv3/json?NetworkId=tyrooone&Target=Affiliate_Offer&Method=getTargetCountries&api_key=e63ca5f6d09fefb3d40d46603804f290719bf3a195b8c12048207d1461e2a43a" + offerids;    	
    	//logger.info("countryLink:"+countryLink);
    	try
    	{
    		String str = OverseaHttp.sendGet(countryLink);

    		json = JSON.parseObject(str);
    		JSONObject json1 = json.getJSONObject("response");
    		array = json1.getJSONArray("data");
    		//获取offerList列表
    		if (array != null)
    		{
    			for(int i = 0 ;i< array.size() ;i++){
    				jsoncs = array.getJSONObject(i);
    				String coutry = "";
    				String id = jsoncs.getString("offer_id");
    				try {
    					jsonc = jsoncs.getJSONObject("countries");

    				} catch (Exception e) {
    					// TODO: handle exception
    					map.put(id,"ALL");
    					//System.out.println("id "+ "all");
    					continue;
    				}

    				Set<Entry<String, Object>> entrySet = jsonc.entrySet();
    				Iterator<Entry<String, Object>> iterator = entrySet.iterator();
    				while (iterator.hasNext()) {
    					coutry = coutry + iterator.next().getKey() + ":";
    				}
    				if(coutry.length() > 0){
    					coutry = coutry.substring(0,coutry.length()-1);
    				}
    				//System.out.println("id co"+ id +coutry);
    				map.put(id,coutry);
    			}
    		}

    		for( OverseaAd_Tem ls:adlist){
    			ls.setCountry(map.get(ls.getOfferid()));
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
    	return adlist;
    }
    
    /**
     * 通过offerID获取track地址
     * 
     * author chenbq
     * 
     */
    public static String getTrackByid(String offerid)
    {
    	//定义初始化变量
    	JSONObject json = null;
    	String re = "";
    	
    	String TrackLink = "https://api.hasoffers.com/Apiv3/json?NetworkId=domob&Target=Affiliate_Offer&Method=generateTrackingLink&api_key=3f50433271befba05da15aaef1c0feeb322a8d5d7312ae6d3bcfe4586231f90a&offer_id=" + offerid;
    	
        try
        {
            String str = OverseaHttp.sendGet(TrackLink);

            json = JSON.parseObject(str);
            JSONObject json1 = json.getJSONObject("response");
            JSONObject trackObject = json1.getJSONObject("data");
            re = trackObject.getString("click_url");
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return re;
    }
    
    
    public static void main(String[] args)
    {
    	 getOfferList();
    	
//    	if(adlist.size() >0){
//    		updateGpOfferToDb(adlist,NgsteamEnum.AD_AFFLIATE, NgsteamEnum.Cat_TYROOapi4);
//    	}
    }
   
}
