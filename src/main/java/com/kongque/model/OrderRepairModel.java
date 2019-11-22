package com.kongque.model;

import java.util.List;

import com.kongque.entity.repair.OrderRepairCheck;

public class OrderRepairModel {
	private String id;
	private String tenantName;//商户名称
	private String orderRepairCode;// 微调单号
	private String customerName;// 会员姓名
	private String orderCode;	//订单号
	private String orderCharacter;// 订单性质
	private String customerCode;// 会员卡号
	private String city;// 城市
	private String shopName;// 门店名称
	private String goodsName;// 商品名称
	private String goodsCode;//商品编码
	private String goodsSn;//商品唯一标识
	private String goodsColor;//颜色
	private String num;//数量
	private String solution;//处理方案
	private String orderRepairStatus;// 微调单状态
	private String sendExpressNumber;// 发货单号
	private String receiveExpressNumber;// 收货单号
	private String repairPerson;// 微调人
	private String isExtract;// 顾客是否提取
	private String checkTime;
	private String companyName;//分公司名称
	private String extendedFileName;
	private String description;
	private String sendTime;//发货时间
	
	private String trimmingJson;//微调内容
	private String repalcementOfExcipients;//更换辅料
	private String repairReason;//微调原因
	private String remark;//微调备注
	private String repairOpinion;//微调建议
	private String repairFeedback;//实调内容
	
	private List<OrderRepairCheck> checkList;

	private String categoryName;//分类明细

	private String orderType;//类型
	private String prodFactory;//生产工厂
	private String publishers;//制版方
	private String technicianName;//制版人
	private String frequency;//第几次微调
	private String days;//天数
	private String deliveryTime;//收货日期

	private String auditNum;//审核数量
	private String receivedNum;//收货数量
	private String issuedNum;//下达数量
	private String finishNum;//完成数量
	private String sendNum; //发货数量


	
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getExtendedFileName() {
		return extendedFileName;
	}
	public void setExtendedFileName(String extendedFileName) {
		this.extendedFileName = extendedFileName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderRepairCode() {
		return orderRepairCode;
	}
	public void setOrderRepairCode(String orderRepairCode) {
		this.orderRepairCode = orderRepairCode;
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

	public String getOrderCharacter() {
		return orderCharacter;
	}
	public void setOrderCharacter(String orderCharacter) {
		this.orderCharacter = orderCharacter;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getOrderRepairStatus() {
		return orderRepairStatus;
	}
	public void setOrderRepairStatus(String orderRepairStatus) {
		this.orderRepairStatus = orderRepairStatus;
	}
	public String getSendExpressNumber() {
		return sendExpressNumber;
	}
	public void setSendExpressNumber(String sendExpressNumber) {
		this.sendExpressNumber = sendExpressNumber;
	}
	public String getReceiveExpressNumber() {
		return receiveExpressNumber;
	}
	public void setReceiveExpressNumber(String receiveExpressNumber) {
		this.receiveExpressNumber = receiveExpressNumber;
	}
	public String getRepairPerson() {
		return repairPerson;
	}
	public void setRepairPerson(String repairPerson) {
		this.repairPerson = repairPerson;
	}
	public String getIsExtract() {
		return isExtract;
	}
	public void setIsExtract(String isExtract) {
		this.isExtract = isExtract;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getGoodsSn() {
		return goodsSn;
	}
	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}
	public String getGoodsColor() {
		return goodsColor;
	}
	public void setGoodsColor(String goodsColor) {
		this.goodsColor = goodsColor;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public String getTrimmingJson() {
		return trimmingJson;
	}
	public void setTrimmingJson(String trimmingJson) {
		this.trimmingJson = trimmingJson;
	}
	public String getRepalcementOfExcipients() {
		return repalcementOfExcipients;
	}
	public void setRepalcementOfExcipients(String repalcementOfExcipients) {
		this.repalcementOfExcipients = repalcementOfExcipients;
	}
	public String getRepairReason() {
		return repairReason;
	}
	public void setRepairReason(String repairReason) {
		this.repairReason = repairReason;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRepairOpinion() {
		return repairOpinion;
	}
	public void setRepairOpinion(String repairOpinion) {
		this.repairOpinion = repairOpinion;
	}
	public String getRepairFeedback() {
		return repairFeedback;
	}
	public void setRepairFeedback(String repairFeedback) {
		this.repairFeedback = repairFeedback;
	}
	public List<OrderRepairCheck> getCheckList() {
		return checkList;
	}
	public void setCheckList(List<OrderRepairCheck> checkList) {
		this.checkList = checkList;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getProdFactory() {
		return prodFactory;
	}

	public void setProdFactory(String prodFactory) {
		this.prodFactory = prodFactory;
	}

	public String getPublishers() {
		return publishers;
	}

	public void setPublishers(String publishers) {
		this.publishers = publishers;
	}

	public String getTechnicianName() {
		return technicianName;
	}

	public void setTechnicianName(String technicianName) {
		this.technicianName = technicianName;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getAuditNum() {
		return auditNum;
	}

	public void setAuditNum(String auditNum) {
		this.auditNum = auditNum;
	}

	public String getReceivedNum() {
		return receivedNum;
	}

	public void setReceivedNum(String receivedNum) {
		this.receivedNum = receivedNum;
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


}
