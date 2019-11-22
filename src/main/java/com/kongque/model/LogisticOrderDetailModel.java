package com.kongque.model;

import com.kongque.entity.repair.OrderRepair;

public class LogisticOrderDetailModel {
	
	
	private OrderDetailModel orderDetailModel;

	private OrderRepairModel orderRepairModel;
	public OrderDetailModel getOrderDetailModel() {
		return orderDetailModel;
	}

	public void setOrderDetailModel(OrderDetailModel orderDetailModel) {
		this.orderDetailModel = orderDetailModel;
	}

	public OrderRepairModel getOrderRepairModel() {
		return orderRepairModel;
	}

	public void setOrderRepairModel(OrderRepairModel orderRepairModel) {
		this.orderRepairModel = orderRepairModel;
	}

	

}
