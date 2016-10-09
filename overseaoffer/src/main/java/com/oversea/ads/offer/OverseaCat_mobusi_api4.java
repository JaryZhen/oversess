package com.oversea.ads.offer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.oversea.ads.bean.OverseaAd;
import com.oversea.ads.bean.OverseaAd_Tem;
import com.oversea.ads.bean.OverseaProvider;
import com.oversea.ads.dao.OverseaAdDao;
import com.oversea.ads.dao.OverseaProviderDao;
import com.oversea.ads.util.OverseaStringUtil;
import com.oversea.factory.OverseaEnum;
import com.oversea.util.OverseaHttp;
import com.oversea.util.OverseaSpringHelper;

public class OverseaCat_mobusi_api4 extends OverseaBaseOffer {

	private static String offerUrl = "http://api.leadzu.com/offer.find";
	private static String offerUrl2 = "http://api.leadzu.com/offer.request";
	//\"api_key\":\"579f449c2111bd1fcd7486f6e0103190\",\"user_id\":\"9398\
	public static void work() {
		try {
			List<OverseaAd_Tem> adlist = getOfferList();

			if (adlist.size() > 0) {
				
				Del_TemAd(OverseaEnum.Cat_mobusi_api4);
				logger.info("begin put Cat_mobusi_api4= "+OverseaEnum.Cat_mobusi_api4+ " size :"+adlist.size());
				PutOfferTo_TemDB(adlist);
				//logger.info("end put offer to db");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<OverseaAd_Tem> getOfferList() {
		// 定义初始化变量
		JSONObject json = null;
		JSONObject array = null;
		List<OverseaAd_Tem> adsList = new ArrayList<OverseaAd_Tem>();
		OverseaProviderDao providerDao =(OverseaProviderDao) OverseaSpringHelper.getBean("dproviderDao");
		OverseaProvider provider = null;
		String ids="[";
		try {
			logger.info("get mobusi offer start");
			//all offer
			String str = OverseaHttp.sendPost(offerUrl,"{\"api_key\":\"579f449c2111bd1fcd7486f6e0103190\",\"user_id\":\"9398\",\"device\":\"mobile\",\"store\":\"and\",\"type\":[\"cpa\",\"cpi\"],\"limit \":\"500\",\"skip\":\"10\",\"currency\":\"USD\",\"approved\":true}");
			//logger.info("the url is:" + offerUrl);
			json = JSON.parseObject(str);
			//logger.info("str:"+str);
			array = json.getJSONObject("answer");
			
			// 获取offerList列表
			if (array != null) {
				logger.info("mobusi offer size:" + array.size());
				Set<Entry<String, Object>> entrySet1 = array.entrySet();
				Iterator<Entry<String, Object>> iterator = entrySet1.iterator();

				provider = providerDao.findSingle(OverseaEnum.Cat_mobusi_api4);
				while (iterator.hasNext()) {
					Map.Entry<String, Object> map =  iterator.next();
					JSONObject item = (JSONObject) map.getValue();
					String pkg = item.getString("appid");
					if ("".equals(pkg) || OverseaStringUtil.isNum(pkg))
						continue;
					// 开始获取offer对象
					JSONObject countries = item.getJSONObject("countries");
					Set<Entry<String,Object>> couset = countries.entrySet();
					Iterator<Entry<String, Object>> iter = couset.iterator();
					 ids = ids+item.getString("id").trim()+",";
					 String id = item.getString("id").trim();
					//Map<String,String> cu = getCoutry_url(id);
					//if(cu ==null) continue;
					
					while (iter.hasNext()) {
						Map.Entry<String, Object> mapc = iter.next();
						String country =mapc.getKey().toUpperCase();
						//if (cu.get(country)==null) continue;
						JSONObject map_coutry = (JSONObject) mapc.getValue();
						OverseaAd_Tem aditem = new OverseaAd_Tem();
						// 循环 只是为了price 
						String price = map_coutry.getString("payout").trim();
						if ("REV/SHARE".equals(price)) {
							aditem.setPrice(0f);
						} else
							aditem.setPrice(Float.parseFloat(price));
						//logger.info("price:" + price);

						// 通过map 取得 getCoutry_url();
						//aditem.setClick(cu.get(country));
						//logger.info("url "+cu.get(country));
						//logger.info("country:" + country);
						//logger.info("id+c=url :"+id+"+"+country+" == ?");
						String url =map_coutry.getString("url");
						if(url==null) continue;
						aditem.setClick(url);
						//logger.info("url "+url);
						aditem.setCountry(country);

						aditem.setOfferid(id);
						aditem.setName(item.getString("title"));

						aditem.setPkg(pkg);
						//logger.info("pkg:" + pkg);

						aditem.setType(OverseaEnum.AD_AFFLIATE);
						aditem.setProvider(Long.parseLong(String.valueOf(OverseaEnum.Cat_mobusi_api4)));

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
							aditem.setWeight(50);
							aditem.setPreweight(5);
							//logger.info("the provider set default");
						}
						adsList.add(aditem);
					}

				}
			}
			//}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 //adsList =getCoutry_url(adsList,ids);
		return adsList;
	}
	public static List<OverseaAd_Tem> getCoutry_url(List<OverseaAd_Tem> list,String id){
		// 定义初始化变量
		JSONObject json = null;
		JSONObject array = null;
		Map<String, String> map_url=null;//id+country
		List<OverseaAd_Tem> adlist = list;
		try {
			//"ids":[3916,1234,12345]}
			id=id.substring(0,id.length()-1);
			String str = OverseaHttp.sendPost(offerUrl2,"{\"api_key\":\"579f449c2111bd1fcd7486f6e0103190\",\"user_id\":\"9398\",\"ids\":"+id+"]}");
			json = JSON.parseObject(str);
			//logger.info("id str:"+id+"=="+str);
			array = json.getJSONObject("answer");
			// 获取offerList列表
			if (array != null) {
				map_url= new HashMap<String, String>();
				Set<Entry<String, Object>> entrySet1 = array.entrySet();
				Iterator<Entry<String, Object>> iterator = entrySet1.iterator();
				while (iterator.hasNext()) {
					Map.Entry<String, Object> map =  iterator.next();
					String ids = map.getKey().toUpperCase();
					//logger.info("coutrys :"+ids);
					JSONObject idlist = (JSONObject)map.getValue();
					
					JSONObject countries = idlist.getJSONObject("countries");
					String offerid = idlist.getString("id");
					Set<Entry<String,Object>> couset = countries.entrySet();
					Iterator<Entry<String, Object>> iter = couset.iterator();

					while (iter.hasNext()) {
						Map.Entry<String, Object> mapc = iter.next();
						String country =mapc.getKey().toUpperCase();
						JSONObject map_coutry = (JSONObject) mapc.getValue();					
						String appro= map_coutry.getString("approved");
						//logger.info("appro :"+appro);
						if(appro==null) continue;
						String url = map_coutry.getString("url");
						if (url ==null) continue;
						map_url.put(offerid+country, url);
						//logger.info("id :"+id);
						//logger.info("id+c=url :"+offerid+"+"+country+" == "+url);
					}
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	//	logger.info("adlist size "+ adlist.size());
		Iterator<OverseaAd_Tem> tem = adlist.iterator();
		while(tem.hasNext()){
			OverseaAd_Tem ls =tem.next();
			String map_ur = map_url.get(ls.getOfferid()+ls.getCountry());
			if(map_ur!=null){
				ls.setClick(map_ur);
			}else{
				tem.remove();
			}
		}
	//	logger.info("adlist size "+ adlist.size());
		return adlist;
	}
	public static void Cat_mobusi_api4Track(int provider) {
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

			if (track.contains("{YOUR_COMPANY_CODE}")
					&& track.contains("{adid}")) { // 处理track地址添加
													// &sub_id={YourClickID}
				track = track.replace("{YOUR_COMPANY_CODE}", "0AR1API");
				track = track.replace("{adid}", Long.toString(temp1.getId())
						+ "_{subway}");
			}

			temp1.setClick(track);
			adDao.update2track(temp1);
		}
		logger.info("update track provider ="+OverseaEnum.Cat_mobusi_api4 +" size:"+allad2.size());

	}

	
	public static void main(String[] args) {
		// http://api.hdyfhpoi.com/gkview/postback/1023?adid={adid}
		getOfferList();
		//getCoutry_url("81697");
	}

}
