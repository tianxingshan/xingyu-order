package com.kongque.model;

import com.kongque.annotation.ExportExcel;

@ExportExcel
public class RepairProductionDetailModel {

	private String repairPlanId;	//微调计划单ID

	@ExportExcel
	private String repairPlanCode;	//微调计划单号

	private String orderRepairId; //微调单ID

	@ExportExcel
	private String orderRepairCode;	//微调单号

	private String customerId;	//会员Id

	private String customerCode;	//会员卡号

	@ExportExcel
	private String customerName;	//会员名称

	@ExportExcel
	private String goodsCode;	//款号

	@ExportExcel
	private String goodsName;	//款名

	@ExportExcel
	private String goodsColorName;	//颜色

	private String goodsCategoryCode; //商品品类编码

	@ExportExcel
	private String goodsCategoryName; //商品品类名称

	private String shopId;	//门店ID

	@ExportExcel
	private String shopName;	//门店名称

	@ExportExcel(source = {"0","1","2","3","4","5","6","7","8","9","10"},
			target = {"未送出","已送出","星域审核通过","星域驳回","计划维护","生产中","生产完成","已发货","已收货","待收货","待发货"})
	private String orderRepairStatus;	//单据状态

	@ExportExcel
	private String character;	//订单性质

	@ExportExcel
	private String orderProductFactory;	//生产工厂

	@ExportExcel
	private String checkTime; 	//星域审核日期

	@ExportExcel
	private String deliveryTime;	//收货日期

	@ExportExcel
	private String deliveryExpressNumber;	//收货单号

	@ExportExcel
	private String repairPlanCreateDate;	//计划创建日期

	@ExportExcel
	private String repairConfirmTime;	//计划审核日期

	@ExportExcel
	private String repairPlanReleaseTime;	//计划下达日期

	@ExportExcel
	private String prodTime;	//投产日期

	@ExportExcel
	private String finishDate;	//准备交货日期
	@ExportExcel
	private String productionFinishDate;//生产完成日期

	@ExportExcel
	private String sendTime;	//发货日期

	@ExportExcel
	private String sendExpressNumber;	//发货单号

	@ExportExcel
	private String technicianName;	//版型师

	@ExportExcel
	private String duration;	//时长

	@ExportExcel
	private String orderSendTime;	//原订单发货日期

	@ExportExcel
	private String orderCode;	//孔雀订单号

	@ExportExcel
	private String goodsSN;	//商品唯一码

	@ExportExcel
	private String ecOrderCode;	//EC订单号
	@ExportExcel
	private String frequency;//微调次数

	private String orderId;	//订单ID

	private String orderDetailId;	//订单明细ID
	@ExportExcel
	private String repairFeedback;	//实调内容

	private String repairOpinion;	//微调建议
	
	private String balanceStatus; //结算状态



	
	public String getBalanceStatus() {
		return balanceStatus;
	}

	public void setBalanceStatus(String balanceStatus) {
		this.balanceStatus = balanceStatus;
	}

	public String getRepairPlanId() {
		return repairPlanId;
	}

	public void setRepairPlanId(String repairPlanId) {
		this.repairPlanId = repairPlanId;
	}

	public String getRepairPlanCode() {
		return repairPlanCode;
	}

	public void setRepairPlanCode(String repairPlanCode) {
		this.repairPlanCode = repairPlanCode;
	}

	public String getOrderRepairId() {
		return orderRepairId;
	}

	public void setOrderRepairId(String orderRepairId) {
		this.orderRepairId = orderRepairId;
	}

	public String getOrderRepairCode() {
		return orderRepairCode;
	}

	public void setOrderRepairCode(String orderRepairCode) {
		this.orderRepairCode = orderRepairCode;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public String getGoodsCategoryCode() {
		return goodsCategoryCode;
	}

	public void setGoodsCategoryCode(String goodsCategoryCode) {
		this.goodsCategoryCode = goodsCategoryCode;
	}

	public String getGoodsCategoryName() {
		return goodsCategoryName;
	}

	public void setGoodsCategoryName(String goodsCategoryName) {
		this.goodsCategoryName = goodsCategoryName;
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

	public String getOrderRepairStatus() {
		return orderRepairStatus;
	}

	public void setOrderRepairStatus(String orderRepairStatus) {
		this.orderRepairStatus = orderRepairStatus;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public String getOrderProductFactory() {
		return orderProductFactory;
	}

	public void setOrderProductFactory(String orderProductFactory) {
		this.orderProductFactory = orderProductFactory;
	}

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getDeliveryExpressNumber() {
		return deliveryExpressNumber;
	}

	public void setDeliveryExpressNumber(String deliveryExpressNumber) {
		this.deliveryExpressNumber = deliveryExpressNumber;
	}

	public String getRepairPlanCreateDate() {
		return repairPlanCreateDate;
	}

	public void setRepairPlanCreateDate(String repairPlanCreateDate) {
		this.repairPlanCreateDate = repairPlanCreateDate;
	}

	public String getRepairConfirmTime() {
		return repairConfirmTime;
	}

	public void setRepairConfirmTime(String repairConfirmTime) {
		this.repairConfirmTime = repairConfirmTime;
	}

	public String getRepairPlanReleaseTime() {
		return repairPlanReleaseTime;
	}

	public void setRepairPlanReleaseTime(String repairPlanReleaseTime) {
		this.repairPlanReleaseTime = repairPlanReleaseTime;
	}

	public String getProdTime() {
		return prodTime;
	}

	public void setProdTime(String prodTime) {
		this.prodTime = prodTime;
	}

	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getSendExpressNumber() {
		return sendExpressNumber;
	}

	public void setSendExpressNumber(String sendExpressNumber) {
		this.sendExpressNumber = sendExpressNumber;
	}

	public String getTechnicianName() {
		return technicianName;
	}

	public void setTechnicianName(String technicianName) {
		this.technicianName = technicianName;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getOrderSendTime() {
		return orderSendTime;
	}

	public void setOrderSendTime(String orderSendTime) {
		this.orderSendTime = orderSendTime;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getGoodsSN() {
		return goodsSN;
	}

	public void setGoodsSN(String goodsSN) {
		this.goodsSN = goodsSN;
	}

	public String getEcOrderCode() {
		return ecOrderCode;
	}

	public void setEcOrderCode(String ecOrderCode) {
		this.ecOrderCode = ecOrderCode;
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

	public String getRepairFeedback() {
		return repairFeedback;
	}

	public void setRepairFeedback(String repairFeedback) {
		this.repairFeedback = repairFeedback;
	}

	public String getRepairOpinion() {
		return repairOpinion;
	}

	public void setRepairOpinion(String repairOpinion) {
		this.repairOpinion = repairOpinion;
	}

	public String getProductionFinishDate() {
		return productionFinishDate;
	}

	public void setProductionFinishDate(String productionFinishDate) {
		this.productionFinishDate = productionFinishDate;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
}
