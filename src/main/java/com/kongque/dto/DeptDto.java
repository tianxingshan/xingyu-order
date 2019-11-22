package com.kongque.dto;

/**
 * 颜色查询条件
 * 
 * @author wangwj
 *
 */
public class DeptDto {
	private String id;
	private String deptName;
	private String deptCode;
	private String deptType;
	private String deptParentId;
	private String deptTenantId;
	private String areaId;
	private String companyId;
	private String tenantId;
	private String deptPhone;
	private String operatorId;
	private String operatorName;
	private String userId;
	private String[] shopIds;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	public String getDeptParentId() {
		return deptParentId;
	}

	public void setDeptParentId(String deptParentId) {
		this.deptParentId = deptParentId;
	}

	public String getDeptTenantId() {
		return deptTenantId;
	}

	public void setDeptTenantId(String deptTenantId) {
		this.deptTenantId = deptTenantId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getDeptPhone() {
		return deptPhone;
	}

	public void setDeptPhone(String deptPhone) {
		this.deptPhone = deptPhone;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String[] getShopIds() {
		return shopIds;
	}

	public void setShopIds(String[] shopIds) {
		this.shopIds = shopIds;
	}
}
