package com.kongque.dto;

import java.util.Date;
/**
 * 订单查询条件封装类
 * @author Administrator
 *
 */
public class OrderClosedDto{
	
	
	private String cid;
	/**
	 * 结案编码
	 */
	private String closedCode;
	/*
	 * 订单id
	 */
	private String orderId;
	
	/*
	 * 结案单申请人
	 */
	private String closedApplicant;
	/*
	 *结案单状态 
	 */
	private String closedStatus;
	/*
	 * 结案单创建时间
	 */
	private Date closedCreateTime; 
	/*
	 *结案单审核人 
	 */
	private String closedAuditor;
	/*
	 *结案单审核时间
	 */
	private Date closedCheckTime;
	/**
	 * 申请结案原因
	 * @return
	 */
	private String closedReason;
	/**
	 * 审核说明
	 */
	private String closedInstruction;
	/**
	 * 结案单提交时间
	 */
	private Date closedSubmitTime;
	private String del;
	
	/**
	 * 会员姓名
	 */
	private String customerName;
	/**
	 * 商品名称
	 */
	private String styleName;
	/**
	 * 商品颜色
	 */
	private String styleColor;

	/**
	 * 订单编号
	 * */
	private String code;
	/**
	 * ERP订单号
	 * @return
	 */
	private String posNo;
	/**
	 * 商品唯一码
	 * @return
	 */
	private String styleSN;
	
	private String[] ids;
	
	private String userId;
	
	
	
	
	private String shopId;
	
	private String userName;//所登录用户的用户名
	
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPosNo() {
		return posNo;
	}
	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}
	public String getStyleSN() {
		return styleSN;
	}
	public void setStyleSN(String styleSN) {
		this.styleSN = styleSN;
	}
	public String[] getIds() {
		return ids;
	}
	public void setIds(String[] ids) {
		this.ids = ids;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
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
	public String getClosedApplicant() {
		return closedApplicant;
	}
	public void setClosedApplicant(String closedApplicant) {
		this.closedApplicant = closedApplicant;
	}
	public String getClosedStatus() {
		return closedStatus;
	}
	public void setClosedStatus(String closedStatus) {
		this.closedStatus = closedStatus;
	}
	public Date getClosedCreateTime() {
		return closedCreateTime;
	}
	public void setClosedCreateTime(Date closedCreateTime) {
		this.closedCreateTime = closedCreateTime;
	}
	public String getClosedAuditor() {
		return closedAuditor;
	}
	public void setClosedAuditor(String closedAuditor) {
		this.closedAuditor = closedAuditor;
	}
	public Date getClosedCheckTime() {
		return closedCheckTime;
	}
	public void setClosedCheckTime(Date closedCheckTime) {
		this.closedCheckTime = closedCheckTime;
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
	public Date getClosedSubmitTime() {
		return closedSubmitTime;
	}
	public void setClosedSubmitTime(Date closedSubmitTime) {
		this.closedSubmitTime = closedSubmitTime;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
