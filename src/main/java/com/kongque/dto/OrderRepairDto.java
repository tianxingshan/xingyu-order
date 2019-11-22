package com.kongque.dto;

import com.kongque.entity.repair.OrderRepairAttachment;

import java.util.List;

public class OrderRepairDto {
	private String id;
	private String orderRepairCode;// 微调单号
	private String orderCode;// 孔雀订单号
	private String ecOrderCode;// EC订单号
	private String customerId;// 会员id
	private String customerCode;// 会员卡号
	private String customerName;// 会员姓名
	private String embroideredName;// 绣字名
	private String customerInfo;// 会员名或会员卡号
	private String goodsCode;// 商品编码
	private String goodsName;// 商品名称
	private String goodsColor;// 商品颜色
	private String num;// 数量
	private String applyTime;// 申请日期
	private String orderRepairStatus;// 微调单状态
	private String isExtract;// 顾客是否提取
	private String extractTime;// 提取时间
	private String city;// 城市
	private String shopName;// 门店名称
	private String isInWarranty;// 是否在保修期（0：否，1：是）
	private String frequency;// 第几次微调
	private String solution;// 处理方案
	private String expressNumber;// 物流单号
	private String orderCharacter;// 订单性质
	private String address;// 收货地址
	private String repairReason;// 微调原因
	private String repalcementOfExcipients;// 更换辅料
	private String charge;// 收费金额
	private String chargeNo;// 收费单号
	private String repairPerson;// 微调人
	private String repairPersonPhone;// 微调人联系电话
	private String remark;// 备注
	private String trimmingJson;// 微调内容json
	private String tenantId;// 商户id
	private String shopId;// 门店id
	private String startCheckTime;// 审核开始时间
	private String endCheckTime;// 审核结束时间
	private String[] imageKeys;// 图片路径
	private String imageDelKeys;// 删除图片路径
	private String sendStartTime;// 发货时间开始
	private String sendEndTime;// 发货时间结束
	private String companyId;//分公司id
	private String extendedFileName;
	private String description;
	private String orderDetailId;//订单详情id
	private String[] ids;
	 
	private String userId;
	
	private String goodsDetailId;//商品详情id

	//收货日期
	private String deliveryStartTime;
	private String deliveryEndTime;

	private String prodFactory;//生产工厂
	private String publishers;//制版方
	private String technicianId;//制版人id

	private String categoryId;//品类id

	private String dateStart;//日期开始
	private String dateEnd;//日期截止

	private Boolean isExportExcel;//是否导出EXCEL标志

	private String[] tenantIds;

	private String flag;

	/**
	 * 商户名称
	 */
	private String tenantName;

	/**
	 * 会员生日
	 */
	private String customerBirthday;

	/**
	 * 会员身高
	 */
	private String customerHeight;

	/**
	 * 会员体重
	 */
	private String customerWeight;

	/**
	 * 会员职业
	 */
	private String customerProfessional;

	/**
	 * 会员电话,收货地址电话
	 */
	private String customerPhone;

	private List<OrderRepairAttachment> attachments;


	public String getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getSendStartTime() {
		return sendStartTime;
	}

	public void setSendStartTime(String sendStartTime) {
		this.sendStartTime = sendStartTime;
	}

	

	public String getSendEndTime() {
		return sendEndTime;
	}

	public void setSendEndTime(String sendEndTime) {
		this.sendEndTime = sendEndTime;
	}

	public String[] getImageKeys() {
		return imageKeys;
	}

	public void setImageKeys(String[] imageKeys) {
		this.imageKeys = imageKeys;
	}

	
	public String getImageDelKeys() {
		return imageDelKeys;
	}

	public void setImageDelKeys(String imageDelKeys) {
		this.imageDelKeys = imageDelKeys;
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

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getEcOrderCode() {
		return ecOrderCode;
	}

	public void setEcOrderCode(String ecOrderCode) {
		this.ecOrderCode = ecOrderCode;
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

	public String getEmbroideredName() {
		return embroideredName;
	}

	public void setEmbroideredName(String embroideredName) {
		this.embroideredName = embroideredName;
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

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getOrderRepairStatus() {
		return orderRepairStatus;
	}

	public void setOrderRepairStatus(String orderRepairStatus) {
		this.orderRepairStatus = orderRepairStatus;
	}

	public String getIsExtract() {
		return isExtract;
	}

	public void setIsExtract(String isExtract) {
		this.isExtract = isExtract;
	}

	public String getExtractTime() {
		return extractTime;
	}

	public void setExtractTime(String extractTime) {
		this.extractTime = extractTime;
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

	public String getIsInWarranty() {
		return isInWarranty;
	}

	public void setIsInWarranty(String isInWarranty) {
		this.isInWarranty = isInWarranty;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}

	public String getOrderCharacter() {
		return orderCharacter;
	}

	public void setOrderCharacter(String orderCharacter) {
		this.orderCharacter = orderCharacter;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRepairReason() {
		return repairReason;
	}

	public void setRepairReason(String repairReason) {
		this.repairReason = repairReason;
	}

	public String getRepalcementOfExcipients() {
		return repalcementOfExcipients;
	}

	public void setRepalcementOfExcipients(String repalcementOfExcipients) {
		this.repalcementOfExcipients = repalcementOfExcipients;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public String getChargeNo() {
		return chargeNo;
	}

	public void setChargeNo(String chargeNo) {
		this.chargeNo = chargeNo;
	}

	public String getRepairPerson() {
		return repairPerson;
	}

	public void setRepairPerson(String repairPerson) {
		this.repairPerson = repairPerson;
	}

	public String getRepairPersonPhone() {
		return repairPersonPhone;
	}

	public void setRepairPersonPhone(String repairPersonPhone) {
		this.repairPersonPhone = repairPersonPhone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTrimmingJson() {
		return trimmingJson;
	}

	public void setTrimmingJson(String trimmingJson) {
		this.trimmingJson = trimmingJson;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getStartCheckTime() {
		return startCheckTime;
	}

	public void setStartCheckTime(String startCheckTime) {
		this.startCheckTime = startCheckTime;
	}

	public String getEndCheckTime() {
		return endCheckTime;
	}

	public void setEndCheckTime(String endCheckTime) {
		this.endCheckTime = endCheckTime;
	}

	public String getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(String customerInfo) {
		this.customerInfo = customerInfo;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGoodsDetailId() {
		return goodsDetailId;
	}

	public void setGoodsDetailId(String goodsDetailId) {
		this.goodsDetailId = goodsDetailId;
	}

	public String getDeliveryStartTime() {
		return deliveryStartTime;
	}

	public void setDeliveryStartTime(String deliveryStartTime) {
		this.deliveryStartTime = deliveryStartTime;
	}

	public String getDeliveryEndTime() {
		return deliveryEndTime;
	}

	public void setDeliveryEndTime(String deliveryEndTime) {
		this.deliveryEndTime = deliveryEndTime;
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

	public String getTechnicianId() {
		return technicianId;
	}

	public void setTechnicianId(String technicianId) {
		this.technicianId = technicianId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public Boolean getExportExcel() {
		return isExportExcel;
	}

	public void setExportExcel(Boolean exportExcel) {
		isExportExcel = exportExcel;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String[] getTenantIds() {
		return tenantIds;
	}

	public void setTenantIds(String[] tenantIds) {
		this.tenantIds = tenantIds;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getCustomerBirthday() {
		return customerBirthday;
	}

	public void setCustomerBirthday(String customerBirthday) {
		this.customerBirthday = customerBirthday;
	}

	public String getCustomerHeight() {
		return customerHeight;
	}

	public void setCustomerHeight(String customerHeight) {
		this.customerHeight = customerHeight;
	}

	public String getCustomerWeight() {
		return customerWeight;
	}

	public void setCustomerWeight(String customerWeight) {
		this.customerWeight = customerWeight;
	}

	public String getCustomerProfessional() {
		return customerProfessional;
	}

	public void setCustomerProfessional(String customerProfessional) {
		this.customerProfessional = customerProfessional;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public List<OrderRepairAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<OrderRepairAttachment> attachments) {
		this.attachments = attachments;
	}
}
