package com.kongque.entity.balance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_balance_repair_relation")
public class BalanceRepairRelation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2684127320332615470L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;// 微调单和结算凭证关联表id
	@Column(name = "c_balance_id")
	private String balanceId;// 结算凭证id
	@Column(name = "c_repair_id")
	private String repairId;// 微调单id
	@Column(name = "c_line_order_code")
	private String lineOrderCode;// 线下订单号
	@Column(name = "c_matter_code")
	private String matterCode;// 物料编码
	@Column(name = "c_unit_price")
	private Float unitPrice;// 单价
	@Column(name = "c_amount")
	private Float amount;// 金额

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBalanceId() {
		return balanceId;
	}

	public void setBalanceId(String balanceId) {
		this.balanceId = balanceId;
	}

	public String getRepairId() {
		return repairId;
	}

	public void setRepairId(String repairId) {
		this.repairId = repairId;
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

	public Float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

}
