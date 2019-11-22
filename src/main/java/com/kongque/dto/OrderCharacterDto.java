package com.kongque.dto;


public class OrderCharacterDto {
	
	private String id;
	private String tenantId;//商户id
	private String parentTenantId;//父商户id
	private String orderCharacterName;// 订单性质
	private String orderCharacterCreateTime;
	private String orderCharacterStatus;// 0:启用 1:禁用 
	private String orderCharacterMemo;//备注

	private String[] tenantIds;//商户组id
	private String orderCharacterId;//订单性质id
	/**
	 * 删除标志
	 */
	private String flag;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderCharacterName() {
		return orderCharacterName;
	}
	public void setOrderCharacterName(String orderCharacterName) {
		this.orderCharacterName = orderCharacterName;
	}
	public String getOrderCharacterCreateTime() {
		return orderCharacterCreateTime;
	}
	public void setOrderCharacterCreateTime(String orderCharacterCreateTime) {
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

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getParentTenantId() {
		return parentTenantId;
	}

	public void setParentTenantId(String parentTenantId) {
		this.parentTenantId = parentTenantId;
	}

	public String[] getTenantIds() {
		return tenantIds;
	}

	public void setTenantIds(String[] tenantIds) {
		this.tenantIds = tenantIds;
	}

	public String getOrderCharacterId() {
		return orderCharacterId;
	}

	public void setOrderCharacterId(String orderCharacterId) {
		this.orderCharacterId = orderCharacterId;
	}
}
