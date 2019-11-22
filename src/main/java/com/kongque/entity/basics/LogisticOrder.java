package com.kongque.entity.basics;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import com.kongque.entity.order.OrderDetail;
import com.kongque.entity.repair.OrderRepair;

@Entity
@Table(name = "t_logistic_order")
@DynamicInsert(true)
@DynamicUpdate(true)
public class LogisticOrder implements Serializable {

	private static final long serialVersionUID = 7256444356425097290L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	@Column(name = "c_logistic_id")
	private String logisticId;
	@Column(name = "c_order_repair_id")
	private String orderRepairId;
	@Column(name = "c_order_detail_id")
	private String orderDetailId;
	@Column(name = "c_order_code")
	private String orderCode;
	@ManyToOne
	@JoinColumn(name = "c_order_repair_id", insertable = false, updatable = false)
	private OrderRepair orderRepair;
	@OneToOne
	@JoinColumn(name = "c_order_detail_id", insertable = false, updatable = false)
	private OrderDetail orderDetail;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogisticId() {
		return logisticId;
	}

	public void setLogisticId(String logisticId) {
		this.logisticId = logisticId;
	}

	public OrderRepair getOrderRepair() {
		return orderRepair;
	}

	public void setOrderRepair(OrderRepair orderRepair) {
		this.orderRepair = orderRepair;
	}

	public OrderDetail getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

	public String getOrderRepairId() {
		return orderRepairId;
	}

	public void setOrderRepairId(String orderRepairId) {
		this.orderRepairId = orderRepairId;
	}

	public String getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

}
