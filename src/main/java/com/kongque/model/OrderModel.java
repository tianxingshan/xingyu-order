package com.kongque.model;

import java.util.List;

import com.kongque.entity.order.BodyLanguage;
import com.kongque.entity.order.BodyMeasure;
import com.kongque.entity.order.Order;
import com.kongque.entity.order.OrderAttachment;
import com.kongque.entity.order.OrderCheck;
import com.kongque.entity.order.OrderDetail;

public class OrderModel {
	
	private Order order;
	
	private List<OrderAttachment> orderAttachmentList;
	
	private List<OrderCheck> orderCheckList;
	
	private List<OrderDetail> orderDetailList;
	
	private BodyMeasure bodyMeasure;
	
	private BodyLanguage bodyLanguage;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<OrderAttachment> getOrderAttachmentList() {
		return orderAttachmentList;
	}

	public void setOrderAttachmentList(List<OrderAttachment> orderAttachmentList) {
		this.orderAttachmentList = orderAttachmentList;
	}

	public List<OrderCheck> getOrderCheckList() {
		return orderCheckList;
	}

	public void setOrderCheckList(List<OrderCheck> orderCheckList) {
		this.orderCheckList = orderCheckList;
	}

	public List<OrderDetail> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<OrderDetail> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

	public BodyMeasure getBodyMeasure() {
		return bodyMeasure;
	}

	public void setBodyMeasure(BodyMeasure bodyMeasure) {
		this.bodyMeasure = bodyMeasure;
	}

	public BodyLanguage getBodyLanguage() {
		return bodyLanguage;
	}

	public void setBodyLanguage(BodyLanguage bodyLanguage) {
		this.bodyLanguage = bodyLanguage;
	}
	
	
}
