package com.kongque.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class MesRepairProductionPlanDto {
	
	private String id;
	
	private String planCode;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date planDate;
	
	private String status;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date finishDate;
	
	private String num;
	
	private String operator;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createTime;
	
	private String confirmer;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date confirmTime;
	
	private String releases;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date releaseTime; 
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date planDateStr;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date planDateEnd;
	
	private String del;
	
	private String repairStatus;
	
	private List<RepairPlanDetailDto> detailList;
	
	private String repairOrderCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getConfirmer() {
		return confirmer;
	}

	public void setConfirmer(String confirmer) {
		this.confirmer = confirmer;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getReleases() {
		return releases;
	}

	public void setReleases(String releases) {
		this.releases = releases;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public List<RepairPlanDetailDto> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<RepairPlanDetailDto> detailList) {
		this.detailList = detailList;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public Date getPlanDateStr() {
		return planDateStr;
	}

	public void setPlanDateStr(Date planDateStr) {
		this.planDateStr = planDateStr;
	}

	public Date getPlanDateEnd() {
		return planDateEnd;
	}

	public void setPlanDateEnd(Date planDateEnd) {
		this.planDateEnd = planDateEnd;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public String getRepairStatus() {
		return repairStatus;
	}

	public void setRepairStatus(String repairStatus) {
		this.repairStatus = repairStatus;
	}

	public String getRepairOrderCode() {
		return repairOrderCode;
	}

	public void setRepairOrderCode(String repairOrderCode) {
		this.repairOrderCode = repairOrderCode;
	}
	
}
