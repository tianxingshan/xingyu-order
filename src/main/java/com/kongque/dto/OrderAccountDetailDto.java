package com.kongque.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author sws
 *
 */
public class OrderAccountDetailDto {

	private String id;
	
	private String orderAccountId;
	
	private String orderCode;
	
	private String goodsCode;
	
	private String goodsName;
	
	private String goodsColorName;
	
	private String mesMeasureSizeId;
	
	private Date orderTime; 
	
	private String orderCharacter;
	
	private String goodsSn;

	private Integer num; 
	
	private BigDecimal accountCostPrice;
	
	private String accountMonth;
	
	private String orderDetailId;
	
	public String getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public String getAccountMonth() {
		return accountMonth;
	}

	public void setAccountMonth(String accountMonth) {
		this.accountMonth = accountMonth;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderAccountId() {
		return orderAccountId;
	}

	public void setOrderAccountId(String orderAccountId) {
		this.orderAccountId = orderAccountId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsColorName() {
		return goodsColorName;
	}

	public void setGoodsColorName(String goodsColorName) {
		this.goodsColorName = goodsColorName;
	}

	public String getMesMeasureSizeId() {
		return mesMeasureSizeId;
	}

	public void setMesMeasureSizeId(String mesMeasureSizeId) {
		this.mesMeasureSizeId = mesMeasureSizeId;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderCharacter() {
		return orderCharacter;
	}

	public void setOrderCharacter(String orderCharacter) {
		this.orderCharacter = orderCharacter;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public BigDecimal getAccountCostPrice() {
		return accountCostPrice;
	}

	public void setAccountCostPrice(BigDecimal accountCostPrice) {
		this.accountCostPrice = accountCostPrice;
	}
	
	
}
