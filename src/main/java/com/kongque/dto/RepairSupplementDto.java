package com.kongque.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.kongque.entity.productionRepair.MesRepairProductionPlanSupplementMateriel;

public class RepairSupplementDto {
	
	private String id;
	
	private String supplementSn;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date supplementTime;
	
	private String supplementStatus;
	
	private String repairPlanId;
	
	private String remark;
	
	private String createUserId;
	
	private Date createtime;
	
	private String updateUserId;
	
	private Date updateTime;
	
	private String confirmerUserId;
	
	private Date confirmertime;
	
	private String repairPlanCode;
	
	private String del;
	
	private List<MesRepairProductionPlanSupplementMateriel> materielList;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date supplementTimeStr;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date supplementTimeEnd;
	

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

	public Date getSupplementTimeStr() {
		return supplementTimeStr;
	}

	public void setSupplementTimeStr(Date supplementTimeStr) {
		this.supplementTimeStr = supplementTimeStr;
	}

	public Date getSupplementTimeEnd() {
		return supplementTimeEnd;
	}

	public void setSupplementTimeEnd(Date supplementTimeEnd) {
		this.supplementTimeEnd = supplementTimeEnd;
	}

	public String getRepairPlanCode() {
		return repairPlanCode;
	}

	public void setRepairPlanCode(String repairPlanCode) {
		this.repairPlanCode = repairPlanCode;
	}

	public List<MesRepairProductionPlanSupplementMateriel> getMaterielList() {
		return materielList;
	}

	public void setMaterielList(List<MesRepairProductionPlanSupplementMateriel> materielList) {
		this.materielList = materielList;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}
	
}
