package com.oversea.tran.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.sun.org.apache.bcel.internal.generic.LNEG;

public class TestTranApi
{
 
    /**
     * 发送HttpPost请求
     * 
     * @param strURL
     *            服务地址
     * @param params
     *            json字符串,例如: "{ \"id\":\"12345\" }" ;其中属性名必须带双引号<br/>
     * @return 成功:返回json字符串<br/>
     */
    public static String sendPostByJson(String strURL, String params)
    {
        System.out.println(strURL);
        System.out.println(params);
        try
        {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection
                    .getOutputStream(), "UTF-8"); // utf-8编码
            out.append(params);
            out.flush();
            out.close();
            // 读取响应
            int length = (int) connection.getContentLength();// 获取长度
            InputStream is = connection.getInputStream();
            if (length != -1)
            {
                byte[] data = new byte[length];
                byte[] temp = new byte[512];
                int readLen = 0;
                int destPos = 0;
                while ((readLen = is.read(temp)) > 0)
                {
                    System.arraycopy(temp, 0, data, destPos, readLen);
                    destPos += readLen;
                }
                String result = new String(data, "UTF-8"); // utf-8编码
                System.out.println(result);
                return result;
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "error"; // 自定义错误信息
    }

    public static void sendToCp()
    {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("country", "FR");
        m.put("imsi", "20815000");
        m.put("mac", "22:3a:da:13:ds:dd");
        m.put("pkg", "com.ngemobi.downloader");
        m.put("channelname", "mytest1");
        m.put("issuperuser", true);
        m.put("dInstallHowlong", "10000");
        m.put("lastInstallAdHowlong", "460010690603089");
        m.put("versioncode", "118");
        final String url = "http://localhost:8080/overseaads/api/101";

        final String jsondata = JSONArray.toJSONString(m);
        String rsp = sendPostByJson(url, jsondata);
        // for (int i = 0; i < 20; i++) {
        // new Thread(new Runnable() {
        // public void run() {
        // }
        // }).start();
        // }
        System.out.println("the responese code is " + rsp);

    }

    public static String getBASE64(String s)
    {
        if (s == null)
            return null;
        return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
    }
    
    /**
	  * 读取文件
	  * 
	  * @param file
	  * @return
	  * @throws Exception
	  */
	 public static void readFile(File file) throws Exception {
	  BufferedReader br = new BufferedReader(new FileReader(file));
	  String line = null;
	  String str[] = null;
	  while ((line = br.readLine()) != null) {
		  if(line.contains("\t")){
			  str = line.split("\t");
			  if(str.length > 0){
				  System.out.println(str[0]);
				  System.out.println(str[1]);
				  System.out.println(str[2]);
			  }
		  }
	  }
	  br.close();
	 }
    public static String  readJsonFile(String s) throws Exception {
    	
    	File file =  new File(s);
    	BufferedReader br = new BufferedReader(new FileReader(file));
    	String line = "";
    	String str ="";
    	while ((line = br.readLine()) != null) {
    		str+=line;
    	}
    	br.close();
    	
    	return str;

    }

    public static void main(String[] args)
    {
    	String url = "http://localhost:8080/overseaads/api/101";
    	
    	String s="json.txt";
    	
    	try {
    		sendPostByJson(url, readJsonFile(s));
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
    }
}

