package com.kongque.entity.basics;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "t_xiuyu_company")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Company implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2371830159232977934L;
	@Id
	@Column(name = "c_id")
	private String id;
	@Column(name = "c_unit_name")
	private String unitName;//单位名称
	@Column(name = "c_address")
	private String address;//公司地址
	@Column(name = "c_contact")
	private String contact;//联系人
	@Column(name = "c_phone_no")
	private String phoneNo;//电话
	@Column(name = "c_city")
	private String city;//城市
	@Column(name = "c_invoice_title")
	private String invoiceTitle;//发票抬头
	@Column(name = "c_taxpayer_no")
	private String taxpayerNo;//纳税人识别号
	@Column(name = "c_deposit_bank")
	private String depositBank;//开户银行
	@Column(name = "c_account_info")
	private String accountInfo;//账户信息
	@Column(name = "c_remark")
	private String remark;//备注
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getTaxpayerNo() {
		return taxpayerNo;
	}

	public void setTaxpayerNo(String taxpayerNo) {
		this.taxpayerNo = taxpayerNo;
	}

	public String getDepositBank() {
		return depositBank;
	}

	public void setDepositBank(String depositBank) {
		this.depositBank = depositBank;
	}

	public String getAccountInfo() {
		return accountInfo;
	}

	public void setAccountInfo(String accountInfo) {
		this.accountInfo = accountInfo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
