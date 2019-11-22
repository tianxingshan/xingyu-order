package com.kongque.model;

import java.util.Date;
import java.util.List;

import com.kongque.entity.repair.OrderRepairCheck;

public class MesRepairProductionPlanModel {
	
	private String id;
	
	private String planCode;
	
	private Date planDate;
	
	private String status;
	
	private Date finishDate;
	
	private String num;
	
	private String operator;
	
	private Date createTime;
	
	private String confirmer;
	
	private Date confirmTime;
	
	private String releases;
	
	private Date releaseTime;
	
	private String del;
	
	private List<OrderRepairModel> orderRepairList;
	
	private List<OrderRepairCheck> checkList;

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

	public List<OrderRepairModel> getOrderRepairList() {
		return orderRepairList;
	}

	public void setOrderRepairList(List<OrderRepairModel> orderRepairList) {
		this.orderRepairList = orderRepairList;
	}

	public List<OrderRepairCheck> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<OrderRepairCheck> checkList) {
		this.checkList = checkList;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}
}
