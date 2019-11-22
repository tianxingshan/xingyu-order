package com.kongque.model;

import com.kongque.entity.basics.Tenant;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

public class GoodsDetailTanantModel {

	private String id;
	private String goodsDetailId;
	private String tenantId;
	private String balancePrice;
	private String tenantPrice;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoodsDetailId() {
		return goodsDetailId;
	}

	public void setGoodsDetailId(String goodsDetailId) {
		this.goodsDetailId = goodsDetailId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getBalancePrice() {
		return balancePrice;
	}

	public void setBalancePrice(String balancePrice) {
		this.balancePrice = balancePrice;
	}

	public String getTenantPrice() {
		return tenantPrice;
	}

	public void setTenantPrice(String tenantPrice) {
		this.tenantPrice = tenantPrice;
	}

	
}
