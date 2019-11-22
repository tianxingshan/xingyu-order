package com.kongque.controller.basics;

import javax.annotation.Resource;

import com.kongque.dto.DeptDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kongque.dto.TenantDto;
import com.kongque.entity.basics.Tenant;
import com.kongque.service.basics.ITenantService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@Controller
@RequestMapping("/tenant")
public class TenantController {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(TenantController.class);
	@Resource
	private ITenantService service;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody Pagination<Tenant> getOrder(TenantDto dto, PageBean pageBean) {
		return service.TenantList(pageBean, dto);
	}
	
	@RequestMapping(value = "/saveOrUpdate",method = RequestMethod.POST)
	public @ResponseBody Result saveOrUpdate(@RequestBody Tenant tenant) {
			return service.saveOrUpdate(tenant);
	}
	
	@RequestMapping(value = "/tenantId", method = RequestMethod.GET)
	public @ResponseBody Result delete(String tenantId) {
		return service.deleteTenant(tenantId);                                                               
	}
	
	@RequestMapping(value = "/updateStatus", method = RequestMethod.GET)
	public @ResponseBody Result updateStatus(String tenantId,String tenantStatus) {
		return service.updateStatusTenant(tenantId,tenantStatus);
	}
	/**
	 * 获取系统标识列表
	 * @return
	 */
	@RequestMapping(value = "/sys/list", method = RequestMethod.GET)
	public @ResponseBody Result getSysList() {
		return service.getSysList();
	}


	/**
	 * 通过订单性质id获取商户信息
	 * @param orderCharacterId
	 * @return
	 */
	@RequestMapping(value = "/getListByOrderCharacterId", method = RequestMethod.GET)
	public @ResponseBody Result getListByOrderCharacterId(String orderCharacterId){
		return service.getListByOrderCharacterId(orderCharacterId);
	}


}
