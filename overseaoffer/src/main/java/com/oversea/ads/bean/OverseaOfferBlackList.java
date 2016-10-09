package com.oversea.ads.bean;

import java.io.Serializable;
import java.util.Date;

public class OverseaOfferBlackList implements Serializable
{

	private static final long serialVersionUID = -1241076739250008929L;

	/**
	 * 
	 */

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	private Long    id;
	private String	pkg;
	private Long	provider;
	private String	country;
	private Integer status;
    public Long getProvider() {
		return provider;
	}

	public void setProvider(Long provider) {
		this.provider = provider;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	private Date    createdate;
    private Date    updatedate;
    

}
