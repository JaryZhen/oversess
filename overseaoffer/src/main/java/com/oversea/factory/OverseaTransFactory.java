package com.oversea.factory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oversea.ads.bean.RequestBean;
import com.oversea.ads.bean.ResponseBean;

import com.oversea.tran.OverseaBaseTrans;

public class OverseaTransFactory
{

	public static RequestBean getReqBean(final HttpServletRequest request)
	throws Exception
	{
		String src = OverseaSrcFactory.getSrc(request);
		OverseaBaseTrans baseTrans = OverseaEnum.getTrans(src);
		// NgsteamBaseTrans baseTrans = new NgsteamBaseTrans();
		RequestBean bean = null;
		try
		{
			if (baseTrans != null)
			{
				bean = baseTrans.getReqBean(request);
			}
			else
			{
				bean = new RequestBean(false);
			}
			if (bean != null)
			{
				bean.setReqType(src);
				bean.setSrc(src);
				bean.setIp(getIpAddr(request));
			}
		}
		catch (Exception e)
		{
			throw e;
		}
		return bean;
	}
	
	/**
	 * 将处理结果返回给请求方
	 * 
	 * @author
	 * @throws Exception
	 * */
	public static String outResBean(final HttpServletResponse response,
			ResponseBean bean) throws Exception
			{
		OverseaBaseTrans baseTrans = OverseaEnum.getTrans(bean.getSrc());
		if (baseTrans != null)
		{
			return baseTrans.outResBean(response, bean);
		}
		else
		{
			return "";
		}
			}



	public static String getIpAddr(HttpServletRequest request)
	{
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
