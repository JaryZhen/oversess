package com.oversea.ads.offer.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ProtocolException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.oversea.ads.bean.OverseaAd_Tem;
import com.oversea.ads.util.OverseaStringUtil;
import com.oversea.factory.OverseaEnum;
import com.oversea.util.OverseaHttp;

public class XMLUtil {
	private final static Logger logger = Logger.getLogger(XMLUtil.class);
	/**
	 * 使用dom4j方式解析xml
	 * @param xml
	 */
	public static List<OverseaAd_Tem> readStringXml(String xml,String campaignUrl) {
	    List<OverseaAd_Tem> list = null;
        Document doc = null;
        try {

            doc = DocumentHelper.parseText(xml); // 将字符串转为XML

            Element rootElt = doc.getRootElement(); // 获取根节点

           // logger.info("根节点：" + rootElt.getName()); // 拿到根节点的名称

            Iterator offers = rootElt.elementIterator("offers"); // 获取根节点下的子节点offers
            String status = rootElt.elementTextTrim("success"); // 获取根节点下的子节点offers
            if(status.equalsIgnoreCase("true")){
            	
            	list = new ArrayList<OverseaAd_Tem>();
            	
            	// 遍历offers节点
            	while (offers.hasNext()) {
            		Element recordEless = (Element) offers.next();
            		Iterator offer = recordEless.elementIterator("offer"); // 获取子节点offer下的子节点status_id 处理为1的
            		while (offer.hasNext()) {
            			Element offer_ele = (Element) offer.next();
            			Iterator offer_status = offer_ele.elementIterator("offer_status"); // 获取子节点offer下的子节点status_id 处理为1的
            			while (offer_status.hasNext()) {
            				Element offer_status_ele = (Element) offer_status.next();
            				String status_id = offer_status_ele.elementTextTrim("status_id");
            				if(status_id!=null&&status_id.equals("1")){
            					String track_link = offer_ele.elementTextTrim("preview_link");
            					String pkg="";
            					if(!OverseaStringUtil.isBlank(track_link)){
            						//包名需要从GP地址上截取，其他地址排除，获取有误
            						if(track_link.indexOf("play.google.com") > 0 ){
            							pkg = track_link.substring(track_link.lastIndexOf("id=")+3);
                						if(pkg.indexOf("&") > 0){
                							pkg = pkg.substring(0, pkg.indexOf("&"));
                						}
                						if(!OverseaStringUtil.isBlank(pkg)){

                							OverseaAd_Tem item = new OverseaAd_Tem();
                        					String campaign_id = offer_ele.elementTextTrim("campaign_id");
                        					
                        					String trackUrl = "";
                        					if(!OverseaStringUtil.isBlank(campaign_id)){
                        						trackUrl = readTrackUrl(campaignUrl,campaign_id);
                        					}
                        					//logger.info("trackUrl is "+trackUrl);
                        					String offer_id = offer_ele.elementTextTrim("offer_id");
                        					//logger.info("offer_id is "+offer_id);
                        					String offer_name = offer_ele.elementTextTrim("offer_name");
                        					offer_name = FormatingOfferName(offer_name);
                        				//	logger.info("offer_name is "+offer_name);
                        					String price = offer_ele.elementTextTrim("payout");
                        					//logger.info("price is "+price);					
                        					StringBuffer allow_country = new StringBuffer();
                        					Element allowed_countries = offer_ele.element("allowed_countries"); 
                        					if(allowed_countries==null){
                        						continue;
                        					}
//                        					while (allowed_countries.hasNext()) {
//                    						Element allowed_countries_ele = (Element) allowed_countries.next();
                        					Iterator country_iter = allowed_countries.elementIterator("country");
                    						while (country_iter.hasNext()) {
                    							Element country = (Element) country_iter.next();
                    							allow_country.append(country.elementTextTrim("country_code")+":");
                    							
                    						}
//                        					}
                        					String country = allow_country.toString();
                        					if(OverseaStringUtil.isBlank(country.trim())){
                        						item.setCountry("ALL");
                        						item.setEcountry("CN");
                        					}else{
                        						country = allow_country.deleteCharAt(allow_country.length()-1).toString();
                        						item.setCountry(country);
                        					}
                        					float priceFloat = 0l;
    										try {
    											priceFloat = Float.parseFloat(getNumberFromString(price));
    										} catch (Exception e) {
    											priceFloat = 0l;
    										}
                        					item.setPrice(priceFloat);
                        					
                        					item.setClick(trackUrl);
                        					item.setPkg(track_link);
                        					item.setOfferid(offer_id);
                        					item.setPkg(pkg);
                        					item.setName(offer_name);
                        					item.setType(OverseaEnum.AD_AFFLIATE);
                                            item.setProvider(Long.parseLong(String
                                                    .valueOf(OverseaEnum.CLICKDEALER)));
                                            item.setStatus(0);// 状态为0的是可用的，状态w为1是不可用的
                                            item.setCap(100); // 设置默认安装量
                                            item.setSinstall(25); //
                                            item.setWeight(70);
                                            if(!"CN".equalsIgnoreCase(country)){
                                            	//logger.info("country is "+item.getCountry());
                                            	list.add(item);
                                            }
                						}
            						}
            					}
            				}
            			}
            		}
            		
            	}
            }
        } catch (DocumentException e) {
            e.printStackTrace();

        }catch (NumberFormatException e){
        	e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

        }
		return list;
    }
	/**
	 * 通过campaignID获取trackURL
	 * @param campaignUrl 需要替换的路径
	 * @param campaignId 
	 * @return trackURL
	 */
	private static String readTrackUrl(String campaignUrl, String campaignId) {
		String retString = null;
		try {
			Document doc = null;
			String request = campaignUrl.replace("{campaign_id}", campaignId);
			//logger.info("the url is:" + request);
//			//本地文件测试
//			 SAXReader reader = new SAXReader(); 
//			 //
//			 doc = reader.read(new File(subxml));//表示你要解析的xml文档 用于本地测试 	
			
			String responsexml = OverseaHttp.sendGet(request);
			doc = DocumentHelper.parseText(responsexml); // 将字符串转为XML

            Element rootElt = doc.getRootElement(); // 获取根节点

           // logger.info("Track root node：" + rootElt.getName()); // 拿到根节点的名称

            String status = rootElt.elementTextTrim("success"); // 获取根节点下的子节点offers
            if(status.equalsIgnoreCase("true")){
            	Iterator campaignIterator = rootElt.elementIterator("campaign");
            	while (campaignIterator.hasNext()) {
            		Element campaignNode = (Element) campaignIterator.next();
            		Element creatives = campaignNode.element("creatives");
            		Element creative_info = creatives.element("creative_info");
            		retString = creative_info.elementText("unique_link");
					
				}
            }
			
			
		} 
		catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retString;
	}
	/**
	 * 格式化offername
	 * @param offername
	 * @return
	 */
	private  static String FormatingOfferName(String offername){ 
		String offer_name="";
		if(!OverseaStringUtil.isBlank(offername)){
			if(offername.startsWith("$[WAP] ")){
				offername = offername.substring(7,offername.length());
			}else if(offername.startsWith("[WAP] ")){
				offername = offername.substring(6,offername.length());
			}
			if(offername.contains("-")){
				offer_name = offername.split("-")[0];
			}else{
				offer_name = offername;
			}
		}
		return offer_name;
	}
	
	public static List<OverseaAd_Tem> GlispaReadStringXml(String xml) {
		List<OverseaAd_Tem> list=null;
		Document doc = null;

	    try {
			doc = DocumentHelper.parseText(xml); // 将字符串转为XML

			Element rootElt = doc.getRootElement(); // 获取根节点

			//logger.info("根节点：" + rootElt.getName()); // 拿到根节点的名称
			
			Iterator offers = rootElt.elementIterator("campaign"); // 获取根节点下的子节点offers
			
			list = new ArrayList<OverseaAd_Tem>();
			while (offers.hasNext()) {
				Element campaignNode = (Element) offers.next();
				
				String offerid = campaignNode.attributeValue("glispaID");
				String offername = campaignNode.attributeValue("name");
				String countries = campaignNode.elementTextTrim("countries");
				countries = countries.replace(" ", ":");
				String payout = campaignNode.elementTextTrim("payout");
				payout = payout.split(" ")[0];
				Element creativesNode = campaignNode.element("creatives");
				Iterator creativeIterator = creativesNode.elementIterator("creative");
				String trackUrl = "";
				String pkg = "";
				while (creativeIterator.hasNext()) {//只取第一个的值
					OverseaAd_Tem item = new OverseaAd_Tem();
					Element creativeNode = (Element) creativeIterator.next();
					trackUrl=creativeNode.elementTextTrim("link");
					if(!OverseaStringUtil.isBlank(trackUrl)){
						String reUrl = getRedirectionUrl(trackUrl);
						if(!OverseaStringUtil.isBlank(reUrl)&&reUrl.contains("play.google.com")&&reUrl.contains("id=")){
							
							if(reUrl.contains("&"))pkg = reUrl.substring(reUrl.indexOf("id=")+3, reUrl.indexOf("&"));
							else pkg = reUrl.substring(reUrl.indexOf("id=")+3, reUrl.length());

							//logger.info("pkg="+pkg);
							//logger.info("countries"+countries);
							item.setCountry(countries);
							item.setPrice(Float.parseFloat(payout));
							item.setClick(trackUrl);
							item.setOfferid(offerid);
							item.setPkg(pkg);
							item.setName(offername);
							item.setType(OverseaEnum.AD_AFFLIATE);
		                    item.setProvider(Long.parseLong(String
		                            .valueOf(OverseaEnum.GLISPA)));
		                    item.setStatus(0);// 状态为0的是可用的，状态w为1是不可用的
		                    item.setCap(100); // 设置默认安装量
		                    item.setSinstall(25); //
		                    item.setWeight(70);
		                    list.add(item);
						}
					}
					break;
					
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	/*private static String getGlispaPkg(String trackUrl) {
		String pkg = "";
		String str = GetRedirectUrl(trackUrl);
		logger.info(str);
		return pkg;
	}
	
	private static String GetRedirectUrl(String srcurl){  
		String location = "";
        try {  
            String url = srcurl;  
            logger.info("访问地址:" + url);  
            URL serverUrl = new URL(url);  
            HttpURLConnection conn = (HttpURLConnection) serverUrl  
                    .openConnection();  
            conn.setRequestMethod("GET");  
            // 必须设置false，否则会自动redirect到Location的地址  
            conn.setInstanceFollowRedirects(false);  
  
            conn.addRequestProperty("Accept-Charset", "UTF-8;");  
            conn.addRequestProperty("User-Agent",  
                    "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");  
            conn.addRequestProperty("Referer", "http://play.google.com/");  
            conn.connect();  
            location = conn.getHeaderField("Location");  
  
            serverUrl = new URL(location);  
            conn = (HttpURLConnection) serverUrl.openConnection();  
            conn.setRequestMethod("GET");  
  
            conn.addRequestProperty("Accept-Charset", "UTF-8;");  
            conn.addRequestProperty("User-Agent",  
                    "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");  
            conn.addRequestProperty("Referer", "http://play.google.com/");  
            conn.connect();  
            logger.info("跳转地址:" + location);  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return location;
    }  */
	/**
	 * 通過原地址，得到跳轉地址
	 * @param url
	 * @return
	 */
	public static String getRedirectionUrl(String url) {
			
		     DefaultHttpClient httpClient = new DefaultHttpClient();
		     CustomRedirectHandler handler=new CustomRedirectHandler();
		     httpClient.setRedirectHandler(handler);
		     HttpGet httpget = new HttpGet(url);
		     HttpContext context = new BasicHttpContext();
		     HttpResponse response = null;
		     try {
		         response = httpClient.execute(httpget, context);
		         if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
		        	 try {
		        		 throw new IOException(response.getStatusLine().toString());
		        	 } catch (IOException e) {
		        		 e.printStackTrace();
		        		 return "";
		        	 }
		        	 HttpUriRequest currentReq = (HttpUriRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
		        	 HttpHost currentHost = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
		        	 String currentUrl = (currentReq.getURI().isAbsolute()) ? currentReq.getURI().toString(): (currentHost.toURI() + currentReq.getURI());
		        	// logger.info("currentUrl:"+currentUrl);
		        	 return currentUrl;
		     } catch (ClientProtocolException e1) {
		         return "";
		     } catch (IOException e1) {
		         return "";
		     }
	}
	/**
	 * 从字符串中提取规范价格
	 * @param str
	 * @return
	 */
	private static String getNumberFromString(String str){
		String numberStr = "";
		// 提取数字
        Pattern pattern = Pattern.compile("[0-9]+(.[0-9]{1,})?");
        Matcher matcher = pattern.matcher(str);
        if(matcher.find()){
        	numberStr = matcher.group();
        	System.out.println(numberStr);
        	return numberStr;
        }else{
        	throw new RuntimeException("价格不包含数字!");
        }
	}
	
	private static String xml = "D://OfferFeed.xml";
	private static String subxml = "D://GetCampaign.xml";
	private static String offerUrl = "https://login.clickdealer.com/affiliates/api/4/offers.asmx/OfferFeed?api_key=oJ4ZxRvN1aDgiajccYZA&affiliate_id=23235&campaign_name=&media_type_category_id=0&vertical_category_id=9&vertical_id=114&offer_status_id=0&tag_id=0&start_at_row=1&row_limit=0";
	private static String tracklink = "https://login.clickdealer.com/affiliates/api/2/offers.asmx/GetCampaign?api_key=oJ4ZxRvN1aDgiajccYZA&affiliate_id=23235&campaign_id={campaign_id}";
	
	public static void main(String[] args) {
		readStringXml(offerUrl,tracklink);
//		getNumberFromString("$0.12 %");
	}
}

class CustomRedirectHandler extends DefaultRedirectHandler {
	     @Override
	     public URI getLocationURI(HttpResponse response, HttpContext context) throws ProtocolException{
	        if(isRedirectRequested( response, context))
	         {
	             Header locationHeader = response.getFirstHeader("location");
	             String location= locationHeader.getValue();
	             if(location!=null&&!"".equals(location)&&!location.startsWith("http")&&location.contains("---")){
	                response.removeHeaders("location");
                 response.setHeader("location","-----"+location);
	                 URI uri=null;
	                 try {
	                     uri =  new URI("------"+location.substring(0, location.lastIndexOf("url=") + 4)
	                             + URLEncoder.encode(location.substring(location.indexOf("url=") + 4, location.length())));
	                 } catch (URISyntaxException e) {
	                     e.printStackTrace();
	                 }
	                 return uri;
	              }
	         }
	        return super.getLocationURI(response,context);
	         
	     }
 }