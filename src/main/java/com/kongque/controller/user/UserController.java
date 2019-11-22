package com.kongque.controller.user;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kongque.dto.RoleAuthorityDto;
import com.kongque.dto.RoleDto;
import com.kongque.dto.UpdatePasswordDto;
import com.kongque.dto.UserDto;
import com.kongque.entity.user.User;
import com.kongque.entity.user.UserRole;
import com.kongque.service.user.IUserService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	@Resource
	private IUserService service;
	
	/**
	 * 查询用户列表
	 * 
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody Pagination<User> userList(UserDto dto, PageBean pageBean) {
		logger.error("用户查询开始"); 
		return service.userList(dto,pageBean);
		
	}
	/**
	 * 保存或更新用户信息
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody Result saveOrUpdate(@RequestBody UserDto dto) {
		return service.saveOrUpdate(dto);
	}
	
	/**
	 * 修改用户启用禁用状态
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/changeUserStatus", method = RequestMethod.GET)
	public @ResponseBody Result changeUserStatus(String id, String status) {
		return service.changeUserStatus(id,status);
	}
	
	/**
	 * 获取角色列表
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "/userRolelist", method = RequestMethod.GET)
	public @ResponseBody Pagination<UserRole> userRoleList(RoleDto dto,PageBean pageBean) {
		return service.userRoleList(dto,pageBean);
	}
	
	/**
	 * 新增或保存角色
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdateUserRole", method = RequestMethod.POST)
	public @ResponseBody Result saveOrUpdateUserRole(@RequestBody RoleDto dto) {
		return service.saveOrUpdateUserRole(dto);
	}
	
	/**
	 * 删除角色
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "/del/userRoleId", method = RequestMethod.POST)
	public @ResponseBody Result deleteUserRole(String userRoleId) {
		return service.deleteUserRole(userRoleId);
	}
	
	/**
	 * 根据角色id获取权限列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/role/authority/{id}", method = RequestMethod.GET)
	public @ResponseBody Result getUserRoleAuthority(@PathVariable String id) {
		return service.getUserRoleAuthority(id);
	}
	
	/**
	 * 更新角色权限
	 * 
	 * @return
	 */
	@RequestMapping(value = "/role/authority/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody Result saveOrUpdateRoleAuthority(@RequestBody RoleAuthorityDto dto) {
		return service.saveOrUpdateRoleAuthority(dto);
	}
	
	
	/**
	 * 根据系统id获取权限列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/tenant/authority/{sysId}", method = RequestMethod.GET)
	public @ResponseBody Result getTenantAuthorityList(@PathVariable String sysId) {
		return service.getTenantAuthorityList(sysId);
	}
	
	/**
	 * 修改密码
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public @ResponseBody Result updatePassword(@RequestBody UpdatePasswordDto dto) {
		return service.updatePassword(dto);
	}


	/**
	 * 查询版型师
	 * @param tenantId
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "/getTechnicianListByTenantId", method = RequestMethod.GET)
	@ResponseBody
	public Pagination<User> getTechnicianListByTenantId(String tenantId,PageBean pageBean){
		return service.getTechnicianListByTenantId(tenantId,pageBean);
	}
	
}
