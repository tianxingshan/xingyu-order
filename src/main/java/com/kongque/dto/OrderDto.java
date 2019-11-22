package com.kongque.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.kongque.entity.order.OrderDetail;

public class OrderDto {
	
	private String id;
	
	private String tenantId;

	private String orderCode;
	
	private String orderCharacter;
	
	private String customerCode;
	
	private String customerName;
	
	private String customerBirthday;
	
	private String customerAge;
	
	private String customerHeight;
	
	private String customerWeight;
	
	private String customerProfessional;
	
	private String customerPhone;
	
	private String city;
	
	private String shopName;
	
	private String measurerName;
	
	private String measurerPhone;
	
	private String statusBussiness;
	
	private String statusBeforeProduce;
	
	private String statusBeforeSend;
	
	private String recorderName;
	
	private String businessCheckerName;
	
	private String ecOrderCode;
	
	private String reset;
	
	private String originalOrderCode;
	
	private String originalGoodsSn;
	
	private String receivingAddress;
	
	private String createTime;
	
	private String updateTime;
	
	private String remark;
	
	private String customerId;
	
	private String shopId;
	
	private String createUserId;
	
	private String createUserName;
	
	private String updateUserId;
	
//	private String ossKey;
	
	private String fileName;
	
	private String tryonOpinion;//试衣说明
	
	private String tryonCode;//试穿尺码
	
	private String tryonJson;//试穿json
	
//	private String checkType;
//	
//	private String checkStatus;
//	
//	private String checkerName;
//	
//	private Date checkTime;
//	
//	private String checkInstruction;
	
	private List<OrderDetail> goodsDetailList;
	
//	private String goodsDetailId;
//	
//	private String goodsSn;
//	
//	private String goodsCode;
//	
//	private String goodsName;
//	
//	private String goodsColorName;
//	
//	private String unit;
//	
//	private int num;
//	
//	private String goodsDetailImageKeys;
//	
//	private String erpNo;
//	
//	private String orderDetailStatus;
//	
//	private String closedStatus;
	
	private String orderLanguage;
	
	private String orderMeasure;
	
	private String[] imageDelKeys;// 删除图片路径
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date crdateTimeBegin;//查询开始日期
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date crdateTimeEnd;//查询结束日期
	
	private String orderType;//状态（1：财务审核订单列表查询时传递）
	
	private String userId;//当前登录用户id
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date xiuyuCheckTimeStr;//秀域审核日期开始
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date xiuyuCheckTimeEnd;//秀域审核日期结束
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date xingyuCheckTimeStr;//星域审核日期开始
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date xingyuCheckTimeEnd;//星域审核日期结束
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date submitTimeStr;//提交日期开始
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date submitTimeEnd;//提交日期结束

	private String prodFactory;//生产工厂

	/**
	 * 量体模板
	 */
	private String orderMeasureTemplate;
	
	

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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerBirthday() {
		return customerBirthday;
	}

	public void setCustomerBirthday(String customerBirthday) {
		this.customerBirthday = customerBirthday;
	}

	public String getCustomerAge() {
		return customerAge;
	}

	public void setCustomerAge(String customerAge) {
		this.customerAge = customerAge;
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

	public String getMeasurerName() {
		return measurerName;
	}

	public void setMeasurerName(String measurerName) {
		this.measurerName = measurerName;
	}

	public String getMeasurerPhone() {
		return measurerPhone;
	}

	public void setMeasurerPhone(String measurerPhone) {
		this.measurerPhone = measurerPhone;
	}

	public String getStatusBussiness() {
		return statusBussiness;
	}

	public void setStatusBussiness(String statusBussiness) {
		this.statusBussiness = statusBussiness;
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

	public String getRecorderName() {
		return recorderName;
	}

	public void setRecorderName(String recorderName) {
		this.recorderName = recorderName;
	}

	public String getBusinessCheckerName() {
		return businessCheckerName;
	}

	public void setBusinessCheckerName(String businessCheckerName) {
		this.businessCheckerName = businessCheckerName;
	}

	public String getEcOrderCode() {
		return ecOrderCode;
	}

	public void setEcOrderCode(String ecOrderCode) {
		this.ecOrderCode = ecOrderCode;
	}

	public String getReset() {
		return reset;
	}

	public void setReset(String reset) {
		this.reset = reset;
	}

	public String getOriginalOrderCode() {
		return originalOrderCode;
	}

	public void setOriginalOrderCode(String originalOrderCode) {
		this.originalOrderCode = originalOrderCode;
	}

	public String getReceivingAddress() {
		return receivingAddress;
	}

	public void setReceivingAddress(String receivingAddress) {
		this.receivingAddress = receivingAddress;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<OrderDetail> getGoodsDetailList() {
		return goodsDetailList;
	}

	public void setGoodsDetailList(List<OrderDetail> goodsDetailList) {
		this.goodsDetailList = goodsDetailList;
	}

	public String getOrderLanguage() {
		return orderLanguage;
	}

	public void setOrderLanguage(String orderLanguage) {
		this.orderLanguage = orderLanguage;
	}

	public String getOrderMeasure() {
		return orderMeasure;
	}

	public void setOrderMeasure(String orderMeasure) {
		this.orderMeasure = orderMeasure;
	}

	public String[] getImageDelKeys() {
		return imageDelKeys;
	}

	public void setImageDelKeys(String[] imageDelKeys) {
		this.imageDelKeys = imageDelKeys;
	}

	public Date getCrdateTimeBegin() {
		return crdateTimeBegin;
	}

	public void setCrdateTimeBegin(Date crdateTimeBegin) {
		this.crdateTimeBegin = crdateTimeBegin;
	}

	public Date getCrdateTimeEnd() {
		return crdateTimeEnd;
	}

	public void setCrdateTimeEnd(Date crdateTimeEnd) {
		this.crdateTimeEnd = crdateTimeEnd;
	}

	public String getTryonOpinion() {
		return tryonOpinion;
	}

	public void setTryonOpinion(String tryonOpinion) {
		this.tryonOpinion = tryonOpinion;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getTryonCode() {
		return tryonCode;
	}

	public void setTryonCode(String tryonCode) {
		this.tryonCode = tryonCode;
	}

	public String getTryonJson() {
		return tryonJson;
	}

	public void setTryonJson(String tryonJson) {
		this.tryonJson = tryonJson;
	}

	public String getOriginalGoodsSn() {
		return originalGoodsSn;
	}

	public void setOriginalGoodsSn(String originalGoodsSn) {
		this.originalGoodsSn = originalGoodsSn;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getXiuyuCheckTimeStr() {
		return xiuyuCheckTimeStr;
	}

	public void setXiuyuCheckTimeStr(Date xiuyuCheckTimeStr) {
		this.xiuyuCheckTimeStr = xiuyuCheckTimeStr;
	}

	public Date getXiuyuCheckTimeEnd() {
		return xiuyuCheckTimeEnd;
	}

	public void setXiuyuCheckTimeEnd(Date xiuyuCheckTimeEnd) {
		this.xiuyuCheckTimeEnd = xiuyuCheckTimeEnd;
	}

	public Date getXingyuCheckTimeStr() {
		return xingyuCheckTimeStr;
	}

	public void setXingyuCheckTimeStr(Date xingyuCheckTimeStr) {
		this.xingyuCheckTimeStr = xingyuCheckTimeStr;
	}

	public Date getXingyuCheckTimeEnd() {
		return xingyuCheckTimeEnd;
	}

	public void setXingyuCheckTimeEnd(Date xingyuCheckTimeEnd) {
		this.xingyuCheckTimeEnd = xingyuCheckTimeEnd;
	}

	public Date getSubmitTimeStr() {
		return submitTimeStr;
	}

	public void setSubmitTimeStr(Date submitTimeStr) {
		this.submitTimeStr = submitTimeStr;
	}

	public Date getSubmitTimeEnd() {
		return submitTimeEnd;
	}

	public void setSubmitTimeEnd(Date submitTimeEnd) {
		this.submitTimeEnd = submitTimeEnd;
	}

	public String getProdFactory() {
		return prodFactory;
	}

	public void setProdFactory(String prodFactory) {
		this.prodFactory = prodFactory;
	}

	public String getOrderMeasureTemplate() {
		return orderMeasureTemplate;
	}

	public void setOrderMeasureTemplate(String orderMeasureTemplate) {
		this.orderMeasureTemplate = orderMeasureTemplate;
	}
}
