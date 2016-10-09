package com.oversea.ads.bean;

import java.io.Serializable;
import java.util.Date;

public class OverseaAppStatisDate implements Serializable
{

    /**
	 * 
	 */
    private static final long serialVersionUID = -2370780299563269341L;

    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getStartnum() {
		return startnum;
	}

	public void setStartnum(Integer startnum) {
		this.startnum = startnum;
	}

	public Long getTimes() {
		return times;
	}

	public void setTimes(Long times) {
		this.times = times;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	private Long    id;
    private String  pkg;
    private Long date;
    private String country;
    private Integer startnum;
    private Long times;
    
    private Date    createdate;
    
    
}
