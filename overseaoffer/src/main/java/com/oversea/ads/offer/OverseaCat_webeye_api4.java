package com.oversea.ads.offer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oversea.ads.bean.OverseaAd_Tem;
import com.oversea.ads.bean.OverseaProvider;
import com.oversea.ads.dao.OverseaProviderDao;

import com.oversea.ads.util.HttpUtil;
import com.oversea.factory.OverseaEnum;
import com.oversea.util.OverseaSpringHelper;

public class OverseaCat_webeye_api4 extends OverseaBaseOffer{
	
	private static Logger logger = Logger.getLogger(OverseaCat_webeye_api4.class);
	
	private final static String offerUrl = "http://api.webeyemob.com/adfetch/v1/s2s/campaign/get";
	
	

	public static void work() {
		List<OverseaAd_Tem> adlist = getOfferList();

		if (adlist.size() > 0) {
			Del_TemAd(OverseaEnum.Cat_webeye_api4);
			logger.info("begin put Cat_webeye_api4 db size :" + adlist.size());
			PutOfferTo_TemDB(adlist);
			logger.info("end put Cat_webeye_api4 to db");
		}
	}
	
	public static List<OverseaAd_Tem> getOfferList() {
		Map<String,String> header = new HashMap<String, String>();
		header.put("token", "98972636-986d-4654-ac40-4e2265a1d1c6");
		List<OverseaAd_Tem> adsList = new ArrayList<OverseaAd_Tem>();
		ZipInputStream zip = null;
		ByteArrayOutputStream bo = null;
		try{
			String jsonStr = HttpUtil.sendGet(offerUrl, header);
			logger.info(jsonStr);
			JSONObject json = JSON.parseObject(jsonStr);
			String download_url = json.getString("download_url");
			if(download_url==null || download_url.isEmpty()) return adsList;
			byte[] result = HttpUtil.sendGetByte(download_url,null);
			zip = new ZipInputStream(new ByteArrayInputStream(result));
			byte[] buff = new byte[2048];
			int r = 0;
			bo = new ByteArrayOutputStream();
			ZipEntry zipEntry = null;
			while((zipEntry = zip.getNextEntry()) != null){
				if(!zipEntry.isDirectory()){
					while((r=zip.read(buff))>0) 
						bo.write(buff, 0, r);
				}
			}
			String offerStr = bo.toString();
			String[] offerArray = offerStr.split("\n");
			OverseaProviderDao providerDao = (OverseaProviderDao) OverseaSpringHelper.getBean("dproviderDao");
			OverseaProvider provider = provider = providerDao.findSingle(OverseaEnum.Cat_webeye_api4);;
			for(String offer:offerArray){
				String[] ad = offer.split("\t");
				if(ad[6]==null || !ad[6].toLowerCase().equals("android")) continue;
				String offerId = ad[1];
				String pkg = ad[2];
				String click = ad[4];
				float price = Float.parseFloat(ad[5]);
				String icon = ad[10];
				String countrys = ad[11];
				String tile = ad[12];
				String country = countrys.replace(",", ":");
				OverseaAd_Tem adTem = new OverseaAd_Tem();
				adTem.setName(tile);
				adTem.setOfferid(offerId);
				adTem.setClick(click);
				adTem.setPkg(pkg);
				adTem.setMainicon(icon);
				adTem.setCountry(country);
				adTem.setPrice(price);
				adTem.setProvider(Long.parseLong(OverseaEnum.Cat_webeye_api4+""));
				adTem.setType(OverseaEnum.AD_AFFLIATE);
				adTem.setObject_cap(0);
				adTem.setStatus(0);
				adTem.setPush_rate(0);
				if (provider != null) {
					adTem.setCap(provider.getCap());
					adTem.setSinstall(provider.getSinstall());
					adTem.setWeight(provider.getPre_weight());
					adTem.setPreweight(provider.getPre_weight());
					//logger.info("the provider set right");
				} else {
					adTem.setCap(20);
					//logger.info("the provider set default");
				}
				adsList.add(adTem);
			}
		}catch (Exception e) {
			logger.error(e);
		}finally{
			try{
				if(zip!=null) zip.close();
				if(bo!=null) bo.close();
			}catch (Exception e) {
				logger.error(e);
			}
		}
		
		return adsList;
	}

	
//	public void postCallBack() {
//		// 重新取出来，将tarck地址进行修改,增加回调参数
//		int provider = OverseaEnum.Cat_webeye_api4;
//		String date = DateUtil.getNowDataStr("yyyyMMdd");
//		date = date + "000000";
//		Long dateint = Long.parseLong(date);
//		// 获取数据库中的数据
//		CacheAdDAO adDao = SpringHelper.getBean("adDAO",CacheAdDAO.class);
//		List<Ad> allad2 = adDao.findAffliateByProviderInsertToday2(
//				OverseaEnum.AD_AFFLIATE, provider, dateint);
//
//		for (Ad temp1 : allad2) {
//			String track = temp1.getClick();
//			track = track.replace("{p}", temp1.getId()+"_{subway}");
//			track = track.replace("{transaction_id}", ""+temp1.getId());
//			temp1.setTrack(track);
//			temp1.setClick(track);
//			adDao.update2track(temp1);
//		}
//		logger.info("update Cat_webeye_api4 down =" + provider);
//		
//	}

	public static void main(String[] args) {
		getOfferList();
	}

}
