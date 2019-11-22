package com.kongque.dto;

/**
 * 商户查询条件
 * 
 * @author wangwj
 *
 */
public class TenantDto {
	private String id;
	private String parentId;
	private String tenantName;// 商户名称
	private String tenantDel;//商户状态
	private String tenantStatus;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getTenantDel() {
		return tenantDel;
	}

	public void setTenantDel(String tenantDel) {
		this.tenantDel = tenantDel;
	}

	public String getTenantStatus() {
		return tenantStatus;
	}

	public void setTenantStatus(String tenantStatus) {
		this.tenantStatus = tenantStatus;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
