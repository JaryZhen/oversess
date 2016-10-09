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

public class OverseaCat_ir_api4 extends OverseaBaseOffer {

	// private static String offerUrl =
	// "https://api.mobilecore.com:8080/v2/getAds?siteid=29536&token=1493868d8a9d6e26dd49f8fc166bbe89&platform=Android";
	private static String offerUrl = "http://api.apprevolve.com/v2/getAds?siteid=40581&token=7i2BUXAtPUS6EQq67D3ATw&platform=Android";

	public static void work() {

		try {
			List<OverseaAd_Tem> adlist = getOfferList();

			if (adlist.size() > 0) {
				Del_TemAd(OverseaEnum.Cat_ir_api4);
				logger.info("begin put Cat_ir_api4= "+OverseaEnum.Cat_ir_api4+ " size :"+adlist.size());
				PutOfferTo_TemDB(adlist);
				//logger.info("end put Cat_ir_api4 to tem db");
				
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
		JSONObject json = null;
		JSONArray array = null;
		List<OverseaAd_Tem> adsList = new ArrayList<OverseaAd_Tem>();
		OverseaProviderDao providerDao = (OverseaProviderDao) OverseaSpringHelper
				.getBean("dproviderDao");
		OverseaProvider provider = null;
		try {
			//logger.info("get Cat_ir_api4 offer start");
			String str = OverseaHttp.sendGet(offerUrl);
			//logger.info("the url is:" + offerUrl);

			json = JSON.parseObject(str);
			array = json.getJSONArray("ads");

			// 获取offerList列表
			if (array.size() > 0) {
				//logger.info("Ironsource offer size:" + array.size());
				provider = providerDao.findSingle(OverseaEnum.Cat_ir_api4);
				for (int i = 0; i < array.size(); i++) {
					JSONObject item = array.getJSONObject(i);
					// 开始获取offer对象				
					// String country = item.getString("countries").trim();
					JSONArray country = item.getJSONArray("geoTargeting");
					for (Object obj_country : country) {
						OverseaAd_Tem aditem = new OverseaAd_Tem();
						aditem.setOfferid(item.getString("internal_campaign_id"));
						aditem.setName(item.getString("title"));
						//logger.info("name:" + item.getString("title"));
						// aditem.setMainicon(item.getString("iconurl"));
						aditem.setPkg(item.getString("packageName"));
						String price = item.getString("bid").trim();
						aditem.setPrice(Float.parseFloat(price));

						aditem.setType(OverseaEnum.AD_AFFLIATE);
						aditem.setProvider(Long.parseLong(String
								.valueOf(OverseaEnum.Cat_ir_api4)));


						aditem.setStatus(0);// 状态为0的是可用的，状态w为1是不可用的
						aditem.setObject_cap(10);
						if (provider != null) {
							aditem.setCap(provider.getCap());
							aditem.setSinstall(provider.getSinstall());
							aditem.setWeight(provider.getPre_weight());
							aditem.setPreweight(provider.getPre_weight());
							//logger.info("the provider set right");
						} else {
							aditem.setCap(10); // 设置默认安装量
							aditem.setSinstall(100); //
							aditem.setWeight(5);
							aditem.setPreweight(0);
							//logger.info("the provider set default");
						}
						aditem.setClick(item.getString("clickURL"));
						if ("com.cmcm.locker".equals(item.getString("packageName"))){
							logger.info(obj_country.toString());
						}
						aditem.setCountry(obj_country.toString());
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

	public static void Cat_ir_api4Track(int provider) {
		// TODO Auto-generated method stub
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
			if (!track.contains("&p1")) { // 处理track地址添加 &sub_id={YourClickID}
				track += "&aff_sub1=d&src=2&p1=offerback&v1=" + temp1.getId()
						+ "_{subway}";
			}
			temp1.setClick(track);
			adDao.update2track(temp1);
		}
		logger.info("update track provider ="+OverseaEnum.Cat_ir_api4 +" size:"+allad2.size());

	}

	public static void main(String[] args) {
		// 52 1043 http://post.freemobinet.com/jary/postback/1043?offerback={p1}
		getOfferList();
	}
}
