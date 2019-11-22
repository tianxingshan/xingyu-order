package com.kongque.controller.production.basics.material;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kongque.dto.ProductionBasicDto;
import com.kongque.entity.material.MaterialCategory;
import com.kongque.service.production.basics.material.IMaterialCategoryService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@RestController
@RequestMapping(value = "/materialCategory")
public class MaterialCategoryController {
	
	@Resource
	private IMaterialCategoryService service;
	
	/**
	 * 获取大类列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Pagination<MaterialCategory> list(PageBean pageBean){
		return service.list(pageBean);
	}
	
	/**
	 * 根据大类id获取中类列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/list/children",method=RequestMethod.GET)
	public Pagination<MaterialCategory> listChildren(String id,PageBean pageBean){
		return service.listChildren(id,pageBean);
	}
	
	/**
	 * 保存或更新物料分类信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public Result saveOrUpdate(@RequestBody ProductionBasicDto dto) {
		return service.saveOrUpdate(dto);
	}
	
	
	/**
	 * 删除物料分类信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public Result del(String id) {
		return service.del(id);
	}
	
}
