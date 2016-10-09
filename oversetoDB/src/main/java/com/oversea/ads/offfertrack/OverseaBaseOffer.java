package com.oversea.ads.offfertrack;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;


import com.oversea.ads.bean.OverseaAd;
import com.oversea.ads.bean.OverseaAd_Tem;
import com.oversea.ads.bean.OverseaApk;
import com.oversea.ads.bean.OverseaBlackApp;
import com.oversea.ads.bean.OverseaClassApk;
import com.oversea.ads.bean.OverseaOfferBlackList;
import com.oversea.ads.bean.OverseaProvider;
import com.oversea.ads.dao.OverseaAdDao;
import com.oversea.ads.dao.OverseaAd_TemDao;
import com.oversea.ads.dao.OverseaApkDao;
import com.oversea.ads.dao.OverseaBlackAppDao;
import com.oversea.ads.dao.OverseaClassApkDao;
import com.oversea.ads.dao.OverseaOfferBlackListDao;
import com.oversea.ads.dao.OverseaProviderDao;
import com.oversea.ads.util.OverseaStringUtil;
import com.oversea.factory.OverseaCacheFactory;
import com.oversea.factory.OverseaEnum;
import com.oversea.factory.OverseaRedisDb;
import com.oversea.util.OverseaMd5;
import com.oversea.util.OverseaSpringHelper;


public class OverseaBaseOffer
{

	protected static final Logger logger    = Logger
	.getLogger(OverseaBaseOffer.class);
	// 非常重要
	protected static final String[] cdnUrlPrefixs = new String[]{
		"http://down.olacdn.com/apk/",
		"http://down.ogamek.com/apk/",
		"http://down.gamedcdn.com/apk/",
		"http://down.appdcdn.com/apk/",
		"http://down.dokcdn.com/apk/",
		"http://down.agacdn.com/apk/",
		"http://down.acpdown.com/apk/",
		"http://down.acfocdn.com/apk/",
		"http://down.awfocdn.com/apk/",
		"http://down.wafocdn.com/apk/",
		"http://down.wokacdn.com/apk/",
		"http://down.akwacdn.com/apk/",
		"http://down.akoacdn.com/apk/",
		"http://down.allacdn.com/apk/",
		"http://down.olaocdn.com/apk/",
		"http://down.alibcdn.com/apk/",
		"http://down.aliacdn.com/apk/"};            // 新服务器更新地址
	protected static final int cdnNum = 17;
	protected static String[] countrys = new String[] { "AD", "AE", "AL", "AO", "AR", "AT",
		"BB", "BD", "BE", "BF", "BG", "BJ", "BN", "BO", "BR", "BS",
		"BT", "BY", "BZ", "CA", "CD", "CF", "CG", "CH", "CI", "CL",
		"CM", "CN", "CO", "CR", "CU", "CV", "CZ", "DE", "DJ", "DK",
		"DM", "DO", "DZ", "EC", "EE", "EG", "ER", "ES", "ET", "FI",
		"FR", "FR", "GA", "GD", "GH", "GM", "GN", "GQ", "GR", "GT",
		"GW", "GY", "HK", "HN", "HR", "HT", "HU", "ID", "IE", "IL",
		"IN", "IN", "IQ", "IS", "IT", "JM", "JP", "KE", "KH", "KM",
		"KP", "KR", "LA", "LI", "LK", "LR", "LS", "LT", "LU", "LV",
		"LY", "MA", "MC", "MD", "ME", "MG", "MK", "ML", "MM", "MN",
		"MO", "MR", "MT", "MU", "MV", "MW", "MX", "MX", "MY", "MZ",
		"NA", "NE", "NG", "NI", "NL", "NO", "NP", "PA", "PE", "PH",
		"PK", "PL", "PR", "PT", "PY", "RO", "RS", "RU", "RW", "SC",
		"SD", "SE", "SG", "SI", "SK", "SL", "SM", "SN", "SO", "SR",
		"SY", "SZ", "TD", "TG", "TH", "TL", "TN", "TR", "TT", "TW",
		"TZ", "UA", "UG", "UK", "UK", "US", "US", "US", "UY", "VE",
		"VN", "ZA", "ZM", "ZW" };

	protected static String shPath = "/data/tools/nginx/html/sync.sh ";
	protected static String apkPath = "/data/tools/nginx/html/apk";


	public static void work()
	{

	}
	public static void PutOfferTo_TemDB(List<OverseaAd_Tem> adlist){
		if(adlist!=null&&adlist.size()>0){
			OverseaAd_TemDao dtemDao = (OverseaAd_TemDao) OverseaSpringHelper.getBean("dtemDao"); // 定义ad表的dao对象
			for (OverseaAd_Tem item : adlist) {
				dtemDao.insert_tem(item);
			}
		}
	}

	public static void Del_TemAd(int provider){
		OverseaAd_TemDao dtemDao = (OverseaAd_TemDao) OverseaSpringHelper
		.getBean("dtemDao"); // 定义ad表的dao对象
		dtemDao.delAdTem(provider);
	}
	
	public static void updateGpOfferToDb2(Map<String,String> blackMap,String pros){

		logger.info("jaryzhen updateGpOfferToDb2  start.............");
		Map<String, String> pkgMap = new HashMap<String, String>();//key : pkg ---》value：pkg

		
		//String pros=null;
		String pros_cahe=",";
		//根据provider 取 List<overseaAd> adsList
		List<OverseaAd_Tem> offer_Tem_List =null;
		//
		List<OverseaAd_Tem> offer_com_List = new ArrayList<OverseaAd_Tem>();
		
		//将adlist 格式化   便于与数据库对比
		List<OverseaAd_Tem> pkglist = new ArrayList<OverseaAd_Tem>(); //overseaAd 对象------------------------------------------------
		OverseaAd_Tem aditem = new OverseaAd_Tem();
		Map<String, OverseaAd_Tem> offerMap = new HashMap<String, OverseaAd_Tem>(); //key : pkg+country ---》 value：overseaAd
		OverseaAd_TemDao temDao = (OverseaAd_TemDao) OverseaSpringHelper.getBean("dtemDao");
		OverseaApkDao pkgdao = (OverseaApkDao) OverseaSpringHelper.getBean("dapkDao");
		// 获取数据库中的数据
		OverseaAdDao adDao = (OverseaAdDao) OverseaSpringHelper.getBean("dadDao");
		//provide表
		OverseaProviderDao proDao= (OverseaProviderDao) OverseaSpringHelper.getBean("dproviderDao");

		//取provider
		//proList = temDao.findProviders();

		//offer  list
		List<String> offer_list = new ArrayList<String>();// list : pkg+country  
		
		//取adlist
		offer_Tem_List=temDao.findAffliateByProvider(pros);
		logger.info(" jaryzhen blackMap size :"+blackMap.size());
		logger.info(" jaryzhen before black offer_tem_List size():"+offer_Tem_List.size());
		
		//获取黑名单map
		//Map<String,String> blackMap = getBlackItem();
		//移除黑名单中的选项
		offer_Tem_List = removeBlackItem(offer_Tem_List,blackMap);
		
		logger.info(" jaryzhen after blackOffer offer_tem  List size():"+offer_Tem_List.size());

		
		//对temlist格式设置cdn
		for(int i = 0 ;i < offer_Tem_List.size(); i++){
			aditem = offer_Tem_List.get(i);
			String offerid;
			String c;
			String k;
			try {
			    c=aditem.getCountry().trim();
			     k=aditem.getPkg().trim();
			} catch (Exception e) {
				// TODO: handle exception
				continue;
			}
			if(aditem.getOfferid()!=null){

				offerid = aditem.getOfferid().trim();
			}else{ 
				continue;
			}
			String p=aditem.getProvider()+"";
			String key =p+":"+k+":"+c+":"+offerid;
			offerMap.put(key, aditem);
			pkgMap.put(aditem.getPkg(), aditem.getPkg());
			pkglist.add(aditem);
			offer_list.add(key);
		}
		
		//添加手动type=3 from redis cache
		List<OverseaAd_Tem> afil_List = OverseaRedisDb.getInstance().getAll_Afil("oadtemtoredis");
		logger.info(" cache  offer  size():"+afil_List.size());
		OverseaAd_Tem afil = new OverseaAd_Tem();
		//list from cache
		List<String> cache_list = new ArrayList<String>();
		
		for(int i = 0 ;i < afil_List.size(); i++){
			afil = afil_List.get(i);
			pros_cahe +=afil.getProvider().toString()+",";
			String offerid;
			String c;
			try {
			    c=afil.getCountry().trim();
				
			} catch (Exception e) {
				// TODO: handle exception
				continue;
			}
			if(afil.getOfferid()!=null){

				offerid = afil.getOfferid().trim();
			}else{ 
				continue;
			}
			String p=afil.getProvider()+"";
			String k=afil.getPkg().trim();
			
			String key =p+":"+k+":"+c+":"+offerid;
			offerMap.put(key, afil);
			pkgMap.put(afil.getPkg(), afil.getPkg());
			pkglist.add(afil);
			offer_list.add(key);
			cache_list.add(key);
		}
		String s=pros_cahe.substring(0,pros_cahe.length()-1);
//		pros= pros+s;
		logger.info("provider from cache: "+s);
		logger.info("pkglist size:"+pkglist.size());
		logger.info("jaryzhen offer_list after  p+K+C+ID "+offer_list.size());
		
		
		//从google抓包--------------------------------------------------------------
		
		String str = "";
		for (String k : pkgMap.keySet())
		{
			str += k + ",";
		}

		Runtime run = Runtime.getRuntime();

		Process proc;
		try {
			proc = run.exec(shPath + " " + str);

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));
			String line;
			logger.info("reader is:" + reader.toString());
			while ((line = reader.readLine()) != null)
			{
				if (line.contains("ok"))
				{
					break;
				}
			}

			Map<String, Integer> files = listFiles(apkPath);
			logger.info("down apk num:" + files.size());

			Map<String,String> pkgMd5 = new HashMap<String, String>();
			List<OverseaApk> listApk = new ArrayList<OverseaApk>();
			int countk=0;
			for (OverseaAd_Tem item : pkglist)
			{
				//System.out.println("pkg is:" + item.getPkg());

				String pkg = item.getPkg();
				OverseaApk tempapk = new OverseaApk();
				
				if (files.get(pkg) != null)
				{
					String name = pkg + "-" + files.get(pkg) + ".apk";
					File file = new File(apkPath + File.separator + name);
					if (file.exists())
					{
						Random r=new Random();
						int i=r.nextInt(cdnNum);
						String apk = cdnUrlPrefixs[i] + name;
						
						tempapk.setApk(apk);
						tempapk.setSize(file.length());
						
						String md5  = pkgMd5.get(pkg);
						if(md5==null || md5.isEmpty()){
						md5= OverseaMd5.getFileMD5String(file
								.getAbsolutePath());
						pkgMd5.put(pkg, md5);
						}
						tempapk.setMd5(md5);
						tempapk.setVersioncode(files.get(pkg));
					}
					tempapk.setName(item.getName());
					tempapk.setPkg(item.getPkg());
					tempapk.setIcon(item.getMainicon());
					tempapk.setStatus(0);

					listApk.add(tempapk);
				}
				else
				{
					
					String offerid = item.getOfferid().trim();
					String c=item.getCountry().trim();
					String p=item.getProvider()+"";
					String k=item.getPkg().trim();
					String key =p+":"+k+":"+c+":"+offerid;
					offer_list.remove(key);
					countk++;
				}
				
			}
			logger.info("  offerlist.removed no pkg ="+countk);

			//distinkect
			logger.info("jaryzhen befor pkg distinckt"+listApk.size());
			Iterator<OverseaApk> it=listApk.iterator();  
			List<OverseaApk> listTemp= new ArrayList<OverseaApk>();
			while(it.hasNext()){  
				OverseaApk a=it.next();  
				if(listTemp.contains(a)){  
					it.remove();  
				}  
				else{  
					listTemp.add(a);  
				}  
			}  
			
			//listApk=null;
		//	listApk.addAll(listTemp);
			logger.info(" jaryzhen after pkg distinckt"+listTemp.size());
			logger.info("now to insert listApk to db listApksize="+ listApk.size());
			// listApk 插入或者修改到表中overseaApkDao 中得
			int p1=0,p2=0;
			for (OverseaApk item : listApk)
			{
				if (item.getApk() != null)
				{
					OverseaApk temp = pkgdao.findByPkg(item.getPkg());

					if (temp != null)
					{
						item.setId(temp.getId());
						pkgdao.update(item);
						p1++;
					}
					else
					{
						pkgdao.insert(item);
						p2++;
					}
				}
			}
			logger.info("pkgdao.update:"+p1);
			logger.info("pkgdao.insert:"+p2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info(" insert Apk done");
		
//-----------------------------------------------------------------------	
	
		//offerlist与原有数据库做对比
		boolean theoldsqlisnull = true;
		List<String> offerHas = null;
		List<String> comHas = null;
		List<String> dbHas = null;
		List<String> swich = new ArrayList<String>();

		List<String> maxList =null;
		List<String> minList = null;

		//从数据库获取对应provider的offer
		List<OverseaAd> adAll = adDao.findAffliateByProvider(pros);
		Map<String, OverseaAd> dbMapall = new HashMap<String, OverseaAd>();//map  key : pkg+country ---》 value：overseaAd

		//DB -----> list
		List<String> dbList = new ArrayList<String>();// list : pkg+country  
		logger.info(" adAll list:"+adAll.size());

		//获取manual为1状态为-2的offer
		List<OverseaAd> mulAll =null ;
		List<OverseaProvider> pro_List= null;
		Map<String, OverseaAd> mulMap = new HashMap<String, OverseaAd>();
		List<String> mulList = new ArrayList<String>();
		List<OverseaAd_Tem> offer_com2_List=new ArrayList<OverseaAd_Tem>();
		List<String> offerHas2 = null;
		List<String> comHas2 = null;
		List<String> dbHas2 = null;

		//判断数据库中是否存在数据
		if (adAll == null || adAll.size() == 0)
		{
			theoldsqlisnull = true;
		}
		else
		{
			theoldsqlisnull = false;

			for (OverseaAd itemad : adAll)
			{ 
				String offerid;
				String c;
				String k;
				try {
					c=itemad.getCountry().trim();
					 k=itemad.getPkg().trim();

				} catch (Exception e) {
					// TODO: handle exception
					continue;
				}
				if(itemad.getOfferid()!=null){

					offerid = itemad.getOfferid().trim();
				}else{ 
					continue;
				}
				String p=itemad.getProvider()+"";

				String key =p+":"+k+":"+c+":"+offerid;
				dbMapall.put(key, itemad);
				dbList.add(key);
			}
			logger.info("jaryzhen dbList after  p+K+C+ID list:"+dbList.size());
			// 初始化,
			offerHas = new ArrayList<String>();
			comHas = new ArrayList<String>();
			dbHas = new ArrayList<String>();
			maxList =offer_list;
			minList =dbList;

			//对比offer和DB 取数据
			if(dbList.size()>offer_list.size())
			{
				maxList = dbList ;
				minList = offer_list;
			}
			Map<String,Integer> mapoffer = new HashMap<String,Integer>(maxList.size());

			for (String string : maxList) {
				mapoffer.put(string, 1);
			}
			for (String string : minList) {
				//有共同的list 
				if(mapoffer.get(string)!=null)
				{
					mapoffer.put(string, 2);
					comHas.add(string);
					offer_com_List.add(offerMap.get(string));
					continue;
				}
				dbHas.add(string);
			}
			for(Map.Entry<String, Integer> entry:mapoffer.entrySet())
			{
				if(entry.getValue()==1)
				{
					offerHas.add(entry.getKey());
				}
			}
			if(dbList.size()>offer_list.size())
			{
				swich=offerHas;
				offerHas=dbHas;
				dbHas=swich;
			}
		}
		//处理offer
		if (!theoldsqlisnull)
		{

			// 仅数据库中有
			logger.info("dbhas:"+dbHas.size());
			if (dbHas != null)
			{
				for (String temp1 : dbHas)
				{
					OverseaAd tempad = dbMapall.get(temp1);
					//                    tempad.setStatus(-1);
					tempad.setStatus(-2);//改为-2 避免与手工下架的起冲突
					adDao.updateStatus_AD(tempad);//问题1
				}
			}
			// 都有的
			logger.info("comHas:"+comHas.size());
			if (comHas != null)
			{
				logger.info("update comHas:"+comHas.size());
				adDao.updateCom(offer_com_List);
			}
			
			//仅offer有
			logger.info("offerHas is :"+offerHas.size());
			//db查询manual=1和状态为-2，和offerhas进行对比：相同设置状态为0，不同直接插入
			mulAll = adDao.findManual(pros);
			//查找provide表的preweight,cap,sinstall
			pro_List =proDao.findProvider_Cap_Sin();
//			Map<Long,OverseaProvider> proMap =new HashMap<Long, OverseaProvider>();
			logger.info("manual all offer is :"+mulAll.size());
			for (OverseaAd itemad : mulAll){
				//String key =itemad.getProvider().toString().trim()+ itemad.getPkg().trim() + itemad.getCountry().trim()+itemad.getOfferid().trim();
				String offerid;
				String c;
				try {
					c=itemad.getCountry().trim();
				} catch (Exception e) {
					// TODO: handle exception
					continue;
				}
				if(itemad.getOfferid()!=null){

					offerid = itemad.getOfferid().trim();
				}else{ 
					continue;
				}
				String p=itemad.getProvider()+"";
				String k=itemad.getPkg().trim();

				String key =p+":"+k+":"+c+":"+offerid;
				mulList.add(key);
			}
			
			offerHas2 = new ArrayList<String>();
			comHas2 = new ArrayList<String>();
			dbHas2 = new ArrayList<String>();
			maxList =offerHas;
			minList =mulList;

			//对比offer和DB 取数据
			if(mulList.size()>offerHas.size()){
				maxList = mulList ;
				minList = offerHas;
			}
			
			Map<String,Integer> mapoffer2 = new HashMap<String,Integer>(maxList.size());

			for (String string : maxList) {
				mapoffer2.put(string, 1);
			}
			for (String string : minList) {
				//有共同的list 2
				if(mapoffer2.get(string)!=null)
				{
					
					mapoffer2.put(string, 2);
					comHas2.add(string);
					offer_com2_List.add(offerMap.get(string));
					continue;
				}
				dbHas2.add(string);
			}
			for(Map.Entry<String, Integer> entry:mapoffer2.entrySet()){
				if(entry.getValue()==1)
				{
					offerHas2.add(entry.getKey());
				}
			}

			if(mulList.size()>offerHas.size())
			{
				swich=offerHas2;
				offerHas2=dbHas2;
				dbHas2=swich;
			}
			//仅offer有
			logger.info("offerHas2 (in manual) is :"+offerHas2.size());
			List<OverseaAd> listKey= null;
			if (offerHas2 != null)
			{
				int cap=0,proweight=0,sinstal=0,offer2=0,offer2_real=0;

				for (String key : offerHas2)
				{
					OverseaAd_Tem tempad = offerMap.get(key);
					//以key找出status=-2 is_manual=1 判断是否有，有修改状态为0 ，没有跳过 key =p+":"+k+":"+c+":"+offerid
					String[] keys = key.split(":");
					//logger.info("key[0123]:"+keys[0]+":"+keys[1]+":"+keys[2]+":"+keys[3]);
					listKey=adDao.findByKey(keys[0],keys[1],keys[2],keys[3]);
					int listK= listKey.size();
					if(listKey.size()!=0){
						OverseaAd keybean=null;
						keybean = listKey.get(0);//可能会有2个或多个
						tempad.setId(keybean.getId());//取原有表的id ，方便只根据id更新
						adDao.update3(tempad);
						offer2++;
					}else{
//						}
						tempad.setIs_exchange(0);
						tempad.setIs_onlypush(0);
						adDao.insertTem(tempad);
						offer2_real++;
					}
				}
				logger.info("new offer but db exit="+offer2);
				logger.info("new offer insert="+(offerHas2.size()-offer2));
			}
			// offer 和 manual 都有的(pi liang)
			logger.info("comHas2 (in manual)is:"+comHas2.size());
			if (comHas2 != null)
			{
				adDao.updateStatus_Tem(offer_com2_List);
			}

		}
		else{
			logger.info("all update into database");
			// 全部插入
			for (String k : offerMap.keySet()){
				OverseaAd_Tem tempad = offerMap.get(k);
				adDao.insertTem(tempad);
			}
		}

		
		for(String cache:cache_list){
			//if offer list contains cahe list ,it means the pkg is already download ,then need to removeit from cache
			if(offer_list.contains(cache)){
				OverseaRedisDb.getInstance().delete_cache("oadtemtoredis",cache);
				logger.info("cache pkg is done:"+cache);
			} else{
				logger.info("cache pkg does done:"+cache);
			}
		}
		
		logger.info("get offer end, and providerid is " + pros);
	    logger.info("updateGpOfferToDb2  end.............");
	}
	/**
	 * 
	 * 更新offerList到数据库, 针对GP的，根据包名下载APK的
	 * 
	 * author chenbq
	 * 
	 */

	public static void updateApkInfo()
	{
		Map<String, String> pkgMap = new HashMap<String, String>();

		// 定义APK表的dao对象
		OverseaApkDao dao = (OverseaApkDao) OverseaSpringHelper
		.getBean("dapkDao");// 定义APK表的dao对象
		OverseaAdDao dadDao = (OverseaAdDao) OverseaSpringHelper
		.getBean("dadDao"); // 定义ad表的dao对象
		OverseaClassApkDao daoclassapk = (OverseaClassApkDao) OverseaSpringHelper
		.getBean("dclassapkDao");
		// APK表中获取需要更新的记录
		List<OverseaApk> apkList = dao.findAll();
		for (OverseaApk item : apkList)
		{
			pkgMap.put(item.getPkg(), item.getPkg());
		}

		// o_ad表中获取需要更新的记录
		List<OverseaAd> adList = dadDao.findByStatus();
		for (OverseaAd item : adList)
		{
			pkgMap.put(item.getPkg(), item.getPkg());
		}

		// 从googleplay重新更新apk信息并将其更新到数据库
		String str = "";
		for (String k : pkgMap.keySet())
		{
			str += k + ",";
		}

		System.out.println("now to print pkg:" + str);
		System.out.println("now to print pkg size:" + pkgMap.keySet().size());

		Runtime run = Runtime.getRuntime();

		Process proc;
		try
		{
			proc = run.exec(shPath + " " + str);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));
			String line;
			System.out.println("reader is:" + reader.toString());
			while ((line = reader.readLine()) != null)
			{
				if (line.contains("ok"))
				{
					break;
				}
			}

			Map<String, Integer> files = listFiles(apkPath);
			logger.info("apk size:" + files.size());

			// 对APK表记录的处理
			List<OverseaApk> listApk = new ArrayList<OverseaApk>();
			for (OverseaApk item : apkList)
			{
				String pkg = item.getPkg();
				OverseaApk tempapk = new OverseaApk();
				if (files.get(pkg) != null)
				{
					String name = pkg + "-" + files.get(pkg) + ".apk";
					File file = new File(apkPath + File.separator + name);
					if (file.exists())
					{
						Random r=new Random();
						int i=r.nextInt(cdnNum);
						String apk = cdnUrlPrefixs[i] + name;
						tempapk.setApk(apk);
						// item.setApk(apk);
						tempapk.setSize(file.length());
						// item.setSize(file.length());
						String md5 = OverseaMd5.getFileMD5String(file
								.getAbsolutePath());
						// item.setMd5(md5);
						tempapk.setMd5(md5);
						tempapk.setVersioncode(files.get(pkg));
					}
					tempapk.setName(item.getName());
					tempapk.setPkg(item.getPkg());
					tempapk.setIcon(item.getIcon());
					tempapk.setStatus(0);
					OverseaClassApk tempclassapk = daoclassapk.findByPkg(item
							.getPkg());
					if (tempclassapk != null)
					{
						tempapk.setCategory(tempclassapk.getCategory());
					}

					listApk.add(tempapk);
				}
			}

			System.out.println("now to insert listApk to db");

			// 将apk表中新的数据更新到数据库
			for (OverseaApk item : listApk)
			{
				if (item.getApk() != null)
				{
					OverseaApk temp = dao.findByPkg(item.getPkg());
					logger.info("Category" + item.getCategory());
					if (temp != null)
					{
						item.setId(temp.getId());

						dao.update(item);

					}
					else
					{
						dao.insert(item);
					}
				}
			}

			// 对广告表记录的处理
			List<OverseaApk> listAPKByAds = new ArrayList<OverseaApk>();
			for (OverseaAd item : adList)
			{
				String pkg = item.getPkg();
				OverseaApk tempapk = new OverseaApk();
				if (files.get(pkg) != null)
				{
					String name = pkg + "-" + files.get(pkg) + ".apk";
					File file = new File(apkPath + File.separator + name);
					if (file.exists())
					{
						Random r=new Random();  
						int i=r.nextInt(cdnNum);
						String apk = cdnUrlPrefixs[i] + name;
						tempapk.setApk(apk);
						// item.setApk(apk);
						tempapk.setSize(file.length());
						// item.setSize(file.length());
						String md5 = OverseaMd5.getFileMD5String(file
								.getAbsolutePath());
						// item.setMd5(md5);
						tempapk.setMd5(md5);
						tempapk.setVersioncode(files.get(pkg));
					}
					tempapk.setName(item.getName());
					tempapk.setPkg(item.getPkg());
					tempapk.setIcon(item.getMainicon());
					tempapk.setStatus(0);
					OverseaClassApk tempclassapk = daoclassapk.findByPkg(item
							.getPkg());
					if (tempclassapk != null)
					{
						tempapk.setCategory(tempclassapk.getCategory());
					}

					listAPKByAds.add(tempapk);
				}
			}

			System.out.println("now to insert listApkbyAds to db");

			// 广告表中获取的apk信息更新到数据库，同时广告表设置为可用
			for (OverseaApk item : listAPKByAds)
			{
				if (item.getApk() != null)
				{

					OverseaApk temp = dao.findByPkg(item.getPkg());
					logger.info("Category" + item.getCategory());
					if (temp != null)
					{
						item.setId(temp.getId());

						dao.update(item);
						// 更新广告表记录为可用
						dadDao.updateStatusByPkg(item.getPkg());

					}
					else
					{
						dao.insert(item);
						// 更新广告表记录为可用
						dadDao.updateStatusByPkg(item.getPkg());
					}

				}
			}

			OverseaCacheFactory.flushAll();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public static Map<String, Integer> listFiles(String path)
	{
		Map<String, Integer> ret = new HashMap<String, Integer>();
		File file = new File(path);
		if (file.exists())
		{
			String[] list = file.list();
			for (String str : list)
			{
				if (str.endsWith(".apk"))
				{
					String[] strs = str.split("-");
					if (strs.length >= 2)
					{
						String key = strs[0];
						strs[1] = strs[1].replace(".apk", "");
						Integer version = Integer.parseInt(strs[1]);
						Integer oldVersion = ret.get(key);
						if (oldVersion == null || oldVersion < version)
						{
							ret.put(key, version);
						}
					}
				}
			}
		}
		return ret;
	}

	/**
	 * API PostCallBack
	 */
	protected static void PostCallBack(int provider){
		//重新取出来，将tarck地址进行修改,增加回调参数
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
		String date = df.format(new Date());
		date = date+"000000";
		Long dateint = Long.parseLong(date);
		// 获取数据库中的数据
		OverseaAdDao adDao = (OverseaAdDao) OverseaSpringHelper
		.getBean("dadDao");
		List<OverseaAd> allad2 = adDao.findAffliateByProviderInsertToday(
				OverseaEnum.AD_AFFLIATE, provider,dateint);

		for (OverseaAd temp1 : allad2)
		{
			String track = temp1.getTrack();
			Date updatedate = temp1.getUpdatedate();
			Date createdate = temp1.getCreatedate();
			if(updatedate.getTime()==createdate.getTime()&&!track.contains("&")){//处理未添加过的
				//track  +="&provider="+provider+"&offerid="+temp1.getOfferid()+"&adid="+temp1.getId();
				track  +="&provider="+provider+"&offerid="+temp1.getOfferid()+"&adid="+temp1.getId();

			}
			logger.info("track="+track);
			temp1.setClick(track);
			adDao.update2(temp1);
		}

	}
	/**
	 * 从str中提取字符串
	 * @param str
	 * @return
	 */
	public static String getStrFromString(String str){
		String countryStr = "";
		// 提取数字
		Pattern pattern = Pattern.compile("\\w+");
		Matcher matcher = pattern.matcher(str);
		if(matcher.find()){
			countryStr = matcher.group();
			//        	System.out.println(countryStr);
			return countryStr;
		}else{
			throw new RuntimeException("国家列表不包含字符串!");
		}
	}

	/**
	 * 移除黑名单中的pkg项
	 * @param list
	 * @return
	 */
	public static List<OverseaAd_Tem> removeBlackItem(List<OverseaAd_Tem> list, Map<String,String> blackMap){

		OverseaAd_Tem overseaAdtem = null;
		try {
			Iterator<OverseaAd_Tem> iterator = list.iterator();
			while (iterator.hasNext()) {
				overseaAdtem =  iterator.next();
				String tempStr1 = blackMap.get(overseaAdtem.getPkg()+ ":null:null" );
				String tempStr2 = blackMap.get(overseaAdtem.getPkg() + ":" + overseaAdtem.getProvider() + ":null");
				String tempStr3 = blackMap.get(overseaAdtem.getPkg() + ":" + overseaAdtem.getProvider() + ":" + overseaAdtem.getCountry());

				if(tempStr1 != null || tempStr2 != null || tempStr3 != null){
					iterator.remove();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取黑名单中的pkg项
	 * @param list
	 * @return
	 */
	public static Map<String,String> getBlackItem(){
		String str = "";
		Map<String,String> blackMap = null;
		OverseaOfferBlackListDao blackListDao = null;
		try {
			blackListDao = (OverseaOfferBlackListDao) OverseaSpringHelper
			.getBean("dofferblacklistDao");
			List<OverseaOfferBlackList> allBlack = blackListDao.findAll();
			logger.info("jaryzhen  OverseaBaseOffer  allBlack "+allBlack.size());
			blackMap = new HashMap<String, String>();
			for (OverseaOfferBlackList black : allBlack) {
				str = black.getPkg() + ":" + black.getProvider() + ":" + black.getCountry();
				blackMap.put(str,black.getPkg()); 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("jaryzhen  OverseaBaseOffer  blackMap "+blackMap.size());
		return blackMap;
	}
	/**
	 * 获取APP黑名单中的pkg项
	 * @param list
	 * @return
	 */
	public static Map<String,String> getAppBlackItem(){
		String str = "";
		Map<String,String> blackMap = null;
		OverseaBlackAppDao blackListDao = null;
		try {
			blackListDao = (OverseaBlackAppDao) OverseaSpringHelper
			.getBean("dblackappDao");
			List<OverseaBlackApp> allBlack = blackListDao.findAll();
			
			blackMap = new HashMap<String, String>();
			for (OverseaBlackApp black : allBlack) {
				str = black.getPkg();
				blackMap.put(str,str); 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return blackMap;
	}
	/**
	 * 移除App黑名单
	 * @param list
	 * @return
	 */
	public static List<OverseaAd_Tem> removeAppBlack(List<OverseaAd_Tem> list){
		Map<String, String> appBlackItem = getAppBlackItem();
		Iterator<OverseaAd_Tem> iterator = list.iterator();
		while (iterator.hasNext()) {
			OverseaAd_Tem item = (OverseaAd_Tem) iterator.next();
			String key = item.getPkg();
			if(!OverseaStringUtil.isBlank(appBlackItem.get(key))){
				iterator.remove();
			}
		}
		return list;
	}

	public static void main(String[] args) {
		
		String key ="waf"+":"+"as"+":"+"adfa"+":"+"";
		String[] keys =key.split(":");
		if("".equals(keys[3])) System.out.println("ddz");
		System.out.println(keys[3]);
		
	} 


}
