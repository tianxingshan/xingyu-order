package com.kongque.dto;


/**
 * 页面的条件查询数据
 * 
 * @author Administrator
 *
 */
public class BalanceDto {

	/**
	 * 城市
	 */
	private String city;
	/**
	 * 门店
	 */
	private String shopId;
	/**
	 * 顾客姓名
	 */
	private String customerName;

	/**
	 * 产品
	 */
	private String styleName;
	/**
	 * 微调单号
	 */
	private String repairCode;
	/**
	 * 下单时间
	 */
	private String createTime;
	/**
	 * 发货时间
	 */
	private String sendTime;
	/**
	 * 起止时间
	 */
	private String startTime;
	private String endTime;
	/**
	 * 分公司id
	 */
	private String companyId;
	/**
	 * 分公司名
	 */
	private String unitName;
	/**
	 * 结算单id
	 */
	private String balanceId;
	/**
	 * 微调项目
	 */
	private String repairProject;
	/**
	 * 业务状态
	 */
	private String repairStatus;
	/**
	 * 财务审核状态
	 */
	private String repairCheckStatus;
	/**
	 * 结算单号
	 */
	private String balanceCode;
	/**
	 * 结算时间
	 */
	private String balanceTime;
	/**
	 * 结算起止时间
	 */
	private String balanceStartTime;
	private String balanceEndTime;

	/**
	 * 结算状态.1：待确认，2：已确认，3：已结算
	 */
	private String balanceStatus;
	/**
	 * 线下订单号
	 */
	private String lineOrderCode;
	/**
	 * 物料编码
	 */
	private String matterCode;
	/**
	 * 孔雀订单号
	 */
	private String orderCode;
	
	
	private String[] orderRepairIds;

	/**
	 * 商户
	 */
	private String tenantId;

	private String tenantName;

	private String payer;// 付款方
	/**
	 * 账期
	 */
	private String paymentDays;// 账期
	/**
	 * 结算单状态集合
	 */
	private String[] balanceStatuses;

	/**
	 * 订单性质
	 */
	private String orderCharacteres;

	/**
	 * 微调发货日期
	 */
	private String repairSendStartTime;
	private String repairSendEndTime;
	/**
	 * 时长
	 */
	private String startDays;
	private String endDays;

	/**
	 * 结算金额
	 */
	private String settlementAmountStart;
	private String settlementAmountEnd;

	/**
	 * 唯一码
	 */
	private String goodsSn;

	/**
	 * 发货日期
	 */
	private String sendStartTime;

	private String sendEndTime;
	/**
	 * 类别
	 */
	private String categoryId;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
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

	public String getRepairCode() {
		return repairCode;
	}

	public void setRepairCode(String repairCode) {
		this.repairCode = repairCode;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getBalanceId() {
		return balanceId;
	}

	public void setBalanceId(String balanceId) {
		this.balanceId = balanceId;
	}

	public String getRepairProject() {
		return repairProject;
	}

	public void setRepairProject(String repairProject) {
		this.repairProject = repairProject;
	}

	public String getRepairStatus() {
		return repairStatus;
	}

	public void setRepairStatus(String repairStatus) {
		this.repairStatus = repairStatus;
	}

	public String getRepairCheckStatus() {
		return repairCheckStatus;
	}

	public void setRepairCheckStatus(String repairCheckStatus) {
		this.repairCheckStatus = repairCheckStatus;
	}

	public String getBalanceCode() {
		return balanceCode;
	}

	public void setBalanceCode(String balanceCode) {
		this.balanceCode = balanceCode;
	}

	public String getBalanceTime() {
		return balanceTime;
	}

	public void setBalanceTime(String balanceTime) {
		this.balanceTime = balanceTime;
	}

	public String getBalanceStartTime() {
		return balanceStartTime;
	}

	public void setBalanceStartTime(String balanceStartTime) {
		this.balanceStartTime = balanceStartTime;
	}

	public String getBalanceEndTime() {
		return balanceEndTime;
	}

	public void setBalanceEndTime(String balanceEndTime) {
		this.balanceEndTime = balanceEndTime;
	}

	public String getBalanceStatus() {
		return balanceStatus;
	}

	public void setBalanceStatus(String balanceStatus) {
		this.balanceStatus = balanceStatus;
	}

	public String getLineOrderCode() {
		return lineOrderCode;
	}

	public void setLineOrderCode(String lineOrderCode) {
		this.lineOrderCode = lineOrderCode;
	}

	public String getMatterCode() {
		return matterCode;
	}

	public void setMatterCode(String matterCode) {
		this.matterCode = matterCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String[] getOrderRepairIds() {
		return orderRepairIds;
	}

	public void setOrderRepairIds(String[] orderRepairIds) {
		this.orderRepairIds = orderRepairIds;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getPaymentDays() {
		return paymentDays;
	}

	public void setPaymentDays(String paymentDays) {
		this.paymentDays = paymentDays;
	}

	public String[] getBalanceStatuses() {
		return balanceStatuses;
	}

	public void setBalanceStatuses(String[] balanceStatuses) {
		this.balanceStatuses = balanceStatuses;
	}

	public String getOrderCharacteres() {
		return orderCharacteres;
	}

	public void setOrderCharacteres(String orderCharacteres) {
		this.orderCharacteres = orderCharacteres;
	}

	public String getRepairSendStartTime() {
		return repairSendStartTime;
	}

	public void setRepairSendStartTime(String repairSendStartTime) {
		this.repairSendStartTime = repairSendStartTime;
	}

	public String getRepairSendEndTime() {
		return repairSendEndTime;
	}

	public void setRepairSendEndTime(String repairSendEndTime) {
		this.repairSendEndTime = repairSendEndTime;
	}

	public String getStartDays() {
		return startDays;
	}

	public void setStartDays(String startDays) {
		this.startDays = startDays;
	}

	public String getEndDays() {
		return endDays;
	}

	public void setEndDays(String endDays) {
		this.endDays = endDays;
	}

	public String getSettlementAmountStart() {
		return settlementAmountStart;
	}

	public void setSettlementAmountStart(String settlementAmountStart) {
		this.settlementAmountStart = settlementAmountStart;
	}

	public String getSettlementAmountEnd() {
		return settlementAmountEnd;
	}

	public void setSettlementAmountEnd(String settlementAmountEnd) {
		this.settlementAmountEnd = settlementAmountEnd;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
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

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
}
