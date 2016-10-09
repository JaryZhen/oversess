package com.oversea.ads.bean;

import java.io.Serializable;
import java.util.Date;

public class OverseaDevice implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 2308090241653271900L;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getDevid()
    {
        return devid;
    }

    public void setDevid(String devid)
    {
        this.devid = devid;
    }

    public String getMcc()
    {
        return mcc;
    }

    public void setMcc(String mcc)
    {
        this.mcc = mcc;
    }

    public String getMnc()
    {
        return mnc;
    }

    public void setMnc(String mnc)
    {
        this.mnc = mnc;
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

    public void setChannel(String channel)
    {
        this.channel = channel;
    }

    public String getProvincecode()
    {
        return provincecode;
    }

    public void setProvincecode(String provincecode)
    {
        this.provincecode = provincecode;
    }

    public String getCitycode()
    {
        return citycode;
    }

    public void setCitycode(String citycode)
    {
        this.citycode = citycode;
    }

    public String getImei()
    {
        return imei;
    }

    public void setImei(String imei)
    {
        this.imei = imei;
    }

    public String getImsi()
    {
        return imsi;
    }

    public void setImsi(String imsi)
    {
        this.imsi = imsi;
    }

    public String getImsi2()
    {
        return imsi2;
    }

    public void setImsi2(String imsi2)
    {
        this.imsi2 = imsi2;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public String getCpu()
    {
        return cpu;
    }

    public void setCpu(String cpu)
    {
        this.cpu = cpu;
    }

    public Integer getCpucore()
    {
        return cpucore;
    }

    public void setCpucore(Integer cpucore)
    {
        this.cpucore = cpucore;
    }

    public String getCpufreq()
    {
        return cpufreq;
    }

    public void setCpufreq(String cpufreq)
    {
        this.cpufreq = cpufreq;
    }

    public Integer getRooted()
    {
        return rooted;
    }

    public void setRooted(Integer rooted)
    {
        this.rooted = rooted;
    }

    public String getMac()
    {
        return mac;
    }

    public void setMac(String mac)
    {
        this.mac = mac;
    }

    public String getSysver()
    {
        return sysver;
    }

    public void setSysver(String sysver)
    {
        this.sysver = sysver;
    }

    public String getSmccenter()
    {
        return smccenter;
    }

    public void setSmccenter(String smccenter)
    {
        this.smccenter = smccenter;
    }

    public Long getRam()
    {
        return ram;
    }

    public void setRam(Long ram)
    {
        this.ram = ram;
    }

    public Long getInstorage()
    {
        return instorage;
    }

    public void setInstorage(Long instorage)
    {
        this.instorage = instorage;
    }

    public Long getExstorage()
    {
        return exstorage;
    }

    public void setExstorage(Long exstorage)
    {
        this.exstorage = exstorage;
    }

    public String getOpengles()
    {
        return opengles;
    }

    public void setOpengles(String opengles)
    {
        this.opengles = opengles;
    }

    public String getTelenum()
    {
        return telenum;
    }

    public void setTelenum(String telenum)
    {
        this.telenum = telenum;
    }

    public String getTelenum2()
    {
        return telenum2;
    }

    public void setTelenum2(String telenum2)
    {
        this.telenum2 = telenum2;
    }

    public Integer getNet()
    {
        return net;
    }

    public void setNet(Integer net)
    {
        this.net = net;
    }

    public String getResolution()
    {
        return resolution;
    }

    public void setResolution(String resolution)
    {
        this.resolution = resolution;
    }

    public String getLcdsize()
    {
        return lcdsize;
    }

    public void setLcdsize(String lcdsize)
    {
        this.lcdsize = lcdsize;
    }

    public String getAppid()
    {
        return appid;
    }

    public void setAppid(String appid)
    {
        this.appid = appid;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
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

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    private Long    id;
    private String  devid;
    private String  channel;
    private String  mcc;
    private String  mnc;
    private String  country;
    private String  provincecode;
    private String  citycode;
    private String  imei;
    private String  imsi;
    private String  imsi2;
    private String  telenum;
    private String  telenum2;
    private String  model;
    private String  cpu;
    private Integer cpucore;
    private String  cpufreq;
    private Integer net;
    private Integer rooted;
    private String  mac;
    private String  sysver;
    private String  smccenter;
    private Long    ram;
    private Long    instorage;
    private Long    exstorage;
    private String  opengles;
    private String  lcdsize;
    private String  resolution;
    private String  appid;
    private String  language;
    private String  ip;
    private Date    createdate;
    private Date    updatedate;
    private Integer status;
}
