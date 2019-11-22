package com.kongque.controller.production.basics.process;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kongque.entity.process.MesProcess;
import com.kongque.service.production.basics.process.IProcessService;
import com.kongque.util.PageBean;
import com.kongque.util.Pagination;
import com.kongque.util.Result;

@RestController
@RequestMapping(value = "/process")
public class ProcessController {
	
	@Resource
	private IProcessService service;

	/**
	 * 根据条件分页查询工序列表
	 * @param dto
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Pagination<MesProcess> list(MesProcess process, PageBean pageBean){
		return service.list(process,pageBean);
	}
	
	/**
	 * 保存或更新工序信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public Result saveOrUpdate(@RequestBody MesProcess process) {
		return service.saveOrUpdate(process);
	}
	
	
	/**
	 * 删除工序信息
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public Result del(String id) {
		return service.del(id);
	}
}
