package com.oversea.ads.bean;

import java.io.Serializable;
import java.util.Date;

public class OverseaPostBackCount implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -858916825366409653L;
	private Long    id;
    private Long    provider;
    private Long  	adid;
    private String  date;
    private Integer installnum;
    private Date    createdate;
    private Date    updatedate;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProvider() {
		return provider;
	}
	public void setProvider(Long provider) {
		this.provider = provider;
	}
	public Long getAdid() {
		return adid;
	}
	public void setAdid(Long adid) {
		this.adid = adid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public Integer getInstallnum() {
		return installnum;
	}
	public void setInstallnum(Integer installnum) {
		this.installnum = installnum;
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
    

}
