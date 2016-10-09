package com.oversea.ads.offer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oversea.ads.bean.OverseaAd;
import com.oversea.ads.bean.OverseaAd_Tem;
import com.oversea.ads.bean.OverseaCountry;
import com.oversea.ads.bean.OverseaProvider;
import com.oversea.ads.dao.OverseaAdDao;
import com.oversea.ads.dao.OverseaCountryDao;
import com.oversea.ads.dao.OverseaProviderDao;
import com.oversea.factory.OverseaEnum;
import com.oversea.util.OverseaHttp;
import com.oversea.util.OverseaSpringHelper;

public class OverseaCat_pub_api4 extends OverseaBaseOffer {

	private static String offerUrl = "http://bulk.pubnative.net/api/bulk/v1/promotions?app_token=c7714bd65b8d1cb85abfd3f156c9306c8832522be6af09a1517fb684734f2b3e&platform=android";

	public static void work() {
		List<OverseaAd_Tem> adlist = getOfferList();

		if (adlist.size() > 0) {
			Del_TemAd(OverseaEnum.Cat_pub_api4);
			logger.info("begin put Cat_pub_api4= "+OverseaEnum.Cat_pub_api4+ " size :"+adlist.size());
			PutOfferTo_TemDB(adlist);
			//logger.info("end put Cat_pub_api4 to db");
			
		}

	}

	/**
	 * 通过yeahmobi接口获取offerList
	 * 
	 * author chenbq
	 * 
	 */
	public static List<OverseaAd_Tem> getOfferList() {
		// 定义初始化变量
		List<OverseaAd_Tem> adsList = new ArrayList<OverseaAd_Tem>();
		JSONArray array = null;
		OverseaProviderDao providerDao = (OverseaProviderDao) OverseaSpringHelper.getBean("dproviderDao");
		OverseaProvider provider = null;
		try {
			logger.info("get PUBNATIVE_api1_n affliate offer start");
			String str = OverseaHttp.sendGet(offerUrl);
			logger.info("the url is:" + offerUrl);

			array = JSON.parseArray(str);

			// 获取offerList列表
			if (array != null) {
				logger.info("PUBNATIVE offer size:" + array.size());
				provider = providerDao.findSingle(OverseaEnum.Cat_pub_api4);
				for (int arry = 0; arry < array.size(); arry++) {
					JSONObject item = array.getJSONObject(arry);
					JSONObject app_details =  item.getJSONObject("app_details");
					String platform = app_details.getString("platform");
					if(!platform.toLowerCase().equals("android")) continue;
					String bundle_id = app_details.getString("bundle_id");
					if(bundle_id==null || bundle_id.isEmpty()) continue;
					//logger.info("pak:"+bundle_id);
					JSONObject creatives = item.getJSONObject("creatives");
					String title = creatives.getString("title");
					JSONArray campaigns = item.getJSONArray("campaigns");
					Map<String,String> map = getCounttryMap();
					for(int i=0;i<campaigns.size();i++){
						JSONObject object = (JSONObject)campaigns.get(i);
						if(object==null) continue;
						OverseaAd_Tem aditem = new OverseaAd_Tem();
						aditem.setPkg(bundle_id);
						aditem.setName(title);
						String click = object.getString("click_url");
						if(click==null||click.isEmpty()) continue;
						aditem.setClick(click);
						JSONArray count = object.getJSONArray("countries");
						if(count==null) continue;
						String countrs = count.toJSONString();
						String countrys = countrs.toString();
						if ("[]".equals(countrys)) continue;
						//logger.info("countrys:" + countrys);
						String country = countryList(countrys, map);
						//logger.info("country:" + country);
						String global = object.getString("global");
						if("".equals(country)||country==null){
							if(!"true".equals(global)){
								continue;
							}else{
								aditem.setCountry("ALL");
							}
						}else{
							aditem.setCountry(country);
						}
						
						String id = "88" ;
						int idnum , idn;
						idnum = click.indexOf("&pid=")+5;
						idn=click.indexOf("&nid");
						if(idnum==4 || idn==-1) id = "88";
						else 
							id = click.substring(idnum, idn);

						aditem.setOfferid(id);
						Integer points = object.getInteger("points");
						aditem.setPrice((float)points/1000);
						if (provider != null) {
							aditem.setCap(provider.getCap());
							aditem.setSinstall(provider.getSinstall());
							aditem.setWeight(provider.getPre_weight());
							aditem.setPreweight(provider.getPre_weight());
							//logger.info("the provider set right");
						} else {
							aditem.setCap(20);
							//logger.info("the provider set default");
						}

						aditem.setType(OverseaEnum.AD_AFFLIATE);
						aditem.setProvider(Long.parseLong(String.valueOf(OverseaEnum.Cat_pub_api4)));
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

	public static Map<String, String> getCounttryMap() {

		// 得到所有country上网简写：
		OverseaCountryDao dao = (OverseaCountryDao) OverseaSpringHelper
				.getBean("dcountryDao");
		Map<String, String> map = new HashMap<String, String>();
		List<OverseaCountry> list;
		list = dao.findAll();

		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				String cou3 = list.get(i).getCountry3ch();
				String cou = list.get(i).getCountry();

				if (cou != null && cou3 != null) {
					map.put(cou3, cou);
				}
			}
		}
		return map;
	}

	private static String countryList(String coutr, Map<String, String> map) {

		coutr = coutr.substring(1, coutr.length() - 1);
		String[] countryStr = coutr.split(",");
		java.util.Arrays.sort(countryStr);
		String countries = ""; // 初始化country字符串["CXR"]
		for (int j = 0; j < countryStr.length; j++) {
			String string = countryStr[j].substring(1,
					countryStr[j].length() - 1);
			String ctry = map.get(string);
			if (ctry == null) {
				continue;
			}
			countries = countries + ctry + ":";
		}
		if (countries.equals("")) {
			return "";
		}
		countries = countries.substring(0, countries.length() - 1);
		return countries;
	}

	public static void pubnativeTrackUrl(int provider) {
		// 重新取出来，将tarck地址进行修改,增加回调参数
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
		String date = df.format(new Date());
		date = date + "000000";
		Long dateint = Long.parseLong(date);
		// 获取数据库中的数据
		OverseaAdDao adDao = (OverseaAdDao) OverseaSpringHelper
				.getBean("dadDao");
		List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(
				OverseaEnum.AD_AFFLIATE, provider, dateint);

		for (OverseaAd temp1 : allad2) {
			String track = temp1.getClick();
			if (!track.contains("&aff_sub")) {// 处理未添加过的
				track += "&aff_sub=" + temp1.getId() + "_{subway}";
			}
			temp1.setClick(track);
			adDao.update2track(temp1);
		}
		logger.info("update track provider ="+OverseaEnum.Cat_pub_api4 +" size:"+allad2.size());
	}

	public static void main(String[] args) {
		// List<NgsteamAd_Tem> adlist = getOfferList();
		// http://api.hdyfhpoi.com/gkview/postback/1001?adid={aff_sub}
		// System.out.println(adlist.size());
		//getOfferList();
		String  click = "http://tr.pubnative.net/click/bulk?aid=1009428&aaid=1005861&pd=3039123&id=26&puid=1005291&pn_u=N326wPLVjeCV2fo1a4d5cKqMjPV3mIIWu9IsATg48pRZZy9LgcUqBdTa1XFrH9INyQATlI6BR1kyFDmD4xj4WfuAMDP_omKi1xZ3HHw&pn_l=103";
		String id = "88" ;
		int idnum , idn;
		idnum = click.indexOf("&pid=")+5;
		idn=click.indexOf("&nid");
		if(idnum==4 || idn==-1) id = "88";
		else 
			id = click.substring(idnum, idn);
		
		System.out.println(id);
	}
}
