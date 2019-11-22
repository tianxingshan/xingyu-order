package com.kongque.service.basics;

import com.kongque.dto.DeptDto;
import com.kongque.dto.TenantDto;
import com.kongque.entity.basics.Tenant;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

public interface ITenantService {
	
	Pagination<Tenant> TenantList(PageBean p,TenantDto tenant);
	
	Result saveOrUpdate(Tenant tenant);
	
	Result deleteTenant(String tenantId);
	
	Result updateStatusTenant(String tenantId,String tenantStatus);
	
	Result getSysList();


    Result getListByOrderCharacterId(String orderCharacterId);
}
