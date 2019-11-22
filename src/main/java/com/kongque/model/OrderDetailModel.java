package com.kongque.model;

import com.kongque.entity.goods.GoodsDetail;

public class OrderDetailModel {
	
	private String id;
	
	private String orderId;
	
	private String goodsDetailId;
	
	private String goodsSn;
	
	private String goodsCode;
	
	private String goodsName;
	
	private String goodsColorName;
	
	private String unit;
	
	private int num;
	
	private String goodsDetailImageKeys;
	
	private String erpNo;
	
	private String orderDetailStatus;
	
	private String closedStatus;
	private String remark;
	
	private GoodsDetail goodsDetail;
	private String shopId;
	private String shopName;
	private String customerName;
	private String orderCode;

	private String tenantName;//商户名称
	private String categoryName;//类别
	private String goodsColor;//颜色
	private String submitNum;//提交数量
	private String auditNum;//审核数量
	private String businessCheckerNum;//财务审核数量
	private String assignNum;//分配数量
	private String technicalFinishNum;//技术完成数量
	private String issuedNum;//下达数量
	private String finishNum;//完成数量
	private String sendNum; //发货数量

	private String prodFactory;//生产工厂

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getGoodsDetailId() {
		return goodsDetailId;
	}
	public void setGoodsDetailId(String goodsDetailId) {
		this.goodsDetailId = goodsDetailId;
	}
	public String getGoodsSn() {
		return goodsSn;
	}
	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getGoodsDetailImageKeys() {
		return goodsDetailImageKeys;
	}
	public void setGoodsDetailImageKeys(String goodsDetailImageKeys) {
		this.goodsDetailImageKeys = goodsDetailImageKeys;
	}
	public String getErpNo() {
		return erpNo;
	}
	public void setErpNo(String erpNo) {
		this.erpNo = erpNo;
	}
	public String getOrderDetailStatus() {
		return orderDetailStatus;
	}
	public void setOrderDetailStatus(String orderDetailStatus) {
		this.orderDetailStatus = orderDetailStatus;
	}
	public String getClosedStatus() {
		return closedStatus;
	}
	public void setClosedStatus(String closedStatus) {
		this.closedStatus = closedStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public GoodsDetail getGoodsDetail() {
		return goodsDetail;
	}
	public void setGoodsDetail(GoodsDetail goodsDetail) {
		this.goodsDetail = goodsDetail;
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
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getAuditNum() {
		return auditNum;
	}

	public void setAuditNum(String auditNum) {
		this.auditNum = auditNum;
	}

	public String getBusinessCheckerNum() {
		return businessCheckerNum;
	}

	public void setBusinessCheckerNum(String businessCheckerNum) {
		this.businessCheckerNum = businessCheckerNum;
	}

	public String getAssignNum() {
		return assignNum;
	}

	public void setAssignNum(String assignNum) {
		this.assignNum = assignNum;
	}

	public String getTechnicalFinishNum() {
		return technicalFinishNum;
	}

	public void setTechnicalFinishNum(String technicalFinishNum) {
		this.technicalFinishNum = technicalFinishNum;
	}

	public String getIssuedNum() {
		return issuedNum;
	}

	public void setIssuedNum(String issuedNum) {
		this.issuedNum = issuedNum;
	}

	public String getFinishNum() {
		return finishNum;
	}

	public void setFinishNum(String finishNum) {
		this.finishNum = finishNum;
	}

	public String getSendNum() {
		return sendNum;
	}

	public void setSendNum(String sendNum) {
		this.sendNum = sendNum;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getGoodsColor() {
		return goodsColor;
	}

	public void setGoodsColor(String goodsColor) {
		this.goodsColor = goodsColor;
	}

	public String getSubmitNum() {
		return submitNum;
	}

	public void setSubmitNum(String submitNum) {
		this.submitNum = submitNum;
	}

	public String getProdFactory() {
		return prodFactory;
	}

	public void setProdFactory(String prodFactory) {
		this.prodFactory = prodFactory;
	}
}
