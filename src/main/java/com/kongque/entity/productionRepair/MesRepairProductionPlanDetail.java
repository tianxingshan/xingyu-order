package com.kongque.entity.productionRepair;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.kongque.entity.repair.OrderRepair;

@Entity
@Table(name = "mes_repair_production_plan_detail")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MesRepairProductionPlanDetail implements Serializable{

	private static final long serialVersionUID = -7581362183232596314L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	
	@Column(name = "c_repair_plan_id")
	private String repairPlanId;
	
	@Column(name = "c_order_repair_id")
	private String orderRepairId;
	
	@Column(name = "c_repair_opinion")
	private String repairOpinion;
	
	@Column(name = "c_repair_feedback")
	private String repairFeedback;
	
	@ManyToOne
    @JoinColumn(name = "c_order_repair_id",insertable =false, updatable =false)
	private OrderRepair repair;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRepairPlanId() {
		return repairPlanId;
	}

	public void setRepairPlanId(String repairPlanId) {
		this.repairPlanId = repairPlanId;
	}

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

	public void setRepairFeedback(String repairFeedback) {
		this.repairFeedback = repairFeedback;
	}

	public OrderRepair getRepair() {
		return repair;
	}

	public void setRepair(OrderRepair repair) {
		this.repair = repair;
	}


}
