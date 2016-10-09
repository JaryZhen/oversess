package com.oversea.ads.offer;

import java.io.IOException;
import java.text.DecimalFormat;
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

public class OverseaCat_Tabatoo_api4 extends OverseaBaseOffer {

	private static String offerUrl = "http://admin.tabatoo.com/offers/list?appid=2f6e225df3bcdae2&n=10&t=json&&all=1&incent=0&platform=0&geo=HK";
	private static String offerUrl1 = "http://admin.tabatoo.com/offers/list?appid=2f6e225df3bcdae2&n=10&t=json&&all=1&incent=1&platform=0&geo=HK";

	public static void work() {
		

		try {
			List<OverseaAd_Tem> adlist = getOfferList(offerUrl);
			List<OverseaAd_Tem> adlist1 = getOfferList(offerUrl1);
			adlist.addAll(adlist1);
			
			if (adlist.size() > 0) {
				Del_TemAd(OverseaEnum.Cat_Tabatoo_api4);
				logger.info("begin put Cat_Tabatoo_api4= "+OverseaEnum.Cat_Tabatoo_api4+ " size :"+adlist.size());
				PutOfferTo_TemDB(adlist);
				//logger.info("end put offer to db");
				
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
	public static List<OverseaAd_Tem> getOfferList(String s) {
		// 定义初始化变量
		JSONObject json = null;
		JSONArray array = null;
		List<OverseaAd_Tem> adsList = new ArrayList<OverseaAd_Tem>();
		OverseaProviderDao providerDao = (OverseaProviderDao) OverseaSpringHelper.getBean("dproviderDao");
		OverseaProvider provider = null;
		try {
			//logger.info("get Cat_Tabatoo_api4 offer start");
			String str = OverseaHttp.sendGet(s);
			//logger.info("the url is:" + offerUrl);

			json = JSON.parseObject(str);
			array = json.getJSONArray("offers");

			// 获取offerList列表
			if (array.size() > 0) {
				//logger.info("Cat_Tabatoo_api4 offer size:" + array.size());
				provider = providerDao.findSingle(OverseaEnum.Cat_Tabatoo_api4);
				for (int i = 0; i < array.size(); i++) {
					JSONObject item = array.getJSONObject(i);
					OverseaAd_Tem aditem = new OverseaAd_Tem();
					// 开始获取offer对象
					if(!"0".equals(item.getString("platform"))) continue;
					aditem.setOfferid(item.getString("externalOfferId"));
					aditem.setName(item.getString("name"));
					//logger.info("name:" + item.getString("name"));
					aditem.setMainicon(item.getString("iconLink"));
					aditem.setPkg(item.getString("packageName"));
					// 四舍五入
					float payout1 = Float.parseFloat(item.getString("bid")
							.trim());
					DecimalFormat fnum = new DecimalFormat("##0.00");
					String payout = fnum.format(payout1);
					aditem.setPrice(Float.parseFloat(payout));

					aditem.setType(OverseaEnum.AD_AFFLIATE);
					aditem.setProvider(Long.parseLong(String
							.valueOf(OverseaEnum.Cat_Tabatoo_api4)));
					String country = item.getString("geo");
					country = country.replaceAll(",", ":");
					//logger.info("country:"+country);
					aditem.setCountry(country);

					aditem.setStatus(0);// 状态为0的是可用的，状态w为1是不可用的

					if (provider != null) {
						aditem.setCap(provider.getCap());
						aditem.setSinstall(provider.getSinstall());
						aditem.setWeight(provider.getPre_weight());
						aditem.setPreweight(provider.getPre_weight());
					} else {
						aditem.setCap(20); // 设置默认安装量
						aditem.setSinstall(25); //
						aditem.setWeight(50);
						aditem.setPreweight(5);
					}
					aditem.setClick(item.getString("shortenURL"));
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

	
	
	public static void tbTrack(int provider) {
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
			StringBuffer sBuffer = new StringBuffer(track);

			if (!track.contains("?&p1=")) { // 处理track地址添加
													// &sub_id={YourClickID}
				sBuffer.append("?&p1=");
				sBuffer.append(temp1.getId() + "_{subway}"+"&subid=906d21a53e2eda2e");
			}
			track = sBuffer.toString();
			temp1.setClick(track);
			adDao.update2track(temp1);
		}
		logger.info("update track provider ="+OverseaEnum.Cat_Tabatoo_api4 +" size:"+allad2.size());

	}

	public static void main(String[] args) {
		// http://api.hdyfhpoi.com/gkview/postback/1054?adid=p1
		//List<OverseaAd_Tem> list = getOfferList();
	}

}
