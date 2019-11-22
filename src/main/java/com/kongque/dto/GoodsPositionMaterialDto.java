package com.kongque.dto;

import java.util.List;

import com.kongque.entity.material.GoodsPositionMaterial;

public class GoodsPositionMaterialDto {

	private String id;	//部位尺码物料表ID

	private String[] ids;	//部位尺码物料表IDS

	private String goodsDetailId;
	
	private String measureSizeId;
	
	private String materialPositionId;
	
	private String[] measureSizeIds;
	
	private List<GoodsPositionMaterial> positionList;
	
	public String getGoodsDetailId() {
		return goodsDetailId;
	}

	public void setGoodsDetailId(String goodsDetailId) {
		this.goodsDetailId = goodsDetailId;
	}

	public String getMeasureSizeId() {
		return measureSizeId;
	}

	public void setMeasureSizeId(String measureSizeId) {
		this.measureSizeId = measureSizeId;
	}

	public String getMaterialPositionId() {
		return materialPositionId;
	}

	public void setMaterialPositionId(String materialPositionId) {
		this.materialPositionId = materialPositionId;
	}

	public List<GoodsPositionMaterial> getPositionList() {
		return positionList;
	}

	public void setPositionList(List<GoodsPositionMaterial> positionList) {
		this.positionList = positionList;
	}

	public String[] getMeasureSizeIds() {
		return measureSizeIds;
	}

	public void setMeasureSizeIds(String[] measureSizeIds) {
		this.measureSizeIds = measureSizeIds;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}
}
