package com.kongque.controller.basics;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.kongque.dto.CompanyDto;
import com.kongque.entity.basics.Company;
import com.kongque.service.basics.ICompanyService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@Controller
@RequestMapping("/company")
public class CompanyController {
	private static Logger logger = LoggerFactory.getLogger(CompanyController.class);
	@Resource
	private ICompanyService service;

	/**
	 * 根据ID查询所有公司
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/companyId", method = RequestMethod.GET)
	@ResponseBody
	public Result companyByid(String id) {
		return service.companyById(id);
	}

	/**
	 * 根据条件分页查询公司
	 * 
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Pagination<Company> list(CompanyDto dto, PageBean pageBean) {
		logger.info(dto.toString(), pageBean.toString());
		return service.companyList(dto, pageBean);
	}

	/**
	 * 保存或更新公司信息
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Result saveOrUpdate(@RequestBody CompanyDto dto) {
		logger.info(dto.toString());
		return service.saveOrUpdate(dto);
	}

	/**
	 * 刪除公司信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public Result delete(String id) {
		return service.delete(id);
	}
}
