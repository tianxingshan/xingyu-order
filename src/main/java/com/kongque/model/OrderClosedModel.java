package com.kongque.model;

import java.io.Serializable;

public class OrderClosedModel implements Serializable {

	private static final long serialVersionUID = 1725956066732523129L;
	
	/**
	 * 来源：订单信息(Order:t_order)
	 */
	/**
	 * 结案单id
	 */
	private String closedID;
	/**
	 * 结案编号
	 */
	private String closedCode;
	/**
	 * 订单id
	 */
	private String orderId;
	/**
	 * 订单详情id
	 */
	private String orderDetailId;
	/**
	 * 款式id
	 */
	private String styleId;
	/**
	 * 订单编号
	 */
	private String orderCode;
	/**
	 * erp 订单号
	 */
	private String posNo;
	
     /**
      *  所属店名
      */
    private String shopName;
	
    /**
     * 顾客姓名
     */
    private String customerName;
    
    
	/**
	 * 款式名（商品名称）
	 */
	private String styleName;
	
	/**
	 * 款式颜色
	 * @since 2017-08-23
	 */
	private String styleColor;
	
   
	/**
	 * 订单状态
	 * @since 2017-08-23
	 */
	//private String billStatus;
	/**
	 * 订单明细状态
	 */
	//private String orderDetailStatus;
	
	 /**
     *结案单建立日期
     */
	private String closedCreateTime;
	
	/**
	 * 结案单审核时间
	 * @since 2017-08-29
	 */
	private String closedCheckTime;

	
    /**
     *  审核人员
     */
    private String closedAuditor;

    /**
     *  提交日期
     */
    private String closedSubmitTime;
    
    /**
     * 申请人
     */
    private String closedApplicant;
    
   
	
	/**
	 * 来源：款式信息(OrderDetail.Style:t_style)
	 */
	/**
	 * 款式码（商品编码）
	 */
	private String styleCode;
	
	
	 /**
	  * 品类名称（商品类别）
	  */
	private String categoryName;
	 
	/**
	 * 来源：订单明细信息(OrderDetail:t_order_detail)
	 */
	
	/**
	 * 商品唯一识别码
	 */
	private String styleSN;
		
	 /**
     *  物料编码
     */
	private String materielCode;
	/**
	 * 结案状态
	 * @return
	 */
	private String closedStatus;
	
	/*
	 * 尺寸
	 */
	private String styleSize;
	/*
	 * 单位
	 */
	private String styleUnit;
	/*
	 * 数量
	 */
	private String num;
	/**
	 * 结案单原因
	 * @return
	 */
	private String closedReason;
	/**
	 * 结案说明
	 */
	private String closedInstruction;
	
	private String shopId;
	
	
	public String getClosedID() {
		return closedID;
	}
	public void setClosedID(String closedID) {
		this.closedID = closedID;
	}
	public String getClosedCode() {
		return closedCode;
	}
	public void setClosedCode(String closedCode) {
		this.closedCode = closedCode;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderDetailId() {
		return orderDetailId;
	}
	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}
	
	public String getStyleId() {
		return styleId;
	}
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getPosNo() {
		return posNo;
	}
	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getStyleName() {
		return styleName;
	}
	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}
	public String getStyleColor() {
		return styleColor;
	}
	public void setStyleColor(String styleColor) {
		this.styleColor = styleColor;
	}
	public String getClosedCreateTime() {
		return closedCreateTime;
	}
	public void setClosedCreateTime(String closedCreateTime) {
		this.closedCreateTime = closedCreateTime;
	}
	public String getClosedCheckTime() {
		return closedCheckTime;
	}
	public void setClosedCheckTime(String closedCheckTime) {
		this.closedCheckTime = closedCheckTime;
	}
	public String getClosedAuditor() {
		return closedAuditor;
	}
	public void setClosedAuditor(String closedAuditor) {
		this.closedAuditor = closedAuditor;
	}
	public String getClosedSubmitTime() {
		return closedSubmitTime;
	}
	public void setClosedSubmitTime(String closedSubmitTime) {
		this.closedSubmitTime = closedSubmitTime;
	}
	public String getClosedApplicant() {
		return closedApplicant;
	}
	public void setClosedApplicant(String closedApplicant) {
		this.closedApplicant = closedApplicant;
	}
	public String getStyleCode() {
		return styleCode;
	}
	public void setStyleCode(String styleCode) {
		this.styleCode = styleCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getStyleSN() {
		return styleSN;
	}
	public void setStyleSN(String styleSN) {
		this.styleSN = styleSN;
	}
	public String getMaterielCode() {
		return materielCode;
	}
	public void setMaterielCode(String materielCode) {
		this.materielCode = materielCode;
	}
	public String getClosedStatus() {
		return closedStatus;
	}
	public void setClosedStatus(String closedStatus) {
		this.closedStatus = closedStatus;
	}
	public String getStyleSize() {
		return styleSize;
	}
	public void setStyleSize(String styleSize) {
		this.styleSize = styleSize;
	}
	public String getStyleUnit() {
		return styleUnit;
	}
	public void setStyleUnit(String styleUnit) {
		this.styleUnit = styleUnit;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getClosedReason() {
		return closedReason;
	}
	public void setClosedReason(String closedReason) {
		this.closedReason = closedReason;
	}
	public String getClosedInstruction() {
		return closedInstruction;
	}
	public void setClosedInstruction(String closedInstruction) {
		this.closedInstruction = closedInstruction;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	
	
}
