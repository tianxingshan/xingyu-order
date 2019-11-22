package com.kongque.entity.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_body_measure")
@DynamicInsert(true)
@DynamicUpdate(true)
public class BodyMeasure implements Serializable{

	private static final long serialVersionUID = -3050974702316203777L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_order_id")
	private String orderId;
	
	@Column(name = "c_customer_id")
	private String customerId;
	
	@Column(name = "c_order_measure")
	private String orderMeasure;

	@Column(name = "c_order_measure_template")
	private String orderMeasureTemplate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderMeasure() {
		return orderMeasure;
	}

	public void setOrderMeasure(String orderMeasure) {
		this.orderMeasure = orderMeasure;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getOrderMeasureTemplate() {
		return orderMeasureTemplate;
	}

	public void setOrderMeasureTemplate(String orderMeasureTemplate) {
		this.orderMeasureTemplate = orderMeasureTemplate;
	}
}
