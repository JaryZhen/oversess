package com.oversea.ads.statis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.oversea.ads.bean.OverseaCountry;
import com.oversea.ads.dao.OverseaCountryDao;
import com.oversea.util.OverseaSpringHelper;

public class OverseaPostBackStatisJob
{
	private static final Logger logger = Logger.getLogger(OverseaPostBackStatisJob.class);
    public void work()
    {
        try
        {
        	//统计前一天postBack安装量分日期、provider、adid
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
	  * 读取文件
	  * 
	  * @param file
	  * @return
	  * @throws Exception
	  */
	 public static void readFile(File file) throws Exception {
		 OverseaCountryDao countrydao = (OverseaCountryDao) OverseaSpringHelper
	     .getBean("dcountryDao");
	  BufferedReader br = new BufferedReader(new FileReader(file));
	  String line = null;
	  String str[] = null;
	  List<OverseaCountry> list = new ArrayList<OverseaCountry>();
	  while ((line = br.readLine()) != null) {
		  if(line.contains("\t")){
			  str = line.split("\t");
			  if(str.length > 0){
				  OverseaCountry item = new OverseaCountry();
				  item.setCountry(str[0]);
				  item.setCountry3ch(str[1]);
				  list.add(item);
//				  countrydao.updateByCountry(item);
//				  System.out.println(str[0]);
//				  System.out.println(str[1]);
//				  System.out.println(str[2]);
			  }
		  }
	  }
	  countrydao.updateByCountry(list);
	  br.close();
	  
	 }
	
	public void test(){
		System.out.println("goto");
		File file =  new File("D:\\country.txt");
		try {
			readFile(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("done");
	}
    
  
    
    public static void main(String[] args)
    {
//        InsertPostBackDBFromRedis();
    	System.out.println("adspostback_;20150524;58615".split(";").length);
    }
}
