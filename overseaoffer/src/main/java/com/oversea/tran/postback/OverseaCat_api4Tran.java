package com.oversea.tran.postback;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.oversea.ads.bean.RequestBean;
import com.oversea.ads.bean.ResponseBean;
import com.oversea.ads.bean.TransmissionUtil;
import com.oversea.tran.OverseaBaseTrans;

public class OverseaCat_api4Tran extends OverseaBaseTrans
{
    public static final Logger log = Logger.getLogger(OverseaCat_api4Tran.class);

    /**
     *验证客戶端请求的数据信息，并对请求的数据进行封装
     * 
     * @throws IOException
     * */
    public RequestBean getReqBean(final HttpServletRequest request)
            throws Exception
    {
        String str = ReadGetData(request);
        //log.info("BEFORE:" + str);
        str = URLDecoder.decode(str, "utf-8");
      //  log.info("AFTER:" + str);

//        json = JSON.parseObject(jstr);
        RequestBean bean = TransmissionUtil.stringToBean(str, "&");

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

    public void validator(RequestBean bean) throws UnsupportedEncodingException
    {
        bean.setCheck(true);
    }
}
