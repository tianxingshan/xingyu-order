package com.kongque.controller.production.basics.material;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kongque.dto.ProductionBasicDto;
import com.kongque.entity.material.MaterialPosition;
import com.kongque.service.production.basics.material.IMaterialPositionService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@RestController
@RequestMapping(value = "/materialPosition")
public class MaterialPositionController {

	@Resource
	private IMaterialPositionService service;
	
	/**
	 * 根据条件分页查询物料部位列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Pagination<MaterialPosition> list(MaterialPosition position, PageBean pageBean){
		return service.list(position,pageBean);
	}
	
	/**
	 * 保存或更新物料部位信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public Result saveOrUpdate(@RequestBody ProductionBasicDto dto) {
		return service.saveOrUpdate(dto);
	}
	
	
	/**
	 * 删除物料部位信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public Result del(String id) {
		return service.del(id);
	}
}
