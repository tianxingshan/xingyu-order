package com.kongque.entity.repair;

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

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "t_order_repair_check")
@DynamicInsert(true)
@DynamicUpdate(true)
public class OrderRepairCheck implements Serializable {
	private static final long serialVersionUID = -6319910568625018059L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	@Column(name = "c_order_repair_id")
	private String orderRepairId;
	@Column(name = "c_check_status")
	private String checkStatus;
	@Column(name = "c_checker_name")
	private String checkerName;
	@Column(name = "c_check_time")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date checkTime;
	@Column(name = "c_check_instruction")
	private String checkInstruction;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getOrderRepairId() {
		return orderRepairId;
	}

	public void setOrderRepairId(String orderRepairId) {
		this.orderRepairId = orderRepairId;
	}

	public String getCheckInstruction() {
		return checkInstruction;
	}

	public void setCheckInstruction(String checkInstruction) {
		this.checkInstruction = checkInstruction;
	}

}