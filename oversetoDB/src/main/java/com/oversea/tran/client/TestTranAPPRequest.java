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

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import com.alibaba.fastjson.JSONArray;

public class TestTranAPPRequest
{
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
	 static class miTM implements javax.net.ssl.TrustManager,  
	 javax.net.ssl.X509TrustManager {  
		 public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
			 return null;  
		 }  

		 public boolean isServerTrusted(  
				 java.security.cert.X509Certificate[] certs) {  
			 return true;  
		 }  

		 public boolean isClientTrusted(  
				 java.security.cert.X509Certificate[] certs) {  
			 return true;  
		 }  

		 public void checkServerTrusted(  
				 java.security.cert.X509Certificate[] certs, String authType)  
		 throws java.security.cert.CertificateException {  
			 return;  
		 }  

		 public void checkClientTrusted(  
				 java.security.cert.X509Certificate[] certs, String authType)  
		 throws java.security.cert.CertificateException {  
			 return;  
		 }  
} 
	private static void trustAllHttpsCertificates() throws Exception {  
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];  
        javax.net.ssl.TrustManager tm = new miTM();  
        trustAllCerts[0] = tm;  
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext  
                .getInstance("TLS");  
        sc.init(null, trustAllCerts, null);  
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc  
                .getSocketFactory());  
    }  
    public static String sendGet(String url, String param)
    {
        String result = "";
        BufferedReader in = null;
        try
        {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            
            HostnameVerifier hv = new HostnameVerifier() {  
				public boolean verify(String urlHostName, SSLSession session) {
					// TODO Auto-generated method stub
					return true;
				}  
            };
            trustAllHttpsCertificates(); 
            HttpsURLConnection.setDefaultHostnameVerifier(hv); 
            
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
           
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // // 遍历所有的响应头字段
            for (String key : map.keySet())
            {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection
                    .getInputStream()));
            String line;
            while ((line = in.readLine()) != null)
            {
                result += line;
            }
        }
        catch (Exception e)
        {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }
            }
            catch (Exception e2)
            {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param)
    {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try
        {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null)
            {
                result += line;
            }
        }
        catch (Exception e)
        {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                }
                if (in != null)
                {
                    in.close();
                }
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        return result;
    }

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
        m.put("country", "IN");
        m.put("imei", "A0000042F0B1EE");
        m.put("mac", "22:3a:da:13:ds:dd");
        m.put("pkg", "com.android.service.utc0");
        m.put("channelname", "share_default");
        m.put("issuperuser", true);
        m.put("dInstallHowlong", "10000");
        m.put("lastInstallAdHowlong", "56");
        m.put("versioncode", "259");
        m.put("android_id", "10019b0a12180a41");
        m.put("imsi", "510030161077801");
        m.put("version_code", 121);
//        final String url = "http://localhost:8080/overseaads/api/103";
        final String url = "http://192.168.1.172:8080/overseaads/api/101";

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
    
    public static void sendEvent()
    {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("wifi", 1);
        m.put("sv", "1.3");
        m.put("lib", "1.3.3663");
        m.put("app_id", "");
        m.put("imei", "99000554304378");
        m.put("imsi", "460030981449147");
        m.put("channel", "mytest");
        m.put("app_name", "com.mmymy.xiao");
        m.put("app_version", "3");
        m.put("client_id", "46003098144914799000554304378MI 4C0c:1d:af:d3:eb:b2");
        m.put("android_id", "8b9bd9cb442384a7");
        m.put("event", "AccessConnect");
        m.put("num", "1");
        m.put("data", "");
        m.put("num", "18");
//        final String url = "http://api.AEDXDRCB.COM/overseaads/api/602";
        final String url = "http://localhost:8080/overseaads/api/602";

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

    public static void main(String[] args)
    {
        // 发送 GET 请求
////         String url = "http://52.74.57.9:8081/gkview/postback/1020";
        String url = "https://api.startappservice.com/1.0/management/bulk/campaigns";
//        // //参数列表为对应通道实例所需要的参数
         String arguments ="partner=107296749&token=59f9c123a7ca89ee866db6c85eeb3d43&segId=207566526&os=0&countries=US&bidType=CPI";
//        // "status=Failed&shortcode=33445&currency=INR&price_wo_vat=5.0&operator=Celcom&message=MY1427760019723899-1646-0&price=999&message_id=be800788a7277f1058e96866f2c4f7e9&keyword=PAY5%20GKT&sig=1894671eba15a92b08cd1577b92d0a71&country=IN&sender=60195837343&billing_type=MT&service_id=4720961b9ef29b6452d29a0cf1d65786";
         String s=TestTranAPPRequest.sendGet(url, arguments);
//         System.out.println(s);
//
//        // //发送 POST 请求
//        // String sr=TestTranAPPRequest.sendPost(url,
//        // "country=ID&imsi=46000000&pkg=com.ngemobi.downloader&channelname=mytest1&dInstallHowlong=10000&lastInstallAdHowlong=100");
//        // System.out.println(sr);
////        sendToCp();
////    	File file =  new File("D:\\country.txt");
////		try {
////			readFile(file);
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//    	sendEvent();
//    	
//    	String statiskey2 = NgsteamEnum.ADSINSTALLKEY + "20150710";
//		Map<Long, Integer> installMap = NgsteamRedisUtil.getAll(statiskey2);
//		System.out.println(installMap.get(34153));
    //	sendToCp();
    }
}


