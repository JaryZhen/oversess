package com.oversea.tran;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oversea.ads.bean.RequestBean;
import com.oversea.ads.bean.ResponseBean;
import com.oversea.ads.bean.TransmissionUtil;
import com.oversea.ads.util.base64;
public class OverseaBaseTrans
{
    public static final Logger log = Logger.getLogger(OverseaBaseTrans.class);

    /**
     *验证客戶端请求的数据信息，并对请求的数据进行封装
     * 
     * @throws IOException
     * */
    public RequestBean getReqBean(final HttpServletRequest request)
            throws Exception
    {
        JSONObject json = null;
        String jstr = ReadPostData(request);
//         log.info("BEFORE:" + jstr);
        // System.out.println("BEFORE:" + jstr);
        // jstr = NgsteamRsa.decryptByPrivate(jstr);
        String jstr2 =null;
        try {
        	jstr2 = URLDecoder.decode(jstr, "utf-8");
            // log.info("IN:" + jstr);
            // System.out.println("IN:" + jstr);

            json = JSON.parseObject(jstr2);
        	
        } catch(Exception e) {
        	try {
        		jstr2 = jstr+"}";
        		
            	jstr2 = URLDecoder.decode(jstr2, "utf-8");
                // log.info("IN:" + jstr);
                // System.out.println("IN:" + jstr);
                json = JSON.parseObject(jstr2);
            	
        	} catch (Exception e2) {
                jstr2 = new String(
                        base64.decode(jstr.getBytes("utf-8"), base64.DEFAULT), "utf-8");
                jstr2 =jstr2+"}";
                jstr = URLDecoder.decode(jstr2, "utf-8");
                json = JSON.parseObject(jstr);
        	}
        }
        RequestBean bean = TransmissionUtil.jsonToBean(json);
        validator(bean);// 对数据进行校验
        return bean;
    }

    /**
     *将操作结果返回给
     * 
     * @throws Exception
     * */
    public String outResBean(HttpServletResponse response,
            final ResponseBean bean) throws Exception
    {
        OutputStream out = null;
        String json = null;
        try
        {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            out = response.getOutputStream();
            json = TransmissionUtil.responseToJson(bean);
            // log.info("OUT:" + json);
            json = URLEncoder.encode(json, "utf-8");
            // json = NgsteamRsa.encryptByPrivate(json);
            out.write(json.getBytes("UTF-8"));
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (out != null)
            {
                out.close();
            }
        }
        return json;
    }

    public static String ReadPostData(final HttpServletRequest request)
            throws IOException
    {
        if (request == null)
        {
            return null;
        }
        InputStream in = null;
        try
        {
            InputStream bis = null;
            in = request.getInputStream();
            String encoding = request.getHeader("Content-Encoding");
            in = request.getInputStream();
            if (encoding != null)
            {
                if (encoding.toLowerCase().contains("gzip"))
                {
                    bis = new GZIPInputStream(in);
                }
            }
            else
            {
                bis = new BufferedInputStream(in);
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // byte[] data = new byte[request.getContentLength()];
            String data;
            byte[] buf = new byte[2048];
            int len = 0;
            int index = 0;
            while ((len = bis.read(buf)) != -1)
            {
                baos.write(buf, 0, len);
                // System.arraycopy(buf, 0, data, index, len);
                index += len;
            }
            data = new String(baos.toByteArray());
            baos.flush();
            baos.close();
            return data;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (in != null)
                in.close(); // 关闭输入流
        }
    }

    public static String ReadGetData(final HttpServletRequest request)
    {
        return request.getQueryString();
    }

    public void validator(RequestBean bean) throws UnsupportedEncodingException
    {
        bean.setCheck(true);
    }
}
