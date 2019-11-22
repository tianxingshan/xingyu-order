package com.kongque.dto;

public class GoodsOptionalTechnologyDto {
	
	private String  id;
	
	private String goodsId;
	
	private String[] optionalTechnologyIds;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String[] getOptionalTechnologyIds() {
		return optionalTechnologyIds;
	}

	public void setOptionalTechnologyIds(String[] optionalTechnologyIds) {
		this.optionalTechnologyIds = optionalTechnologyIds;
	}
	
}
