package com.kongque.controller.basics;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kongque.dto.DeptDto;
import com.kongque.entity.basics.Dept;
import com.kongque.service.basics.IDeptService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@RestController
@RequestMapping("/dept")
public class DeptController {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(DeptController.class);
	@Resource
	private IDeptService service;
	
	/**
	 * 根据条件分页查询门店列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public  Pagination<Dept> DeptList(DeptDto dto, PageBean pageBean){
		return service.DeptList(dto,pageBean);
	}
	
	/**
	 * 保存或更新门店信息
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public  Result saveOrUpdate(@RequestBody DeptDto dto) {
		return service.saveOrUpdate(dto);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
    @RequestMapping(value ="/delete/id", method = RequestMethod.GET)
    public  Result delete(String id){
        return service.delete(id);
    }

    /*
    部门列表
     */
    @RequestMapping(value ="/list/parent", method = RequestMethod.GET)
    public  Result parent(String tenantId){
        return service.parent(tenantId);
    }


	/**
	 * 获取商户下的部门
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/getTenantDepts", method = RequestMethod.GET)
    public Result getTenantDepts(DeptDto dto){
		return service.getTenantDeps(dto);
	}

	/**
	 * 给用户授店铺权限
	 * @param dto
	 * @return
	 */
	@RequestMapping(value ="/saveShopList/byUserId", method = RequestMethod.POST)
	public Result saveShopListByUserId(@RequestBody DeptDto dto){
		return service.saveShopListByUserId(dto);
	}

	
}
