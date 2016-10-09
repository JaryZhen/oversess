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
import com.oversea.ads.util.OverseaStringUtil;
import com.oversea.factory.OverseaEnum;
import com.oversea.util.OverseaHttp;
import com.oversea.util.OverseaSpringHelper;

public class OverseaCat_clickky_api4 extends OverseaBaseOffer {
	private static String offerUrl = "https://cpactions.com/api/v1.0/feed/public/offers?site_id=9291&hash=7bc580e8755a310dd64ba4e0cee5fe3adfcaa084&filters[os][and][]=android&type=4&items_per_page=1000&page=";
	//static int pc=0,i=1;
	public static void work() {

		try {
			List<OverseaAd_Tem> adlist = new ArrayList<OverseaAd_Tem>();
			/*List<OverseaAd_Tem> adlist2=null;
			do {
				logger.info("i="+i);
				adlist2 = getOfferList(offerUrl+i);
				adlist.addAll(adlist2);
				logger.info("begin put Cat_clickky_api4= "+ OverseaEnum.Cat_clickky_api4 + " size :"+ adlist2.size());
				logger.info("begin put Cat_clickky_api4= "+ OverseaEnum.Cat_clickky_api4 + " size2 :"+ adlist.size());
				i++;
			} while (pc>=i);*/
			
			adlist = getOfferList();
			if (adlist.size() > 0) {
				logger.info("begin put Cat_clickky_api4= "
						+ OverseaEnum.Cat_clickky_api4 + " size :"
						+ adlist.size());
				Del_TemAd(OverseaEnum.Cat_clickky_api4);
				PutOfferTo_TemDB(adlist);
				logger.info("end put Cat_clickky_api4 to db");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void test() {
		Cat_clickky_api4PostCallBack(OverseaEnum.Cat_clickky_api4);
	}

	public static List<OverseaAd_Tem> getOfferList() {
		List<OverseaAd_Tem> adsList = new ArrayList<OverseaAd_Tem>();
		try{
			int page = 1;
			List<OverseaAd_Tem> ads = new ArrayList<OverseaAd_Tem>();
			int maxPage = getAds(page,ads);
			adsList.addAll(ads);
			for(int i=0;i<maxPage-1;i++){
				getAds(++page,ads);
				adsList.addAll(ads);
			}
		}catch (Exception e) {
			logger.error(e);
		}
		return adsList;
	}
	
	
	public static int getAds(int page,List<OverseaAd_Tem> ads){

		// 定义初始化变量
		JSONObject json = null;
		JSONArray array = null;
		List<OverseaAd_Tem> adsList = new ArrayList<OverseaAd_Tem>();
		ads.clear();
		OverseaProviderDao providerDao =null;// (OverseaProviderDao) OverseaSpringHelper.getBean("dproviderDao");
		OverseaProvider provider = null;
		int pagecount = 0;
		try {
			// logger.info("get artclick offer start");
			String str = OverseaHttp.sendGet(offerUrl+page);
			 //logger.info("the url is:=" + str);

			json = JSON.parseObject(str);
			// JSONObject response = json.getJSONObject("response");
			String status = "1";
		    pagecount = Integer.parseInt(json.getString("pagecount"));
			array = json.getJSONArray("offers");
			if ("1".equals(status)) {
				// 获取offerList列表
				if (array!=null &&array.size() > 0) {
					// logger.info("artclick offer size:" + array.size());
				//	provider = providerDao.findSingle(OverseaEnum.Cat_clickky_api4);
					for (int i = 0; i < array.size(); i++) {
						JSONObject item = array.getJSONObject(i);
						OverseaAd_Tem aditem = new OverseaAd_Tem();
						// 开始获取offer对象

						JSONObject targeting = item.getJSONObject("targeting");
						JSONObject allowed = targeting.getJSONObject("allowed");
						logger.info("allowed:"+i);
						String os="o" ;
						try {
							os = allowed.getJSONArray("os").toString();
						} catch (Exception e) {
							// TODO: handle exception
							logger.info("os...........");
							e.printStackTrace();
							continue;
						}
					

						if (os.contains("Android")) {
							String name = item.getString("name");
							// name = handleName(item.getString("name"));
							aditem.setName(name);
							aditem.setPrice(Float.parseFloat(item
									.getString("payout")));
							String psg = item.getString("app_id");
							if ("-1".equals(psg))
								continue;
							aditem.setPkg(psg);
							aditem.setOfferid(item.getString("offer_id"));
							aditem.setType(OverseaEnum.AD_AFFLIATE);
							aditem.setProvider(Long.parseLong(String
									.valueOf(OverseaEnum.Cat_clickky_api4)));
							String country=null;
							try{
								country = allowed.getJSONArray("countries").toString();
							}catch (Exception e) {
								// TODO: handle exception
								logger.info("exing.........."+item.getString("offer_id"));
								e.printStackTrace();
								continue;
							}
						    //logger.info("id："+item.getString("offer_id"));
							if(country.length()<=2 || country==null)continue;
							country = country.substring(2, country.length() - 2);
							country = country.replace("\",\"", ":");
							aditem.setCountry(country);
							aditem.setEcountry("CN");
							aditem.setStatus(0);// 状态为0的是可用的，状态w为1是不可用的

							int oc = 0;
							float oc1 = 0;
							try {
								String cap = item
										.getString("caps_daily_remaining");
								if ("unlimited".equals(cap)) {
									oc = 0;
								} else {
									oc1 = Float.parseFloat(cap);
									oc = (int) oc1;
								}
								// logger.info("oc:"+oc);
							} catch (Exception e) {
								// TODO: handle exception
								// e.printStackTrace();
							}
							if (oc == -1)
								oc = 0;
							aditem.setObject_cap(oc);
							if (provider != null) {
								aditem.setCap(provider.getCap());
								aditem.setSinstall(provider.getSinstall());
								aditem.setWeight(provider.getPre_weight());
								aditem.setPreweight(provider.getPre_weight());
								// logger.info("the provider set right");
							} else {
								aditem.setCap(20); // 设置默认安装量
								aditem.setSinstall(25); //
								aditem.setWeight(70);
								// logger.info("the provider set default");
							}
							String click = item.getString("link");
							// logger.info("click:"+click);
							if (click == null)
								continue;
							aditem.setClick(click);
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
		ads.addAll(adsList);
		return pagecount;
	}


	private static String handleName(String str) {
		String name = "";
		if (str.length() > 0) {
			if (str.indexOf("-") > 0) {
				String[] names = str.split("-");
				name = names[0].trim();

			} else {
				name = str;
			}
		}
		return name;
	}

	/**
	 * 判断地址是否包含google
	 * 
	 * @param os
	 *            os
	 * @return
	 */
	private static boolean decideAndroid(String os) {
		if (!OverseaStringUtil.isBlank(os)) {
			if ("Android".equalsIgnoreCase(os)) {
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * Cat_clickky_api4 PostCallBack
	 */
	public static void Cat_clickky_api4PostCallBack(int provider) {
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
		if (allad2.size() > 0) {
			for (OverseaAd temp1 : allad2) {
				String track = temp1.getClick();
				if (track.contains("?")) {// 处理track地址
					if (temp1.getOfferid() != null) {
						track = track.replace("{your_clickid_here}",
								temp1.getId() + "_{subway}").replace(
								"{your_subid_here}", temp1.getOfferid());
					} else {
						track = track.replace("{your_clickid_here}", temp1
								.getId()
								+ "_{subway}");
					}
				}
				temp1.setClick(track);
				adDao.update2track(temp1);
			}

		}
		logger.info("update track provider =" + OverseaEnum.Cat_clickky_api4+ " size:" + allad2.size());

	}

	public static void main(String[] args) {
//		List<OverseaAd_Tem> adlist 
//		// http://api.hdyfhpoi.com/gkview/postback/1014?&adid={aff_sub}&offerid={source}
//		getOfferList();
	}

}
