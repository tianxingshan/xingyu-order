package com.kongque.entity.basics;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.kongque.entity.order.Order;

@Entity
@Table(name = "t_order_closed")
public class OrderClosed {
	/*
	 * 结案单id
	 */
	private String cid;
	/**
	 * 结案编码
	 */
	private String closedCode;
	/*
	 * 订单id
	 */
	private Order orderId;
	
	/*
	 * 结案单申请人
	 */
	private String closedApplicant;
	/*
	 *结案单状态 
	 */
	private String closedStatus;
	/*
	 * 结案单创建时间
	 */
	private Date closedCreateTime; 
	/*
	 *结案单审核人 
	 */
	private String closedAuditor;
	/*
	 *结案单审核时间
	 */
	private Date closedCheckTime;
	/**
	 * 申请结案原因
	 * @return
	 */
	private String closedReason;
	/**
	 * 审核说明
	 */
	private String closedInstruction;
	/**
	 * 结案单提交时间
	 */
	private Date closedSubmitTime;
	private String del;
	
	
	@Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "c_id")
	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}
	@Column(name = "c_closed_code")
	 public String getClosedCode() {
		return closedCode;
	}

	public void setClosedCode(String closedCode) {
		this.closedCode = closedCode;
	}
	@OneToOne
	@JoinColumn(name = "c_order_id")
	public Order getOrderId() {
		return orderId;
	}

	public void setOrderId(Order orderId) {
		this.orderId = orderId;
	}

	@Column(name = "c_closed_applicant")
	public String getClosedApplicant() {
		return closedApplicant;
	}
	
	public void setClosedApplicant(String closedApplicant) {
		this.closedApplicant = closedApplicant;
	}
	@Column(name = "c_closed_status")
	public String getClosedStatus() {
		return closedStatus;
	}
	public void setClosedStatus(String closedStatus) {
		this.closedStatus = closedStatus;
	}
	@Column(name = "c_closed_createtime")
	public Date getClosedCreateTime() {
		return closedCreateTime;
	}
	public void setClosedCreateTime(Date closedCreateTime) {
		this.closedCreateTime = closedCreateTime;
	}
	@Column(name = "c_closed_auditor")
	public String getClosedAuditor() {
		return closedAuditor;
	}
	public void setClosedAuditor(String closedAuditor) {
		this.closedAuditor = closedAuditor;
	}
	@Column(name = "c_closed_checktime")
	public Date getClosedCheckTime() {
		return closedCheckTime;
	}
	public void setClosedCheckTime(Date closedCheckTime) {
		this.closedCheckTime = closedCheckTime;
	}
	@Column(name = "c_closed_reason")
	public String getClosedReason() {
		return closedReason;
	}

	public void setClosedReason(String closedReason) {
		this.closedReason = closedReason;
	}
	@Column(name = "c_closed_instruction")
	public String getClosedInstruction() {
		return closedInstruction;
	}

	public void setClosedInstruction(String closedInstruction) {
		this.closedInstruction = closedInstruction;
	}
	
	

	@Column(name = "c_closed_submittime")
	public Date getClosedSubmitTime() {
		return closedSubmitTime;
	}
	public void setClosedSubmitTime(Date closedSubmitTime) {
		this.closedSubmitTime = closedSubmitTime;
	}
	@Column(name = "c_closed_delete")
	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	
	
}
