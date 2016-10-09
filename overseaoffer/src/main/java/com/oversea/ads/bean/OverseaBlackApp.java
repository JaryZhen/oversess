package com.oversea.ads.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 游戏黑名单
 * @author Administrator
 *
 */
public class OverseaBlackApp implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2820804202952220294L;
	
	private String pkg;
	private String name;
	private Integer status;
    private Date    createdate;
    private Date    updatedate;
	
	public String getPkg() {
		return pkg;
	}
	public void setPkg(String pkg) {
		this.pkg = pkg;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	 public Date getCreatedate()
	    {
	        return createdate;
	    }

	    public void setCreatedate(Date createdate)
	    {
	        this.createdate = createdate;
	    }

	    public Date getUpdatedate()
	    {
	        return updatedate;
	    }

	    public void setUpdatedate(Date updatedate)
	    {
	        this.updatedate = updatedate;
	    }

	
}
