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

public class OverseaCat_ava_api4 extends OverseaBaseOffer {

	private static String offerUrl = "http://api.apx.avazutracking.net/affiliate/sdkad/s2s/getapps.php?sourceid=17617&os=android&page=1&pagenum=2000&incent=2";

	public static void work() {

		try {
			List<OverseaAd_Tem> adlist = getOfferList();
			if (adlist.size() > 0) {
				Del_TemAd(OverseaEnum.Cat_ava_api4);
				logger.info("begin put Cat_ava_api4= "+OverseaEnum.Cat_ava_api4+ " size :"+adlist.size());
				PutOfferTo_TemDB(adlist);
				//logger.info("end put offer to tem db");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过Clickdealer接口获取offerList
	 * 
	 * author chenbq
	 * 
	 */
	public static List<OverseaAd_Tem> getOfferList() {
		// 定义初始化变量
		JSONObject json1;
		JSONObject json2;
		JSONArray array = null;
		List<OverseaAd_Tem> adsList = new ArrayList<OverseaAd_Tem>();
		OverseaProviderDao providerDao = (OverseaProviderDao) OverseaSpringHelper
				.getBean("dproviderDao");
		OverseaProvider provider = null;
		// String status = ""; // 返回是否成功状态

		try {
			//logger.info("get avazu4 offer start");
			String str = OverseaHttp.sendGet(offerUrl);
			//logger.info("the url is:" + offerUrl);

			json1 = JSON.parseObject(str);
			// status = json1.getString("status");
			json2 = json1.getJSONObject("ads");
			array = json2.getJSONArray("ad");

			// 获取offerList列表
			if (array != null) {
				System.out.println("avazu offer num:" + array.size());
				provider = providerDao.findSingle(OverseaEnum.Cat_ava_api4);
				for (int i = 0; (array != null) && (i < array.size()); i++) {
					JSONObject item = array.getJSONObject(i);
					OverseaAd_Tem aditem = new OverseaAd_Tem();
					String country = item.getString("countries");
					country = country.replace("|", ":");
					aditem.setCountry(country);
					if (country.equalsIgnoreCase("ALL")) {
						aditem.setEcountry("CN");
					}
					aditem.setName(item.getString("title"));
					aditem.setPkg(item.getString("pkgname"));
					String price = String.valueOf(item.getString("payout"))
							.trim();
					price = price.substring(0, price.length() - 1);
					aditem.setPrice(Float.parseFloat(price));
					aditem.setMainicon(item.getString("icon"));
					aditem.setType(OverseaEnum.AD_AFFLIATE);
					aditem.setProvider(Long.parseLong(String
							.valueOf(OverseaEnum.Cat_ava_api4)));
					aditem.setStatus(0);// 状态为0的是可用的，状态w为1是不可用的
					if (provider != null) {
						aditem.setCap(provider.getCap());
						aditem.setSinstall(provider.getSinstall());
						aditem.setWeight(provider.getPre_weight());
						aditem.setPreweight(provider.getPre_weight());
						//logger.info("the provider set right");
					} else {
						aditem.setCap(150); // 设置默认安装量
						aditem.setSinstall(25); //
						aditem.setWeight(20);
						//logger.info("the provider set default");
					}
					String track = item.getString("clkurl");
					aditem.setClick(track);
					//logger.info("track:" + track);
					aditem.setOfferid(item.getString("campaignid"));

					adsList.add(aditem);
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return adsList;
	}

	/**
	 * API PostCallBack
	 */
	public static void avazuCallBack(int provider) {

		// 重新取出来，将tarck地址进行修改
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
		String date = df.format(new Date());
		date = date + "000000";
		Long dateint = Long.parseLong(date);

		// 获取数据库中的数据
		OverseaAdDao adDao = (OverseaAdDao) OverseaSpringHelper
				.getBean("dadDao");

		List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday(
				OverseaEnum.AD_AFFLIATE, OverseaEnum.Cat_ava_api4, dateint);

		for (OverseaAd temp1 : allad2) {

			String track = temp1.getTrack();
			if (!track.contains("&dv5")) {// 处理未添加过的&dv1=[clickid]&dv5=[trafficsourceid]

				track += "&dv5=" + temp1.getId() + "{subway}";// +"&dv1="+System.currentTimeMillis();
			}
			temp1.setClick(track);
			adDao.update2(temp1);
		}
		logger.info("update track provider ="+OverseaEnum.Cat_ava_api4 +" size:"+allad2.size());

	}

	public static void main(String[] args) {

		List<OverseaAd_Tem> adlist = getOfferList();
		// 1002 http://api.aedxdrcb.com/gkview/postback/1039?adid={dv5}
		// if(adlist.size() >0){
		// updateGpOfferToDb(adlist,NgsteamEnum.AD_AFFLIATE,
		// NgsteamEnum.AVAZU_API);
		// }
	}

}
