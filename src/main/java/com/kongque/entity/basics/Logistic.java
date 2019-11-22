package com.kongque.entity.basics;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;




@Entity
@Table(name = "t_logistic")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Logistic implements Serializable {
	
	private static final long serialVersionUID = 6883563041113327605L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	@Column(name = "c_tenant_id")
	private String tenantId;
	@Column(name = "c_express_company")
	private String expressCompany;//快递公司
	@Column(name = "c_express_number")
	private String expressNumber;//快递单号
	@Column(name = "c_express_price")
	private String expressPrice;//快递价格
	@Column(name = "c_send_time")
	private Date sendTime;//发货时间
	@Column(name = "c_delivery_time")
	private Date deliveryTime;//收货时间
	@Column(name = "c_logistic_type")
	private String logisticType;//收货   1      发货  0
	@Column(name = "c_sender")
	private String sender;//寄件人
	@Column(name = "c_sender_address")
	private String senderAddress;//寄件地址
	@Column(name = "c_receiver_address")
	private String receiverAddress;//收件地址
	@Column(name = "c_sender_phone")
	private String senderPhone;//寄件人电话
	@Column(name = "c_settlement_type")
	private String settlementType;//结算类型
	@Column(name = "c_receiver")
	private String receiver;//收件人
	@Column(name = "c_receiver_phone")
	private String receiverPhone;//收件人电话
	@Column(name = "c_order_type")
	private String orderType;//1.微调单 0.订单明细
	@Column(name = "c_shop_id")
	private String shopId;//门店id
	@Column(name = "c_shop_name")
	private String shopName;//门店名称
	@Column(name = "c_check_status")
	private String checkStatus;//审核状态0.未审核 1.审核通过
	@Column(name = "c_check_time")
	private Date checkTime;//审核时间
	@Column(name = "c_create_time")
	private Date createTime;//录入时间
	@Column(name = "c_check_user_id")
	private String checkUserId;//审核人id
	@Column(name = "c_note")
	private String note;//备注
	@Column(name = "c_logistic_status")
	private String logisticStatus;
	@Column(name = "c_delete_flag")
	private String deleteFlag;//是否删除
	@OneToMany(mappedBy = "logisticId",cascade = CascadeType.PERSIST)
	private List<LogisticOrder> list;
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
	public String getExpressCompany() {
		return expressCompany;
	}
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
	public String getExpressNumber() {
		return expressNumber;
	}
	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}
	public String getExpressPrice() {
		return expressPrice;
	}
	public void setExpressPrice(String expressPrice) {
		this.expressPrice = expressPrice;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Date getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getLogisticType() {
		return logisticType;
	}
	public void setLogisticType(String logisticType) {
		this.logisticType = logisticType;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSenderAddress() {
		return senderAddress;
	}
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getSenderPhone() {
		return senderPhone;
	}
	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}
	public String getSettlementType() {
		return settlementType;
	}
	public void setSettlementType(String settlementType) {
		this.settlementType = settlementType;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
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
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getCheckUserId() {
		return checkUserId;
	}
	public void setCheckUserId(String checkUserId) {
		this.checkUserId = checkUserId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getLogisticStatus() {
		return logisticStatus;
	}
	public void setLogisticStatus(String logisticStatus) {
		this.logisticStatus = logisticStatus;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public List<LogisticOrder> getList() {
		return list;
	}
	public void setList(List<LogisticOrder> list) {
		this.list = list;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
