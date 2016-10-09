package com.oversea.ads.statis;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


import com.oversea.ads.bean.OverseaAd;
import com.oversea.ads.bean.OverseaAd_Tem;
import com.oversea.ads.dao.OverseaAdDao;
import com.oversea.ads.dao.OverseaAd_TemDao;
import com.oversea.ads.offfertrack.OverseOffersTrack;
import com.oversea.ads.offfertrack.OverseaBaseOffer;
import com.oversea.factory.OverseaCacheFactory;
import com.oversea.util.OverseaSpringHelper;

public class OverseaStatisJob 
{

    private static final Logger logger    = Logger
                                                  .getLogger(OverseaStatisJob.class);
    // 非常重要campaigns
    private static final String serverUrl = "http://d.altascdn.com/apk/";             // 新服务器更新地址

	static OverseaAdDao adDao = (OverseaAdDao) OverseaSpringHelper.getBean("dadDao");
    public void updat_Db_Track()
    {
    	//   ..............................................................................
    	OverseaAd_TemDao temDao = (OverseaAd_TemDao) OverseaSpringHelper.getBean("dtemDao");
    	
		List<OverseaAd_Tem> proList = new ArrayList<OverseaAd_Tem>();
    	
    	//获取黑名单
    	Map<String,String> blackMap = OverseaBaseOffer.getBlackItem();
    	StringBuffer prossBuffer=new StringBuffer();
    	String pros=null;
    	//use for callback
    	
    	proList = temDao.findProviders();
    	int[] prosInt = new int[proList.size()];
    	
    	for (int i = 0; i < proList.size(); i++) {
    		//	StringBuffer pros= new StringBuffer();
    		if(proList.get(i)!=null){
    			Long prolong= proList.get(i).getProvider();
    			String prov = prolong.toString();
    			prosInt[i]= Integer.parseInt(prov);
    			prossBuffer.append(prov+",");
    			pros= prossBuffer.toString();
    		}
    	}
    	pros=pros.substring(0, pros.length()-1);

		//pros="48";
		logger.info("providers....:"+pros);
    	logger.info("update OfferToDb2 start");
    	OverseaBaseOffer.updateGpOfferToDb2(blackMap,pros);
    	
    	//updata track 
    	OverseOffersTrack.offersTrack(prosInt);
    	logger.info("");
    	logger.info("..jaryzhen..update All Track end...........................................");
    	logger.info("");
    	
    	loadingToRedis();
    }

    private static void loadingToRedis(){
    	
    	try
    	{
    		//将数据库数据更新到redis，同时清除缓存数据
    		List<OverseaAd> list = adDao.updateRedisFromDb();
    		logger.info("redis list size:"+list.size());
    		
    		if(list.size() > 0){
    			OverseaCacheFactory.flushAll();
    		}
    		logger.info("...........memecahe flushAll end ......................");
    	}
    	catch (Exception e)
    	
    	{
    		e.printStackTrace();
    	}
    }
    
    /**
     * 从服务器下载文件
     */
    public static String downloadFile(String url, String filePath,
            String fileFullName) throws IOException
    {
        URL theURL = new URL(url);
        URLConnection con = theURL.openConnection();

        if (fileFullName != null)
        {
            byte[] buffer = new byte[4 * 1024];
            int read;
            String path = filePath + "/" + fileFullName;
            File fileFolder = new File(filePath);
            if (!fileFolder.exists())
            {
                fileFolder.mkdirs();
            }
            InputStream in = con.getInputStream();
            FileOutputStream os = new FileOutputStream(path);
            while ((read = in.read(buffer)) > 0)
            {
                os.write(buffer, 0, read);
            }
            os.close();
            in.close();
        }
        return fileFullName;
    }

    public static void downloadFile(String path, String url) throws IOException
    {
        logger.info("apkPath:" + path);
        logger.info("url:" + url);
        HttpClient client = null;
        try
        {
            // 创建HttpClient对象
            client = new DefaultHttpClient();
            // 获得HttpGet对象
            HttpGet httpGet = getHttpGet(url, null, null);
            // 发送请求获得返回结果
            HttpResponse response = client.execute(httpGet);
            // 如果成功
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                byte[] result = EntityUtils.toByteArray(response.getEntity());
                BufferedOutputStream bw = null;
                try
                {
                    // 创建文件对象
                    File f = new File(path);
                    // 创建文件路径
                    if (!f.getParentFile().exists())
                        f.getParentFile().mkdirs();
                    // 写入文件
                    bw = new BufferedOutputStream(new FileOutputStream(path));
                    bw.write(result);
                }
                catch (Exception e)
                {
                    logger.info("保存文件错误,apkPath=" + path + ",url=" + url, e);
                }
                finally
                {
                    try
                    {
                        if (bw != null)
                            bw.close();
                    }
                    catch (Exception e)
                    {
                        logger.info(
                                "finally BufferedOutputStream shutdown close",
                                e);
                    }
                }
            }
            // 如果失败
            else
            {
                StringBuffer errorMsg = new StringBuffer();
                errorMsg.append("httpStatus:");
                errorMsg.append(response.getStatusLine().getStatusCode());
                errorMsg.append(response.getStatusLine().getReasonPhrase());
                errorMsg.append(", Header: ");
                Header[] headers = response.getAllHeaders();
                for (Header header : headers)
                {
                    errorMsg.append(header.getName());
                    errorMsg.append(":");
                    errorMsg.append(header.getValue());
                }
                logger.info("HttpResonse Error:" + errorMsg);
            }
        }
        catch (ClientProtocolException e)
        {
            logger.info("下载文件保存到本地,http连接异常,apkPath=" + path + ",url=" + url, e);
            throw e;
        }
        catch (IOException e)
        {
            logger.info("下载文件保存到本地,文件操作异常,apkPath=" + path + ",url=" + url, e);
            throw e;
        }
        finally
        {
            try
            {
                client.getConnectionManager().shutdown();
            }
            catch (Exception e)
            {
                logger.error("finally HttpClient shutdown error", e);
            }
        }
    }

    /**
     * 获得HttpGet对象
     * 
     * @param url
     *            请求地址
     * @param params
     *            请求参数
     * @param encode
     *            编码方式
     * @return HttpGet对象
     */
    private static HttpGet getHttpGet(String url, Map<String, String> params,
            String encode)
    {
        StringBuffer buf = new StringBuffer(url);
        if (params != null)
        {
            // 地址增加?或者&
            String flag = (url.indexOf('?') == -1) ? "?" : "&";
            // 添加参数
            for (String name : params.keySet())
            {
                buf.append(flag);
                buf.append(name);
                buf.append("=");
                try
                {
                    String param = params.get(name);
                    if (param == null)
                    {
                        param = "";
                    }
                    buf.append(URLEncoder.encode(param, encode));
                }
                catch (UnsupportedEncodingException e)
                {
                    logger.info("URLEncoder Error,encode=" + encode + ",param="
                            + params.get(name), e);
                }
                flag = "&";
            }
        }
        HttpGet httpGet = new HttpGet(buf.toString());
        return httpGet;
    }
    
    public static void clearCache(){
    	
    	OverseaAdDao adDao = (OverseaAdDao) OverseaSpringHelper.getBean("dadDao");
    	try{
    		
    		//将数据库数据更新到redis，同时清除缓存数据
    		List<OverseaAd> list = adDao.updateRedisFromDb();
    		logger.info("Total offer size:"+list.size());
    		
    		if(list.size() > 0){
    			OverseaCacheFactory.flushAll();
    		}
    		logger.info("Clear cache done.");
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }

    public static void downloadNet(String fullname, String neturl)
            throws MalformedURLException
    {
        logger.info("neturl:" + neturl);
        // 下载网络文件
        int bytesum = 0;
        int byteread = 0;

        URL url = new URL(neturl);

        try
        {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream(fullname);

            byte[] buffer = new byte[1204];
            while ((byteread = inStream.read(buffer)) != -1)
            {
                bytesum += byteread;
                fs.write(buffer, 0, byteread);
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

 

    public static void main(String[] args)
    {
        // updateAvazuOffer();
        // updateAffliateOffer();
        // updateApkInfo();
        // updateAvazuOffer();
      

        // updateTapjoyOffer();
        // try {
        // downloadFile("D:\\data\\tools\\nginx\\nginx\\html\\tapjoy\\test.apk",
        // "https://ws.tapjoyads.com/click/app?data=270aaedaec2b9f641c8a8aaba9decaf7ad22a599d0ff08998ec8a8a341b66d62ae01ffd5328433599bfdf2d72ba7da69aa03f9f0585a896c156e2b79ca3d1b427dd6a4874c7a4fd98b4f5e0c60b5c3c48b0ade66970db62593ef53a98676df313121cd21f05370b7fc7a699c7e1b2f9a1abbd16705a22819ff931ee5c93007899c2ccfe8316cf93783b2d40b1da7da3f2c311b37888d3b4a7f1eb21597366ec917ad36526bcac8b3189c5524cdd4e9996844498d9b45ef3fbeaed86bbeb43a70421daf04b29a54bea4a144a798c55dc27ff3d9644ead135dc1bc6c1e0ceb2102f61b5d1aebd9c36cebad06dbd86f2dbff69299e785f408aa8c01b172687887f3f9c714bf479d3e5f867ebb8185a5d9d1057c2ec3f126eb53074df9b1c745dabc21fae68ad24383351102ed010316e89f4b320eb31d91387e816df864045897baaabc0275c4ad857b75fb0a968e007c314ae9f1e14a161d745a123b4b47cd9122b7fc855c66576922b1d5f24a1334b3b84a206f564c972a48e794fa61eb38abb4d72cec32206cd23dc6b14540966918a48eb78199bf3e6641b2c1a93a85cb94c5f98687c02bdc7c1807fa652f080149121cedfe46c63c1e1f4d5aef9e98e43b5e08c71750070c62450b57e2e18bd6ab4d94f77be142991fbd511cd592bf335a6cb658fb331763fd2c445c2c7a9eca4769c3018e6dc75297090ffe3e5fc672de30ef868657379a8a8e356312e42b165cb7a9c1a1ffd8609d732ba8b1b47f4fd1ad97fd5191af9fb1976b3c15408e607594574f0867192fda01619412f06f75cbcb26a7638c7b1f8f1f08c57d4f15c8fe919a78859c6615084ecc0098c183ce649ecb99e8d6528f2a895c98b18efcbd224b7ca7f535bceb41a87c956036dc22ca22ff45adaa3bae6dfc7afa0555d9b425d732cd73351516295fb18ff747d8606c9cb8ffeb3c1c8ac07cd3e65bfecbe873c755b216d284a07c8ba336d3effb5a8e46a1ed90ae5d430c95f39ddd911272e6ab7659c70aad4471c9130c72a1fefec8155f1a276e390e4c96193f76cc74841340216c1e6cd04116c48921c877521b69f2fd18193ac6b0ce36ce66babc9f10eee1e2763d2da6cb84ec26e11f12f75936765c328782cb71f67695641b8b0d44aea534b746cfd8310cf4fa12a68f3ae0d4ba62c51ff6ff382dbe76111da666c65cdc328ac2b4e91068dfdb2e3a34983653112dc46b12fe15291225349a9cc38018c28a13859e6718a3dab7179723878e6f72607969e44c16acf71e318d72e82be982f905283728275f207dcb107c5272d1217b44810354ecc6472391250b065e6c5b829d0b4e61c3e4199795eb5bf6bfe77098302d6dc4a3cebd8d2611b91650052e9d16f5b185ab325a297a0e181fc537000636e6bb4d6b322543946e0e787422e241f90ff47700e80c91b8ffeabc1bbc3b854c037e514278db3a64d70c42efb19410926e5da97955ce357106c53f1693ac9dc83eae07ec52098ce8a9c60dc74d6b1b416f4e989058d0135aa3756fa06627f3f39ddc3d3b3f40b227aa1b9c828990b28003163646bd8471aa1dd200afcd5703c7811a8910845e673bf46f437f540d1a9a78315d00ea1136db020b52f61a362e262528ba1ef2764a9f2676ced4e39ab9d01096c81c40be13d74e36a93e31a4419f920cb15a37430bd2a5957b862760a5e3352fad11eaf307d2cb932da5c872a71a3b5c2993fc158ff92e46f904b5a62949f37dac93021d5cc043128172579f022bcf8a481153a156e8c486bd23a772165bd181e9c397d49df8b37522a06b425f594b8cc4ae699d808858d9bea51c3d82b241c6f7bd4ab243b587c3ad3ca42768d40ae3d3b038c77f048c0a1910fb717945a442c9a0f4676c018a68f44557276c12e813d2ed868e84e25ad76ddb8b9f035d50be9b23bfc8d58da311620e2dadaecae38c6dd83587f501e9e779e3fc70d12281abe8a06bc3bae5317966d02b60dd2b1082e5ce8b2a7ce289bcae310147ead80fdee4ba36832ce4ea5186500b2a3f6f986f4321b4177cf75fb6f5690abef2eb395469ce9b7674a99b2258ee88565e0dcfea00f70d6e6cdc3d37dac20082a6fdb049530b3f87968181f79554ab43b62b464a9eade2a472013a124415eef1e2e6db3128120502e8ae58dcd6d30195f4182748736d9084708ac42bae2907fa3ba3c74df2b3fc39b360e528fb32c0e5317f83fad0f0c827b1ea385245e86b3204a90b868ab7453bc0d38a7d955116f96df266b9502c63cd3795e4858debf0477d4cf1701f39dc39e7517d90d1947f169c98bbcbf20c74846160a7a221b09d48787389fad35c1c61426e19b551fd689ca03ff723e223bc6de4571705891f17ea65f25edf72712bf34e23d24d9ba8c7d65f40665886f22eef8af4bdca788ba218fe77b82e4cb797c0f0c5b953ef1cf10357da70b69f9b9458842d972973ac5239861ee56707b6226938d5f51f3cc4b12ae04abfc88ccab5f06f5c9b93d01f731f292c3f919645104acfd6eec7c849e7ef330c0a98574909efe8fac544864978538767d41145e8e4b65b1c9e2534793c4970493e46a0046e693ece3b90a63b272bb7f0f19b6b66a15b51abd951c00ffc29591f92326e1de76e8b2aa44d7fe7b1bf0713f5973d1cf2fd0369cbb54733b1c1f324fe609cbc11bbe0cb353ea8d5e9a6deef3b1ac0f1f58a1b410b386a50a70d3b2ed86afad345c9304a086579c9a13b");
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
    }
}
