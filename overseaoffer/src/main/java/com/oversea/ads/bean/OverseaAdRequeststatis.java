package com.oversea.ads.bean;

import java.io.Serializable;
import java.util.Date;

public class OverseaAdRequeststatis implements Serializable
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

    public Integer getDate()
    {
        return date;
    }

    public void setDate(Integer date)
    {
        this.date = date;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getChannel()
    {
        return channel;
    }

    public Integer getNewuser()
    {
        return newuser;
    }

    public void setNewuser(Integer newuser)
    {
        this.newuser = newuser;
    }

    public void setChannel(String channel)
    {
        this.channel = channel;
    }

    public Integer getClientnum()
    {
        return clientnum;
    }

    public void setClientnum(Integer clientnum)
    {
        this.clientnum = clientnum;
    }

    public Integer getRequestnum()
    {
        return requestnum;
    }

    public void setRequestnum(Integer requestnum)
    {
        this.requestnum = requestnum;
    }

    public Integer getOneweek()
    {
        return oneweek;
    }

    public void setOneweek(Integer oneweek)
    {
        this.oneweek = oneweek;
    }

    public Integer getTwoweek()
    {
        return twoweek;
    }

    public void setTwoweek(Integer twoweek)
    {
        this.twoweek = twoweek;
    }

    public Integer getThreeweek()
    {
        return threeweek;
    }

    public void setThreeweek(Integer threeweek)
    {
        this.threeweek = threeweek;
    }

    public Integer getSutrue()
    {
        return sutrue;
    }

    public void setSutrue(Integer sutrue)
    {
        this.sutrue = sutrue;
    }

    public Integer getSuflase()
    {
        return suflase;
    }

    public void setSuflase(Integer suflase)
    {
        this.suflase = suflase;
    }

    public Date getCreatedate()
    {
        return createdate;
    }

    public void setCreatedate(Date createdate)
    {
        this.createdate = createdate;
    }

    @Override
    public String toString()
    {
        return "NgsteamAdsRequesttatis [id=" + id + ", date=" + date
                + ", country=" + country + ", channel=" + channel
                + ", clientnum=" + clientnum + ", requestnum=" + requestnum
                + ", oneweek=" + oneweek + ", twoweek=" + twoweek
                + ", threeweek=" + threeweek + ", sutrue=" + sutrue
                + ", suflase=" + suflase + ", createdate=" + createdate + "]";
    }

    private Long    id;
    private Integer date;
    private String  country;
    private String  channel;
    private Integer newuser;
    private Integer clientnum;
    private Integer requestnum;
    private Integer oneweek;
    private Integer twoweek;
    private Integer threeweek;
    private Integer sutrue;
    private Integer suflase;

    private Date    createdate;

}
