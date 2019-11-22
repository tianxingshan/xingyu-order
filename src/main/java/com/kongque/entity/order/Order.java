package com.kongque.entity.order;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.kongque.entity.user.User;

@Entity
@Table(name = "t_order")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Order implements Serializable{

	private static final long serialVersionUID = 2465070886300440519L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_tenant_id")
	private String tenantId;
	
	@Column(name = "c_sys_id")
	private String sysId;

	@Column(name = "c_order_code")
	private String orderCode;
	
	@Column(name = "c_order_character")
	private String orderCharacter;
	
	@Column(name = "c_customer_code")
	private String customerCode;
	
	@Column(name = "c_customer_name")
	private String customerName;
	
	@Column(name = "c_embroid_name")
	private String embroidName;
	
	@Column(name = "c_customer_birthday")
	private String customerBirthday;
	
	@Column(name = "c_customer_age")
	private String customerAge;
	
	@Column(name = "c_customer_height")
	private String customerHeight;
	
	@Column(name = "c_customer_weight")
	private String customerWeight;
	
	@Column(name = "c_customer_professional")
	private String customerProfessional;
	
	@Column(name = "c_customer_phone")
	private String customerPhone;
	
	@Column(name = "c_city")
	private String city;
	
	@Column(name = "c_shop_name")
	private String shopName;
	
	@Column(name = "c_measurer_name")
	private String measurerName;
	
	@Column(name = "c_measurer_phone")
	private String measurerPhone;
	
	@Column(name = "c_status_bussiness")
	private String statusBussiness;
	
	@Column(name = "c_status_before_produce")
	private String statusBeforeProduce;
	
	@Column(name = "c_status_before_send")
	private String statusBeforeSend;
	
	@Column(name = "c_recorder_name")
	private String recorderName;
	
	@Column(name = "c_business_checker_name")
	private String businessCheckerName;
	
	@Column(name = "c_business_checker_time")
	private Date businessCheckerTime;
	
	@Column(name = "c_ec_order_code")
	private String ecOrderCode;
	
	@Column(name = "c_reset")
	private String reset;
	
	@Column(name = "c_original_order_code")
	private String originalOrderCode;
	
	@Column(name = "c_original_goods_sn")
	private String originalGoodsSn;
	
	@Column(name = "c_receiving_address")
	private String receivingAddress;
	
	@Column(name = "c_create_time")
	private Date createTime;
	
	@Column(name = "c_update_time")
	private Date updateTime;
	
	@Column(name = "c_remark")
	private String remark;
	
	@Column(name = "c_customer_id")
	private String customerId;
	
	@Column(name = "c_shop_id")
	private String shopId;
	
	@Column(name = "c_create_user_id")
	private String createUserId;
	
	@Column(name = "c_update_user_id")
	private String updateUserId;
	
	@Column(name = "c_tryon_opinion")
	private String tryonOpinion;
	
	@Column(name = "c_tryon_code")
	private String tryonCode;
	
	@Column(name = "c_tryon_json")
	private String tryonJson;
	
	@Column(name = "c_delete_flag")
	private String deleteFlag;
	
	@Column(name = "c_xiuyu_chek_time")
	private Date xiuyuChekTime;
	
	@Column(name = "c_xingyu_chek_time")
	private Date xingyuChekTime;
	
	@Column(name = "c_submit_time")
	private Date submitTime;
	/**
	 * 外部订单标志
	 */
	@Column(name = "c_out_flag")
	private String outFlag;

	/**
	 * 订单工艺
	 */
	@Column(name = "c_order_process")
	private String orderProcess;
	
	@OneToMany(mappedBy = "orderId",cascade = CascadeType.PERSIST)
//	@OrderBy("order asc")
	private List<OrderDetail> orderDetailList;
	
	@ManyToOne
	@JoinColumn(name = "c_create_user_id",insertable =false, updatable =false)
	private User CreateUser;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
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

	public List<OrderDetail> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<OrderDetail> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

	public String getTryonOpinion() {
		return tryonOpinion;
	}

	public void setTryonOpinion(String tryonOpinion) {
		this.tryonOpinion = tryonOpinion;
	}

	public String getEmbroidName() {
		return embroidName;
	}

	public void setEmbroidName(String embroidName) {
		this.embroidName = embroidName;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Date getXiuyuChekTime() {
		return xiuyuChekTime;
	}

	public void setXiuyuChekTime(Date xiuyuChekTime) {
		this.xiuyuChekTime = xiuyuChekTime;
	}

	public Date getXingyuChekTime() {
		return xingyuChekTime;
	}

	public void setXingyuChekTime(Date xingyuChekTime) {
		this.xingyuChekTime = xingyuChekTime;
	}

	public User getCreateUser() {
		return CreateUser;
	}

	public void setCreateUser(User createUser) {
		CreateUser = createUser;
	}

	public Date getBusinessCheckerTime() {
		return businessCheckerTime;
	}

	public void setBusinessCheckerTime(Date businessCheckerTime) {
		this.businessCheckerTime = businessCheckerTime;
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

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getOutFlag() {
		return outFlag;
	}

	public void setOutFlag(String outFlag) {
		this.outFlag = outFlag;
	}

	public String getOrderProcess() {
		return orderProcess;
	}

	public void setOrderProcess(String orderProcess) {
		this.orderProcess = orderProcess;
	}
}
