package com.kongque.service.basics;

import com.kongque.dto.DeptDto;
import com.kongque.entity.basics.Dept;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

public interface IDeptService {

	Pagination<Dept> DeptList(DeptDto dto, PageBean pageBean);
	
	Result saveOrUpdate(DeptDto dto);
	
	Result delete(String id);
	
	Result parent(String tenantId);

    Result getTenantDeps(DeptDto dto);

	Result saveShopListByUserId(DeptDto dto);
}
