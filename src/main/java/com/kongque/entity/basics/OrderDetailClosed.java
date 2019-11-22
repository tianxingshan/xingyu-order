package com.kongque.entity.basics;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.kongque.entity.order.OrderDetail;

@Entity
@Table(name = "t_order_detail_closed")
public class OrderDetailClosed {
	/*
	 * 结案单明细id
	 */
	private String id;
	/*
	 * 结案单id
	 */
	private OrderClosed orderClosedId;
	
	/*
	 *订单明细id
	 */
	private OrderDetail orderDetailId;
	

	@Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@OneToOne
	@JoinColumn(name = "c_order_closed_id")
	public OrderClosed getOrderClosedId() {
		return orderClosedId;
	}

	public void setOrderClosedId(OrderClosed orderClosedId) {
		this.orderClosedId = orderClosedId;
	}
	@OneToOne
	@JoinColumn(name = "c_order_detail_id")
	public OrderDetail getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(OrderDetail orderDetailId) {
		this.orderDetailId = orderDetailId;
	}
	
	
	
	
	

	


	
	
	
}
