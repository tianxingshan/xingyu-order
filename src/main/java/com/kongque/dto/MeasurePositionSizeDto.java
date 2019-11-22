package com.kongque.dto;

import java.util.List;

public class MeasurePositionSizeDto {
	
	private String id;	//部位尺码尺寸表ID

	private String[] ids;	//部位尺码尺寸表IDS
	
	private String goodsId;	//款式ID
	
	private String measureSizeId;	//尺码ID

	private String[] measureSizeIds;	//尺码IDS
	
	private String categoryId;
	
	private List<PositionDto> positionList;

	private String measurePositionId;

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

	public String getMeasureSizeId() {
		return measureSizeId;
	}

	public void setMeasureSizeId(String measureSizeId) {
		this.measureSizeId = measureSizeId;
	}

	public List<PositionDto> getPositionList() {
		return positionList;
	}

	public void setPositionList(List<PositionDto> positionList) {
		this.positionList = positionList;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String[] getMeasureSizeIds() {
		return measureSizeIds;
	}

	public void setMeasureSizeIds(String[] measureSizeIds) {
		this.measureSizeIds = measureSizeIds;
	}

	public String getMeasurePositionId() {
		return measurePositionId;
	}

	public void setMeasurePositionId(String measurePositionId) {
		this.measurePositionId = measurePositionId;
	}
}
