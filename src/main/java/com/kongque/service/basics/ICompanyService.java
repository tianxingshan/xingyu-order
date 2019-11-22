package com.kongque.service.basics;

import com.kongque.dto.CompanyDto;
import com.kongque.entity.basics.Company;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

public interface ICompanyService {
	//根据条件分页查询公司
	public Pagination<Company> companyList(CompanyDto dto, PageBean pageBean);
	//根据id查询某一公司
	public Result companyById(String companyId);
	
	//添加或修改公司
	public Result saveOrUpdate(CompanyDto dto);
	//根据id删除公司
	public Result delete(String companyId);
	
}
