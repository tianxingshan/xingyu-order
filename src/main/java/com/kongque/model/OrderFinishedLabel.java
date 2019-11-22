package com.kongque.model;

import java.io.Serializable;

public class OrderFinishedLabel  implements Serializable{

	private static final long serialVersionUID = 620317571763880177L;
	//孔雀订单号
	private String code;
	//计划单号
	private String planNo;
	//客户姓名
	private String customerName;
	//产品唯一码
	private String styleSN;
	//款式编码
	private String styleCode;
	//款式名称
	private String styleName;
	//颜色
	private String color;
	//门店
	private String shopName;
	//产品条形码
	private String barCode;
	//绣字名(归属人姓名)
	private String owner;
	
	private String character;
	
	private String embroidName;
	
	private String orderCharacter;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPlanNo() {
		return planNo;
	}
	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getStyleSN() {
		return styleSN;
	}
	public void setStyleSN(String styleSN) {
		this.styleSN = styleSN;
	}
	public String getStyleCode() {
		return styleCode;
	}
	public void setStyleCode(String styleCode) {
		this.styleCode = styleCode;
	}
	public String getStyleName() {
		return styleName;
	}
	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getCharacter() {
		return character;
	}
	public void setCharacter(String character) {
		this.character = character;
	}
	public String getEmbroidName() {
		return embroidName;
	}
	public void setEmbroidName(String embroidName) {
		this.embroidName = embroidName;
	}
	public String getOrderCharacter() {
		return orderCharacter;
	}
	public void setOrderCharacter(String orderCharacter) {
		this.orderCharacter = orderCharacter;
	}
	
	
}
