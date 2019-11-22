package com.kongque.dto;

import java.util.List;

import com.kongque.entity.user.Menu;
import com.kongque.entity.user.UserRole;

public class RoleAuthorityDto {
	
	/**
	 * 角色id
	 */
	private String roleId;
	/**
	 * 权限列表
	 */
	private List<Menu> menuList;
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public List<Menu> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}
	
	

}
