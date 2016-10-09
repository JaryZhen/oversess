package com.oversea.ads.bean;

import java.io.Serializable;
import java.util.Date;

public class OverseaProvider implements Serializable
{

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
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

    
    public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getPre_weight() {
		return pre_weight;
	}

	public void setPre_weight(Integer preWeight) {
		pre_weight = preWeight;
	}

	public Integer getCap() {
		return cap;
	}

	public void setCap(Integer cap) {
		this.cap = cap;
	}

	public Integer getSinstall() {
		return sinstall;
	}

	public void setSinstall(Integer sinstall) {
		this.sinstall = sinstall;
	}


	/**
	 * 
	 */
    private static final long serialVersionUID = 5463407943802255296L;

    public Long               id;
    public String             name;
    public String             url;
    public String             username;
    public String             password;
    
    public Integer            weight;
    public Integer            pre_weight;
    public Integer            cap;
    public Integer            sinstall;
    
    public Integer            status;
    public Date               createdate;
    public Date               updatedate;
    
}
