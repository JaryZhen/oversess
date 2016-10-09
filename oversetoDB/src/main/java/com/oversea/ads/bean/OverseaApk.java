package com.oversea.ads.bean;

import java.io.Serializable;
import java.util.Date;

public class OverseaApk implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -6551350471828955778L;

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

    public String getPkg()
    {
        return pkg;
    }

    public void setPkg(String pkg)
    {
        this.pkg = pkg;
    }

    public String getApk()
    {
        return apk;
    }

    public void setApk(String apk)
    {
        this.apk = apk;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public Integer getVersioncode()
    {
        return versioncode;
    }

    public void setVersioncode(Integer versioncode)
    {
        this.versioncode = versioncode;
    }

    public String getAndroidversion()
    {
        return androidversion;
    }

    public void setAndroidversion(String androidversion)
    {
        this.androidversion = androidversion;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }


	public Long getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(Long subcategory) {
		this.subcategory = subcategory;
	}

	public Long getSize()
    {
        return size;
    }

    public void setSize(Long size)
    {
        this.size = size;
    }

    public String getMd5()
    {
        return md5;
    }

    public void setMd5(String md5)
    {
        this.md5 = md5;
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
    
    

    private Long    id;
    private String  name;
    private String  pkg;
    private String  apk;
    private String  icon;
    private String  version;
    private Integer versioncode;
    private String  androidversion;
    private String  category;
    private Long    subcategory;
    private Long    size;
    private String  md5;
    private Integer status;
    private Date    createdate;
    private Date    updatedate;
}
