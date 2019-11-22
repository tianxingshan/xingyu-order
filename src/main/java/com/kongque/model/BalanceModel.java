package com.kongque.model;

public class BalanceModel {
	private String num;

	/**
	 * 城市
	 */
	private String city;
	/**
	 * 门店
	 */
	private String shopName;
	/**
	 * 分公司名
	 */
	private String unitName;
	/**
	 * 顾客姓名
	 */
	private String customerName;
	/**
	 * 微调项目
	 */
	private String repairProject;
	/**
	 * 款式名
	 */
	private String styleName;
	/**
	 * 款式码
	 */
	private String styleCode;

	/**
	 * 件数
	 */
	private String number;
	/**
	 * 颜色
	 */
	private String color;
	/**
	 * 微调单号
	 */
	private String repairCode;
	/**
	 * 结算时间
	 */
	private String balanceTime;

	/**
	 * 微调单创建时间
	 */
	private String createTime;

	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 单据状态
	 */
	private String repairStatus;
	/**
	 * 财务审核状态
	 */
	private String checkStatus;
	/**
	 * 微调单id
	 */
	private String repairId;

	/**
	 * 物流单删除标识 0删除 1正常
	 */
	private String logisticdeleteflag;
	/**
	 * 店铺id
	 */
	private String shopId;
	/**
	 * 分公司id
	 */
	private String companyId;
	/**
	 * 结算单号
	 */
	private String balanceNumeber;
	/**
	 * 结算单id
	 */
	private String balanceId;
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
	/**
	 * 单价
	 */
	private String unitPrice;
	/**
	 * 金额
	 */
	private String amount;
	/**
	 * 发货时间
	 */
	private String sendTime;
	/**
	 * 收货时间
	 */
	private String expressTime;
	/**
	 * 物流单号
	 */
	private String expressNumber;
	
	
	/**
	 * 后期改动后新增的字段
	 */
	private String orderRepairCode;// 微调单号
	private String orderCharacter;// 订单性质
	private String goodsName;// 商品名称
	private String goodsCode;//商品编码
	private String goodsSn;//商品唯一标识
	private String goodsColor;//颜色
	private String orderRepairStatus;// 微调单状态
	private String companyName;//分公司名称

	/**
	 * 生产工厂
	 */
	private String prodFactory;

	/**
	 * 商户
	 */
	private String tenantId;
	private String tenantName;

	private String payer;// 付款方
	private String paymentDays;// 账期
	/**
	 * 微调审核日期
	 */
	private String repairAuditTime;
	/**
	 * 类别名称
	 */
	private String categoryName;
	/**
	 * 第几次微调
	 */
	private String frequency;
	/**
	 * 出版方
	 */
	private String publishers;
	/**
	 * 订单发货日期
	 */
	private String orderSendTime;
	/**
	 * 时长
	 */
	private String days;

	public String getOrderRepairCode() {
		return orderRepairCode;
	}

	public void setOrderRepairCode(String orderRepairCode) {
		this.orderRepairCode = orderRepairCode;
	}

	public String getOrderCharacter() {
		return orderCharacter;
	}

	public void setOrderCharacter(String orderCharacter) {
		this.orderCharacter = orderCharacter;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
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

	public String getOrderRepairStatus() {
		return orderRepairStatus;
	}

	public void setOrderRepairStatus(String orderRepairStatus) {
		this.orderRepairStatus = orderRepairStatus;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getRepairProject() {
		return repairProject;
	}

	public void setRepairProject(String repairProject) {
		this.repairProject = repairProject;
	}

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public String getStyleCode() {
		return styleCode;
	}

	public void setStyleCode(String styleCode) {
		this.styleCode = styleCode;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getRepairCode() {
		return repairCode;
	}

	public void setRepairCode(String repairCode) {
		this.repairCode = repairCode;
	}

	public String getBalanceTime() {
		return balanceTime;
	}

	public void setBalanceTime(String balanceTime) {
		this.balanceTime = balanceTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRepairStatus() {
		return repairStatus;
	}

	public void setRepairStatus(String repairStatus) {
		this.repairStatus = repairStatus;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getLogisticdeleteflag() {
		return logisticdeleteflag;
	}

	public void setLogisticdeleteflag(String logisticdeleteflag) {
		this.logisticdeleteflag = logisticdeleteflag;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getBalanceNumeber() {
		return balanceNumeber;
	}

	public void setBalanceNumeber(String balanceNumeber) {
		this.balanceNumeber = balanceNumeber;
	}

	public String getBalanceId() {
		return balanceId;
	}

	public void setBalanceId(String balanceId) {
		this.balanceId = balanceId;
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

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getExpressTime() {
		return expressTime;
	}

	public void setExpressTime(String expressTime) {
		this.expressTime = expressTime;
	}

	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}

	public String getRepairId() {
		return repairId;
	}

	public void setRepairId(String repairId) {
		this.repairId = repairId;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getProdFactory() {
		return prodFactory;
	}

	public void setProdFactory(String prodFactory) {
		this.prodFactory = prodFactory;
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

	public String getRepairAuditTime() {
		return repairAuditTime;
	}

	public void setRepairAuditTime(String repairAuditTime) {
		this.repairAuditTime = repairAuditTime;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getPublishers() {
		return publishers;
	}

	public void setPublishers(String publishers) {
		this.publishers = publishers;
	}

	public String getOrderSendTime() {
		return orderSendTime;
	}

	public void setOrderSendTime(String orderSendTime) {
		this.orderSendTime = orderSendTime;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}
}
