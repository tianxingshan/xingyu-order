package com.kongque.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class UserDto {
	private String id;
	
	private String userName;//用户名
	
	private String userCode;//员工编号
	
	private String actualName;//用户真实姓名
	
	private String password;//密码
	
	private String status;//用户状态  0.有效  1.禁用
	
	private String remark;//用户描述
	
	private String createUser;//创建者
	
	@DateTimeFormat(pattern="yy-MM-dd HH:mm:ss")
	private Date createTime;//创建时间
	
	private String updateUser;//修改者
	
	private String[] roleIds;//用户角色Id
	
	private String phoneNo;//用户手机号
	
	private String email;//用户邮箱
	
	private String[] sysIds;//系统标识id
	
	private String deptId;//关联门店id
	
	private String tenantId;//关联商户id

	/**
	 * 自动授权
	 */
	private int defaultAuthorityFlag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getActualName() {
		return actualName;
	}

	public void setActualName(String actualName) {
		this.actualName = actualName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String[] getSysIds() {
		return sysIds;
	}

	public void setSysIds(String[] sysIds) {
		this.sysIds = sysIds;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getDefaultAuthorityFlag() {
		return defaultAuthorityFlag;
	}

	public void setDefaultAuthorityFlag(int defaultAuthorityFlag) {
		this.defaultAuthorityFlag = defaultAuthorityFlag;
	}
}
