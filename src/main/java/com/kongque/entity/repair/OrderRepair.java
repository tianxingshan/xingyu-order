package com.kongque.entity.repair;

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
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_order_repair")
@DynamicInsert(true)
@DynamicUpdate(true)
public class OrderRepair implements Serializable {

	private static final long serialVersionUID = 259981227605420875L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	@Column(name = "c_sys_id")
	private String sysId;
	@Column(name = "c_order_repair_code")
	private String orderRepairCode;// 微调单号
	@Column(name = "c_order_code")
	private String orderCode;
	@Column(name="c_order_detail_id")
	private String orderDetailId;//订单详情id
	@Column(name="c_goods_detail_id")
	private String goodsDetailId;//商品详情id
	@Column(name = "c_ec_order_code")
	private String ecOrderCode;
	@Column(name = "c_customer_id")
	private String customerId;
	@Column(name = "c_customer_code")
	private String customerCode;// 会员卡号
	@Column(name = "c_customer_name")
	private String customerName;// 会员姓名
	@Column(name = "c_embroidered_name")
	private String embroideredName;
	@Column(name = "c_goods_code")
	private String goodsCode;
	@Column(name = "c_goods_name")
	private String goodsName;// 商品名称
	@Column(name = "c_goods_color")
	private String goodsColor;
	@Column(name = "c_num")
	private String num;
	@Column(name = "c_apply_time")
	private String applyTime;// 申请日期
	@Column(name = "c_order_repair_status")
	private String orderRepairStatus;// 微调单状态
	@Column(name = "c_is_extract")
	private String isExtract;// 顾客是否提取
	@Column(name = "c_extract_time")
	private String extractTime;
	@Column(name = "c_city")
	private String city;// 城市
	@Column(name = "c_shop_name")
	private String shopName;// 门店名称
	@Column(name = "c_is_in_warranty")
	private String isInWarranty;
	@Column(name = "c_frequency")
	private String frequency;
	@Column(name = "c_solution")
	private String solution;
	@Column(name = "c_express_number")
	private String expressNumber;// 物流单号
	@Column(name = "c_order_character")
	private String orderCharacter;// 订单性质
	@Column(name = "c_address")
	private String address;
	@Column(name = "c_repair_reason")
	private String repairReason;
	@Column(name = "c_repalcement_of_excipients")
	private String repalcementOfExcipients;
	@Column(name = "c_charge")
	private String charge;
	@Column(name = "c_charge_no")
	private String chargeNo;
	@Column(name = "c_repair_person")
	private String repairPerson;// 微调人
	@Column(name = "c_repair_person_phone")
	private String repairPersonPhone;
	@Column(name = "c_remark")
	private String remark;
	@Column(name = "c_trimming_json")
	private String trimmingJson;
	@Column(name = "c_tenant_id")
	private String tenantId;
	@Column(name = "c_shop_id")
	private String shopId;
	@Column(name = "c_extended_file_name")
	private String extendedFileName;
	@Column(name = "c_description")
	private String description;
	@Column(name = "c_owner")
	private String owner;
	@Column(name = "c_finish_date")
	private String finishDate;
	@Column(name = "c_finance_check_status")
	private String financeCheckStatus;
	@Column(name = "c_del")
	private String del;
	@Column(name = "c_create_time")
	private Date createTime;
	@Column(name = "c_update_time")
	private Date updateTime;
	@Column(name = "c_character")
	private String character;
	@Column(name = "c_check_time")
	private Date checkTime;
	// @OneToMany(mappedBy = "orderRepairId", cascade = CascadeType.PERSIST)
	@OneToMany(mappedBy = "orderRepairId", cascade = CascadeType.PERSIST)
	private List<OrderRepairCheck> checks;
	@OneToMany(mappedBy = "orderRepairId", cascade = CascadeType.PERSIST)
	private List<OrderRepairAttachment> attachments;

	@Column(name = "c_out_flag")
	private String outFlag;
	
	@Transient
	private String categoryName;
	@Transient
	private String categoryId;
	@Transient
	private String goodsSn;
	
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

	public List<OrderRepairCheck> getChecks() {
		return checks;
	}

	public void setChecks(List<OrderRepairCheck> checks) {
		this.checks = checks;
	}

	public List<OrderRepairAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<OrderRepairAttachment> attachments) {
		this.attachments = attachments;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getTrimmingJson() {
		return trimmingJson;
	}

	public void setTrimmingJson(String trimmingJson) {
		this.trimmingJson = trimmingJson;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
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

	public String getFinanceCheckStatus() {
		return financeCheckStatus;
	}

	public void setFinanceCheckStatus(String financeCheckStatus) {
		this.financeCheckStatus = financeCheckStatus;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
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

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getOutFlag() {
		return outFlag;
	}

	public void setOutFlag(String outFlag) {
		this.outFlag = outFlag;
	}
}