package com.kongque.entity.productionRepair;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "mes_repair_production_plan_supplement")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MesRepairProductionPlanSupplement implements Serializable{

	private static final long serialVersionUID = 2808621193859913178L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_supplement_sn")
	private String supplementSn;
	
	@Column(name = "c_supplement_time")
	private Date supplementTime;
	
	@Column(name = "c_supplement_status")
	private String supplementStatus;
	
	@Column(name = "c_repairPlan_id")
	private String repairPlanId;
	
	@Column(name = "c_remark")
	private String remark;
	
	@Column(name = "c_create_user_id")
	private String createUserId;
	
	@Column(name = "c_create_time")
	private Date createtime;
	
	@Column(name = "c_update_user_id")
	private String updateUserId;
	
	@Column(name = "c_update_time")
	private Date updateTime;
	
	@Column(name = "c_confirmer_user_id")
	private String confirmerUserId;
	
	@Column(name = "c_confirmer_time")
	private Date confirmertime;
	
	@Column(name = "c_del")
	private String del;
	
	@ManyToOne
	@JoinColumn(name = "c_repairPlan_id",insertable =false, updatable =false)
	private MesRepairProductionPlan repairPlan;
	
	@Transient
	private String repairPlanCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSupplementSn() {
		return supplementSn;
	}

	public void setSupplementSn(String supplementSn) {
		this.supplementSn = supplementSn;
	}

	public Date getSupplementTime() {
		return supplementTime;
	}

	public void setSupplementTime(Date supplementTime) {
		this.supplementTime = supplementTime;
	}

	public String getSupplementStatus() {
		return supplementStatus;
	}

	public void setSupplementStatus(String supplementStatus) {
		this.supplementStatus = supplementStatus;
	}

	public String getRepairPlanId() {
		return repairPlanId;
	}

	public void setRepairPlanId(String repairPlanId) {
		this.repairPlanId = repairPlanId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
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

	public String getConfirmerUserId() {
		return confirmerUserId;
	}

	public void setConfirmerUserId(String confirmerUserId) {
		this.confirmerUserId = confirmerUserId;
	}

	public Date getConfirmertime() {
		return confirmertime;
	}

	public void setConfirmertime(Date confirmertime) {
		this.confirmertime = confirmertime;
	}

	public MesRepairProductionPlan getRepairPlan() {
		return repairPlan;
	}

	public void setRepairPlan(MesRepairProductionPlan repairPlan) {
		this.repairPlan = repairPlan;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public String getRepairPlanCode() {
		return repairPlanCode;
	}

	public void setRepairPlanCode(String repairPlanCode) {
		this.repairPlanCode = repairPlanCode;
	}
	
}
