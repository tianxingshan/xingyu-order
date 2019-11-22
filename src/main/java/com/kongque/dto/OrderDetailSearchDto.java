package com.kongque.dto;

public class OrderDetailSearchDto {
	
	private String orderCode; //订单编号
	
	private String orderCharacter; //订单性质
	
	private String customerName; //会员姓名
	
	private String city; //城市
	
	private String shopName;//门店
	
	private String statusBussiness;//订单状态
	
	private String reset;//订单类型
	
	private String statusBeforeProduce;//生产前审核状态
	
	private String statusBeforeSend;//发货前审核状态
	
	private String orderDetailStatus;//订单明细状态
	
	private String goodsName;//商品名
	
	private String goodsColorName;//商品颜色
	
	private String goodsSn;//唯一编码
	
	private String erpNo;//Erp订单号
	
	private String categoryId;//商品类别
	
	private String createTimeStart;//创建开始日期
	
	private String createTimeEnd;//创建结束日期
	
	private String sendTimeStart;//发货开始日期
	
	private String sendTimeEnd;//发货结束日期

	private String prodFactory;//生产工厂
	
	private  String[] ids;

	private String[] tenantIds;//商户ids

	private String flag;//标志

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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public String getStatusBussiness() {
		return statusBussiness;
	}

	public void setStatusBussiness(String statusBussiness) {
		this.statusBussiness = statusBussiness;
	}

	public String getReset() {
		return reset;
	}

	public void setReset(String reset) {
		this.reset = reset;
	}

	public String getStatusBeforeProduce() {
		return statusBeforeProduce;
	}

	public void setStatusBeforeProduce(String statusBeforeProduce) {
		this.statusBeforeProduce = statusBeforeProduce;
	}

	public String getStatusBeforeSend() {
		return statusBeforeSend;
	}

	public void setStatusBeforeSend(String statusBeforeSend) {
		this.statusBeforeSend = statusBeforeSend;
	}

	public String getOrderDetailStatus() {
		return orderDetailStatus;
	}

	public void setOrderDetailStatus(String orderDetailStatus) {
		this.orderDetailStatus = orderDetailStatus;
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

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public String getErpNo() {
		return erpNo;
	}

	public void setErpNo(String erpNo) {
		this.erpNo = erpNo;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getSendTimeStart() {
		return sendTimeStart;
	}

	public void setSendTimeStart(String sendTimeStart) {
		this.sendTimeStart = sendTimeStart;
	}

	public String getSendTimeEnd() {
		return sendTimeEnd;
	}

	public void setSendTimeEnd(String sendTimeEnd) {
		this.sendTimeEnd = sendTimeEnd;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String getProdFactory() {
		return prodFactory;
	}

	public void setProdFactory(String prodFactory) {
		this.prodFactory = prodFactory;
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
}
