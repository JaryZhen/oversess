package com.oversea.ads.bean;

import java.io.Serializable;


public class OverseaDimension implements Serializable
{

	private static final long serialVersionUID = -1241076739250008929L;

    private Long    id;
	private String	name;
	private Integer weight;
    private Integer status;
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
    

}
