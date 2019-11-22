package com.kongque.service.user;

import java.text.ParseException;

import com.kongque.dto.RoleAuthorityDto;
import com.kongque.dto.RoleDto;
import com.kongque.dto.UpdatePasswordDto;
import com.kongque.dto.UserDto;
import com.kongque.entity.user.User;
import com.kongque.entity.user.UserRole;
import com.kongque.model.LogInModel;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

public interface IUserService {

	/**
	 * 查询用户列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	Pagination<User> userList(UserDto dto, PageBean pageBean);
	
	Long userListCount(UserDto dto) throws ParseException;
	
	/**
	 * 保存或更新用户信息
	 * @param user
	 * @return
	 */
	Result saveOrUpdate(UserDto dto);
	
	/**
	 * 修改用户启用禁用状态
	 * @param id
	 * @param status
	 * @return
	 */
	Result changeUserStatus(String id, String status);
	
	/**
	 * 查询用户列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	Pagination<UserRole> userRoleList(RoleDto dto,PageBean pageBean);
	/**
	 * 保存或更新用户信息
	 * @param user
	 * @return
	 */
	Result saveOrUpdateUserRole(RoleDto dto);
	/**
	 * 删除用户
	 * @param userId
	 * @return
	 */
	Result deleteUserRole(String userRoleId);
	/**
	 * 根据角色id获取权限列表
	 * @return
	 */
	Result getUserRoleAuthority(String id);
	/**
	 * 更新角色权限
	 * @return
	 */
	Result saveOrUpdateRoleAuthority(RoleAuthorityDto dto);
	
	/**
	 * 根据系统id获取权限列表
	 * @return
	 */
	Result getTenantAuthorityList(String sysId);
	
	/**
	 * 用户登录时获取权限列表
	 * @return
	 */
	Result getMenuRole(LogInModel model);
	
	/**
	 * 修改密码
	 * @return
	 */
	Result updatePassword(UpdatePasswordDto dto);

	/**
	 * 通过商户Id查询版型师
	 * @param tenantId
	 * @return
	 */
	Pagination<User> getTechnicianListByTenantId(String tenantId, PageBean pageBean);
	
}
