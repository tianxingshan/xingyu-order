package com.kongque.controller.production.basics.process;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kongque.dto.ProductionBasicDto;
import com.kongque.entity.process.OptionalTechnology;
import com.kongque.service.production.basics.process.IOptionalTechnologyService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@RestController
@RequestMapping(value = "/optionalTechnology")
public class OptionalTechnologyController {
	
	@Resource
	private IOptionalTechnologyService service;
	
	/**
	 * 根据条件分页查询可选项工艺列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Pagination<OptionalTechnology> list(OptionalTechnology technology, PageBean pageBean){
		return service.list(technology,pageBean);
	}
	
	/**
	 * 保存或更新可选项工艺信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public Result saveOrUpdate(@RequestBody ProductionBasicDto dto) {
		return service.saveOrUpdate(dto);
	}
	
	
	/**
	 * 删除可选项工艺信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public Result del(String id) {
		return service.del(id);
	}

}
