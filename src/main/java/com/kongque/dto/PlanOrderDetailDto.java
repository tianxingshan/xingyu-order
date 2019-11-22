package com.kongque.dto;

public class PlanOrderDetailDto {
	
	//@DateTimeFormat(pattern="yyyy-MM-dd")
	private String assignCreatetimeStr;
	
	//@DateTimeFormat(pattern="yyyy-MM-dd")
	private String assignCreatetimeEnd;
	
	//@DateTimeFormat(pattern="yyyy-MM-dd")
	private String technicalFinishedTimeStr;
	
	//@DateTimeFormat(pattern="yyyy-MM-dd")
	private String technicalFinishedTimeEnd;
	
	//@DateTimeFormat(pattern="yyyy-MM-dd")
	private String prodFinishTimeStr;
	
	//@DateTimeFormat(pattern="yyyy-MM-dd")
	private String prodFinishTimeEnd;
	
	//@DateTimeFormat(pattern="yyyy-MM-dd")
	private String prodTimeStr;
	
	//@DateTimeFormat(pattern="yyyy-MM-dd")
	private String prodTimeEnd;
	
	//@DateTimeFormat(pattern="yyyy-MM-dd")
	private String sendTimeStr;
	
	//@DateTimeFormat(pattern="yyyy-MM-dd")
	private String sendTimeEnd;
	
	//@DateTimeFormat(pattern="yyyy-MM-dd")
	private String submitTimeStr;
	
	//@DateTimeFormat(pattern="yyyy-MM-dd")
	private String submitTimeEnd;
	
	private String xingyuChekTimeStr;//星域审核时间开始
	
	private String xingyuChekTimeEnd;//星域审核时间结束
	
	private String erpNo;//POS单号
	
	private String planNumber;//计划单号
	
	private String orderCode;//订单号
	
	private String goodsCode;//订单号
	
	private String prodFactory;//生产工厂
	
	private String planStatus;//计划单状态
	
	private String customerName;//会员名称
	
	private String orderDetailStatus;//订单明细状态
	
	private String goodsColorName;//商品颜色
	
	private String goodsSn;//唯一码
	
	private String orderCharacter;//订单性质
	
	private String mesMeasureSizeName;//配码尺寸
	
	private String technicianName;//制版人

	private String technicianId;//制版人id
	
	private String closedStatus;//结案单状态
	
	private String publishers;//制版方
	
	private String[] goodsSns;//唯一码数组
	
	private String categoryId;//品类id

	private String embroidName; //绣字名

	private String statusBussiness;//订单状态

	private String  issueTimeStr;//下达日期
	private String  issueTimeEnd;//

	private Integer days;//时长

	private String flag;//标志
	/**
	 * 款式Id
	 */
	private String goodsId;
	/**
	 * 商户
	 */
	private String tenantId;

	public String getAssignCreatetimeStr() {
		return assignCreatetimeStr;
	}

	public void setAssignCreatetimeStr(String assignCreatetimeStr) {
		this.assignCreatetimeStr = assignCreatetimeStr;
	}

	public String getAssignCreatetimeEnd() {
		return assignCreatetimeEnd;
	}

	public void setAssignCreatetimeEnd(String assignCreatetimeEnd) {
		this.assignCreatetimeEnd = assignCreatetimeEnd;
	}

	public String getTechnicalFinishedTimeStr() {
		return technicalFinishedTimeStr;
	}

	public void setTechnicalFinishedTimeStr(String technicalFinishedTimeStr) {
		this.technicalFinishedTimeStr = technicalFinishedTimeStr;
	}

	public String getTechnicalFinishedTimeEnd() {
		return technicalFinishedTimeEnd;
	}

	public void setTechnicalFinishedTimeEnd(String technicalFinishedTimeEnd) {
		this.technicalFinishedTimeEnd = technicalFinishedTimeEnd;
	}

	public String getProdFinishTimeStr() {
		return prodFinishTimeStr;
	}

	public void setProdFinishTimeStr(String prodFinishTimeStr) {
		this.prodFinishTimeStr = prodFinishTimeStr;
	}

	public String getProdFinishTimeEnd() {
		return prodFinishTimeEnd;
	}

	public void setProdFinishTimeEnd(String prodFinishTimeEnd) {
		this.prodFinishTimeEnd = prodFinishTimeEnd;
	}

	public String getProdTimeStr() {
		return prodTimeStr;
	}

	public void setProdTimeStr(String prodTimeStr) {
		this.prodTimeStr = prodTimeStr;
	}

	public String getProdTimeEnd() {
		return prodTimeEnd;
	}

	public void setProdTimeEnd(String prodTimeEnd) {
		this.prodTimeEnd = prodTimeEnd;
	}

	public String getSendTimeStr() {
		return sendTimeStr;
	}

	public void setSendTimeStr(String sendTimeStr) {
		this.sendTimeStr = sendTimeStr;
	}

	public String getSendTimeEnd() {
		return sendTimeEnd;
	}

	public void setSendTimeEnd(String sendTimeEnd) {
		this.sendTimeEnd = sendTimeEnd;
	}

	public void setSubmitTimeStr(String submitTimeStr) {
		this.submitTimeStr = submitTimeStr;
	}

	public void setSubmitTimeEnd(String submitTimeEnd) {
		this.submitTimeEnd = submitTimeEnd;
	}

	public String getPlanNumber() {
		return planNumber;
	}

	public void setPlanNumber(String planNumber) {
		this.planNumber = planNumber;
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

	public String getProdFactory() {
		return prodFactory;
	}

	public void setProdFactory(String prodFactory) {
		this.prodFactory = prodFactory;
	}

	public String getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getOrderDetailStatus() {
		return orderDetailStatus;
	}

	public void setOrderDetailStatus(String orderDetailStatus) {
		this.orderDetailStatus = orderDetailStatus;
	}

	public String getGoodsColorName() {
		return goodsColorName;
	}

	public void setGoodsColorName(String goodsColorName) {
		this.goodsColorName = goodsColorName;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public String getOrderCharacter() {
		return orderCharacter;
	}

	public void setOrderCharacter(String orderCharacter) {
		this.orderCharacter = orderCharacter;
	}

	public String getMesMeasureSizeName() {
		return mesMeasureSizeName;
	}

	public void setMesMeasureSizeName(String mesMeasureSizeName) {
		this.mesMeasureSizeName = mesMeasureSizeName;
	}

	public String getTechnicianName() {
		return technicianName;
	}

	public void setTechnicianName(String technicianName) {
		this.technicianName = technicianName;
	}

	public String getClosedStatus() {
		return closedStatus;
	}

	public void setClosedStatus(String closedStatus) {
		this.closedStatus = closedStatus;
	}

	public String getPublishers() {
		return publishers;
	}

	public void setPublishers(String publishers) {
		this.publishers = publishers;
	}

	public String getSubmitTimeStr() {
		return submitTimeStr;
	}

	public String getSubmitTimeEnd() {
		return submitTimeEnd;
	}

	public String getErpNo() {
		return erpNo;
	}

	public void setErpNo(String erpNo) {
		this.erpNo = erpNo;
	}

	public String[] getGoodsSns() {
		return goodsSns;
	}

	public void setGoodsSns(String[] goodsSns) {
		this.goodsSns = goodsSns;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getXingyuChekTimeStr() {
		return xingyuChekTimeStr;
	}

	public void setXingyuChekTimeStr(String xingyuChekTimeStr) {
		this.xingyuChekTimeStr = xingyuChekTimeStr;
	}

	public String getXingyuChekTimeEnd() {
		return xingyuChekTimeEnd;
	}

	public void setXingyuChekTimeEnd(String xingyuChekTimeEnd) {
		this.xingyuChekTimeEnd = xingyuChekTimeEnd;
	}

	public String getEmbroidName() {
		return embroidName;
	}

	public void setEmbroidName(String embroidName) {
		this.embroidName = embroidName;
	}

	public String getStatusBussiness() {
		return statusBussiness;
	}

	public void setStatusBussiness(String statusBussiness) {
		this.statusBussiness = statusBussiness;
	}

	public String getIssueTimeStr() {
		return issueTimeStr;
	}

	public void setIssueTimeStr(String issueTimeStr) {
		this.issueTimeStr = issueTimeStr;
	}

	public String getIssueTimeEnd() {
		return issueTimeEnd;
	}

	public void setIssueTimeEnd(String issueTimeEnd) {
		this.issueTimeEnd = issueTimeEnd;
	}

	public String getTechnicianId() {
		return technicianId;
	}

	public void setTechnicianId(String technicianId) {
		this.technicianId = technicianId;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}
