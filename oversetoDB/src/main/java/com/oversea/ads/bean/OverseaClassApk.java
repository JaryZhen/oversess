package com.oversea.ads.bean;

import java.io.Serializable;

public class OverseaClassApk implements Serializable
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

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public Long getSize()
    {
        return size;
    }

    public void setSize(Long size)
    {
        this.size = size;
    }

    public String getCat()
    {
        return cat;
    }

    public void setCat(String cat)
    {
        this.cat = cat;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    private Long   id;
    private String name;
    private String pkg;
    private String icon;
    private Long   size;
    private String cat;
    private String category;

}
