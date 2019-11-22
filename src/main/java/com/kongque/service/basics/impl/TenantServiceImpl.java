package com.kongque.service.basics.impl;

import java.util.List;

import javax.annotation.Resource;

import com.kongque.dto.DeptDto;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.kongque.dao.IDaoService;
import com.kongque.dto.TenantDto;
import com.kongque.entity.basics.Dept;
import com.kongque.entity.basics.Sys;
import com.kongque.entity.basics.Tenant;
import com.kongque.service.basics.ITenantService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;


@Service
public class TenantServiceImpl implements ITenantService{
	
	@Resource
	IDaoService dao;

	@Override
	public Pagination<Tenant> TenantList(PageBean pageBean, TenantDto tenant) {
		Pagination<Tenant> pagination = new Pagination<>();
		Criteria criteria = dao.createCriteria(Tenant.class);
		if(!StringUtils.isBlank(tenant.getId())) {
			criteria.add(Restrictions.eq("id", tenant.getId()));
		}
		if(!StringUtils.isBlank(tenant.getTenantName())) {
			criteria.add(Restrictions.like("tenantName", tenant.getTenantName()));
		}
		if(!StringUtils.isBlank(tenant.getTenantStatus())){
			criteria.add(Restrictions.eq("tenantStatus", tenant.getTenantStatus()));
		}
		if(StringUtils.isNotBlank(tenant.getParentId())){
			criteria.add(Restrictions.eq("parentId",tenant.getParentId()));
		}
		if(pageBean.getPage() == null) {
			pageBean.setPage(0);
		}
		if(pageBean.getRows() == null) {
			pageBean.setRows(999);
		}
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}

	@Override
	public Result saveOrUpdate(Tenant tenant) {
		if(StringUtils.isBlank(tenant.getId())) {
			tenant.setTenantDel("0");
			dao.save(tenant);
			return new Result(tenant);
		}else {
			tenant.setTenantDel("0");
			dao.update(tenant);
			return new Result(tenant);
		}
	}

	@Override
	public Result deleteTenant(String tenantId) {
		Criteria criteria=dao.createCriteria(Dept.class);
		criteria.add(Restrictions.eq("deptTenantId.id", tenantId));
		@SuppressWarnings("unchecked") 
		List<Dept> list=criteria.list();
		if(list!=null && list.size()>0){
			return new Result("500","该商户下存在可用部门，不可删除");
		}
		Tenant tenant = dao.findById(Tenant.class, tenantId);
		tenant.setTenantDel("1");
		dao.update(tenant);
		return new Result(tenant);
	}

	@Override
	public Result updateStatusTenant(String tenantId,String tenantStatus) {
		Tenant tenant = dao.findById(Tenant.class, tenantId);
		tenant.setTenantStatus(tenantStatus);
		dao.update(tenant);
		return new Result(tenant);
	}

	@Override
	public Result getSysList() {
		Criteria criteria = dao.createCriteria(Sys.class);
		@SuppressWarnings("unchecked")
		List<Sys> list = criteria.list();
		return new Result(list);
	}

	@Override
	public Result getListByOrderCharacterId(String orderCharacterId) {
		Criteria criteria = dao.createCriteria(Tenant.class);
		criteria.add(Restrictions.eq("id", "00"));
		List<Tenant> list = criteria.list();
		list = setChildList(list,orderCharacterId);
		return new Result(list);
	}


	private List<Tenant> setChildList(List<Tenant> list,String orderCharacterId){
		for(Tenant tenant :list){
			Integer count = Integer.parseInt(dao.uniqueBySql("select count(1) from t_order_character_tenant_relation where c_order_character_id='"+orderCharacterId+"' and c_tenant_id='"+tenant.getId()+"'").toString());
			tenant.setFlag(count);

			List<Tenant> tenants = tenant.getChildren();
			if(tenants.size()> 0){
				List<Tenant> childrens = setChildList(tenants,orderCharacterId);
				tenant.setChildren(childrens);
			}
		}
		return list;
	}


}
