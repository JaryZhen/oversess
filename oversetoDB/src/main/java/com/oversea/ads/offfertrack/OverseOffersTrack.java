package com.oversea.ads.offfertrack;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.oversea.ads.bean.OverseaAd;
import com.oversea.ads.dao.OverseaAdDao;
import com.oversea.ads.util.Base64Decoder;
import com.oversea.ads.util.OverseaStringUtil;
import com.oversea.util.OverseaSpringHelper;

public class OverseOffersTrack {
	protected static final Logger logger    = Logger.getLogger(OverseaBaseOffer.class);

	public static void main(String[] args) {
		
		String track = "http://track.56txs4.com/app/directlink?accesstoken=f7fa9289-97e3-43c6-aed5-934c1e368a1b&appid=63642&campaignpartnerid=36948&subid=&publishertoken=&publishername=&usertoken=&deviceAndroidId=&deviceIfa=&age=&gender=&publisher_type=&format=";
		int a  = track.indexOf("&subid=");
		track = track.substring(0,a);
		track=track+"&subid=44_{subway}";
		System.out.println(track);
	}
	public static void offersTrack(int [] prosint){
		// 获取数据库中的数据
		OverseaAdDao adDao = (OverseaAdDao) OverseaSpringHelper.getBean("dadDao");

		//重新取出来，将tarck地址进行修改
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
		String date = df.format(new Date());
		date = date+"000000";
		Long dateint = Long.parseLong(date);
		int affliate =3;

		for(int i = 0 ;i<prosint.length;i++){
			int provider = prosint[i];

			//		 if(provider==){
			//				List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);
			//				
			//				
			//				logger.info("update track provider ="+provider +" size:"+allad2.size());
			//			}else 

			if( provider==4013 ){
				List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);
				for (OverseaAd temp1 : allad2) {
					String track = temp1.getClick();
					if (track.contains("&q=q")) { // 处理track地址添加
						track=track.substring(0,track.length()-1);
						track +=temp1.getId() + "_{subway}";
					}
					temp1.setClick(track);
					adDao.update2track(temp1);
				}
				logger.info("update track provider ="+provider +" size:"+allad2.size());
			}else 
			if( provider==73 || provider==87 || provider==86 ){
				List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);
				for (OverseaAd temp1 : allad2) {
					String track = temp1.getClick();
					if (!track.contains("&a=")) { // 处理track地址添加
						track += "&a=" + temp1.getId() + "_{subway}";
					}
					temp1.setClick(track);
					adDao.update2track(temp1);
				}
				logger.info("update track provider ="+provider +" size:"+allad2.size());
			}else 
				if( provider==5001 ){
					List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);
					for (OverseaAd temp1 : allad2) {
						String track = temp1.getClick();
						if (!track.contains("&postback=")) { // 处理track地址添加
							String b = Base64Decoder.getBase64("http://post.omkol.com/jmichael/saysha/f5mbvt?cb="+temp1.getId()+"_0");
							track +="&postback="+ b;
						}
						temp1.setClick(track);
						adDao.update2track(temp1);
					}
					logger.info("update track provider ="+provider +" size:"+allad2.size());
				}else if(provider==4014){
					List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);
					for (OverseaAd temp1 : allad2) {
						String track = temp1.getClick();
						if (!track.contains("&postback=")) { // 处理track地址添加
							String b = Base64Decoder.getBase64("http://s2s.gokaog.com/jackson/kaoba/cmoc4?mj="+temp1.getId()+"_0");
							track +="&postback="+ b;
						}
						temp1.setClick(track);
						adDao.update2track(temp1);
					}
					logger.info("update track provider ="+provider +" size:"+allad2.size());
				}else 
					if( provider==42 ){
						List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);
						for (OverseaAd temp1 : allad2) {
							String track = temp1.getClick();
							if (track.contains("{adid}")) { // 处理track地址添加
								track = track.replace("{adid}", Long.toString(temp1.getId())+ "_{subway}");
							}
							temp1.setClick(track);
							adDao.update2track(temp1);
						}
						logger.info("update track provider ="+provider +" size:"+allad2.size());
					}else 
						if(provider==4012){
							List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);
							for (OverseaAd temp1 : allad2) {
								String track = temp1.getClick();
								track = track.replace("{p}", temp1.getId()+"_{subway}");
								track = track.replace("{transaction_id}", ""+temp1.getId());
								track +="&gaid=";
								temp1.setClick(track);
								adDao.update2track(temp1);
							}
							logger.info("update track provider ="+provider +" size:"+allad2.size());
						}else 
							if(provider==40 || provider==4007){
								List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);
								for (OverseaAd temp1 : allad2) {
									String track = temp1.getClick();
									if (!track.contains("&click_id")) {// 处理未添加过的
										track += "&click_id=" + temp1.getId() + "_{subway}";
									}
									temp1.setClick(track);
									adDao.update2track(temp1);
								}			
								logger.info("update track provider ="+provider +" size:"+allad2.size());
							}else 
								if(provider==19 || provider==99 || provider==2001 || provider==5004){
									List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);
									for (OverseaAd temp1 : allad2) {
										String track = temp1.getClick();
										track += "&d2="+temp1.getId()+"_{subway}";
										temp1.setClick(track);
										adDao.update2track(temp1);
									}
									logger.info("update track provider ="+provider +" size:"+allad2.size());
								}else 
									if( provider==92 || provider==97 ){
										List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);
										for (OverseaAd temp1 : allad2) {
											String track = temp1.getClick();
											if (!track.contains("&aff_sub")) {// 处理未添加过的
												track += "&aff_sub=" + temp1.getId() + "_{subway}";
											}
											temp1.setClick(track);
											adDao.update2track(temp1);
										}
										logger.info("update track provider ="+provider +" size:"+allad2.size());
									}else 
										if(provider==91  ){
											List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);
											for (OverseaAd temp1 : allad2) {
												String track = temp1.getClick();
												if (!track.contains("&aff_sub")) {// 处理未添加过的
													track += "&aff_sub2={aid}&aff_sub=" + temp1.getId() + "_{subway}";
												}
												temp1.setClick(track);
												adDao.update2track(temp1);
											}
											logger.info("update track provider ="+provider +" size:"+allad2.size());
										}else 
											if(provider==90){
												List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);
												for (OverseaAd temp1 : allad2) {
													String track = temp1.getClick();
													StringBuffer sBuffer = new StringBuffer(track);
													if (!track.contains("&sid=")) {// 处理track地址添加 &sub_id={YourClickID}
														sBuffer.append("&sid=");
														sBuffer.append(temp1.getId() + "_{subway}");
													}
													track = sBuffer.toString();
													temp1.setClick(track);
													adDao.update2track(temp1);

												}

												logger.info("update track provider ="+provider +" size:"+allad2.size());
											}else 
												if(provider==89 || provider==93){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();
														if (track.contains("&subid=")) {// 处理track地址
															// http://hasoffers.mobisummer.com/aff_c?offer_id={offer_id}&aff_id=1369
															int a  = track.indexOf("&subid=");
															track = track.substring(0,a);
															track = track+"&subid="+temp1.getId() + "_{subway}";
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==88 || provider==4010 || provider==1001 ||provider==5005){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();
														if (!track.contains("&aff_sub=")) {// 处理track地址
															// http://hasoffers.mobisummer.com/aff_c?offer_id={offer_id}&aff_id=1369
															track = track + "&aff_sub=" + temp1.getId() + "_{subway}";
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==23){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();
														if (!track.contains("&aff_sub=")) {// 处理track地址
															// http://hasoffers.mobisummer.com/aff_c?offer_id={offer_id}&aff_id=1369
															track = track + "&aff_sub=" + temp1.getId() + "_{subway}";
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==85){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();
														if (track.contains("?")) {// 处理track地址
															if (temp1.getOfferid() != null) {
																track = track.replace("{your_clickid_here}",
																		temp1.getId() + "_{subway}").replace(
																				"{your_subid_here}", temp1.getOfferid());
															} else {
																track = track.replace("{your_clickid_here}",
																		temp1.getId() + "_{subway}");
															}
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else  if(provider==84){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();
														if (track.contains("?")) {// 处理track地址
															if (temp1.getOfferid() != null) {
																track = track.replace("{your_clickid_here}",
																		temp1.getId() + "_{subway}").replace("{your_subid_here}", temp1.getOfferid());
															} else {
																track = track.replace("{your_clickid_here}",temp1.getId() + "_{subway}");
															}
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==83){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();
														if (!track.contains("&p1")) { // 处理track地址添加 &sub_id={YourClickID}
															track += "&aff_sub1=d&src=2&p1=mj&v1=" + temp1.getId()
															+ "_{subway}";
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==81){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();
														if (!track.contains("&aff_sub")) {// 处理未添加过的
															track += "&aff_sub=" + temp1.getId() + "_{subway}";
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==80){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);
													for (OverseaAd temp1 : allad2)
													{
														String track = temp1.getClick();
														//处理未添加过的   sid

														if(!track.contains("?subid1")){
															track+="?subid1="+temp1.getId()+"_{subway}";
														}
														temp1.setClick(track);
														adDao.update2track(temp1);

													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==79 || provider==5003){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {

														String track = temp1.getClick();
														if (!track.contains("&dv1")) {// 处理未添加过的&dv1=[clickid]&dv5=[trafficsourceid]

															track += "&dv1=" + temp1.getId() + "_{subway}";// +"&dv1="+System.currentTimeMillis();
														}
														temp1.setClick(track);
														adDao.update2(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==14){

													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);
													for (OverseaAd temp1 : allad2)
													{
														String track = temp1.getClick();
														if(!track.contains("&dv5")){//处理未添加过的&dv1=[clickid]&dv5=[trafficsourceid]

															track  +="&dv5="+temp1.getId()+"_{subway}";//+"&dv1="+System.currentTimeMillis();
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}
													logger.info("update track provider ="+provider +" size:"+allad2.size());

												}else if(provider==15){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2)
													{
														String track = temp1.getClick();
														if(!OverseaStringUtil.isBlank(track)&&!track.contains("aff_sub")){//处理未添加过的
															track  +="&aff_sub="+temp1.getId()+"_{subway}";
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}
													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==20){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2)
													{
														String track = temp1.getClick();
														StringBuffer sBuffer= new StringBuffer(track);
														if(!track.contains("&tid1=")){//处理track地址添加  &sub_id={YourClickID}
															sBuffer.append("&tid1=");
															sBuffer.append(temp1.getOfferid());
															sBuffer.append("&tid2=");
															sBuffer.append(temp1.getId()+"_{subway}");

														}
														track=sBuffer.toString();
														temp1.setClick(track);
														adDao.update2track(temp1);
													}
													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==22){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);
													for (OverseaAd temp1 : allad2)
													{
														String track = temp1.getClick();
														//处理未添加过的   sid

														if(!track.contains("&sid")){
															track+="&sid="+temp1.getId()+"_{subway}";
														}
														//logger.info("track="+track);
														temp1.setClick(track);
														adDao.update2track(temp1);

													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==25){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);
													for (OverseaAd temp1 : allad2)
													{
														String track = temp1.getClick();

														if(track.contains("&cv1v={adid}")){
															//处理track地址ttp://clks.appia.com/v2/clickAd.jsp?campaignId=485&siteId=8526&aaid=&ts=1436174037656&subSite={subID}&cv1n=adid&cv1v={adid}
															track =track.replace("{adid}", temp1.getId()+"_{subway}");
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==28){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();
														StringBuffer sBuffer = new StringBuffer(track);

														if (!track.contains("&custom_adid=")) { // 处理track地址添加
															// &sub_id={YourClickID}
															sBuffer.append("&custom_adid=");
															sBuffer.append(temp1.getId() + "_{subway}");
															track = sBuffer.toString();
															track = track.replace("[USER_ID]", "scrambleme");
															track = track.replace("{advertisingid}", "123");
														}

														temp1.setClick(track);
														adDao.update2track(temp1);
													}
													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==32){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();
														if (track.contains("?")) {// 处理track地址
															if (temp1.getOfferid() != null) {
																track = track.replace("{your_clickid_here}",
																		temp1.getId() + "_{subway}").replace(
																				"{your_subid_here}", temp1.getOfferid());
															} else {
																track = track.replace("{your_clickid_here}",
																		temp1.getId() + "_{subway}");
															}
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else  if(provider==37){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2)
													{
														String track = temp1.getClick();
														if(!track.contains("&aff_sub=")){//处理track地址
															track  =track+"&aff_sub="+temp1.getId()+"_{subway}";
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}
													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==35){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);
													for (OverseaAd temp1 : allad2)
													{
														String track = temp1.getClick();

														if(!track.contains("?subid1")){//处理未添加过的  
															track+="?subid1="+temp1.getId()+"_{subway}";

														}
														temp1.setClick(track);
														adDao.update2track(temp1);

													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==39){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2)
													{
														String track = temp1.getClick();
														StringBuffer sBuffer= new StringBuffer(track);

														if(!track.contains("&adid=")){   //处理track地址添加  &sub_id={YourClickID}
															sBuffer.append("&adid=");
															sBuffer.append(temp1.getId()+"_{subway}");
														}
														track=sBuffer.toString();
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==40){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);
													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();
														StringBuffer sBuffer = new StringBuffer(track);

														if (!track.contains("&click_id=")) { // 处理track地址添加
															// &sub_id={YourClickID}
															sBuffer.append("&click_id=");
															sBuffer.append(temp1.getId() + "_{subway}");
														}
														track = sBuffer.toString();
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==43){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();
														StringBuffer sBuffer = new StringBuffer(track);

														if (!track.contains("&p1=adid&v1=")) { // 处理track地址添加
															// &sub_id={YourClickID}
															sBuffer.append("&aff_sub1={subID}");
															sBuffer.append("&p1=adid&v1=");
															sBuffer.append(temp1.getId() + "_{subway}");
														}
														track = sBuffer.toString();
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==47){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2)
													{
														String track = temp1.getClick();
														StringBuffer sb = new StringBuffer(track);
														if(!track.contains("&aff_sub=")){
															//处理track地址   String TrackLink = "http://globalfastads.go2cloud.org/aff_c?offer_id={offer_id}&aff_id=3228&aff_sub=";
															sb  =sb.append("&aff_sub="+temp1.getId()+"_{subway}");
														}
														track=sb.toString();
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==49 || provider==48 || provider==65){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();
														if (!OverseaStringUtil.isBlank(track) && !track.contains("aff_sub")) {// 处理未添加过的
															track += "&aff_sub=" + temp1.getId() + "_{subway}";
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==51 || provider==4004){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();
														if (!track.contains("&aff_sub")) { // 处理track地址添加
															// &sub_id={YourClickID}
															//int s = track.indexOf("&subid1=");
															//track = track.substring(0, s);
															track += "&aff_sub=" + temp1.getId() + "_{subway}";
															// http://tyrooone.go2cloud.org/aff_c?offer_id=23812&aff_id=96&subid1=4545
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==53){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();
														if (!track.contains("&aff_sub=")) {// 处理track地址
															// http://hasoffers.mobisummer.com/aff_c?offer_id={offer_id}&aff_id=1369
															track = track + "&aff_sub=" + temp1.getId() + "_{subway}";
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==54){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();
														if (!track.contains("&aff_sub=")) {// 处理track地址
															// http://hasoffers.mobisummer.com/aff_c?offer_id={offer_id}&aff_id=1369
															track = track + "&aff_sub=" + temp1.getId() + "_{subway}";
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==55){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();
														if (!track.contains("&clickId=")) {// 处理track地址添加
															// &sub_id={YourClickID}
															track += "&segId=207566526&prod=com.speed.fast.clean&clickId="
																+ temp1.getId() + "_{subway}";
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==52){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();
														StringBuffer sBuffer = new StringBuffer(track);

														if (!track.contains("&v1=")) { // 处理track地址添加 &sub_id={YourClickID}
															sBuffer.append("&v1=");
															sBuffer.append(temp1.getId() + "_{subway}");
														}
														if (!track.contains("&aff_sub1")) {
															sBuffer.append("&aff_sub1={subID}");
														}
														track = sBuffer.toString();
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==60){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();
														if (!track.contains("&p1")) { // 处理track地址添加 &sub_id={YourClickID}
															track += "&aff_sub1=d&src=2&p1=offerback&v1=" + temp1.getId()
															+ "_{subway}";
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==62){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);
													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();
														if (!track.contains("&clickId=")) {// 处理track地址添加
															// &sub_id={YourClickID}
															track += "&clickId=" + temp1.getId() + "_{subway}";
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}
													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==66){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {

														String track = temp1.getClick();
														if (!track.contains("&dv1")) {// 处理未添加过的&dv1=[clickid]&dv5=[trafficsourceid]

															track += "&dv1=" + temp1.getId() + "_{subway}";// +"&dv1="+System.currentTimeMillis();
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==64){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();
														if (!track.contains("&aff_sub=")) {// 处理track地址
															// http://hasoffers.mobisummer.com/aff_c?offer_id={offer_id}&aff_id=1369
															track = track + "&aff_sub=" + temp1.getId() + "_{subway}";
														}

														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==67){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {

														String track = temp1.getClick();
														if (!track.contains("&dv1")) {// 处理未添加过的&dv1=[clickid]&dv5=[trafficsourceid]

															track += "&dv1=" + temp1.getId() + "_{subway}";// +"&dv1="+System.currentTimeMillis();
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==68){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();
														if (!track.contains("&aff_sub")) { // 处理track地址添加
															// &sub_id={YourClickID}
															//int s = track.indexOf("&subid1=");
															//track = track.substring(0, s);
															track += "&aff_sub=" + temp1.getId() + "_{subway}";
															// http://FL_tryoo3_APIone.go2cloud.org/aff_c?offer_id=23812&aff_id=96&subid1=4545
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==69 || provider==96){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();
														if (!track.contains("&aff_sub=")) {// 处理track地址
															// http://hasoffers.mobisummer.com/aff_c?offer_id={offer_id}&aff_id=1369
															track = track + "&aff_sub=" + temp1.getId() + "_{subway}";
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==70 || provider==98){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

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

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==71){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();

														if (!track.contains("{subway}")) {
															// 处理track地址ttp://clks.Adscend_media_api1_ym.com/v2/clickAd.jsp?campaignId=485&siteId=8526&aaid=&ts=1436174037656&subSite={subID}&cv1n=adid&cv1v={adid}
															track +=temp1.getId() + "_{subway}";
														}

														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==72){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();
														if (!track.contains("&click_id=")) {// 处理track地址添加
															// &sub_id={YourClickID}
															track += "&click_id="+ temp1.getId() + "_{subway}";
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==77 || provider==94){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);
													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();
														if (track.contains("?")) {// 处理track地址
															if (temp1.getOfferid() != null) {
																track = track.replace("{your_clickid_here}",
																		temp1.getId() + "_{subway}").replace(
																				"{your_subid_here}", temp1.getOfferid());
															} else {
																track = track.replace("{your_clickid_here}",
																		temp1.getId() + "_{subway}");
															}
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}else if(provider==76){
													List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday2(affliate, provider,dateint);

													for (OverseaAd temp1 : allad2) {
														String track = temp1.getClick();

														if (!track.contains("&transaction_id=")) {
															// 处理track地址ttp://clks.Adscend_media_api1_ym.com/v2/clickAd.jsp?campaignId=485&siteId=8526&aaid=&ts=1436174037656&subSite={subID}&cv1n=adid&cv1v={adid}
															track += "&transaction_id=" + temp1.getId() + "_{subway}";
														}
														temp1.setClick(track);
														adDao.update2track(temp1);
													}

													logger.info("update track provider ="+provider +" size:"+allad2.size());
												}
		}
	}

}
