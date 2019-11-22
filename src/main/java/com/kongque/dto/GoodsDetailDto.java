package com.kongque.dto;

import java.util.List;

import com.kongque.entity.goods.GoodsDetailTanant;

public class GoodsDetailDto {
	
	private String id;
	
	private String goodsId;
	
	private String colorName;
	
	private String costPrice;
	
	private String materielCode;
	
	private String imageKeys;

	private String status;
	
	private List<GoodsDetailTanant> listGoodsDetailTanant;

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

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}

	public String getMaterielCode() {
		return materielCode;
	}

	public void setMaterielCode(String materielCode) {
		this.materielCode = materielCode;
	}

	public String getImageKeys() {
		return imageKeys;
	}

	public void setImageKeys(String imageKeys) {
		this.imageKeys = imageKeys;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<GoodsDetailTanant> getListGoodsDetailTanant() {
		return listGoodsDetailTanant;
	}

	public void setListGoodsDetailTanant(List<GoodsDetailTanant> listGoodsDetailTanant) {
		this.listGoodsDetailTanant = listGoodsDetailTanant;
	}
	
	

}
