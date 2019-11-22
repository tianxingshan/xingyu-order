package com.kongque.entity.basics;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_customer_measure_relation")
@DynamicInsert(true)
@DynamicUpdate(true)
public class CustomerMeasureRelation implements Serializable{

	private static final long serialVersionUID = -7574750801370686744L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_customer_id")
	private String customerId;
	
	@Column(name = "c_order_measure_id")
	private String orderMeasureId;
	
	@Column(name = "c_order_language_id")
	private String orderLanguageId;
	
	@Column(name = "c_create_time")
	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getOrderMeasureId() {
		return orderMeasureId;
	}

	public void setOrderMeasureId(String orderMeasureId) {
		this.orderMeasureId = orderMeasureId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOrderLanguageId() {
		return orderLanguageId;
	}

	public void setOrderLanguageId(String orderLanguageId) {
		this.orderLanguageId = orderLanguageId;
	}
	
}
