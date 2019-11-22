package com.kongque.dto;


import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * 
 * 
 * @author 
 *
 */
public class LogisticDto {
	private String id;
	private String tenantId;
	private String expressCompany;//快递公司
	private String expressNumber;//快递单号
	private String expressPrice;//快递价格
	@DateTimeFormat(pattern="yy-MM-dd HH:mm:ss")
	private Date sendTime;//发货时间
	@DateTimeFormat(pattern="yy-MM-dd HH:mm:ss")
	private Date deliveryTime;//收货时间
	private String logisticType;//收货发货
	private String sender;//寄件人
	private String senderAddress;//寄件地址
	private String receiverAddress;//收件地址
	private String senderPhone;//寄件人电话
	private String settlementType;//结算类型
	private String receiver;//收件人
	private String receiverPhone;//收件人电话
	
	private String shopId;//门店id
	private String shopName;//门店名称
	private String checkStatus;//审核状态
	private String checkTime;//审核时间
	private String checkUserId;//审核人id
	private String note;//备注
	private String deleteFlag;//是否删除1.作废  0.正常
//	private String orderRepairId;
//	private String orderDetailId;
	
	private LogisticOrderListDto orderList;
	
	private String voucherType; //判断是微调单还是订单1.微调单 0.订单
	private String customerName;//顾客姓名
	private String orderRepairCode;
	@DateTimeFormat(pattern="yy-MM-dd")
	private Date startTime;
	@DateTimeFormat(pattern="yy-MM-dd")
	private Date endTime;

	@DateTimeFormat(pattern="yy-MM-dd")
	private Date startCreateTime;
	@DateTimeFormat(pattern="yy-MM-dd")
	private Date endCreateTime;
	private String orderCode;
	
	private String  vourType;//0定制订单   1微调订单

	private String orderType;//0定制订单   1微调订单
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getExpressCompany() {
		return expressCompany;
	}
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
	public String getExpressNumber() {
		return expressNumber;
	}
	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}
	public String getExpressPrice() {
		return expressPrice;
	}
	public void setExpressPrice(String expressPrice) {
		this.expressPrice = expressPrice;
	}
	
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Date getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getLogisticType() {
		return logisticType;
	}
	public void setLogisticType(String logisticType) {
		this.logisticType = logisticType;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSenderAddress() {
		return senderAddress;
	}
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getSenderPhone() {
		return senderPhone;
	}
	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}
	public String getSettlementType() {
		return settlementType;
	}
	public void setSettlementType(String settlementType) {
		this.settlementType = settlementType;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public String getCheckUserId() {
		return checkUserId;
	}
	public void setCheckUserId(String checkUserId) {
		this.checkUserId = checkUserId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
//	public String getOrderRepairId() {
//		return orderRepairId;
//	}
//	public void setOrderRepairId(String orderRepairId) {
//		this.orderRepairId = orderRepairId;
//	}
//	public String getOrderDetailId() {
//		return orderDetailId;
//	}
//	public void setOrderDetailId(String orderDetailId) {
//		this.orderDetailId = orderDetailId;
//	}
	
	public String getVoucherType() {
		return voucherType;
	}
	
	public LogisticOrderListDto getOrderList() {
		return orderList;
	}
	public void setOrderList(LogisticOrderListDto orderList) {
		this.orderList = orderList;
	}
	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getOrderRepairCode() {
		return orderRepairCode;
	}
	public void setOrderRepairCode(String orderRepairCode) {
		this.orderRepairCode = orderRepairCode;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getVourType() {
		return vourType;
	}
	public void setVourType(String vourType) {
		this.vourType = vourType;
	}

	public Date getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(Date startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
}
