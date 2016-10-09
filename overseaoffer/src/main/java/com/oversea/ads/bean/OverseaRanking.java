package com.oversea.ads.bean;

import java.io.Serializable;

public class OverseaRanking implements Serializable
{

	private static final long serialVersionUID = -1241076739250008929L;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAdid() {
		return adid;
	}
	public void setAdid(Long adid) {
		this.adid = adid;
	}
	public Long getDimension() {
		return dimension;
	}
	public void setDimension(Long dimension) {
		this.dimension = dimension;
	}
	public Integer getOfferid() {
		return offerid;
	}
	public void setOfferid(Integer offerid) {
		this.offerid = offerid;
	}
	public Integer getRanking() {
		return ranking;
	}
	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
	public Integer getDate() {
		return date;
	}
	public void setDate(Integer date) {
		this.date = date;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}

	private Long    id;
	private Long	adid;
    private Long dimension;
    private Integer offerid;
    private Integer ranking;
    private Integer date;
    private Integer num;
    private Float score;
	private Float price;

}
