package com.kongque.controller.production.basics.process;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kongque.dto.GoodsSewProcessDto;
import com.kongque.entity.process.GoodsSewProcess;
import com.kongque.service.production.basics.process.IGoodsSewProcessService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@RestController
@RequestMapping(value = "/goodsSewProcess")
public class GoodsSewProcessController {
	
	@Resource
	private IGoodsSewProcessService service;
	
	/**
	 * 根据条件分页查询可选项工艺列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Pagination<GoodsSewProcess> list(GoodsSewProcess sewProcess, PageBean pageBean){
		return service.list(sewProcess,pageBean);
	}
	
	/**
	 * 保存或更新可选项工艺信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public Result saveOrUpdate(@RequestBody GoodsSewProcessDto dto) {
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
	
	
	
	/**
	 * 根据品类获取所有部位以及部位下的工艺
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/getPostionInfo/byCategoryId", method = RequestMethod.GET)
	public Result getPostionInfo(String categoryId) {
		return service.getPostionInfo(categoryId);
	}
}
