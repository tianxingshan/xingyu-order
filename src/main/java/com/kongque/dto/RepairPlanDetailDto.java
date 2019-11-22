package com.kongque.dto;

public class RepairPlanDetailDto {
	
	private String orderRepairId;
	
	private String repairOpinion;
	
	private String repairFeedback;
	
	private String[] materielIds;

	public String getOrderRepairId() {
		return orderRepairId;
	}

	public void setOrderRepairId(String orderRepairId) {
		this.orderRepairId = orderRepairId;
	}

	public String getRepairOpinion() {
		return repairOpinion;
	}

	public void setRepairOpinion(String repairOpinion) {
		this.repairOpinion = repairOpinion;
	}

	public String getRepairFeedback() {
		return repairFeedback;
	}

	public String[] getMaterielIds() {
		return materielIds;
	}

	public void setMaterielIds(String[] materielIds) {
		this.materielIds = materielIds;
	}

	public void setRepairFeedback(String repairFeedback) {
		this.repairFeedback = repairFeedback;
	}

}
