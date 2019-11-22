package com.kongque.entity.productionRepair;

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
@Table(name = "mes_repair_production_plan_check")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MesRepairProductionPlanCheck implements Serializable{

	private static final long serialVersionUID = 8795072019045030758L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_repair_plan_id")
	private String repairPlanId;
	
	@Column(name = "c_status")
	private String status;
	
	@Column(name = "c_remark")
	private String remark;
	
	@Column(name = "c_check_user_id")
	private String checkUserId;
	
	@Column(name = "c_check_user_name")
	private String checkUserName;
	
	@Column(name = "c_check_time")
	private Date checkTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRepairPlanId() {
		return repairPlanId;
	}

	public void setRepairPlanId(String repairPlanId) {
		this.repairPlanId = repairPlanId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(String checkUserId) {
		this.checkUserId = checkUserId;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}
	
}
