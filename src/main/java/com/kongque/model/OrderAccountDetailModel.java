package com.kongque.model;

import com.kongque.entity.order.OrderAccountDetail;

public class OrderAccountDetailModel {
	
	private OrderAccountDetail orderAccountDetail;
	
	private String materialId;
	
	private String orderDetailId;
	
	public String getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public OrderAccountDetail getOrderAccountDetail() {
		return orderAccountDetail;
	}

	public void setOrderAccountDetail(OrderAccountDetail orderAccountDetail) {
		this.orderAccountDetail = orderAccountDetail;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

}
