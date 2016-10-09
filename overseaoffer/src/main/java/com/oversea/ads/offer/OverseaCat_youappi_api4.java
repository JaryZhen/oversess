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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oversea.ads.bean.OverseaAd_Tem;
import com.oversea.ads.bean.OverseaProvider;
import com.oversea.ads.dao.OverseaProviderDao;
import com.oversea.factory.OverseaEnum;
import com.oversea.util.OverseaHttp;
import com.oversea.util.OverseaSpringHelper;

public class OverseaCat_youappi_api4 extends OverseaBaseOffer {

	private static String offerUrl = "http://service.youappi.com/cmp/campaigninfo?accesstoken=6f5e9f8d-d0a6-4cf9-8da6-e625375e7846";

	public static void work() {

		try {
			List<OverseaAd_Tem> adlist = getOfferList();

			if (adlist.size() > 0) {
				Del_TemAd(OverseaEnum.Cat_youappi_api4);
				logger.info("begin put Cat_youappi_api4= "+OverseaEnum.Cat_youappi_api4+ " size :"+adlist.size());
				PutOfferTo_TemDB(adlist);
				//logger.info("end put Cat_youappi_api4 to tem db");
				
			}
		} catch (Exception e) {
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
	public static List<OverseaAd_Tem> getOfferList() {
		// 定义初始化变量
		JSONObject json = null;
		List<OverseaAd_Tem> adsList = new ArrayList<OverseaAd_Tem>();
		OverseaProviderDao providerDao = (OverseaProviderDao) OverseaSpringHelper.getBean("dproviderDao");
		OverseaProvider provider = null;
		try {
			//logger.info("get Cat_youappi_api4 offer start");
			String str = OverseaHttp.sendGet(offerUrl);
			json = JSON.parseObject(str);
			JSONObject offer = json.getJSONObject("data");
			// 获取offerList列表
			if (offer != null) {
				Set<Entry<String, Object>> entrySet = offer.entrySet();
				Iterator<Entry<String, Object>> iterator = entrySet.iterator();
				provider = providerDao.findSingle(OverseaEnum.Cat_youappi_api4);
				
				while (iterator.hasNext()) {
		
					Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
					JSONObject offerItem = (JSONObject) entry.getValue();
					
					String platform = offerItem.getString("platform");
					//logger.info("plt"+platform);
					if ("android".equals(platform)) {
						// 只处理GP的数据
						String pkg = offerItem.getString("app_url");
						if (pkg.indexOf("play.google.com") > 0) {
							pkg = pkg.substring(pkg.indexOf("id=") + 3);
							if (pkg.indexOf("&") > 0) {
								pkg = pkg.substring(0, pkg.indexOf("&"));
							}
							OverseaAd_Tem aditem = new OverseaAd_Tem();
							// 开始获取offer对象
							aditem.setOfferid(offerItem.getString("campaign_id"));
							JSONObject app_details = offerItem.getJSONObject("app_details");
							String name = app_details.getString("app_name").trim();
							aditem.setName(name);
							//logger.info("name:"+name);
							aditem.setPkg(pkg);

							String price = offerItem.getString("cpi");
							aditem.setPrice(Float.parseFloat(price));

							aditem.setType(OverseaEnum.AD_AFFLIATE);
							aditem.setProvider(Long.parseLong(String.valueOf(OverseaEnum.Cat_youappi_api4)));

							String country = offerItem.getJSONArray("countries").toString();
							//logger.info("country:"+country);
							country=country.replace("\",\"", ":");
							country=country.substring(2,country.length()-2).toUpperCase();
							//logger.info("country:"+country);
							if (country == null)
								country = "ALL";
							aditem.setEcountry("CN");
							aditem.setCountry(country);

							aditem.setStatus(0);// 状态为0的是可用的，状态w为1是不可用的

							int oc = 0;
							try {
								oc = Integer.parseInt(offerItem.getString("max_daily"));
							} catch (Exception e) {
								// TODO: handle exception
							}
							if (oc == -1)
								oc = 0;
							aditem.setObject_cap(oc);

							if (provider != null) {
								aditem.setCap(provider.getCap());
								aditem.setSinstall(provider.getSinstall());
								aditem.setWeight(provider.getPre_weight());
								aditem.setPreweight(provider.getPre_weight());
							} else {

								aditem.setCap(20); // 设置默认安装量
								aditem.setSinstall(25); //
								aditem.setWeight(70);
							}
							aditem.setClick(offerItem.getString("redirect_url"));
							//logger.info(offerItem.getString("redirect_url"));
							
							adsList.add(aditem);
						}

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

	public static void main(String[] args) {// http://api.hdyfhpoi.com/gkview/postback/1038?adid={aff_sub}

		List<OverseaAd_Tem> adlist = getOfferList();
		//System.out.println("adfaefda");

//		String track= "";
//		track = track.replace("&subid=","&subid=" + "_{subway}");
//		System.out.println(track);
	}

}
