package com.kongque.controller.customer;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kongque.controller.user.UserController;
import com.kongque.dto.XiuyuCustomerDto;
import com.kongque.entity.basics.XiuyuCustomer;
import com.kongque.service.customer.ICustomerService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	@Resource
	private ICustomerService service;
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Pagination<XiuyuCustomer> customerList(XiuyuCustomerDto dto,PageBean pageBean){
		logger.error("会员查询开始");
		return service.customerList(dto,pageBean);
	}
	
	
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Result saveOrUpdateCustomer(@RequestBody XiuyuCustomer customer){
		logger.error("会员新增或修改开始");
		return service.saveOrUpdateCustomer(customer);
	}
	
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	@ResponseBody
	public Result delCustomer(String id){
		return service.delCustomer(id);
	}
	
	/**
	 * 根据条件模糊查询会员信息
	 * @param q
	 * @return
	 */
	@RequestMapping(value = "/remote", method = RequestMethod.GET)
	@ResponseBody
	public Result remoteCustomer(String q,PageBean pageBean){
		return service.remoteCustomer(q,pageBean);
	}

}
