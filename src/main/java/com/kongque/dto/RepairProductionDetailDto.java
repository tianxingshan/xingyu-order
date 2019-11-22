package com.kongque.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class RepairProductionDetailDto {
	
	private String orderRepairId; //微调单ID

	private String orderRepairCode;	//微调单号

	private String repairPlanCode;	//微调计划单号

	private String customerCodeOrName;	//会员卡号或名称

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date deliveryTimeStart;	//收货日期查询开始

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date deliveryTimeEnd;	//收货日期查询结束

	private String goodsSN;	//商品唯一码

	private String goodsCodeOrName;	//款号或款名

	private String goodsCategoryCodeOrName; //商品品类名称或编码

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date repairPlanCreateDateStart;	//计划创建日期查询开始

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date repairPlanCreateDateEnd;	//计划创建日期查询结束

	private String owner;	//责任归属人

	private String orderCode;	//孔雀订单号

	private String orderProductFactory;	//生产工厂

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date repairPlanReleaseTimeStart;	//计划下达日期查询开始

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date repairPlanReleaseTimeEnd;	//计划下达日期查询结束

	private String shopId;	//门店ID

	private String[] shopIds;	//门店ID

	private String shopName;	//门店名称

	private String character;	//订单性质

	private String orderRepairStatus;	//单据状态

	private String[] orderRepairStatusArr;	//单据状态

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date planDateStart;	//投产日期查询开始

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date planDateEnd;	//投产日期查询结束

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date finishDateStart;	//准备发货日期查询开始

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date finishDateEnd;	//准备发货日期查询结束

	private String durationStart;	//时长查询开始

	private String durationEnd;	//时长查询开始
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date sendDateStart; //发货日期查询开始
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date sendDateEnd; //发货日期查询结束
	
	private String balanceStatus; //结算状态查询;

	private String technicianIds;//版型师 对个以逗号分隔

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date productionFinishDateStart;//生产开始日期
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date productionFinishDateEnd; //生产截止日期

	private String frequency;//微调次数
	
	public Date getSendDateStart() {
		return sendDateStart;
	}

	public void setSendDateStart(Date sendDateStart) {
		this.sendDateStart = sendDateStart;
	}

	public Date getSendDateEnd() {
		return sendDateEnd;
	}

	public void setSendDateEnd(Date sendDateEnd) {
		this.sendDateEnd = sendDateEnd;
	}

	public String getBalanceStatus() {
		return balanceStatus;
	}

	public void setBalanceStatus(String balanceStatus) {
		this.balanceStatus = balanceStatus;
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

	public String getRepairPlanCode() {
		return repairPlanCode;
	}

	public void setRepairPlanCode(String repairPlanCode) {
		this.repairPlanCode = repairPlanCode;
	}

	public String getCustomerCodeOrName() {
		return customerCodeOrName;
	}

	public void setCustomerCodeOrName(String customerCodeOrName) {
		this.customerCodeOrName = customerCodeOrName;
	}

	public Date getDeliveryTimeStart() {
		return deliveryTimeStart;
	}

	public void setDeliveryTimeStart(Date deliveryTimeStart) {
		this.deliveryTimeStart = deliveryTimeStart;
	}

	public Date getDeliveryTimeEnd() {
		return deliveryTimeEnd;
	}

	public void setDeliveryTimeEnd(Date deliveryTimeEnd) {
		this.deliveryTimeEnd = deliveryTimeEnd;
	}

	public String getGoodsSN() {
		return goodsSN;
	}

	public void setGoodsSN(String goodsSN) {
		this.goodsSN = goodsSN;
	}

	public String getGoodsCodeOrName() {
		return goodsCodeOrName;
	}

	public void setGoodsCodeOrName(String goodsCodeOrName) {
		this.goodsCodeOrName = goodsCodeOrName;
	}

	public String getGoodsCategoryCodeOrName() {
		return goodsCategoryCodeOrName;
	}

	public void setGoodsCategoryCodeOrName(String goodsCategoryCodeOrName) {
		this.goodsCategoryCodeOrName = goodsCategoryCodeOrName;
	}

	public Date getRepairPlanCreateDateStart() {
		return repairPlanCreateDateStart;
	}

	public void setRepairPlanCreateDateStart(Date repairPlanCreateDateStart) {
		this.repairPlanCreateDateStart = repairPlanCreateDateStart;
	}

	public Date getRepairPlanCreateDateEnd() {
		return repairPlanCreateDateEnd;
	}

	public void setRepairPlanCreateDateEnd(Date repairPlanCreateDateEnd) {
		this.repairPlanCreateDateEnd = repairPlanCreateDateEnd;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderProductFactory() {
		return orderProductFactory;
	}

	public void setOrderProductFactory(String orderProductFactory) {
		this.orderProductFactory = orderProductFactory;
	}

	public Date getRepairPlanReleaseTimeStart() {
		return repairPlanReleaseTimeStart;
	}

	public void setRepairPlanReleaseTimeStart(Date repairPlanReleaseTimeStart) {
		this.repairPlanReleaseTimeStart = repairPlanReleaseTimeStart;
	}

	public Date getRepairPlanReleaseTimeEnd() {
		return repairPlanReleaseTimeEnd;
	}

	public void setRepairPlanReleaseTimeEnd(Date repairPlanReleaseTimeEnd) {
		this.repairPlanReleaseTimeEnd = repairPlanReleaseTimeEnd;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String[] getShopIds() {
		return shopIds;
	}

	public void setShopIds(String[] shopIds) {
		this.shopIds = shopIds;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public String getOrderRepairStatus() {
		return orderRepairStatus;
	}

	public void setOrderRepairStatus(String orderRepairStatus) {
		this.orderRepairStatus = orderRepairStatus;
	}

	public String[] getOrderRepairStatusArr() {
		return orderRepairStatusArr;
	}

	public void setOrderRepairStatusArr(String[] orderRepairStatusArr) {
		this.orderRepairStatusArr = orderRepairStatusArr;
	}

	public Date getPlanDateStart() {
		return planDateStart;
	}

	public void setPlanDateStart(Date planDateStart) {
		this.planDateStart = planDateStart;
	}

	public Date getPlanDateEnd() {
		return planDateEnd;
	}

	public void setPlanDateEnd(Date planDateEnd) {
		this.planDateEnd = planDateEnd;
	}

	public Date getFinishDateStart() {
		return finishDateStart;
	}

	public void setFinishDateStart(Date finishDateStart) {
		this.finishDateStart = finishDateStart;
	}

	public Date getFinishDateEnd() {
		return finishDateEnd;
	}

	public void setFinishDateEnd(Date finishDateEnd) {
		this.finishDateEnd = finishDateEnd;
	}

	public String getDurationStart() {
		return durationStart;
	}

	public void setDurationStart(String durationStart) {
		this.durationStart = durationStart;
	}

	public String getDurationEnd() {
		return durationEnd;
	}

	public void setDurationEnd(String durationEnd) {
		this.durationEnd = durationEnd;
	}

	public String getTechnicianIds() {
		return technicianIds;
	}

	public void setTechnicianIds(String technicianIds) {
		this.technicianIds = technicianIds;
	}

	public Date getProductionFinishDateStart() {
		return productionFinishDateStart;
	}

	public void setProductionFinishDateStart(Date productionFinishDateStart) {
		this.productionFinishDateStart = productionFinishDateStart;
	}

	public Date getProductionFinishDateEnd() {
		return productionFinishDateEnd;
	}

	public void setProductionFinishDateEnd(Date productionFinishDateEnd) {
		this.productionFinishDateEnd = productionFinishDateEnd;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
}
