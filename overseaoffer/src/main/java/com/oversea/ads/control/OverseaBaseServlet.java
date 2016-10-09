package com.oversea.ads.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oversea.ads.bean.RequestBean;
import com.oversea.ads.bean.ResponseBean;
import com.oversea.ads.util.OverseaStringUtil;
import com.oversea.factory.OverseaResultCodeFactory;
import com.oversea.factory.OverseaSrcFactory;
import com.oversea.factory.OverseaTransFactory;
import com.oversea.log.OverseaLog;

public abstract class OverseaBaseServlet extends HttpServlet
{

    /**
     * 
     */
    private static final long serialVersionUID = 5828401831894498712L;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
    {
        RequestBean bean = null;
        ResponseBean responsebean = null;
        try
        {
            request.setCharacterEncoding("utf-8");
            bean = OverseaTransFactory.getReqBean(request);
        }
        catch (Exception e)
        {
            OverseaLog.log(e);
            responsebean = new ResponseBean(
                    OverseaResultCodeFactory.UNKOWN_ERROR);
        }
        String errorcode = bean.getValue("response_error_code");
        if (!OverseaStringUtil.isBlank(errorcode))
        {
            responsebean = new ResponseBean(
                    OverseaResultCodeFactory.UNKOWN_ERROR);
        }
        else
        {
            try
            {
                responsebean = process(bean);
            }
            catch (Exception e)
            {
                OverseaLog.log(e);
                responsebean = new ResponseBean(
                        OverseaResultCodeFactory.REQUEST_INTERFACE_ERROR);
            }
        }
        responsebean.setSrc(OverseaSrcFactory.getSrc(request));
        String result = "";
        try
        {
            result = OverseaTransFactory.outResBean(response, responsebean);// 输出处理结果
        }
        catch (Exception e)
        {
            OverseaLog.log(e);
        }
        // long totaltime = System.currentTimeMillis() - start;
        // NgsteamLog.log(bean, responsebean, result, totaltime);// 记录访问日志
        // watch.stop();
    }

    public abstract ResponseBean process(RequestBean request);
}
