package com.oversea.ads.bean;

import java.io.Serializable;
import java.util.Date;

public class OverseaCategory implements Serializable
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

    public Long getPcid()
    {
        return pcid;
    }

    public void setPcid(Long pcid)
    {
        this.pcid = pcid;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
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
    private Long    pcid;
    private String  desc;
    private Integer status;
    private Date    createdate;
    private Date    updatedate;
}
