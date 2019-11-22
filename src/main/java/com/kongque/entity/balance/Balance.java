package com.kongque.entity.balance;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.OneToMany;

import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "t_balance")
public class Balance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5271488730615872122L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;// 主键
	@Column(name = "c_payee")
	private String payee;// 收款方
	/**
	 * 商户id
	 */
	@Column(name = "c_tenant_id")
	private String tenantId;
	@Column(name = "c_payer")
	private String payer;// 付款方
	@Column(name = "c_payment")
	private String payment;// 付款方式.1:定金，2：现金，3：部分定金部分现金
	@Column(name = "c_settlement_amount")
	private String settlementAmount;// 结算金额
	@Column(name = "c_deduct_earnest")
	private String deductEarnest;// 本次扣除定金
	@Column(name = "c_amount_receivable")
	private String amountReceivable;// 本次应收金额
	@Column(name = "c_balance_time")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date balanceTime;// 结算时间
	@Column(name = "c_earnest_total")
	private String earnestTotal;// 当前定金总额
	@Column(name = "c_service_type")
	private String serviceType;// 业务类型（1：微调单 2：定制订单）
	@Column(name = "c_payment_days")
	private String paymentDays;// 账期
	@Column(name = "c_tax_info")
	private String taxInfo;// 税务相关信息
	@Column(name = "c_balance_numeber")
	private String balanceNumeber;// 结算单号
	@Column(name = "c_balance_status")
	private String balanceStatus;// 结算状态.1：待确认，2：已确认，3：已结算
	@Column(name = "c_orderrepair_source")
	private String orderrepairSource;// 微调单来源
	@Column(name = "c_remark")
	private String remark;// 备注
	@Column(name = "c_money_type")
	private String moneyType;// 货币类型：1：人民币 2：美元 3：台币
	@Column(name = "c_check_info")
	private String checkInfo;// 审核说明
	@Column(name = "c_company_id")
	private String companyId;// 分公司id
	@Column(name = "c_city")
	private String city;// 门店所属城市
	@Column(name = "c_unit_price")
	private Float unitPrice;// 单价
	@Column(name = "c_count")
	private Integer count;// 数量
	@Column(name = "c_create_user_id")
	private String createUserId;// 创建人id
	@Column(name = "c_create_time")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createTime;// 创建时间
	@Column(name = "c_update_user_id")
	private String updateUserId;// 修改人id
	@Column(name = "c_update_time")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date updateTime;// 修改时间
	@OneToMany(mappedBy = "balanceId", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private List<BalanceRepairRelation> balanceRepairRelations;//微调单关联

	public List<BalanceRepairRelation> getBalanceRepairRelations() {
		return balanceRepairRelations;
	}

	public void setBalanceRepairRelations(List<BalanceRepairRelation> balanceRepairRelations) {
		this.balanceRepairRelations = balanceRepairRelations;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(String settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public String getDeductEarnest() {
		return deductEarnest;
	}

	public void setDeductEarnest(String deductEarnest) {
		this.deductEarnest = deductEarnest;
	}

	public String getAmountReceivable() {
		return amountReceivable;
	}

	public void setAmountReceivable(String amountReceivable) {
		this.amountReceivable = amountReceivable;
	}

	public Date getBalanceTime() {
		return balanceTime;
	}

	public void setBalanceTime(Date balanceTime) {
		this.balanceTime = balanceTime;
	}

	public String getEarnestTotal() {
		return earnestTotal;
	}

	public void setEarnestTotal(String earnestTotal) {
		this.earnestTotal = earnestTotal;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getPaymentDays() {
		return paymentDays;
	}

	public void setPaymentDays(String paymentDays) {
		this.paymentDays = paymentDays;
	}

	public String getTaxInfo() {
		return taxInfo;
	}

	public void setTaxInfo(String taxInfo) {
		this.taxInfo = taxInfo;
	}

	public String getBalanceNumeber() {
		return balanceNumeber;
	}

	public void setBalanceNumeber(String balanceNumeber) {
		this.balanceNumeber = balanceNumeber;
	}

	public String getBalanceStatus() {
		return balanceStatus;
	}

	public void setBalanceStatus(String balanceStatus) {
		this.balanceStatus = balanceStatus;
	}

	public String getOrderrepairSource() {
		return orderrepairSource;
	}

	public void setOrderrepairSource(String orderrepairSource) {
		this.orderrepairSource = orderrepairSource;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMoneyType() {
		return moneyType;
	}

	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}

	public String getCheckInfo() {
		return checkInfo;
	}

	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}
