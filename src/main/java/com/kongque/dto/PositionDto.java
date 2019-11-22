package com.kongque.dto;

public class PositionDto {

	private String id;

	private String measurePositionId;//量体部位id

	private String measureSizeId;//尺码id
	
	private String modelNetSize;//样板净尺寸
	
	private String modelFinishSize;//样板完成尺寸
	
	private String shrinkage;//缩量
	
	private String tolerance;//公差
	
	private String positionCode;//部位编码
	
	private String positionName;//部位名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMeasurePositionId() {
		return measurePositionId;
	}

	public void setMeasurePositionId(String measurePositionId) {
		this.measurePositionId = measurePositionId;
	}

	public String getModelNetSize() {
		return modelNetSize;
	}

	public void setModelNetSize(String modelNetSize) {
		this.modelNetSize = modelNetSize;
	}

	public String getModelFinishSize() {
		return modelFinishSize;
	}

	public void setModelFinishSize(String modelFinishSize) {
		this.modelFinishSize = modelFinishSize;
	}

	public String getShrinkage() {
		return shrinkage;
	}

	public void setShrinkage(String shrinkage) {
		this.shrinkage = shrinkage;
	}

	public String getTolerance() {
		return tolerance;
	}

	public void setTolerance(String tolerance) {
		this.tolerance = tolerance;
	}

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getMeasureSizeId() {
		return measureSizeId;
	}

	public void setMeasureSizeId(String measureSizeId) {
		this.measureSizeId = measureSizeId;
	}
}
