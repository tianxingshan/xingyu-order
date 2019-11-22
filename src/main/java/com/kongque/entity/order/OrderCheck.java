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

@Entity
@Table(name = "t_order_check")
@DynamicInsert(true)
@DynamicUpdate(true)
public class OrderCheck implements Serializable{

	private static final long serialVersionUID = 966398664171076494L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_order_id")
	private String orderId;
	
	@Column(name = "c_check_type")
	private String checkType;
	
	@Column(name = "c_check_status")
	private String checkStatus;
	
	@Column(name = "c_checker_name")
	private String checkerName;
	
	@Column(name = "c_check_time")
	private Date checkTime;
	
	@Column(name = "c_check_instruction")
	private String checkInstruction;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCheckerName() {
		return checkerName;
	}

	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getCheckInstruction() {
		return checkInstruction;
	}

	public void setCheckInstruction(String checkInstruction) {
		this.checkInstruction = checkInstruction;
	}
	
	


}
