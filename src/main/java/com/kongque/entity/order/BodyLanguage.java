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
@Table(name = "t_body_language")
@DynamicInsert(true)
@DynamicUpdate(true)
public class BodyLanguage implements Serializable{

	private static final long serialVersionUID = 6748761749694796485L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_order_id")
	private String orderId;
	
	@Column(name = "c_customer_id")
	private String customerId;
	
	@Column(name = "c_order_language")
	private String orderLanguage;

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

	public String getOrderLanguage() {
		return orderLanguage;
	}

	public void setOrderLanguage(String orderLanguage) {
		this.orderLanguage = orderLanguage;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
}
