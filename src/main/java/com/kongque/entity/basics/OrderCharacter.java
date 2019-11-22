package com.kongque.entity.basics;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "t_order_character")
@DynamicInsert(true)
@DynamicUpdate(true)
@Where(clause = "c_delete_flag='0'")
public class OrderCharacter implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5934715634131644933L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "c_id")
	private String id;
	@Column(name = "c_tenant_id")
	private String tenantId;//商户id
	@Column(name = "c_character_name")
	private String orderCharacterName;// 订单性质
	@Column(name = "c_create_time")
	private Date orderCharacterCreateTime;
	@Column(name = "c_status")
	private String orderCharacterStatus;// 0:启用 1:禁用 
	@Column(name = "c_memo")
	private String orderCharacterMemo;//备注

	@Column(name = "c_delete_flag")
	private String deleteFlag;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getOrderCharacterName() {
		return orderCharacterName;
	}
	public void setOrderCharacterName(String orderCharacterName) {
		this.orderCharacterName = orderCharacterName;
	}
	public Date getOrderCharacterCreateTime() {
		return orderCharacterCreateTime;
	}
	public void setOrderCharacterCreateTime(Date orderCharacterCreateTime) {
		this.orderCharacterCreateTime = orderCharacterCreateTime;
	}
	public String getOrderCharacterStatus() {
		return orderCharacterStatus;
	}
	public void setOrderCharacterStatus(String orderCharacterStatus) {
		this.orderCharacterStatus = orderCharacterStatus;
	}
	public String getOrderCharacterMemo() {
		return orderCharacterMemo;
	}
	public void setOrderCharacterMemo(String orderCharacterMemo) {
		this.orderCharacterMemo = orderCharacterMemo;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
}
