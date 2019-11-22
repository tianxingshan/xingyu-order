package com.kongque.service.user.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.kongque.entity.basics.Dept;
import com.kongque.entity.basics.XiuyuShopDirectorRelation;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.kongque.constants.Constants;
import com.kongque.dao.IDaoService;
import com.kongque.dto.RoleAuthorityDto;
import com.kongque.dto.RoleDto;
import com.kongque.dto.UpdatePasswordDto;
import com.kongque.dto.UserDto;
import com.kongque.entity.basics.Tenant;
import com.kongque.entity.user.Menu;
import com.kongque.entity.user.RoleMenuRelation;
import com.kongque.entity.user.User;
import com.kongque.entity.user.UserDeptRelation;
import com.kongque.entity.user.UserRole;
import com.kongque.entity.user.UserRoleRelation;
import com.kongque.entity.user.UserSysRelation;
import com.kongque.model.LogInModel;
import com.kongque.service.user.IUserService;
import com.kongque.util.CryptographyUtils;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@Service
public class UserServiceImpl implements IUserService{
	
	@Resource
	IDaoService dao;

	@Override
	public Pagination<User> userList(UserDto dto, PageBean pageBean) {
		Pagination<User> pagination = new Pagination<>();
		Criteria criteria=dao.createCriteria(User.class);
		if(StringUtils.isNotBlank(dto.getActualName())){
			criteria.add(Restrictions.like("actualName", dto.getActualName(),MatchMode.ANYWHERE)); 
		}
		if(StringUtils.isNotBlank(dto.getUserName())){
			criteria.add(Restrictions.like("userName", dto.getUserName(),MatchMode.ANYWHERE)); 
		}
		if(StringUtils.isNotBlank(dto.getDeptId())){
//			criteria.createCriteria("userDeptRelationList", "ll").createCriteria("dept", "o")
//			.add(Restrictions.eq("o.id", dto.getDeptId()));
			criteria.createCriteria("userDeptRelationList", "ll").add(Restrictions.eq("ll.deptId", dto.getDeptId()));
		}
		if(StringUtils.isNotBlank(dto.getTenantId())){
			criteria.createCriteria("userDeptRelationList", "ll").createCriteria("dept", "o").createCriteria("deptTenantId", "s")
			.add(Restrictions.eq("s.id", dto.getTenantId()));
		}
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
//		List<UserModel> userModelList = new ArrayList<>();
//		StringBuilder sql = new StringBuilder("select a.c_id,a.c_user_name,a.c_actual_name,a.c_email,a.c_phone_no,a.c_status,d.c_tenant_name,c.c_dept_name from t_user a left join t_user_dept_relation b on a.c_id=b.c_user_id left join t_dept c on b.c_dept_id = c.c_id left join t_tenant d on c.c_tenant_id = d.c_id where 1=1 ");
//		if(dto.getActualName() != null && !dto.getActualName().isEmpty()){
//			sql.append(" and a.c_actual_name like '%").append(dto.getActualName()).append("%'");
//		}
//		if(dto.getUserName() != null && !dto.getUserName().isEmpty()){
//			sql.append(" and a.c_user_name like '%").append(dto.getUserName()).append("%'");
//		}
//		if(dto.getDeptId() != null && !dto.getDeptId().isEmpty()){
//			sql.append(" and c.c_id = '"+dto.getDeptId()+"'");
//		}
//		if(dto.getTenantId() != null && !dto.getTenantId().isEmpty()){
//			sql.append(" and d.c_id = '"+dto.getTenantId()+"'");
//		}
//		sql.append(" limit "+(pageBean.getPage() - 1) * pageBean.getRows()+","+pageBean.getRows());
//		List resultSet = dao.queryBySql(sql.toString());		
//		for(Object result : resultSet){
//			UserModel userModel = new UserModel();//构建返回数据模型
//			Object[] properties = (Object[])result;
//			userModel.setId(properties[0]==null ? "" : properties[0].toString());
//			userModel.setUserName(properties[1]==null ? "" : properties[1].toString());
//			userModel.setActualName(properties[2]==null ? "" : properties[2].toString());
//			userModel.setEmail(properties[3]==null ? "" : properties[3].toString());
//			userModel.setPhoneNo(properties[4]==null ? "" : properties[4].toString());
//			userModel.setStatus(properties[5]==null ? "" : properties[5].toString());
//			userModel.setTenantName(properties[6]==null ? "" : properties[6].toString());
//			userModel.setDeptName(properties[7]==null ? "" : properties[7].toString());
//			userModelList.add(userModel);
//		}
//		return userModelList;
	}

	
	@Override
	public Long userListCount(UserDto dto) throws ParseException {
		StringBuilder sql = new StringBuilder("select count(*) from t_user a left join t_user_dept_relation b on a.c_id=b.c_user_id left join t_dept c on b.c_dept_id = c.c_id left join t_tenant d on c.c_tenant_id = d.c_id where 1=1 ");
		if(dto.getActualName() != null && !dto.getActualName().isEmpty()){
			sql.append(" and a.c_actual_name like '%").append(dto.getActualName()).append("%'");
		}
		if(dto.getUserName() != null && !dto.getUserName().isEmpty()){
			sql.append(" and a.c_user_name like '%").append(dto.getUserName()).append("%'");
		}
		if(dto.getDeptId() != null && !dto.getDeptId().isEmpty()){
			sql.append(" and c.c_id = '"+dto.getDeptId()+"'");
		}
		if(dto.getTenantId() != null && !dto.getTenantId().isEmpty()){
			sql.append(" and d.c_id = '"+dto.getTenantId()+"'");
		}
		List<BigInteger> result = dao.queryBySql(sql.toString());
		return result == null || result.isEmpty() ? 0L : result.get(0).longValue();
	}
	@Override
	public Result saveOrUpdate(UserDto dto) {



		if(StringUtils.isBlank(dto.getId())) {

			if(StringUtils.isNotBlank(dto.getUserName())){
				List<User> userList = dao.findListByProperty(User.class,"userName",dto.getUserName());
				if(userList.size()> 0){
					return new Result("500","用户代码已经存在!");
				}
			}

				User user = new User();
				try {
					BeanUtils.copyProperties(user, dto);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				user.setPassword(CryptographyUtils.md5(dto.getPassword())); 
				user.setCreateTime(new Date());
				dao.save(user);
				saveUserRelation(user,dto);
				return new Result(user);
//			}else{
//				return new Result(result.getReturnCode(), result.getReturnMsg());
//			}
		}else{
			User user = new User();
			try {
				BeanUtils.copyProperties(user, dto);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			if (user.getPassword().length() != 32) {
				user.setPassword(CryptographyUtils.md5(dto.getPassword())); 
			}
			user.setUpdateTime(new Date());
			dao.update(user);
			updateUserRelation(user,dto);
			return new Result(user);
		}
	}

	@Override
	public Result changeUserStatus(String id, String status){
		User user = dao.findById(User.class, id);
		user.setStatus(status);
		dao.update(user);
		return new Result(user);
	}
	
	public void saveUserRelation(User user,UserDto dto){

		Dept dept = dao.findById(Dept.class,dto.getDeptId());
		if (dept !=null){
			if ("1".equals(dept.getDeptType())){
				Criteria criteria = dao.createCriteria(XiuyuShopDirectorRelation.class);
				criteria.add(Restrictions.eq("userId",user.getId()));
				criteria.add(Restrictions.eq("deptId",dto.getDeptId()));
				@SuppressWarnings("unchecked")
				List<XiuyuShopDirectorRelation> list = criteria.list();
				if(list.size() < 1){
					XiuyuShopDirectorRelation xiuyuShopDirectorRelation = new XiuyuShopDirectorRelation();
					xiuyuShopDirectorRelation.setDeptId(dto.getDeptId());
					xiuyuShopDirectorRelation.setUserId(user.getId());
					dao.save(xiuyuShopDirectorRelation);
				}
			}
		}

		//新增用户部门关联关系
		UserDeptRelation userDept = new UserDeptRelation();
		userDept.setUserId(user.getId());
		userDept.setDeptId(dto.getDeptId());
		dao.save(userDept);
		//新增用户系统关联关系
		for (int i = 0; i < dto.getSysIds().length; i++) {
			UserSysRelation userSys = new UserSysRelation();
			userSys.setUserId(user.getId());
			userSys.setSysId(dto.getSysIds()[i]);
			dao.save(userSys);
		}
		//新增用户角色关联关系
		for (int i = 0; i < dto.getRoleIds().length; i++) {
			UserRoleRelation userRole = new UserRoleRelation();
			userRole.setUserId(user.getId());
			userRole.setRoleId(dto.getRoleIds()[i]);
			dao.save(userRole); 
		}
	}
	
	public void updateUserRelation(User user,UserDto dto){

		Dept dept = dao.findById(Dept.class,dto.getDeptId());
		if (dept !=null){
			if ("1".equals(dept.getDeptType())){
				Criteria criteria = dao.createCriteria(XiuyuShopDirectorRelation.class);
				criteria.add(Restrictions.eq("userId",user.getId()));
				criteria.add(Restrictions.eq("deptId",dto.getDeptId()));
				@SuppressWarnings("unchecked")
				List<XiuyuShopDirectorRelation> list = criteria.list();
				if(list.size() < 1){
					XiuyuShopDirectorRelation xiuyuShopDirectorRelation = new XiuyuShopDirectorRelation();
					xiuyuShopDirectorRelation.setDeptId(dto.getDeptId());
					xiuyuShopDirectorRelation.setUserId(user.getId());
					dao.save(xiuyuShopDirectorRelation);
				}
			}
		}
		if(StringUtils.isNotBlank(dto.getDeptId())){
			UserDeptRelation userDept1 = dao.findUniqueByProperty(UserDeptRelation.class, "userId", dto.getId());
			dao.delete(userDept1);
			//新增用户部门关联关系
			UserDeptRelation userDept = new UserDeptRelation();
			userDept.setUserId(user.getId());
			userDept.setDeptId(dto.getDeptId());
			dao.save(userDept);
		}
		if(dto.getSysIds()!=null){
			Criteria criteria = dao.createCriteria(UserSysRelation.class);
			criteria.add(Restrictions.eq("userId", dto.getId()));
			@SuppressWarnings("unchecked")
			List<UserSysRelation> list = criteria.list();
			for (UserSysRelation userSysRelation : list) {
				dao.delete(userSysRelation);
			}
			for (int i = 0; i < dto.getSysIds().length; i++) {
				UserSysRelation userSys = new UserSysRelation();
				userSys.setUserId(user.getId());
				userSys.setSysId(dto.getSysIds()[i]);
				dao.save(userSys);
			}
		}
		if(dto.getRoleIds()!=null){
			Criteria criteria = dao.createCriteria(UserRoleRelation.class);
			criteria.add(Restrictions.eq("userId", dto.getId()));
			@SuppressWarnings("unchecked")
			List<UserRoleRelation> list = criteria.list();
			for (UserRoleRelation userRoleRelation : list) {
				dao.delete(userRoleRelation);
			}
			for (int i = 0; i < dto.getRoleIds().length; i++) {
				UserRoleRelation userRole = new UserRoleRelation();
				userRole.setUserId(user.getId());
				userRole.setRoleId(dto.getRoleIds()[i]);
				dao.save(userRole); 
			}
		}
	}
	

	@Override
	public Pagination<UserRole> userRoleList(RoleDto dto,PageBean pageBean) {
		Pagination<UserRole> pagination = new Pagination<UserRole>();
		Criteria criteria = dao.createCriteria(UserRole.class);
		if(StringUtils.isNotBlank(dto.getRoleName())){
			criteria.add(Restrictions.like("roleName", dto.getRoleName(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(dto.getTenantId())){
			criteria.add(Restrictions.eq("tenant.id", dto.getTenantId()));
		}
		if(pageBean.getRows()==null){
			pageBean.setRows(999);
		}
		if(pageBean.getPage()==null){
			pageBean.setPage(1); 
		}
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria,pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}

	@Override
	public Result saveOrUpdateUserRole(RoleDto dto) {
	    if (StringUtils.isBlank(dto.getId())){
	    	UserRole userRole = new UserRole();
	    	userRole.setRoleName(dto.getRoleName());
	    	userRole.setTenant(dao.findById(Tenant.class, dto.getTenantId()));
            dao.save(userRole);
            return new Result(userRole);
        }else {
        	UserRole userRole = dao.findById(UserRole.class, dto.getId());
        	userRole.setRoleName(dto.getRoleName());
        	userRole.setTenant(dao.findById(Tenant.class, dto.getTenantId()));
            dao.update(userRole);
            return new Result(userRole);
        }
	}

	@Override
	public Result deleteUserRole(String userRoleId) {
		UserRole userRole=dao.findById(UserRole.class, userRoleId);
		dao.delete(userRole);
		return new Result(200);
	}
	
	@Override
	public Result getUserRoleAuthority(String id) {
		Criteria criteria = dao.createCriteria(RoleMenuRelation.class);
		criteria.add(Restrictions.eq("roleId", id));
//		criteria.setProjection(Projections.property("menu.id"));
		@SuppressWarnings("unchecked")
		List<RoleMenuRelation> authorityList = criteria.list();
		List<Menu> list = new ArrayList<>();
		if(authorityList!=null && !authorityList.isEmpty()){
			for (int i = 0; i < authorityList.size(); i++) {
				Menu m = authorityList.get(i).getMenu();
				if(!"-1".equals(m.getParent().getId())){
					list.add(m);
				}
			}
		}
		removeIntermediateNode(list);
		return new Result(list);
	}
	
	@Override
	public Result saveOrUpdateRoleAuthority(RoleAuthorityDto dto) {
		deleteAllAuthority(dto.getRoleId());
		List<RoleMenuRelation> userRoleAuthorityList = new ArrayList<RoleMenuRelation>();
		for (Menu menu : dto.getMenuList()) {
				RoleMenuRelation roleMenuRelation = new RoleMenuRelation();
				roleMenuRelation.setMenu(menu);
				roleMenuRelation.setRoleId(dto.getRoleId());
				userRoleAuthorityList.add(roleMenuRelation);
		}
		dao.saveAllEntity(userRoleAuthorityList);
		return new Result("200","操作成功");
	}
	
	@Override
	public Result getTenantAuthorityList(String sysId) {
		Criteria criteria = dao.createCriteria(Menu.class);
		criteria.add(Restrictions.eq("sysId", sysId));
		criteria.add(Restrictions.eq("parent.id", "-1"));
		@SuppressWarnings("unchecked")
		List<Menu> authorityList = criteria.list();
		return new Result(authorityList);
	}
	
	
	@Override
	public Result getMenuRole(LogInModel model) {
		Criteria criteria = dao.createCriteria(UserRoleRelation.class);
        criteria.add(Restrictions.eq("userId", model.getId()));
        @SuppressWarnings("unchecked")
		List<UserRoleRelation> roleList = criteria.list();
        String roles = "";
        for (UserRoleRelation userRoleRelation : roleList) {
        	if(roles == ""){
        		roles = "'"+userRoleRelation.getRoleId()+"'";
        	}else{
        		roles = roles + "," + "'"+userRoleRelation.getRoleId()+"'";
        	}
		}
        List<Menu> menuRoleList = new ArrayList<>();
		StringBuffer sql = new StringBuffer("select distinct(a.c_menu_id),b.c_name,b.c_sys_id from t_role_menu_relation a left join t_menu b on a.c_menu_id=b.c_id where b.c_sys_id='"+model.getSysId()+"' and a.c_role_id in ("+roles+")");
		List resultSet = dao.queryBySql(sql.toString());		
		for(Object result : resultSet){
			Menu menu = new Menu();//构建返回数据模型
			Object[] properties = (Object[])result;
			menu.setId(properties[0]==null ? "" : properties[0].toString());
			menu.setName(properties[1]==null ? "" : properties[1].toString());
			menu.setSysId(properties[2]==null ? "" : properties[2].toString());
			menuRoleList.add(menu); 
		}
		StringBuffer sql1 = new StringBuffer("select DISTINCT(b.c_parent_id) from t_role_menu_relation a left join t_menu b on a.c_menu_id=b.c_id where b.c_sys_id='"+model.getSysId()+"' and a.c_role_id in ("+roles+")");
		List resultSet1 = dao.queryBySql(sql1.toString());
		for(Object result1 : resultSet1){
			Menu menu = dao.findById(Menu.class , result1.toString());
			menuRoleList.add(menu); 
		}
		removeIntermediateNode(menuRoleList);
		model.setMenus(menuRoleList);
		return new Result(model);
	}
	/**
     * 删除children节点,返回一级列表
     * @param authMenuList
     */
    private void removeIntermediateNode(List<Menu> authMenuList) {
    	for (int i = 0; i < authMenuList.size(); i++) {
			if(authMenuList.get(i).getChildren()!=null && !authMenuList.get(i).getChildren().isEmpty()){
				authMenuList.get(i).getChildren().removeAll(authMenuList.get(i).getChildren());
			}
		}
    }
	
	private void deleteAllAuthority(String getRoleId) {
		Criteria criteria = dao.createCriteria(RoleMenuRelation.class);
		criteria.add(Restrictions.eq("roleId", getRoleId));
		@SuppressWarnings("unchecked")
		List<RoleMenuRelation> authorityList = criteria.list();
		if (authorityList!=null && authorityList.size()>0) {
			dao.deleteAllEntity(authorityList);
		}
	}
	
	@Override
	public Result updatePassword(UpdatePasswordDto dto) {
		User findedUser = dao.findUniqueByProperty(User.class, "userName", dto.getUserName());
		if (findedUser == null) {
			return new Result(Constants.RESULT_CODE.SYS_ERROR, "您尚未注册！");
		}
		if (findedUser.getStatus().equals("1")) {
			return new Result(Constants.RESULT_CODE.SYS_ERROR, "您的账户无效！");
		}
		//1、判断用户名密码
		if (findedUser.getPassword().equals(DigestUtils.md5Hex(dto.getOldPassword()))) {
			findedUser.setPassword(DigestUtils.md5Hex(dto.getNewPassword()));
			dao.update(findedUser);
			return new Result("200","密码修改成功！");
		}else {
			return new Result(Constants.RESULT_CODE.SYS_ERROR, "密码错误！");
		}
	}


	/**
	 * 通过商户Id查询版型师
	 * @param tenantId
	 * @return
	 */
	@Override
	public Pagination<User> getTechnicianListByTenantId(String tenantId, PageBean pageBean) {
		Pagination<User> pagination = new Pagination<>();
		Criteria criteria = dao.createCriteria(User.class);
		criteria.createCriteria("userRoleRelationList","list")
				.createCriteria("userRole","role")
				.add(Restrictions.eq("role.roleName", "版型师"))
				.createCriteria("tenant","t")
				.add(Restrictions.eq("t.id", tenantId));

		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}
	
	
}
