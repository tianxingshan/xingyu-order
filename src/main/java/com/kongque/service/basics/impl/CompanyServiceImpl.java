package com.kongque.service.basics.impl;

import java.util.List;

import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.kongque.constants.Constants;
import com.kongque.dao.IDaoService;
import com.kongque.dto.CompanyDto;
import com.kongque.entity.basics.Area;
import com.kongque.entity.basics.Company;
import com.kongque.service.basics.ICompanyService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@Service("Company")
public class CompanyServiceImpl implements ICompanyService {
	@Resource
	IDaoService dao;

	// 根据id查询某一公司
	@Override
	public Result companyById(String companyId) {

		Company company = dao.findById(Company.class, companyId);
		if (company != null) {
			return new Result(company);
		} else {
			return new Result(Constants.RESULT_CODE.SYS_ERROR, "没有此公司！");
		}
	}
	// 根据条件分页查询公司
	@Override
	public Pagination<Company> companyList(CompanyDto dto, PageBean pageBean) {
		if (pageBean.getPage() == null) {
			pageBean.setPage(0);
		}
		if (pageBean.getRows() == null) {
			pageBean.setRows(9999);
		}
		Pagination<Company> pagination = new Pagination<Company>();
		Criteria criteria = dao.createCriteria(Company.class);
		if (StringUtils.isNotBlank(dto.getId())) {
			criteria.add(Restrictions.eq("id", dto.getId()));
		}
		if (StringUtils.isNotBlank(dto.getUnitName())) {
			criteria.add(Restrictions.like("unitName", dto.getUnitName(), MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(dto.getAddress())) {
			criteria.add(Restrictions.like("address", dto.getAddress(), MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(dto.getContact())) {
			criteria.add(Restrictions.like("contact", dto.getContact(), MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(dto.getPhoneNo())) {
			criteria.add(Restrictions.eq("phoneNo", dto.getPhoneNo()));
		}
		if (StringUtils.isNotBlank(dto.getCity())) {
			criteria.add(Restrictions.like("city", dto.getCity(), MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(dto.getInvoiceTitle())) {
			criteria.add(Restrictions.like("invoiceTitle", dto.getInvoiceTitle(), MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(dto.getTaxpayerNo())) {
			criteria.add(Restrictions.eq("taxpayerNo", dto.getTaxpayerNo()));
		}
		if (StringUtils.isNotBlank(dto.getDepositBank())) {
			criteria.add(Restrictions.eq("depositBank", dto.getDepositBank()));
		}
		if (StringUtils.isNotBlank(dto.getAccountInfo())) {
			criteria.add(Restrictions.eq("accountInfo", dto.getAccountInfo()));
		}
		if (StringUtils.isNotBlank(dto.getRemark())) {
			criteria.add(Restrictions.eq("remark", dto.getRemark()));
		}
		pagination.setRows(dao.findListWithPagebeanCriteria(criteria, pageBean));
		pagination.setTotal(dao.findTotalWithCriteria(criteria));
		return pagination;
	}

	// 添加或修改公司
	@Override
	public Result saveOrUpdate(CompanyDto dto) {
		if (StringUtils.isBlank(dto.getId())) {
			Criteria criteria = dao.createCriteria(Company.class);
			criteria.add(Restrictions.eq("unitName", dto.getUnitName()));
			List<Company> companys = (List<Company>) criteria.list();
			if (companys != null && companys.size() > 0) {
				return new Result(Constants.RESULT_CODE.SYS_ERROR, "不能重复添加此公司！");
			} else {

				Company company = new Company();
				BeanUtils.copyProperties(dto, company);
				dao.save(company);
				return new Result(company);
			}
		} else {
			Company company = dao.findById(Company.class, dto.getId());
			if (company == null) {
				return new Result(Constants.RESULT_CODE.SYS_ERROR, "没有此公司！");
			}
			BeanUtils.copyProperties(dto, company);
			dao.update(company);
			return new Result(company);
		}
	}
	// 根据id删除公司
	@Override
	public Result delete(String companyId) {
		Company company = dao.findById(Company.class, companyId);
		if (company != null) {
			dao.delete(company);
			return new Result(company);

		} else {
			return new Result(Constants.RESULT_CODE.SYS_ERROR, "没有此公司！");
		}
	}

}
