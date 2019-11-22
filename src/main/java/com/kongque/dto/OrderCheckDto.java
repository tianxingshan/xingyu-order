package com.kongque.dto;

public class OrderCheckDto {
	//订单id
	private String orderId;
	//审核类型：1：订单业务审核；2：财务生产前审核；3：财务发货前审核
	private String checkType;
	//1：审核不通过；2：审核通过
	private String checkStatus;
	//审核人用户名
	private String checkerName;
	//审核说明
	private String checkInstruction;
	//（0：未送出；1：已送出；2：已提交星域；3：星域审核通过；4：星域驳回；5：秀域驳回；6：生产完成；7：已发货；8：已收货）
	private String status;

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

	public String getCheckInstruction() {
		return checkInstruction;
	}

	public void setCheckInstruction(String checkInstruction) {
		this.checkInstruction = checkInstruction;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
