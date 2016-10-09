package com.oversea.ads.bean;

import java.io.Serializable;
import java.util.Date;

public class OverseaMasterSlaves implements Serializable
{

    /**
	 * 
	 */
    private static final long serialVersionUID = -2370780299563269341L;

	private Long    id;
    private String  name;
    private String  pkg;
    private String  md5;
    private Integer size;
    private String  version;
    private String  url;
    private Integer type;
    private Integer status;
    private Date    createdate;
    private Date    updatedate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPkg() {
		return pkg;
	}
	public void setPkg(String pkg) {
		this.pkg = pkg;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	@Override
	public String toString() {
		return "NgsteamMasterSlaves [id=" + id + ", type=" + type + ", name="
				+ name + ", pkg=" + pkg + ", md5=" + md5 + ", size=" + size
				+ ", version=" + version + ", url=" + url + ", status="
				+ status + ", createdate=" + createdate + ", updatedate="
				+ updatedate + "]";
	}
    
    
    
}
