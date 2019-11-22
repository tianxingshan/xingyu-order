package com.kongque.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.kongque.entity.material.MaterialCategory;

/**核算订单.物料详情返回model
 * @author sws
 *
 */
public class OrderAccountMesModel implements Serializable{

	private static final long serialVersionUID = 3180333235935834528L;
	
	/**
	 * 物料Id
	 */
	private String materiaId;
	
	/**
	 * 物料编号
	 */
	private String materiaCode;
	
	/**
	 * 物料名字
	 */
	private String materiaName;
	
	/**
	 * 生产物料实体
	 */
	private MaterialCategory  mesMaterialCategory;
	
	/**
	 * 面料分类
	 */
	private String materiaClasslName;
	
	/**
	 * 单位
	 */
	private String unit;

	/**
	 * 规格
	 */
	private String spec;
	
	/**
	 * 材质
	 */
	private String quality;
	
	/**
	 * 颜色
	 */
	private String color;
	
	/**
	 * 颜色值
	 */
	private String colorValue;
	
	/**
	 * 数量
	 */
	private String count;
	
	/**
	 * 均价
	 */
	private String unitPrice;
	
	/**
	 * 小计
	 */
	private BigDecimal sumPrice;

	public String getMateriaId() {
		return materiaId;
	}

	public void setMateriaId(String materiaId) {
		this.materiaId = materiaId;
	}

	public String getMateriaCode() {
		return materiaCode;
	}

	public void setMateriaCode(String materiaCode) {
		this.materiaCode = materiaCode;
	}

	public String getMateriaName() {
		return materiaName;
	}

	public void setMateriaName(String materiaName) {
		this.materiaName = materiaName;
	}

	public MaterialCategory getMesMaterialCategory() {
		return mesMaterialCategory;
	}

	public void setMesMaterialCategory(MaterialCategory mesMaterialCategory) {
		this.mesMaterialCategory = mesMaterialCategory;
	}

	public String getMateriaClasslName() {
		return materiaClasslName;
	}

	public void setMateriaClasslName(String materiaClasslName) {
		this.materiaClasslName = materiaClasslName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColorValue() {
		return colorValue;
	}

	public void setColorValue(String colorValue) {
		this.colorValue = colorValue;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(BigDecimal sumPrice) {
		this.sumPrice = sumPrice;
	}

}
