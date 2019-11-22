package com.kongque.controller.production.basics.process;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kongque.dto.GoodsOptionalTechnologyDto;
import com.kongque.entity.process.GoodsOptionalTechnology;
import com.kongque.service.production.basics.process.IGoodsOptionalTechnologyService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@RestController
@RequestMapping(value = "/goodsOptionalTechnology")
public class GoodsOptionalTechnologyController {
	
	@Resource
	private IGoodsOptionalTechnologyService service;
	
	/**
	 * 根据条件分页查询可选项工艺列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Pagination<GoodsOptionalTechnology> list(GoodsOptionalTechnology technology, PageBean pageBean){
		return service.list(technology,pageBean);
	}
	
	/**
	 * 保存或更新可选项工艺信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public Result saveOrUpdate(@RequestBody GoodsOptionalTechnologyDto dto) {
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
