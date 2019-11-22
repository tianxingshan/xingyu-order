package com.kongque.entity.order;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author sws
 *
 */
@Entity
@Table(name = "t_order_account")
@DynamicInsert(true)
@DynamicUpdate(true)
public class OrderAccount implements Serializable {

	private static final long serialVersionUID = -7765949893841516399L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_account_number")
	private String accountNumber;
	
	@Column(name = "c_account_month")
	private String accountMonth;
	
	@Column(name = "c_currency")
	private String currency;
	
	@Column(name = "c_account_person")
	private String accountPerson;
	
	@Column(name = "c_create_time")
	private Date createTime;
	
	@Column(name = "c_update_time")
	private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountMonth() {
		return accountMonth;
	}

	public void setAccountMonth(String accountMonth) {
		this.accountMonth = accountMonth;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getAccountPerson() {
		return accountPerson;
	}

	public void setAccountPerson(String accountPerson) {
		this.accountPerson = accountPerson;
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
	

	
	
}
